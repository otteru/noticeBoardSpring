document.getElementById("loginForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const loginId = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;

    // 현재 URL에서 redirectURL 파라미터 추출
    const urlParams = new URLSearchParams(window.location.search);
    const redirectURL = urlParams.get('redirectURL') || '/';

    // 기존 에러 메시지 초기화
    document.querySelectorAll('.field-error').forEach(el => el.textContent = '');

    // URLSearchParams 객체를 사용하여 쿼리 파라미터 구성
    const queryParams = new URLSearchParams({redirectURL: redirectURL});

    fetch(`/login?${queryParams}`, {
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
            window.location.href = data.redirect;
        })
        .catch(error => {
            console.error('Error:', error);
            if (error.errorDetails && error.errorDetails.length > 0) {
                error.errorDetails.forEach(err => {
                    console.log(err.field);
                    if(err.field) { // 필드 오류 처리
                        let errorDiv = document.querySelector(`.error-message.${err.field}`);
                        let errorInput = document.querySelector(`.form-control.${err.field}`);
                        if (errorDiv) {
                            //console.log('errorDiv: ', errorDiv);
                            //console.log('errorInput: ', errorInput);
                            errorDiv.textContent = err.message;
                            errorDiv.classList.add("field-error");
                            errorInput.classList.add("field-error");
                        }
                    } else { // 글로벌 오류 처리
                        console.log("global error");
                        let errorDiv = document.querySelector(`.global-error`);
                        if(errorDiv) {
                            errorDiv.textContent = err.message;
                            errorDiv.classList.add("field-error");
                        }
                    }
                });
            } else {
                alert('글 저장 중 오류가 발생했습니다: ' + (error.message || '알 수 없는 오류'));
            }
        });
});