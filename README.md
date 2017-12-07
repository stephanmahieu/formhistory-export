# Form History Export

Standalone Java application for exporting formhistory from Mozilla's (Firefox) SQLite database

## Requirements
This application requires a Java runtime of at least version 8.
You can check if you already have Java installed by executing the following command from the commandline:

     java -version

If this command is not recognized you most likely have no java runtime installed.
You can find instructions on how to download and install Java here: https://java.com/nl/download/
or use the package manager provided by your OS.

## Download the export application
Download the java application fhcExport-1.0.0.jar from here: [fhcExport-1.0.0.jar](https://github.com/stephanmahieu/fhc-home/raw/master/downloads/fhcExport-1.0.0.jar) .

Put this file in any directory you like, then open a dosbox and go to the directory where you downloaded the file.

## Usage

### Step 1: Export data from Firefox's internal formhistory store
From the command-line you can start this java application using the following command:

    java -jar fhcExport-1.0.0.jar

That should display a little help text because you have to add at least one parameter:

     -h,--help                show help.
     -o,--outputfile <arg>    optional, the path of the outputfile (default:formhistory.xml in current directory)
     -p,--profile-dir <arg>   required, the Firefox profile directory.


The application needs to know where to find the Firefox database so you have to provide the Firefox profile directory.

How to find the profile directory is described here: https://support.mozilla.org/en-US/kb/profiles-where-firefox-stores-user-data

For example the profile directory can be something like: C:\Users\John\AppData\Roaming\Mozilla\Firefox\Profiles\jguiwjin.default

If you know the profile directory you can start the java application and try to export the formhistory data,

execute the application as follows with your profile directory as parameter:

     java -jar fhcExport-1.0.0.jar -p C:\Users\John\AppData\Roaming\Mozilla\Firefox\Profiles\jguiwjin.default

If everything runs smoothly you should see something like:

    Start formhistory export
    - profile directory: C:\Users\John\AppData\Roaming\Mozilla\Firefox\Profiles\jguiwjin.default/
    - destination file : formhistory.xml
    checking outputfile:        OK
    checking profile directory: OK
    reading inputfields:        2230
    reading multilinefields:    441
    writing data:               OK

You should now have an exportfile named formhistory.xml that you can use to import into the new add-on.

_The step reading multilinefields might fails, this is completely normal if you never used this add-on before.
The export should still contain the formhistory for all standard text inputfields Firefox normally stores in its internal database._

### Step 2: Import the formhistory xml file:

* open the Form History Control add-on in Firefox (right-click on the Form History Control icon, choose first item)
* From this dialog, choose File from the Menu on top, then choose Import now an import dialog will open
* Locate and select the formhistory.xml file you created in the first step
* Selecting the xml file will enable the Import button, click this button to start the actual Import