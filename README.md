# COMS-4156-GarlicBread-Client
Client App for COMS 4156 Advanced Software Engineering Project (Includify) offered in Columbia University during Fall 2024.

## Service Codebase

This is an Android application which consumes the Includify service residing [here](https://github.com/Aditi-Chowdhuri/COMSW4156-GarlicBread).

## App Overview (Includify)

This Android application focuses on the target user group of disabled and the elderly. It has the following features:
1. The users can add **emergency contacts** for quick look-up in case of an emergency. They can called be dialled by just a click from this application.
2. They can add details of the type of **medication** they're on and when they're scheduled to consume them. This app aims to deliver them **daily reminders**
at the specified times so that they don't miss out on their essential medications.

Apart from the above additional features, Includify also uses the [service](https://garlicbread-includify.ue.r.appspot.com/) built by us to implement the following features. </br>
1. **Explore** - The users can browse nearby places offering them accessibility services. They can use it to get an in-depth idea of the nature of resources
they offer and their availability.
2. **Appointments** - The users can use our service to book appointments at a specific date and time at a give organisation which they plan to visit. They can
also add resources to the appointment which they plan to use. Additionally, volunteers can choose to add themselves to such appointments via our service as per their convenience
whose details will also be visible to the users.

Thus, our service coupled with the Includify app allows our target users to manage their day to day activities in a far better and more efficient way, overcoming their difficulties.
It aims to empower them in more ways than one.

## Installation

**Application**: [Android](https://www.android.com/)<br>
**Language**: [Kotlin](https://kotlinlang.org/)<br>
**Build Tool**: [Gradle](https://gradle.org/)

Use the following command to clone the repo.
```agsl
git clone https://github.com/abhishekpaul11/COMS-4156-GarlicBread-Client.git
```

Open the Project only in [Android Studio IDE](https://developer.android.com/studio) which comes with all other necessary tools for building Android apps.

## Generating Builds

In the [build.gradle](https://github.com/abhishekpaul11/COMS-4156-GarlicBread-Client/blob/main/app/build.gradle) file, there are 2 types of builds mentioned. You can use the commands below for either of them.

```agsl
./gradlew app:assembleRelease
```
or 

```agsl
./gradlew app:assembleDebug
```
## Testing

The codebase contains [Unit Tests](https://github.com/abhishekpaul11/COMS-4156-GarlicBread-Client/tree/main/app/src/test/java/com/garlicbread/includify) and automated [End-to-End Tests](https://github.com/abhishekpaul11/COMS-4156-GarlicBread-Client/tree/main/app/src/androidTest/java/com/garlicbread/includify).

Use this for running the unit tests.

```agsl
./gradlew testDebugUnitTest
```

Use this for running the end-to-end tests.

```agsl
./gradlew connectedDebugAndroidTest
```

## Coverage (56%)

We have added the [Jacoco Gradle Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html) to get test coverage. Run the following command to generate the coverage report.
```agsl
./gradlew jacocoTestReport
```

Currently, we are at **56%** branch coverage.

<img width="1145" alt="Screenshot 2024-11-27 at 6 44 57 PM" src="https://github.com/user-attachments/assets/3c8f7c4e-10fd-4a36-b7c8-bff50f5163d2">

## Deployment

Ideally, an Android app should be deployed on the [Play Store](https://play.google.com/store/games?hl=en_US). However, given the scope of the project that is not feasible as we would need an app signing key and so on.
So we have currently generated an unsigned [apk file](https://en.wikipedia.org/wiki/Apk_(file_format)) for testing purposes.

It is available [here](/includify.apk). It can be installed directly on any Android device.

Additionally, you can also build a new apk from Android Studio by following the steps mentioned [here](https://developer.android.com/studio/run).

## End-to-End Test Cases

| Screen                | Description                                                                                                                                                                                     | Screenshot                                                                                |
|-----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| Login                 | Enter your credentials to sign-in.                                                                                                                                                              | <img src="/screenshots/Sign%20In.png" width="240">                                                 |
| Sign-up               | Enter the user details, choose the suitable user category and click Sign-Up to create a new account.                                                                                            | <img src="/screenshots/Sign%20Up.png" width="240">                                          |
| Dashboard             | Click on a button to proceed.                                                                                                                                                                   | <img src="/screenshots/Dashboard.png" width="240">                                                |
| Contacts              | It enlists the emergency contacts added. Click on the call icon to directly place a call.                                                                                                       | <img src="/screenshots/EmergencyContacts.png" width="240"> <img src="/screenshots/Call.png" width="240">  |
| Add Contacts          | Enter details and click add to create a new Contact.                                                                                                                                            | <img src="/screenshots/AddContact.png" width="240">                                                |
| Medicines             | It enlists the medicines added along with the details. The app also sends you a notification at the given time to remind you to take the medicine.                                              | <img src="/screenshots/Medicines.png" width="240">  <img src="/screenshots/Notification.png" width="240">   |
| Add Medicines         | Enter details and click add to set the reminder.                                                                                                                                                | <img src="/screenshots/Add%20Medicine.png" width="240">                                            |
| Organisation List     | Displays the list of ADA compliant organisations you can visit. Click on Visit to schedule an appointment in that organisation. Click on a card to view more.                                   | <img src="/screenshots/Orgs.png" width="240">                                                     |
| Organisation Details  | It shows the details of the organisation. Click on the view on map link to check the location of the organisation. It also enlists the resources it has to offer. Click on a card to view more. | <img src="/screenshots/Org%20Details.png" width="240"> <img src="/screenshots/Maps.png" width="240">        |
| Resource Details      | Shows all details of the individual resource.                                                                                                                                                   | <img src="/screenshots/ResourceDetails.png" width="240">                                           |
| Schedule Appointments | Enter details and click schedule.                                                                                                                                                               | <img src="/screenshots/Schedule%201.png" width="240"> <img src="/screenshots/Schedule%202.png" width="240">|
| Appointment List      | Shows a list of all the scheduled appointments.                                                                                                                                                 | <img src="/screenshots/Appointments.png" width="240">                                              |
| Appointment Details   | Shows details of the individual appointment along with volunteer details (if assigned)                                                                                                          | <img src="/screenshots/AppointmentDetails.png" width="240">                                        |





