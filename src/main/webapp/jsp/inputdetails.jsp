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
    $('#frminput').formValidation({
       
        framework: 'bootstrap',
        
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        
        live:'disabled',
        fields: {
            txtage: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    greaterThan:{value:18,message:'Age should be greater than 18'},
                    lessThan:{value:101,message:'Age should be less than or equal to 100'}
                }
            },
            
            txtcigs: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    greaterThan:{value:-1,message:'Please enter value greter than or equal to 0'},
                    lessThan:{value:100,message:'Value should be less or equal to 100'}
                }
            },
            
             txtchol: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    greaterThan:{value:124,message:'Please enter value greter than or equal to 125'},
                    lessThan:{value:566,message:'Value should be less or equal to 565'}
                }
            },
            
            txtdia: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    greaterThan:{value:39,message:'Please enter value greter than or equal to 40'},
                    lessThan:{value:201,message:'Value should be less or equal to 200'}
                }
            },
            
            txtsys: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    greaterThan:{value:81,message:'Please enter value greter than or equal to 80'},
                    lessThan:{value:201,message:'Value should be less or equal to 200'}
                }
            },
            
            txtheartrate: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    greaterThan:{value:71,message:'Please enter value greter than or equal to 70'},
                    lessThan:{value:201,message:'Value should be less or equal to 200'}
                }
            },
            
            txtglu: {
                validators: {
                    notEmpty: {},
                    digits: {},
                    greaterThan:{value:41,message:'Please enter value greater than or equal to 40'},
                    lessThan:{value:401,message:'Value should be less or equal to 400'}
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
           
         <div class="col-md-6 col-md-offset-3" >
           ${errormsg}
             <div class="panel panel-info" >
                     <div class="panel-heading" style="font-weight: bolder;  "><i class="fa fa-user-plus" ></i> Enter details</div>
                     <div class="panel-body">

                  <form role="form" name="frminput" id="frminput" action="${pageContext.request.contextPath}/PredictionController" method="post">
                     
                      <div class="form-group">
                          <label for="txtage">Enter age:</label>
                        <input type="text" class="form-control" name="txtage" placeholder="Enter age" maxlength="3"  value="${mobile}">
                    </div>
                    
                    <div class="form-group">
                         <label for="txtgender">Select Gender:</label>
                        <select class="form-control" name="txtgender" id="txtgender">
                                <option selected="true">Select Gender</option>
                                <option value="1">Male</option>
                                <option value="0">Female</option>
                               </select>
                            
                    </div>
                    
                     <div class="form-group">
                          <label for="txtcigs">Smoking (cigarettes/day):</label>
                        <input type="text" class="form-control" name="txtcigs" placeholder="Enter cigarettes    /day" maxlength="3"  value="${mobile}">
                    </div>
                    
                     <div class="form-group">
                          <label for="txtchol">Enter cholesterol level:</label>
                        <input type="text" class="form-control" name="txtchol" placeholder="Enter cholesterol" maxlength="3"  value="${mobile}">
                    </div>
                    
                    <div class="form-group">
                          <label for="txtdia">Enter diastolic BP level:</label>
                        <input type="text" class="form-control" name="txtdia" placeholder="Enter diastolic BP level" maxlength="3"  value="${mobile}">
                    </div>
                    
                    <div class="form-group">
                          <label for="txtsys">Enter systolic BP level:</label>
                        <input type="text" class="form-control" name="txtsys" placeholder="Enter systolic BP level" maxlength="3"  value="${mobile}">
                    </div>  
                    
                    <div class="form-group">
                         <label for="txtdiab">Select Diabetes:</label>
                        <select class="form-control" name="txtdiab" id="txtdiab">
                                <option selected="true">Select Diabetes</option>
                                <option value="1">YES</option>
                                <option value="0">NO</option>
                               </select>
                            
                    </div>
                    
                    <div class="form-group">
                          <label for="txtglu">Enter Glucose level:</label>
                        <input type="text" class="form-control" name="txtglu" placeholder="Enter Glucose level level" maxlength="3"  value="${mobile}">
                    </div>  
                   <div class="form-group">
                          <label for="txtheartrate">Enter heart rate:</label>
                        <input type="text" class="form-control" name="txtheartrate" placeholder="Enter heart rate" maxlength="3"  value="${mobile}">
                    </div>                    
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-info" id="register" style="width:25%;font-weight: bolder;">Submit</button>
                    </div>
              </form>                            
            </div>
             </div>
         </div>
                   
</html>
