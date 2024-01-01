package chap07.step2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserRegister_Mock_Test {

    private final StubWeakPasswordChecker mockPasswordChecker = mock(StubWeakPasswordChecker.class);
    private final MemoryUserRepository mockFakeRepository = mock(MemoryUserRepository.class);
    private final SpyEmailNotifier mockEmailNotifier = mock(SpyEmailNotifier.class);
    private UserRegister userRegister;

    @BeforeEach
    void setUp() {
        this.userRegister = new UserRegister(mockPasswordChecker, mockFakeRepository, mockEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPasswordTest() {
        given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);

        assertThatThrownBy(() -> {
            userRegister.register("id", "pw", "email");
        });
    }

    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    @Test
    void dupIdExists() {
        given(mockFakeRepository.findById("id")).willReturn(new User("id", "pw", "email@email.com"));

        assertThatThrownBy(() ->
                userRegister.register("id", "pw", "email@email.com"));
    }

    @DisplayName("같은 ID가 없으면 가입 성공")
    @Test
    void noDupId_RegisterSuccess() {
        Mockito.when(mockFakeRepository.findById("id"))
                .thenReturn(new User("id", "pw", "email"));

        final User savedUser = mockFakeRepository.findById("id");
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getPw()).isEqualTo("pw");
        assertThat(savedUser.getEmail()).isEqualTo("email");
    }


    @DisplayName("가입하면 메일을 전송함")
    @Test
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pw", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockEmailNotifier)
                .should()
                .sendRegisterEmail(captor.capture());

        final String realEmail = captor.getValue();
        assertThat(realEmail).isEqualTo("email@email.com");
    }

    @DisplayName("회원 가입시 암호 검사 수행함")
    @Test
    void checkPassword() {
        userRegister.register("id", "pw", "email");

        BDDMockito.then(mockPasswordChecker)
                .should()
                .checkPasswordWeak(anyString());
    }

}
