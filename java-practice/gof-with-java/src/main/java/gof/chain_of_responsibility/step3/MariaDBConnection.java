package gof.chain_of_responsibility.step3;

class MariaDBConnection extends ConnectionMiddleware {
    @Override
    void connection() {
        System.out.println("---Maria DB Connection---");
        nextConnection();
    }
}
