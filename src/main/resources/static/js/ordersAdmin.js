const statues = {
    "NEW": "Новий",
    "PROCESSING": "Обробляється",
    "SHIPPED": "Доставка",
    "CANCELLED": "Скасований",
    "COMPLETED": "Виконано"
}

const statuesColor = {
    "NEW": "text-primary",
    "PROCESSING": "text-warning",
    "SHIPPED": "text-info",
    "CANCELLED": "text-danger",
    "COMPLETED": "text-success"
}

let orderApiUrl = 'http://localhost:8080/api/v1/order'

function getAllOrders() {
    return fetch(`${orderApiUrl}/all-orders`)
        .then(resp => resp.json())
}

async function buildOrdersTable() {
    let orders = await getAllOrders()
    let tableElement = document.getElementById("ordersTable")

    orders.forEach(order => {
        tableElement.innerHTML += buildRow(order)
    })
}


function buildRow(order) {
    let id = order.id
    let customerEmail = order.user.email
    let managerEmail = ''

    if (order.manager === null) {
        managerEmail = 'Відсутній'
    } else {
        managerEmail = order.manager.email
    }

    let status = statues[order.status]

    return `
    <tr id="orderRow${id}">
        <th scope="row"><a href="http://localhost:8080/admin/user-order/${id}">${id}</a></th>
        <td>${customerEmail}</td>
        <td>${managerEmail}</td>
        <td><span class="${statuesColor[order.status]}">${status}</span></td>
        </tr>`
}

function getOrderById(id) {
    return fetch(`${orderApiUrl}/user-order-info/${id}`)
        .then(resp => resp.json())
}

async function loadUserOrderManagementInfo(id) {
    let orderNumberElement = document.getElementById("orderNumber")

    let acctNameElement = document.getElementById("acctName")
    let acctEmailElement = document.getElementById("acctEmail")

    let receiverNameElement = document.getElementById("receiverName")
    let receiverEmailElement = document.getElementById("receiverEmail")
    let receiverPhoneElement = document.getElementById("receiverPhone")

    let fullOrderPriceElement = document.getElementById("fullOrderPrice")
    let statusElement = document.getElementById("status")
    let datetimeElement = document.getElementById("datetime")
    let managerElement = document.getElementById("manager")

    let productsElement = document.getElementById("products")

    let order = await getOrderById(id)

    orderNumberElement.innerText = order.id
    acctNameElement.innerText = `${order.user.firstname} ${order.user.lastname}`
    acctEmailElement.innerText = `${order.user.email}`
    receiverNameElement.innerText = `${order.receiver.receiverFirstname} ${order.receiver.receiverLastname}`
    receiverEmailElement.innerText = order.receiver.receiverEmail
    receiverPhoneElement.innerText = order.receiver.receiverPhone
    fullOrderPriceElement.innerText = order.fullPrice.toFixed(2)
    statusElement.innerText = statues[order.status]
    statusElement.classList.add(statuesColor[order.status])

    const timeComponents = order.createdDate.split('T')[1].split('.')[0];
    const [hours, minutes, seconds] = timeComponents.split(':');
    const dateComponents = order.createdDate.split('T')[0];
    const [year, month, day] = dateComponents.split('-');
    const fullDate = `${hours}:${minutes}:${seconds} ${day}/${month}/${year}`

    datetimeElement.innerText = fullDate

    if (order.manager === null) {
        managerElement.innerText = `Відсутній`
        if (order.status === "NEW") {
            managerElement.innerHTML += `<button class="btn btn-warning ml-2" onclick="claimOrder('${order.id}')">Прийняти замовлення</button>`
        }
    } else {
        managerElement.innerText = `${order.manager.firstname} ${order.manager.lastname} - ${order.manager.email}`
    }
//TODO: ADD buttons for each status
    productsElement.innerHTML = ''
    order.items.forEach(product => {
        productsElement.innerHTML += buildProductCards(product)
    })

}

function buildProductCards(product) {
    let name = product.product.name
    let quantity = product.quantity
    let price = product.priceAtOrder
    let fullProductPrice = (price * quantity).toFixed(2)


    return `
    <div class="col-md-4">
                    <div class="card">
                        <img src="product1.jpg" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title"><strong>${name}</strong></h5>
                            <p class="card-text"><strong>Ціна при замовленні:</strong> ${price}</p>
                            <p class="card-text"><strong>Кількість:</strong> ${quantity}</p>
                            <p class="card-text"><strong>Остаточна ціна:</strong> ${fullProductPrice}₴</p>
                        </div>
                    </div>
                </div>
    `
}

function claimOrder(id) {
    fetch(`${orderApiUrl}/claim-order/${id}`)
        .then(() => {
            loadUserOrderManagementInfo(id)
        })
}

function findOrdersByFilter() {
    const htmlFilter = document.getElementById('orderFilter');
    const filter = htmlFilter.value

    let tableElement = document.getElementById("ordersTable")
    tableElement.innerHTML = ''

    if (filter === '') {
        buildOrdersTable()
    } else {
        fetch(`${orderApiUrl}/filter/${filter}`)
            .then(resp => resp.json())
            .then(orders => {
                    orders.forEach(order => {
                        tableElement.innerHTML += buildRow(order)
                    })
                }
            )
    }
}