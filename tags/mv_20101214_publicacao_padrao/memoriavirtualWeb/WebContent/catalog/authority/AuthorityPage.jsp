<%-- 
    Document   : AddAuthor
    Created on : 16.10.2009, 20:19:39
    Author     : Fabricio
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

                    <h1>Autoria > Novo Cadastro </h1>

                    <div class="boxsp">

                        <s:form id="myForm" namespace="/authority" action="SaveAuthorityPage" >


                            <s:textfield label="Nome"
                                         name="name"
                                         id="authorName"
                                         disabled="false"
                                         cssStyle="width:470px;"
                                         />

                            <s:textfield label="Apelido"
                                         name="nickname"
                                         id="nickname"
                                         disabled="false"
                                         cssStyle="width:470px;"
                                         />

                            <s:textfield label="Data de Nascimento"
                                         name="birthdate"
                                         id="birthdate"
                                         disabled="false"
                                         cssStyle="width:470px;"/>

                            <s:textfield label="Data de Óbito"
                                         name="deathdate"
                                         id="deathdate"
                                         disabled="false"
                                         cssStyle="width:470px;"/>

                                <button id="transferButton"
                                        type="submit">Salvar</button>

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