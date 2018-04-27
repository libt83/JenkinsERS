

document.getElementById("add").addEventListener("click", displayAddReimbForm);
// Displays the add reimbursement form
function displayAddReimbForm() {
	let el1 = document.getElementById("reimb-form");
	let el2 = document.getElementById("table");
	let el3 = document.getElementById("search");
	
	if(el3.style.visibility === "visible") {
		el3.style.visibility = "collapse";
	}
	
	if(el2.style.visibility === "visible") {
		el2.style.visibility = "collapse";
	}

	if(el1.style.visibility === "collapse") {
		el1.style.visibility = "visible";
	}
};

document.getElementById("view").addEventListener("click", displayUserReimbsTable);
// Displays user reimbursements
function displayUserReimbsTable() {
	let empViewUrl = "http://localhost:8082/ersproject1/view_emp_reimb";
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
	function displayEmpReimb(xhr) {
		let arrOfReimbs = JSON.parse(xhr.responseText);
		$("#myTable").empty();
		
		let table = document.getElementById("myTable");	
		// Go through the array of JSON reimbursement objects
		// and inject them into a table row
		for(reimb in arrOfReimbs) {
			let reimbId = arrOfReimbs[reimb].reimbId;
			let reimbAmount = arrOfReimbs[reimb].amount;
			let reimbSub = new Date(arrOfReimbs[reimb].reimbSubmitted);
			if(!arrOfReimbs[reimb].reimbResolved) {
				reimbRes = "";
			}else {
				reimbRes = new Date(arrOfReimbs[reimb].reimbResolved);
			}
			let reimbDescr = arrOfReimbs[reimb].reimbDescription;
			let reimbResolver = arrOfReimbs[reimb].reimbResolver;
			if(!reimbResolver) {
				reimbResolver = "Not resolved";
			}
			let reimbStatus = arrOfReimbs[reimb].reimbStatusId;
			switch(reimbStatus) {
			case 1:
				reimbStatus = "Approved";
				break;
			case 2:
				reimbStatus = "Denied";
				break;
			case 3:
				reimbStatus = "Pending";
				break;
			}
			let reimbType = arrOfReimbs[reimb].reimbTypeId;
			switch(reimbType) {
			case 1:
				reimbType = "Lodging";
				break;
			case 2:
				reimbType = "Travel";
				break;
			case 3:
				reimbType = "Food";
				break;
			case 4:
				reimbType = "Other";
			}
			
			row = document.createElement("tr");
			row.innerHTML = `
				<td style="border: 1px solid black">${reimbId}</td>
				<td style="border: 1px solid black">$${reimbAmount}</td>
				<td style="border: 1px solid black">${reimbSub}</td>
				<td style="border: 1px solid black">${reimbRes}</td>
				<td style="border: 1px solid black">${reimbDescr}</td>
				<td style="border: 1px solid black">${reimbResolver}</td>
				<td style="border: 1px solid black">${reimbStatus}</td>
				<td style="border: 1px solid black">${reimbType}</td>`
				
				table.appendChild(row);
		}
	}
	let el1 = document.getElementById("reimb-form");
	let el2 = document.getElementById("table");
	let el3 = document.getElementById("search");

	if(el1.style.visibility === "visible") {
		el1.style.visibility = "collapse";
	}

	if(el2.style.visibility === "collapse") {
		el2.style.visibility = "visible";
	}
	
	if(el3.style.visibility === "collapse") {
		el3.style.visibility = "visible";
	}
};
