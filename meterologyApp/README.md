# Meterology App
This is an app for the Meteorology department at SJSU. We are using web-based
framework known as PhoneGap for developement.


##Setting up
* Install [Node.js](http://nodejs.org/)

* PhoneGap installation:

```
sudo npm install -g phonegap
```

* Optional Cordova Command-Line tool

__MACOSX OR LINUX__
```
sudo npm install -g cordova
```

__WINDOWS__
```
C:\>npm install -g cordova
```

##FAQ's/Tips
* Tip: Don't forget to download the SDK's for the specified platform and update path if you have trouble building.

* How to build app
```
cordova build // this will build for all platforms
```

```
cordova build <platform> // this will build for only specific platform
```

cordova build is a shorthand command that does the following:
```
cordova prepare <platform>
cordova compile <platform>
```

* How to run emulator via cordova?
```
cordova emulate <platform>
```

* How to run on device

Plug in device:
```
cordova run <platform>
```


##Author's
* Mike Phe
* John Ludeman

##Credit
* Andrew Soto (Graphic Designer)
