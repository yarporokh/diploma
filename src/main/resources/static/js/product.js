let apiUrl = 'http://localhost:8080/api/v1/product';

// getAll()
loadProductsPage(0)

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

function getProductsPageList(page = 0, size = 12) {
    return fetch(`${apiUrl}?page=${page}&size=${size}`)
        .then(resp => resp.json())
}

async function loadProductsPage(page) {
    let productsOnPage = await getProductsPageList(page)
    console.log(productsOnPage)

    const div = document.getElementById("row");
    div.innerHTML = ''
    productsOnPage.content.map(product => {
        div.innerHTML += buildCard(product)
    })

    getOrder()
    renderPagination(page, productsOnPage.totalPages)
}

function renderPagination(page, totalPages, visiblePages = 5) {
    let paginationContainer = document.getElementById('paginationContainer');
    paginationContainer.innerHTML = ''; // Очищаем контейнер перед рендерингом новой пагинации

    let prevDisabled = page === 0 ? 'disabled' : '';
    let nextDisabled = page === totalPages - 1 ? 'disabled' : '';

    let startPage = Math.max(0, page - Math.floor(visiblePages / 2));
    let endPage = Math.min(totalPages - 1, startPage + visiblePages - 1);

    let paginationHTML = `
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li class="page-item ${prevDisabled}">
                    <a class="page-link" href="#" onclick="loadProductsPage(${page - 1})" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
    `;

    for (let i = startPage; i <= endPage; i++) {
        let active = page === i ? 'active' : '';
        paginationHTML += `
            <li class="page-item ${active}">
                <a class="page-link" href="#" onclick="loadProductsPage(${i})">${i + 1}</a>
            </li>
        `;
    }

    paginationHTML += `
                <li class="page-item ${nextDisabled}">
                    <a class="page-link" href="#" onclick="loadProductsPage(${page + 1})" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    `;

    paginationContainer.innerHTML = paginationHTML;
}