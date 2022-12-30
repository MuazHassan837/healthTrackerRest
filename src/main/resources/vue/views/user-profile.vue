<template id="user-profile">
  <app-layout>
    <div v-if="noUserFound">
      <p> We're sorry, we were not able to retrieve this user.</p>
      <p> View <a :href="'/users'">all users</a>.</p>
    </div>
    <!--     displays the user profile buttons-->
    <div class="card bg-light mb-3" v-if="!noUserFound">
      <div class="card-header" id="configColor">
        <div class="row">
          <div class="col-6"> User Profile</div>
          <div class="col" align="right">
            <button rel="tooltip" title="Update"
                    class="btn btn-info btn-simple btn-link"
                    @click="updateUser()">
              <i class="far fa-save" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete"
                    class="btn btn-info btn-simple btn-link"
                    @click="deleteUser()">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <!--       displays the user profile and change can be done on it-->
      <div class="card-body" id="configColor">
        <form>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-id">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="user.id" name="id" readonly placeholder="Id"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-name">Name</span>
            </div>
            <input type="text" class="form-control" v-model="user.name" name="name" placeholder="Name"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-user-email">Email</span>
            </div>
            <input type="email" class="form-control" v-model="user.email" name="email" placeholder="Email"/>
          </div>
        </form>
      </div>
    </div>

    <!--       starting point for activity feed-->
    <div class="card-footer text-left" id="configColor">
      <p> Your activity analysis: {{activityAnalysis}}</p>
      <p v-if="activities.length == 0"> No activities yet...</p>
      <p v-if="activities.length > 0"> Activities so far...</p>
      <div class="col" align="right">
        <button rel="tooltip" title="Add"
                class="btn btn-info btn-simple btn-link"
                @click="hideForm =!hideForm">
          <i class="fa fa-plus" aria-hidden="true"></i>
        </button>
      </div>

      <div class="card-body" :class="{ 'd-none': hideForm}">
        <form id="addActivity">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-id">ID</span>
            </div>
            <input type="text" class="form-control" v-model="formData.id" name="id" placeholder="Activity ID"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-description">Description</span>
            </div>
            <input type="text" class="form-control" v-model="formData.description" name="description"
                   placeholder="Activity Description"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-duration">Duration</span>
            </div>
            <input type="number" class="form-control" v-model="formData.duration" name="duration"
                   placeholder="Activity Duration"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-calories">Calories</span>
            </div>
            <input type="number" class="form-control" v-model="formData.calories" name="calories"
                   placeholder="Activity Calories"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-userId">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="formData.userId" name="userId"
                   placeholder="For User ID"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addActivity()">Add
          Activity
        </button>
      </div>
      <!--       displays the activity and actions on it-->
      <div class="list-group list-group-flush">
        <div class="list-group-item d-flex align-items-start" id="configColor"
             v-for="(activity,index) in activities" v-bind:key="index">
          <div class="mr-auto p-2">
            <span><a> {{activity.description}} for {{activity.duration}} minutes</a></span>
          </div>
          <div class="p2">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
                    @click="getActivity(activity.id)">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteActivity(activity.id, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
      <!--       can be used to update/edit the activity-->
      <div class="card-body" :class="{ 'd-none': hideForm1}">
        <form id="editActivity">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-id">ID</span>
            </div>
            <input type="text" class="form-control" v-model="currActivity.id" name="id" readonly
                   placeholder="Activity ID"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-description">Description</span>
            </div>
            <input type="text" class="form-control" v-model="currActivity.description" name="description"
                   placeholder="Activity Description"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-duration">Duration</span>
            </div>
            <input type="number" class="form-control" v-model="currActivity.duration" name="duration"
                   placeholder="Activity Duration"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-calories">Calories</span>
            </div>
            <input type="number" class="form-control" v-model="currActivity.calories" name="calories"
                   placeholder="Activity Calories"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-activity-userId">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="currActivity.userId" name="userId"
                   placeholder="For User ID"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
                @click="editActivity(currActivity.id)">Done
        </button>
      </div>
    </div>
    &nbsp;

    <!--fitness app-->
    <div class="card-footer text-left" id="configColor">
      <p> Your fitness Analysis : {{fitnessAnalysis}}</p>
      <p v-if="fitnessData.length == 0"> No fitness stuff yet...</p>
      <p v-if="fitnessData.length > 0"> Fitness so far...</p>
      <div class="col" align="right">
        <button rel="tooltip" title="Add"
                class="btn btn-info btn-simple btn-link"
                @click="fitnessForm =!fitnessForm">
          <i class="fa fa-plus" aria-hidden="true"></i>
        </button>
      </div>
      <!-- add to the fitness form -->
      <div class="card-body" :class="{ 'd-none': fitnessForm}" id="configColor">
        <form id="addFitness">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-fitness-id">ID</span>
            </div>
            <input type="number" class="form-control" v-model="fitnessFormData.id" name="id" placeholder="Fitness ID"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-fitness-dayType">Day Type</span>
            </div>
            <input type="text" class="form-control" v-model="fitnessFormData.dayType" name="dayType"
                   placeholder="Leg/chest Day..."/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-fitness-userId">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="fitnessFormData.userId" name="userId"
                   placeholder="For User ID"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addFitness()">Add
          Fitness
        </button>
      </div>
      <!--     displays the fitness and actions on it-->
      <div class="list-group list-group-flush">
        <div class="list-group-item d-flex align-items-start" id="configColor"
             v-for="(fitness,index) in fitnessData" v-bind:key="index">
          <div class="mr-auto p-2">
            <span><a> {{fitness.dayType}} on {{fitness.started.substring(0, 10)}} at {{fitness.started.substring(11, 16)}}</a></span>
          </div>
          <div class="p2">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
                    @click="getFitness(fitness.id)">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteFitness(fitness.id, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
        <!--       get be used to update/edit the fitness-->
        <div class="card-body" :class="{ 'd-none': fitnessEditForm}">
          <form id="editFitness">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-fitness-id">ID</span>
              </div>
              <input type="text" class="form-control" v-model="currFitness.id" name="id" readonly
                     placeholder="Fitness ID"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-fitness-dayType">Day Type</span>
              </div>
              <input type="text" class="form-control" v-model="currFitness.dayType" name="dayType"
                     placeholder="Leg/chest Day..."/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-fitness-userId">User ID</span>
              </div>
              <input type="number" class="form-control" v-model="currFitness.userId" name="userId"
                     placeholder="For User ID"/>
            </div>
          </form>
          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
                  @click="editFitness(currFitness.id)">Done
          </button>
        </div>
      </div>
    </div>
    &nbsp;
    <!--    intake app-->
    <div class="card-footer text-left" id="configColor">
      <p>Your Intake Analysis: {{intakeAnalysis}}</p>
      <p v-if="inTakeData.length == 0"> Be hydrated...</p>
      <p v-if="inTakeData.length > 0"> Hydrated so far...</p>
      <div class="col" align="right">
        <button rel="tooltip" title="Add"
                class="btn btn-info btn-simple btn-link"
                @click="inTakeForm =!inTakeForm">
          <i class="fa fa-plus" aria-hidden="true"></i>
        </button>
      </div>
      <!-- add to the intake form -->
      <div class="card-body" :class="{ 'd-none': inTakeForm}">
        <form id="addIntake">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-intake-id">ID</span>
            </div>
            <input type="number" class="form-control" v-model="inTakeFormData.id" name="id" placeholder="Intake ID"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-intake-amountltr">Amount</span>
            </div>
            <input type="number" class="form-control" v-model="inTakeFormData.amountltr" name="amountltr"
                   placeholder="Enter in Liter..."/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-intake-substance">Substance</span>
            </div>
            <input type="text" class="form-control" v-model="inTakeFormData.substance" name="substance"
                   placeholder="Water/Protein Shake..."/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-intake-userId">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="inTakeFormData.userId" name="userId"
                   placeholder="For User ID"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addInTake()">Add Intake
        </button>
      </div>
      <!--     displays the intake and actions on it-->
      <div class="list-group list-group-flush">
        <div class="list-group-item d-flex align-items-start" id="configColor"
             v-for="(inTake,index) in inTakeData" v-bind:key="index">
          <div class="mr-auto p-2">
            <span><a> Took {{inTake.amountltr}} liter of {{inTake.substance}} on {{inTake.started.substring(0, 10)}} at {{inTake.started.substring(11, 16)}}</a></span>
          </div>
          <div class="p2">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
                    @click="getInTake(inTake.id)">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteInTake(inTake.id, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
        <!--       get be used to update/edit the intake-->
        <div class="card-body" :class="{ 'd-none': inTakeEditForm}">
          <form id="editIntake">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-intake-id">ID</span>
              </div>
              <input type="text" class="form-control" v-model="currInTake.id" name="id" readonly
                     placeholder="Intake ID"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-intake-amountltr">Amount</span>
              </div>
              <input type="number" class="form-control" v-model="currInTake.amountltr" name="amountltr"
                     placeholder="Enter in Liter..."/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-intake-substance">Substance</span>
              </div>
              <input type="text" class="form-control" v-model="currInTake.substance" name="substance"
                     placeholder="Water/Protein Shake..."/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-intake-userId">User ID</span>
              </div>
              <input type="number" class="form-control" v-model="currInTake.userId" name="userId"
                     placeholder="For User ID"/>
            </div>
          </form>
          <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
                  @click="editInTake(currInTake.id)">Done
          </button>
        </div>
      </div>
    </div>
    &nbsp;
    <!--    mood app-->
    <div class="card-footer text-left" id="configColor">
      <p> Your Mood Analysis: {{moodAnalysis}}</p>
      <p v-if="moodData.length == 0"> Be happy...</p>
      <p v-if="moodData.length > 0"> Mood swings so far...</p>
      <div class="col" align="right">
        <button rel="tooltip" title="Add"
                class="btn btn-info btn-simple btn-link"
                @click="moodForm =!moodForm">
          <i class="fa fa-plus" aria-hidden="true"></i>
        </button>
      </div>
      <!-- add to the mood form -->
      <div class="card-body" :class="{ 'd-none': moodForm}">
        <form id="addMood">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-mood-id">ID</span>
            </div>
            <input type="number" class="form-control" v-model="moodFormData.id" name="id" placeholder="Mood ID"/>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <label class="input-group-text" for="inputGroupSelect01">Current Mood</label>
            </div>
              <select class="custom-select" id="inputGroupSelect01" v-model="moodFormData.mood" name="mood">
                 <option value="Happy">Happy</option>
                <option value="Sad">Sad</option>
                <option value="Uncertain">Uncertain</option>
            </select>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="input-mood-userId">User ID</span>
            </div>
            <input type="number" class="form-control" v-model="moodFormData.userId" name="userId"
                   placeholder="For User ID"/>
          </div>
        </form>
        <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link" @click="addMood()">Add Mood
        </button>
      </div>
      <!--     displays the mood and actions on it-->
      <div class="list-group list-group-flush" >
        <div class="list-group-item d-flex align-items-start" id="configColor"
             v-for="(mood,index) in moodData" v-bind:key="index">
          <div class="mr-auto p-2">
            <span><a> User: {{mood.userId}} was feeling {{mood.mood}} on {{mood.started.substring(0, 10)}} at {{mood.started.substring(11, 16)}}</a></span>
          </div>
          <div class="p2">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link"
                    @click="getMood(mood.id)">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
            <button rel="tooltip" title="Delete" class="btn btn-info btn-simple btn-link"
                    @click="deleteMood(mood.id, index)">
              <i class="fas fa-trash" aria-hidden="true"></i>
            </button>
          </div>
        </div>
        <!--       get be used to update/edit the mood-->
        <div class="card-body" :class="{ 'd-none': moodEditForm}">
          <form id="editMood">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-mood-id">ID</span>
              </div>
              <input type="text" class="form-control" v-model="currMood.id" name="id" readonly
                     placeholder="Mood ID"/>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <label class="input-group-text" for="inputGroupSelect01">Current Mood</label>
              </div>
              <select class="custom-select" id="inputGroupSelect01" v-model="currMood.mood" name="mood">
                <option value="Happy">Happy</option>
                <option value="Sad">Sad</option>
                <option value="Uncertain">Uncertain</option>
              </select>
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="input-mood-userId">User ID</span>
              </div>
              <input type="number" class="form-control" v-model="currMood.userId" name="userId"
                     placeholder="For User ID"/>
            </div>
          </form>
          <button rel="tooltip" title="Update Mood" class="btn btn-info btn-simple btn-link"
                  @click="editMood(currMood.id)">Done
          </button>
        </div>
      </div>
    </div>
    <!--     refresh button to refresh the page-->
    &nbsp;
    <div align="center">
      <button rel="tooltip" class="btn btn-info btn-simple btn-link" @click="refresh()">
        <i class="fa fa-refresh fa-spin"></i>
      </button>
    </div>

  </app-layout>
</template>

<script>
Vue.component("user-profile", {
  template: "#user-profile",
  data: () => ({
    user: null,
    noUserFound: false,
    activities: [],
    fitnessData: [],
    inTakeData: [],
    moodData: [],
    fitnessFormData: [],
    inTakeFormData: [],
    moodFormData: [],
    fitnessForm: true,
    fitnessEditForm: true,
    inTakeForm: true,
    inTakeEditForm: true,
    moodForm: true,
    moodEditForm: true,
    formData: [],
    currActivity: [],
    currFitness: [],
    currInTake: [],
    currMood: [],
    hideForm: true,
    hideForm1: true,


    activityAnalysis: "Nothing Yet",
    fitnessAnalysis: "Nothing Yet",
    intakeAnalysis: "Nothing Yet",
    moodAnalysis: "Nothing Yet",
  }),
  created: function () {
    const userId = this.$javalin.pathParams["user-id"];
    const url = `/api/users/${userId}`
    axios.get(url)
        .then(res => this.user = res.data)
        .catch(error => {
          console.log("No user found for id passed in the path parameter: " + error)
          this.noUserFound = true
        })
    axios.get(url + `/activities`)
        .then(res => {this.activities = res.data
        this.computeActivity(this.activities)
        })
        .catch(error => {
          console.log("No activities added yet (this is ok): " + error)
        })
    axios.get(url + `/fitness`)
        .then(res => {
          this.fitnessData = res.data
          this.computeFitness(this.fitnessData)
        })
        .catch(error => {
          console.log("No fitness added yet (this is ok): " + error)
        })
    axios.get(url + `/intakes`)
        .then(res =>
            {this.inTakeData = res.data
              this.computeHydration(this.inTakeData)
            })
        .catch(error => {
          console.log("No intake added yet (this is ok): " + error)
        })
    axios.get(url + `/moods`)
        .then(res => {
          this.moodData = res.data
          this.computeMood(this.moodData)
        })
        .catch(error => {
          console.log("No mood added yet (this is ok): " + error)
        })
  },
  methods: {
    computeMood : function (comingMoodData) {
      let uniqueMoods = new Set();
      for (indMood in comingMoodData) {
        uniqueMoods.add(comingMoodData[indMood].mood)
      }
      if (uniqueMoods.size == 1) {
        if (uniqueMoods.has("Happy")) {
          this.moodAnalysis = "You are feeling happy most of the time. That's amazing."
        } else if (uniqueMoods.has("Sad")) {
          this.moodAnalysis = "You are feeling sad most of the time. Cheer up..."
        } else if (uniqueMoods.has("Uncertain")) {
          this.moodAnalysis = "You are feeling uncertain most of the time. Try to be positive!"
        }
      } else {
        this.moodAnalysis = "You are feeling different moods. Try to be positive!"
      }

    },
    computeHydration : function (comingInTakeData) {
      let totalIntake = 0;
      let favDrink = new Set();
      for (indIntake in comingInTakeData) {
        totalIntake += comingInTakeData[indIntake].amountltr
        favDrink.add(comingInTakeData[indIntake].substance)
      }

      if (totalIntake < 1.9) {
        this.intakeAnalysis = "You are not hydrated enough. Try to drink more Water i.e."
      } else {
        if (favDrink.size == 1){
          this.intakeAnalysis = "You are well hydrated. Keep it up, your favorite drink is " + favDrink.values().next().value
        }else {
          this.intakeAnalysis = "You are well hydrated. Keep it up"
        }
      }
    },
    computeFitness : function (comingFitnessData) {
      if (comingFitnessData.length <= 2) {
          this.fitnessAnalysis = "You are not active enough. Time to hit the gym."
      } else {
          this.fitnessAnalysis = "You are active, well done. Keep rocking!"
      }
    },
    computeActivity : function (comingActivityData) {
      var calories = 0;
      var activity = new Set();
      for (indActivityData in comingActivityData) {
        calories += comingActivityData[indActivityData].calories
        activity.add(comingActivityData[indActivityData].description)
      }
      if (calories >= 2000 && calories <= 2500){
        this.activityAnalysis = "You are going healthy. Keep it up!"

      }else if (calories > 2500){
        this.activityAnalysis = "You are doing" + activity.values().next().value + " too much. Try to balance with the diet"
      }else {
        this.activityAnalysis = "You are not active enough. Try to " + activity.values().next().value + " for more duration"
      }
    },
    updateUser: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const url = `/api/users/${userId}`
      axios.patch(url,
          {
            name: this.user.name,
            email: this.user.email
          })
          .then(response =>
              this.user.push(response.data))
          .catch(error => {
            console.log(error)
          })
      swal("Success", "Updated!", "success")
    },
    deleteUser: function () {
      if (confirm("Do you really want to delete?")) {
        const userId = this.$javalin.pathParams["user-id"];
        const url = `/api/users/${userId}`
        axios.delete(url)
            .then(response => {
              swal("Success", "Deleted!", "success")
              //display the /users endpoint
              window.location.href = '/users';
            })
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    refresh: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const url = `/api/users/${userId}`
      axios.get(url + `/activities`)
          .then(res => this.activities = res.data)
          .catch(error => {
            console.log("No activities added yet (this is ok): " + error)
          })
      axios.get(url + `/fitness`)
          .then(res => this.fitnessData = res.data)
          .catch(error => {
            console.log("No fitness added yet (this is ok): " + error)
          })
      axios.get(url + `/intakes`)
          .then(res => this.inTakeData = res.data)
          .catch(error => {
            console.log("No intake added yet (this is ok): " + error)
          })
      axios.get(url + `/moods`)
          .then(res => this.moodData = res.data)
          .catch(error => {
            console.log("No mood added yet (this is ok): " + error)
          })
    },
    deleteActivity: function (id, index) {
      if (confirm('Are you sure you want to delete this activity? This action cannot be undone.', 'Warning')) {

        const url = `/api/activities/${id}`;
        axios.delete(url)
            .then(response =>
                this.activities.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    deleteFitness: function (id, index) {
      if (confirm('Are you sure you want to delete this fitness? This action cannot be undone.', 'Warning')) {

        const url = `/api/fitness/${id}`;
        axios.delete(url)
            .then(response =>
                this.fitnessData.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    deleteInTake: function (id, index) {
      if (confirm('Are you sure you want to delete this intake? This action cannot be undone.', 'Warning')) {

        const url = `/api/intakes/${id}`;
        axios.delete(url)
            .then(response =>
                this.inTakeData.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    deleteMood: function (id, index) {
      if (confirm('Are you sure you want to delete this mood? This action cannot be undone.', 'Warning')) {

        const url = `/api/moods/${id}`;
        axios.delete(url)
            .then(response =>
                this.moodData.splice(index, 1).push(response.data))
            .catch(function (error) {
              console.log(error)
            });
      }
    },
    addActivity: function () {
      const url = `/api/activities`;
      axios.post(url,
          {
            id: this.formData.id,
            description: this.formData.description,
            duration: this.formData.duration,
            calories: this.formData.calories,
            started: new Date().toISOString(),
            userId: this.formData.userId
          })
          .then(response => {
            this.activities.push(response.data)
            this.hideForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    addFitness: function () {
      const url = `/api/fitness`;
      axios.post(url,
          {
            id: this.fitnessFormData.id,
            dayType: this.fitnessFormData.dayType,
            started: new Date().toISOString(),
            userId: this.fitnessFormData.userId
          })
          .then(response => {
            this.fitnessData.push(response.data)
            this.fitnessForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    addInTake: function () {
      const url = `/api/intakes`;
      axios.post(url,
          {
            id: this.inTakeFormData.id,
            amountltr: this.inTakeFormData.amountltr,
            substance: this.inTakeFormData.substance,
            userId: this.inTakeFormData.userId,
            started: new Date().toISOString()
          })
          .then(response => {
            this.inTakeData.push(response.data)
            this.inTakeForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    addMood: function () {
      const url = `/api/moods`;
      axios.post(url,
          {
            id: this.moodFormData.id,
            mood: this.moodFormData.mood,
            userId: this.moodFormData.userId,
            started: new Date().toISOString()
          })
          .then(response => {
            this.moodData.push(response.data)
            this.moodForm = true;
          })
          .catch(error => {
            console.log(error)
          })
    },
    editActivity: function (id) {
      const url = `/api/activities/${id}`;
      axios.patch(url,
          {
            description: this.currActivity.description,
            duration: this.currActivity.duration,
            calories: this.currActivity.calories,
            started: new Date().toISOString(),
            userId: this.currActivity.userId
          })
          .then(_ =>
              this.refresh()
          ).catch(function (error) {
        console.log(error)
      })
      this.hideForm1 = !this.hideForm1
    },
    editFitness: function (id) {
      const url = `/api/fitness/${id}`;
      axios.patch(url,
          {
            id: this.currFitness.id,
            dayType: this.currFitness.dayType,
            started: new Date().toISOString(),
            userId: this.currFitness.userId,
          })
          .then(_ =>
              this.refresh()
          ).catch(function (error) {
        console.log(error)
      })
      this.fitnessEditForm = !this.fitnessEditForm
    },
    editInTake: function (id) {
      const url = `/api/intakes/${id}`;
      axios.patch(url,
          {
            id: this.currInTake.id,
            amountltr: this.currInTake.amountltr,
            substance: this.currInTake.substance,
            userId: this.currInTake.userId,
            started: new Date().toISOString()
          })
          .then(_ =>
              this.refresh()
          ).catch(function (error) {
        console.log(error)
      })
      this.inTakeEditForm = !this.inTakeEditForm
    },
    editMood: function (id) {
      const url = `/api/moods/${id}`;
      axios.patch(url,
          {
            id: this.currMood.id,
            mood: this.currMood.mood,
            userId: this.currMood.userId,
            started: new Date().toISOString()
          })
          .then(_ =>
              this.refresh()
          ).catch(function (error) {
        console.log(error)
      })
      this.moodEditForm = !this.moodEditForm
    },
    getActivity: function (id) {
      this.hideForm1 = false
      const url = `/api/activities/${id}`;
      axios.get(url)
          .then(res => {
            this.currActivity = res.data
            console.log(this.currActivity)
          })
          .catch(error => {
            console.log("No activities added yet (this is ok): " + error)
          })

    },
    getFitness: function (id) {
      this.fitnessEditForm = false
      const url = `/api/fitness/${id}`;
      axios.get(url)
          .then(res => {
            this.currFitness = res.data
            console.log(this.currFitness)
          })
          .catch(error => {
            console.log("No fitness added yet (this is ok): " + error)
          })

    },
    getInTake: function (id) {
      this.inTakeEditForm = false
      const url = `/api/intakes/${id}`;
      axios.get(url)
          .then(res => {
            this.currInTake = res.data
            console.log(this.currInTake)
          })
          .catch(error => {
            console.log("No intake added yet (this is ok): " + error)
          })

    },
    getMood: function (id) {
      this.moodEditForm = false
      const url = `/api/moods/${id}`;
      axios.get(url)
          .then(res => {
            this.currMood = res.data
            console.log(this.currMood)
          })
          .catch(error => {
            console.log("No mood added yet (this is ok): " + error)
          })

    },
  }
});
</script>

<style>
#configColor {
  background-color: #F8F4EA;
}
</style>