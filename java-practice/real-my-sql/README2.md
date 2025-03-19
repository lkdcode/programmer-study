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

#### 쿼리 실행 순서를 바꾸고 싶다면 `Inline view`로 풀어낼 수 있다.  

만약 `GROUP BY`전에 `LIMIT`을 적용하고 싶다면 `inline view`로 풀어낼 수 있다.
```sql
SELECT ...
FROM ( `INLINE_VIEW` ) temp_view
ORDER BY ...
```
하지만 `DERIVED` 테이블(임시 테이블)이 발생할 수 있으므로 주의해야 한다.  

#### 인덱스 컬럼의 값을 바꾸면 사용할 수 없다. 

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

#### 복합 인덱스의 순서, MySQL8.0 버전부터는 개선되었다. 

복합인덱스가 `COL1, COL2, COL3, COL4` 로 되어있을 때, 순서에 상관없이 인덱스를 사용할 수 있게 개선되었다.  
옵티마이저가 순서에 상관없이 인덱스를 사용하나 비교 조건이 중요하다.  
동등 비교 조건은 무리없이 사용 가능하다. 

`COL3 = ? AND COL4 = ? AND COL1 = ? AND COL2` 이런 경우는 사용할 수 있지만,
`COL3 <= ? AND COL4 = ? AND COL1 = ? AND COL2` 이런 경우에는 `COL1, COL2`는 인덱스로 처리되고  
`COL3`은 인덱스로 처리할 수 없으므로 범위 조건 이후의 컬럼인 `COL4`도 인덱스로 처리할 수 없다.  













