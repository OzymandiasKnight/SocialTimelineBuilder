<script setup>
import { ref } from 'vue'
import Panel from './Panel.vue'
import './style.css'

const props = defineProps({
  title: String,
  signin: Boolean,
  send_function: Function,
  password_message: String,
})

const email = ref('')
const password = ref('')
const birthday = ref('')

var curr_date = new Date();
var dd = String(curr_date.getDate()).padStart(2, '0')
var mm = String(curr_date.getMonth() + 1).padStart(2,'0')
var yyyy = curr_date.getFullYear()-13;

curr_date = yyyy + '-' + mm + '-' + dd;

</script>

<template>
  <Panel width="600px" height="fit-content">
    <h1>{{ title }}</h1>
    <h2>Email</h2>
    <div class="input">
        <input type="text" placeholder="" maxlength="254" v-model="email">
    </div>
    <h2>Password</h2>
    <div class="input">
      <input type="text" placeholder="" maxlength="25" v-model="password">
    </div>
    <h2 v-if="signin">Birthday</h2>
    <p v-if="signin">You must be 13 years old to register</p>
    <div v-if="signin">
      <input type="date" class="date" :max=curr_date min="" v-model="birthday">
    </div>
    <p>{{ password_message }}</p>
    <div class="input">
      <button @click="props.send_function({
        email: email,
        password: password,
        birthday: birthday
      })">
        {{ props.title }}
      </button>
    </div>

  </Panel>
</template>


