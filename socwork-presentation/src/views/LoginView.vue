<script>
export default {
  name: 'LoginView',
  data() {
    return {
     input:{
       username: '',
       password: ''
     }
    };
  },
  methods: {
    async loginUser() {
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(this.input),
      };
      const response = await fetch('http://localhost:8080/accounts/login', options);
      if(response.ok){
        //this.$router.push({name: 'AccountLoginView'});
        const body = await response.body.getReader().read().then(({value}) => {
          return new TextDecoder().decode(value);
        });
        console.log('Connectée');
        console.log(body);
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
    <h1>Connexion</h1>
    <form @submit.prevent="loginUser" novalidate>
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
