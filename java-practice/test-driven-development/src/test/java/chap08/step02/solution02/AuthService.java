package chap08.step02.solution02;

import chap08.step01.problem2.AuthUtil;

class AuthService {
    private String authKey = "someKey";

    public int authenticate(String id, String pw) {
        boolean authorized = AuthUtil.authorize(authKey);
        if (authorized) {
            return AuthUtil.authenticate(id, pw);
        } else {
            return -1;
        }
    }
}
