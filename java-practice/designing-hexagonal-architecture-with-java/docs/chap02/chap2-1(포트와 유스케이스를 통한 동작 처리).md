# 2. 포트와 유스케이스를 통한 동작 처리

도메인 헥사곤에 비즈니스 규칙을 정의하고 나면 사용자나 다른 애플리케이션에서 오는 데이터를<br/>
처리하는 방법을 고려하는 동시에 소프트웨어의 기능을 만들기 위해 이러한 규칙의 사용 방법에 대해 생각을 시작할 수 있다.<br/>

유용한 소프트웨어 기능을 제공하기 위해 시스템 데이터와 비즈니스 규칙을 조정하는 경우<br/>
**포트**와 **유스케이스**는 헥사고날 아키텍처에서 이러한 문제를 처리한다.<br/>
소프트웨어에서 지원하는 동작을 정의하기 위해 유스케이스를 사용한다.<br/>
유스케이스를 통합해 헥사고날 시스템 내의 통신 흐름을 설정하는 데 있어 입력 포트와 출력 포트의 역할이 중요하다.<br/>

소프트웨어가 갖는 목표를 달성하기 위해 헥사고날 시스템에서 반드시 해야 하는 모든 사항을<br/>
**포트**와 **유스케이스**를 사용해 조정할 수 있다.<br/>
**포트**와 **유스케이스**는 강력한 기능을 구성하기 위해 도메인 헥사곤과<br/>
애플리케이션 헥사곤의 요소들을 결합할 때 사용한다.<br/>

- 유스케이스를 통한 소프트웨어의 동작 표현
- 입력 포트를 갖는 유스케이스 구현
- 출력 포트를 이용한 외부 데이터 처리
- 애플리케이션 헥사곤을 통한 동작 자동화