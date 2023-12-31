package chap07.step1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class AutoDebitRegister_Fake_Test {
    private AutoDebitRegister register;
    private StubCardNumberValidator validator;

    private MemoryAutoDebitInfoRepository repository;

    @BeforeEach
    void setUp() {
        this.validator = new StubCardNumberValidator();
        this.repository = new MemoryAutoDebitInfoRepository();
        this.register = new AutoDebitRegister(validator, repository);
    }

    @Test
    void alreadyRegistered_InfoUpdated() {
        repository.save(
                new AutoDebitInfo("user1", "111222333444", LocalDate.now())
        );

        final AutoDebitReq req = new AutoDebitReq("user1", "123456789012");
        final RegisterResult result = this.register.register(req);

        final AutoDebitInfo saved = repository.findOne("user1");

        assertThat(saved.getCardNumber())
                .isEqualTo("123456789012");
    }

    @Test
    void notYetRegistered_newInfoRegistered() {
        final AutoDebitReq req = new AutoDebitReq("user12", "71234123412341234");
        final RegisterResult result = this.register.register(req);

        final AutoDebitInfo saved = repository.findOne("user12");

        assertThat(saved.getCardNumber())
                .isEqualTo("71234123412341234");
    }
}









