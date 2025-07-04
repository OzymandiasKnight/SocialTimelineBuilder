<script setup>
import Panel from './Panel.vue'

const props = defineProps({
  image: String,
  title: String,
  user: String,
})

const valid_image = props.image !== ""

</script>

<template>
    <Panel>
        <h1>{{ props.title }}</h1>
        <h2>{{ props.user }}</h2>
        
        <div v-show="valid_image">
            <img :src="`../images/${props.image}`">
        </div>
    </Panel>
</template>