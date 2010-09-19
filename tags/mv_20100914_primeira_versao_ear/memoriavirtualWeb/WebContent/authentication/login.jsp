<%-- 
    Document   : login
    Created on : 30.06.2009, 13:06:53
    Author     : Fabricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>

<%@include file="/templates/Header.jsp" %>

<html>
    <head>
        <templates:HeadDefault />
        <title>${initParam.applicationName}</title>
    </head>


        <body class="mainBody">
            <templates:BodyDefault mainHeader="${initParam.applicationName}">
                <p><s:text name="login.message"/></p>
                <br />
                <s:form namespace="/authentication" action="EvalLogin">
                    <s:textfield key="login.username" />
                    <br />
                    <s:password key="login.password" />
                    <br />
                    <s:submit />
                </s:form>
            </templates:BodyDefault>
        </body>

</html>
