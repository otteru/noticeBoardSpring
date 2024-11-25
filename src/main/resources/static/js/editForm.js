document.getElementById('editForm').addEventListener('submit', function(e){
    e.preventDefault(); // 폼의 기본 제출 동작을 막습니다

    if(confirm("수정하시겠습니까?")) {
        const postId = document.querySelector("#postId").value;
        const titleValue = document.getElementById('title').value;
        const bodyValue = document.getElementById('body').value;

        fetch(`/noticeBoard/posts/${postId}`, {
            method: "PATCH",
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify({
                id: postId,
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
                alert("글이 성공적으로 수정되었습니다.");
                window.location.href = `/noticeBoard/posts/${postId}`;
            })
            .catch(error => {
                console.error('Error:', error);
                if (error.errorDetails && error.errorDetails.length > 0) {
                    error.errorDetails.forEach(err => {
                        let errorDiv = document.querySelector(`.field-error.${err.field}`);
                        let errorInput = document.querySelector(`.form-control.${err.field}`);
                        if (errorDiv) {
                            errorDiv.textContent = err.message;
                            errorInput.classList.add("field-error");
                        }
                    });
                } else {
                    alert('글 저장 중 오류가 발생했습니다: ' + (error.message || '알 수 없는 오류'));
                }
            });
    }
});