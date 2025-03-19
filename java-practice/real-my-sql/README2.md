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

