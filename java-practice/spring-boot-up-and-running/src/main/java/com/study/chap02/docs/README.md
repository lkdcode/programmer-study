# chap02 - 도구 선택 및 시작

## 메이븐 vs 그레이들

## 자바 vs 코틀린

## 스프링 부트 버전

아래와 같은 극히 일부 예외나 임시 상황을 제외한다면,<br/>
현재 버전의 스프링 부트를 사용해야 한다.<br/>

- 스프링 부트 이전 버전을 실행 중이지만 현재 버전의 앱을 실행하기 위해 애플리케이션을 업그레이드, 테스트, 배포 중인 경우
- 스프링 부트 이전 버전을 실행 중인데 현재 버전 스프링 부트에 공식 보고된 충돌이나 버그가 있고, 문제가 있는 스프링 부트나 의존성이 업데이트될 때까지 기다리라는 공식 가이드가 있는 경우
- 스냅숏, 마일스톤 또는 출시 후보인 출시 전 버전(GA)의 기능을 사용해야만 하는 경우
- 아직 GA(프로덕션용)로 출시되지 않은 코드에 내재된 위험을 기꺼이 받아들이는 경우

## 자바 버전 관리 툴

[SDKMAN!](http://taewan.kim/oci_docs/98_misc_tips/tools/management_of_java_tools_with_sdkman/)

## 스프링 부트 CLI 설치

```bash
# 다양한 패키지 보기
$ sdk list

# 스프링 부트 버전 확인
$ sdk list springboot

# 최신 버전 설치
# $ sdk install <tool> <버전명>
$ sdk install springboot

# 스프링 부트 CLI를 설치한 후
# 스프링 이니셜라이저로 만든 프로젝트와 동일한 것을 생성
$ spring init

# `domo.zip` 을 `demo` 라는 디렉터리에 압축 해제
$ unzip demo.zip -d demo

# 기본값 설정을 명시적으로 나타내기
$ spring init -a demo -l java --build maven demo
# -a demo(또는 --artifactId demo) : 프로젝트의 아티팩트 ID 설정
# -l java(또는 --language java) : 프로젝트의 기본 언어로 자바, 코틀린, 그루비 지정 가능
# --build : 빌드 시스템 인수의 플래그 값. 유효한 값은 maven or gradle
# -x demo : 이니셜라이저로 만든 프로젝트의 .zip 파일을 `demo` 디렉터리에 압축 해제

# 스프링 CLI 명령어 옵션 확인
$ spring help init
```

## 스프링 부트

- 프로젝트 생성부터 개발과 유지보수에 용이한 의존성 관리 간소화
- 작업하려는 도메인의 보일러플레이트 코드를 극적으로 줄이거나 없애주는 자동 설정
- 패키징과 배포를 간편하게 만들어주는 배포 간소화