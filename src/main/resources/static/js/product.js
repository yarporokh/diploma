let apiUrl = 'http://localhost:8080/api/v1/product';


getAll()


function getAll() {
    fetch(apiUrl + '/all')
        .then(response => response.json())
        .then(data => {
            const div = document.getElementById("main");
            console.log(data);


        })
        .catch(error => console.error('Error fetching data:', error));

}

function buildCard(id, name, description, price, category, quantity) {
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