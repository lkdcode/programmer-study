package chap07.step2;

class UserRegister {

    private final WeakPasswordChecker weakPasswordChecker;
    private final UserRepository userRepository;
    private final EmailNotifier emailNotifier;

    public UserRegister(
            WeakPasswordChecker weakPasswordChecker
            , UserRepository userRepository
            , EmailNotifier emailNotifier) {
        this.weakPasswordChecker = weakPasswordChecker;
        this.userRepository = userRepository;
        this.emailNotifier = emailNotifier;
    }

    public void register(String id, String pw, String email) {
        if (this.weakPasswordChecker.checkPasswordWeak(pw)) {
            throw new WeakPasswordException();
        }

        User user = this.userRepository.findById(id);
        if (user != null) {
            throw new DupIdException();
        }

        this.userRepository.save(new User(id, pw, email));
        this.emailNotifier.sendRegisterEmail(email);
    }

}
