#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
mapofdev API 테스트 자동화 스크립트
CSV 테스트 케이스를 읽어서 자동으로 API 테스트를 실행합니다.
"""

import requests
import json
import csv
import time
import argparse
from datetime import datetime
from typing import Dict, List, Optional, Tuple
import os
import sys
from urllib.parse import urljoin
import pandas as pd
from pathlib import Path

class APITestRunner:
    def __init__(self, base_url: str = "http://localhost:3000", csv_file: str = None):
        """
        API 테스트 러너 초기화
        
        Args:
            base_url: API 서버의 기본 URL
            csv_file: 테스트 케이스 CSV 파일 경로
        """
        self.base_url = base_url.rstrip('/')
        
        # CSV 파일 경로 설정 (None이면 자동으로 찾기)
        if csv_file is None:
            current_dir = Path(__file__).parent.absolute()
            project_root = current_dir.parent
            csv_file = str(project_root / "docs" / "qa" / "test_cases_template.csv")
        
        self.csv_file = csv_file
        self.session = requests.Session()
        self.auth_token = None
        self.test_results = []
        
        # 결과 저장 폴더 생성
        csv_path = Path(self.csv_file)
        self.results_dir = str(csv_path.parent / "test_results")
        os.makedirs(self.results_dir, exist_ok=True)
        
        print(f"🚀 API 테스트 러너 초기화")
        print(f"📍 기본 URL: {self.base_url}")
        print(f"📋 테스트 케이스 파일: {self.csv_file}")
        print(f"📁 결과 저장 디렉토리: {self.results_dir}")

    # ... 나머지 메서드들은 기존과 동일 ...
    
    def load_test_cases(self) -> List[Dict]:
        """CSV 파일에서 테스트 케이스를 로드합니다."""
        test_cases = []
        try:
            with open(self.csv_file, 'r', encoding='utf-8') as file:
                reader = csv.DictReader(file)
                for row in reader:
                    test_cases.append(row)
            print(f"✅ {len(test_cases)}개의 테스트 케이스를 로드했습니다.")
            return test_cases
        except FileNotFoundError:
            print(f"❌ 테스트 케이스 파일을 찾을 수 없습니다: {self.csv_file}")
            print("💡 샘플 CSV 파일을 생성하려면 launch_in_vscode.py를 실행하세요.")
            return []
        except Exception as e:
            print(f"❌ 테스트 케이스 로드 중 오류: {e}")
            return []

    def execute_api_request(self, method: str, endpoint: str, data: Dict = None, 
                          headers: Dict = None, expect_status: int = 200) -> Tuple[bool, Dict]:
        """
        API 요청을 실행합니다.
        
        Args:
            method: HTTP 메서드 (GET, POST, PUT, DELETE)
            endpoint: API 엔드포인트
            data: 요청 본문 데이터
            headers: 요청 헤더
            expect_status: 예상 HTTP 상태 코드
            
        Returns:
            (성공 여부, 응답 데이터)
        """
        url = urljoin(self.base_url, endpoint.lstrip('/'))
        
        # 기본 헤더 설정
        default_headers = {'Content-Type': 'application/json'}
        if headers:
            default_headers.update(headers)
        
        # 인증 토큰이 있으면 추가
        if self.auth_token and 'Authorization' not in default_headers:
            default_headers['Authorization'] = f'Bearer {self.auth_token}'
        
        try:
            print(f"📤 요청: {method} {url}")
            
            if method.upper() == 'GET':
                response = self.session.get(url, headers=default_headers, timeout=30)
            elif method.upper() == 'POST':
                response = self.session.post(url, json=data, headers=default_headers, timeout=30)
            elif method.upper() == 'PUT':
                response = self.session.put(url, json=data, headers=default_headers, timeout=30)
            elif method.upper() == 'DELETE':
                response = self.session.delete(url, headers=default_headers, timeout=30)
            else:
                return False, {"error": f"지원하지 않는 HTTP 메서드: {method}"}
            
            print(f"📥 응답: {response.status_code}")
            
            # JSON 응답 파싱 시도
            try:
                response_data = response.json()
            except:
                response_data = {"text": response.text, "status_code": response.status_code}
            
            # 상태 코드 확인
            success = response.status_code == expect_status
            
            return success, {
                "status_code": response.status_code,
                "data": response_data,
                "headers": dict(response.headers),
                "url": url,
                "method": method
            }
            
        except requests.exceptions.ConnectionError:
            return False, {"error": "연결 실패 - 서버가 실행 중인지 확인하세요"}
        except requests.exceptions.Timeout:
            return False, {"error": "요청 시간 초과"}
        except Exception as e:
            return False, {"error": f"요청 실행 중 오류: {str(e)}"}
    
    def parse_test_step(self, test_step: str) -> Tuple[str, str, Dict, int]:
        """
        테스트 단계를 파싱하여 HTTP 메서드, 엔드포인트, 데이터, 예상 상태코드를 추출합니다.
        """
        lines = test_step.strip().split('\n')
        
        # 첫 번째 줄에서 메서드와 엔드포인트 추출
        first_line = lines[0].strip()
        if first_line.startswith('GET '):
            method = 'GET'
            endpoint = first_line[4:].strip()
            data = None
            expect_status = 200
        elif first_line.startswith('POST '):
            method = 'POST'
            endpoint = first_line[5:].strip()
            data = {}
            expect_status = 201 if '/signup' in endpoint else 200
            
            # JSON 데이터 파싱
            for line in lines[1:]:
                line = line.strip()
                if line.startswith('{') and line.endswith('}'):
                    try:
                        data = json.loads(line)
                    except:
                        # JSON 파싱 실패 시 기본값 사용
                        pass
        else:
            # 기본값
            method = 'GET'
            endpoint = '/api/v1/trends'
            data = None
            expect_status = 200
        
        return method, endpoint, data, expect_status
    
    def run_single_test(self, test_case: Dict) -> Dict:
        """단일 테스트 케이스를 실행합니다."""
        tc_id = test_case['TC_ID']
        test_name = test_case['테스트명']
        test_step = test_case['테스트단계']
        expected_result = test_case['예상결과']
        
        print(f"\n🧪 테스트 실행: {tc_id} - {test_name}")
        
        # 특별한 사전조건 처리
        precondition = test_case['사전조건']
        if '서버중지' in precondition:
            # 서버 다운 시뮬레이션
            original_base_url = self.base_url
            self.base_url = "http://invalid-server:9999"
        
        # 테스트 단계 파싱
        method, endpoint, data, expect_status = self.parse_test_step(test_step)
        
        # 예상 결과에서 상태 코드 추출
        if '200' in expected_result:
            expect_status = 200
        elif '201' in expected_result:
            expect_status = 201
        elif '400' in expected_result:
            expect_status = 400
        elif '401' in expected_result:
            expect_status = 401
        elif '404' in expected_result:
            expect_status = 404
        elif '409' in expected_result:
            expect_status = 409
        elif '413' in expected_result:
            expect_status = 413
        
        # API 요청 실행
        start_time = time.time()
        success, response = self.execute_api_request(method, endpoint, data, expect_status=expect_status)
        execution_time = time.time() - start_time
        
        # 결과 분석
        actual_result = self.analyze_response(response, expected_result)
        pass_fail = "PASS" if success and self.validate_response(response, expected_result) else "FAIL"
        
        # 서버 URL 복원
        if '서버중지' in precondition:
            self.base_url = original_base_url
        
        # 인증 토큰 저장 (로그인 성공 시)
        if tc_id == 'TC009' and pass_fail == "PASS" and 'data' in response:
            if isinstance(response['data'], dict) and 'token' in response['data']:
                self.auth_token = response['data']['token']
                print(f"🔑 인증 토큰 저장됨")
        
        result = {
            'TC_ID': tc_id,
            '기능분류': test_case['기능분류'],
            '테스트명': test_name,
            '사전조건': precondition,
            '테스트단계': test_step,
            '예상결과': expected_result,
            '실제결과': actual_result,
            'Pass/Fail': pass_fail,
            '비고': test_case['비고'],
            '실행시간': f"{execution_time:.2f}초",
            '타임스탬프': datetime.now().isoformat(),
            '응답데이터': response
        }
        
        print(f"📊 결과: {pass_fail} ({execution_time:.2f}초)")
        return result
    
    def analyze_response(self, response: Dict, expected_result: str) -> str:
        """응답을 분석하여 실제 결과를 생성합니다."""
        if 'error' in response:
            return f"오류: {response['error']}"
        
        status_code = response.get('status_code', 'Unknown')
        
        if status_code == 200:
            data = response.get('data', {})
            if isinstance(data, list):
                return f"200 OK, JSON 배열 ({len(data)}개 항목)"
            elif isinstance(data, dict):
                return f"200 OK, JSON 객체 (키: {list(data.keys())[:3]}...)"
            else:
                return f"200 OK, 데이터 타입: {type(data).__name__}"
        elif status_code == 201:
            return "201 Created, 생성 성공"
        elif status_code == 400:
            return "400 Bad Request, 잘못된 요청"
        elif status_code == 401:
            return "401 Unauthorized, 인증 실패"
        elif status_code == 404:
            return "404 Not Found, 리소스 없음"
        elif status_code == 409:
            return "409 Conflict, 충돌 발생"
        else:
            return f"{status_code} 응답"
    
    def validate_response(self, response: Dict, expected_result: str) -> bool:
        """응답이 예상 결과와 일치하는지 검증합니다."""
        if 'error' in response:
            return '연결실패' in expected_result or '실패' in expected_result
        
        status_code = response.get('status_code')
        
        # 상태 코드 검증
        if '200' in expected_result and status_code != 200:
            return False
        if '201' in expected_result and status_code != 201:
            return False
        if '400' in expected_result and status_code != 400:
            return False
        if '401' in expected_result and status_code != 401:
            return False
        if '404' in expected_result and status_code != 404:
            return False
        if '409' in expected_result and status_code != 409:
            return False
        
        # 데이터 형태 검증
        data = response.get('data', {})
        if 'JSON 배열' in expected_result and not isinstance(data, list):
            return False
        if 'JSON' in expected_result and status_code == 200 and not (isinstance(data, (list, dict))):
            return False
        
        return True
    
    def run_all_tests(self, test_ids: List[str] = None) -> List[Dict]:
        """모든 테스트 케이스 또는 지정된 테스트들을 실행합니다."""
        test_cases = self.load_test_cases()
        if not test_cases:
            return []
        
        # 특정 테스트 ID가 지정된 경우 필터링
        if test_ids:
            test_cases = [tc for tc in test_cases if tc['TC_ID'] in test_ids]
            print(f"🎯 지정된 테스트만 실행: {test_ids}")
        
        print(f"\n📋 총 {len(test_cases)}개의 테스트 케이스 실행 시작")
        print("=" * 50)
        
        results = []
        for i, test_case in enumerate(test_cases, 1):
            print(f"\n진행률: {i}/{len(test_cases)}")
            result = self.run_single_test(test_case)
            results.append(result)
            
            # 잠시 대기 (서버 부하 방지)
            time.sleep(0.5)
        
        self.test_results = results
        return results
    
    def generate_report(self, results: List[Dict]) -> str:
        """테스트 결과 리포트를 생성합니다."""
        if not results:
            return "테스트 결과가 없습니다."
        
        total_tests = len(results)
        passed_tests = len([r for r in results if r['Pass/Fail'] == 'PASS'])
        failed_tests = total_tests - passed_tests
        pass_rate = (passed_tests / total_tests) * 100
        
        # 카테고리별 통계
        categories = {}
        for result in results:
            category = result['기능분류']
            if category not in categories:
                categories[category] = {'total': 0, 'passed': 0}
            categories[category]['total'] += 1
            if result['Pass/Fail'] == 'PASS':
                categories[category]['passed'] += 1
        
        # 리포트 생성
        report = f"""
# mapofdev API 테스트 결과 리포트

## 📊 전체 통계
- **총 테스트 수**: {total_tests}개
- **성공**: {passed_tests}개 ✅
- **실패**: {failed_tests}개 ❌
- **성공률**: {pass_rate:.1f}%

## 📋 카테고리별 결과
"""
        
        for category, stats in categories.items():
            rate = (stats['passed'] / stats['total']) * 100 if stats['total'] > 0 else 0
            report += f"- **{category}**: {stats['passed']}/{stats['total']} ({rate:.1f}%)\n"
        
        report += "\n## 🔍 상세 결과\n\n"
        
        for result in results:
            status_emoji = "✅" if result['Pass/Fail'] == 'PASS' else "❌"
            report += f"### {status_emoji} {result['TC_ID']} - {result['테스트명']}\n"
            report += f"- **실제 결과**: {result['실제결과']}\n"
            report += f"- **실행 시간**: {result['실행시간']}\n"
            if result['Pass/Fail'] == 'FAIL':
                report += f"- **예상 결과**: {result['예상결과']}\n"
            report += "\n"
        
        # 실패한 테스트 요약
        failed_results = [r for r in results if r['Pass/Fail'] == 'FAIL']
        if failed_results:
            report += "## ⚠️ 실패한 테스트 요약\n\n"
            for result in failed_results:
                report += f"- **{result['TC_ID']}**: {result['테스트명']} - {result['실제결과']}\n"
        
        report += f"\n---\n**리포트 생성 시간**: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n"
        report += f"**작성자**: MiniMax Agent\n"
        
        return report
    
    def save_results(self, results: List[Dict], format: str = 'both'):
        """테스트 결과를 파일로 저장합니다."""
        timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
        
        if format in ['json', 'both']:
            # JSON 파일 저장
            json_file = os.path.join(self.results_dir, f'test_results_{timestamp}.json')
            with open(json_file, 'w', encoding='utf-8') as f:
                json.dump(results, f, ensure_ascii=False, indent=2)
            print(f"💾 JSON 결과 저장: {json_file}")
            
            # 최신 결과 링크 생성
            latest_json = os.path.join(self.results_dir, 'latest_test_results.json')
            with open(latest_json, 'w', encoding='utf-8') as f:
                json.dump(results, f, ensure_ascii=False, indent=2)
        
        if format in ['csv', 'both']:
            # CSV 파일 저장 (실행 결과가 추가된 형태)
            csv_file = os.path.join(self.results_dir, f'test_results_{timestamp}.csv')
            df = pd.DataFrame(results)
            # 응답데이터 컬럼은 제외 (너무 복잡함)
            df_save = df.drop('응답데이터', axis=1, errors='ignore')
            df_save.to_csv(csv_file, index=False, encoding='utf-8-sig')
            print(f"💾 CSV 결과 저장: {csv_file}")
        
        # 리포트 저장
        report = self.generate_report(results)
        report_file = os.path.join(self.results_dir, f'test_report_{timestamp}.md')
        with open(report_file, 'w', encoding='utf-8') as f:
            f.write(report)
        print(f"📄 리포트 저장: {report_file}")
        
        # 최신 리포트 링크 생성
        latest_report = os.path.join(self.results_dir, 'latest_test_report.md')
        with open(latest_report, 'w', encoding='utf-8') as f:
            f.write(report)

def main():
    """메인 실행 함수"""
    parser = argparse.ArgumentParser(description='mapofdev API 테스트 러너')
    parser.add_argument('--base-url', default='http://localhost:3000', 
                       help='API 서버 기본 URL (기본값: http://localhost:3000)')
    parser.add_argument('--csv-file', default=None,
                       help='테스트 케이스 CSV 파일 경로 (기본값: 자동 탐지)')
    parser.add_argument('--test-id', action='append', 
                       help='실행할 특정 테스트 ID (예: TC001). 여러 개 지정 가능')
    parser.add_argument('--format', choices=['json', 'csv', 'both'], default='both',
                       help='결과 저장 형식 (기본값: both)')
    
    args = parser.parse_args()
    
    # 테스트 러너 초기화
    runner = APITestRunner(base_url=args.base_url, csv_file=args.csv_file)
    
    # 테스트 실행
    results = runner.run_all_tests(test_ids=args.test_id)
    
    if results:
        # 결과 저장
        runner.save_results(results, format=args.format)
        
        # 요약 출력
        print("\n" + "="*50)
        print("🎉 테스트 완료!")
        total = len(results)
        passed = len([r for r in results if r['Pass/Fail'] == 'PASS'])
        print(f"📊 결과: {passed}/{total} 성공 ({(passed/total)*100:.1f}%)")
        print(f"📁 결과 파일: {runner.results_dir}/latest_test_report.md")
    else:
        print("❌ 실행된 테스트가 없습니다.")

if __name__ == "__main__":
    main()