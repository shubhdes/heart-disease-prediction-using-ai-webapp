<%-- 
    Document   : head
    Created on : Jan 9, 2017, 4:35:41 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <title>Heart Disease Prediction System Using AI</title>
       

  		<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/formValidation.css"/> <%--used for validation--%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome-4.7.0/css/font-awesome.css"/> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/breadcrumbs.css"/>
        <!--  <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap-datetimepicker.css"/>-->
        <!--  <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/bootstrap-magnify.css">-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/css/magnify.css">
        
         <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery-3.1.1.js"></script>
         <!--  <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/jquery-1.12.0.js"></script>-->
         <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
         <!-- <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/moment.js"></script> -->
        <!--  <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap-datetimepicker.js"></script> -->
         <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/formValidation.js"></script> <%--used for validation--%>
         <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap_1.js"></script>  <%--used for validation--%>
        <!-- <script src="${pageContext.request.contextPath}/bootstrap-3.3.7-dist/js/bootstrap-magnify.js"></script> -->

        
       <script>
                  
                  $(document).ready(function(){
                
                 
                  var m=window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
                 
                  if(m=='newuser.jsp' || m=='Login' || m=='Signup')
                  {
                         
                      $("#d1").hide();
                   
                  }
                  
           });
         


          
           $(document).ready(function(){
                
                 
                  var m=window.location.pathname.substring(window.location.pathname.lastIndexOf("/")+1);
                 
                  if(m=='signup.jsp' || m=='Login' || m=='Signup')
                  {
                         
                      $("#d1").hide();
                   
                  }
                  
           });
         
                        
$(document).ready(function(){
    $("#login").click(function(){
        $("#modal_login").modal();
    });
});
</script>
<style>
    .row {
        height: 100px;
       
      }
    @media screen and (max-width: 1200px) {
      .row {
        height: auto;
     
      }
      
    }
</style>


    </head>
    
    <body>
        <%
        int count=0;
            session=request.getSession(false);
            java.util.Enumeration e=session.getAttributeNames();
            while(e.hasMoreElements())
            {
                String n=(String)e.nextElement();
                if(n.charAt(0)=='E')
                {
                    count++;
                }
            }    
        %>
       
             <div class="container-fluid">
                 <div class="row" style="background-color:#269abc;color :white;padding-top: 20px;height: 100%">
                <div class="col-md-6">
            <p style="font-size: 40px;font-weight: bolder;font-style: oblique;font-family:cursive;padding-left: 1%"><a href="${pageContext.request.contextPath}/jsp/home.jsp" style="text-decoration: none;color:white">Heart Disease Predictor</a></p>
            
           </div>
                
                
              <%
                if(session.getAttribute("txtuserid")!=null && ((String)session.getAttribute("txtuserid")).equalsIgnoreCase("admin@hrp.com"))
                {
              %>
              
              <div class="col-md-2 col-md-offset-2" id="d2">
                  
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><p style="text-align: center;cursor: pointer;color:white"><i class="fa fa-2x fa-user-secret "></i></p>
                <p style="text-align: center;font-size: 15px;font-weight: bolder;cursor: pointer;color:white">Hello, <%=session.getAttribute("txtfname") %> <i class="fa fa-angle-down" ></i></p></a>
              <ul class="dropdown-menu dropdown-menu-right">
                  <li><a href="${pageContext.request.contextPath}/jsp/addproduct.jsp">Add Product</a></li>
                  <li><a href="${pageContext.request.contextPath}/jsp/viewproduct.jsp">View Product/Stock</a></li>
                  <li><a href="${pageContext.request.contextPath}/jsp/editproduct.jsp">Edit Product</a></li>
                  <li><a href="${pageContext.request.contextPath}/jsp/vieworders.jsp">View Orders</a></li>
                  <li><a href="${pageContext.request.contextPath}/jsp/viewcustomer.jsp">View Customers</a></li>
                  <li><a href="${pageContext.request.contextPath}/jsp/viewfeedback.jsp">View Feedback</a></li>
                  
                   <li><hr></hr></li>
                   <li><a href="${pageContext.request.contextPath}/jsp/changepassword.jsp" style="margin-top:-10px">Change Password</a></li>
                   <li><a href="${pageContext.request.contextPath}/jsp/editprofile.jsp">Edit Profile</a></li>
                   <li ><a href="${pageContext.request.contextPath}/jsp/signout.jsp" >Sign out</a></li>
            </ul>  
              </div> 
              <%
                }
                else if(session.getAttribute("txtuserid")!=null)
                {
                    
              %>
               <div class="col-md-2 col-md-offset-2" id="d2">
                  
                   
            <a class="dropdown-toggle" style="display: inline-block;color:white" data-toggle="dropdown" href="#"><p style="text-align: center;cursor: pointer;"><i  class="fa fa-2x fa-user-circle-o"></i></p>
                <p style="text-align: center;font-size: 15px;font-weight: bolder;cursor: pointer">Hello, <%=session.getAttribute("txtfname") %> <i class="fa fa-angle-down"></i></p></a>
              <ul class="dropdown-menu dropdown-menu-right">
                  <li><a href="${pageContext.request.contextPath}/jsp/inputdetails.jsp">Get Prediction</a></li>
                  
                   <li><hr></hr></li>
                   <li><a href="${pageContext.request.contextPath}/jsp/changepassword.jsp" style="margin-top:-10px">Change Password</a></li>
                   <li><a href="${pageContext.request.contextPath}/jsp/editprofile.jsp">Edit Profile</a></li>
                   <li ><a href="${pageContext.request.contextPath}/jsp/signout.jsp" >Sign out</a></li>
            </ul>  
              </div> 
             <% 
                }
                else
                {    
               %>
               <div class="col-md-2 col-md-offset-4" id="d1">
                    
                <a href="${pageContext.request.contextPath}/jsp/newuser.jsp" style="display:inline-block"><p style="text-align: center;cursor: pointer;"><i class="fa fa-2x fa-user" style="color:white;background-color: #269abc"></i></p>
            <p style="text-align: center;font-size: 15px;font-weight: bolder;cursor: pointer;color:white">Login / Sign up</p></a>
                </div>
               <%
                             }
                %>
             </div>
                     
            </div>
</div>
        
        <nav class="navbar navbar-default" style="background-color:#269abc;" >
  
</nav>

        
      
    </body>
    
      
</html>
