<?php
	if (!isset($_COOKIE['gamertag']))
	{
		header('Location: index.html');
		die();
	}
?>

<?php
	include "class_players.php";
	$connection = new PDO("mysql:host=localhost;dbname=bruteforce", "bruteforce", "password");
	$connection->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
	$tag = $_COOKIE['gamertag'];
  	$query = "SELECT firstName AS first, lastName AS last, gamerTag AS 'Gamer_Tag', Scores.Score, email
              FROM Players join Scores
              WHERE Players.idPlayers = Scores.idPlayer
              AND Players.gamerTag = \"$tag\"
              ORDER BY Score DESC
              LIMIT 1;";

    $ps = $connection->prepare($query);
    $ps->execute();
    $ps->setFetchMode(PDO::FETCH_CLASS, "Players");
    
    
    while ($players = $ps->fetch()) { 
		$first = $players->getFirst();
      	$last = $players->getLast();
      	$gameTag = $players->getGamerTag();
      	$score = $players->getScore();
      	$email = $players->getEmail();
    }

    if($first == "") {
    	$tag = $_COOKIE['gamertag'];
	  	$query = "SELECT firstName AS first, lastName AS last, gamerTag AS 'Gamer_Tag', email
	              FROM Players
	              WHERE Players.gamerTag = \"$tag\"
	              LIMIT 1;";

	    $ps = $connection->prepare($query);
	    $ps->execute();
	    $ps->setFetchMode(PDO::FETCH_CLASS, "Players");
	    
	    
	    while ($players = $ps->fetch()) { 
			$first = $players->getFirst();
	      	$last = $players->getLast();
	      	$gameTag = $players->getGamerTag();
	      	$score = 0;
	      	$email = $players->getEmail();
	    }
    }
	$path = "icons/face.png";
	$path2 = "tests.png";
?>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title id="title" pageName="Users">Game | Home</title>
		<link rel="stylesheet" type="text/css" href="css/index.css">
		<link rel="stylesheet" type="text/css" href="css/nav-bar.css">
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="slick/slick.css"/>
 		<link rel="stylesheet" type="text/css" href="slick/slick-theme.css"/>
		<script type="text/javascript" charset="utf-8" src="js/jquery.min.js"></script>
	    <script> 
				$(init);
		 		function init()
		    	{
		    	  $("#includedContent").load("navBar.html"); 
		    	  $("#footer").load("footer.html");
		    	  $("#image-upload").submit(s);
		    	}
		    	
		    	 function s(event, ui)
		    	 {
		    	 	event.preventDefault();
		    	 	//window.alert("Clicked!");
		    	 	document.getElementById("user-img").src = <?php print "\"$path2\"" ?>;
		    	 }
	    </script>
	    <style>
	    	.slick-arrow {
	    		background: black !important;
			   	border-radius: 50px;
	    	}
			.slider{
			width:600px;
			margin:20px auto;
			text-align:center;
			}
			h3{
			padding:40px 20px;
			background:white;
			border: 1px solid;
			}
			.slider div{
			margin-right:5px;
			}
			a {
				text-decoration: none;
				color: #0a3a51;
			}
			a:hover {
				text-decoration: none;
				color: #8a0707;
			}
		</style> 
	</head>

	<body>
		<div id="includedContent"></div>
		<div class="container" style="position: relative; margin-top: 180px;">
			<div class="row">
				<div class="col-md-3" style="text-align: center;">
					<a class="thumbnail" href=""><img id="user-img" src=<?php echo $path ?> alt=""></a>
					<span>
							<form action='upload.php' method='POST' id="image-upload" enctype='multipart/form-data'>
							<input type='file' name='userFile'>
							<input type='submit' name='upload_btn' value='Upload'>
							</form>
					</span>
				</div>
				<div class="col-md-3">
					<dl>
						<dt>Name</dt>
						<dd><?php echo $first . " " . $last?></dd>
					</dl>
					<dl>
					<dl>
						<dt>Gamer Tag</dt>
						<dd><?php echo $gameTag?></dd>
					</dl>
				</div>
				<div class="col-md-3">
					<dl>
						<dt>Highest Score</dt>
						<dd><?php echo $score ?></dd>
					</dl>
					<dl>
						<dt>Email</dt>
						<dd><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span><?php echo $email ?></dd>
					</dl>
				</div>

				<dl class="col-md-4">
					<dt><h4>About</h4></dt>
					<dl>
						"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do 
						eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim 
						ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut 
						aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit 
						in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
						Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia 
						eserunt mollit anim id est laborum."
					</dl>
				</dl>
				<dl class="col-md-4">
					<dt><h4>Hobbies</h4></dt>
					<dl>
						"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do 
						eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim 
						ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut 
						aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit 
						in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
						Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia 
						eserunt mollit anim id est laborum."
					</dl>
				</dl>
			</div>
		</div>

		<div class="row bottom">
			<dt style="text-align: center;"><h4>Top Friends</h4></dt>
			<div class="slider">
			    <div><a href="#"><h3>Bob Ross</h3></a></div>
			    <div><a href="#"><h3>Ron Mak</h3></a></div>
			    <div><a href="#"><h3>Bobby Flay</h3></a></div>
			    <div><a href="#"><h3>Brandon</h3></a></div>
			    <div><a href="#"><h3>Brian</h3></a></div>
			    <div><a href="#"><h3>Alex</h3></a></div>
			    <div><a href="#"><h3>Mike</h3></a></div>
			    <div><a href="#"><h3>Jessica</h3></a></div>
			    <div><a href="#"><h3>Sally</h3></a></div>
		  	</div>
	  	</div>

		<div id="footer"></div>

   		<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
  		<script type="text/javascript" src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
  		<script type="text/javascript" src="slick/slick.min.js"></script>
  		<script type="text/javascript">
		    $(document).ready(function(){
		        $('.slider').slick({
		          dots: true,
		        infinite: true,
		        slidesToShow: 3,
		        slidesToScroll: 3
		    });
		    });
		</script>
	</body>
</html>
