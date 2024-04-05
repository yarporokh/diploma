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
                event.preventDefault()
                form.classList.add('was-validated')
            }, false)
        })
    }, false)
}())

getCart()

function getOrder() {
    return fetch(orderApiUrl)
        .then(resp => resp.json())
}

async function getCart() {
    let cartListElement = document.getElementById("cartList")
    let price = 0
    const data = await getOrder()

    data.forEach(product => {
        cartListElement.innerHTML += buildProductRow(product)
        price += product.quantity * product.priceAtOrder
    })

    cartListElement.innerHTML += `<li class="list-group-item d-flex justify-content-between">
                    <span>Загалом</span>
                    <strong id="fullPrice">${price.toFixed(2)}₴</strong>
                </li>`
}

function buildProductRow(product) {
    let name = product.product.name
    let price = product.priceAtOrder
    let quantity = product.quantity
    const fullPrice = (price * quantity).toFixed(2)

    return `
        <li class="list-group-item d-flex justify-content-between lh-condensed">
                    <div>
                        <h6 class="my-0">${name} x ${quantity}</h6>
                    </div>
                    <span class="text-muted">${fullPrice}₴</span>
                </li>
    `
}

function confirmCheckout() {
    let receiverFirstnameElement = document.getElementById("receiverFirstname")
    let receiverLastnameElement = document.getElementById("receiverLastname")
    let receiverEmailElement = document.getElementById("receiverEmail")
    let receiverPhoneElement = document.getElementById("receiverPhone")
    let receiverAddressElement = document.getElementById("receiverAddress")

    let receiverFirstname = receiverFirstnameElement.value
    let receiverLastname = receiverLastnameElement.value
    let receiverEmail = receiverEmailElement.value
    let receiverPhone = receiverPhoneElement.value
    let receiverAddress = receiverAddressElement.value

    if (receiverFirstname === '' ||
        receiverLastname === '' ||
        receiverEmail === '' ||
        receiverAddress === '') {
        return
    }

    const reqBody = {
        receiverFirstname: receiverFirstname,
        receiverLastname: receiverLastname,
        receiverEmail: receiverEmail,
        receiverPhone: receiverPhone,
        receiverAddress: receiverAddress
    }

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(reqBody)
    }

    fetch(`${orderApiUrl}/save-order`, requestOptions)
        .then(() => {
            let btn = document.getElementById("confirmCheckoutButton")
            btn.classList.remove('btn-primary')
            btn.classList.add('btn-success')
            btn.innerText = "Замовлення оформлено"
            btn.disabled = true
        })
}