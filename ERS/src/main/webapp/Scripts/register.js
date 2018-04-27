/* Was attempting at an earlier stage to use the onsubmit for validation, but it still wouldn't allow for me
 * to check the uniqueness of the username and email, I wasn't sure how to handle it other than how I did, but
 * I don't feel it is ideal as it doesn't display any information as to whether it was a success/failure.
 */
document.getElementById("create-btn").addEventListener("submit", validateInput);

function validateInput(event) {
	event.preventDefault();
	let email = document.getElementById("email").value;
	let emailRegx = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	let password = document.getElementById("password").value;
	let passwordRegx = /(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$!*@%&]).{5,8}$/;
	
	if(!emailRegx.test(email)) {
		let el = document.getElementById("email");
		el.setCustomValidity("Invalid Email");
		return false;
	}else if(!passwordRegx.test(password)) {
		console.log("failed password")
		let el = document.getElementById("password");
		el.setCustomValidity("Password does not meet specified constraints");
		return false;
	}else {
		console.log("true");
		return false;
	}
}





