<script setup>
import './Band.css'
import { computed, watch } from 'vue'
import NavLink from './CustomNavLink.vue'

const props = defineProps({
  username: "String,",
  is_connected: false
});



</script>

<template>
  <div class="head_band">
    <nav>
      <div class="home">
        <NavLink to="/">
          <h2>Home</h2>
        </NavLink>
        <NavLink to="/post">
          <h2>Make a Post</h2>
        </NavLink>
      </div>
      <div class="accounts" v-if="props.is_connected">
        <NavLink :to="`/profile?url=${props.username}`">
          <h2>{{ username }}</h2>
        </NavLink>
      </div>
      <div class="accounts" v-if="!props.is_connected">
        <NavLink to="/login">
          <h2>Log In</h2>
        </NavLink>
        <NavLink to="/signin">
          <h2>Sign In</h2>
        </NavLink>
      </div>
    </nav>
  </div>
</template>


