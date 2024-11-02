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
                if(response.ok) {
                    return response.json().then(data => {
                        alert("글이 성공적으로 저장되었습니다.");
                        window.location.href = `/noticeBoard/posts/${data.id}`;
                    });
                } else {
                    return response.json();
                }
            })
            .then(data => {
                if(data.errors) {
                    data.errors.forEach(err => {
                        let errorDiv = document.querySelector(`.field-error.${err.field}`);
                        if(errorDiv) {
                            errorDiv.textContent = err.codes[0] || err.defaultMessage;
                        }
                    });
                } else {
                    console.error('Error:', data);
                    alert('글 저장 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('글 저장 중 오류가 발생했습니다: ' + error.message);
            });
    }
});