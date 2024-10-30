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
            // .then(data => {
            //     if(response.status == 400) {
            //         $.each(error : data.errors) {
            //             let errorDiv = document.querySelector("field-error " + data.error);
            //             errorDiv.textContent = data.error.message;
            //         }
            //     }
            // })
            .catch(error => {
                console.error('Error:', error);
                alert('글 수정 중 오류가 발생했습니다: ' + error.message);
            });
    }

})


// }).done(function () {
//     alert('로그인 되었습니다.');
//     window.location.href = '/';
// }).fail(function (response) {
//     console.log(response);
//     if (response.status === 400 && response.responseJSON.errorDetails != null) {
//         $.each(response.responseJSON.errorDetails, function (index, errorDetails) {
//             let errorSpan =  document.querySelector(".error-" + errorDetails.field);
//             errorSpan.textContent = errorDetails.message;
//         });
//     } else {
//         alert(response.responseJSON.message);
//     }



//
// .then(response => response.json())
// .then(data => {
//     if(!response.ok()){
//         if(response.status === 400){
//             sessionStorage.setItem('errors', JSON.stringify(data.errors));
//             window.location.href = data.redirect;
//         }else
//             throw new Error("서버 응답이 실패했습니다.");
//     }else{
//         alert("글이 성공적으로 저장되었습니다.");
//         window.location.href = `/noticeBoard/posts/${data.id}`;
//     })
// }