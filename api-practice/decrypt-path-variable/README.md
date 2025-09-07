```sql
`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
```

데이터베이스의 증분되는 식별자를 사용할 경우 양수의 1씩 증가되는 값을 사용하게 된다. Pathvariable 에서 `id` 값을 통해 무분별한 api 요청에 대해서 방어하기위해 암호화된 변수를 주고 받을 수 있다.

## 🎯 Jasypt

암호화를 하기 위한 라이브러리로 Jasypt 를 사용한다. 의존성을 추가해주고 설정해주면 사용할 수 있다.

암호화 키는 환경 변수로 관리하고 해시 암호화도 보안 수준에 맞게 설정해준다.

- 의존성 추가

```groovy
implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")
```

- 빈 등록

```groovy
@Configuration
class JasyptConfig {
    
    @Bean
    fun stringEncryptor(): StringEncryptor =
        StandardPBEStringEncryptor()
            .apply {
                this.setPassword("lkdcode")
                this.setIvGenerator(NoIvGenerator())
            }
}
```

최대한 간결한 설정으로 빈에 등록했는데 Encryptor 의 종류도 여러가지이며 기본 생성자에서 제공하는 옵션들을 참고해 설정을 등록해준다.

- `StandardPBEStringEncryptor()` 의 경우 내부적으로 `StandardPBEByteEncryptor()` 생성자를 호출하는데 내부적으로 셋팅되어 있는 값들을 아래에서 확인할 수 있다.

```java
public final class StandardPBEByteEncryptor implements PBEByteCleanablePasswordEncryptor {
    public static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDES";
    public static final int DEFAULT_KEY_OBTENTION_ITERATIONS = 1000;
    public static final int DEFAULT_SALT_SIZE_BYTES = 8;
    public static final int DEFAULT_IV_SIZE_BYTES = 16;
    private String algorithm = "PBEWithMD5AndDES";
    private String providerName = null;
    private Provider provider = null;
    private char[] password = null;
    private int keyObtentionIterations = 1000;
    private SaltGenerator saltGenerator = null;
    private int saltSizeBytes = 8;
    private IvGenerator ivGenerator = null;
    private int ivSizeBytes = 16;
    private PBEConfig config = null;
    private boolean algorithmSet = false;
    private boolean passwordSet = false;
    private boolean iterationsSet = false;
    private boolean saltGeneratorSet = false;
    private boolean ivGeneratorSet = false;
    private boolean providerNameSet = false;
    private boolean providerSet = false;
    private boolean initialized = false;
    private SecretKey key = null;
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;
    private boolean optimizingDueFixedSalt = false;
    private byte[] fixedSaltInUse = null;

    public StandardPBEByteEncryptor() {
    }
/*생략*/
}
```

그외에 설정은 필요에 따라 추가해준다.

## 🎯 GenericConverter

컨트롤러에서 `@PathVariable` 을 통해 받는 값이 자동으로 암/복호화가 되는 것이 더 편리하다.

`GenericConverter` 는 스프링 `ConversionService` 가 컨트롤러 파라미터 바인딩 시 소스 타입 → 대상 타입으로 값을 변환하는 범용 컨버터다. 이 기능을 활용하기 위해 커스텀 어노테이션을 만들어 암→복호화를 자동으로 수행할 수 있도록 스프링 빈에 등록해준다.

- 암 → 복호화 어노테이션

```kotlin
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class DecryptId
```

- Resolver

```kotlin
@Component
class CryptoIdResolver(
    private val cryptoSupport: CryptoSupport
) : ConditionalGenericConverter {

    override fun getConvertibleTypes(): MutableSet<GenericConverter.ConvertiblePair>? =
        mutableSetOf(
            ConvertiblePair(String::class.java, java.lang.Long::class.java),
            ConvertiblePair(String::class.java, java.lang.Long.TYPE)
        )

    override fun convert(source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): Any? =
        cryptoSupport.decryptId(source as String)

    override fun matches(sourceType: TypeDescriptor, targetType: TypeDescriptor): Boolean =
        targetType.hasAnnotation(DecryptId::class.java)

}
```

- `getConvertibleTypes()` : 어떤 타입을 어떻게 변환할 것인지 `ConversionService` 에 등록한다.
- `convert()` : 실제 변환 로직을 담당하는데 이때 cryptionSupport 를 통해 암/복호화를 수행한다.
- `matches()` : Predicate 이다. 해당 조건이 만족하면 변환을 수행한다. 위에서 선언한 어노테이션을 매치시면 된다.

유사한 `HandlerMethodArgumentResolver` 가 있다. `GenericConverter` 는 타입 변환기이며 `HandlerMethodArgumentResolver` 는 특정 파라미터를 어떻게 만들지 설정하는것인데 server to server 에서 파라미터 주입기로 사용할 수 있다.

Jasypt 로 암호화된 변수명을 받으면 증분 식별자로 자동으로 변환해주는 어노테이션을 완성했다.

```kotlin
// $ localhost:18080/Y2xZWE9jQ1ZHQzNta3Q1Nnc0RldyZz09
@GetMapping("/{id}")
fun encryptedId(
    @DecryptId @PathVariable(name = "id") id: Long,
) {
    println("ID : $id") // ID : 33
}
```