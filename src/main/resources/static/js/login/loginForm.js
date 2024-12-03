document.getElementById("loginForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const loginId = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;

    // 기존 에러 메시지 초기화
    document.querySelectorAll('.field-error').forEach(el => el.textContent = '');

    fetch("/login", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: JSON.stringify({
            loginId: loginId,
            password: password
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorData => Promise.reject(errorData));
            }
            return response.json();
        })
        .then(data => {
            window.location.href = `/`;
        })
        .catch(error => {
            console.error('Error:', error);
            if (error.errorDetails && error.errorDetails.length > 0) {
                error.errorDetails.forEach(err => {
                    let errorDiv = document.querySelector(`.error-message.${err.field}`);
                    let errorInput = document.querySelector(`.form-control.${err.field}`);
                    if (errorDiv) {
                        //console.log('errorDiv: ', errorDiv);
                        //console.log('errorInput: ', errorInput);
                        errorDiv.textContent = err.message;
                        errorDiv.classList.add("field-error");
                        errorInput.classList.add("field-error");
                    }
                });
            } else {
                alert('글 저장 중 오류가 발생했습니다: ' + (error.message || '알 수 없는 오류'));
            }
        });



});