<template id="home-page">
  <app-layout>
    <div class="row" id="configGap">
      <div class="col">
        <div class="card" id="configColor">
          <h5 class="card-header">Registered Users</h5>
          <div class="card-body">
            <h5 class="card-title">{{users.length}} users</h5>
            <a href="/users" class="btn btn-primary" id="configButton">More Details...</a>
          </div>
        </div>
      </div>
    </div>
    <div class="row" id="configGap">
      <div class="col">
        <div class="card" id="configColor">
          <h5 class="card-header">Total Activities</h5>
          <div class="card-body">
            <h5 class="card-title">{{activities.length}} activities</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card" id="configColor">
          <h5 class="card-header">Fitness Tasks</h5>
          <div class="card-body">
            <h5 class="card-title">{{fitness.length}} task</h5>
          </div>
        </div>
      </div>
    </div>
    <div class="row" id="configGap">
      <div class="col">
        <div class="card" id="configColor">
          <h5 class="card-header">Hydration Status</h5>
          <div class="card-body">
            <h5 class="card-title">{{intakes.length}} statuses</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card" id="configColor">
          <h5 class="card-header">Mood Status</h5>
          <div class="card-body">
            <h5 class="card-title">{{moods.length}} statuses</h5>
          </div>
        </div>
      </div>
    </div>
    &nbsp;
    <div align="center">
      <button rel="tooltip" class="btn btn-info btn-simple btn-link" @click="refresh()">
        <i class="fa fa-refresh fa-spin"></i>
      </button>
    </div>
  </app-layout>

</template>

<script>
Vue.component('home-page',
    {
      template: "#home-page",
      data: () => ({
        users: [],
        activities: [],
        fitness: [],
        intakes: [],
        moods: []
      }),
      created: function () {
        this.refresh();
      },
      methods: {
        refresh: function () {
          axios.get("/api/users")
              .then(res => this.users = res.data)
              .catch(() => swal("Opsss!", "Error while fetching users", "error"));
          axios.get("/api/activities")
              .then(res => this.activities = res.data)
              .catch(() => console.log("Error while fetching activities"));
          axios.get("/api/fitness")
              .then(res => this.fitness = res.data)
              .catch(() => console.log("Error while fetching fitness"));
          axios.get("/api/intakes")
              .then(res => this.intakes = res.data)
              .catch(() => console.log("Error while fetching intakes"));
          axios.get("/api/moods")
              .then(res => this.moods = res.data)
              .catch(() => console.log("Error while fetching moods"));
        }
      }
    });
</script>

<style>
#configColor {
  background-color: #F8F4EA;
}
#configButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configGap {
  padding: 10px;
}
</style>