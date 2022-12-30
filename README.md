# Fitness Pro : Health Edition.<br />
Application is primarily based on the user. <br /> **The user** has three properties, user id, name, and email. Where the User ID is unique. 
Other properties based on User are Activities, Fitness, Hydration, and Mood.
### Activities:
It consists of properties like its own id, description of the activity, the duration for which the activity was executed, number of calories that were burned during the activities, and user id. The user id is our non-null primary key. Activity inserted must be based on existing user id. We can perform methods like update, delete, add, and fetch to activities.
### Fitness:
It has properties like its own id, exercise type, starting time, and its relevant user. All methods like update, delete, add and fetch can be applied to fitness as well. There is a wide range of fitness workouts available. Workouts divided by categories and loaded with related information are provided to the user. Users can record/ save the activity to keep the track of their exercises.
### Hydration:
It has properties like id, amount and type of substance, and time taken for the relevant user. It supports all common API methods as well. Users can put the amount of the substance based in liter in the text field with the type of substance like water, milk.
### Mood:
It consists of mood id, mood, relevant user, and registered time supporting all API methods like patch, get, post, and delete. Moods like happy, sad, and uncertain are available via popup options.</br>
Tasks mentioned above have many benefits, including the ability to better monitor and improve one's overall fitness, pinpoint areas of improvement, and set and achieve new fitness goals. Additionally, activity tracking can help to motivate and encourage people to be more active </br>
Our first step will be adding a user as the application has no currently active users. This can be affirmed by the alert when the application is launched. </br>
<img src="https://user-images.githubusercontent.com/113602921/210081260-b8011e98-189d-47a1-8f9b-149aebda4996.png" width="40%"></br>
Initial application view: The current view provides overall entries made into the application to date. Ranging from activities to fitness goals added.</br>
<img src="https://user-images.githubusercontent.com/113602921/210071477-2af49a4c-658a-41db-95b8-eddfb888a506.png" width="50%"></br>
Exploring user section: It lists currently signed-up users as well option to signup users. Currently signed users' information can be edited via the leading navigation view and users can be deleted from this view.</br>
<img src="https://user-images.githubusercontent.com/113602921/210071672-1bda1c03-b049-4119-878a-306ab7ff9c27.png" width="60%"></br>
Upon trigging edit on the current user. A new page is loaded where users' information can be updated via text field changes, user can be deleted as well. Other features related to users like activity, mood, fitness, and hydration are listed as well. All features integrated have been given the ability to fetch, update, add and delete in this view.</br>
<img src="https://user-images.githubusercontent.com/113602921/210071799-57853f92-4bc3-47aa-af10-b430d0d0100e.png" width="70%"></br>
Exploring working of individual features: new activity is added and it is instantly viewable as well. The analysis section for each feature performs computation on specific parameters to provide intelligent output.</br>
<img src="https://user-images.githubusercontent.com/113602921/210071982-622e2b86-81d4-4639-859d-335466dba9ac.png" width="60%"></br>
Editing on the feature fetches its current information and performs an update operation on it. </br>
<img src="https://user-images.githubusercontent.com/113602921/210080053-a096d6ab-e958-4ce0-8985-a2787e71c458.png" width="60%"></br>
Analysis corrects itself with current feature data.</br>
<img src="https://user-images.githubusercontent.com/113602921/210080273-d2e7d18b-49ae-4f8e-a5a1-d4fa398fcf78.png" width="60%"></br>
Intake tracking feature and its analysis according to the provided data.</br>
<img src="https://user-images.githubusercontent.com/113602921/210080536-be04ba3b-2765-4a4b-ad3b-bd3ea10a2f31.png" width="60%"></br>
Mood tracking feature and its analysis according to the provided data.</br>
<img src="https://user-images.githubusercontent.com/113602921/210080726-27ae0b9b-1363-4ba5-a960-aa4d5325e79c.png" width="60%"></br>
Updating mood data via different provided options.</br>
<img src="https://user-images.githubusercontent.com/113602921/210080849-44a869dd-ed0e-48b9-aa35-0b4f93054c90.png" width="60%"></br>
Main fitness tracking feature and its analysis according to the provided data.
<img src="https://user-images.githubusercontent.com/113602921/210080914-890f8ff5-6776-44c9-92d5-8d8b1af9c58d.png" width="60%"></br>
JetFit fitness section where users can log in and set new goals. User name and ID much match to log in successfully.</br>
<img src="https://user-images.githubusercontent.com/113602921/210081012-63ea3124-99b8-4ef5-8345-4abe6c56b85c.png" width="70%"></br>
A prompt appears user if the login was successful or if user should try again. If it's successful, the user is navigated to the main page.</br>
<img src="https://user-images.githubusercontent.com/113602921/210081057-fa56c573-3e26-47bf-9d17-f692bdf58e88.png" width="40%"></br>
On this page, fitness exercises are divided into different categories, exercise can be accessed via a dropdown menu. Upon selection of an exercise, the user can see the information related to the picked exercise. Users can also set a goal or record an exercise by tapping on the save button.</br>
![image](https://user-images.githubusercontent.com/113602921/210083999-80fcb39d-c8a7-4371-9987-8d53f2a69edf.png)
![image](https://user-images.githubusercontent.com/113602921/210084049-9be636ce-41ac-4ed8-ae43-fb5065ff2429.png)
![image](https://user-images.githubusercontent.com/113602921/210084078-a621f419-fca6-4b6c-9a07-7620ee226366.png)
Overall application top view after features configuration.</br>
<img src="https://user-images.githubusercontent.com/113602921/210081237-28928538-0faf-4ba8-b546-44fe7879319d.png" width="60%"></br>
