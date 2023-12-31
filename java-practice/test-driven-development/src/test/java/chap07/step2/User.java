package chap07.step2;

class User {
    private final String id;
    private final String pw;
    private final String email;

    public User(String id, String pw, String email) {
        this.id = id;
        this.pw = pw;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getEmail() {
        return email;
    }
}
