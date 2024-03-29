# 7장 경계 조건: CORRECT 기억법

## [C]conformance(준수): 값이 기대한 양식을 준수하고 있는가?

많은 데이터가 특정 규약을 준수해야 한다.<br/>
특정 규약을 따르는지 검증하려면 많은 규칙들이 필요한데,<br/>
여러 규칙들을 조합하면 많은 수의 규약을 검증할 수 있다.<br/>
`브레인스토밍`을 통해 찾을 수 있다.<br/>
ex. 계좌 번호 같은 필드는 수많은 메서드에 넘겨질텐데, 최초 입력될 때 검증한다면 수월할 것이다.<br/>

## [O]rdering(순서): 값의 집합이 적절하게 정렬되거나 정렬되지 않았나?

데이터 순서 혹은 커다란 컬렉션에 있는 데이터 한 조각의 위치는 코드가 쉽게 잘못될 수 있다.<br/>

## [R]ange(범위): 이성적인 최솟값과 최댓값 안에 있는가?

자바 기본형으로 변수를 만들 때 대부분은 필요한 것보다 훨씬 많은 용량을 가진다.<br/>
ex. 나이는 음수가 없다, 나이를 `int` 표현한다면 수백만 세기를 표현할 만큼 충분하다.<br/>
사용자 정의 추상화를 클래스로 만들어 캡슐화하라.<br/>
캡슐화는 불변성 보장할 수 있다.<br/>

### 불변성을 검사하는 사용자 정의 매처 생성

```java
public class ConstrainsSidesTo extends TypeSafeMatcher<Rentangle> {
    private int length;
    
    public ConstrainsSidesTo(int length) {
        this.length = length;
    }
    
    @Override
    public void describeTo(Description description) {
        description.appendText("both sides must be <=" + length);
    }
    
    @Override
    protected boolean matchesSafely(Rentangle rect) {
        return
            Math.abs(rect.origin().x - rect.opposite().x) <= length &&
            Math.abs(rect.origin().y - rect.opposite().y) <= length;
    }
    
    @Factory
    public static <T> Matcher<Rectangle> constrainsSidesTo(int length) {
        return new ConstrainsSidesTo(length);
    }
}
```

### 불변 메서드를 내장하여 범위 테스트

- 희소 배열의 `value` 는 `null` 이 아닌 값만 허용하기 때문에 배열 크기는 반드시 `null` 이 아닌 값들의 개수와 같아야 한다.
- <u>내부 배열에 저장한 값들을 조사하는 테스트가 불필요하게 내부 구현 사항을 노출하게 만들 수 있다.</u>


<br/>
<br/>

인덱싱은 수많은 잠재적인 오류를 포함하고 있다.<br/>

인덱스를 다룰 때 고려해야 할 몇 가지 시나리오<br/>

- 시작과 마지막 인덱스가 같으면 안 된다.
- 시작이 마지막보다 크면 안 된다.
- 인덱스는 음수가 아니어야 한다.
- 인덱스가 허용된 것보다 크면 안 된다.
- 갯수가 실제 항목 갯수와 맞아야 한다.

## [R]eference(참조): 코드 자체에서 통제할 수 없는 어떤 외부 참조를 포함하고 있는가?

어떤 메서드를 테스트할 때는 다음을 고려해야 한다.

- 범위를 넘어서는 것을 참조하고 있지 않은가?
- 외부 의존성은 무엇인가?
- 특정 상태에 있는 객체를 의존하고 있는지 여부
- 반드시 존재해야 하는 그 외 다른 조건들

어떤 상태에 대해 가정할 때는 그 가정이 맞지 않으면 코드가 합리적으로 잘 동작하는지 검사해야 한다.<br/>
사전 조건이 맞지 않는다면 혹은 맞는다면 어떻게 동작하는가?<br/>

## [E]xistence(존재): 값이 존재하는가?(non-null, non-zero, 집합에 존재하는가? 등)

스스로에게 `주어진 값이 존재하는가?` 라고 물어봄으로써 많은 잠재적인 결함을 발견할 수 있다.<br/>
`null`, `0`, `""` 등과 같은 존재 여부에 대한 테스트를 하라.<br/>
<u>메서드가 홀로 설 수 있도록 만들어라.</u><br/>

## [C]ardinality(기수): 정확히 충분한 값들이 있는가?

울타리 기둥 오류: 한 끗 차이로 발생하는 수 많은 경우 중 한 가지를 의미한다.<br/>
테스트 코드는 0-1-n  이라는 경계 조건에 집중하고 n은 비즈니스 요구 사항에 따라 바뀔 수 있다.<br/>

ex. 식당에서 상위 10개의 음식 목록을 유지한다고 가정한다.<br/>

- 목록에 항목이 하나도 없을 대 보고서 출력하기
- 목록에 항목이 한 개만 있을 때 보고서 출력하기
- 목록에 항목이 없을 때 한 항목 추가하기
- 목록에 항목이 하나만 있을 때 한 항목 추가하기
- 목록에 항목이 아직 열 개 미만일 때 한 항목 추가하기
- 목록에 항목이 이미 열 개가 있을 때 한 항목 추가하기

테스트는 훌륭하며, 상위 20개의 음식 목록으로 변경 요구 사항이 온다면 단 한 줄의 코드만 변경해야 한다.<br/>

```java
public static final int MAX_ENTRIES = 20;
```

## [T]ime(절대적 혹은 상대적 시간): 모든 것이 순서대로 일어나는가? 정확한 시간에? 정시에?

- 상대적 시간 (시간 순서)
- 절대적 시간 (측정된 시간)
- 동시성 문제들

데이터의 순서가 중요한 것처럼 메서드의 호출 순서도 중요하다.<br/>

상대적인 시간은 타임아웃 문제도 포함한다.<br/>
수명이 짧은 자원에 대해 코드가 얼마나 기다릴 수 있는지 결정해야 한다.<br/>

동시에 같은 객체를 다수의 스레드가 접근한다면 어떤 일이 벌어질 것인가?<br/>
어떤 전역, 인스턴스 수준의 데이터나 메서드에 동기화를 해야할까?<br/>
클라이언트에 동시성 요구 사항이 있다면 다수의 클라이언트 스레드를 보여 주는 테스트를 작성할 필요가 있다.<br/>

## 마치며

테스트는 모든 경계를 알 필요가 있다.<br/>
경계 조건들은 자주 고약하고 작은 결함들을 만들어 내는 곳입니다.<br/>