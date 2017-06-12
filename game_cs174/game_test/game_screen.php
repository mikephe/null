<?php
	if (!isset($_COOKIE['gamertag']))
	{
		header('Location: index.html');
	}
?>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title id="title" pageName="Play">Game | Play</title>
		<link rel="stylesheet" type="text/css" href="css/index.css">
		<link rel="stylesheet" type="text/css" href="css/nav-bar.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<script type="text/javascript" charset="utf-8" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="comm.js"></script>
		<script> 
		    $(function(){ 
		    	$("#includedContent").load("navBar.html"); 
		    	$("#footer").load("footer.html");
		    });
	    </script>
	</head>
	<body>
	<div id="includedContent"></div>

    <canvas id="canvas" width="900" height="300" style="border:1px solid #000000;">
    	Canvas unsupported on current browser.
    </canvas>

	<div id="footer" class="container-fluid" style="margin-top: 25%;"></div>
	
	<script type="text/javascript" src="js/Object.js"></script>
	<script type="text/javascript" src="js/DrawableObject.js"></script>
	<script type="text/javascript" src="js/ScrollingBackground.js"></script>
    <script type="text/javascript" src="js/AnimationManager.js"></script>
    <script type="text/javascript" src="js/Player.js"></script>
    <script type="text/javascript" src="js/Level.js"></script>
    <script type="text/javascript" src="js/Main.js"></script>
    <script type="text/javascript" src="js/ObjectManager.js"></script>
	<script type="text/javascript" src="js/Rectangle.js"></script>
	<script type="text/javascript" src="js/Pickup.js"></script>
	<script type="text/javascript" src="js/Enemy.js"></script>
	</body>
</html>