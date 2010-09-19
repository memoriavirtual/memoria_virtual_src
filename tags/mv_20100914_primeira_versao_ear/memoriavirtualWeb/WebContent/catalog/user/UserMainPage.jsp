<%-- 
    Document   : UserMainPage
    Created on : 10/01/2010, 18:11:44
    Author     : Elisa Yumi Nakagawa
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
                        <div class="boxsp">

                            <s:form id="myForm" action="RenderUserPage" >

                                <s:hidden name="from" value="UserMainPage" />

                                <s:select id= "listOfUsers"
                                              name="userSelected"
                                              list="listOfUsers"
                                              listKey="id"
                                              listValue="name"
                                              size="4"
                                              label="Listagem de usuários"
                                              cssStyle="height:150px;width:800px;"
                                              onchange="optionToTextFields ('listOfInterventions', 'interventionData')"/>

                                <button id="newButton"
                                        name="newButton"
                                        type="submit"
                                        onclick="">Novo Cadastro</button>

                                <button id="editButton"
                                        name="editButton"
                                        type="submit"
                                        onclick="">Editar</button>

                                <button id="removeButton"
                                        name="removeButton"
                                        type="submit"
                                        onclick="">Excluir</button>

                            </s:form>
                        </div> <%-- boxsp --%>

            </div> <%-- groupsp --%>

            <div id="footersp">
                <%@include file="/Footer.jsp" %>
            </div>
        </div> <!-- Site Main Division -->
    </body>
</html>
