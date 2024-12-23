let apiUrl = 'http://localhost:8080/api/v1/product';

loadProductsPage(0)

function buildCard(product) {
    let dis = ''
    if (product.quantity === 0) {
        dis = 'disabled'
    }

    let addToCartBtn = ""
    if (userPrincipal.role === undefined || userPrincipal.role === "USER") {
        addToCartBtn = `<button onclick="addToOrder(${product.id})" type="submit" class="btn btn-primary" ${dis}>Додати до кошика</button>`
    }

    return `<div class="col-md-4" id="${product.id}">
                            <div class="card mb-4">
                            <img class="card-img-top" width="250px" height="175px" src="${product.photoUrl}" alt="${product.name}">
                                <div class="card-body">
                                <a class="product-link" href="http://localhost:8080/product/${product.id}">
                                        <h5 class="card-title">${product.name}</h5>
                                        </a>
                                        <p class="card-text">${product.price.toFixed(2)}₴</p>
                                        ${addToCartBtn}
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
    paginationContainer.innerHTML = '';

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

function performSearch() {
    const filter = document.getElementById('search-input').value;

    if (filter.length > 0) {
        fetch(`${apiUrl}/filter/${filter}`)
    .then(response => response.json())
            .then(data => {
                displaySearchResults(data)
            });
    } else {
        displaySearchResults([])
    }
}

function displaySearchResults(products) {
    const resultsContainer = document.getElementById('search-results')
    resultsContainer.innerHTML = '';

    if (products.length > 0) {
        products.forEach(product => {
            resultsContainer.innerHTML += `<li class="list-group-item">
                                                <a class="product-link" href="http://localhost:8080/product/${product.id}">${product.name}<br/>${product.price.toFixed(2)}₴</a>
                                            </li>`
        });
    }
}