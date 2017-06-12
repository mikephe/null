<?php
	include "class_players.php";

	

	try{
			$connection = new PDO("mysql:host=localhost;dbname=bruteforce", "bruteforce", "password");
	    	$connection->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

			$firstName = filter_input(INPUT_POST, "firstname"); 
			$lastName = filter_input(INPUT_POST, "lastname"); 
			$gamertag = filter_input(INPUT_POST, "gamertag");
			$email = filter_input(INPUT_POST, "email");
			$newPassword = filter_input(INPUT_POST, "password");


			$query = "INSERT INTO Players(firstName, lastName, gamerTag, email, password) 
					  VALUES (:first, :last, :tag, :email, :password)";

			$ps = $connection->prepare($query);
			$ps->bindParam(':first', $firstName);
			$ps->bindParam(':last', $lastName);
			$ps->bindParam(':tag', $gamertag);
		    $ps->bindParam(':email', $email);
		    $ps->bindParam(':password', $newPassword);
		    $ps->execute();
		    $ps->setFetchMode(PDO::FETCH_CLASS, "Players");

		    if (!isset($_COOKIE['gamertag']))
			{
				setcookie('gamertag', $gamertag);
			}

		    echo 'true';
		}
		catch(Exception $e)
		{
			echo 'ERROR****: '.$e->getMessage();
		}
?>