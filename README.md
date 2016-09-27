GreenAndroids
=============

A WikiNote Android Application Demo.

# Senior Android Developer Note

1. In comparison between iOS apps and android apps due to more Android 
   OS versions android apps tend to not be as polished. The fix is 
   back-porting certain UI features so that we have a consistent 
   UI look and UI feel across all Android OS versions.
   
2. Material Design is meant to be extended in coming up with your own colors 
   as that is one of the major tools we have in mobile to brand applications.
   Hence the maaterialcolors library.
   
3. Loading custom Typefaces from the assets folder is still leaking 
   memory in Lollipop, ie Android 5.x. Hence the typeface library.
   
4. RecyclerView by itself only has one multiviewstate implemented the emptyView.
   Hence the multistateview library.
   
5. Simple onboarding, ie color splash at app initialization was chosen 
   as we have both local and remote loading here in the app thus doing 
   toolbar movement as things are loading would be overkill and not have 
   much chance of being viewed. Rest of on-boarding occurs in the tour-guide-introduction 
   of the app to the user.
   
6. Android 4.x UI support is via the rippleeffect and zdepth libraries.
   The only thing lacking is support for SystemUi which of course is just 
   not possible on Android 4.x devices, except for Android 4.4.
   
7. To make it easier to see the business logic of how the progresses from 
   activity to activity and fragment we use a router library. Its a simpler
   implement of a command layer. Hence the router library.
   
8. The other part of on-boarding is the tour guide that introduces the 
   user to the app. Hence the materialintroscreen  library. Switched it 
   using the native fragments.
   
9. Guava is useful but due to the 65K method limits in a single apk, 
   one has to slim that down. Hence the mini-guava library.
   
10. We use a separate module to run UIAutomator as we will be
    running at minSDK 18 but the target minSDK for the app is 
    16 as well. It avoids having to use cucumber for UI behavior testing.
    
11. The difference between using Dagger or not is in the not case we 
    SetUp the Injector class in a product flavor  to initialize a build 
    product flavor specific class and call the Injector class from main etc.
    It has some limitations but we will not face those in this app.
    
12. Yeah, Google left out using a cursor loader for SQLite operations. It 
    gets added to this app as the complexity of the SQLite queries 
    increases the query results get back time tends to increase as well.
    
13. In Instrumented testing Aserrtj for Android is used to make writing 
    asserts in the android environment easier.

# Developed By

![gws logo](art/gws_github_header.png)

<a href="http://stackoverflow.com/users/237740/fred-grott">
<img src="http://stackoverflow.com/users/flair/237740.png" width="208" height="58" alt="profile for Fred Grott at Stack Overflow, Q&amp;A for professional and enthusiast programmers" title="profile for Fred Grott at Stack Overflow, Q&amp;A for professional and enthusiast programmers">
</a>


Created by [Fred Grott](http://shareme.github.com).

# Credits


The original concept and code for the app is from the former 
GogoleCode apps-for-android project that was authored by Google employees.

[

# License

Apache 2.0 License
