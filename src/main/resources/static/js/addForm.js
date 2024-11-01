document.getElementById("addForm").addEventListener("submit", function(e) {
    e.preventDefault();

    if(confirm("제출하시겠습니까?")) {
        const titleValue = document.getElementById('title').value;
        const bodyValue = document.getElementById('body').value;

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
                if(response.ok){
                    alert("글이 성공적으로 저장되었습니다.");
                }
                return response.json();
            })
            .then(data => {
                if(data.errors) {
                    data.errors.forEach(err => {
                        let fieldName = err.field || "gloabal";
                        let errorDiv = document.querySelector(`.field-error[data-field="${fieldName}"]`);
                        if(errorDiv) {
                            errorDiv.textContent = err.codes[0] || err.defaultMessage;
                        }
                    })
                }else{
                    alert('글 저장 중 오류가 발생했습니다: ' + error.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('글 수정 중 오류가 발생했습니다: ' + error.message);
            });
    }


})

