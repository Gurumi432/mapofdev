#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
완전한 테스트 실행 스크립트
Mock 서버를 실행하고 전체 테스트를 수행한 후 결과를 분석합니다.
"""

import sys
import time
import subprocess
import signal
import os
from test_utils import MockServer
from api_test_runner import APITestRunner

class FullTestRunner:
    """완전한 테스트 실행기"""
    
    def __init__(self):
        self.mock_server = None
        self.test_runner = None
    
    def setup_environment(self):
        """테스트 환경 설정"""
        print("🔧 테스트 환경 설정 중...")
        
        # Mock 서버 시작
        self.mock_server = MockServer('localhost', 3000)
        if not self.mock_server.start():
            print("❌ Mock 서버 시작 실패")
            return False
        
        # 서버가 완전히 시작될 때까지 대기
        time.sleep(2)
        
        # 테스트 러너 초기화
        self.test_runner = APITestRunner(
            base_url="http://localhost:3000",
            csv_file="/workspace/docs/qa/test_cases_template.csv"
        )
        
        print("✅ 테스트 환경 설정 완료")
        return True
    
    def run_basic_api_tests(self):
        """기본 API 테스트 실행"""
        print("\n📋 기본 API 테스트 실행 중...")
        
        # 공개 API 테스트 (TC001, TC003, TC004)
        basic_tests = ['TC001', 'TC003', 'TC004']
        results = self.test_runner.run_all_tests(test_ids=basic_tests)
        
        return results
    
    def run_auth_tests(self):
        """인증 관련 테스트 실행"""
        print("\n🔐 인증 테스트 실행 중...")
        
        # 인증 API 테스트 (TC006, TC007, TC008, TC009, TC010, TC011)
        auth_tests = ['TC006', 'TC007', 'TC008', 'TC009', 'TC010', 'TC011']
        results = self.test_runner.run_all_tests(test_ids=auth_tests)
        
        return results
    
    def run_protected_api_tests(self):
        """보호된 API 테스트 실행"""
        print("\n🛡️ 보호된 API 테스트 실행 중...")
        
        # 보호된 API 테스트 (TC012, TC013, TC014)
        protected_tests = ['TC012', 'TC013', 'TC014']
        results = self.test_runner.run_all_tests(test_ids=protected_tests)
        
        return results
    
    def run_scenario_tests(self):
        """시나리오 테스트 실행"""
        print("\n🎭 시나리오 테스트 실행 중...")
        
        # 시나리오 테스트 (TC015, TC016)
        scenario_tests = ['TC015', 'TC016']
        results = self.test_runner.run_all_tests(test_ids=scenario_tests)
        
        return results
    
    def cleanup(self):
        """정리 작업"""
        print("\n🧹 정리 작업 중...")
        
        if self.mock_server:
            self.mock_server.stop()
        
        print("✅ 정리 완료")
    
    def run_full_test_suite(self):
        """전체 테스트 스위트 실행"""
        try:
            print("🚀 mapofdev API 전체 테스트 시작")
            print("=" * 60)
            
            # 환경 설정
            if not self.setup_environment():
                return False
            
            all_results = []
            
            # 단계별 테스트 실행
            basic_results = self.run_basic_api_tests()
            if basic_results:
                all_results.extend(basic_results)
            
            auth_results = self.run_auth_tests()
            if auth_results:
                all_results.extend(auth_results)
            
            protected_results = self.run_protected_api_tests()
            if protected_results:
                all_results.extend(protected_results)
            
            scenario_results = self.run_scenario_tests()
            if scenario_results:
                all_results.extend(scenario_results)
            
            # 결과 저장 및 분석
            if all_results:
                self.test_runner.save_results(all_results)
                self.analyze_results(all_results)
            
            return True
            
        except KeyboardInterrupt:
            print("\n\n⚠️ 사용자에 의해 테스트가 중단되었습니다.")
            return False
        except Exception as e:
            print(f"\n❌ 테스트 실행 중 오류 발생: {e}")
            return False
        finally:
            self.cleanup()
    
    def analyze_results(self, results):
        """결과 분석 및 요약"""
        print("\n" + "=" * 60)
        print("📊 테스트 결과 분석")
        print("=" * 60)
        
        total_tests = len(results)
        passed_tests = len([r for r in results if r['Pass/Fail'] == 'PASS'])
        failed_tests = total_tests - passed_tests
        pass_rate = (passed_tests / total_tests) * 100 if total_tests > 0 else 0
        
        print(f"📈 전체 통계:")
        print(f"   총 테스트: {total_tests}개")
        print(f"   성공: {passed_tests}개 ✅")
        print(f"   실패: {failed_tests}개 ❌")
        print(f"   성공률: {pass_rate:.1f}%")
        
        # 카테고리별 분석
        categories = {}
        for result in results:
            category = result['기능분류']
            if category not in categories:
                categories[category] = {'total': 0, 'passed': 0, 'failed_tests': []}
            categories[category]['total'] += 1
            if result['Pass/Fail'] == 'PASS':
                categories[category]['passed'] += 1
            else:
                categories[category]['failed_tests'].append(result['TC_ID'])
        
        print(f"\n📋 카테고리별 분석:")
        for category, stats in categories.items():
            rate = (stats['passed'] / stats['total']) * 100
            status = "✅" if rate == 100 else "⚠️" if rate >= 50 else "❌"
            print(f"   {status} {category}: {stats['passed']}/{stats['total']} ({rate:.1f}%)")
            if stats['failed_tests']:
                print(f"      실패: {', '.join(stats['failed_tests'])}")
        
        # 권장사항
        print(f"\n💡 권장사항:")
        if pass_rate == 100:
            print("   🎉 모든 테스트가 통과했습니다! 배포 준비가 완료되었습니다.")
        elif pass_rate >= 80:
            print("   👍 대부분의 테스트가 통과했습니다. 실패한 테스트들을 확인하세요.")
        elif pass_rate >= 50:
            print("   ⚠️ 일부 중요한 기능에 문제가 있을 수 있습니다. 실패 원인을 조사하세요.")
        else:
            print("   🚨 많은 테스트가 실패했습니다. 시스템 전반적인 점검이 필요합니다.")
        
        print(f"\n📁 상세 결과: /workspace/docs/qa/test_results/latest_test_report.md")

def main():
    """메인 실행 함수"""
    runner = FullTestRunner()
    
    # 시그널 핸들러 등록 (Ctrl+C 처리)
    def signal_handler(sig, frame):
        print("\n\n⚠️ 종료 신호 받음. 정리 중...")
        runner.cleanup()
        sys.exit(0)
    
    signal.signal(signal.SIGINT, signal_handler)
    
    # 전체 테스트 실행
    success = runner.run_full_test_suite()
    
    if success:
        print("\n🎉 전체 테스트 완료!")
        exit_code = 0
    else:
        print("\n❌ 테스트 실행 실패!")
        exit_code = 1
    
    sys.exit(exit_code)

if __name__ == "__main__":
    main()
