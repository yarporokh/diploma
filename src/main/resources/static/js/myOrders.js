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

findMyOrders()

function getAllMyOrders() {
    return fetch(`${orderApiUrl}/user-orders`)
        .then(resp => resp.json())
}

async function findMyOrders() {
    let ordersListElement = document.getElementById("ordersList")
    let data = await getAllMyOrders()
    data.forEach(order => {
        ordersListElement.innerHTML += buildRow(order)
    })
}

function buildRow(order) {
    const dateTime = new Date(order.createdDate);
    const date = dateTime.toISOString().split('T')[0];
    const time = dateTime.toISOString().split('T')[1].split('.')[0];
    const price = order.fullPrice.toFixed(2)
    const status = statues[order.status]
    const statusColor = statuesColor[order.status]
    let cards = buildOrderCards(order.items)

    const closeOrderBtn = (order.status !== 'CANCELED' && order.status !== 'COMPLETED')
        ? `<button onclick="closeOrder('${order.id}')" type="submit" class="btn btn-danger">Відмінити замовлення</button>`
        : ''

    return `<div class="card">
            <div class="card-header" id="heading${order.id}">
                <h4 class="mb-0">
                    <button class="btn btn-link" data-toggle="collapse" data-target="#collapse${order.id}" aria-expanded="true" aria-controls="collapse${order.id}">
                        <span><strong>№ ${order.id}</strong></span>
                    </button>
                            <div class="d-flex justify-content-between align-items-center">
            <h4 class="mb-0"><span class="mr-3"><strong>Ціна:</strong> ${price}₴</span></h4>
            <h4 class="mb-0"><span class="mr-3"><strong>Дата і час:</strong> ${time} ${date}</span></h4>
            <h4 class="mb-0"><span><strong>Статус: <span class="${statusColor}" id="stauts${order.id}">${status}</span></strong></span>
            ${closeOrderBtn}</h4>
        </div>
                </h4>
            </div>
            <div id="collapse${order.id}" class="collapse" aria-labelledby="heading${order.id}" data-parent="#ordersList">
                <div class="card-body">
                    ${cards}
                    <div class="d-flex flex-column">
                        <span><strong>Отримувач замовлення:</strong> ${order.receiver.receiverFirstname} ${order.receiver.receiverLastname}</span>
                        <span>${order.receiver.receiverPhone}</span>
                        <span>${order.receiver.receiverEmail}</span>
                        <span><strong>Адреса доставки:</strong> ${order.receiver.receiverAddress}</span>
                    </div>
                </div>
            </div>
        </div>`
}

function buildOrderCards(products) {
    let cards = ''

    products.forEach(product => {
        cards += `<div class="card" id="${product.id}">
                                <div class="card-body">
                                <a class="product-link" href="http://localhost:8080/product/${product.id}">
                                        <h5 class="card-title">${product.product.name}</h5>
                                        </a>
                                        <p class="card-text"><strong>Ціна:</strong> ${product.priceAtOrder}₴</p>
                                        <p class="card-text"><strong>Кількість:</strong> ${product.quantity}</p>
                                    </div>
                            </div>`
    })
    return cards;
}

function closeOrder(id) {
    console.log(id)
    fetch(`${orderApiUrl}/v1/order/close-order/${id}`)
        .then(() => {
            let statusElement = document.getElementById(`status${id}`)
            statusElement.innerText = statues['CANCELLED']
            statusElement.className = ""
            statusElement.classList.add(statuesColor['CANCELLED'])
        })
}