package chap07.step2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRegisterTest {

    private final StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();
    private final MemoryUserRepository fakeRepository = new MemoryUserRepository();
    private final SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();
    private UserRegister userRegister;

    @BeforeEach
    void setUp() {
        this.userRegister = new UserRegister(stubWeakPasswordChecker, fakeRepository, spyEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPasswordTest() {
        stubWeakPasswordChecker.setWeak(true);

        assertThatThrownBy(() -> {
            userRegister.register("id", "pw", "email");
        });
    }

    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    @Test
    void dupIdExists() {
        fakeRepository.save(new User("id", "pw", "email@email.com"));

        assertThatThrownBy(() ->
                userRegister.register("id", "pw", "email@email.com"));
    }

    @DisplayName("같은 ID가 없으면 가입 성공")
    @Test
    void noDupId_RegisterSuccess() {
        userRegister.register("id", "pw", "email");

        final User savedUser = fakeRepository.findById("id");
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getPw()).isEqualTo("pw");
        assertThat(savedUser.getEmail()).isEqualTo("email");
    }


    @DisplayName("가입하면 메일을 전송함")
    @Test
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pw", "email@email.com");

        assertThat(this.spyEmailNotifier.isCalled()).isTrue();
        assertThat(spyEmailNotifier.getEmail()).isEqualTo("email@email.com");
    }

}
