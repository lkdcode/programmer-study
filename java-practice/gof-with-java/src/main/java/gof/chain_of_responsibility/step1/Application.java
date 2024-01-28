package gof.chain_of_responsibility.step1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Application {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final Server SERVER;

    static {
        SERVER = new Server();
        SERVER.register("admin@example.com", "admin_pass");
        SERVER.register("user@example.com", "user_pass");

        Middleware middleware = Middleware.link(
                new ThrottlingMiddleware(2),
                new UserExistsMiddleware(SERVER),
                new RoleCheckMiddleware()
        );

        SERVER.setMiddleware(middleware);
    }

    public static void main(String[] args) throws IOException {
        boolean success = true;

        while (success) {
            System.out.print("Enter email: ");
            final String email = READER.readLine();
            System.out.print("Input password: ");
            final String password = READER.readLine();
            success = SERVER.logIn(email, password);
        }
    }
}
