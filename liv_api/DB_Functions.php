<?php
/*
 * This file contains functions to store user in database, get user from database
 */
class DB_Functions{

	private $db;
	// constructor
	function __construct(){
		retuire_once 'DB_Connect.php';
		// Connecting to database
		$this->db = new DB_conect();
		$this->db->connect();
	}

	// destructor
	function __destruct(){

	}

	/*
	 * Store new user, returns user details
	 */
	public function storeUser($name, $email, $password){
		// uniqid, generates a unique ID based on the 
		// microtime (current time in microseconds)
		$uuid = uniqid('', true);
		$hash = $this->hashSSHA($password);
		$encrypted_password = $hash["encrypted"]; // encrypted password
		$salt = $hash["salt"];
		// mysql_query(), returns the query handler for SELECT queries, TRUE?FALSE for other queries
		$result = mysqli_query("INSERT INTO users(unique_id, name, email, encrypted_password, salt, created_at)
			VALUES('$uuid', '$name', '$email', '$encrypted_password', 'salt', NOW())");
		//check for successful store
		if ($result){
			// get user details
			// mysql_insert_id, Retrieves the ID generated for an AUTO_INCREMENT column
			// by the previous query (usually INSERT).
			$uid = mysqli_insert_id(); // last inserted id
			$result = mysqli_query("SELECT * FROM users WHERE uid = $uid ");
			// return the tuple just inseted
			return mysqli_fetch_array($result);
		}
		else{
			return false;
		}
	}

	/*
	 * Get user by email and password
	 */
	public function getUserByEmailAndPassword($email, $password){
		$result = mysqli_query("SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
		// check for result
		$no_of_rows = mysqli_num_rows($result);
		if ($no_of_rows > 0){
			$result = mysqli_fetch_array($result);
			$salt = $result['salt'];
			$encrypted_password = $result['encrypted_password'];
			$hash = $this->checkhashSSHA($salt,$password);
			// check for password equality
			if ($encrypted_password == $hash){
				// user authentication details are correct
				return $result;
			}
			else{
				//user not found
				return false;
			}
		}
	}

	/*
	 * Check user is existing or not
	 */
	pubic function isUserExist($email){
		$result = mysqli_query("SELECT email fro users WHERE email = '$email'");
		$no_of_rows = mysqli_num_rows($result);
		if($no_of_rows > 0){
			// user existing
			return true;
		}
		else{
			return false;
		}
	}

	/*
	 * Encrypting password
	 * @param password
	 * returns salt and encrypted password
	 */
	public function hashSSHA($password){
		$salt = sha1(rand());
		$salt = substr($salt, 0, 10);
		$encrypted = base64_encode(sha1($password . $salt, true) . $salt);
		$hash = array("salt" => $salt, "encrypted" => $encrypted);
		return $hash;
	}

	/*
     * Decrypting password
     * @param salt, password
     * returns hash string
	 */
	public function checkhashSSHA($salt, $password){
		$hash = base64_encode(sha1($password . $salt, true) . $salt);
		return $hash;
	}
}

?>