(function () {
    'use strict'

    window.addEventListener('load', function () {
        var forms = document.getElementsByClassName('needs-validation')

        Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    }, false)
}())

function openRegisterModal() {
    $('#loginModal').modal('hide');
    $('#registerModal').modal('show');
    removeValidity()
}

function openLoginModal() {
    $('#registerModal').modal('hide');
    $('#loginModal').modal('show');
    removeValidity()
}

function removeValidity() {
    var forms = document.getElementsByClassName('needs-validation');
    Array.prototype.filter.call(forms, function (form) {
        form.classList.remove('was-validated');
    });
}