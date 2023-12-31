package chap07.step2;

class StubWeakPasswordChecker implements WeakPasswordChecker {

    private boolean weak;

    public void setWeak(boolean weak) {
        this.weak = weak;
    }

    public boolean isWeak() {
        return this.weak;
    }

    @Override
    public boolean checkPasswordWeak(String pw) {
        return this.weak;
    }

}
