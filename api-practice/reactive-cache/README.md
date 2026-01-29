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