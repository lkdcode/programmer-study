document.addEventListener('DOMContentLoaded', () => {
    const handleSignup = function(event) {
        event.preventDefault();

        const username = document.querySelector('.form.sign-up input[placeholder="Username"]').value;
        const email = document.querySelector('.form.sign-up input[placeholder="Email"]').value;
        const password = document.querySelector('.form.sign-up input[placeholder="Password"]').value;
        const confirmPassword = document.querySelector('.form.sign-up input[placeholder="Confirm password"]').value;

        const data = {
            username: username,
            email: email,
            password1: password,
            password2: confirmPassword
        };

        fetch('/sign-up', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.status === 200) {
                Swal.fire({
                    icon: "success",
                    title: "회원 가입에 성공했습니다.",
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    window.location.href = '/'
                });
            } else if (response.status === 400) {
                Swal.fire({
                    icon: "error",
                    title: "회원 가입에 실패했습니다.",
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        })
        .catch((error) => {
            Swal.fire({
                position: "top-end",
                icon: "error",
                title: "회원 가입에 실패했습니다.",
                showConfirmButton: false,
                timer: 1500
            });
        });
    };

    const signupButton = document.querySelector('.form.sign-up button');
    const usernameInput = document.querySelector('.form.sign-up input[placeholder="Username"]');
    const emailInput = document.querySelector('.form.sign-up input[placeholder="Email"]');
    const passwordInput = document.querySelector('.form.sign-up input[placeholder="Password"]');
    const confirmPasswordInput = document.querySelector('.form.sign-up input[placeholder="Confirm password"]');

    usernameInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            handleSignup(event);
        }
    });

    emailInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            handleSignup(event);
        }
    });

    passwordInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            handleSignup(event);
        }
    });

    confirmPasswordInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            handleSignup(event);
        }
    });

    signupButton.addEventListener('click', handleSignup);
});
