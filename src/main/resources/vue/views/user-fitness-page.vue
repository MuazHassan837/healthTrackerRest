<template id="user-fitness-page">
  <app-layout>
    <p class="h2">Welcome to JETFIT</p>
    <div class="container" id="firstHeaderGap">
      <form>
        <div class="form-group row">
          <label for="inputEmail3" class="col-sm-2 form-control-label">User Name</label>
          <div class="col-sm-10">
            <input type="username" class="form-control" id="inputEmail3" v-model="inputData.name" name="name" placeholder="Enter your user name">
          </div>
        </div>
        <div class="form-group row">
          <label for="inputPassword3" class="col-sm-2 form-control-label">User ID</label>
          <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPassword3" v-model="inputData.id" name="id" placeholder="Enter your ID">
          </div>
        </div>
      </form>
      <button type="button" class="btn btn-outline-primary" @click="verifyUser()">Submit</button>
    </div>
  </app-layout>
</template>


<script>
Vue.component("user-fitness-page",{
  template: "#user-fitness-page",
  data: () => ({
    inputData: [],
  }),
  methods: {
    verifyUser : function () {
      const userId = this.inputData.id;
      const url = `/api/users/${userId}`

      axios.get(url)
        .then(response => {
          console.log(response.status);
          if (response.status == 200 && response.data.name == this.inputData.name) {
            swal("Success", "User exits!", "success");
            setTimeout(function(){
              window.location.href = `/users/${userId}/fitness`;
            }, 1500);
          } else {
            swal("Opsss!", "User doesn't exits", "error");
          }
        })
        .catch(error => {
          console.log(error);
          swal("Opsss!", "User doesn't exits", "error");
        });
    },
  }
});
</script>

<style>
#firstHeaderGap{
  padding-top: 25px;
}
</style>