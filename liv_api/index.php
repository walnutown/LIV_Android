<?php
/*
 * This file plays role of accepting requests and giving response.
 * On each request, it will talk to database and will give appropriate
 * response in JSON format.
 */

/*
 * Check for POST requet
 */
// isset, determine if a variable is set and is not NULL
if (isset($_POST['tag']) && $_POST['tag'] != ''){
	// get tag
	$tag = $_POST['tag'];
	//include db handler
	require_once 'include/DB_Functions.php';
	$db = new DB_Functions();
	// response array
	$response = array("tag" => $tag, "success" => 0, "error" => 0);
	
	//check for tag type
	if ($tag == 'login'){
		// Request type is login
		$email = $_POST['email'];
		$password = $_POST['password'];
		//check for user
		$user = $db->getUserByEmailAndPassword($email, $password);
		if ($user != false){
			// user found
			// echo json with success = 1
			$response['success'] = 1;
			$response['uid'] = $user['unique_id'];
			$response['user']['name'] = $user['name'];
			$response['user']['email'] = $user['email'];
			$response['user']['created_at'] = $user['created_at'];
			$response['user']['updated_at'] = $user['updated_at'];
			echo json_encode($response);
		}
		else{
			// user not found
			// echo json with error = 1
			$response['error'] = 1;
			$response['error_msg'] = "Incorrect email or password!";
			echo json_encode($response);
		}
	}
	else if ($tag == 'register'){
		// Request type is register new user
		$name = $_POST['name'];
		$email = $_POST['email'];
		$password = $_POST['password'];

		// check if user is already existing
		if ($db->isUserExist($email)){
			// user is already existing - error response
			$resposnep['error'] = 2;
			$responsep['error_msg'] = "User already existing";
			echo json_encode($response);
		}
		else{
			// store user
			$user = $db->storeUser($name, $email, $password);
			if($user){
				// user stored successfully
				$resposne['success'] = 1;
				$response['uid'] = $user['unique_id'];
				$response['user']['name'] = $user['name'];
				$response['user']['email'] = $user['email'];
				$response['user']['created_at'] = $user['created_at'];
				$response['user']['updated_at'] = $user['updated_at'];
				echo json_encode($response);
			}
			else{
				// user failed to store
				$responsep['error'] = 1;
				$response['error_msg'] = "Error occured in Registration";
				echo json_encode($response);
			}
		}
	}
	else{
		echo "Invalid Request";
	}
}
else{
	echo "Accessed Denied";
}

/*
 * Types of API JSON responses
 */
/*
// Registration Success Response
{
    "tag": "register",
    "success": 1,
    "error": 0,
    "uid": "4f074ca1e3df49.06340261",
    "user": {
        "name": "Ravi Tamada",
        "email": "ravi8x@gmail.com",
        "created_at": "2012-01-07 01:03:53",
        "updated_at": null
    }
}


// Registration Error Resposne - Error in storing
{
    "tag": "register",
    "success": 0,
    "error": 1,
    "error_msg": "Error occured in Registartion"
}

// Registration Error Response - User Already Existing
{
    "tag": "register",
    "success": 0,
    "error": 2,
    "error_msg": "User already existed"
}

// Login Success Response - User login in
{
    "tag": "login",
    "success": 1,
    "error": 0,
    "uid": "4f074eca601fb8.88015924",
    "user": {
        "name": "Ravi Tamada",
        "email": "ravi8x@gmail.com",
        "created_at": "2012-01-07 01:03:53",
        "updated_at": null
    }
}
// Login Error Resposne - Login Error, Incorrect username
{
    "tag": "login",
    "success": 0,
    "error": 1,
    "error_msg": "Incorrect email or password!"
}
*/

?>

