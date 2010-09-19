<%-- 
    Document   : AddDescriptor
    Created on : 10/01/2010, 20:31:53
    Author     : Elisa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>

<html>
    <head>
        <script language='javascript' type='text/javascript' src='${initParam.appPath}/catalog/heritage/common.js' ></script>

        <templates:HeadDefault />
        <title>${initParam.applicationName}</title>
    </head>

    <body>
        <div id="sitemain"> <!-- Site main division -->

            <%@include file="/templates/logo.jsp" %>

            <div id="topmenusp">
                <%@include file="/catalog/TopMenu.jsp" %>
            </div> <%-- topmenusp --%>

            <div id="groupsp">

                <div id="maincontentsp" style="margin-left:0px;">

                    <h1>Descritor > Novo Cadastro </h1>

                    <div class="boxsp">

                        <s:form id="myForm" namespace="/descriptor" action="SaveDescriptorPage" >

                                <s:textarea name="descriptor"
                                             label="Novo descritor"
                                             cssStyle="width:470px;" />

                                <button type="submit">Salvar</button>

                            </s:form>
                        </div> <%-- boxsp --%>

                </div> <%-- maincontentsp --%>
            </div> <%-- groupsp --%>

            <div id="footersp">
                <%@include file="/Footer.jsp" %>
            </div> <%-- Footersp --%>
        </div> <%-- Site Main Division --%>
    </body>
</html>