<%-- 
    Document   : forgotpassword1
    Created on : Jan 12, 2017, 4:21:29 PM
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
    $('#frmforgotpassword1').formValidation({
       
        framework: 'bootstrap',
        
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {
             txtsecret_ans: {
                validators: {
                    notEmpty: {
                        message: 'Please enter secret answer to continue'
                        
                    }
                    
                    
                   
                }
            
            }
            
        }
          
    });
});

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
             txtotp1: {
                validators: {
                    notEmpty: {
                        message: 'Please enter OTP sent to your emailid to continue'
                        
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
            <a href="#" class="btn btn-default completed" style="font-weight:  bolder;" >OTP/Hint answer</a>
            <a href="#" class="btn btn-default " style="font-weight:  bolder">New Password</a>
        </div>
	
       
      <div class="container-fluid">
           <div  ${div1} style="padding-top: 10px">
              <div class="text-primary">${m}</div>
                  <form role="form"  id="frmforgotpassword2" action="${pageContext.request.contextPath}/ForgotPasswordSecondStepController" method="post"  >
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtotp1" placeholder="Enter OTP value" maxlength="4" ${autofocus} >
                        <input type="hidden" name="txtotp0" value="${txtotp0}">
                        <input type="hidden" name="txtuserid" value="${txtuserid}">
                         <input type="hidden" name="txtoption" value="${txtoption}">
                    </div>
                     
                      <a class="btn btn-success" href="${pageContext.request.contextPath}/jsp/forgot_password.jsp"> << Previous</a>
                      <button type="submit" class="btn btn-success pull-right" >Next >></button>
                    
              </form>                            
           </div>
      
             <div  ${div2} style="padding-top: 10px">
                 
                  <form role="form"  id="frmforgotpassword1" action="${pageContext.request.contextPath}/ForgotPasswordSecondStepController" method="post"  >
                    <div class="form-group">
                        <label for="txtsecret_ans" > ${txtsecretquestion} </label>
                        <input type="text" class="form-control" name="txtanswer1" id="txtanswer1" placeholder="Enter answer"  >
                        <input type="hidden" name="txtsecretquestion" value="${txtsecretquestion}">
                        <input type="hidden" name="txtanswer0" value="${txtanswer0}">
                        <input type="hidden" name="txtuserid" value="${txtuserid}">
                         <input type="hidden" name="txtoption" value="${txtoption}">
                    </div>
                     <a class="btn btn-success" href="${pageContext.request.contextPath}/jsp/forgot_password.jsp"> << Previous</a>
                      <button type="submit" class="btn btn-success pull-right" >Next >></button>
                         
              </form>   
             </div>
            </div>
    </div>
        </div>
    </body>
</html>
