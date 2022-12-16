console.log("hola")


fetch('http://localhost:8080/api/1/pizze/all')
.then((response) => (response.json()))
.then((data) => (console.log(data)))