<%-- 
    Document   : forgotpassword2
    Created on : Jan 12, 2017, 4:24:10 PM
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
    $('#frmforgotpassword2').formValidation({
       
        framework: 'bootstrap',
       
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {
             txtpassword: {
                validators: {
                    notEmpty: {
                        message: 'The password value is required'
                        
                    },
                    identical: {
                        field: 'txtconfirmpassword',
                        message: 'The password and confirm password do not match'

                    }
                 }
            },
            txtconfirmpassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirm password value is required'
                        
                    },
                    identical: {
                        field: 'txtpassword',
                        message: 'The password and confirm password do not match'

                    }
                }
            }
        }
    });
}); 
         </script>
   
    </head>
    <body>
      
      
       <div class="container-fluid" >
       <div class="row"  >
         <div class="col-md-4 col-md-offset-4" >
           ${errormsg}
           <div class="well" style="padding-top: 0px;padding-left: 0px;padding-right: 0px;width:418px;" >    
        <div class="btn-group btn-breadcrumb">
            <a href="#" class="btn btn-default" style="font-weight:  bolder">Basic details</a>
            <a href="#" class="btn btn-default " style="font-weight:  bolder">OTP/Hint answer</a>
            <a href="#" class="btn btn-default completed" style="font-weight:  bolder;">New Password</a>
        </div>
	
           
                <div class="container-fluid">     
                  <form id="frmforgotpassword2" method="post" action="${pageContext.request.contextPath}/ForgotPasswordThirdStepController" style="padding-top: 10px"  >
                     <div class="form-group">
                        <input type="password" class="form-control" name="txtpassword" placeholder="Enter New Password"  >
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="txtconfirmpassword" placeholder="Re-enter Password"  >
                    </div>
                     <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary" >Submit</button>
                    </div>
                    <input type="hidden" name="txtuserid" value="${txtuserid}">
              </form>  
                </div>
            </div>
    </div>
        </div>
    </body>
</html>
