<script setup>
import { ref } from 'vue'
import Form from '../components/Form.vue'
import './Signin.css'
import { useRouter } from 'vue-router'
const router = useRouter()

const serverResponse = ref({
  "password_message": "error"
});

const formMessage = ref('')

async function SendForm({ email, password, birthday }) {
  //Try to connect
  try {
    //Send Infos
    const res = await fetch('http://localhost:8091/api/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password, birthday })
    })
    //Wait response
    const data = await res.json()

    serverResponse.valid_birthday = data["valid_birthday"]==="true";
    serverResponse.valid_email = data["valid_email"]==="true";
    serverResponse.taken_email = data["taken_email"]==="true";
    serverResponse.password_message = data["password_message"];

    //Test Cases
    if (!serverResponse.valid_email) {
      formMessage.value = "Invalid email"
      return
    }
    if (serverResponse.taken_email) {
      formMessage.value = "This email already has an account"
      return
    }
    if (serverResponse.password_message != "successfull") {
      formMessage.value = serverResponse.password_message
      return
    }
    if (!serverResponse.valid_birthday) {
      formMessage.value = "Set a valid birthdate"
      return
    }
    console.log("test")
    if (serverResponse.password_message == "successfull") {
      formMessage.value = "Account Created"
      router.push('/login')
    }
    
  } catch (e) {
    console.error('Error:', e)
  }
}

</script>

<template>
  <div class="sign-in">
    <Form title="Sign In" :signin="true" :send_function="SendForm" :password_message="formMessage"></Form>
  </div>
</template>
