package chap07.step1;


import java.time.LocalDate;

class AutoDebitInfo {
    private final String userName;
    private LocalDate now;
    private String cardNumber;

    public AutoDebitInfo(String userName, String cardNumber, LocalDate now) {
        this.userName = userName;
        this.cardNumber = cardNumber;
        this.now = now;
    }

    public void changeCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUserId() {
        return this.userName;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }
}
