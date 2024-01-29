package gof.chain_of_responsibility.step3;

public class DBConnectionServer {
    private final ConnectionMiddleware connectionMiddleware;

    public DBConnectionServer(ConnectionMiddleware connectionMiddleware) {
        this.connectionMiddleware = connectionMiddleware;
    }

    public void connect() {
        connectionMiddleware.connection();
    }
}
