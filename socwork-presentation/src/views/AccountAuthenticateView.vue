<script >
export default {
  name: 'AccountAuthenticateView',
  data() {
    return {
      input:{
        username: '',
        password: ''
      }
    };
  },
  methods: {
    async submit() {
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.input),
      };
      const response = await fetch('http://localhost:8080/accounts/authenticate', options);

      if( response.status === 401 ){
        alert('Bad Crédentials');
      }else{
        const data = await response.text();
        console.log(data);
      }
      console.log(this.input);
    }
  }
}
</script>

<template>
  <div class="account-create">
    <h1>Crée un compte</h1>
    <form @submit.prevent="submit" novalidate>
      <label for="username">Nom d'utilisateur</label>
      <input type="email" id="username" name="username" v-model="input.username" required>
      <label for="password">Mot de passe</label>
      <input type="password" id="password" name="password" v-model="input.password" required>
      <button type="submit" >Je m'authentifie</button>
    </form>
    <div> {{ input.username }} </div>
  </div>
</template>

<style scoped>

</style>
