### Break Point

```java

package io.r2dbc.postgresql;

final class PostgresqlConnection implements io.r2dbc.postgresql.api.PostgresqlConnection, Wrapped<Object> {

    @Override
    public PostgresqlStatement createStatement(String sql) {
        Assert.requireNonNull(sql, "sql must not be null"); // <-- BreakPoint
        return new io.r2dbc.postgresql.PostgresqlStatement(this.resources, sql);
    }
}
```

### 설정별 트랜잭션

```text
전체 미설정:
        jOOQ → Pool.create() → 새 커넥션
               (조회 시도 없음)

TransactionAwareConnectionFactoryProxy만 사용:
        jOOQ → Proxy.create() → doGetConnection()
               → forCurrentTransaction() → Context 없음 → 새 커넥션
               (Context 캡처 메커니즘 없음)

TransactionAwareConnectionFactoryProxy + JooqContextAwareConnectionFactory 사용, ReactorSubscriberProvider 미사용
        jOOQ ─(일반 Subscriber)─→ ContextAwareFactory.create()
               → deferContextual → Context 비어있음 → Proxy.create()
               → doGetConnection() → forCurrentTransaction() → Context 없음 → 새 커넥션
               (Context 전파 체인이 jOOQ 내부에서 끊김)

전체 설정: 
        jOOQ ─(CoreSubscriber)─→ ContextAwareFactory.create()
               → deferContextual → Context 있음! → Proxy.create()
               → doGetConnection() → forCurrentTransaction() → 트랜잭션 커넥션 A 반환!
               (모든 체인 연결됨)
```