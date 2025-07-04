<script setup>

const props = defineProps({
  width: String,
  height: String,
})

</script>

<style>
@import url(style.css);
</style>

<template>
  <div class="panel" :style="{width: props.width, height: props.height}">
    <slot />
  </div>
</template>
