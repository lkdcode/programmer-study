package chap07.step1;


class AutoDebitReq {
    private final String userName;
    private final String cardNumber;

    public AutoDebitReq(String userName, String cardNumber) {
        this.userName = userName;
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getUserId() {
        return this.userName;
    }
}
