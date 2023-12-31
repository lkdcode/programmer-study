package chap07.step1;

import java.time.LocalDate;

class AutoDebitRegister {
    private CardNumberValidator validator;
    private AutoDebitInfoRepository repository;

    public AutoDebitRegister(CardNumberValidator validator, AutoDebitInfoRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public RegisterResult register(AutoDebitReq req) {
        final CardValidity validity = validator.validate(req.getCardNumber());

        if (validity != CardValidity.VALID) {
            return RegisterResult.error(validity);
        }

        final AutoDebitInfo info = repository.findOne(req.getUserId());

        if (info != null) {
            info.changeCardNumber(req.getCardNumber());
        } else {
            final AutoDebitInfo newInfo = new AutoDebitInfo(req.getUserId(), req.getCardNumber(), LocalDate.now());
            repository.save(newInfo);
        }

        return RegisterResult.success();
    }
}
