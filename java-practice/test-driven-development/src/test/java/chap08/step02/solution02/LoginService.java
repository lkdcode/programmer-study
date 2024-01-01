package chap08.step02.solution02;

import chap08.step01.problem2.AuthUtil;
import chap08.step01.problem2.Customer;
import chap08.step01.problem2.CustomerRepository;
import chap08.step01.problem2.LoginResult;

class LoginService {
    private String authKey = "someKey";
    private CustomerRepository customerRepository;
    private AuthService authService;

    public LoginService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public LoginResult login(String id, String pw) {
        int resp = authService.authenticate(id, pw);
        if (resp == -1) return LoginResult.badAuthKey();
        if (resp == 1) {
            final Customer c = customerRepository.findOne(id);
            return LoginResult.authenticated(c);
        } else {
            return LoginResult.fail(resp);
        }
    }
}
