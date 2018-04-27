/**
 * 
 */

window.addEventListener("load", populateReimbs);

function populateReimbs() {
	let empViewUrl = "http://localhost:8082/ersproject1/manager_reimb";
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
	
	function displayEmpReimb(xhr) {
		let arrOfReimbs = JSON.parse(xhr.responseText);
		
		let table = document.getElementById("myTable");
		let select = document.getElementById("ids");
		select.setAttribute("style", "border-width: 1.2px; border-color: black; border-radius: 10px 10px");
		
		// Go through the array of JSON reimbursement objects
		// and inject them into a table row
		for(reimb in arrOfReimbs) {
			let reimbId = arrOfReimbs[reimb].reimbId;
			let reimbAmount = arrOfReimbs[reimb].amount;
			let reimbSub = new Date(arrOfReimbs[reimb].reimbSubmitted);
			let reimbRes;
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
			// Only append to drop-down ids with 'pending' status
			if(reimbStatus === "Pending") {
				let option = document.createElement("option");
				option.innerHTML = `
				${reimbId}`	
				select.appendChild(option);				
			}
			
			let row = document.createElement("tr");
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
}