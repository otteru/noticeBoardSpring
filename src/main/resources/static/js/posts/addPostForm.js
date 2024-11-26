document.getElementById("addForm").addEventListener("submit", function(e) {
    e.preventDefault();

    if(confirm("제출하시겠습니까?")) {
        const titleValue = document.getElementById('title').value;
        const bodyValue = document.getElementById('body').value;

        // 기존 에러 메시지 초기화
        document.querySelectorAll('.field-error').forEach(el => el.textContent = '');

        fetch("/noticeBoard/posts", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify({
                title: titleValue,
                body: bodyValue
            })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(errorData => Promise.reject(errorData));
                }
                return response.json();
            })
            .then(data => {
                alert("글이 성공적으로 저장되었습니다.");
                window.location.href = `/noticeBoard/posts/${data.id}`;
            })
            .catch(error => {
                console.error('Error:', error);
                if (error.errorDetails && error.errorDetails.length > 0) {
                    error.errorDetails.forEach(err => {
                        let errorDiv = document.querySelector(`.error-message.${err.field}`);
                        let errorInput = document.querySelector(`.form-control.${err.field}`);
                        if (errorDiv) {
                            console.log('errorDiv: ', errorDiv);
                            console.log('errorInput: ', errorInput);
                            errorDiv.textContent = err.message;
                            errorDiv.classList.add("field-error");
                            errorInput.classList.add("field-error");
                        }
                    });
                } else {
                    alert('글 저장 중 오류가 발생했습니다: ' + (error.message || '알 수 없는 오류'));
                }
            });
    }
});