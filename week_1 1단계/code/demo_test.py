#!/usr/bin/env python3
"""
API 테스트 러너 데모 스크립트
실제 서버 없이도 테스트 스크립트 동작을 확인할 수 있습니다.
"""

import sys
import os
from pathlib import Path

# 현재 스크립트의 디렉토리를 sys.path에 추가
current_dir = Path(__file__).parent.absolute()
sys.path.insert(0, str(current_dir))

try:
    from api_test_runner import APITestRunner
except ImportError as e:
    print(f"❌ api_test_runner 모듈을 찾을 수 없습니다: {e}")
    print(f"현재 디렉토리: {current_dir}")
    print("api_test_runner.py 파일이 같은 디렉토리에 있는지 확인해주세요.")
    sys.exit(1)

def get_project_paths():
    """프로젝트 경로들을 자동으로 찾습니다."""
    # 현재 스크립트의 디렉토리
    code_dir = Path(__file__).parent.absolute()
    
    # 프로젝트 루트 디렉토리 (code의 상위 디렉토리)
    project_root = code_dir.parent
    
    # docs 디렉토리
    docs_dir = project_root / "docs"
    qa_dir = docs_dir / "qa"
    
    # 필요한 디렉토리들이 없으면 생성
    qa_dir.mkdir(parents=True, exist_ok=True)
    (qa_dir / "test_results").mkdir(exist_ok=True)
    
    return {
        'code_dir': str(code_dir),
        'csv_file': str(qa_dir / "test_cases_template.csv"),
        'results_dir': str(qa_dir / "test_results")
    }

def create_sample_csv_if_missing(csv_path):
    """테스트 케이스 CSV 파일이 없으면 샘플을 생성합니다."""
    csv_file = Path(csv_path)
    if not csv_file.exists():
        print(f"📋 테스트 케이스 CSV 파일이 없어서 샘플을 생성합니다: {csv_file}")
        
        # 샘플 CSV 내용
        sample_csv = '''TC_ID,기능분류,테스트명,사전조건,테스트단계,예상결과,비고
TC001,공개API,트렌드 데이터 조회,서버실행,GET /api/v1/trends,200 OK JSON 배열,기본 API 테스트
TC002,공개API,존재하지 않는 엔드포인트,서버실행,GET /api/v1/nonexistent,404 Not Found,에러 처리 테스트
TC003,공개API,인기 스킬 조회,서버실행,GET /api/v1/skills/popular,200 OK JSON 배열,스킬 데이터 테스트'''
        
        csv_file.parent.mkdir(parents=True, exist_ok=True)
        with open(csv_file, 'w', encoding='utf-8') as f:
            f.write(sample_csv)
        print("✅ 샘플 테스트 케이스 파일 생성 완료!")

def demo_test():
    """데모 테스트 실행"""
    print("🎭 API 테스트 러너 데모 실행")
    print("=" * 50)
    
    try:
        # 프로젝트 경로 설정
        paths = get_project_paths()
        
        print(f"📍 코드 디렉토리: {paths['code_dir']}")
        print(f"📍 CSV 파일: {paths['csv_file']}")
        
        # 샘플 CSV 파일 생성 (없는 경우)
        create_sample_csv_if_missing(paths['csv_file'])
        
        # 테스트 러너 초기화
        runner = APITestRunner(
            base_url="http://localhost:3000",
            csv_file=paths['csv_file']
        )
        
        # 샘플 테스트 (처음 3개만 실행)
        print("\n📋 샘플 테스트 실행 (TC001, TC002, TC003)")
        results = runner.run_all_tests(test_ids=['TC001', 'TC002', 'TC003'])
        
        if results:
            # 결과 저장
            runner.save_results(results)
            print("\n✅ 데모 완료! 결과는 다음 위치에 저장되었습니다:")
            print(f"📁 {paths['results_dir']}/latest_test_report.md")
        else:
            print("❌ 테스트 실행 실패 - 서버가 실행 중이지 않을 수 있습니다.")
            print("💡 Mock 서버를 먼저 실행해보세요: python test_utils.py server")
            
    except Exception as e:
        print(f"❌ 데모 실행 중 오류 발생: {e}")
        print("스택 트레이스:")
        import traceback
        traceback.print_exc()

if __name__ == "__main__":
    demo_test()