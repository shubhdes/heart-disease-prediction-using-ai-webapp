<%-- 
    Document   : forgotpassword
    Created on : Jan 12, 2017, 1:01:57 PM
    Author     : shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Heart Disease Prediction System Using AI</title>
         <%@include file="head.jsp" %>
         
         <script>
             
             $(document).ready(function() {
    $('#frmforgotpassword').formValidation({
       
        framework: 'bootstrap',
       
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {
             txtuserid: {
                validators: {
                    notEmpty:{
                         message: 'The userid is required'
                    },
                    emailAddress: {message: 'Invalid emailid format'}
                }
            }
        }
    });
}); 
         </script>
         <style>
             
         </style>     
        
    </head>
    <body>
      
      
       <div class="container-fluid" >
        <div class="row">
         <div class="col-md-4 col-md-offset-4" >
              ${errormsg}
           <div class="well" style="padding-top: 0px;padding-left: 0px;padding-right: 0px;width:418px;" >  
        <div class="btn-group btn-breadcrumb">
            <a href="#" class="btn btn-default completed" style="font-weight:  bolder; ">Basic Details</a>
            <a href="#" class="btn btn-default notcompleted" style="font-weight:  bolder;">OTP/Hint answer</a>
            <a href="#" class="btn btn-default" style="font-weight:  bolder">New Password</a>
        </div>
	   
          
               <div class="container-fluid">   
           <form role="form" name="frmforgotpassword" id="frmforgotpassword" action="${pageContext.request.contextPath}/ForgotPasswordFirstStepController" style="padding-top: 10px"  >
               
               <div class="form-group"  >
                       <label for="txtuserid">Enter User Id:</label>
                        <input type="text" class="form-control col-md-2" id="txtuserid" name="txtuserid" placeholder="Enter User Id" ${autofocus}>
                </div>
                <label for="" style="text-align:center">Choose how do you want to set new password?</label>
                    <div class="radio">
                        <label><input type="radio" name="txtoption" value="secret">Answer Secret Question</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="txtoption" value="otp">Generate OTP</label>
                    </div>    
                            
                    <ul class="pager ">
                        <button type="submit"  class=" btn btn-success pull-right">Next >></button>
                    </ul>
                 
              </form> 
               </div>
              </div>
            </div>
    </div>
       </div>
    </body>
    
</html>
