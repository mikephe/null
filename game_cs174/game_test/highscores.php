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
		<title id="title" pageName="High Scores">Game | High Score</title>
		<link rel="stylesheet" type="text/css" href="css/index.css">
		<link rel="stylesheet" type="text/css" href="css/nav-bar.css">
		<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" href="css/table.css">
		<script type="text/javascript" charset="utf-8" src="js/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script> 
				$(init);
		 		function init()
		    	{
		    	  $("#includedContent").load("navBar.html"); 
		    	  $("#highscore_table").load("db_highscores.php");
		    	  $("#footer").load("footer.html");
		    	  $("#search").submit(s);
		    	}
		    	
		    	 function s(event, ui)
		    	 {
		    	 	event.preventDefault();
		    	 	gamertag = $("#gamertag").val();
		    	 	//window.alert(gamertag);
		    	 	$("#highscore_table").load("db_highscores.php", {"gamertag": gamertag});
		    	 }
	    </script>
	</head>
	<body>
		<div id="includedContent"></div>
		<form method="POST" id="search">
		<fieldset>
			<legend>Search by Gamer Tag</legend>
				<input type="text" name="gamertag" id="gamertag" placeholder="Gamer Tag"/>
			<input type="Submit" value="Submit">
		</fieldset>
		</form>
		<div class="bottom" id="highscore_table"></div>
		<div id="footer" class="container-fluid"></div>
	</body>
</html>