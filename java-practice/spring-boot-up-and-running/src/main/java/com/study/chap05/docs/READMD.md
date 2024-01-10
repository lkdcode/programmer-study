# chap05 - 애플리케이션 설정과 검사

디버깅은 개발에서 매우 중요하며 시간을 엄청나게 절약하게 해줌.<br/>
코드 디버깅은 애플리케이션 내 동작을 구축, 식별, 분리하는 한 단계에 불과<br/>
동적이고 분산된 애플리케이션이 많아지면 종종 다음 작업을 수행해야 한다.<br/>

- 애플리케이션의 동적 설정과 재설정
- 현재 설정과 출처의 확인과 결정
- 애플리케이션 환경과 헬스 지표의 검사와 모니터링
- 실행 중인 애플리케이션의 로깅 수준을 일시적으로 조정해 오류 원인 식별

스프링 부트 액추에이터로 애플리케이션 환경 설정을 유연하게 동적으로 생성, 식별, 수정 가능<br/>

## 애플리케이션 설정

- Spring Boot Developer Tools(devtools) 활성 시 $HOME/.config/spring-boot 디렉터리 내 전역 환경 설정
- 테스트의 @TestPropertySource 어노테이션
- 애플리케이션 슬라이스 테스트를 위해 테스트에서 사용되는 @SpringBootTest와 다양한 테스트 어노테이션의 설정 속성<sup>properties attribute</sup>
- 명령 줄 인수<sup>Command line arguments</sup>
- SPRING_APPLICATION_JSON 속성 (환경 변수 또는 시스템 속성에 포함된 인라인<sup>inline</sup>)
- ServletConfig 초기 매개변수
- ServletContext 초기 매개변수
- java: comp/env의 JNDI 속성
- 자바 시스템 속성(System.getProperties())
- OS 환경 변수
- random.* 내에서만 속성을 가지는 RandomValuePropertySource
- 패키징된 애플리케이션 jar 밖에 있는 프로필별 애플리케이션 속성(application -{profile}.properties 파일 또는 YAML 파일)
- 패키징된 애플리케이션 jar 안에 있는 프로필별 애플리케이션 속성(application -{profile}.properties 파일 또는 YAML 파일)
- 패키징된 애플리케이션 jar 밖에 있는 애플리케이션 속성(application.properties 파일 또는 YAML 파일)
- 패키징된 애플리케이션 jar 안에 있는 애플리케이션 속성(application.properties 파일 또는 YAML 파일)
- @Configuration 클래스의 @PropertySource 어노테이션 
  - 주의: 이 속성은 애플리케이션 컨텍스트가 새로고침될 때까지 실행 환경에 반영되지 않습니다.
  - logging.* 와 spring.main.* 등에서는 새로고침한 후에야 속성이 반영되므로
  - 애플리케이션 실행 후에는 설정하기에는 너무 늦습니다.
- SpringApplication.setDefaultProperties로 설정되는 기본 속성

## @Value, @ConfigurationProperties

## 잠재적 서드 파티 옵션 <sup>@ConfigurationProperties</sup>

## 자동 설정 리포트

애플리케이션 디버깅 모드

```bash
# --debug 옵션으로 애플리케이션의 jar 파일 실행
$ java -jar bootapplication.jar --debug

# JVM 매개변수로 애플리케이션의 jar 파일 실행
$ java -Ddebug=true -jar bootapplication.jar

# 애플리케의션의 application.propeties 파일에 debug=true 추가
# 셸에서 export DEBUG=true를 실행하거나 윈도우 환경에서 export DEBUG=true를 추가한 다음
$ java -jar bootapplication.jar 
```

확인 사항들

- 애플리케이션 의존성을 가진 JPA 와 H2
- SQL 데이터 소스와 함께 작동하는 JPA
- 내장 데이터베이스 H2
- 임베디드 SQL 데이터 소스를 지원하는 클래스

## 액추에이터

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
}
```

```json
{
    "_links": {
        "self": {
            "href": "http://localhost:8181/actuator",
            "templated": false
        },
        "beans": {
            "href": "http://localhost:8181/actuator/beans",
            "templated": false
        },
        "caches-cache": {
            "href": "http://localhost:8181/actuator/caches/{cache}",
            "templated": true
        },
        "caches": {
            "href": "http://localhost:8181/actuator/caches",
            "templated": false
        },
        "health": {
            "href": "http://localhost:8181/actuator/health",
            "templated": false
        },
        "health-path": {
            "href": "http://localhost:8181/actuator/health/{*path}",
            "templated": true
        },
        "info": {
            "href": "http://localhost:8181/actuator/info",
            "templated": false
        },
        "conditions": {
            "href": "http://localhost:8181/actuator/conditions",
            "templated": false
        },
        "configprops": {
            "href": "http://localhost:8181/actuator/configprops",
            "templated": false
        },
        "configprops-prefix": {
            "href": "http://localhost:8181/actuator/configprops/{prefix}",
            "templated": true
        },
        "env": {
            "href": "http://localhost:8181/actuator/env",
            "templated": false
        },
        "env-toMatch": {
            "href": "http://localhost:8181/actuator/env/{toMatch}",
            "templated": true
        },
        "loggers": {
            "href": "http://localhost:8181/actuator/loggers",
            "templated": false
        },
        "loggers-name": {
            "href": "http://localhost:8181/actuator/loggers/{name}",
            "templated": true
        },
        "heapdump": {
            "href": "http://localhost:8181/actuator/heapdump",
            "templated": false
        },
        "threaddump": {
            "href": "http://localhost:8181/actuator/threaddump",
            "templated": false
        },
        "metrics-requiredMetricName": {
            "href": "http://localhost:8181/actuator/metrics/{requiredMetricName}",
            "templated": true
        },
        "metrics": {
            "href": "http://localhost:8181/actuator/metrics",
            "templated": false
        },
        "scheduledtasks": {
            "href": "http://localhost:8181/actuator/scheduledtasks",
            "templated": false
        },
        "mappings": {
            "href": "http://localhost:8181/actuator/mappings",
            "templated": false
        }
    }
}
```

- /actuator/beans : 애플리케이션에서 생성한 모든 스프링 빈
- /actuator/conditions : 스프링 빈의 생성 조건이 충족됐는지 여부
- /actuator/configprops : 애플리케이션에서 액세스할 수 있는 모든 Environment 속성
- /actuator/env : 애플리케이션이 작동하는 환경의 무수한 측면 확인, 개별 configprop 값의 출처 확인에 특히 유용
- /actuator/health : health 정보 (설정에 따라 기본 또는 확장)
- /actuator/heapdump : 트러블 슈팅과 분석을 위해 힙 덤프<sup>heap dump</sup> 시작
- /actuator/loggers : 모든 컴포넌트의 로깅 수준
- /actuator/mappings : 모든 엔드포인트 매핑과 세부 지원 정보
- /actuator/metrics : 애플리케이션에서 현재 캡처 중인 매트릭스
- /actuator/threaddump : 트러블 슈팅과 분석을 위해 스레드 덤프<sup>thread dump</sup>시작

## 액추에이터 열기

```yaml
management:
  endpoint:
    health:
      show-details: always
# http://localhost:8181/actuator/health
```

## 마치며

개발자는 프로덕션 애플리케이션에서 나타나는 동작을 설정, 식별, 분리해주는 유용한 도구가 필요하다.<br/>
분산된 동적 애플리케이션이 많을수록 종종 다음 작업을 수행해야 한다.<br/>

- 애플리케이션을 동적으로 설정 및 재설정
- 현재 설정과 해당 출처를 결정/확인
- 애플리케이션 환경과 health 지표 검사. 모니터링
- 실시간으로 애플리케이션의 로깅 수준을 일시적으로 조정해 근본원인 식별