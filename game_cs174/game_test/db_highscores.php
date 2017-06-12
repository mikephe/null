
<?php
include "class_players.php";

function run() {
  try {
    //TODO: Move database credentials to a seperate file and include that file here
    //include (".:db_credentials.php");

    //Connect to the database
    
    $connection = new PDO("mysql:host=localhost;dbname=bruteforce", "bruteforce", "password");
    $connection->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    
    $gamertag = filter_input(INPUT_POST, "gamertag");
    
    $query = "SELECT firstName AS 'First Name', lastName AS 'Last Name', gamerTag AS 'Gamer Tag', Score, Scores.timestamp as Date 
              FROM Players JOIN Scores";
    
    print "<table border='2'>\n";
    $result = $connection->query($query);
    $row = $result->fetch(PDO::FETCH_ASSOC);

    print "<tr>\n";
    foreach($row as $field => $value){
        print "<th>$field</th>\n";
    }
    print "</tr>\n";
    if($gamertag != "") {
      executeUserHighScoresQuery($gamertag, $connection);
    }
    else {
      executeHighScoresQuery($connection);
    }
    lastGameQuery($gamertag, $connection);
    
  } catch (Exception $e) {
      echo 'ERROR: '.$e->getMessage();
  }
}

function executeUserHighScoresQuery($gamertag, $connection) {
  $query = "SELECT firstName AS first, lastName AS last, gamerTag as Gamer_Tag, Score, Scores.timestamp
            FROM Players join Scores
            WHERE Players.idPlayers = Scores.idPlayer
            AND Players.gamerTag = :gamertag
            ORDER BY Scores.score DESC;";
    $ps = $connection->prepare($query);
    $ps->bindParam(':gamertag', $gamertag);
    $ps->execute();
    $ps->setFetchMode(PDO::FETCH_CLASS, "Players");
    

    
    while ($players = $ps->fetch()) { 
      print "        <tr>\n";
      print "            <td>" . $players->getFirst()     . "</td>\n";
      print "            <td>" . $players->getLast()     . "</td>\n";
      print "            <td>" . $players->getGamerTag()     . "</td>\n";
      print "            <td>" . $players->getScore()     . "</td>\n";
      print "            <td>" . $players->getTimeStamp()     . "</td>\n";
    }
    print "        </tr>\n";
    print "    </table>\n";   
}

function executeHighScoresQuery($connection) {
    $query = "SELECT firstName, lastName, gamerTag, Scores.Score, Scores.timestamp
              FROM Players join Scores
              WHERE Players.idPlayers = Scores.idPlayer
              ORDER BY Score DESC
              LIMIT 10;";
  
    

    $ps = $connection->prepare($query);
    $ps->execute();
    $data = $ps->fetchAll(PDO::FETCH_ASSOC);
    
    

    foreach ($data as $row) {
        print "<tr>\n";
        foreach ($row as $name => $value) {
            print "<td>$value</td>\n";
        }
        print "</tr>\n";
    }
    print "</table>\n";

    
}

function lastGameQuery($gamertag, $connection) {
    $lastGameScoreQuery = "SELECT Scores.score, date_format(`timestamp`, '%Y/%m/%d at %H:%i') as Date
                           FROM Players join Scores
                           WHERE Players.idPlayers = Scores.idPlayer
                           ORDER BY timestamp DESC
                           LIMIT 1;";
    $ps = $connection->prepare($lastGameScoreQuery);
    $ps->execute();
    $currentGameData = $ps->fetchAll(PDO::FETCH_ASSOC);

    print "<table>\n";
    print "<th>Last Played Game Score</th>\n";
    print "<th>Date</th>\n";
    foreach ($currentGameData as $scorerow) {
        print "<tr>\n";
        foreach ($scorerow as $name => $value) {
            print "<td>$value</td>\n";
        }
        print "</tr>\n";
    }
    print "</table>\n";
}

run();
?>
