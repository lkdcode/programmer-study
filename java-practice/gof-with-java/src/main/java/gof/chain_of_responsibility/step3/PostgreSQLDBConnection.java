package gof.chain_of_responsibility.step3;

public class PostgreSQLDBConnection extends ConnectionMiddleware {
    @Override
    void connection() {
        System.out.println("---PostgreSQLDBConnection---");
        nextConnection();
    }
}
