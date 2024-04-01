let apiUrl = 'http://localhost:8080/api/v1/user';
const roles = {"MANAGER": "Менеджер", "ADMIN": "Адміністратор"}
const statues = {false : "Відключений", true : "Активний"}

//TODO: finish edit myself

getAllStaff()

function getAllStaff() {
    fetch(apiUrl + '/workers')
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById("table");
            table.innerHTML = ''
            data.map(worker => {
                table.innerHTML += buildRow(worker)
            })
        })
        .catch(error => console.error('Error fetching data:', error));
}

function buildRow(worker) {
    const status = statues[worker.isEnabled];
    let selectRole = optionsRoles(worker.role)
    let selectStatus = optionsStatues(worker.isEnabled)


    return `<tr id="${worker.id}" data-toggle="modal" data-target="#workerModal${worker.id}">
        <th scope="row">${worker.id}</th>
        <td id="firstname${worker.id}">${worker.firstname}</td>
        <td id="lastname${worker.id}">${worker.lastname}</td>
        <td id="email${worker.id}">${worker.email}</td>
        <td id="role${worker.id}">${worker.role}</td>
        <td id="status${worker.id}">${status}</td>
        </tr>
<div class="modal fade" id="workerModal${worker.id}" tabindex="-1" role="dialog" aria-labelledby="workerModal${worker.id}"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="worker${worker.id}">Редагування</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label for="efirstname">Ім'я</label>
                    <input type="text" value="${worker.firstname}" required id="efirstname${worker.id}" name="efirstname"/>
                    <br>
                    <label for="elastname">Прізвище</label>
                    <input type="text" value="${worker.lastname}" required id="elastname${worker.id}" name="elastname"/>
                    <br>
                    <label for="eemail">Електронна пошта</label>
                    <input type="text" value="${worker.email}" required id="eemail${worker.id}" name="eemail"/>
                    <br>
                    <label for="erole">Роль</label>
                    <select name="erole" id="erole${worker.id}">
                        ${selectRole}
                    </select>
                    <br>
                    <label for="estatus">Статус</label>
                    <select name="estatus" id="estatus${worker.id}">
                        ${selectStatus}
                    </select>
                    <br>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрити</button>
                        <button onclick="editWorker('${worker.id}')" type="submit" class="btn btn-primary">Замінити</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
`
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

function findStaffByFilter() {
    const htmlFilter = document.getElementById('staffFilter');
    const filter = htmlFilter.value

    if (filter === '') {
        getAllStaff()
    } else {
        fetch(`${apiUrl}/workers/${filter}`)
            .then(response => response.json())
            .then(data => {
                const table = document.getElementById("table");
                table.innerHTML = ''
                data.map(worker => {
                    table.innerHTML += buildRow(worker)
                })


            })
            .catch(error => console.error('Error fetching data:', error));
    }
}

function editWorker(id) {
    let htmlfirstname = document.getElementById(`efirstname${id}`)
    let htmllastname = document.getElementById(`elastname${id}`)
    let htmlemail = document.getElementById(`eemail${id}`)
    let htmlrole = document.getElementById(`erole${id}`)
    let htmlstatus = document.getElementById(`estatus${id}`)

    const firstname = htmlfirstname.value
    const lastname = htmllastname.value
    const email = htmlemail.value
    const role = htmlrole.value
    const status = htmlstatus.value

    if (
        firstname === "" ||
        lastname === "" ||
        email === "" ||
        role === "" ||
        status === "") {
        return;
    }

    const reqBody = {
        id: id,
        firstname: firstname,
        lastname: lastname,
        email: email,
        role: role,
        isEnabled: status
    }

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reqBody)
    }

    fetch(`${apiUrl}/save`, requestOptions)
        .then(() => {
            document.getElementById(`firstname${id}`).innerText = firstname
            document.getElementById(`lastname${id}`).innerText = lastname
            document.getElementById(`email${id}`).innerText = email
            document.getElementById(`role${id}`).innerText = role
            document.getElementById(`status${id}`).innerText = statues[status]

            let rolesSelection = optionsRoles(role)
            htmlrole.innerHTML = rolesSelection

            let statuesSelection = optionsStatues(status)
            htmlstatus.innerHTML = statuesSelection

            $(`#workerModal${id}`).modal('hide');
        })
}

function optionsRoles(role) {
    return role === "MANAGER" ?
        `<option value="MANAGER">Менеджер</option>
        <option value="ADMIN">Адміністратор</option>` :
        `<option value="ADMIN">Адміністратор</option>
        <option value="MANAGER">Менеджер</option>`
}

function optionsStatues(status) {
    return status === true ?
        `<option value="true">Активний</option>
        <option value="false">Відключений</option>` :
        `<option value="false">Відключений</option>
        <option value="true">Активний</option>`
}