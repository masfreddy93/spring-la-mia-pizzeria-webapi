<template>
  <div>
    <h2>Pizze</h2>
    <button @click="test">Test</button>

    <!-- Create pizza -->
    <div v-if="!pizza_create_form">
      <button @click="pizza_create_form = true">Create pizza</button>
    </div>
    <div v-else>
      <form @submit="createPizza">
        <label for="name">Name</label>
        <input type="text" name="name" v-model="pizza_create.name">
        <br>
        <label for="name">Description</label>
        <input type="text" name="name" v-model="pizza_create.description">
        <br>
        <label for="name">Price</label>
        <input type="number" name="name" v-model="pizza_create.price">
        <br>
        <input type="submit" value="Create">
      </form>
    </div>

    <!-- index pizze -->
    <ul>
      <li v-for="pizza in pizze" :key="pizza.id">
        {{ pizza.name }}

        <!-- Show ingredients -->
        <button @click="getIngredients(pizza.id)">Show ingredients</button>
        <ul>
          <li v-for="ingrediente in pizza.ingredients" :key="ingrediente.id">
            {{ ingrediente.name }}
          </li>
        </ul>
        
        <!-- Edit pizza -->
        <button @click="editPizza(pizza.id)">Edit</button>
        <div v-if="pizze_edit_id == pizza.id">
          <br>

          <!-- Update -->
          <form @submit="updatePizza">
              <label for="name">Name</label>
              <input type="text" name="name" v-model="pizza.name">
              <br>
              <label for="name">Description</label>
              <input type="text" name="name" v-model="pizza.description">
              <br>
              <label for="name">Price</label>
              <input type="number" name="name" v-model="pizza.price">
              <br>

              <input type="submit" value="Update">
              <button @click="editPizza(PIZZE_EDIT_ID_DEFAULT)">
                Cancel
              </button>
            </form>

          <!-- Delete -->
          <button @click="deletePizza(pizza.id)">Delete</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

const API_URL = 'http://localhost:8080/api/1'
const PIZZE_EDIT_ID_DEFAULT = -1

export default {

  name: 'MainComponent',
  data() {
    return {

      pizze: [],
      pizze_edit_id: PIZZE_EDIT_ID_DEFAULT,
      pizza_create: {},
      pizza_create_form: false
    }
  },
  methods: {

    // test(){

    //   console.log(this.pizze[2])
    // },

    getPizzaIndexById(id) {

      for (let i = 0; i < this.pizze.length; i++) {

        const pizza = this.pizze[i]
        if (pizza.id == id)
          return i
      }
      return -1
    },
    getPizzaById(id) {

      return this.pizze[this.getPizzaIndexById(id)]
    },
    editPizza(id) {

      console.log('edit pizza')
      this.pizze_edit_id = id;

    },
    createPizza(e) {

      console.log('crea una pizza')

      e.preventDefault();
      this.pizza_create_form = false

      axios
        .post(API_URL + "/pizze/create", this.pizza_create)
        .then(res => {

          const pizza = res.data

          if (pizza == null)
            return

          this.pizze.push(pizza)
        })
    },
    updatePizza(e){

      e.preventDefault();

      const id = this.pizze_edit_id
      const pizza = this.getPizzaById(id)

      this.editPizza(PIZZE_EDIT_ID_DEFAULT)

      axios
        .post(API_URL + '/pizze/update/' + id, pizza)
        .then(res => {

          const index = this.getPizzaIndexById(id)
          const oldPizza = this.pizze[index]

          const pizza = res.data
          pizza.ingredients = oldPizza.ingredients

          this.pizze[index] = pizza
        })
    },
    deletePizza(id) {

      console.log("pizza to delete has got this id: " + id)

      axios
        .get(API_URL + '/pizze/delete/' + id)
        .then(res => {

          const deleted = res.data
          if (deleted != true)
            return

          const index = this.getPizzaIndexById(id)
          this.pizze.splice(index, 1)
          //perchè devo aggiornare pagina x vedere scomparire pizza da front end mentre nel DB già se ne è andata??
        })
    },
    getIngredients(id){
      console.log('ingredienti della pizza con id: ' + id)

      axios
        .get(API_URL + '/ingredients/by/pizza/' + id)
        .then(res => {

          // console.log(JSON.stringify(res.data))

          const ingredients = res.data

          if(ingredients == null) 
            return

          const index = this.getPizzaIndexById(id)
          this.pizze[index].ingredients = ingredients
        })
    }
  },
  mounted() {

    axios
      .get(API_URL + '/pizze/all')
      .then(res => {

        // console.log(res.data)
        const pizze = res.data
        if (pizze == null) return

        this.pizze = pizze
      })

  }
}
</script>



<!-- Add "scoped" attribute to limit CSS to this component only -->
<!-- <style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style> -->
