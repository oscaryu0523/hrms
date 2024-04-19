
window.addEventListener('load', function() {
    console.log("執行權限不足");
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    console.log(error);
    if (error === '權限不足') {
        alert('權限不足');
    }
});

