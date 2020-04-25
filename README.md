# Codingo
This project is a MVP of a gamified coding language learning application, completed for the INFS3634 course.
The purpose of the application is to teach coding knowledge to all students for free. The app has fuuctionalities such 
as reading content, video content, flashcards, quizzes, user profiles and leaderboards. The app also implements various
APIs (refer to Built With or Acknowledgements section) and other services such as Firebase.

## Read Before Starting
This app implements Google Firebase as part of the database. Although the developers can not see your personal information, we <b>STRONGLY ADVISE</b> that you <b>DO NOT INPUT PERSONAL INFORMATION</b>.

## Getting Started
These instructions will get you a copy of the project up and running for development and testing purposes.

### Pre-requisites
* Installed the latest version of Android Studio (as of 26/04/2020, 3.6 is recommended)
* Have an Android device or emulator that runs Android Nougat (Android 7.1.1 recommended)
* Recommended devices are the Google Pixel range (esp. for emulators as they are easy to install)
* Possess a stable internet connection to fully utilise the application

### Installation Instructions
#### Install via Android Studio Terminal
* Open up Android Studio and open the Terminal
* In the terminal, type:
```
git clone https://github.com/KhangBui999/Codingo.git
```
* Sync build.gradle configurations
* Run the app! :)

#### Install via GitHub Download
* Download ZIP from GitHub
* Unzip file and place unzipped contents into your Android Studio Projects directory
* Run Android Studio, select "Open an Existing Studio Project" and open the downloaded project
* Sync build.gradle configurations
* Run the app! :)

### Installation Resources
* [Android Studio Installation](https://developer.android.com/studio)
* [Guide on Installing Emulators](https://developer.android.com/studio/run/emulator#install)

## Features Catalog
* Login and registration feature
* BottomNavigationView for easy and accessible navigation
* Homepage/dashboard that showcases the use of FragmentTransactions & API calls
* Use of [Programming Quotes API](https://github.com/skolakoda/programming-quotes-api) to display inspirational quotes on the homepage
* Flashcard learning feature
* Videos and reading content (same screen) via [YouTubeAPI](https://developers.google.com/youtube/v3)
* Quiz feature that allows user to accumulates points
* Badge feature (collect them by getting 100% in a topic OR accumulating points)
* User profile feature that displays total points, badges and quiz statistics
* Use of [RobotHash Image API](https://robohash.org/) for randomised avatar images
* Leaderboard feature that showcases the Top 10 Players (by points)
* Firebase implementation such as Firebase Authentication & Firebase Firestore

## Built With
* [Programming Quotes API](https://github.com/skolakoda/programming-quotes-api)
* [RobotHash Image API](https://robohash.org/)
* [YouTubeAPI](https://developers.google.com/youtube/v3)
* [Glide](https://github.com/bumptech/glide)
* [Google Firebase](https://firebase.google.com/)

## Versioning
Our commit log can be seen by clicking [here](https://github.com/KhangBui999/Codingo/commits/master).

## Authors
* [Khang Bui](https://github.com/KhangBui999) (z5209606) - Lead Developer
* [Brian Vu](https://github.com/blabbit321) (z5209608) - Lead Product Manager
* [Shara Bakal](https://github.com/SgilG91) (z5115841) - Lead UI/UX Designer
* [Laurence Truong](https://github.com/laurence1111)(z5205455) - Lead Database Manager

## Copyright and License
This code was written by INFS3634 2020T1 Team 31 ([KhangBui999](https://github.com/KhangBui999), [blabbit321](https://github.com/blabbit321), [SgilG91](https://github.com/SgilG91) & [laurence1111](https://github.com/laurence1111)).
You may make copies, distribute and make derivative works of this work for any non-commercial purpose provided that you acknowledge this work.
The preferred way to do this is simply providing a link to this GitHub repository.

## Acknowledgements
* [Skolakoda](https://github.com/skolakoda) - [Programming Quotes API](https://github.com/skolakoda/programming-quotes-api)
* RobotHash - [RobotHash Image API](https://robohash.org/)
* [Bumptech](https://github.com/bumptech) - [Glide](https://github.com/bumptech/glide)
* [Medyo](https://gist.github.com/medyo) - [YouTubePlayerSupportFragmentX](https://gist.github.com/medyo/f226b967213c3b8ec6f6bebb5338a492)
* [FreePik](https://www.flaticon.com/authors/freepik) - for various icons used in our application
