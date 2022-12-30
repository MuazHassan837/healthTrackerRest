<template id="jet-fit-page">
  <app-layout>
    <p class="h3">Exercises</p>

    <div class="alert alert-warning" role="alert">
      <strong>Exercises</strong> Select and record the exercises.
    </div>
    <div class="row">
      <div class="col" >
        <img src="https://user-images.githubusercontent.com/113602921/210082278-46572de1-07b3-4de5-bda6-4bff0630f13e.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Triceps</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="triType">
            The bench dip exercise is one of the most basic and best workouts for building the triceps and bigger arms
          </p>
          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configTriButton">
              Bench Dip
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="triceps1()">Bench Dip</button>
              <button class="dropdown-item" type="button" @click="triceps2()">Machine Dip</button>
              <button class="dropdown-item" type="button" @click="triceps3()">Weighted Tricep Dip</button>
              <button class="dropdown-item" type="button" @click="triceps4()">Dumbbell Tricep</button>
              <button class="dropdown-item" type="button" @click="triceps5()">Cable Rope Triceps</button>
            </div>
          </div>

          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
      <div class="col">
        <img src="https://user-images.githubusercontent.com/113602921/210082425-88f0eab8-a78c-414e-9b4b-c0cdf4494672.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Chest</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="chestType">
            The push up exercise is one of the best exercises available for building muscle and strength in the chest, shoulders, and triceps
          </p>

          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configChestButton">
              Push-Up
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="chestType1()">Push-Up</button>
              <button class="dropdown-item" type="button" @click="chestType2()">Machine Fly</button>
              <button class="dropdown-item" type="button" @click="chestType3()">Dumbbell Fly</button>
              <button class="dropdown-item" type="button" @click="chestType4()">Bench Press</button>
              <button class="dropdown-item" type="button" @click="chestType5()">Cable Cross-Over</button>
            </div>
          </div>
          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
    </div>

    <div class="row" id="configGap">
      <div class="col" >
        <img src="https://user-images.githubusercontent.com/113602921/210083097-153e7b1e-fd90-4f0d-ae3e-60c78c3dfa51.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Shoulder</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="shoulderType">
            The dumbbell lateral raise exercise helps build strong shoulders and muscle mass
          </p>
          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configshldButton">
              Dumbbell Lateral Raise
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="shld1()">Dumbbell Lateral Raise</button>
              <button class="dropdown-item" type="button" @click="shld2()">Dumbbell Shoulder Press</button>
              <button class="dropdown-item" type="button" @click="shld3()">Barbell Military Press</button>
              <button class="dropdown-item" type="button" @click="shld4()">Cable Rope Face Pull</button>
              <button class="dropdown-item" type="button" @click="shld5()">Machine Shoulder Press</button>
            </div>
          </div>

          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
      <div class="col">
        <img src="https://user-images.githubusercontent.com/113602921/210083239-553f7d48-557a-42d9-be13-b93ed063221d.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Biceps</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="biType">
            The barbell curl is a basic bicep exercise that helps increase the size of the muscles.
          </p>

          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configBiButton">
              Barbell Curl
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="biType1()">Barbell Curl</button>
              <button class="dropdown-item" type="button" @click="biType2()">Hammer Curl</button>
              <button class="dropdown-item" type="button" @click="biType3()">Bicep Curl</button>
              <button class="dropdown-item" type="button" @click="biType4()">Bar Curl</button>
              <button class="dropdown-item" type="button" @click="biType5()">Concentration Curl</button>
            </div>
          </div>
          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
    </div>


    <div class="row" id="configGap">
      <div class="col" >
        <img src="https://user-images.githubusercontent.com/113602921/210083308-c4189d59-ec3f-40f0-8e94-10a2a6f51029.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Core</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="coreType">
            Start  by kneeling on all fours and aligning both hands right below your shoulders keep your knees beneath your hips. Extend both of your feet out behind you and squeeze on your core muscles, making sure that your body is aligned straight.
          </p>
          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configcoreButton">
              Plank
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="coreTrigger1()">Plank</button>
              <button class="dropdown-item" type="button" @click="coreTrigger2()">Machine Ab Crunch</button>
              <button class="dropdown-item" type="button" @click="coreTrigger3()">Crunches</button>
              <button class="dropdown-item" type="button" @click="coreTrigger4()">Leg Raise</button>
              <button class="dropdown-item" type="button" @click="coreTrigger5()">Cable Crunch</button>
            </div>
          </div>

          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
      <div class="col">
        <img src="https://user-images.githubusercontent.com/113602921/210083493-383d2bb8-1848-4555-9bd5-c6d94e3cab9a.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Back</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="backType">
            The wide-grip lat pulldown is one of the classic bodybuilding exercises used to help build a stronger back
          </p>

          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configBackButton">
              Wide Grip Lat Pulldown
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="backType1()">Wide Grip Lat Pulldown</button>
              <button class="dropdown-item" type="button" @click="backType2()">Cable Seated Row</button>
              <button class="dropdown-item" type="button" @click="backType3()">Barbell Deadlift</button>
              <button class="dropdown-item" type="button" @click="backType4()">Barbell Bent-Over Row</button>
              <button class="dropdown-item" type="button" @click="backType5()">Dumbbell One-Arm Row</button>
            </div>
          </div>
          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
    </div>


    <div class="row" id="configGap">
      <div class="col" >
        <img src="https://user-images.githubusercontent.com/113602921/210083643-2f03f4bc-df10-4ecf-8a8c-c6eb91ddd6a7.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Forearms</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="forearmType">
            Stand with your feet shoulder-width apart. Bending at the keens pick up barbell. With your palms facing downward lift the barbell to your chest while remaining up straight.
          </p>
          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configforearmButton">
              Barbell reverse curl
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="forearmTrigger1()">Barbell reverse curl</button>
              <button class="dropdown-item" type="button" @click="forearmTrigger2()">Wrist Curl</button>
              <button class="dropdown-item" type="button" @click="forearmTrigger3()">Grip Curl</button>
              <button class="dropdown-item" type="button" @click="forearmTrigger4()">Wrist Roller</button>
              <button class="dropdown-item" type="button" @click="forearmTrigger5()">Reverse Curl</button>
            </div>
          </div>

          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
      <div class="col">
        <img src="https://user-images.githubusercontent.com/113602921/210083729-7bcd90fd-8792-44e8-a377-71ccf7b5c2fd.png" class="img-fluid" alt="Responsive image" width="100%">
        <h3 class="card-header" id="configTitleColor">Calves</h3>
        <div class="card-block" id="configBackColor">
          <p class="card-text" id="calType">
            The seated calf raise exercise is one of the simplest and most effective calf workouts.
          </p>

          <div class="btn-group">
            <button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="configcalButton">
              Seated Calf Raise
            </button>
            <div class="dropdown-menu dropdown-menu-right" id="configBackColor">
              <button class="dropdown-item" type="button" @click="calType1()">Seated Calf Raise</button>
              <button class="dropdown-item" type="button" @click="calType2()">Standing Calf Raise</button>
              <button class="dropdown-item" type="button" @click="calType3()">Leg Press</button>
              <button class="dropdown-item" type="button" @click="calType4()">Barbell Standing Calf Raise</button>
              <button class="dropdown-item" type="button" @click="calType5()">Dumbbell Standing Calf Raise</button>
            </div>
          </div>
          <button rel="tooltip" title="Add"
                  class="btn btn-info btn-simple btn-link"
                  @click="addFitnessActivity()" id="configAlign">
            <i class="far fa-save"></i>
          </button>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
Vue.component("jet-fit-page",{
  template: "#jet-fit-page",
  data: () => ({
    currCount : 1,
    dropdownID : "configTriButton",
  }),
  methods: {
    chestType1: function() {
      this.dropdownID = "configChestButton";
      document.getElementById("configChestButton").innerHTML = "Push-Up";
      document.getElementById("chestType").textContent="The push up exercise is one of the best exercises available for building muscle and strength in the chest, shoulders, and triceps";
    },
    chestType2: function() {
      this.dropdownID = "configChestButton";
      document.getElementById("configChestButton").innerHTML = "Machine Fly";
      document.getElementById("chestType").textContent="The machine fly exercise is an easy way to target your chest and inner chest muscles";
    },
    chestType3: function() {
      this.dropdownID = "configChestButton";
      document.getElementById("configChestButton").innerHTML = "Dumbbell Fly";
      document.getElementById("chestType").textContent="The dumbbell fly exercise targets your pecs with more focus than the standard dumbbell bench press aswell as focusing upon the inner part for your pecs";
    },
    chestType4: function() {
      this.dropdownID = "configChestButton";
      document.getElementById("configChestButton").innerHTML = "Bench Press";
      document.getElementById("chestType").textContent="The bench press exercise targets the chest muscles and offers more stability for people new to the exercise";
    },
    chestType5: function() {
      this.dropdownID = "configChestButton";
      document.getElementById("configChestButton").innerHTML = "Cable Cross-Over";
      document.getElementById("chestType").textContent="If you want to target your inner chest, the cable cross-over exercise is the best way to do so";
    },
    triceps1: function() {
      this.dropdownID = "configTriButton";
      document.getElementById("configTriButton").innerHTML = "Bench Dip";
      document.getElementById("triType").textContent="The bench dip exercise is one of the most basic and best workouts for building the triceps and bigger arms";
    },
    triceps2: function() {
      this.dropdownID = "configTriButton";
      document.getElementById("configTriButton").innerHTML = "Machine Dip";
      document.getElementById("triType").textContent="To begin this exercise; start off sitting upon a dip machine and grabbing the handles. the most important aspect of this exercise is to keep your elbows close to the inside as you focus more upon the triceps";
    },
    triceps3: function() {
      this.dropdownID = "configTriButton";
      document.getElementById("configTriButton").innerHTML = "Weighted Tricep Dip";
      document.getElementById("triType").textContent="Start by setting up a weighted belt around your waist with the amount of weight that you can perform this exercise. Elevate yourself off the platform so that you are being held up by your upper body and the weight is dangling towards the floor.";
    },
    triceps4: function() {
      this.dropdownID = "configTriButton";
      document.getElementById("configTriButton").innerHTML = "Dumbbell Tricep";
      document.getElementById("triType").textContent="The dumbbell standing triceps extension works the tricep muscles by extending your arms behind your neck.";
    },
    triceps5: function() {
      this.dropdownID = "configTriButton";
      document.getElementById("configTriButton").innerHTML = "Cable Rope Triceps";
      document.getElementById("triType").textContent="The rope triceps pushdown exercise uses a rope to target the triceps muscle for better definition and bigger arms."
    },
    shld1: function() {
      this.dropdownID = "configshldButton";
      document.getElementById("configshldButton").innerHTML = "Dumbbell Lateral Raise";
      document.getElementById("shoulderType").textContent="The dumbbell lateral raise exercise helps build strong shoulders and muscle mass";
    },
    shld2: function() {
      this.dropdownID = "configshldButton";
      document.getElementById("configshldButton").innerHTML = "Dumbbell Shoulder Press";
      document.getElementById("shoulderType").textContent="The dumbbell shoulder press is a variation of the standard military press that puts more focus on the trap muscles and deltoid muscles";
    },
    shld3: function() {
      this.dropdownID = "configshldButton";
      document.getElementById("configshldButton").innerHTML = "Barbell Military Press";
      document.getElementById("shoulderType").textContent="To begin this exercise; start off by taking a weighted barbell and have it pressed up against your chest with your palms facing outward.";
    },
    shld4: function() {
      this.dropdownID = "configshldButton";
      document.getElementById("configshldButton").innerHTML = "Cable Rope Face Pull";
      document.getElementById("shoulderType").textContent="Start by facing a high pulley cable machine with either a rope or dual handles connect to the machine. Grab onto the handles and pill the weight directly towards your face, using your shoulders, and seperating your shoulders as you pull the weights back.";
    },
    shld5: function() {
      this.dropdownID = "configshldButton";
      document.getElementById("configshldButton").innerHTML = "Machine Shoulder Press";
      document.getElementById("shoulderType").textContent="The shoulder press machine exercise works the shoulder muscles, mainly the deltoid muscles.";
    },
    biType1: function() {
      this.dropdownID = "configBiButton";
      document.getElementById("configBiButton").innerHTML = "Barbell Curl";
      document.getElementById("biType").textContent="The barbell curl is a basic bicep exercise that helps increase the size of the muscles.";
    },
    biType2: function() {
      this.dropdownID = "configBiButton";
      document.getElementById("configBiButton").innerHTML = "Hammer Curl";
      document.getElementById("biType").textContent="The alternate hammer curl exercise uses a hammering(up and down) motion to isolate the biceps and to build bigger arms.";
    },
    biType3: function() {
      this.dropdownID = "configBiButton";
      document.getElementById("configBiButton").innerHTML = "Bicep Curl";
      document.getElementById("biType").textContent="The dumbbell bicep curl uses both arms at the same time to build stronger arms.";
    },
    biType4: function() {
      this.dropdownID = "configBiButton";
      document.getElementById("configBiButton").innerHTML = "Bar Curl";
      document.getElementById("biType").textContent="The bar curl exercise uses either an bar or curl bar to help isolate the biceps and build bigger arms.";
    },
    biType5: function() {
      this.dropdownID = "configBiButton";
      document.getElementById("configBiButton").innerHTML = "Concentration Curl";
      document.getElementById("biType").textContent="The dumbbell concentration curl exercise limits your range of motion and concentrates on isolating the bicep muscle.";
    },
    backType1: function() {
      this.dropdownID = "configBackButton";
      document.getElementById("configBackButton").innerHTML = "Wide Grip Lat Pulldown";
      document.getElementById("backType").textContent="The wide-grip lat pulldown is one of the classic bodybuilding exercises used to help build a stronger back";
    },
    backType2: function() {
      this.dropdownID = "configBackButton";
      document.getElementById("configBackButton").innerHTML = "Cable Seated Row";
      document.getElementById("backType").textContent="Targeting the large muscles in your back, the cable seated rows are an effective and beneficial back exercise.";
    },
    backType3: function() {
      this.dropdownID = "configBackButton";
      document.getElementById("configBackButton").innerHTML = "Barbell Deadlift";
      document.getElementById("backType").textContent="The barbell deadlift is a classic bodybuilding exercise meant for putting on mass and building overall strength throughout the entire body.";
    },
    backType4: function() {
      this.dropdownID = "configBackButton";
      document.getElementById("configBackButton").innerHTML = "Barbell Bent-Over Row";
      document.getElementById("backType").textContent="To begin this exercise, stand in front of a barbell with your feet shoulder-width apart and bend over untill your back is parallel with the floor.";
    },
    backType5: function() {
      this.dropdownID = "configBackButton";
      document.getElementById("configBackButton").innerHTML = "Dumbbell One-Arm Row";
      document.getElementById("backType").textContent="Start by kneeling over the side of a bench and then position the opposite foot on the floor. Grab a dumbbell from the floor opposite of the side supporting the body and pull the dumbbbell up slowly to your chest.";
    },
    coreTrigger1: function() {
      this.dropdownID = "configcoreButton";
      document.getElementById("configcoreButton").innerHTML = "Plank";
      document.getElementById("coreType").textContent="Start  by kneeling on all fours and aligning both hands right below your shoulders keep your knees beneath your hips. Extend both of your feet out behind you and squeeze on your core muscles, making sure that your body is aligned straight";
    },
    coreTrigger2: function() {
      this.dropdownID = "configcoreButton";
      document.getElementById("configcoreButton").innerHTML = "Machine Ab Crunch";
      document.getElementById("coreType").textContent="Sit down in the machine and select a resistance that is with in safe limits of your ability. Do a crunch in a slow controlled manner while slowly breathing out.";
    },
    coreTrigger3: function() {
      this.dropdownID = "configcoreButton";
      document.getElementById("configcoreButton").innerHTML = "Crunches";
      document.getElementById("coreType").textContent="The crunch exercise is the most common abdominal exercise and often is improperlu performed. Start by placing your hand across your chest or on either side of your head as this will be your starting position. Then slowly raise your upper body up off the floor and towards your knees,squeezing and crunching your abs.";
    },
    coreTrigger4: function() {
      this.dropdownID = "configcoreButton";
      document.getElementById("configcoreButton").innerHTML = "Leg Raise";
      document.getElementById("coreType").textContent="The leg raise exercise helps work and strengthen your lower abs which are typically a hard muscle to target.";
    },
    coreTrigger5: function() {
      this.dropdownID = "configcoreButton";
      document.getElementById("configcoreButton").innerHTML = "Cable Crunch";
      document.getElementById("coreType").textContent="Kneel below a high pulley that contains a rope attachment. Grasp cable rope attachment and lower the rope untill your hands are near your cheeks. Crunch down with your abs so that your face gets very close to the ground.";
    },
    forearmTrigger1: function() {
      this.dropdownID = "configforearmButton";
      document.getElementById("configforearmButton").innerHTML = "Barbell reverse curl";
      document.getElementById("forearmType").textContent="Stand with your feet shoulder-width apart. Bending at the keens pick up barbell. With your palms facing downward lift the barbell to your chest while remaining up straight.";
    },
    forearmTrigger2: function() {
      this.dropdownID = "configforearmButton";
      document.getElementById("configforearmButton").innerHTML = "Wrist Curl";
      document.getElementById("forearmType").textContent="Start by picking up a dumbbell in each hand while kneeling over a bench. With your palms facing upwards flex each forearm by lifting and lowering the dumbbell with your wrist.";
    },
    forearmTrigger3: function() {
      this.dropdownID = "configforearmButton";
      document.getElementById("configforearmButton").innerHTML = "Grip Curl";
      document.getElementById("forearmType").textContent="Start by standing up straight with your feet shoulder width apart and holding a weighted ez bar in your hands at waist level in a reverse grip. Slowly elevate the bar up towards your shoulders, squeezing your forearms and hold for a count.";
    },
    forearmTrigger4: function() {
      this.dropdownID = "configforearmButton";
      document.getElementById("configforearmButton").innerHTML = "Wrist Roller";
      document.getElementById("forearmType").textContent="To begin, stand straight with feet shoulder length apart and grab a wrist roller using a palms down grip. Slowly lift both arms untill they are fully extended and parallel to the floor in front of you.";
    },
    forearmTrigger5: function() {
      this.dropdownID = "configforearmButton";
      document.getElementById("configforearmButton").innerHTML = "Reverse Curl";
      document.getElementById("forearmType").textContent="Start by setting up either a straight or ez bar on a low pulley cable machine and grab the bar using a reverse shoulder width grip. Keep your elbows tucked in at your sides and slowly raise the bar up towards your shoulder, squeezing your foreaems and hold for a count.";
    },
    calType1: function() {
      this.dropdownID = "configcalButton";
      document.getElementById("configcalButton").innerHTML = "Seated Calf Raise";
      document.getElementById("calType").textContent="The seated calf raise exercise is one of the simplest and most effective calf workouts.";
    },
    calType2: function() {
      this.dropdownID = "configcalButton";
      document.getElementById("configcalButton").innerHTML = "Standing Calf Raise";
      document.getElementById("calType").textContent="The standing calf raise exercise uses a machine to target the lower calf muscles and is used by beginner for proper form.";
    },
    calType3: function() {
      this.dropdownID = "configcalButton";
      document.getElementById("configcalButton").innerHTML = "Leg Press";
      document.getElementById("calType").textContent="The calf press on a leg press exercise uses a leg press machine to help work and strengthen your calf muscles.";
    },
    calType4: function() {
      this.dropdownID = "configcalButton";
      document.getElementById("configcalButton").innerHTML = "Barbell Standing Calf Raise";
      document.getElementById("calType").textContent="The standing barbell calf raise helps target the lower leg muscles and build stronger legs.";
    },
    calType5: function() {
      this.dropdownID = "configcalButton";
      document.getElementById("configcalButton").innerHTML = "Dumbbell Standing Calf Raise";
      document.getElementById("calType").textContent="Grasp dumbbell in one hand to side and position toes and balls of feet on calf block with arches and heels extending off.";
    },










    addFitnessActivity: function () {
      const userId = this.$javalin.pathParams["user-id"];
      const text = document.getElementById(this.dropdownID).innerHTML;
      const url = `/api/fitness`;

      axios.get(`/api/users/${userId}/fitness`)
          .then(res => {
            this.currCount = res.data.length + 1;
            this.postFitness(url,text,userId)
          })
          .catch(error => {
            this.postFitness(url,text,userId)
          })
    },
    postFitness : function (url, text, userId) {
      axios.post(url,
          {
            id: this.currCount,
            dayType: text,
            started: new Date().toISOString(),
            userId: userId
          })
          .then(resp => {
            swal("Success", "Added!", "success");
          })
          .catch(_ => {
            swal("Opsss!", "Try Again", "error");
          })
    }


  }

});
</script>


<style>
#configTitleColor {
  background-color: #E1D7C6;
}
#configBackColor {
  background-color: #F8F4EA;
}
#configButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configChestButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configTriButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configshldButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configBiButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configBackButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configcoreButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}

#configforearmButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configCalButton {
  background-color: #E1D7C6;
  color: black;
  border-color: white;
}
#configAlign {
  position: absolute;
  right: 20px;
}
#configGap {
  padding-top: 50px;
}
</style>

