<script setup>
import { ref } from 'vue'
import Panel from './Panel.vue'

defineProps({
  image: String
})

const count = ref(0)
</script>

<style>
@import url(style.css);
</style>

<template>
  <Panel width='400px' height='400px'>
    <img :src>
  </Panel>
</template>
