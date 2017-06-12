SELECT p1.idPlayers as Player, p2.idPlayers as Friend
FROM Players as p1, Players as p2, Friends
WHERE p1.idPlayers = Friends.idFriend
AND	  p2.idPlayers = Friends.idPlayer