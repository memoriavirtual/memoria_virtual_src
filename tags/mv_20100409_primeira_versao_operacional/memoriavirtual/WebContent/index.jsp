<%-- 
    Document   : index.jsp
    Created on : 16.07.2009, 23:38:37
    Author     : Fabricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>

<html>
    <head>
        <templates:HeadDefault />
        <title>${applicationName}</title>
    </head>

    <body>
        <c:redirect url="authentication/Login.action" />
    </body>
    
</html>
