<%-- 
    Document   : home
    Created on : Jan 1, 2017, 12:19:18 AM
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
        ${msg1}           
             
 

            

$(document).ready(function() {
   $('#frmlogin').formValidation({
       
       framework: 'bootstrap',
       container:'tooltip',
       icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {
            
            txt_username: {
                validators: {
                    notEmpty:{
                         message: 'User Id is required'
                    },
                    emailAddress: {
                        message:'Invalid format'
                    }
                }
            },
            txt_password: {
                validators: {
                    notEmpty:{
                         message: 'Password is required'
                    }
                    
                }
            }
        }
       
        });
    });
    
        </script>
        
    </head>
    <body>
        
     <%
        
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        
       
        %>

      <%
           session=request.getSession(true);
          
         %>

        <div class="container-fluid">
 <!--        <div class="row" >
             <div class="col-md-12">
                
                  
                  <div class="panel panel-danger" >
        
        <div class="panel-body"><img src="${pageContext.request.contextPath}/images/heart.jpg" class="img-responsive" style="width:100%;" alt="Offer 1"></div>
        </div>
           
                 
           
             </div>
            
                
</div>
         -->
        </div>   
        

    </body>
</html>
