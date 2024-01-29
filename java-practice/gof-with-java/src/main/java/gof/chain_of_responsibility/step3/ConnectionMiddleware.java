package gof.chain_of_responsibility.step3;

abstract class ConnectionMiddleware {
    private ConnectionMiddleware next;

    public static ConnectionMiddleware link(ConnectionMiddleware first, ConnectionMiddleware... link) {
        ConnectionMiddleware head = first;

        for (ConnectionMiddleware chain : link) {
            head.next = chain;
            head = chain;
        }

        return first;
    }

    abstract void connection();

    public void nextConnection() {
        if (next != null) next.connection();
    }
}
