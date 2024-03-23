let apiUrl = 'http://localhost:8080/api/v1/user';

getAllStaff()

function getAllStaff() {
    fetch(apiUrl + '/workers')
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById("table");
            table.innerHTML = ''
            data.map(worker => {
                console.log(worker)
                table.innerHTML += buildRow(worker)
            })
        })
        .catch(error => console.error('Error fetching data:', error));
}

function buildRow(worker) {
    return `<tr id="${worker.id}"">
        <th scope="row">${worker.id}</th>
        <td>${worker.firstname}</td>
        <td>${worker.lastname}</td>
        <td>${worker.email}</td>
        <td>${worker.role}</td>
        </tr>`
}


function addNewWorker() {
    let htmlfirstname = document.getElementById("n-firstname")
    let htmllastname = document.getElementById("n-lastname")
    let htmlemail = document.getElementById("n-email")
    let htmlpassword = document.getElementById("n-password")
    let htmlrole = document.getElementById("n-role")

    let firstname = htmlfirstname.value
    let lastname = htmllastname.value
    let email = htmlemail.value
    let password = htmlpassword.value
    let role = htmlrole.value

    if (
        firstname === "" ||
        lastname === "" ||
        email === "" ||
        password === "" ||
        role === "") {
        return;
    }

    const reqBody = {
        firstname: firstname,
        lastname: lastname,
        email: email,
        password: password,
        role: role
    }
    console.log(reqBody)
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reqBody)
    }

    fetch(apiUrl, requestOptions)
        .then(() => {
            htmlfirstname.value = ''
            htmllastname.value = ''
            htmlemail.value = ''
            htmlpassword.value = ''
            htmlrole.value = ''
            $('#addWorkerModal').modal('hide');
            getAllStaff()
        })
        .then(err => {
            console.log("USER EXISTS")
        })
}