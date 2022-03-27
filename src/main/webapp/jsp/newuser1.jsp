<%-- 
    Document   : signup1
    Created on : Jan 9, 2017, 5:22:15 PM
    Author     : Administrator
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
    $('#frmsignup1').formValidation({
       
        framework: 'bootstrap',
         container: 'tooltip',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {
             
            txtotp1: {
                validators: {
                    notEmpty:{
                         message: 'Please enter OTP value'
                    }
                   
                }
            }
            
        }
          
    });
});
        </script>
    </head>
    <body>
       
        <div class="row" style="padding-top:100px" >
         <div class="col-md-4 col-md-offset-4" >
           ${errormsg}
             <legend style="border-bottom:2px solid">Enter OTP value</legend>
                  <form role="form" name="frmsignup1" id="frmsignup1" action="${pageContext.request.contextPath}/CreateRegistrationSecondStepController" method="post"  >
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtotp1" placeholder="Input OTP here" ${autofocus} ${disabled} >
                    </div>
                           <input type="hidden"  name="txtfname" value="${txtfname}">  
                            <input type="hidden"  name="txtlname" value="${txtlname}">
                             <input type="hidden"  name="txtmobile" value="${txtmobile}">
                              <input type="hidden"  name="txtemailid" value="${txtemailid}">
                              <input type="hidden"  name="txtsecretquestion" value="${txtsecretquestion}">
                              <input type="hidden"  name="txtanswer0" value="${txtanswer0}">
                               <input type="hidden"  name="txtotp0" value="${txtotp0}" >
                               <input type="hidden"  name="txtpassword" value="${txtpassword}" >
                    <div class="form-group ">
                        <button type="submit"  class="btn btn-danger"  ${disabled}  >Submit</button>
                    </div>
              </form>                            
            </div>
    </div>
      
    </body>
</html>
