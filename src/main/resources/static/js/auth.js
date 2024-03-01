function openRegisterModal() {
    $('#loginModal').modal('hide');
    $('#registerModal').modal('show');
}

function openLoginModal() {
    $('#registerModal').modal('hide');
    $('#loginModal').modal('show');
}
/*
function registerNewUser() {
    let firstname = document.getElementById("firstname").value
    let lastname = document.getElementById("lastname").value
    let email = document.getElementById("email").value
    let password = document.getElementById("password").value
    let phone = document.getElementById("phone").value

    // let phoneRegex = /^0\d{9}$/
    if (firstname === ""
        || lastname === ""
        || email === ""
        || password === ""
        || phone === ""
       /!* || !phoneRegex.test(phone)*!/) {
        return
    }

    let requestBody = {
        firstname: firstname,
        lastname: lastname,
        email: email,
        password: password,
        phone: phone
    }

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    }

    let errDiv = document.getElementById("err")

    let uri = "http://localhost:8080/api/v1/auth/register"
    fetch(uri, requestOptions)
        .then(res => res.json())
        .then(data => {
            console.log(data)
            if (data === true) {
                errDiv.innerHTML = ''
                errDiv.innerHTML += "<span>Юзер успешно зареган</span>"
                alert("Юзер успешно зарега")
            } else {
                errDiv.innerHTML = ''
                errDiv.innerHTML += "<span>Юзер уже зареган</span>"
                alert("Юзер уже зареган")
            }
        })

}*/

