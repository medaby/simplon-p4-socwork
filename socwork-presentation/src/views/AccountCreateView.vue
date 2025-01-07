<script>
export default {
  name: 'AccountCreateView',
  data() {
    return {
     input:{
       username: '',
       password: ''
     }
    };
  },
  methods: {
    async createAccount() {
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.input),
      };
      const response = await fetch('https://localhost:8443/accounts', options);

      console.log(response.status);

      if(response.ok){
        //this.$router.push({name: 'AccountLoginView'});
        alert('Compte créé avec succès');
      }else{
        console.error('Une erreur est survenue');
      }
      console.log(this.input);
    }
  }
}
</script>

<template>
  <div class="account-create">
    <h1>Crée un compte</h1>
    <form @submit.prevent="createAccount" novalidate>
      <label for="username">Nom d'utilisateur</label>
      <input type="email" id="username" name="username" v-model="input.username" required>
      <label for="password">Mot de passe</label>
      <input type="password" id="password" name="password" v-model="input.password" required>
      <button type="submit">Créer un compte</button>
    </form>
   <div> {{ input.username }} </div>
  </div>
</template>

<style scoped>
label+input {
  display: block;
  margin-bottom: 1rem;
}

label::after {
  content: ' *';
  color: red;
}

h1 {
  color: blue;
  font-weight: bold;
}
</style>
