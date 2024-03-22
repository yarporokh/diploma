let apiUrl = 'http://localhost:8080/api/v1/product';


getAll()

function getAll() {
    fetch(apiUrl + '/all')
        .then(response => response.json())
        .then(data => {
            const div = document.getElementById("row");
            data.map(product => {
                div.innerHTML += buildCard(product.id, product.name, product.description, product.price)
            })


        })
        .catch(error => console.error('Error fetching data:', error));

}

function buildCard(id, name, description, price) {
    return `<div class="col-md-4" id="${id}">
                            <div class="card mb-4">
                                <div class="card-body">
                                        <h5 class="card-title">${name}</h5>
                                        <p class="card-text">${description}</p>
                                        <p class="card-text">${price}</p>
                                    </div>
                            </div>
                        </div>`
}

function addNewProduct() {
    let htmlname = document.getElementById("name")
    let htmldescription = document.getElementById("description")
    let htmlprice = document.getElementById("price")
    let htmlquantity = document.getElementById("quantity")

    let name = htmlname.value
    let description = htmldescription.value
    let price = htmlprice.value
    let quantity = htmlquantity.value

    if (
        name === "" ||
        description === "" ||
        price === "" ||
        quantity === "") {
        return;
    }

    const reqBody = {
        name: name,
        description: description,
        price: price,
        quantity: quantity
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
            htmlname.value = ''
            htmldescription.value = ''
            htmlprice.value = ''
            htmlquantity.value = ''
            $('#addProductModal').modal('hide');
        })
}