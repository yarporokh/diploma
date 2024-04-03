let apiUrl = 'http://localhost:8080/api/v1/product';
let orderApiUrl = 'http://localhost:8080/api/v1/order'

getAll()

function getAll() {
    fetch(apiUrl + '/all')
        .then(response => response.json())
        .then(data => {
            const div = document.getElementById("row");
            data.map(product => {
                div.innerHTML += buildCard(product)
            })


        })
        .catch(error => console.error('Error fetching data:', error));

}

function buildCard(product) {
    return `<div class="col-md-4" id="${product.id}">
                            <div class="card mb-4">
                                <div class="card-body">
                                        <h5 class="card-title">${product.name}</h5>
                                        <p class="card-text">${product.description}</p>
                                        <p class="card-text">${product.price.toFixed(2)}₴</p>
                                        <button onclick="addToOrder(${product.id})" type="submit" class="btn btn-primary">Додати до кошика</button>
                                    </div>
                            </div>
                        </div>`
}

function addToOrder(id) {
    try {
        fetch(orderApiUrl + '/add/' + id)
            .then(resp => {
                if (resp.status === 200) {
                    return resp
                }
                throw new Error("Error")
            })
            .then(data => {
                getOrder()
            })
            .catch(error => console.error('Error fetching data:', error));
    } catch (error) {
        console.log("ERROR")
    }
}

function removeFromOrder(id) {
    fetch(orderApiUrl + '/remove/' + id)
        .then(() => {
            getOrder()
        })
        .catch(error => console.error('Error fetching data:', error))
}

function getOrder() {
    const orderDiv = document.getElementById("cart");
    orderDiv.innerHTML = ''

    fetch(orderApiUrl)
        .then(resp => resp.json())
        .then(data => {
            data.forEach(p => {
                const productId = p.product.id
                const name = p.product.name
                const quantity = p.quantity
                const price = p.priceAtOrder
                const fullPrice = (price * quantity).toFixed(2)
                orderDiv.innerHTML += `<li>${name} - <button onclick="removeFromOrder(${productId})" type="submit" class="btn btn-info">-</button> ${quantity} <button onclick="addToOrder(${productId})" type="submit" class="btn btn-info">+</button>
                                        x ${price}
                                        <br>${fullPrice}<br>
                                        "-----------------"<br></li>`
            })
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        })
}