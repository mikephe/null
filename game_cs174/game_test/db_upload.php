<?php
		$info = pathinfo($_FILES['userFile']['name']);
		$fn = $info['filename'];
		$ext = $info['extension']; // get the extension of the file
		$newFile = "$fn.$ext";
		$pathToFolder = './images/username/';
		//echo "\nINFO: $newFile\n";

		$target = "$pathToFolder$newFile";

		if(file_exists($pathToFolder)) {
			//echo "\nFOLDER EXISTS!\n\n";
			// uncomment to save to file system
			move_uploaded_file( $_FILES['userFile']['tmp_name'], $target);
		} else {
			mkdir("$pathToFolder");
			//echo "\nFOLDER DOESN'T EXISTS!\n\n";
		}

		//var_dump($info);

?>