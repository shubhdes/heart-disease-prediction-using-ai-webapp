<%-- 
    Document   : signup
    Created on : Jan 9, 2017, 4:32:19 PM
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
         var url = "<%= request.getContextPath() %>" + "/FetchRegistration?txtemailid=" + "<%= session.getAttribute("txtuserid") %>";
              $.get(url,function(data, status){
                  
               if(data!="")
               { 
                    var res = data.split("-");
                    $("#txtfname").val(res[0]);
                    $("#txtlname").val(res[1]);
                    $("#txtmobile").val(res[2]);
                    $("#txtsecretquestion").val(res[3]);
                    $("#txtanswer0").val(res[4]);
                    
               }
    
    });
    
         });
         
         $(document).ready(function() {
            ${msg}
        });
        
      $(document).ready(function() {
    $('#frmeditprofile').formValidation({
       
        framework: 'bootstrap',
        
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {
             txtfname: {
                validators: {
                    notEmpty: {
                        message: 'The first name is required'
                        
                    },
                    
                    
                    regexp: {
                        regexp: /^[a-zA-Z]+$/,
                        message: 'The first name can only consist of alphabetical, number and underscore'
                    }
                }
            },
            txtlname: {
                validators: {
                    notEmpty: {
                        message: 'The last name is required'
                        
                    },
                    
                    
                    regexp: {
                        regexp: /^[a-zA-Z]+$/,
                        message: 'The last name can only consist of alphabetical, number and underscore'
                    }
                }
            },
          
             txtmobile: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    phone: {
                        country: 'IN'
                    }
                }
            },
            
            txtsecretquestion: {
                validators: {
                    notEmpty: {
                        message: 'A secret question is required'
                        
                    }
                    
                }
            },
            txtanswer0: {
                validators: {
                    notEmpty: {
                        message: 'The answer value is required'
                        
                    }
                    
                }
            }
            
        }
          
    });
});


        
    </script>
    </head>
    <body>
      
        <div class="container-fluid" style="padding-top: 20px">
        <div class="row"  >
         <div class="col-md-4 col-md-offset-4" >
          
             <div class="panel panel-danger" >
                     
                     <div class="panel-body">

                  <form role="form" name="frmeditprofile" id="frmeditprofile" action="${pageContext.request.contextPath}/UpdateRegistrationController" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtfname" id="txtfname" placeholder="Enter First Name" >
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtlname" id="txtlname" placeholder="Enter Last Name"  >
                    </div>
                    
                    <div class="form-group">
                        <input type="tel" class="form-control" name="txtmobile" id="txtmobile" placeholder="Enter Mobile" maxlength="10">
                    </div>
                    
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtsecretquestion" id="txtsecretquestion" placeholder="Enter Secret Question" >
                    </div>
                     <div class="form-group">
                        <input type="text" class="form-control" name="txtanswer0" id="txtanswer0" placeholder="Enter Answer" >
                    </div>                   
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-danger" id="register" style=";font-weight: bolder;">Save changes</button>
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
        <h4 class="modal-title" >Success</h4>
      </div>
      <div class="modal-body">
        <p>Changed saved successfully.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
            
       <%
        
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        if((String)session.getAttribute("txtuserid")==null)
               {
            response.sendRedirect("jsp/home.jsp");
            return;
               }
       
        %>

    </body>
</html>
