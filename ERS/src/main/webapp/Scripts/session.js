window.addEventListener("load", displayCurrentUserSessionInfo);
function displayCurrentUserSessionInfo() {
	let empViewUrl = "http://localhost:8082/ersproject1/session";
	function ajaxGetRequest(url, func){
		let xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if(this.readyState == 4 && this.status == 200){
				func(this);
			}
		}
		xhr.open("GET",url);
		xhr.send();	
	}

	ajaxGetRequest(empViewUrl, displayEmpReimb);
	
	// Parses JSON from GET request and fills the table with reimbursement rows
	// pertaining to the current user
	function displayerUsername(xhr) {
		let user = JSON.parse(xhr.responseText);
		console.log(user.username);
		let span = document.getElementById("LoggedIn");
		let username = user.username;
		span.innerHTML = `You are currently logged in as ${username}`;
	}
}

