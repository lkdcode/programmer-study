package gof.chain_of_responsibility.step3;

class Application {

    public static void main(String[] args) {
        final ConnectionMiddleware connectionMiddleware = ConnectionMiddleware.link(
                new MariaDBConnection(),
                new MongoDBConnection(),
                new PostgreSQLDBConnection(),
                new MongoDBConnection(),
                new PostgreSQLDBConnection(),
                new MongoDBConnection(),
                new PostgreSQLDBConnection(),
                new MongoDBConnection(),
                new PostgreSQLDBConnection(),
                new PostgreSQLDBConnection()
        );

        connectionMiddleware.connection();
    }
}
