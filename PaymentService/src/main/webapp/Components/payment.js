$(document).ready(function() {
    if ($('#alertSuccess').text().trim() == "") {
        $('#alertSuccess').hide();
    }

    $('#alertError').hide();
})


// SAVE
$(document).on("click","#btnSave", function(event) {
    // Clear alerts
    $("#alertSuccess").text(""); 
    $("#alertSuccess").hide(); 
    $("#alertError").text(""); 
    $("#alertError").hide();

    // Form validation
    var status = validatePaymentForm(); 
    if (status != true) 
    { 
        $("#alertError").text(status); 
        $("#alertError").show(); 
        return; 
    } 

// if hidPayIDSave value is null set as POST else set as PUT
    var type = ($("#hidPayIDSave").val() == "") ? "POST" : "PUT";

    // ajax communication
    $.ajax({
        url: "PaymentAPI",
        type: type,
        data: $("#formPayment").serialize(),
        dataType: "text",
        complete: function(response, status) {
            onPaymentSaveComplete(response.responseText, status);
        }
    });
});


// after completing save request
function onPaymentSaveComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully saved");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divPaymentGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while saving");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while saving");
        $("#alertError").show();
    } 

//resetting the form
    $("#hidPayIDSave").val("");
    $("#formPayment")[0].reset();
}


// UPDATE
//to identify the update button we didn't use an id we used a class
$(document).on("click", ".btnUpdate", function(event) 
{ 
    //get pay id from the data-payid attribute in update button
    $("#hidPayIDSave").val($(this).data('payid')); 
    //get data from <td> element
    $("#CardName").val($(this).closest("tr").find('td:eq(0)').text()); 
    $("#CardNumber").val($(this).closest("tr").find('td:eq(1)').text()); 
    $("#Ex_Month").val($(this).closest("tr").find('td:eq(2)').text()); 
    $("#Ex_Year").val($(this).closest("tr").find('td:eq(3)').text()); 
	$("#Amount").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#CV").val($(this).closest("tr").find('td:eq(5)').text());
	$("#Date").val($(this).closest("tr").find('td:eq(6)').text()); 
}); 


// DELETE
$(document).on("click",".btnRemove", function(event) {
    // ajax communication
    $.ajax({
        url: "PaymentAPI",
        type: "DELETE",
        data: "PayID=" + $(this).data("payid"),
        dataType: "text",
        complete: function(response, status) {
            onPaymentDeleteComplete(response.responseText, status);
        }
    });
});


// after completing delete request
function onPaymentDeleteComplete(response, status) {

    if (status == "success") { //if the response status is success
        var resultSet = JSON.parse(response);

        if (resultSet.status.trim() === "success") { //if the json status is success
            //display success alert
            $("#alertSuccess").text("Successfully deleted");
            $("#alertSuccess").show();
    
            //load data in json to html
            $("#divPaymentGrid").html(resultSet.data);

        } else if (resultSet.status.trim() === "error") { //if the json status is error
            //display error alert
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") { 
        //if the response status is error
        $("#alertError").text("Error while deleting");
        $("#alertError").show();
    } else { 
        //if an unknown error occurred
        $("#alertError").text("Unknown error occurred while deleting");
        $("#alertError").show();
    } 
}


// VALIDATION
function validatePaymentForm() { 
    // NAME
    if ($("#CardName").val().trim() == "") 
    { 
        return "Insert Card Name."; 
    } 
    
    // NUMBER
    if ($("#CardNumber").val().trim() == "") 
    { 
        return "Insert Card Number."; 
    } 

    // MONTH
    if ($("#Ex_Month").val().trim() == "") 
    { 
        return "Insert Expiratin Month."; 
    } 

    // YEAR
    if ($("#Ex_Year").val().trim() == "") 
    { 
        return "Insert Expiratin Year."; 
    } 
    
    // PRICE
    if ($("#Amount").val().trim() == "") 
    { 
        return "Insert Amount."; 
    } 
    
    // is numerical value 
    var tmpPrice = $("#Amount").val().trim(); 
    if (!$.isNumeric(tmpPrice)) 
    { 
        return "Insert a numerical value for Amount."; 
    } 

	// convert to decimal price 
    $("#Amount").val(parseFloat(tmpPrice).toFixed(4)); 

	// CV
    if ($("#CV").val().trim() == "") 
    { 
        return "Insert CV."; 
    } 

	// DATE
    if ($("#Date").val().trim() == "") 
    { 
        return "Insert Date."; 
    } 

return true; 
} 

