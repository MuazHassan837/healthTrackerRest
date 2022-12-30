<template id="user-activity-overview">
  <app-layout>
    <div>
      <h3>Activities list </h3>
      <ul>
        <li v-for="activity in activities">
          {{activity.id}}: {{activity.description}} for {{activity.duration}} minutes
        </li>
      </ul>
    </div>
  </app-layout>
</template>

<script>
Vue.component("user-activity-overview",{
  template: "#user-activity-overview",
  data: () => ({
    activities: [],
  }),
  created() {

    const userId = this.$javalin.pathParams["user-id"];

    if (userId) {
      const url = `/api/users/${userId}/activities`
      axios.get(url)
          .then(res => this.activities = res.data)
          .catch(() => swal("Opsss!", "Error fetching activities for user"+ userId, "error"));
    } else {
      axios.get("/api/activities")
          .then(res => this.activities = res.data)
          .catch(() => swal("Opsss!", "Error fetching activities", "error"));
    }

  }
});
</script>


