<?php

include "class_players.php";

try{
			$connection = new PDO("mysql:host=localhost;dbname=bruteforce", "bruteforce", "password");
	    	$connection->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

			$gamertag = filter_input(INPUT_POST, "gamertag");
			$score = filter_input(INPUT_POST, "score");

			$insert_score_query = "INSERT INTO Scores(idPlayer, score) 
					  			   VALUES ((SELECT idPlayers FROM Players WHERE gamerTag = :Gamer_Tag) , :score)";
			$ps2 = $connection->prepare($insert_score_query);
			$ps2->bindParam(':Gamer_Tag', $gamertag);
			$ps2->bindParam(':score', $score);
		    $ps2->execute();
		    echo 'true';
		}
		catch(Exception $e)
		{
			echo 'ERROR****: '.$e->getMessage();
		}

?>

