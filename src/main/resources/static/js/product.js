let apiUrl = 'http://localhost:8080/api/v1/product';


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
                                    </div>
                            </div>
                        </div>`
}