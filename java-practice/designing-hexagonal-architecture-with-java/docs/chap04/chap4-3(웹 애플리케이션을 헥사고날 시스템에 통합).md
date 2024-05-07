# 웹 애플리케이션을 헥사고날 시스템에 통합

기술의 발전과 변화는 오래된 시스템을 환상적인 프레임워크를 기반으로 교체되었다.  
서로 다른 범주의 컴포넌트들 사이의 경계를 명확하게 만들기를 원했고  
마침내 프런트엔드 코드와 백엔드 코드를 가까이 두는 것이 소프트웨어 프로젝트에서 엔트로피의 원인이 될 수 있다는 것을 깨달았다.  
이러한 관행에 대한 대응으로, 프런트엔드 시스템이 하나 이상의 백엔드 시스템이 있는 네트워크를 통해 상호작용하는 분리된 독립 실행형 애플리케이션이 있는 분리된 아키텍처에 관심은 갖게 되었다.

## 구현 & 리팩터링 예시

```java
public interface RouterNetworkUseCase {
    Router addNetworkToRouter(RouterId routerId, Network network);

    Router getRouter(RouterId routerId);
}
```

`getRouter` 메서드를 추가해 프런트엔드 애플리케이션이 라우터를 표시할 수 있도록 한다.

```java
public class RouterNetworkInputPort implements RouterNetworkUseCase {
    /** 코드 생략**/
    @Override
    public Router getRouter(RouterId routerId) {
        return fetchRouter(routerId);
    }

    private Router fetchRouter(RouterId routerId) {
        return routerNetworkOutputPort.fetchRouterById(routerId);
    }
    /** 코드 생략**/
}
```

`fetchRouter` 는 이미 입력 포트 구현을 갖고 있지만, 라우터 검색을 할 수 있는 노출된 오퍼레이션을 갖고 있지 않다.  
`fetchRouter` 메서드는 `addNetworkToRouter` 뿐만 아니라 `getRouter` 에서도 사용된다.

```java
/* RouterNetworkAdapter */
public Router getRouter(Map<String, String> params){
    var routerId=RouterId.withId(params.get("routerId"));
    return routerNetworkUseCase.getRouter(routerId);
}
```

입력 포트가 변경된 것을 입력 어댑터에도 전달해야 한다.  

`RouterNetworkAdapter` 는 `RouterNetworkCLIAdapter` 와 `RouterNetworkRestAdapter` 모두의 기본 입력 어댑터다.  
프런트엔드 애플리케이션이 헥사고날 시스템과 통신할 수 있게 하려면 REST 어댑터를 사용해야 한다.  
따라서 이러한 통신을 위해 `RouterNetworkRestAdapater` 를 알맞게 변경해야 한다.  

이제 애플리케이션의 프런트엔드 부분에 대한 개발로 초점을 이동할 수 있다.  


## 테스트 에이전트 실행

프런트엔드 애플리케이션 외에 드리븐 오퍼레이션의 또 다른 일반적인 유형은 기능이 잘 동작하는지 확인하기 위해  
헥사고날 시스템과 상호작용하는 테스트와 모니터링 에이전트다.  
포스트맨 같은 도구를 이용하면 특저 요청에 대한 애플리케이션의 동작 방법을 검증하기 위한 포괄적인 테스트 케이스를 생성할 수 있다.  

또한 특정 애플리케이션 엔드포인트가 정상인지 아닌지를 확인하기 위해 엔드포인트에 대한 요청을 주기적으로 발행할 수 있다.  
애플리케이션이 정상 상태인지 확인할 수 있도록 특저 엔드포인트를 제공하는 스프링 액추에이터 같은 도구와 함께 대중화되었다.  

일부 기법은 애플리케이션이 활성화되어 있는지 확인하기 위해 주기적으로 애플리케이션에 요청을 보내는 프로브 메커니즘(probe mechanism)을 포함하기도 한다.  
예를 들어, 애플리케이션이 활성화되어 있지 않거나 시간 초과를 발생시키는 경우에는 애플리케이션이 자동으로 다시 시작할 수 있다.  
쿠버네티스에 기반한 클라우드 네이티브 아키텍처에서는 프로브 메커니즘을 사용하는 시스템을 아주 흔히 볼 수 있다.  

포스트맨과 뉴먼을 사용해 테스트를 실행하는 것은 헥사고날 애플리케이션을 지속적인 통합(CI:Continuous Intergration) 파이프라인에 통합하는 데 좋다.  
포스트맨을 사용해 컬렉션과 개별 테스트를 만들고, 이러한 동일 컬렉션들은 테스트를 실행하는 데 뉴먼을 사용할 수 있는(젠킨스 등) CI 도구를 통해 트리거되고 검증된다.  