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
