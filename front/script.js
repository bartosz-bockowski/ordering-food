let link = "http://localhost:8080/api/v1"
let jwtToken
let cartLoaded = false

$(document).ready(() => {



    $("#registerButton").click(() => {
        fetch(link + "/customer", {
            method: "post",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "username": $("#loginReg").val(),
                "password": $("#passwordReg").val()
            })
        })
        .then(res => {
            if(res.ok){
                alert("zarejestrowano")
                return
            }
            alert("blad rejestracji")
            return
        })
    })




    $("#loginButton").click(() => {
        fetch(link + "/security/login", {
            method: 'post',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "username": $("#login").val(),
                "password": $("#password").val()
            })
        })
        .then(res => {
            if(res.ok){
                alert("zalogowano")
                $("#loginForm").hide()
                $("#restaurantList").show()
                $("#cartButton").show()
                return res.json()
            }
            alert("złe dane logowania")
            return
        })
        .then(res => {
            jwtToken = "Bearer " + res.token
        })
    })



    $("#loadRestaurantsButton").click(() => {
        fetch(link + "/restaurant/all", {
            headers: {
                Authorization: jwtToken
            }
        })
        .then(res => res.json())
        .then(res => {
            $("#loadRestaurantsButton").hide()
            let restaurantList = $("#restaurantList")
            for(let restaurant of res){
                let restaurantDiv = $("<div></div>")
                restaurantDiv.text(restaurant.name)
                restaurantDiv.css("width","200px")
                restaurantDiv.css("margin-left","auto")
                restaurantDiv.css("margin-right","auto")

                let restaurantButton = $("<button class='restaurantButton'></button>")
                restaurantButton.text("potrawy")
                restaurantButton.css("margin-left","5px")
                restaurantButton.attr("restaurantId", restaurant.id)
                restaurantButton.attr("loaded",false)
                restaurantButton.click((e) => {
                    let button = $(e.currentTarget)
                    loadOrHideMealsOfRestaurantById(button)
                })

                restaurantDiv.append(restaurantButton)

                let mealList = $("<div class='mealList' style='display: none'></div>")
                
                restaurantDiv.append(mealList)

                restaurantList.append(restaurantDiv)
            }
        })
    })



    $("#cartButton").click((e) => {
        let button = $(e.currentTarget)
        if(!cartLoaded){
            cartLoaded = true
            loadCart()
        }
        $("#restaurantList").toggle()
        $("#cart").toggle()
        if(button.text() === "koszyk"){
            button.text("restauracje")
        } else {
            button.text("koszyk")
        }
    })



    $("#buy").click(() => {
        fetch(link + "/cart/clear", {
            method: "DELETE",
            headers: {
                Authorization: jwtToken
            }
        })
        .then(res => {
            if(res.ok){
                alert("zaplacono")
                loadCart()
            }
        })
    })
})

function loadOrHideMealsOfRestaurantById(button){
    id = button.attr("restaurantId")
    element = button.parent().find(".mealList")
    element.toggle()
    if(button.attr("clicked")){
        return
    }
    button.attr("clicked",true)

    fetch(link + "/restaurant/" + id + "/meals", {
        headers: {
            Authorization: jwtToken
        }
    })
    .then(res => res.json())
    .then(res => {
        for(let meal of res){
            let mealDiv = $("<div></div>")
            mealDiv.css("border","1px solid black")
            mealDiv.css("padding","5px")

            let mealNameDiv = $("<div></div>")
            mealNameDiv.text(meal.name)
            mealNameDiv.css("float","left")

            let mealPriceDiv = $("<div></div>")
            mealPriceDiv.text(meal.price + "zł")
            mealPriceDiv.css("float","right")

            let mealQuantity = $("<input type='number' name='quantity' placeholder='ilosc'/>")
            
            let addMealButton = $("<button mealId=" + meal.id + " class='addMeal'></button>")
            addMealButton.text("dodaj posilek")
            addMealButton.click((e) => {
                let button = $(e.currentTarget)
                let quantity = button.parent().find("input").val()
                if(quantity === "" || quantity < 1){
                    alert("wprowadz ilosc wieksza od 0")
                    return
                }
                fetch(link + "/cart/putItem", {
                    method: "PUT",
                    headers: {
                        Authorization: jwtToken,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        "meal": {
                            "id": button.attr("mealId")
                        },
                        "quantity": quantity
                    })
                })
                .then(() => loadCart())
            })

            let topDiv = $("<div></div>")
            let bottomDiv = $("<div></div>")

            topDiv.append(mealNameDiv)
            topDiv.append(mealPriceDiv)

            bottomDiv.append(mealQuantity)
            bottomDiv.append(addMealButton)

            mealDiv.append(topDiv)
            mealDiv.append(bottomDiv)

            element.append(mealDiv)
        }
    })
}



function loadCart(){
    $("#cart").find(".cartItemDiv").remove()
    fetch(link + "/customer/cart", {
        headers: {
            Authorization: jwtToken
        }
    })
    .then(res => res.json())
    .then(res => {
        let cart = $("#cart")
        for(let item of res){
            let cartItemDiv = $("<div class='cartItemDiv'></div>")
            cartItemDiv.text(item.meal.name + " x " + item.quantity)
            cart.find(".list").append(cartItemDiv)
        }
    })
}