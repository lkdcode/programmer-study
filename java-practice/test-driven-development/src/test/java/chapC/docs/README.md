# Mockito

### 모의 객체 생성

```java
private Apple mockApple = mock(Apple.class);
private Banana mockBanana = mock(Banana.class);
final FruitBox fakeFruitBox = mock(FruitBox.class);
```

### 스텁 설정

```java
    @Test
    void hasTest_mock() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        given(fakeFruitBox.get(1))
                .willReturn(new Apple("나는 사과"));

        assertThat(fakeFruitBox.get(1).getName())
                .isEqualTo("나는 사과");
    }
```

### 인자 매칭 처리

```java
    @Test
    void addTest_mock() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        given(fakeFruitBox.has(any()))
                .willReturn(true);

        assertThat(fakeFruitBox.has(new Apple("나는 사과")))
                .isTrue();

        assertThat(fakeFruitBox.has(new Banana("나는 바나나")))
                .isTrue();

        assertThat(fakeFruitBox.size())
                .isZero();
    }
```

- anyInt(), anyShort(), anyLong(), anyByte(), anyInt(), anyChar(), anyDouble(), anyFloat(), anyBoolean(),
- anyString()
- any() : 임의 타입
- anyList(), anySet(), anyMap(), anyCollection()
- matches(String), matches(Pattern) : 정규식
- eq(값): 특정 값과 일치 여부

```java
given(mockList.set(anyInt(), "123").willReturn("456")); // ❌
given(mockList.set(anyInt(), eq("123")).willReturn("456")); // ⭕️
```

### 행위 검증

```java
    @Test
    void sizeTest_mock() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        given(fakeFruitBox.size()).willReturn(99);

        assertThat(fakeFruitBox.size()).isEqualTo(99);

        then(fakeFruitBox).should(only()).size();
        then(fakeFruitBox).should(never()).add(any());
    }
```

### 인자 캡처

```java
    @Test
    void addTest_mock_captor() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        Apple apple = new Apple("애플");
        fakeFruitBox.add(apple);

        ArgumentCaptor<Fruit> argumentCaptor = ArgumentCaptor.forClass(Apple.class);

        then(fakeFruitBox)
                .should()
                .add(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getName())
                .isEqualTo(apple.getName());
    }
```

### JUnit 5 확장 설정

```java
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;

@ExtendWith(MockitoException.class)
public class ClazzTest {
    @Mock
    private Clazz fakeClazz;
    
    private Clazz fakeClazz2 = mock(Clazz.class);
}
```