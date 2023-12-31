package chap07.step1;

class StubCardNumberValidator extends CardNumberValidator {
    private String invalidNo;
    private String theftNo;

    public void setInvalidNo(String invalidNo) {
        this.invalidNo = invalidNo;
    }

    public void setTheftNo(String theftNo) {
        this.theftNo = theftNo;
    }

    @Override
    public CardValidity validate(String cardNumber) {
        if (invalidNo != null && invalidNo.equals(cardNumber)) {
            return CardValidity.INVALID;
        }

        if (this.theftNo != null && this.theftNo.equals(cardNumber)) {
            return CardValidity.THEFT;
        }

        return CardValidity.VALID;
    }
}
