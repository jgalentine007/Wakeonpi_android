#wakeonpi_android

This (simple) Android application will allow you to control wakeonpi easily.  

## Installation

You can build the project yourself with Android Studio, or download an .APK from the GitHub releases page for this project.  To install an APK, you can simply browse to the release URL from your Android phone.

##  Build Requirements

The project is written in Android Studio 2.0 Preview 4.  It is only targeted at Android versions > 5.0.1.

The project includes all neccessary external libraries:
* gson-2.5.jar
* snakeyaml-1.16.jar
* twitter4j-core-4.0.4.jar

## Operation

It's easy!

### Settings

Copy your `wakeonpi.conf` configuration data to your phone (by e-mail or twitter direct message, for instance.)  Copy all of the configuration lines to your phone's clipboard.  The lines should include the 'twitter:' and 'computers:' sections.

* Run the application
* To input your *wakeonpi* settings, press action bar settings icon (vertical elipses)
* Press the Settings menu item
* Press the General menu item
* Press the Twitter Configuration menu item
* Paste in your wakeonpi.conf data from your phone's clipboard and press OK
* Press the Android back button until you return to the screen
* You should see your computers listed

### Wake a computer

To wake a computer, press and hold it's name for a few seconds (Android long press.)  A notification message will appear.

## To-do

Allow login with twitter username and password
Store twitter credentials using Android account manager
Pull computer names using `wol list` command and twitter4j stream listener
