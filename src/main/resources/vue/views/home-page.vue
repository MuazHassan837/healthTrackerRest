<!-- the "home-page" element is passed as a parameter to VueComponent in the JavalinConfig file -->
<template id="home-page">
  <app-layout>
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">Registered Users</h5>
          <div class="card-body">
            <h5 class="card-title">{{users.length}} users</h5>
            <a href="/users" class="btn btn-primary">More Details...</a>
          </div>
        </div>
      </div>
    </div>
    &nbsp;
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">Total Activities</h5>
          <div class="card-body">
            <h5 class="card-title">{{activities.length}} activities</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
          <h5 class="card-header">Fitness Tasks</h5>
          <div class="card-body">
            <h5 class="card-title">{{fitness.length}} task</h5>
          </div>
        </div>
      </div>
    </div>
    &nbsp;
    <div class="row">
      <div class="col">
        <div class="card">
          <h5 class="card-header">Hydration Status</h5>
          <div class="card-body">
            <h5 class="card-title">{{intakes.length}} statuses</h5>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card">
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
              .catch(() => alert("Error while fetching users"));
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
<!--another test-->