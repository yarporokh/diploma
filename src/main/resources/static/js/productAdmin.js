let apiUrl = 'http://localhost:8080/api/v1/product';

getAll()

function getAll() {
    fetch(apiUrl + '/all')
        .then(response => response.json())
        .then(data => {
            const table = document.getElementById("table");
            table.innerHTML = ''
            data.map(product => {
                table.innerHTML += buildRow(product)
            })


        })
        .catch(error => console.error('Error fetching data:', error));
}

function buildRow(product) {
    return `<tr id="${product.id}">
        <th scope="row">${product.id}</th>
        <td>${product.name}</td>
        <td>${product.description.substring(0, 11) + '...'}</td>
        <td>${product.category}</td>
        <td>${product.price}</td>
        <td>${product.quantity}</td>
        </tr>`
}

function addNewProduct() {
    let htmlname = document.getElementById("name")
    let htmldescription = document.getElementById("description")
    let htmlprice = document.getElementById("price")
    let htmlquantity = document.getElementById("quantity")
    let htmlcategory = document.getElementById("category")

    const name = htmlname.value
    const description = htmldescription.value
    const price = htmlprice.value
    const quantity = htmlquantity.value
    const category = htmlcategory.value

    if (
        name === "" ||
        description === "" ||
        price === "" ||
        quantity === "" ||
        category === "") {
        return;
    }

    const reqBody = {
        name: name,
        description: description,
        price: price,
        quantity: quantity,
        category: category
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
            htmlcategory.value = ''
            $('#addProductModal').modal('hide');
            getAll()
        })
}