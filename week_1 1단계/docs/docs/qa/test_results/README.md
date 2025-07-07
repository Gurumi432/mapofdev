# 테스트 결과 폴더

이 폴더에는 API 테스트 자동화 스크립트의 실행 결과가 저장됩니다.

## 📁 파일 구조

```
test_results/
├── README.md                           # 이 파일
├── test_results_YYYYMMDD_HHMMSS.json   # 타임스탬프별 JSON 결과
├── test_results_YYYYMMDD_HHMMSS.csv    # 타임스탬프별 CSV 결과  
├── test_report_YYYYMMDD_HHMMSS.md      # 타임스탬프별 리포트
├── latest_test_results.json            # 최신 JSON 결과
└── latest_test_report.md               # 최신 리포트
```

## 📋 파일 설명

### JSON 결과 파일
- 테스트 실행의 상세한 결과를 JSON 형태로 저장
- API 응답 데이터, 실행 시간, 오류 정보 등 모든 세부사항 포함
- 프로그래밍적 분석에 적합

### CSV 결과 파일
- 테스트 케이스와 결과를 표 형태로 저장
- Excel에서 열어서 분석하기 적합
- Pass/Fail 상태와 실제 결과를 한눈에 확인 가능

### 마크다운 리포트
- 사람이 읽기 쉬운 형태의 종합 보고서
- 통계, 카테고리별 결과, 실패 요약 포함
- GitHub 등에서 바로 확인 가능

## 🔍 결과 확인 방법

### 최신 결과 빠르게 확인
```bash
# 최신 리포트 확인
cat latest_test_report.md

# 최신 JSON 결과 확인  
cat latest_test_results.json | python -m json.tool
```

### 특정 날짜 결과 확인
```bash
# 특정 날짜의 리포트 확인
cat test_report_20250705_235500.md

# CSV 파일을 Excel로 열기
open test_results_20250705_235500.csv
```

## 📊 결과 분석 팁

1. **전체 성공률 확인**: 리포트 상단의 통계 섹션 참조
2. **카테고리별 분석**: 어떤 기능 영역에서 문제가 많은지 확인
3. **실패 원인 파악**: 실패한 테스트의 상세 결과를 통해 근본 원인 분석
4. **성능 모니터링**: 실행 시간을 통해 API 응답 속도 확인

---

**참고**: 이 폴더의 파일들은 자동으로 생성되므로 직접 편집하지 마세요.
