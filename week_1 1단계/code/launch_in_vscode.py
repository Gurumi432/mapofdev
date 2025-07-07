#!/usr/bin/env python3
"""
VSCode에서 API 테스트 러너를 쉽게 실행하기 위한 런처 스크립트
이 파일을 VSCode에서 F5로 실행하면 테스트를 선택할 수 있습니다.
"""

import os
import sys
import subprocess
from pathlib import Path

def get_project_paths():
    """프로젝트 경로들을 자동으로 찾습니다."""
    # 현재 스크립트의 디렉토리
    current_dir = Path(__file__).parent.absolute()
    
    # code 디렉토리 (현재 스크립트가 있는 곳)
    code_dir = current_dir
    
    # 프로젝트 루트 디렉토리 (code의 상위 디렉토리)
    project_root = current_dir.parent
    
    # docs 디렉토리
    docs_dir = project_root / "docs"
    qa_dir = docs_dir / "qa"
    
    # 필요한 디렉토리들이 없으면 생성
    qa_dir.mkdir(parents=True, exist_ok=True)
    (qa_dir / "test_results").mkdir(exist_ok=True)
    
    return {
        'code_dir': str(code_dir),
        'project_root': str(project_root),
        'docs_dir': str(docs_dir),
        'qa_dir': str(qa_dir),
        'csv_file': str(qa_dir / "test_cases_template.csv"),
        'results_dir': str(qa_dir / "test_results")
    }

def show_menu():
    """실행 옵션 메뉴를 표시합니다."""
    print("🚀 API 테스트 러너 - VSCode 실행 메뉴")
    print("=" * 50)
    print("1. 📋 데모 테스트 실행 (빠른 확인)")
    print("2. 🔧 Mock 서버만 시작")
    print("3. 🧪 개별 테스트 실행")
    print("4. 🎯 전체 테스트 실행 (Mock 서버 포함)")
    print("5. 📊 최신 테스트 리포트 보기")
    print("6. 🔧 테스트 데이터 생성")
    print("7. 📁 프로젝트 경로 확인")
    print("0. ❌ 종료")
    print("-" * 50)

def run_demo(paths):
    """데모 테스트 실행"""
    print("\n🎭 데모 테스트 실행 중...")
    os.chdir(paths['code_dir'])
    subprocess.run([sys.executable, "demo_test.py"])

def run_mock_server(paths):
    """Mock 서버 실행"""
    print("\n🔧 Mock 서버 시작...")
    print("⚠️  Ctrl+C로 서버를 중지할 수 있습니다.")
    os.chdir(paths['code_dir'])
    subprocess.run([sys.executable, "test_utils.py", "server"])

def run_individual_test(paths):
    """개별 테스트 실행"""
    print("\n🧪 개별 테스트 실행")
    print("사용 가능한 테스트 ID:")
    print("TC001: 트렌드 데이터 조회")
    print("TC002: 존재하지 않는 엔드포인트") 
    print("TC003: 인기 스킬 조회")
    print("TC006: 회원가입 성공")
    print("TC009: 로그인 성공")
    print("...")
    
    test_ids = input("실행할 테스트 ID들을 입력하세요 (공백으로 구분, 예: TC001 TC003): ").strip()
    if test_ids:
        os.chdir(paths['code_dir'])
        cmd = [sys.executable, "api_test_runner.py", "--csv-file", paths['csv_file']]
        for test_id in test_ids.split():
            cmd.extend(["--test-id", test_id])
        subprocess.run(cmd)
    else:
        print("❌ 테스트 ID가 입력되지 않았습니다.")

def run_full_test(paths):
    """전체 테스트 실행"""
    print("\n🎯 전체 테스트 실행 중...")
    print("⚠️  이 작업은 몇 분 정도 소요될 수 있습니다.")
    os.chdir(paths['code_dir'])
    subprocess.run([sys.executable, "run_full_test.py"])

def show_latest_report(paths):
    """최신 테스트 리포트 보기"""
    report_path = Path(paths['results_dir']) / "latest_test_report.md"
    if report_path.exists():
        print("\n📊 최신 테스트 리포트:")
        print("=" * 60)
        with open(report_path, 'r', encoding='utf-8') as f:
            content = f.read()
            # 처음 30줄만 표시
            lines = content.split('\n')[:30]
            print('\n'.join(lines))
            if len(content.split('\n')) > 30:
                print("\n... (더 보려면 파일을 직접 열어보세요)")
        print(f"\n📁 전체 리포트: {report_path}")
    else:
        print("❌ 테스트 리포트를 찾을 수 없습니다. 먼저 테스트를 실행하세요.")

def create_test_data(paths):
    """테스트 데이터 생성"""
    print("\n🔧 테스트 데이터 생성 중...")
    os.chdir(paths['code_dir'])
    subprocess.run([sys.executable, "test_utils.py", "data"])

def show_project_paths(paths):
    """프로젝트 경로 확인"""
    print("\n📁 프로젝트 경로 정보:")
    print("=" * 50)
    for key, value in paths.items():
        status = "✅" if Path(value).exists() else "❌"
        print(f"{status} {key}: {value}")
    print()

def create_sample_csv_if_missing(paths):
    """테스트 케이스 CSV 파일이 없으면 샘플을 생성합니다."""
    csv_path = Path(paths['csv_file'])
    if not csv_path.exists():
        print(f"📋 테스트 케이스 CSV 파일이 없어서 샘플을 생성합니다: {csv_path}")
        
        # 샘플 CSV 내용
        sample_csv = '''TC_ID,기능분류,테스트명,사전조건,테스트단계,예상결과,비고
TC001,공개API,트렌드 데이터 조회,서버실행,GET /api/v1/trends,200 OK JSON 배열,기본 API 테스트
TC002,공개API,존재하지 않는 엔드포인트,서버실행,GET /api/v1/nonexistent,404 Not Found,에러 처리 테스트
TC003,공개API,인기 스킬 조회,서버실행,GET /api/v1/skills/popular,200 OK JSON 배열,스킬 데이터 테스트'''
        
        csv_path.parent.mkdir(parents=True, exist_ok=True)
        with open(csv_path, 'w', encoding='utf-8') as f:
            f.write(sample_csv)
        print("✅ 샘플 테스트 케이스 파일 생성 완료!")

def main():
    """메인 함수"""
    try:
        # 프로젝트 경로 설정
        paths = get_project_paths()
        
        print(f"📍 현재 작업 디렉토리: {os.getcwd()}")
        print(f"📍 코드 디렉토리: {paths['code_dir']}")
        
        # 샘플 CSV 파일 생성 (없는 경우)
        create_sample_csv_if_missing(paths)
        
        while True:
            show_menu()
            try:
                choice = input("선택하세요 (0-7): ").strip()
                
                if choice == "1":
                    run_demo(paths)
                elif choice == "2":
                    run_mock_server(paths)
                elif choice == "3":
                    run_individual_test(paths)
                elif choice == "4":
                    run_full_test(paths)
                elif choice == "5":
                    show_latest_report(paths)
                elif choice == "6":
                    create_test_data(paths)
                elif choice == "7":
                    show_project_paths(paths)
                elif choice == "0":
                    print("👋 프로그램을 종료합니다.")
                    break
                else:
                    print("❌ 잘못된 선택입니다. 0-7 사이의 숫자를 입력하세요.")
                
                input("\n⏸️  아무 키나 누르면 메뉴로 돌아갑니다...")
                print("\n" * 2)  # 화면 정리
                
            except KeyboardInterrupt:
                print("\n\n👋 프로그램을 종료합니다.")
                break
            except Exception as e:
                print(f"❌ 오류 발생: {e}")
                input("⏸️  아무 키나 누르면 계속합니다...")
                
    except Exception as e:
        print(f"❌ 초기화 오류: {e}")
        print("현재 디렉토리 구조를 확인해주세요.")
        input("⏸️  아무 키나 누르면 종료합니다...")

if __name__ == "__main__":
    main()