<%-- 
    Document   : newpaper
    Created on : Feb 2, 2017, 2:13:50 PM
    Author     : shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Heart Disease Prediction System Using AI</title>
          <%@include file="head.jsp" %>
        <script>
            
              $(document).ready(function() {
            ${msg}
        });


            
            
            $(document).ready(function() {
    $('#frmchangepassword').formValidation({
       
        framework: 'bootstrap',
        
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {

	     txtoldpassword: {
                validators: {
                    notEmpty: {
                        message: 'The password value is required'
                        
                    }
                 }
            },

             txtnewpassword: {
                validators: {
                    notEmpty: {
                        message: 'The new password value is required'
                        
                    },
                    different: {
                        field: 'txtoldpassword',
                        message: 'Old Password and new password cannot be same'

                    },
                    identical: {
                        field: 'txtreenterpassword',
                        message: 'The password and confirm password do not match'

                    }
                 }
            },
            txtreenterpassword: {
                validators: {
                    notEmpty: {
                        message: 'The confirm password value is required'
                        
                    },
                    identical: {
                        field: 'txtnewpassword',
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
        <%
        
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        if((String)session.getAttribute("txtuserid")==null)
               {
            response.sendRedirect("jsp/home.jsp");
            return;
               }
       
        %>
      <div class="container-fluid" >
         <div class="row" >
             
             <div class="col-md-offset-4 col-md-4">
                 
                 <div class="panel panel-info" >
                   <div class="panel-heading" style="font-weight: bolder;">Change Password  </div>
                    <div class="panel-body" >
                    <form role="form"  id="frmchangepassword" action="${pageContext.request.contextPath}/UpdatePasswordController" method="post"  >
                                        <div class="form-group">
                                            <label for="txtoldpin">Enter Old Password:</label>
                                            <input type="password" class="form-control" name="txtoldpassword" >
                                        </div>
                                     <div class="form-group ">
                                            <label for="txtnewpin">Enter New Password:</label>
                                            <input type="password" class="form-control" name="txtnewpassword" >
                                        </div>
                                     <div class="form-group">
                                            <label for="txtreenterpin">Re-enter New Password:</label>
                                            <input type="password" class="form-control" name="txtreenterpassword" >
                                        </div>
                                               
                                        <div class="form-group text-center">
                                            <button type="submit"  class="btn btn-info">Change</button>
                                        </div>
                                  </form>
            </div>
             
         </div>
         </div>
<div id="modal-msg" class="modal fade" role="dialog" style="padding-top: 200px">
  <div class="modal-dialog modal-sm">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title" >${m2}</h4>
      </div>
      <div class="modal-body">
        <p>${m1}</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>  

    </body>
</html>





