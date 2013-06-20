<?php
/*
 * This file is used connect or disconnect to the database.
 */
class DB_Connect{

	// constructor
	function __construct(){

	}

	// destructor
	function __destruct(){
		// $this->close();
	}

	// Connecting to database
	public function connect(){
		// require_once, includes and evaluates the file, if failed, produce error; 
		// if the file has been included, not include again.
		require_once 'config.php';
		// Connecting to mysql
		$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD);
		// selecting database
		mysqli_select_db(DB_DATABASE);

		// return database handler
		return $con; 
	}

	// Closing database connection
	public function close(){
		mysqli_close();
	}
}


?>