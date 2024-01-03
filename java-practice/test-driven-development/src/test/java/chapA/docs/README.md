# 부록 A

### 조건에 따른 테스트

```java
import org.junit.jupiter.api.condition.*;

@EnabledOnOs
@DisabledOnOs

@EnabledOnJre()
@DisabledOnJre()

@EnabledIfSystemProperty()
@DisabledIfSystemProperty()

@EnabledIfEnvironmentVariable()
@DisabledIfEnvironmentVariable()
```

### 태깅과 필터링

```java
import org.junit.jupiter.api.Tag;

@Tag()
```

```java
test {
    useJUnitPlagtform {
        includeTags 'intergration'
        excludeTags 'slow | very-slow'
    }
}
```

### 중첩 구성

```java
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;import org.junit.jupiter.api.Nested;

public class Outer { // 1
    @BeforeEach
    void outerBefore() {} // 3

    @Test 
    void outer() {}

    @AfterEach 
    void outerAfter() {} // 7
    
    @Nested
    class NestedA { // 2
        @BeforeEach 
        void nestedBefore() {} // 4
        
        @Test 
        void nested1() {} // 5
        
        @AfterEach
        void nestedAfter() {} // 6
    }
}
```

1. Outer 객체 생성
2. NestedA 객체 생성
3. outerBefore() 메서드 실행
4. nestedBefore() 메서드 실행
5. nestd1() 테스트 실행
6. nestedAfter() 메서드 실행
7. outerAfter() 메서드 실행

### 테스트 메시지

```java
//...
List<Integer> ret = getResuts();
List<Integer> expexted = Arrays.asList(1, 2, 3);
for (int i = 0; i < expected.size(); i++) {
    assertEquals(expected.get(i), ret.get(i), "ret[" + i + "]");
}
//...
```

### 임시 폴더 생성

```java
import org.junit.jupiter.api.BeforeAll;import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;

class TempTest {
    @TempDir
    File tempFolder;

    @TempDir
    static tempFolderPerClazz;
    
    @BeforeAll
    static void setup(@TempDir File tempFolder) {}
    
    @Test
    void fileTest(@TempDir Path tempFolder) {}
}

```

### 시간 검증

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TimeoutTest {
    @Test
    @Timeout
    void sellp2seconds() throws InterruptedException {
        Thread.sleep(2000);
    }
    
    //.. TimeoutException
}
```