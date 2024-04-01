let apiUrl = 'http://localhost:8080/api/v1/product';

const products = {
    "PROTEIN":"Протеїн",
    "CREATINE":"Креатин",
    "GAINER":"Гейнер",
    "FATBURNER":"Жироспалювач",
    "AMINOCYCLOTES":"Амінокислоти",
    "PRETRAIN":"Предтренувальний комплекс"
}

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
    return `<tr id="${product.id}" data-toggle="modal" data-target="#productModal${product.id}">
        <th scope="row">${product.id}</th>
        <td id="name${product.id}">${product.name}</td>
        <td id="description${product.id}">${product.description.substring(0, 11) + '...'}</td>
        <td id="category${product.id}">${product.category}</td>
        <td id="price${product.id}">${product.price.toFixed(2)}</td>
        <td id="quantity${product.id}">${product.quantity}</td>
        </tr>
        
        <div class="modal fade" id="productModal${product.id}" tabindex="-1" role="dialog" aria-labelledby="productModal${product.id}"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="product${product.id}">Редагування</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label for="ename">Назва</label>
                    <input type="text" value="${product.name}" required id="ename${product.id}" name="ename"/>
                    <br>
                    <label for="edescription">Опис</label>
                    <input type="text" value="${product.description}" required id="edescription${product.id}" name="edescription"/>
                    <br>
                    <label for="eprice">Ціна</label>
                    <input type="number" value="${product.price.toFixed(2)}" min="0" step="0.01" required id="eprice${product.id}" name="eprice"/>
                    <br>
                    <label for="equantity">Кількість</label>
                    <input type="number" value="${product.quantity}" required id="equantity${product.id}" name="equantity" min="0" step="1"/>
                    <br>
                    <label for="ecategory">Категорія</label>
                    <select name="ecategory" id="ecategory${product.id}">
                        <option id="placeholderCategory${product.id}" value="${product.category}">${products[product.category]}</option>
                        <option value="PROTEIN">Протеїн</option>
                        <option value="CREATINE">Креатин</option>
                        <option value="GAINER">Гейнер</option>
                        <option value="FATBURNER">Жироспалювач</option>
                        <option value="AMINOCYCLOTES">Амінокислоти</option>
                        <option value="PRETRAIN">Предтренувальний комплекс</option>
                    </select>
                    <br>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрити</button>
                        <button onclick="editProduct(${product.id})" type="submit" class="btn btn-primary">Замінити</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
`
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

function editProduct(id) {
    let htmlname = document.getElementById(`ename${id}`)
    let htmldescription = document.getElementById(`edescription${id}`)
    let htmlprice = document.getElementById(`eprice${id}`)
    let htmlquantity = document.getElementById(`equantity${id}`)
    let htmlcategory = document.getElementById(`ecategory${id}`)

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
        id: id,
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
            document.getElementById(`name${id}`).innerText = name
            document.getElementById(`description${id}`).innerText = description.substring(0, 11) + '...'
            document.getElementById(`category${id}`).innerText = category
            document.getElementById(`price${id}`).innerText = price
            document.getElementById(`quantity${id}`).innerText = quantity

            let placeholderCategory = document.getElementById(`placeholderCategory${id}`)
            placeholderCategory.value = category
            placeholderCategory.innerText = products[category]

            $(`#productModal${id}`).modal('hide');
        })
}


function findProductsByFilter() {
    const htmlFilter = document.getElementById('productFilter');
    const filter = htmlFilter.value

    if (filter === '') {
        getAll()
    } else {
        fetch(`${apiUrl}/filter/${filter}`)
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
}