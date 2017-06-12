<?php
	//if (isset($_COOKIE['gamertag']))
	unset($_COOKIE['gamertag']);
	setCookie('gamertag', '', time()-3600);
	header('Location: index.html');
?>