# 🗺️ mapofdev  
**"데이터로 커리어를 설계하다" – 당신만의 커리어 나침반**

---

## ✨ Intro

> “개발 2년 차, 아직도 내 길이 맞는지 모르겠습니다.”  
누군가는 백엔드에서 머신러닝으로, 누군가는 스타트업을 거쳐 Google로 갔습니다.  
**나는 어디로, 어떻게 가야 할까요?**

`mapofdev`는 이런 고민을 겪는 개발자들이 **'감'이 아닌 '데이터'로 진로를 설계할 수 있도록** 돕는 플랫폼입니다.  

- **GitHub 활동 데이터**
- **산업 채용 트렌드**
- **직군별 통계 데이터**  
를 기반으로, 개발자 커리어를 **정량적으로 분석하고 시각적으로 설계**해줍니다.

[🧠 Software Requirements Specification 🔍](https://www.notion.so/SRS-221bf97a4caf81fc98a7e02148c6d5cb)

[📘 Use Case Specification 👤](https://www.notion.so/Use-Case-Specification-221bf97a4caf813db155f6fafbd049ba?pvs=21)

[📊 Use Case Diagram](https://www.notion.so/Use-Case-Diagram-221bf97a4caf81d39d7cfbc42260a000?pvs=21)

[🔌 API 📡](https://www.notion.so/API-221bf97a4caf818f992fcedfc2933985?pvs=21)

[🗄️ DB 🧱](https://www.notion.so/221bf97a4caf818994f1c8517dd56f43?pvs=21)

[🧩 Architecture](https://www.notion.so/Architecture-221bf97a4caf812885c5f551301a17a9?pvs=21)

[📦 Class Diagram](https://www.notion.so/Class-Diagram-221bf97a4caf818795adc660002d8725?pvs=21)

[🔄 Sequence Diagram](https://www.notion.so/Sequence-Diagram-221bf97a4caf810680bedf6e31f4aee7?pvs=21)

[🧪 Test Case](https://www.notion.so/Test-Case-221bf97a4caf81abb383f654ceb1bf4d?pvs=21)

[🧭 UX **Scenario**](https://www.notion.so/UX-Scenario-221bf97a4caf818cba59e4781dd43c8c?pvs=21)

[📈 Data Visualization](https://www.notion.so/Data-Visualization-221bf97a4caf812d8523ed21dda1bab5?pvs=21)

---

## 🎯 프로젝트 목표

| 고민 | mapofdev의 해결 방식 |
|------|----------------------|
| 어떤 직군이 나에게 맞을까? | 📊 직군별 산업 통계 분석 |
| 요즘 뜨는 기술은 뭘까? | 🔍 채용 공고 트렌드 시각화 |
| 내 실력은 어느 정도일까? | 🧠 GitHub 활동 분석 |
| 뭘 공부해야 하지? | 🧭 목표 대비 학습 로드맵 추천 |
| 나와 비슷한 사람은 어떤 길을 갔을까? | 🤝 유사 사용자 진로 추론 |

---

## 🧭 전체 UX 흐름

1. **시장 데이터 탐색** → 분야별 채용 트렌드 및 직군별 성장률 파악
2. **GitHub 연동** → 활동량/리포지터리/스타 등 기반으로 기술 역량 분석 
3. **커리어 추천** → 유사 사용자 기반 경로 제안  
4. **학습 로드맵 제공** → 목표 진로 대비 부족 역량 분석 및 학습 순서 제공

---

## 🧪 사용 예시

### ▶️ 예시 1: 백엔드 신입 개발자 A
- GitHub 연동 결과 → 자바 기반 활동 + 알고리즘 위주 커밋
- 추천 진로 → "Spring 백엔드 → 데이터 플랫폼 진입"
- 부족 역량 → 테스트 코드, API 보안, CI/CD
- 로드맵 → "JWT 인증 학습 → Swagger 기반 문서화 → Jenkins 실습"

---

## ⚙️ 기술 아키텍처

```plaintext
사용자 → GitHub 연동 → 활동 데이터 수집
               ↓
     [Spring Boot 백엔드 API 서버]
               ↓
   PostgreSQL + Redis 캐시 구조
               ↓
    Python 분석 모듈 (KNN + NLP)
               ↓
       결과 시각화 (D3.js, Chart.js)
````

---

## 📌 기술 스택 요약

| 영역  | 기술                      | 설명                |
| --- | ----------------------- | ----------------- |
| 백엔드 | Spring Boot, JPA        | RESTful 도메인 기반 설계 |
| 분석  | Python, pandas, konlpy  | 키워드/감성/패턴 분석      |
| DB  | PostgreSQL, Redis       | 통계 캐싱 및 데이터 저장    |
| 인증  | GitHub OAuth, JWT       | 사용자 인증 및 보호       |
| 크롤링 | Jsoup, Selenium         | 정적·동적 채용 공고 수집    |
| 추천  | KNN + Cosine Similarity | 유사 사용자 기반 경로 제안   |
| 테스트 | JUnit, Mockito          | 도메인 유닛 테스트 구축     |
| 배포  | Nginx, Docker, EC2      | CI/CD 파이프라인 구성    |

---

## 📘 도메인 모델 예시

```plaintext
User
 ├─ githubProfile (1:1)
 ├─ careerPath (1:N)
 └─ analysisResult (1:1)

JobStat
 ├─ category (e.g. 백엔드, 데이터)
 └─ keywords (e.g. Spring, Kafka)

RecommendationResult
 ├─ targetUser
 └─ similarUsers[]
```

---

## 🧮 추천 알고리즘 개요

* 활동 기반 벡터 생성:
  `[커밋 수, 기술 태그, 인기 리포지터리 수, 언어 분포...]`
* Cosine 유사도 기반 KNN 분석 (K=5)
* 유사 사용자의 진로/전직 이력을 집계하여 확률 분포 생성

```plaintext
예시 결과:
- 백엔드 → 데이터 엔지니어 (58%)
- 백엔드 → SRE (32%)
- 백엔드 → PM (10%)
```

---

## 🧪 주요 예외 처리 및 문제 해결

| 상황            | 처리 방식                                         |
| ------------- | --------------------------------------------- |
| GitHub API 오류 | fallback → 최근 분석 결과로 대체                       |
| 크롤링 실패        | Redis 캐시된 데이터 제공                              |
| 분석 실패         | 상세 에러 로그 노출 (분석 모듈 단위)                        |
| 단위 테스트        | 도메인 단위별 (`User`, `CareerPath`, etc) 테스트 구성 완료 |

---

## 🔎 핵심 구현 로직 예시

### KNN 추천 서비스 흐름

```java
public RecommendationResult recommend(User user) {
    Vector userVector = vectorize(user.getGitHubProfile());
    List<User> candidates = userRepository.findAll();
    return knnService.findMostSimilar(userVector, candidates);
}
```

### GitHub 분석 벡터화 예시

```python
def vectorize(profile):
    return [
        len(profile.repos),
        profile.commit_count,
        profile.primary_language_score(),
        profile.starred_repo_count
    ]
```

---

## 📊 실사용 결과

| 항목              | 수치        |
| --------------- | --------- |
| MVP 테스트 사용자 수   | 37명       |
| 진로 추천 만족도       | 4.3 / 5.0 |
| 역량 분석 피드백 긍정률   | 91%       |
| 추천 경로 실제 전직 유사도 | 85%       |

---

## 🔁 리팩터링 계획

| 항목       | 개선 방향             | 목적              |
| -------- | ----------------- | --------------- |
| 분석 파이프라인 | 분석 서버 분리 + MQ 도입  | 확장성 향상          |
| NLP 성능   | Mecab 도입          | 감성 분석 정밀도 개선    |
| 추천 로직    | 실제 이직 사례 학습 기반 강화 | 현실 반영 강화        |
| UI 렌더링   | SSR 구조 + SEO 대응   | 퍼포먼스 및 검색 노출 개선 |

---

## 📂 저장소 / 데모

* GitHub: [https://github.com/yourname/mapofdev](https://github.com/yourname/mapofdev)
* 데모: [https://mapofdev.site](https://mapofdev.site)
* Swagger: [https://mapofdev.site/swagger-ui.html](https://mapofdev.site/swagger-ui.html)

---

## ✅ 마무리

저는 단순히 기술을 사용하는 개발자가 아니라,
**문제를 정의하고, 데이터를 수집하고, 해석하고, 기술로 해결책을 설계하는 개발자**입니다.

이 프로젝트는 그런 저의 기획력과 기술 역량을 입증하기 위한 첫 번째 결과물입니다.
실제 사용자 데이터를 기반으로, 문제를 정의하고, 기술을 통해 실질적인 인사이트를 도출해냈습니다.

앞으로도 현실 문제를 데이터와 기술로 해결하는 백엔드 개발자가 되겠습니다.
감사합니다.

---
