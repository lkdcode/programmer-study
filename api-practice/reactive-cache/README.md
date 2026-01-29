Spring MVC 에서는 `@Cacheable` 어노테이션을 사용하여 편리하게 캐싱을 사용할 수 있었는데  
Spring Webflux 에서도 그대로 사용해도 될까?  

# Spring Cache

`@Cacheable`  `@CachePut`  `@CacheEvict` 어노테이션은 개발자들을 대신하여 쉽게 캐시 작업을 수행할 수 있도록 도와준다.  
내부적으로 AOP 로 동작하게 되며 등록한 `cacheManager`, `keyGenerator` 등을 통해 간편하게 할 수 있다. (이때 여러 개의 캐시 매니저도 등록할 수 있음!)  

# Spring webflux 에서는?

Spring webflux 는 Thread per request 방식이 아니라 Event Loop 방식으로 돌아가는데,  
이때 블로킹되는 작업이 하나라도 이벤트 루프에 들어오게 되면 전체 쓰레드가 블로킹되는 대참사가 발생한다.  
그럼, AOP 는 블로킹일까? 논블로킹일까?  

# AOP 는 블로킹? 논블로킹?

결론은 AOP 안에 작성된 함수에 따라 다르다.  
AOP 로 가로챈 코드안에서 블로킹 함수를 호출하게 되면 해당 AOP 는 블로킹으로 동작하게 되고,  
리액티브하다면 여전히 리액티브할 것이다.  

# 그렇다면, webflux 에서 `@Cacheable` 을 써도 되겠군?

결론부터 말하자면 사용하지 않는 것이 좋다. 우선 공식문서를 보면.  

[Declarative Annotation-based Caching :: Spring Framework](https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html?utm_source=chatgpt.com#cache-annotations-cacheable-reactive)

> **Caching with CompletableFuture and Reactive Return TypesAs of 6.1, cache annotations take CompletableFuture and reactive return types into account, automatically adapting the cache interaction accordingly.**
>

CompletableFuture 와 리액티브 반환 타입에 맞게 캐시를 해준다고 나와있다.  
마치 비동기/논블로킹을 지원할 것 같은데 실제 실행 결과를 보면 다르다는 것을 알 수 있다.  

# BlockHound

[GitHub - reactor/BlockHound: Java agent to detect blocking calls from non-blocking threads.](https://github.com/reactor/BlockHound?tab=readme-ov-file#blockhound)

“non-blocking operations only” . JVM 클래스들을 계측해 블로킹 호출이 수행될 경우 에러를 던지는 라이브러리다.
webflux 이벤트 루프에 블로킹 작업이 들어오면 안 되기 때문에 이와 같은 라이브러리를 활용해 미리 확인할 수 있다.
주로 테스트 코드에서 작성되지만 본 포스팅에서는 main 메서드에 포함시키도록 간소화했다.
IntelliJ 로 메인 메서드를 실행할 경우 VM options 에 `-XX:+AllowRedefinitionToAddDeleteMethods` 를 추가하자. (그외에는 공식 문서를 참고)  

```java
@SpringBootApplication
class ReactiveCacheApplication

fun main(args: Array<String>) {
    BlockHound.install()
    runApplication<ReactiveCacheApplication>(*args)
}
```

# Config

RedisCacheManager 를 등록하고 테스트에 사용할 API → Service → Repository 를 작성하고 `@Cacheable` 어노테이션을 작성한다.  
Spring Data Redis 에서 공식적으로 제공하는 드라이버는 Lettuce 와 Jedis 가 있으며 간략히 비교하면 비동기/논블로킹 지원 여부가 다르다. (Lettuce 가 지원)  
Lettuce 가 사실상 표준이다.  

[Drivers :: Spring Data Redis](https://docs.spring.io/spring-data/redis/reference/redis/drivers.html#redis:connectors:connection)

# Run

설정 후 실행을 하게 되면, BlockHound 가 사냥(?)에 성공하며 다음과 같은 에러를 만날 수 있다.
추적할 클래스와 함께 Stack Trace 도 보인다.

`@Cacheable` 을 사용한 경우 `CacheInterceptor` 로부터 시작해서 `CompletableFuture` 에서 `LockSupport.java:221`를 호출하면서 Blocking 된다.  
`LockSupport.java:221` 에서 브레이크 포인트를 걸고 쓰레드를 확인해볼 수 있는데, `reactor-http-nio` 는 Webflux 핵심인 이벤트 루프 스레드임을 의미한다.
218 라인에서 가상 쓰레드가 아니므로, 221 라인 `U.park(false, 0L);` 에서 멈추게 된다. `park()` 메서드를 보면 첫 줄에 Block 된다고 나와있다.  

```java
/**
 * Blocks current thread, ...
 */
@IntrinsicCandidate
public native void park(boolean isAbsolute, long time);
```

또, 여러 operation 들을 사용할 때 해당 operation 이 parallel 스레드를 사용하는 경우가 있다.  
마찬가지로 Block 이 되면 안되는데, `Schedulers.parallel()` 은 기본적으로 CPU 코어 수와 똑같은 개수만 생성하기 때문이다. (테스트를 위해 사용한 operation 은 `.delayElement()` 이다)  
`Schedulers.parallel()` 메서드는 스레드를 강제로 `parallel` 스레드로 변경하기 때문에 UpstreamOperation or DownstreamOperation 을 사용해 `.boundedElastic()` 스레드를 명시하더라도 BoundedElastic 스레드가 끝까지 유지되지는 않는다.  
`@Cacheable` 어노테이션을 사용하기 위해 Reactive 한 RediCacheManager 를 적용하는 것도 불가한데, interface 가 다르기 때문이다.  
`RedisCacheManager` 의 interface 를 보면 모두 동기식으로 반환하는 것을 알 수 있다. (Cache 도 마찬가지.)  

```java
public interface CacheManager {
		Cache getCache(String name);
		Collection<String> getCacheNames();
}
```

Reactive 한 지원을 위해선 완전히 새로운 인터페이스가 필요하다.

# Custom @ReactiveCacheable

프로그래밍 방식으로 직접 제어할 수도 있지만 다소 장황하고 불편하다. `@Cacheable` 처럼 선언형으로 사용할 수 있도록 해보자.  
AOP 와 함께 `interface ReactiveRedisOperations` 를 사용하는 `CacheManager` 를 두어서 사용할 수 있다.  
이제 BlockHound 가 사냥(?)하지 않고 캐시도 사용할 수 있게 된다.  

### cache.annotation.*

간소화한 속성들만 선언해주고 기본적인 keyGenerator 를 두자. 나머지 put, evict 어노테이션도 마찬가지다.  

### cache.interceptor.ReactiveKeyGenerator

keyGenerator 속성에서 사용할 인터페이스와 가로챈 메서드의 클래스명, 메서드명, 키값으로 문자열을 제공해주는 기본 클래스를 등록한다.  
**ReactiveCacheArgsParser** 와 함께 협력한다.  

### cache.handler.ReactiveCacheConditionHandler

어노테이션 속성에 있는 condition/unless 속성을 SpEL 표현식을 통해 핸들링해준다.  

### cache.handler.ReactiveCachePropertyHandler

어노테이션 속성에 있는 key, TTL, unless 등 속성을 추출하여 필요한 값들을 생성한다.  

### cache.aspect.*
어노테이션이 붙어있는 메서드를 가로채고 수행할 작업을 작성한다.  
각 어노테이션에 맞게 작성해주면 된다.  

### cache.resolver.KotlinTypeResolver

리모트 캐시로 Redis 를 사용하는데 역직렬화 과정에서 @class 정보를 두어 해결한다.  
이는 다형성과 코틀린의 기본 동작과 관련있는데 코틀린은 `open` 키워드를 명시하지 않는 이상 모두 `final` 이다.  
`final` 클래스는 직렬화할 때 상속할 대상이 없으므로 클래스 정보를 저장하지 않는데,  
이때 interface 를 상속할 경우 해당 클래스가 어떤 하위 타입인지 알 수 없는 문제를 맞이한다.  
`ObjectMapper.DefaultTyping.EVERYTHING` 으로 두면 불필요한 객체까지 되므로 보안, 성능 문제가 있다.(이미 deprecated)  
그래서 코틀린 전용 리졸버를 두어 해당 문제를 해결한다.  