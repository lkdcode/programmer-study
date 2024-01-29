package gof.chain_of_responsibility.step3;

public class MongoDBConnection extends ConnectionMiddleware {
    @Override
    void connection() {
        System.out.println("---Mongo DB Connection---");
        nextConnection();
    }
}
