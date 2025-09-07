조회성 API를 처리할 때 반복되는 부분들이 존재합니다. QueryString 과 페이지네이션을 위한 값들이 해당되는데, 이러한 부분들을 추상화하고 집중할 수 있는 곳을 간단하게 만들면 변경되는 요구 사항에 빠르게 대처할 수 있습니다. JPA 와 QueryDSL 을 사용할 때 어떻게 생상선이 높아지는지 알아보겠습니다.

### ⚠️ Caution

- Kotlin 2.1.10, SpringBoot 3.4.5, JPA, QueryDSL, MySQL
- JPA Entity 클래스나 Repository 따로 설명하지 않고 최대한 간단하게 설정했습니다.
- 본 코드는 코틀린으로 작성되었지만 자바로도 충분히 가능합니다.
- 가독성을 위해 간결하게 작성됐습니다. 함수명, 객체 지향, 아키텍쳐 개념 등이 다소 무너져있습니다.

## 🎯 시나리오

클라이언트는 과일에 대한 다양한 정보를 조회할 수 있습니다. 조건, 정렬, 정렬 방향의 기능을 지원하며, 첫 번째 요구 사항을 만족 시키고 이후 추가되는 두 번째 요구 사항에 대해 어떻게 대처할 수 있는지 알아보겠습니다.

- 첫 번째 요구 사항: 품종, 당도, 가격을 기준으로 필터링 및 정렬
- 두 번째 요구 사항: 브랜드, 등급, 수확일 기준으로 필터링 및 정렬

## 🎯 Table

이번 글에서 사용할 테이블입니다. 총 2개의 테이블을 사용합니다.

- 과일 정보를 담고 있는 테이블

```sql
CREATE TABLE `fruit` (
	`id`              BIGINT UNSIGNED   NOT NULL  AUTO_INCREMENT PRIMARY KEY  COMMENT '식별 인덱스',
    `variety`         VARCHAR(100)      NOT NULL                              COMMENT '품종',
    `sweetness_brix`  DECIMAL(4,1)      NOT NULL                              COMMENT '당도',
    `brand`           VARCHAR(120)      NOT NULL                              COMMENT '브랜드',
    `grade`           VARCHAR(50)       NOT NULL                              COMMENT '등급',
    `harvested_at`    DATE              NOT NULL                              COMMENT '수확일'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO fruit (variety, sweetness_brix, brand, grade, harvested_at) VALUES
('사과', 13.5, '자바 농장', '특', '2025-09-01'),
('샤인머스캣', 18.7, 'lkdcode 농장', '상', '2025-08-28'),
('오렌지', 11.2, 'lkdcode 농장', '상', '2025-08-20'),
('딸기', 9.5, 'lkdcode 농장', '상', '2025-03-15'),
('배', 12.1, '자바 농장', '특', '2025-09-02'),
('복숭아', 12.8, 'lkdcode 농장', '보통', '2025-08-18'),
('자두', 12.0, 'lkdcode 농장', '보통', '2025-08-10'),
('멜론', 14.8, '자바 농장', '특', '2025-07-30'),
('바나나', 19.2, 'lkdcode 농장', '상', '2025-08-25'),
('블루베리', 12.3, 'lkdcode 농장', '상', '2025-07-15');
```

- 가격 정보를 담고 있는 테이블

```sql
CREATE TABLE `price` (
	`id`              BIGINT UNSIGNED   NOT NULL  AUTO_INCREMENT PRIMARY KEY  COMMENT '식별 인덱스',
	`fruit_id`        BIGINT UNSIGNED   NOT NULL                              COMMENT '과일 식별 인덱스',
    `price`           DECIMAL(10,2)     NOT NULL                              COMMENT '가격',
    `currency`        CHAR(3)           NOT NULL DEFAULT 'KRW'                COMMENT 'ISO 4217 (KRW, USD)',
    `unit`            VARCHAR(20)       NOT NULL DEFAULT 'KG'                 COMMENT '단위 (KG, BOX, EA)',
    FOREIGN KEY (`fruit_id`)            REFERENCES fruit(`id`)                ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO price (fruit_id, price, currency, unit) VALUES
(1, 25000.00, 'KRW', 'BOX'),
(2, 22000.00, 'KRW', 'KG'),
(3, 12000.00, 'KRW', 'KG'),
(4,  6000.00, 'KRW', 'EA'),
(5, 18000.00, 'KRW', 'KG'),
(6, 15000.00, 'KRW', 'KG'),
(7,  8000.00, 'KRW', 'KG'),
(8, 12000.00, 'KRW', 'EA'),
(9,  4000.00, 'KRW', 'KG'),
(10, 7000.00, 'KRW', 'BOX');
```

### 🎯 응답 JSON 포맷

- 클라이언트에게 응답할 JSON 포맷

```json
{
  "items": [
    {
      "fruitId": integer,
      "variety": string,
      "sweetnessBrix": number,
      "brand": string,
      "grade": string,
      "harvestedAt": date,
      "price": number,
      "currency": string,
      "unit": string
    }
  ],
  "pagination": {
    "totalItems": integer,
    "totalPages": integer,
    "currentPage": integer,
    "perPage": integer
  }
}
```

### 🎯 RestController

`@PageableDefault` 로 기본 페이지네이션을 제공하고 `@ModelAttribute` 를 통해 조회 조건을 받습니다.

`@ModelAttribute` 에서 사용할 변수들의 타입은 상황에 맞게 변경해도 무방합니다.

- 두 가지 버전으로 나누어진 예시이며 `@PageableDefault` 와 `@ModelAttribute`  로 쿼리 파라미터를 받습니다.

```kotlin
@RestController
class FruitQueryApi(
    private val queryFruitUsecase: QueryFruitUsecase,
) {

    @GetMapping("/api/v1/fruits")
    fun getListV1(
        @PageableDefault(size = 15, page = 0, sort = ["variety"], direction = ASC) pageable: Pageable,
        @ModelAttribute queryString: FruitQueryStringV1,
    ): ResponseEntity<FruitPage> {
        val response = queryFruitUsecase.fetchV1(queryString.convert(), pageable)

        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/api/v2/fruits")
    fun getListV2(
        @PageableDefault(size = 15, page = 0, sort = ["variety"], direction = ASC) pageable: Pageable,
        @ModelAttribute queryString: FruitQueryStringV2,
    ): ResponseEntity<FruitPage> {
        val response = queryFruitUsecase.fetchV2(queryString.convert(), pageable)

        return ResponseEntity.ok().body(response)
    }
}
```

### QueryParameter

- 첫 번째 요구 사항을 만족시키기 위한 쿼리 파라미터

```kotlin
data class FruitQueryStringV1(
    val variety: String? = "",

    val sweetnessBrixGoe: String? = "",
    val sweetnessBrixLoe: String? = "",

    val priceGoe: String? = "",
    val priceLoe: String? = "",
) : BaseQueryString()
```

## 🎯 BaseQueryString()

Query에서 공통적으로 사용할 프로퍼티를 만들기 위한 클래스입니다. `ParamConditionList` 값 객체를 통해 사용하게 되며, 변수명이 곧 조건의 key 값이 되고 변수의 값이 조건에서 사용할 값이 됩니다. 이 부분을 리플렉션으로 풀어냈지만 다른 방법으로도 만들 수 있습니다.

```kotlin
abstract class BaseQueryString {
    fun convert(): ParamConditionList = ParamConditionList(
        this::class.memberProperties
            .filterIsInstance<KProperty1<Any, *>>()
            .mapNotNull { property ->
                val value = property.get(this) as? String
                if (!value.isNullOrBlank()) {
                    ParamCondition(property.name, value)
                } else null
            }
            .toList()
    )
}
```

### 리플렉션 vs Map

리플렉션을 위한 의존성과 약간의 성능이 필요합니다. 하지만 유지보수/검증/문서화 측면에서 DTO 로 받아 리플렉션으로 처리하는 것이 더 유리하며 Map 의 경우 의존성과 성능 이슈에서 비교적 우위를 점하지만 유지보수/검증/문서화 측면에서는 더 불리하므로 알맞게 선택하는 것이 좋습니다.

## 🎯 QueryAdapter

QueryDSL 이라는 기술을 활용하여 실제 테이블과 상호작용하며 조회 쿼리를 처리할 클래스입니다. 조건, 정렬, 정렬방향은 항상 작성되는데 이 부분을 추상화해서 공통적으로 재활용할 수 있도록 만듭니다. 각 Adapter 마다 필요한 조건들만 작성하면 되므로 추상화를 통한 이점이 더 많습니다.

### where 절에 사용될 컨디션

`.where()` 메서드는 매개변수로 `com.querydsl.core.types.Predicate` 를 받고 있는데 이 부분에서 사용할 `Predicate` 를 추상화하면 됩니다.

앞서 컨트롤러에서 받을 QueryString 은 ParamConditionList 로 변환이 되는데 이를 가공해 BooleanExpression을 리턴하여 `.where()` 에서 사용할 조건을 만들 수 있습니다.(`BooleanExpression`은 `Predicate` 의 구현체)

- `.where()` 에서 사용할 필터 조건

```kotlin
fun whereCondition(
    conditions: ParamConditionList,
    vararg addConditions: BooleanExpression?
): Array<BooleanExpression> = (conditions.list
    .filter { true }
    .filter { it.isNotValue() }
    .mapNotNull(paramCondition())
        + addConditions.filterNotNull())
    .toTypedArray()
```

`vararg addConditions: BooleanExpression?` 을 통해 클라이언트의 요구 사항에 대한 조건 외에도 다른 조건들을 쉽게 추가할 수 있습니다.

### Pageable

페이지 정보를 가져오기 위해 사용되는 `.orderBy()` 메서드 역시 공통적으로 사용되는 부분이 많으므로 공통 메서드로 분리합니다. 해당 메서드는 `.orderBy()` 가 매개변수로 받는 `OrderSpecifier` 해당 클래스를 클라이언트의 요구 사항대로 설정할 수 있는 메서드입니다.

- `.orderBy()` 에서 사용할 조건 (`Pageable`)

```kotlin
fun creteOrderSpecifier(
    pageable: Pageable
): Array<OrderSpecifier<*>> = pageable.sort
    .filterNotNull()
    .map {
        val direction = if (it.isAscending) Order.ASC else Order.DESC
        val property = it.property
        OrderSpecifier(direction, sortingCondition().invoke(property))
    }
    .filter { true }
    .toTypedArray()
```

스트림 함수들을 통해 필터링해주며 `Pageable` 없이 `Sort` 기능만을 제공해야할 수도 있습니다. 이때 `Sort` 를 매개변수로 받아 똑같이 `.orderBy()` 에서 사용할 조건을 만들어 줍니다.

- `.orderBy()` 에서 사용할 조건 (`Sort`)

```kotlin
fun creteOrderSpecifier(
    sort: Sort
): Array<OrderSpecifier<*>> = sort
    .filterNotNull()
    .map {
        val direction = if (it.isAscending) Order.ASC else Order.DESC
        val property = it.property
        OrderSpecifier(direction, sortingCondition().invoke(property))
    }
    .filter { true }
    .toTypedArray()
```

`.where()` 함수와 `.orderBy()` 함수가 사용할 매개변수를 만드는 메서드안을 보면 `paramCondition()` 함수와 `sortingCondition()` 함수가 있는데 이는 해당 클래스를 상속할 `Adapter` 계층이 구현하게 됩니다.

### paramCondition() 구현

- `.where()` 절에서 사용할 `Predicate` 작성 예시

```kotlin
override fun paramCondition(): (ParamCondition) -> BooleanExpression? = { e ->
    when (e.key) {
        "fruitId" -> FRUIT.id.eq(e.valueToLong)
        "variety" -> FRUIT.variety.containsIgnoreCase(e.valueString)

        "sweetnessBrixGoe" -> FRUIT.sweetnessBrix.goe(e.valueToBigDecimal)
        "sweetnessBrixLoe" -> FRUIT.sweetnessBrix.loe(e.valueToBigDecimal)

        "brand" -> FRUIT.brand.containsIgnoreCase(e.valueString)

        "grade" -> FRUIT.grade.eq(Grade.valueOf(e.valueString))

        "harvestedAtGoe" -> FRUIT.harvestedAt.goe(e.valueToDate)
        "harvestedAtLoe" -> FRUIT.harvestedAt.goe(e.valueToDate)

        "priceGoe" -> PRICE.price.goe(e.valueToBigDecimal)
        "priceLoe" -> PRICE.price.loe(e.valueToBigDecimal)

        "currency" -> PRICE.currency.eq(Currency.valueOf(e.valueString))
        "unit" -> PRICE.unit.eq(Unit.valueOf(e.valueString))

        else -> null
    }
}
```

위에는 모든 조건들이 나열되어있지만 특정 조건들만 열어두어도 무방합니다.

- `OrderSpecifier` 가 사용할 `Expression<T>` 작성 예시

```kotlin
override fun sortingCondition(): (String) -> Expression<out Comparable<*>>? = { e ->
    when (e.trim()) {
        "fruitId" -> FRUIT.id
        "variety" -> FRUIT.variety

        "sweetnessBrix" -> FRUIT.sweetnessBrix
        "brand" -> FRUIT.brand
        "grade" -> FRUIT.grade

        "harvestedAt" -> FRUIT.harvestedAt
        "price" -> PRICE.price

        "currency" -> PRICE.currency
        "unit" -> PRICE.unit

        else -> FRUIT.id
    }
}
```

`.orderBy()` 는 `OrderSpecifier` 를 매개변수로 받는데 이를 생성하기 위해선 `Expression<T>` 가 필요하므로 해당 클래스를 만들어줍니다.

이렇게 쿼리에서 사용할 공통 부분을 추상화하고 하위 구현 클래스에서 각각의 요구 사항에 알맞게 작성해주면 변경되는 요구 사항에 좀 더 빠르게 대처할 수 있습니다.

Adapter 부분을 이렇게 작성하면 Controller 에서 열어두는 쿼리 스트링을 기준으로 처리할 수 있습니다. 이는 DTO를 리플렉션으로 할지 Map 으로 할지와도 연결됩니다. 이제 변경된 두번째 요구사항을 달성해봅니다. 몇 개의 변수만 추가해주면 됩니다.

### QueryParameter

- 두 번째 요구 사항을 만족시키기 위한 쿼리 파라미터

```kotlin
data class FruitQueryStringV2(
    val fruitId: String? = "",
    val variety: String? = "",

    val sweetnessBrixGoe: String? = "",
    val sweetnessBrixLoe: String? = "",

    val priceGoe: String? = "",
    val priceLoe: String? = "",

    val brand: String? = "",
    val grade: String? = "",

    val harvestedAtGoe: String? = "",
    val harvestedAtLoe: String? = "",
) : BaseQueryString()
```

### example URL

```bash
# 10,000 이하 / 가격 내림차순
http://localhost:18080/api/v1/fruits?priceLoe=10000&sort=price,DESC

# 10,000 이하 / 가격 내림차순 / 딸기
http://localhost:18080/api/v1/fruits?priceLoe=10000&sort=price,DESC&variety=딸기

# 10,000 이하 / 브랜드 / 가격 내림차순 + 수확일 오름차순
http://localhost:18080/api/v2/fruits?priceGoe=10000&brand=lkdcode&sort=price,DESC%26harvestedAt,ASC
```