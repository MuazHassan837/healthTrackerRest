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