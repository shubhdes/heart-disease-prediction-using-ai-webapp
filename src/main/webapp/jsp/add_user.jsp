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
    $('#frmsignup').formValidation({
       
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
            txtemailid: {
                validators: {
                    notEmpty:{
                         message: 'The emailid is required'
                    },
                    emailAddress: {message: 'Invalid emailid format'}
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
            txtpassword: {
                validators: {
                    notEmpty: {
                        message: 'The password value is required'
                        
                    },
                    identical: {
                        field: 'txtconfirmpassword',
                        message: 'The password and confirm password do not match'

                    },
                    different: {
                        field: 'txtfname' ,
                        message: 'The password cannot be the same as first name'
                      
                    },
                    
                    
                    stringLength: {
                        min: 6,
                        message: 'The password must be less than 8 characters'
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

$(document).ready(function() {
    $('#frmlogin').formValidation({
       
        framework: 'bootstrap',
         container: 'popover',
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
                         message: 'The emailid is required'
                    },
                    emailAddress: {message: 'Invalid emailid format'}
                }
            },
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
            }
            
        }
          
    });
});
        
    </script>
    </head>
    <body>
        <div class="container-fluid" style="padding-top: 20px">
        <div class="row"  >
           
         <div class="col-md-6 col-md-offset-1" >
           ${errormsg}
             <div class="panel panel-info" >
                     <div class="panel-heading" style="font-weight: bolder;  "><i class="fa fa-user-plus" ></i> Sign up</div>
                     <div class="panel-body">

                  <form role="form" name="frmsignup" id="frmsignup" action="${pageContext.request.contextPath}/CreateRegistrationFirstStepController" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtfname" placeholder="Enter First Name" value="${txtfname}">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtlname" placeholder="Enter Last Name"  value="${txtlname}">
                    </div>
                    <div class="form-group">
                        <input type="email" class="form-control" name="txtemailid"  placeholder="Enter Email Id" ${autofocus}>
                    </div>
                    <div class="form-group">
                        <input type="tel" class="form-control" name="txtmobile" placeholder="Enter Mobile" maxlength="10"  value="${txtmobile}">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="txtpassword" placeholder="Enter Password" onCopy="return false" onPaste="return false">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="txtconfirmpassword" placeholder="Re-enter Password" onCopy="return false" onPaste="return false">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtsecretquestion" placeholder="Enter Secret Question" value="${txtsecretquestion}" >
                    </div>
                     <div class="form-group">
                        <input type="text" class="form-control" name="txtanswer0" placeholder="Enter Answer"  value="${txtanswer0}">
                    </div>                   
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-info" id="register" style="width:25%;font-weight: bolder;">Register</button>
                    </div>
              </form>                            
            </div>
             </div>
         </div>
                    <div class="col-md-3 col-md-offset-1" >
           ${msg}
             <div class="panel panel-info"  >
                     <div class="panel-heading" style="font-weight: bolder;  "><i class="fa fa-sign-in"></i> Sign in</div>
                     <div class="panel-body">

                  <form role="form" name="frmlogin" id="frmlogin" action="${pageContext.request.contextPath}/LoginController" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" name="txtuserid" placeholder="Enter User Id" value="${fname}">
                    </div>
                   
                    <div class="form-group">
                        <input type="password" class="form-control" name="txtpassword" placeholder="Enter password" onCopy="return false" onPaste="return false">
                    </div>
                   
                    <div class="form-group ">
                        <button type="submit" class="btn btn-info" id="login" style=";font-weight: bolder;">Login</button>
                        <a href="${pageContext.request.contextPath}/jsp/forgot_password.jsp" style="float: right">Forgot Password</a>
                    </div>
              </form>                            
            </div>
    </div>
                    </div>
    </body>
</html>
