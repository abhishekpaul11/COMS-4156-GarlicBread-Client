# COMS-4156-GarlicBread-Client
Client App for COMS 4156 Advanced Software Engineering Project (Includify) offered in Columbia University during Fall 2024.

## Service Codebase

This is an Android application which consumes the Includify service residing [here](https://github.com/Aditi-Chowdhuri/COMSW4156-GarlicBread).

## App Overview (Includify)

This Android application focuses on the target user group of disabled and the elderly. It has the following features:
1. The users can add **emergency contacts** for quick look-up in case of an emergency. They can called be dialled by just a click from this application.
2. They can add details of the type of **medication** they're on and when they're scheduled to consume them. This app aims to deliver them **daily reminders**
at the specified times so that they don't miss out on their essential medications.

Apart from the above additional features, Includify also uses the [service](https://garlicbread-includify.ue.r.appspot.com/) built by us to implement the following features.
3. **Explore** - The users can browse nearby places offering them accessibility services. They can use it to get an in-depth idea of the nature of resources
they offer and their availability.
4. **Appointments** - The users can use our service to book appointments at a specific date and time at a give organisation which they plan to visit. They can
also add resources to the appointment which they plan to use. Additionally, volunteers can choose to add themselves to such appointments via our service as per their convenience
whose details will also be visible to the users.

Thus, our service coupled with the Includify app allows our target users to manage their day to day activities in a far better and more efficient way, overcoming their difficulties.
It aims to empower them in more ways than one.

## Installation

**Application**: [Android](https://www.android.com/)
**Language**: [Kotlin](https://kotlinlang.org/)
**Build Tool**: [Gradle](https://gradle.org/)

Use the following command to clone the repo.
```agsl
git clone https://github.com/abhishekpaul11/COMS-4156-GarlicBread-Client
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

## Coverage

We have added the [Jacoco Gradle Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html) to get test coverage. Run the following command to generate the coverage report.
```agsl
./gradlew jacocoTestReport
```

Currently, we are at **56%** branch coverage.

## Deployment

Ideally, an Android app should be deployed on the [Play Store](https://play.google.com/store/games?hl=en_US). However, given the scope of the project that is not feasible as we would need an app signing key and so on.
So we have currently generated an unsigned [apk file](https://en.wikipedia.org/wiki/Apk_(file_format)) for testing purposes.

It is available [here](https://drive.google.com/file/d/1iSplaEY6LZONAWO0Qn1yebUhkBcR-fTr/view?usp=sharing). It can be installed directly on any Android device. Please note that you need to be signed in to your
Columbia LionMail account to access the apk.

Additionally, you can also build a new apk from Android Studio by following the steps mentioned [here](https://developer.android.com/studio/run).

## End-to-End Test Cases

| Screen                | Description                                                                                                                                                                                     | Screenshot |
|-----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------|
| Login                 | Enter your credentials to sign-in.                                                                                                                                                              |            |
| Sign-up               | Enter the user details, choose the suitable user category and click Sign-Up to create a new account.                                                                                            |            |
| Dashboard             | Click on a button to proceed.                                                                                                                                                                   |            |
| Contacts              | It enlists the emergency contacts added. Click on the call icon to directly place a call.                                                                                                       |            |
| Add Contacts          | Enter details and click add to create a new Contact.                                                                                                                                            |            |
| Medicines             | It enlists the medicines added along with the details. The app also sends you a notification at the given time to remind you to take the medicine.                                              |            |
| Add Medicines         | Enter details and click add to set the reminder.                                                                                                                                                |            |
| Organisation List     | Displays the list of ADA compliant organisations you can visit. Click on Visit to schedule an appointment in that organisation. Click on a card to view more.                                   |            |
| Organisation Details  | It shows the details of the organisation. Click on the view on map link to check the location of the organisation. It also enlists the resources it has to offer. Click on a card to view more. |            |
| Resource Details      | Shows all details of the individual resource.                                                                                                                                                   |            |
| Schedule Appointments | Enter details and click schedule.                                                                                                                                                               |            |
| Appointment List      | Shows a list of all the scheduled appointments.                                                                                                                                                 |            |
| Appointment Details   | Shows details of the individual appointment along with volunteer details (if assigned)                                                                                                          |            |





