package dev.mapofdev.config;

import dev.mapofdev.domain.JobCategory;
import dev.mapofdev.domain.Skill;
import dev.mapofdev.domain.Trend;
import dev.mapofdev.repository.JobCategoryRepository;
import dev.mapofdev.repository.SkillRepository;
import dev.mapofdev.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 데이터 초기화 설정 클래스
 *
 * 개발 환경에서 애플리케이션 시작 시
 * 트렌드(Trend), 기술 스택(Skill), 직군(JobCategory) 등의
 * 초기 데이터를 자동으로 생성하여 테스트 및 개발 환경을 구성하는
 * 스프링 부트 설정 클래스입니다.
 */
@Slf4j // 로깅 기능 자동 생성
@Configuration // 스프링 설정 클래스로 등록
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class DataInitializer {

  private final TrendRepository trendRepository; // 트렌드 데이터 관리 Repository
  private final SkillRepository skillRepository; // 기술 스택 데이터 관리 Repository
  private final JobCategoryRepository jobCategoryRepository; // 직군 데이터 관리 Repository

  /**
   * 초기 데이터 로드 실행 Bean
   *
   * 프로덕션 환경이 아닌 경우(dev, test 등)에만 실행되어
   * 개발 및 테스트용 더미 데이터를 자동으로 생성합니다.
   */
  @Bean
  @Profile("!prod") // 프로덕션 환경에서는 실행되지 않음
  CommandLineRunner init() {
    return args -> {
      log.info("=== 초기 데이터 설정 시작 ===");

      initTrends(); // 트렌드 데이터 초기화
      initSkills(); // 기술 스택 데이터 초기화
      initJobCategories(); // 직군 데이터 초기화

      log.info("=== 초기 데이터 설정 완료 ===");
    };
  }

  /**
   * 트렌드 데이터 초기화
   *
   * 백엔드, 프론트엔드, 데이터 엔지니어, DevOps, AI/ML 등
   * 주요 개발 분야별 채용 트렌드 데이터를 생성합니다.
   */
  private void initTrends() {
    log.info("트렌드 데이터 초기화");

    // 현재 월을 기준으로 통계 기간 설정 (yyyy-MM 형식)
    String currentPeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

    // 백엔드 개발 트렌드 생성
    trendRepository.save(Trend.builder()
      .category("백엔드 개발")
      .jobCount(2850) // 채용 공고 수
      .growthRate(12.5) // 성장률 (%)
      .description("Spring Boot와 MSA 아키텍처 수요 증가")
      .period(currentPeriod)
      .build());

    // 프론트엔드 개발 트렌드 생성
    trendRepository.save(Trend.builder()
      .category("프론트엔드 개발")
      .jobCount(2340)
      .growthRate(8.3)
      .description("React와 Next.js 기반 개발 증가")
      .period(currentPeriod)
      .build());

    // 데이터 엔지니어 트렌드 생성
    trendRepository.save(Trend.builder()
      .category("데이터 엔지니어")
      .jobCount(1560)
      .growthRate(23.7)
      .description("빅데이터와 실시간 처리 수요 급증")
      .period(currentPeriod)
      .build());

    // DevOps/SRE 트렌드 생성
    trendRepository.save(Trend.builder()
      .category("DevOps/SRE")
      .jobCount(980)
      .growthRate(18.2)
      .description("클라우드 네이티브와 자동화 수요")
      .period(currentPeriod)
      .build());

    // AI/ML 엔지니어 트렌드 생성
    trendRepository.save(Trend.builder()
      .category("AI/ML 엔지니어")
      .jobCount(1120)
      .growthRate(31.5)
      .description("생성형 AI와 LLM 관련 채용 폭증")
      .period(currentPeriod)
      .build());

    log.info("트렌드 {} 건 생성 완료", trendRepository.count());
  }

  /**
   * 기술 스택 데이터 초기화
   *
   * 프로그래밍 언어, 프레임워크, 데이터베이스, 클라우드 등
   * 카테고리별 주요 기술 스택과 채용 수요를 생성합니다.
   */
  private void initSkills() {
    log.info("기술 스택 데이터 초기화");

    // 프로그래밍 언어 카테고리
    skillRepository.save(Skill.builder()
      .name("Java")
      .category("Language")
      .demandCount(3420) // 수요 공고 수
      .ranking(1) // 순위
      .iconUrl("/icons/java.svg")
      .build());

    skillRepository.save(Skill.builder()
      .name("Python")
      .category("Language")
      .demandCount(3180)
      .ranking(2)
      .iconUrl("/icons/python.svg")
      .build());

    skillRepository.save(Skill.builder()
      .name("JavaScript")
      .category("Language")
      .demandCount(2890)
      .ranking(3)
      .iconUrl("/icons/javascript.svg")
      .build());

    // 프레임워크 카테고리
    skillRepository.save(Skill.builder()
      .name("Spring Boot")
      .category("Framework")
      .demandCount(2150)
      .ranking(1)
      .iconUrl("/icons/spring.svg")
      .build());

    skillRepository.save(Skill.builder()
      .name("React")
      .category("Framework")
      .demandCount(1980)
      .ranking(2)
      .iconUrl("/icons/react.svg")
      .build());

    skillRepository.save(Skill.builder()
      .name("Django")
      .category("Framework")
      .demandCount(890)
      .ranking(5)
      .iconUrl("/icons/django.svg")
      .build());

    // 데이터베이스 카테고리
    skillRepository.save(Skill.builder()
      .name("MySQL")
      .category("Database")
      .demandCount(2340)
      .ranking(1)
      .iconUrl("/icons/mysql.svg")
      .build());

    skillRepository.save(Skill.builder()
      .name("PostgreSQL")
      .category("Database")
      .demandCount(1560)
      .ranking(2)
      .iconUrl("/icons/postgresql.svg")
      .build());

    skillRepository.save(Skill.builder()
      .name("MongoDB")
      .category("Database")
      .demandCount(980)
      .ranking(3)
      .iconUrl("/icons/mongodb.svg")
      .build());

    // 클라우드 카테고리
    skillRepository.save(Skill.builder()
      .name("AWS")
      .category("Cloud")
      .demandCount(2680)
      .ranking(1)
      .iconUrl("/icons/aws.svg")
      .build());

    log.info("기술 스택 {} 건 생성 완료", skillRepository.count());
  }

  /**
   * 직군 데이터 초기화
   *
   * 백엔드, 프론트엔드, 풀스택, 데이터 엔지니어, DevOps 등
   * 주요 개발 직군별 채용 공고 수, 평균 연봉, 필요 기술 등을 생성합니다.
   */
  private void initJobCategories() {
    log.info("직군 데이터 초기화");

    // 백엔드 개발자 직군
    jobCategoryRepository.save(JobCategory.builder()
      .name("백엔드 개발자")
      .totalJobs(2850) // 전체 채용 공고 수
      .juniorJobs(580) // 신입 채용 공고 수
      .avgSalary(new BigDecimal("5200")) // 평균 연봉 (만원)
      .requiredSkills("Java,Spring,JPA,MySQL,Git") // 필요 기술 (쉼표 구분)
      .build());

    // 프론트엔드 개발자 직군
    jobCategoryRepository.save(JobCategory.builder()
      .name("프론트엔드 개발자")
      .totalJobs(2340)
      .juniorJobs(720)
      .avgSalary(new BigDecimal("4800"))
      .requiredSkills("JavaScript,React,HTML/CSS,Git,TypeScript")
      .build());

    // 풀스택 개발자 직군
    jobCategoryRepository.save(JobCategory.builder()
      .name("풀스택 개발자")
      .totalJobs(1680)
      .juniorJobs(120)
      .avgSalary(new BigDecimal("5500"))
      .requiredSkills("JavaScript,Java,React,Spring,Docker")
      .build());

    // 데이터 엔지니어 직군
    jobCategoryRepository.save(JobCategory.builder()
      .name("데이터 엔지니어")
      .totalJobs(1560)
      .juniorJobs(180)
      .avgSalary(new BigDecimal("5800"))
      .requiredSkills("Python,Spark,Kafka,Airflow,SQL")
      .build());

    // DevOps 엔지니어 직군
    jobCategoryRepository.save(JobCategory.builder()
      .name("DevOps 엔지니어")
      .totalJobs(980)
      .juniorJobs(60)
      .avgSalary(new BigDecimal("6200"))
      .requiredSkills("Docker,Kubernetes,Jenkins,AWS,Terraform")
      .build());

    log.info("직군 {} 건 생성 완료", jobCategoryRepository.count());
  }
}
