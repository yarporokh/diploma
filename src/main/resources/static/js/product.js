let apiUrl = 'http://localhost:8080/api/v1/product';

getAll()

function getAll() {
    fetch(apiUrl + '/all')
        .then(response => response.json())
        .then(data => {
            const div = document.getElementById("row");
            div.innerHTML = ''
            data.map(product => {
                div.innerHTML += buildCard(product)
            })

            getOrder()
        })
        .catch(error => console.error('Error fetching data:', error));
}

function buildCard(product) {
    let dis = ''
    if (product.quantity === 0) {
        dis = 'disabled'
    }
    return `<div class="col-md-4" id="${product.id}">
                            <div class="card mb-4">
                                <div class="card-body">
                                <a class="product-link" href="http://localhost:8080/product/${product.id}">
                                        <h5 class="card-title">${product.name}</h5>
                                        </a>
                                        <p class="card-text">${product.price.toFixed(2)}₴</p>
                                        <button onclick="addToOrder(${product.id})" type="submit" class="btn btn-primary" ${dis}>Додати до кошика</button>
                                    </div>
                            </div>
                        </div>`
}

function findByCategory(category) {
    fetch(`${apiUrl}/category/${category}`)
        .then(response => response.json())
        .then(data => {
            const div = document.getElementById("row");
            div.innerHTML = ''
            data.map(product => {
                div.innerHTML += buildCard(product)
            })

            getOrder()
        })
        .catch(error => console.error('Error fetching data:', error));
}