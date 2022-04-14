<%-- 
    Document   : signout
    Created on : Feb 24, 2018, 1:14:59 PM
    Author     : shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <title>Heart Disease Prediction System Using AI</title>
    </head>
    <body>
        <%
            session.removeAttribute("txtuserid");
            session.removeAttribute("txtfname");
          
            response.sendRedirect("home.jsp");
        %>
    </body>
</html>
