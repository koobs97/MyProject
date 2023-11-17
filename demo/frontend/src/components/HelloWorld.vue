<template>
  <div>
    <a href="/usr">사용자 목록</a>
    <button @click="getData">데이터 호출</button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'HomeView',

  methods: {
    getData(){
      axios
        .post('http://localhost:8081/findAll')
        .then(Response => {
          console.log(Response)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>
