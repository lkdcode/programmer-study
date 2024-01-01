package chap10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

public class UserRegisterTest {

    private PasswordChecker mockPasswordChecker = Mockito.mock(PasswordChecker.class);
    private PasswordChecker passwordChecker;

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPasswordTest_bad() {
        BDDMockito
                .given(mockPasswordChecker.checkPasswordWeak("pw"))
                .willReturn(true);
        //...
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPasswordTest_good() {
        BDDMockito
                .given(mockPasswordChecker.checkPasswordWeak(Mockito.any(String.class)))
                .willReturn(true);
        //...
    }

    @DisplayName("회원 가입시 암호 검사 수행함")
    @Test
    void checkPasswordSignUpTest_bad() {

    }

}
