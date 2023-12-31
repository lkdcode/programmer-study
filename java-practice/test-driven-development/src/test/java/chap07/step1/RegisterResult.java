package chap07.step1;

class RegisterResult {
    private final CardValidity cardValidity;

    public RegisterResult(CardValidity cardValidity) {
        this.cardValidity = cardValidity;
    }

    public static RegisterResult error(CardValidity validity) {
        return new RegisterResult(validity);
    }

    public static RegisterResult success() {
        return new RegisterResult(CardValidity.VALID);
    }

    public CardValidity getValidity() {
        return this.cardValidity;
    }
}
