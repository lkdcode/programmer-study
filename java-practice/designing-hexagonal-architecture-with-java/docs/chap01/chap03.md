# 도메인 헥사곤으로 비즈니스 규칙 감싸기

도메인 헥사곤은 가장 내부에 있는 헥사곤이므로 그 위에 있는 <u>어떤 헥사곤에도 의존하지 않는다.</u></br>
또한 다른 모든 헥사곤이 도메인 헥사곤에 의존하는 방식으로 다른 헥사곤들의 오퍼레이션을 수행하게 만든다.</br>
이러한 방식은 다른 헥사곤보다 도메인 헥사곤에 훨씬 높은 수준의 책임과 관련성을 부과한다.</br>

우리가 해결하고자 하는 문제를 가장 잘 표현하고 그룹화하는 <u>모든 비즈니스 규칙과 데이터</u>가 해당 도메인 상에 있기 때문에 이러한 방식을 사용한다.</br>

- 엔티티를 활용한 문제 영역 모델링
- 값 객체를 통한 서술성(descriptiveness) 향상
- 애그리게잇(aggregate)을 통한 일관성 보장
- 도메인 서비스 활용
- 정책(Policy) 및 명세(Specification) 패턴을 활용한 비즈니스 규칙 처리
- POJO(Plain Old Java Object)를 이용한 비즈니스 규칙 정의

## 엔티티를 활용한 문제 영역 모델링

DDD에서는 코드를 작성하기 전에 개발자와 비즈니스를 깊게 이해하는 도메인 전문가 사이에서 많은 논의가 있어야 한다.</br>
브레인 스토밍에 기반한 지식 크런칭(knowledge crunching) 과정을 통해 귀중한 정보를 제공한다.</br>

> 지식 크런칭(knowledge crunching): 지식 고속 처리를 의미하며, 개발자와 도메인 전문가로 이루어진 팀의 협업 활동으로 주로 개발자가 주도한다.</br>
 
해당 지식은 보편 언어(Ubiquitous Language)를 통해 통합된다.</br>
프로젝트와 관련된 모든 사람 사이에서 공용어(lingua franca) 역할을 하고 문서, 일상 대화, 코드에도 존재하게 된다.</br>

엔티티를 다룰 때는 코드를 읽는 것만으로 비즈니스에 대해 얼마나 많이 배울 수 있는지 항상 유념해야 한다.</br>

엔티티를 엔티티로 간주하려면, 엔티티가 식별자를 가져야 한다.</br>

### 도메인 엔티티의 순수성

문제 영역을 모델링할 때 주된 초점은 가능한 한 정확하게 실제 시나리오를 코드로 변환하는 것이다.</br>
문제 영역 모델링의 핵심은 엔티티를 만드는 것이다.</br>

엔티티는 비즈니스 요구사항과 밀접한 관계를 가져야 하기 때문에 이러한 엔티티를 기술적인 요구사항으로부터 보호해야 한다.</br>
비즈니스 관련 코드와 기술 관련 코드가 혼동되는 것을 방지하기 위해 노력해야 한다.</br>

> '기술적': 소프트웨어 맥락에서만 존재하고 의미가 있는 것.
 
소프트웨어 없이 비즈니스 요구사항만 고려한다면 이러한 기술 관심사는 의미가 없다.</br>
또한 문제 영역이 항상 비즈니스 요구사항을 나타내는 것이 아니라는 사실을 인식해야 한다.</br>
문제 영역이 새로운 개발 프레임워크를 만드는 것 같이 순수하게 기술적인 것일 수도 있다.</br>

도메인 엔티티는 비즈니스 관심사만 처리한다는 점에서 순수해야 한다.</br>

### 관련 엔티티

**비즈니스 규칙**과 **비즈니스 데이터**라는 두 요소의 존재는 관련 엔티티의 특징을 결정한다.</br>
데이터 부분만 표현하고 규칙은 무시하는 데이터베이스 엔티티 객체과 다르다.</br>

- 빈약한 도메인 모델(anemic domain model): 비즈니스적으로 유의미하지 않는 객체나 비즈니스 로직이 거의 없는 도메인 객체들을 갖는 도메인 모델.</br>

도메인 객체에 동작이 존재하지 않는 경우에는 엔티티가 무엇을 해야 하는지를 완전하게 파악하기 위해 다른 곳으로 가야 한다.</br>
반면, 모델링하려는 엔티티에 본질적이지 않은 로직으로 엔티티 클래스에 과부하를 줘서는 안 된다.</br>
__처음에는 오퍼레이션을 엔티티의 일부로 생각할 수 있기 때문__

엔티티 동작에 본질적이지 않은 것으로 간주되는 것들에 대해서는 도메인 서비스를 사용할 수 있는 특권이 있다.</br>
__엔티티 클래스와 잘 맞지 않는 이러한 오퍼레이션들은 서비스를 통해 수용할 수 있다.__

```java
// 라우터들을 필터링하고 나열하기 위한 메서드
public static List<Router> retrieveRouter(List<Router> routers, Predicate<Router> predicate) {
    return routers.stream()
        .filter(predicate)
        .collect(Collectors.<Router>toList());
}
```

**실제 세계에서 이 라우터 동작을 라우터의 본질적인 특성으로 볼 수 있을까?**<br/>
    - 없다면 엔티티 클래스에서 이 동작을 제거해야 한다.<br/>
**리스트에 라우터를 추가하기 전에 라우터 타입을 확인하는 데 사용한 제약사항은 어떻게 되는가?**
    - 이러한 검증을 라우터의 고유한 동작으로 본다면?<br/>

- 이 제약사항을 엔티티 클래스에 직접 포함시킨다.<br/>
- 해당 제약사항을 어써션 처리(assert)하기 위한 명세를 생성한다.<br/>

라우터의 타입을 확인하는 제약사항을 직접 포함하고 있는 Router 엔티티 클래스의 예다.<br/>

```java
import java.util.function.Predicate;

public class Router {
    /** 코드 생략 **/
    public static Predicate<Router> filterRouterByType(RouterTpye routerTpye) {
        return routerType.equals(RouterType.CORE) ? Router.isCore() : Router.isEdge();
    }
    
    public static Predicate<Router> isCore() {
        return p -> p.getRouterType() == RouterType.CORE;
    }
    
    public static Predicate<Router> isEdge() {
        return p -> p.getRouterType() == RouterType.EDGE;
    }
    /** 코드 생략 **/
}
```

도메인 서비스 메서드를 수용하기 위해 RouterSearch 라는 도메인 서비스 클래스를 만들고,<br/>
다음과 같이 Router 클래스에서 이 클래스로 retrieveRouter 메서드를 옮겨야 한다.<br/>

```java
import java.util.List;
import java.util.function.Predicate;

public class RouterSearch {

    public static List<Router> retieveRouter(List<Router> routers, Predicate<Router> predicate) {
        return routers.stream()
                .filter(predicate)
                .collect(Collectors.<Router>toList());
    }
}
```

`retrieveRouter` 메서드는 도메인 헥사곤과 다른 헥사곤에 있는 다양한 객체들이 서비스로 사용할 수 있다.<br/>

### UUID를 이용한 식별자 정의

식별자(ID:identifier)의 중복 생성 및 방지를 위해 데이터베이스 시퀀스 매커니즘에 의존하는 식별자 생성 기법으로<br/>
위임하는 것이 편리하지만 그렇게 함으로써 소프트웨어의 중요 부분을 외부 시스템과 결합하게 된다.<br/>

가급적 기술 의존성이 적은 비즈니스 코드로 진화할 수 있는 헥사고날 애플리케이션을 개발하는 것을 목표로 한다고 가정해 보자.<br/>

중앙 기관에 의존하지 않는 식별자를 만드는 일반적인 방법은 **범용적 고유 식별자(universally unique identifier)** UUID를 사용하는 것이다.<br/>
분산 컴퓨터 환경(DCE:Distributed Computer Environment) 시간 기반, 보안, 이름 기반, 무작위 생성이라는 4가지 방법이 있다.<br/>

```java
// 이름 기반 UUID
var bytes = new byte[20];
new Random().nextBytes(bytes);
var nameBasedUUID = UUID.nameUUIDFromBytes(bytes);

// 무작위로 생성되는 UUID
var randomUUID = UUID.randomUUID();
```

엔티티 ID는 한 번 정의하고 나면 변경하지 말아야 하므로 불변 속성이 된다.<br/>
이러한 불변 속성은 엔티티 ID 속성을 값 개체로 모델링하기에 적합한 후보로 만든다.<br/>

```java
import java.util.UUID;

public class RouterId {
    private final UUID id;

    private RouterId(UUID id) {
        this.id = id;
    }

    public static RouterId withId(String id) {
        return new RouterId(UUID.fromString(id));
    }

    public static RouterID withoutId() {
        return new RouterId(UUID.randomUUID());
    }
}
```

엔티티는 헥사고날 아키텍처의 일급 객체(first-class citizen)다.<br/>

## 값 객체를 통한 서술력 향상

엔티티는 다른 소프트웨어 컴포넌트에서 파생된 기본 요소다.<br/>
그러나 도메인의 모든 것이 ID를 갖는 것이 아니기 때문에 풍부한 도메인 모델을 생성하기에는 엔티티만으로는 충분하지 않다.<br/>
문제 영역에 대한 서술력을 높이기 위한 객체 타입인 값 객체로 메운다.<br/>

DDD 에서는 문제 영역에서 사물을 측정하고, 수량화하거나 서술하기 위해 값 객체를 사용해야 한다.<br/>
기본 타입을 특정 값 객체로 감싸서 수량화를 좀 더 명확하게 표현할 수 있다.<br/>
문제 영역을 모델링하기 위해 프로그래밍 언어의 내장 타입만 사용하는 것으로는 충분하지 않다.<br/>
시스템의 본질과 목적을 더욱 명확하게 하기 위해 이러한 내장 타입, 심지어 우리가 생성한 타입도 잘 정의된 값 객체로 감싸야 한다.<br/>

- 값 객체는 불변이다.
- 값 개체는 식별자를 갖지 않는다.

값 객체는 문제 영역을 설명하는 데 사용되는 원재료다.<br/>
원재료만으로는 큰 의미를 표현하지도 큰 가치를 갖지도 못한다.<br/>
진정한 가치는 관련성 있고 식별 가능한 엔티티를 만들기 위해 원재료들을 결합하고 함께 사용할 때 나온다.<br/>

값 객페는 폐기할 수 있어야 하고 엔티티나 다른 객체 타입을 구성하는 데 사용할 수 있는 쉽게 교체 가능한 객체여야 한다.<br/>

엔티티 속성에 값 객체를 사용하지 않는 예다.<br/>

```java
public class Event implements Comparable<Event> {
    private EventId id;
    private OffsetDateTime timestamp;
    private String protocol;
    private String activity;
    ...
}
```

로그를 Event 객체로 파싱하고자 하는 데이터 항목이다.<br/>

```shell
00:44:06.906367 100430035020260940012015 IPV6 casanova.58183 > menuvivofibra.br.domain: 64865+ PTR? 1.0.0.224.in-addr.arpa.(40)
00:44:06:912775 100430035020260940012016 IPV4 menuvivofibra.br.domain > casanova.58183: 64865 1/0/0 PTR all-systems.mcast.net. (75)
```

로그를 적절하게 파싱하면 다음과 같은 네트워크 트래픽 액티비티 문자열 필드가 있는 Event 객체를 갖게 된다.<br/>

```shell
casanova.58183 > menuvivofibra.br.domain
```

'보다 크다'를 의미하는 부등호 기호 앞에는 출발지 호스트가 있고 뒤에는 목적지 호스트가 있다.<br/>
이를 패킷의 출발지와 목적지를 나타내는 액티비티라면, 문자열이기 때문에 조회 시 부담이 있다.<br/>

```java
/**casanova.58183**/
var srcHost = event.getActivity().split(">")[0];
```

엔티티 속성 중 하나인 값 객체를 사용한 예다.<br/>

```java
public class Activity {
    private STring description;
    private final String srcHost;
    private final String dstHost;
    
    public Activity (String description, String srcHost, String dstHost) {
        this.description = description;
        this.srcHost = srcHost;
        this.dstHost = dstHost;
    }
    
    public String retrieveSrcHost() {
        return this.srcHost;
    }
}
```

엔티티 클래스에 적용한다.<br/>

```java
public class Event implements Comparable<Event> {
    private EventId id;
    private OffsetDateTime timestamp;
    private String protocol;
    private Activity activity;
    ...
}
```

클라이언트 코드가 더 명확해지고 표현력도 좋아진다.<br/>
클라이언트는 출발지 호스트와 목적지 호스트를 조회하기 위해 데이터 자체를 처리할 필요가 없다.<br/>
```java
var srcHost = event.getActivity().retrieveSrcHost();
// casanova.58183
```

값 객체를 통해 데이터에 대해 더 높은 유연성과 제어권을 갖기 때문에 더 응집력 있는 방법으로 도메인 모델을 표현할 수 있다.<br/>