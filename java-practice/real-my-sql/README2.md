# SELECT

- 일반적인 `SELECT` 쿼리의 순서

```mermaid
flowchart LR
DrivingTable_WHERE_and_JOIN --> GRUOP_BY --> DISTINCT --> HAVING --> ORDER_BY --> LIMIT 
```

`INDEX` 통한 조회는 `ORDER BY`, `GROUP BY` 가 무시된다. 당연하게도 `INDEX` 자체가 이미 정렬되어 있으므로.  

- `GROUP BY` 절이 없이 `ORDER BY`만 사용된 쿼리의 순서  

```mermaid
flowchart LR
DrivingTable_WHERE --> ORDER_BY --> DrivenTable_Join --> LIMIT 
```

- `WHERE` 절에 사용되는 인덱스의 타입은 중요하다.
- `WHERE` 절에 복합인덱스의 순서는 크게 중요하지 않다.(MySQL 8.0)
- `WHERE` 절에 `OR` 연산을 주의해야 한다.

### 쿼리 실행 순서를 바꾸고 싶다면 `Inline view`로 풀어낼 수 있다.  

만약 `GROUP BY`전에 `LIMIT`을 적용하고 싶다면 `inline view`로 풀어낼 수 있다.

```sql
SELECT ...
FROM ( `INLINE_VIEW` ) temp_view
ORDER BY ...
```

하지만 `DERIVED` 테이블(임시 테이블)이 발생할 수 있으므로 주의해야 한다.  

### 인덱스 컬럼의 값을 바꾸면 사용할 수 없다. 

`GROUP BY`, `WHERE`, `ORDER BY`에서 `INDEX`를 활용할 수 있지만, 값을 변형하면 안 된다.  
`INDEX`는 `B-Tree`에 그대로 저장되어 사용하므로 내장함수를 호출해서 컬럼의 값을 바꾸면 `INDEX`를 활용할 수 없다.  

- 단순한 형태, `price`가 `INDEX`인 경우
```sql
# 인덱스 사용불가
SELECT price FROM tb_order WHERE price * 10 > 5000;

# 인덱스 사용 가능
SELECT price FROM tb_order WHERE price > 5000 / 10; 
```

복잡한 연산을 수행한다거나 특정 내장 함수처럼 해시 값을 만들어서 비교해야 하는 경우라면,  
미리 계산된 값을 저장하도록 `가상 컬럼(Virtual Comlumn)`을 추가하고 인덱스를 생성해 함수 기반의 인덱스를 사용하면 된다.  

컬럼의 데이터 타입 또한 일치해야 한다.  

```sql
# age 는 VARCHAR 타입이고 인덱스이다.
CREATE TABLE tb_test (age VARCHAR(10), INDEX ix_age (age));

# 조건절에 숫자 타입이 들어가는 경우, 인덱스 사용 불가.
SELECT * FROM tb_test WHERE age = 2;
```

`인덱스 레인지 스캔`을 사용할 수 없어 인덱스 풀 스캔이 수행될 것으로 예상된다.  
옵티마이저가 내부적으로 `age` 컬럼을 숫자 타입으로 바꾼 후 비교하기에 `인덱스 레인지 스캔`이 불가하다.  

```sql
# 문자 타입으로 바꿔주면, 인덱스 레인지 스캔 가능.
SELECT * FROM tb_test WHERE age = '2';
```

### 복합 인덱스의 순서, MySQL8.0 버전부터는 개선되었다. 

복합인덱스가 `COL1, COL2, COL3, COL4` 로 되어있을 때, 순서에 상관없이 인덱스를 사용할 수 있게 개선되었다.  
옵티마이저가 순서에 상관없이 인덱스를 사용하나 비교 조건이 중요하다.  
동등 비교 조건은 무리없이 사용 가능하다. 

`COL3 = ? AND COL4 = ? AND COL1 = ? AND COL2` 이런 경우는 사용할 수 있지만,
`COL3 <= ? AND COL4 = ? AND COL1 = ? AND COL2` 이런 경우에는 `COL1, COL2`는 인덱스로 처리되고  
`COL3`은 인덱스로 처리할 수 없으므로 범위 조건 이후의 컬럼인 `COL4`도 인덱스로 처리할 수 없다.  

### GROUP BY 절의 인덱스 사용

`GROUP BY`는 인덱스의 순서가 중요하다.  
복합인덱스가 `COL1, COL2, COL3, COL4` 로 되어있을 때, 순서로 명시해야 인덱스를 사용할 수 있다.  

- `GROUP BY` 사용 불가 패턴
  
```sql
... GROUP BY COL2, COL1
... GROUP BY COL1, COL3, COL2
... GROUP BY COL1, COL3
... GROUP BY COL1, COL2, COL3, COL4, COL5
```

1. 복합 인덱스와 `GROUP BY` 컬럼의 순서가 일치하지 않아 사용 불가
2. 복합 인덱스와 `GROUP BY` 컬럼의 순서가 일치하지 않아 사용 불가
3. COL2 가 누락되어 사용 불가
4. 마지막 COL5 는 복합 인덱스에서 정의되지 않았으므로 사용 불가

- `GROUP BY` 사용 가능 패턴

```sql
... GROUP BY COL1
... GROUP BY COL1, COL2
... GROUP BY COL1, COL2, COL3
... GROUP BY COL1, COL2, COL3, COL4
```

`Query`의 실행 순서를 보자, `WHERE` 절부터 시작되는 것을 알 수 있는데 `WHERE` 절에서 COL1이 동등 비교 조건으로 사용되면   
`GROUP BY`에서 COL1 이 빠진 인덱스를 사용 가능하다.    

- `WHERE` 절에서 사용, `GROUP BY`에서 사용가능한 패턴

```sql
... WHERE COL1=? ... GROUP BY COL2, COL3
... WHERE COL1=? AND COL2=? ... GROUP BY COL3, COL4
... WHERE COL1=? AND COL2=? AND COL=3? ... GROUP BY COL4
```

`GROUP BY`절을 고쳐도 똑같은 결과가 조회된다면 `WHERE` 절과 `GROUP BY` 절이 모두 인덱스를 사용할 수 있는 쿼리이다.  

### ORDER BY 절의 인덱스 사용

MySQL에서 `GROUP BY`와 `ORDER BY`는 처리 방법이 매우 비슷하다. 그래서 `GROUP BY`와 `ORDER BY`의 요건이 흡사하다.  
정렬되는 각 컬럼의 오름차순(ASC), 내림차순(DESC) 옵션이 인덱스와 같거나 정반대인 경우에만 사용할 수 있다는 것이다.  
MySQL의 모든 인덱스는 오름차순으로만 정렬돼 있기 때문에 `ORDER BY` 절의 모든 컬럼이 오름차순이거나 내림차순일 때만 사용할 수 있다.  

복합 인덱스 `COL1, COL2, COL3, COL4` 는 이미 ASC로 정렬되어 있다. `ORDER BY`에 정렬을 명시하지 않으면 기본적으로 ASC 쿼리로 해석한다.  

- `ORDER BY` 인덱스 사용 불가

```sql
... ORDER BY COL2, COL3
... ORDER BY COL1, COL3, COL2
... ORDER BY COL1, COL2 DESC, COL3
... ORDER BY COL1, COL3
... ORDER BY COL1, COL2, COL3, COL4, COL5
```

1. 복합 인덱스와 `ORDER BY` 컬럼의 순서가 일치하지 않아 사용 불가 (COL1 누락)
2. 복합 인덱스와 `ORDER BY` 컬럼의 순서가 일치하지 않아 사용 불가 (COL1, COL2, COL3만 가능)
3. 복합 인덱스는 `ASC` 로 정렬되어 있는데 COL2 를 `DESC`로 찾기 때문에 불가 (COL1, COL2 DESC, COL3... 로 복합 인덱스를 정의하면 사용 가능)
4. 복합 인덱스와 `ORDER BY` 컬럼의 순서가 일치하지 않아 사용 불가 (COL2 누락)
5. 마지막 COL5 는 복합 인덱스에서 정의되지 않았으므로 사용 불가

### WHERE 조건과 ORDER BY(또는 GROUP BY)절의 인덱스 사용

쿼리에 `WHERE`,`GRUOP BY`, `ORDER BY` 중에 한 구문만 들어간다면 사용된 절 하나에만 초점을 맞춰서 인덱스를 사용할 수 있게 튜닝하면 된다.  
일반적으로 사용되는 쿼리에는 `WHERE` 절이 들어가고, 선택적으로 `GRUOP BY`, `ORDER BY` 가 들어가는데,  
`WHERE` 절에 A 인덱스를 사용하고 `GROUP BY`, `ORDER BY`에는 B 인덱스를 사용하도록 쿼리를 실행할 수 없다.  

`WHERE` 절과 `ORDER BY` 절이 같이 사용된 하나의 쿼리 문장은 다음 3가지 중 한 가지 방법으로만 인덱스를 이용한다.  

- `WHERE` 절과 `ORDER BY` 절이 동시에 같은 인덱스 사용: ⭐️⭐️⭐️⭐️⭐️  

두 구문에서 같은 인덱스를 사용하기 때문에 가장 좋은 방법이다.  

- `WHERE` 절만 인덱스 사용: ⭐️⭐️⭐️⭐️  

인덱스를 이용하여 정렬은 하지 못하며, 인덱스를 통해 검색된 결과 레코드를 별도의 정렬 처리 과정(Using Filesort)을 거쳐 정렬을 수행한다.  
`WHERE` 절의 조건에 일치하는 레코드 건수가 적을 때 효율적이다.  

- `ORDER BY` 절만 인덱스 사용: ⭐️⭐️⭐️  

`ORDER BY` 절의 순서대로 인덱스를 읽으면서 레코드 한 건씩 `WHERE` 절의 조건에 일치하는지 비교한다.  
테이블 전체를 스캔하거나 부분적으로만 필터링한다. 반면 정렬은 인덱스에 의해 이미 되어 있으므로 빠르지만, 필터링 비용이 크면 전체적으로 성능은 낮아진다.  

`WHERE` 절에서 동등 비교 조건으로 비교된 컬럼과 `ORDER BY` 절에 명시된 컬럼이 순서대로 빠짐없이 인덱스 컬럼의 왼쪽부터 일치해야 한다.  

- 인덱스: `(COL1 ASC, COL2 ASC, COL3 ASC, COL4 ASC)`  

위와 같은 복합 인덱스가 존재한다고 가정하자, 총 4개의 컬럼이 있고 오름차순으로 정렬되어 있다.  

- Query1: `... WHERE COL1 = ? ... ORDER BY COL2 ASC, COL3 ASC`

`WEHRE`절에서 `COL1`을 동등 조건으로 비교하고 이후에 순서대로 `COL2`, `COL3` 컬럼을 `ORDER BY` 에서 사용하므로 복합 인덱스를 사용할 수 있다. ✅  

- Query2: `... WHERE COL1 = ? AND COL2 = ? AND COL3 > ? ... ORDER BY COL3 DESC, COL4 DESC`

# 🎯 벌크 데이터로 실험하기

`WHERE`절에서 `COL1`, `COL2`, `COL3` 컬럼을 순서대로 동등 조건을 비교하고(`COL3`은 인덱스 레인지 스캔),  
`ORDER BY` 에서 다시 순서대로 `COL3 DESC`, `COL4 DESC`를 정렬하므로 인덱스를 사용할 수 있다.✅ 


동등 비교 조건은 정렬해도 의미가 없기 때문에 쿼리 튜닝에서 포인트가 될 수 있다.  

- Query3: `... WHERE COL1 = ? ... ORDER BY COL2 ASC, COL3 ASC`

복합 인덱스를 사용하기 위해서 컬럼의 순서가 중요한데, (MySQL 8.0 새롭게 추가된 인덱스 스킵 스캔 논외.) `COL1 = ?` 상수로 동등 비교를 하기 때문에,  
`... WHERE COL1 = ? ... ORDER BY COL1 ASC, COL2 ASC, COL3 ASC` 로 수정해도 된다.  
`GROUP BY` or `ORDER BY`가 인덱스를 사용할 수 있을지 없을지 애매한 경우 위와 같이 튜닝할 수 있다.  

- `... WHERE COL1 > ? ORDER BY COL1, COL2, COL3`: 사용 가능✅
- `... WHERE COL1 > ? ORDER BY COL2, COL3`: 사용 불가❌
- `... WHERE COL1 = ? ORDER BY COL3`: 사용 불가❌
- `... WHERE COL1 > ? ORDER BY COL2`: 사용 불가❌
- `... WHERE COL1 IN (?, ?, ?, ?) ORDER BY COL2`: 사용 불가❌

`WHERE` 절과 `GROUP BY` 절의 조합에서 인덱스의 사용 여부를 판단하는 능력은 상당히 중요하다.  

### GROUP BY 절과 ORDER BY 절의 인덱스 사용

`GROUP BY` + `ORDER BY` 절이 동시에 같은 인덱스를 사용하게 하려면 `GROUP BY` == `ORDER BY` 가 성립해야 된다.  
`GRUOP BY`와 `ORDER BY`가 같이 사용된 쿼리에서는 둘 중 하나라도 인덱스를 이용할 수 없을 때는 둘 다 인덱스를 사용하지 못한다.  

`WHERE`, `GROUP BY`, `ORDER BY` 에서 인덱스 사용은 다음과 같다.  
인덱스 사용여부는 ⭕️, ❌로 표기한다. 중요한 부분은 `WHERE` 절이 인덱스를 사용할 수 있느냐인데,  
이를 기준으로 다음과 같은 경우의 수를 뽑을 수 있다.  

- `WHERE`: ⭕️  `GROUP BY`: ⭕️ `ORDER BY`: ⭕️ = 모두 인덱스 사용
- `WHERE`: ⭕️  `GROUP BY`: ❌ `ORDER BY`: ❓ = WHERE 절만 인덱스 사용
- `WHERE`: ⭕️  `GROUP BY`: ⭕️ `ORDER BY`: ❌ = WHERE 절만 인덱스 사용



- `WHERE`: ❌  `GROUP BY`: ❌ `ORDER BY`: ❓ = 인덱스 사용 불가
- `WHERE`: ❌  `GROUP BY`: ⭕️ `ORDER BY`: ❌ = 인덱스 사용 불가
- `WHERE`: ❌  `GROUP BY`: ⭕️ `ORDER BY`: ⭕️ = GROUP BY, ORDER BY 절만 인덱스 사용   

### WHERE 절의 비교 조건 사용 시 주의사항

`NULL` 값이 포함된 레코드도 인덱스로 관리된다. 하나의 값으로 관리하는 것을 의미하지만, SQL 표준에서는 `NULL`의 정의를 비교할 수 없는 값이다.  
동등 비교는 불가능하고, `IS NULL(또는 "<=>")` 연산자를 사용해야 한다.  

- `...WHERE col_1 IS NULL;`: 인덱스 레인지 스캔
- `...WHERE ISNULL(col_1 );`: 인덱스 레인지 스캔
- `...WHERE ISNULL(col_1 )=1;`: 인덱스 사용 불가(인덱스 풀 스캔)
- `...WHERE ISNULL(col_1 )=true;`: 인덱스 사용 불가(인덱스 풀 스캔)

### 문자열이나 숫자 비교

그 타입에 맞는 상숫값을 사용하라. (`col_1`: 숫자, `col_2`: 문자열)  

- `...WHERE col_1 = 1000`: 인덱스 사용
- `...WHERE col_1 = '1000'`: 문자열 상숫값을 숫자 타입으로 바꾸지만 특별한 성능 저하는 없다.
- `...WHERE col_2 = 1000`: ❌ 옵티마이저는 숫자 타입에 우선 순위를 두어 `col_2` 컬럼의 데이터를 바꾸기에 인덱스 사용 불가
- `...WHERE col_2 = '1000'`: 인덱스 사용

타입에 주의!  

### 날짜 비교

`col_1`의 타입이 `DATE`, `DATETIME` 이고 인덱스가 있을 때.  
`DATE` or `DATETIME` 과 문자열을 비교할 땐 `문자열 -> DATETIME` 타입의 값으로 변환해 비교를 수행한다.  
함수를 호출해 `STR_TO_DATE('2025-04-02', '%Y-%m-%d')`로 바꾸지 않아도 내부적으로 변환을 수행한다.  

반대로 `DATE_FORMAT(col_1, '%Y-%m-%d')` 함수를 호출하면 인덱스 컬럼의 값이 변경되므로 인덱스를 사용할 수 없다.  
`DATE_ADD()` 함수나 `DATE_SUB()` 함수처럼 날짜 타입의 값을 더하거나 빼는 함수를 호출해 비교해도 인덱스를 사용할 수 없다.  

### DATE 와 DATETIME 비교

`DATETIME` 값에서 시간 부분만 떼어 내면 `DATE` 와 비교할 수 있는데, 함수를 호출하더라도 인덱스 영향은 없다.  
`STR_TO_DATE('2025-04-02', '%Y-%m-%d')`를 호출하더라도 말이다. 하지만 `00:00:00` 시간으로 설정되므로 결과만 주의하자.  

### DATETIME 과 TIMESTAMP 비교

`DATE` or `DATETIME` 값과 `TIMESTAMP` 를 비교하면(타입 변환 없이) 마치 인덱스 레인지 스캔을 잘 쓰는 것처럼 보이지만,  
실행계획도 그렇게 보이지만, 아니다.  

`col_1` : `DATE` or `DATETIME` 일 때,  
`col_2` : `TIMESTAMP` 일 때,  

- `...WHERE col_1 > FROM_UNIXTIME(?)`: `FROM_UNIXTIME()` 함수를 호출해 `TIMESTAMP` -> `DATETIME` 으로 변환
- `...WHERE col_2 > UNIX_TIMESTAMP(?)`: `UNIX_TIMESTAMP()` 함수를 호출해 `DATETIME` -> `TIMESTAMP` 으로 변환

`DATE`와 `DATETIME` 의 비교는 위에서 언급했듯, 인덱스 영향이 없다.  

# 🎯 벌크 데이터로 실험하기: 인덱스 컬럼의 타입과 비교 상숫값의 타입을 비교해보기

### Short-Circuit evaluation

`Short-Circuit evaluation(단락 평가)`은 조건문을 계산할 때 결과가 확정되면, 나머지는 계산하지 않는다.  

- `if (A and B) {...}` : A 가 false 라면 B 는 호출되지 않는다.
- `if (A or B) {...}`  : A 가 true 라면 B 는 호출되지 않는다.

조건문의 결과가 (다소 엉뚱하더라도) 다음과 같을 때.  

1. `...WHERE col_1 > 1000`: 결과가 30만건 
2. `...WHERE col_1 < 500` : 결과가 0건

- `...WHERE col_1 > 1000 AND col_1 < 500`: 30만건을 가져온 후 500 미만인지 체크한다. (결과값은 0개)
- `...WHERE col_1 > 1000 AND col_1 < 500`: 500 미만인지 먼저 체크하므로 1000초과는 체크 안함. (결과값은 0개)

`WHERE` 구문의 순서는 `Short-Circuit evaluation(단락 평가)` 과 밀접한 관계가 있으므로 쿼리 성능을 향상시키는데 도움을 준다.  

하지만, 인덱스가 추가되면 이야기는 달라진다.  
MySQL 8.0 부터는 옵티마이저가 `WHERE` 절에 사용된 복합 인덱스의 명시된 컬럼의 순서가 달라도 내부적으로 최적화되어 잘 사용한다는 사실을 알고 있다.  
`Short-Circuit evaluation(단락 평가)`의 예상과 달리 인덱스 컬럼을 먼저 사용하여 최적화를 진행하게 된다.  

```sql
...
WHERE col_1 = ?
    AND col_2 = ?
    AND col_3 = ?
    AND col_4 = ?
```

`col_2` 가 인덱스가 있는 커럶이라면 `col_2` 부터 평가하고 이후에 순서대로 평가하게 된다.  

- 서브쿼리의 세미 조인1

```sql
SELECT *
FROM employees e
WHERE e.first_name = ?
    AND e.last_name = ?
    AND EXISTS (
        SELECT 1 FROM salaries s
        WHERE s.emp_no = e.emp_no AND s.to_date > ?
        GROUP BY s.salary HAVING COUNT(*) > 1
    )
```

- 서브쿼리의 세미 조인2

```sql
SELECT *
FROM employees e
WHERE e.first_name = ?
    AND EXISTS (
        SELECT 1 FROM salaries s
        WHERE s.emp_no = e.emp_no AND s.to_date > ?
        GROUP BY s.salary HAVING COUNT(*) > 1
    )
    AND e.last_name = ?
```

1. first_name 인덱스를 먼저 사용하여 범위를 최소화를 한다.
2. `WHERE` 절에 서브쿼리 조건 먼저 평가한다.
3. 이후 e.last_name 을 평가한다.



| Variable_name     | 1번 쿼리 VALUE | 2번 쿼리 VALUE |
|-------------------|-------------|-------------|
| Handler_read_key  | 9           | 1807        | 
| Handler_read_next | 247         | 2454        | 
| Handler_rnd_next  | 8           | 1806        | 
| Handler_write     | 7           | 1573        | 

두 쿼리의 결과는 동일하지만 내부적으로 수행하는 동작에 대해서는 큰 차이가 있다.  
복잡한 연산을 먼저하는 첫 번째 쿼리가 더 비효율적이다. 이는 *SHOW STATUS LIKE 'Handler%';* 결과를 통해 상태 값들을 볼 수 있는데,  

`first_name` 컬럼은 인덱스가 있으므로 먼저 평가하게 되고, 나머지 조건들은 순서대로 평가하게 된다.  
EXISTS(서브쿼리) 조건을 평가하면서 상당히 많은 레코드를 읽고, 임시테이블에 쓰는 작업을 한 것을 확인할 수 있다.

인덱스를 사용할 수 있는 컬럼은 순서에 상관없이 먼저 평가되므로 고려하지 않아도 된다.  
가장 많은 레코드들을 인덱스로 읽어온 후 비교적 덜 복잡한 연산을 우선순위로 두어 최적화를 진행할 수 있다.  

#### LIMIT

- `SELECT * FROM tb_1 LIMIT 0, 10`
- `SELECT col_1 FROM tb_1 GROUP BY col_1 LIMIT 0, 10`
- `SELECT DISTINCT col_1 FROM tb_1 LIMIT 0, 10`
- `SELECT * FROM WHERE col_2 BETWEEN ? AND ? ORDER BY col_1 tb_1 LIMIT 0, 10`

`GROUP BY`, `ORDER BY` 가 인덱스를 활용하지 못하는 경우에 쿼리 결과는 다음과 같다.  

1. 테이블 풀스캔을 하지만, 스토리지 엔진으로부터 10개의 레코드를 읽어드리는 순간 종료되므로 상당히 빨리 끝날 수 있다.  
2. `GROUP BY` 작업이 모두 완료되고 `LIMIT`을 수행하므로 `LIMIT`이 작업 내용을 크게 줄여주지는 못한다.
3. `DISTINCT` 를 통해 유니크한 값을 읽어드리다 10개가 채워지면 종료되므로 (1번 처럼) 작업 성능을 크게 줄여줄 수 있다.
4. `WHERE` 절에 해당하는 모든 컬럼을 읽은 후 `ORDER BY` 정렬을 하게 된다. 정렬 중 10건이 완성되는 순간 쿼리가 종료되지만 2번처럼 작업량을 크게 줄여주지는 못한다.

`GROUP BY`, `ORDER BY`에서의 `LIMIT`은 대체적으로 크진 않지만, 작게나마 성능 향상은 있다고 볼 수 있다.  
만약 `GROUP BY`, `ORDER BY` 가 인덱스를 활용할 수 있다면 `LIMIT`을 통해 꼭 필요한 만큼의 레코드를 읽으므로 성능 향상에 도움이 된다.  

`LIMIT`에 `Offset` 쿼리를 줄 때 수치가 매우 커질 수 있다.  
`... LIMIT 200000, 10` 200010건의 레코드를 읽은 후 200000건은 버리고 마지막 10건만 리턴하게 된다.  

성능을 고려한다면 `WHERE` 절에 인데스 컬럼을 이용할 수 있다.  

```sql
-- 첫 페이지 조회
SELECT * FROM tb_1 ORDER BY col_1 LIMIT 0, 10;

-- 두 번째 페이지 조회
SELECT * FROM tb_1 
WHERE col_1 >= ? AND NOT (col_1 = ? AND col_2 = ?) -- 이전 페이지에서 제외 조건 
ORDER BY col_1 LIMIT 0, 10;

-- 마지막 페이지 조회
SELECT * FROM tb_1 
WHERE col_1 >= ? AND NOT (col_1 = ? AND col_2 = ?) -- 이전 페이지에서 제외 조건 
ORDER BY col_1 LIMIT 0, 10;
 
```

`WHERE` 구문을 통해 인덱스가 걸려있는 컬럼을 활용해 제외 조건을 추가해주면 원하는 위치에서 10개만 읽는 형태의 쿼리를 작성할 수 있다.  
단, 데이터 누락, 중복이 발생할 수 있으므로 `WHERE` 조건에 들어갈 컬럼 작성에 주의해야 한다.  

#### COUNT()

`COUNT(*)` 를 사용하더라도 모든 레코드를 가져오라는 의미가 아니라 레코드 자체를 의미하는 것이다.  
MyISAM 에서는 테이블의 메타 정보에 전체 레코드 건수를 관리하므로 `SELECT COUNT(*)` 처럼 조건없는 쿼리는 결과를 바로 얻을 수 있다.  
InnoDB 에서는 테이블을 직접 읽어야 되므로 큰 테이블에서 COUNT() 함수를 주의해야 한다.  

대략적인 테이블 건수로 해결이 된다면 `SHOW TABLE STATUS` 명령으로 통계 정보를 참조하는 것도 좋은 방법이다.  

`COUNT` 쿼리를 사용할 때 `ORDER BY`, `LEFT JOIN` 과 같은 레코드 건수를 가져오는 것과는 무관한 작업을 포함시키면 안 된다.  
MySQL 8.0 부터는 `COUNT(*)` 쿼리에 적용된 `ORDER BY` 절은 옵티마이저가 무시하도록 개선됐다.  

`COUNT` 쿼리는 서버에 부하를 많이 유발한다. 가령 게시물 목록을 보여주는  
`SELECT COUNT(*) FROM articles WHERE board_id=1` 쿼리는 전체 테이블을 읽어야 하므로  
게시글 전체 수를 보여주기 위해 다른 방법을 검토해볼만하다.  