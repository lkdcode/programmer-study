package chap07.step1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AutoDebitRegister_Stub_Test {
    private AutoDebitRegister register;
    private StubCardNumberValidator stubValidator;
    private StubAutoDebitInfoRepository stubRepository;

    @BeforeEach
    void setUp() {
        this.stubValidator = new StubCardNumberValidator();
        this.stubRepository = new StubAutoDebitInfoRepository();
        this.register = new AutoDebitRegister(stubValidator, stubRepository);
    }

    @Test
    void invalidCard() {
        this.stubValidator.setInvalidNo("4111122223333");
        final AutoDebitReq req = new AutoDebitReq("user1", "4111122223333");
        final RegisterResult result = register.register(req);

        assertThat(result.getValidity()).isEqualTo(CardValidity.INVALID);
    }

    @Test
    void theftCard() {
        stubValidator.setTheftNo("41234567890123456");
        AutoDebitReq req = new AutoDebitReq("user1", "41234567890123456");
        RegisterResult result = this.register.register(req);

        assertThat(result.getValidity()).isEqualTo(CardValidity.THEFT);
    }

}
