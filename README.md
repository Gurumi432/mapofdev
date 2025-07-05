**"데이터로 커리어를 설계하다" – 당신만의 커리어 나침반**

> ⚠️ 현재 상태: 상세 설계 완료, 프로토타입 개발 중
>

---

## 🚀 빠른 시작

### 실행 방법

```
git clone <https://github.com/jinnamm/mapofdev.git>
cd mapofdev
gradlew bootRun
```

---

## ✨ Intro

> "개발자가 되고 싶은데, 어디서부터 시작해야 할지 모르겠습니다."
>
>
> 💭 "부트캠프? 독학? 전공자들과 경쟁이 될까? 어떤 길이 맞을까?"
>
> 💭 "백엔드? 프론트? 데이터? 너무 많은 분야 중에 뭐가 나랑 맞을까?"
>
> 💭 "요즘 채용공고 보면 다들 경력직만 뽑던데... 신입은 어디서 뽑나?"
>
> **"개발 2년 차, 아직도 내 길이 맞는지 모르겠습니다."**
>
> 💭 "동기는 이직하고 연봉이 올랐는데, 나는 아직 제자리인 것 같고..."
>
> 💭 "Spring만 해왔는데 요즘은 Node.js가 대세라던데, 이 기술 계속해도 될까?"
>
> 💭 "SI만 하다가 서비스 회사로 갈 수 있을까? 백엔드에서 데이터 쪽으로 전향할 수 있을까?"
>

`mapofdev`는 이런 고민을 겪는 예비 개발자와 현직 개발자들이 **'감'이 아닌 '데이터'로 진로를 설계할 수 있도록** 돕는 플랫폼입니다.

- **GitHub 활동 데이터**로 현재 역량 분석
- **산업 채용 트렌드**로 시장 수요 파악
- **유사 개발자 경로**로 미래 방향 제시

---

## 🎯 프로젝트 목표

### 핵심 가치 제안

**"시장을 먼저 보고, 나를 분석하고, 미래를 설계한다"**

| 고민 | mapofdev의 해결 방식 | 접근성 |
| --- | --- | --- |
| 어떤 직군이 뜨고 있을까? | 📊 실시간 채용 트렌드 대시보드 | 🌐 비로그인 |
| 요즘 뜨는 기술은 뭘까? | 🔍 기술 스택 인기도 시각화 | 🌐 비로그인 |
| 각 분야 연봉은 어느정도일까? | 💰 직군별 연봉 통계 | 🌐 비로그인 |
| 내 실력은 어느 정도일까? | 🧠 GitHub 활동 분석 | 🔐 로그인 필요 |
| 뭘 공부해야 하지? | 🧭 맞춤형 학습 로드맵 | 🔐 로그인 필요 |
| 나와 비슷한 사람은 어떤 길을? | 🤝 유사 사용자 진로 추천 | 🔐 로그인 필요 |

---

## 💪 프로젝트 강점

### 📐 체계적인 설계

- **완전한 문서화**: SRS부터 Test Case까지 전체 개발 문서 완비
- **표준 방법론**: UML 다이어그램, RESTful API 설계 원칙 준수
- **확장 가능한 구조**: MSA 전환 고려한 모듈화 설계

### 🎯 실제 문제 해결

- **타겟 명확**: 진로 고민이 있는 주니어 개발자
- **데이터 기반**: 추측이 아닌 실제 데이터로 의사결정 지원
- **검증 가능**: 추천 결과를 실제 이직 경로와 비교 가능

### 🛠️ 기술적 도전

- **대용량 데이터 처리**: GitHub API + 채용 공고 크롤링
- **머신러닝 적용**: KNN 알고리즘으로 유사도 계산
- **실시간 분석**: Redis 캐싱으로 빠른 응답 제공

---

## 🧭 전체 시스템 흐름

```
1. 시장 탐색 단계 (비로그인)
   └─ IT 산업 채용 트렌드 대시보드
   └─ 기술 스택 인기도 차트
   └─ 직군별 성장률 시각화
   └─ "나의 위치는?" CTA 버튼

2. 개인 분석 단계 (로그인 필요)
   └─ GitHub OAuth 로그인
   └─ 활동 데이터 자동 수집
   └─ 기술 역량 점수화
   └─ 시장 내 포지셔닝 표시

3. 추천 생성 단계
   └─ 유사 사용자 탐색 (KNN)
   └─ 진로 경로 분석
   └─ 확률 기반 추천

4. 로드맵 제공 단계
   └─ 현재 vs 목표 갭 분석
   └─ 학습 우선순위 결정
   └─ 단계별 가이드 생성

```

---

## 🧪 주요 시나리오

### 시나리오 1: 백엔드 개발자의 진로 탐색

```
상황: 2년차 Spring 개발자, 데이터 엔지니어링에 관심

1. 비로그인 상태로 시장 탐색
   - 메인 페이지에서 "데이터 엔지니어" 카테고리 선택
   - 실시간 채용 트렌드 확인: 전년 대비 35% 증가
   - 필수 기술 워드클라우드: Spark, Kafka, Airflow 상위
   - 연봉 비교 차트: 백엔드 대비 15% 높음
   - "내 실력으로 전직 가능할까?" 버튼 클릭

2. GitHub 로그인 유도
   - "GitHub으로 3초만에 내 실력 분석하기" 팝업
   - OAuth 로그인 진행

3. 개인 분석 결과
   - 현재 포지션: 백엔드 중급 (상위 30%)
   - 주요 언어: Java (78%), Python (15%)
   - 부족한 스킬: 빅데이터 처리, 스트리밍

4. 맞춤 추천 제공
   - 유사 개발자 10명 중 6명이 데이터 엔지니어로 전직
   - 평균 준비 기간: 6-8개월
   - 성공 확률: 72%
   - 추천 학습 경로 제시

```

### 시나리오 3: 취준생의 진로 결정

```
상황: 컴공과 4학년, 졸업 전 어떤 분야로 준비할지 고민

1. 비로그인 상태로 전체 시장 둘러보기
   - "신입 개발자" 필터로 채용 현황 확인
   - 신입 채용 많은 분야 TOP 5: 웹 백엔드, 프론트엔드, QA
   - 분야별 요구 기술: Java/Spring, React, Python
   - "우리 학교 졸업생들은 어디로 갔을까?" 궁금증

2. 관심 분야 깊이 탐색
   - "백엔드" 카테고리 상세 진입
   - 신입 필수 기술: Java, Spring Boot, MySQL
   - 우대 사항: AWS, Docker, JPA
   - 평균 코딩테스트 난이도: 백준 실버~골드

3. GitHub 연동으로 현재 수준 확인
   - 학교 프로젝트 3개, 알고리즘 문제 풀이 기록
   - 현재 수준: "신입 백엔드 하위 40%"
   - 부족한 부분: 실무 프로젝트 경험, 테스트 코드
   - 추천: "토이 프로젝트 + 오픈소스 기여로 경쟁력 확보"

```

---

## ⚙️ 기술 아키텍처

### 시스템 구성도

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   Frontend      │     │   Backend API   │     │  Analysis       │
│   (React)       │────▶│  (Spring Boot)  │────▶│  (Python)       │
└─────────────────┘     └─────────────────┘     └─────────────────┘
                               │                          │
                               ▼                          ▼
                        ┌─────────────┐           ┌─────────────┐
                        │ PostgreSQL  │           │    Redis    │
                        │   (Main)    │           │   (Cache)   │
                        └─────────────┘           └─────────────┘

```

### 데이터 플로우

```
GitHub API ─┐
            ├─▶ Data Pipeline ─▶ Analysis Engine ─▶ Recommendation
Job Sites  ─┘                          │
                                       ▼
                                 Visualization

```

---

## 📌 기술 스택 상세

### Backend

| 기술 | 용도 | 선택 이유 |
| --- | --- | --- |
| Spring Boot 3.0 | 메인 프레임워크 | 안정성과 생산성 |
| JPA/Hibernate | ORM | 복잡한 관계형 데이터 처리 |
| Spring Security | 보안 | OAuth 2.0 통합 용이 |
| JWT | 인증 토큰 | Stateless 아키텍처 |

### Data & Analysis

| 기술 | 용도 | 선택 이유 |
| --- | --- | --- |
| Python 3.9 | 데이터 분석 | 풍부한 ML 라이브러리 |
| pandas | 데이터 처리 | 효율적인 데이터 조작 |
| scikit-learn | ML 알고리즘 | KNN 구현 |
| konlpy | 한국어 NLP | 채용공고 키워드 추출 |

### Infrastructure

| 기술 | 용도 | 선택 이유 |
| --- | --- | --- |
| PostgreSQL | 메인 DB | JSON 지원, 확장성 |
| Redis | 캐싱 | API 제한 대응 |
| Docker | 컨테이너화 | 일관된 배포 환경 |
| AWS EC2 | 서버 호스팅 | 유연한 스케일링 |

---

## 📘 핵심 도메인 모델

```java
@Entity
public class User {
    private Long id;
    private String githubId;
    private LocalDateTime joinedAt;

    @OneToOne(cascade = CascadeType.ALL)
    private GitHubProfile githubProfile;

    @OneToMany(mappedBy = "user")
    private List<CareerPath> careerPaths;

    @OneToOne
    private AnalysisResult latestAnalysis;
}

@Entity
public class GitHubProfile {
    private String username;
    private Integer publicRepos;
    private Integer followers;
    private Map<String, Integer> languageStats;
    private List<Repository> topRepositories;
}

@Entity
public class CareerPath {
    private String fromRole;
    private String toRole;
    private Float probability;
    private Integer avgMonths;
}

@Entity
public class JobMarketTrend {
    private String category;
    private LocalDate date;
    private Integer postingCount;
    private List<String> topKeywords;
    private Float growthRate;
}

```

---

## 🧮 추천 알고리즘 설계

### 1. 특성 벡터 생성

```python
def create_feature_vector(user_profile):
    return np.array([
        user_profile.commit_count / 365,           # 일평균 커밋
        user_profile.repo_count,                   # 저장소 수
        user_profile.star_count,                   # 받은 스타 수
        user_profile.primary_language_ratio,       # 주 언어 비율
        user_profile.framework_diversity_score,    # 기술 다양성
        user_profile.contribution_consistency      # 기여 일관성
    ])

```

### 2. KNN 유사도 계산

```python
def find_similar_users(target_vector, all_users, k=10):
    distances = []
    for user in all_users:
        user_vector = create_feature_vector(user)
        distance = cosine_similarity(target_vector, user_vector)
        distances.append((user, distance))

    return sorted(distances, key=lambda x: x[1])[:k]

```

### 3. 경로 확률 계산

```python
def calculate_path_probability(similar_users):
    path_counts = defaultdict(int)
    for user in similar_users:
        if user.career_transition:
            path_counts[user.career_transition] += 1

    total = len(similar_users)
    return {path: count/total for path, count in path_counts.items()}

```

---

## 🔌 주요 API 설계

### 주요 페이지 구성

### 1. 메인 페이지 (비로그인)

```
GET /

주요 컴포넌트:
- IT 채용시장 실시간 대시보드
- 인기 기술 스택 트렌드
- 직군별 성장률 차트
- "GitHub 연동하고 내 위치 확인하기" CTA

```

### 2. 직군별 상세 페이지 (비로그인)

```
GET /careers/{category}

제공 정보:
- 해당 직군 채용 트렌드 (6개월)
- 필수/우대 기술 분포
- 연봉 범위 및 분포
- 주니어/시니어 비율

```

### 3. 개인 분석 API (로그인 필요)

```
POST /api/v1/analysis/github
Authorization: Bearer {jwt_token}

Response:
{
    "userId": 12345,
    "analysisId": "uuid",
    "profile": {
        "mainLanguages": ["Java", "Python"],
        "techStack": ["Spring", "JPA", "Docker"],
        "activityScore": 85,
        "consistencyScore": 92
    },
    "timestamp": "2024-01-01T00:00:00Z"
}

```

### 2. 진로 추천

```
GET /api/v1/recommendations/{userId}

Response:
{
    "recommendations": [
        {
            "role": "Data Engineer",
            "probability": 0.65,
            "avgTransitionMonths": 6,
            "requiredSkills": ["Apache Spark", "Kafka"],
            "similarUsersCount": 8
        }
    ]
}

```

### 3. 시장 트렌드

```
GET /api/v1/trends?category=backend&period=6m

Response:
{
    "trends": [
        {
            "month": "2024-01",
            "postingCount": 1250,
            "topKeywords": ["Spring", "MSA", "Kubernetes"],
            "growthRate": 0.15
        }
    ]
}

```

---

## 🧪 테스트 전략

### 단위 테스트

```java
@Test
void testGitHubProfileAnalysis() {
    // Given
    GitHubProfile profile = createMockProfile();

    // When
    AnalysisResult result = analyzer.analyze(profile);

    // Then
    assertThat(result.getMainLanguage()).isEqualTo("Java");
    assertThat(result.getActivityScore()).isGreaterThan(80);
}

```

### 통합 테스트

- API 엔드포인트 테스트
- 데이터 파이프라인 테스트
- 추천 알고리즘 정확도 테스트

### 성능 테스트

- 목표: 1000명 동시 사용자 처리
- API 응답시간: 평균 200ms 이하
- 추천 생성시간: 3초 이내

---

## 📊 예상 성과 지표

### 목표 지표

| 지표 | 목표값 | 측정 방법 |
| --- | --- | --- |
| 추천 정확도 | 80% 이상 | 실제 전직 경로와 비교 |
| 사용자 만족도 | 4.0/5.0 이상 | 설문조사 |
| 활성 사용자 | 월 1,000명 | Google Analytics |
| API 응답속도 | 200ms 이하 | APM 모니터링 |

### MVP 검증 계획

1. **퍼블릭 데이터 수집**: 채용 사이트 크롤링으로 시장 데이터 구축
2. **알파 테스트**: 비로그인 대시보드 사용성 테스트 (20명)
3. **베타 테스트**: GitHub 연동 및 개인 분석 기능 테스트 (30명)
4. **공개 베타**: 전체 기능 오픈 및 피드백 수집 (100명)

---

## 🚧 구현 로드맵

### Phase 1: 기반 구축 (2개월)

- [x]  요구사항 분석 및 설계
- [x]  프로젝트 문서화
- [ ]  Spring Boot 프로젝트 설정
- [ ]  기본 도메인 모델 구현
- [ ]  GitHub OAuth 연동

### Phase 2: 핵심 기능 (2개월)

- [ ]  데이터 수집 파이프라인
- [ ]  KNN 추천 엔진 구현
- [ ]  RESTful API 개발
- [ ]  기본 UI 프로토타입

### Phase 3: 고도화 (1개월)

- [ ]  Redis 캐싱 레이어
- [ ]  성능 최적화
- [ ]  모니터링 시스템
- [ ]  배포 자동화

### Phase 4: 출시 준비 (1개월)

- [ ]  사용자 테스트
- [ ]  버그 수정 및 안정화
- [ ]  문서 정리
- [ ]  마케팅 준비

---

## 🔍 기술적 도전과 해결 방안

### 1. GitHub API 제한

- **문제**: 시간당 5,000회 요청 제한
- **해결**: Redis 캐싱 + 배치 처리

### 2. 크롤링 안정성

- **문제**: 사이트 구조 변경 시 오류
- **해결**: 다중 파서 + 실패 알림 시스템

### 3. 추천 정확도

- **문제**: 적은 데이터로 정확한 추천
- **해결**: 특성 엔지니어링 + 하이브리드 추천

### 4. 확장성

- **문제**: 사용자 증가시 성능 저하
- **해결**: 수평 확장 가능한 아키텍처

---

## 📚 프로젝트 문서

### 📋 설계 문서

- [🧠 Software Requirements Specification](https://www.notion.so/SRS)
- [📘 Use Case Specification](https://www.notion.so/Use-Case)
- [🧩 Architecture Design](https://www.notion.so/Architecture)

### 📊 다이어그램

- [📈 Use Case Diagram](https://www.notion.so/Use-Case-Diagram)
- [🗄️ ERD & DB Schema](https://www.notion.so/ERD-DB-Schema)
- [📦 Class Diagram](https://www.notion.so/Class-Diagram)
- [🔄 Sequence Diagram](https://www.notion.so/Sequence-Diagram)

### 🧪 품질 보증

- [✅ Test Case Documentation](https://www.notion.so/Test-Case)
- [🎯 UX Scenario](https://www.notion.so/UX-Scenario)
- [📊 Data Visualization Plan](https://www.notion.so/Data-Visualization)

### 🔧 개발 문서

- [🔌 API Specification](https://www.notion.so/API-Specification)
- [📝 Coding Convention](https://www.notion.so/Coding-Convention)
- [🚀 Deployment Guide](https://www.notion.so/Deployment-Guide)

---

## 📂 저장소 구조

```
mapofdev/
├── backend/
│   ├── src/main/java/com/mapofdev/
│   │   ├── domain/          # 도메인 모델
│   │   ├── repository/      # 데이터 접근
│   │   ├── service/         # 비즈니스 로직
│   │   ├── controller/      # REST API
│   │   └── config/          # 설정
│   └── src/test/           # 테스트
├── analysis/
│   ├── data_collector/     # 크롤러
│   ├── ml_engine/          # 추천 엔진
│   └── nlp_processor/      # 텍스트 분석
├── frontend/
│   ├── components/         # React 컴포넌트
│   ├── pages/             # 페이지
│   └── services/          # API 클라이언트
└── docs/                  # 문서

```

---

## 🔗 관련 링크

- **GitHub**: https://github.com/jinnamm/mapofdev
- **프로젝트 문서**: [Notion 워크스페이스](https://www.notion.so/mapofdev)
- **이슈 트래커**: [GitHub Issues](https://github.com/jinnamm/mapofdev/issues)
- **개발 일지**: [개발 블로그](https://blog.jinnam.com/mapofdev)

---

## ✅ 마무리

저는 단순히 기술을 사용하는 개발자가 아니라,

**문제를 정의하고, 데이터를 수집하고, 해석하고, 기술로 해결책을 설계하는 개발자**입니다.

이 프로젝트는 그런 저의 체계적인 사고와 문제 해결 능력을 보여주는 포트폴리오입니다.

아직 구현 단계이지만, 철저한 설계와 명확한 목표를 바탕으로 실제 가치를 만들어낼 것입니다.

**"좋은 설계는 성공적인 구현의 절반이다"** - 이 믿음으로 프로젝트를 진행하고 있습니다.

감사합니다.

[📂 1. Project Management](https://www.notion.so/1-Project-Management-225bf97a4caf80c29cf0d56775a935c0?pvs=21)

[📂 2. Requirements](https://www.notion.so/2-Requirements-225bf97a4caf80db8feddf3e235bddb7?pvs=21)

[📂 3. Analysis & Design](https://www.notion.so/3-Analysis-Design-225bf97a4caf80f591e4e3074f64a48f?pvs=21)

[📂 4. Interfaces](https://www.notion.so/4-Interfaces-225bf97a4caf80489bdac8f0b37cccd6?pvs=21)

[📂 5. QA & Release](https://www.notion.so/5-QA-Release-221bf97a4caf81abb383f654ceb1bf4d?pvs=21)
