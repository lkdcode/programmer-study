document.addEventListener('DOMContentLoaded', () => {
    const handleSignin = function(event) {
        event.preventDefault();

        const email = document.querySelector('.form.sign-in input[placeholder="Email"]').value;
        const password = document.querySelector('.form.sign-in input[placeholder="Password"]').value;

        const data = {
            email: email,
            password: password
        };

        fetch('/sign-in', {
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
                    title: "로그인에 성공했습니다.",
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    window.location.href = '/'
                });
            } else if (response.status === 400) {
                Swal.fire({
                    icon: "error",
                    title: "로그인에 실패했습니다.",
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    window.location.href = '/'
                });
            }
        })
        .catch((error) => {
            Swal.fire({
                icon: "error",
                title: "로그인에 실패했습니다.",
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                window.location.href = '/'
            });
        });
    };

    const signupButton = document.querySelector('.form.sign-in button');
    const emailInput = document.querySelector('.form.sign-in input[placeholder="Email"]');
    const passwordInput = document.querySelector('.form.sign-in input[placeholder="Password"]');

    emailInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            handleSignin(event);
        }
    });

    passwordInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            handleSignin(event);
        }
    });

    signupButton.addEventListener('click', handleSignin);
});