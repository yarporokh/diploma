let orderApiUrl = 'http://localhost:8080/api/v1/order'


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
    let quantityElement = document.getElementById(`quantityValue${id}`)
    let quantityValue = parseInt(quantityElement.textContent, 10)

    if (quantityValue === 1) {
        removeAllQuantityFromOrder(id)
    } else {
        quantityElement.textContent = quantityValue - 1

        fetch(`${orderApiUrl}/${id}`)
            .then(resp => resp.json())
            .then(data => {
                let fullPriceValueElement = document.getElementById(`fullPriceValue${id}`)
                let fullPriceValueElementValue = parseFloat(fullPriceValueElement.textContent)
                let newFullPrice = fullPriceValueElementValue - data.priceAtOrder

                fullPriceValueElement.textContent = newFullPrice.toFixed(2)

                removeRequest(id)
            })

    }
}

function removeRequest(id) {
    fetch(orderApiUrl + '/remove/' + id)
        .then(() => {
        })
        .catch(error => console.error('Error fetching data:', error))
}

function removeAllQuantityFromOrder(id) {
    fetch(orderApiUrl + '/remove-all/' + id)
        .then(() => {
            var itemToRemove = document.getElementById(`cartProduct${id}`);
            itemToRemove.parentElement.removeChild(itemToRemove)
        })
        .catch(error => console.error('Error fetching data:', error))
}

function getOrder() {
    const orderDiv = document.getElementById("cartView");
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
                //todo: update add and delete
                orderDiv.innerHTML +=
                    `<div id="cartProduct${productId}">
                        <div class="row mb-4 d-flex justify-content-between align-items-center">
                  <div class="col-md-3 col-lg-3 col-xl-3">
                       <h6 class="text-black mb-0">${name}</h6>
                       <button onclick="removeAllQuantityFromOrder(${productId})" type="submit" class="btn btn-link px-2"><i class="fa fa-trash-o"></i></button> 
                  </div>
                  <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                     <button onclick="removeFromOrder(${productId})" type="submit" class="btn btn-link px-2"><i class="fa fa-minus"></i></button> 
                     <h5 id="quantityValue${productId}">${quantity}</h5> 
                     <button onclick="addToOrder(${productId})" type="submit" class="btn btn-link px-2"><i class="fa fa-plus"></i></button>
                  </div>
                  <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                      <h6 id="fullPriceValue${productId}" class="mb-0">${fullPrice}</h6>
                  </div>
                  <div>
                  <hr class="my-4">
                  </div>
                `
            })
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        })
}