<script setup>
import Panel from '../components/Panel.vue'
import './PostPage.css'

function send_function(title, image) {
    
}

async function SendPost({ title, image }) {
  //Try to connect
  try {
    //Send Infos
    const res = await fetch('http://localhost:8091/api/makepost', {
      method: 'POST',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title, image})
    })
    //Wait response
    const data = await res.json()
    
    console.log('Server response', data);

    console.log(serverResponse)
    
  } catch (e) {
    console.error('Error:', e)
  }
}

</script>

<template>
    <div class="post-page">
        <Panel width="600px" height="fit-content">
            <h1>Post name</h1>
            <div class="input">
                <input type="text" placeholder="Your post title" maxlength="70" v-model="title">
            </div>

            <div class="input">
            <button @click="send_function({
            title: title,
            image: ''
            })">
            Publish post
            </button>
    </div>
        </Panel>

    </div>
</template>
