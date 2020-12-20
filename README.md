# custom-launcher

Fetches all launcher apps list that are currently installed on Android device.
App list is sorted in ascending order based on app name.

- All launcher apps are getting displayed inside a recycler view.
- Application can be lauched on tap of the launcher app list item.
- Search option is available to find out apps based on app name.
- Library module retrieves launcher apps list and provides the data to app module.
- Library module also notifies about apps installation & un installtion.
- App module updates the launcher apps list whenever it gets notified by library module on apps installtion & un installation.

