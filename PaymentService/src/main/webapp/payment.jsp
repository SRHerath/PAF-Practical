<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.2.1.min.js"></script>
        <script src="Components/payment.js"></script>
		<title>Payment Management</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<h1>Payment Management</h1>
					<form id="formPayment" name="formPayment" method="POST" action="payment.jsp">
						Owner's Name: 
						<input 
							id="CardName" 
							name="CardName" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Card Number: 
						<input 
							id="CardNumber"
							name="CardNumber" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Expiration Month: 
						<input 
							id="Ex_Month" 
							name="Ex_Month" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Expiration Year: 
						<input 
							id="Ex_Year" 
							name="Ex_Year" 
							type="text" 
							class="form-control form-control-sm"
						><br>
						
						Amount: 
						<input 
							id="Amount" 
							name="Amount" 
							type="text" 
							class="form-control form-control-sm"
						><br>		
						
						CV: 
						<input 
							id="CV" 
							name="CV" 
							type="text" 
							class="form-control form-control-sm"
						><br>					

						Date: 
						<input 
							id="Date" 
							name="Date" 
							type="text" 
							class="form-control form-control-sm"
						><br>	
						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						>

						<input type="hidden" name="hidPayIDSave" id="hidPayIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divPaymentGrid">
						<%
							Payment payment = new Payment(); 
							out.print(payment.readPayment());
						%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>