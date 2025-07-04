<script setup>
import { RouterLink } from 'vue-router'

defineProps({
  to: {
    type: String,
    required: true
  }
})
</script>

<template>
  <RouterLink :to="to" class="nav_link">
    <slot />
    <div>
    </div>
  </RouterLink>
</template>

<style>

</style>