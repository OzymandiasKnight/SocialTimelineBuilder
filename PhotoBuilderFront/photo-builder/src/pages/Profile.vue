  <script setup>
  import { ref, watch } from 'vue'
  import { useRoute } from 'vue-router'
  import Panel from '../components/Panel.vue'
  import './Profile.css'

  const profile_data = ref({})

  const route = useRoute()
  const username = ref(route.query.url ?? '')
  const is_found = ref(false);

  function to_date(date) {

  }

  async function GetProfile({ name }) {
    //Try to connect
    try {
      //Send Infos
      const res = await fetch('http://localhost:8091/api/profile', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify( {name} )
      })
      //Wait response
      const data = await res.json()
      profile_data.value = data
      console.log("Is found :")
      is_found.value = profile_data.value['is_found'] === "true";
      console.log(is_found.value)
      
    } catch (e) {
      console.error('Error:', e)
    }
  }

  GetProfile({name: username.value});

  watch(
    () => route.query.url,
    (newUrl) => {
      username.value = newUrl ?? ''
      GetProfile({ name: username.value })
    }
  )

  </script>

  <template>
      <div class="profile">
        <Panel>
          <div v-if="is_found">
            <h1>{{ profile_data['username'] }}</h1>
            <h2>{{ profile_data['mail']}}</h2>
            <h2>{{ profile_data['birthdate'] }}</h2>
          </div>
          <div v-else>
            <h1>User not found</h1>
          </div>

        </Panel>
      </div>

  </template>
