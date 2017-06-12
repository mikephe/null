<?php
include "class_players.php";

function login()
{
	try{
		$connection = new PDO("mysql:host=localhost;dbname=bruteforce", "bruteforce", "password");
	    $connection->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

		$gamertag = filter_input(INPUT_POST, "gamertag");
		$password = filter_input(INPUT_POST, "password");

		$query = "SELECT Players.password 
				  FROM Players 
				  WHERE Players.gamertag = :gamertag";

	    $ps = $connection->prepare($query);
	    $ps->bindParam(':gamertag', $gamertag);
		$ps->execute();
   		$ps->setFetchMode(PDO::FETCH_CLASS, "Players");
   		$players = $ps -> fetch();

   		if ($password === ($players-> getPassword()))
   		{
   			if (!isset($_COOKIE['gamertag']))
   			{
   				setCookie('gamertag', $gamertag);
   			}
   			echo 'true';
   		}

	} catch (Exception $e) {
      echo 'ERROR: '.$e->getMessage();
	}
}

login();
?>