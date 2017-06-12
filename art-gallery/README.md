# Art Gallery
This program will act as a visual aid tool for Geometric  purposes, which will be used to help solve a mathematical problem known as the “Art Gallery” problem. The name of this program will be known as Art Gallery: A Computational Geometry Tool. This program will essentially allow  a user to draw a polygon and place points (known as “guards”) within the polygon and each point will have a 360 view that will shade the polygon’s regions within its line of sight. Other features will also be added as well depending on the instructor’s request.

## Git-Hub Link
https://github.com/mikephe/art-gallery

## Contributors
* Mike Phe
* Richard M. Low

## Requirements
* Java 8 or higher

## How to compile
* Ensure manifest file is created
* All class files should be within the same directory
* Then run command:

```java
jar cfm ArtGallery.jar manifest.mf *.class
```

## Features
* Place points to draw a 2D Polygon
* Place guards on canvas
* Shade the inside region of the polygon
* Undo previous operation
* Save image as screenshot of application
* Remove guards
* Clear entire screen

## How to use
* Entire canvas can be used to plot points and each point plotted will connect a line to the previous point
* Buttons at the bottom represent options to be selected
* There is a counter at the top left to indicate how many guards are placed on screen
* The label on the top right shows which option is currently selected
* The save option will save only the frame of the application as a screenshot (PNG format)

## TO-DO
* Undo operation only applies when guard or point is selected, fix to undo operation
  regardless of selection
* Guards do not shade region correctly, entire polygon shades (use ray casting?)
* Add color coordination for guarded and unguarded regions (only guarded regions colored)