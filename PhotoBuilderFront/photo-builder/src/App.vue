<script setup>
import { ref, onMounted } from 'vue'
import HeadBand from './components/Band.vue'

const message = ref('Loading...')
const should_display = ref(true)
const username = ref('')
const is_connected = ref('')



onMounted(async () => {
  const res = await fetch('http://localhost:8091/api/status', {
    method: 'GET',
    credentials: 'include',
  })
  message.value = await res.json()
  if ('username' in message.value) {
    username.value = message.value['username']
  }
  else {
    username.value = ""
  }
  should_display.value = false
  is_connected.value = username.value != ""
})

</script>

<template>
  <HeadBand :username="username" :is_connected="is_connected" v-if="!should_display"/>
  <RouterView/>
  <div class="home_message" v-if="should_display">
    <div>
      <h1>{{message}}</h1>
      <p>Trying to connect to the server...</p>
    </div>
  </div>
</template>

<style>
.home_message {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--c-bg);
}

</style>