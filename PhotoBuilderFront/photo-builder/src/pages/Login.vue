<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import Form from '../components/Form.vue'
const router = useRouter()

const serverResponse = ref({
  "password_message": "error"
});


async function ConnectForm({ email, password, birthday }) {
  console.log(password, email, birthday)
  //Try to connect
  try {
    //Send Infos
    const res = await fetch('http://localhost:8091/api/connect', {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password, birthday})
    })
    //Wait response
    const data = await res.json()
    
    console.log('Server response', data);
    if ('login' in data) {
      if (data['login'] == "successfull") {
        router.push('/')
      }
    }

    console.log(serverResponse)
  } catch (e) {
    console.error('Error:', e)
  }
}

</script>

<template>
  <div class="sign-in">
    <Form title="Log In" :send_function="ConnectForm"></Form>
  </div>
</template>
