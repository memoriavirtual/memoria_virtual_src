<%--
    Document   : CreateHeritage
    Created on : 10.01.2010, 08:27:50
    Author     : Fabricio Zancanella and Elisa Yumi Nakagawa
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

                            <s:form id="myForm" namespace="/institution" action="RenderInstitutionPage" >

                                <s:hidden name="from" value="InstitutionMainPage" />

                                <s:select id= "listOfInstitutions"
                                              name="selectedInstitution"
                                              list="listOfInstitutions"
                                              listKey="id"
                                              listValue="name"
                                              size="4"
                                              multiple="false"
                                              label="Listagem de instituições"
                                              cssStyle="height:150px;width:800px;"
                                              onchange="optionToTextFields ('listOfInterventions', 'interventionData')"/>

                                <button id="newButton"
                                        name="newInstitutionButton"
                                        type="submit">Novo Cadastro</button>

                                <button id="button"
                                        name="editInstitutionButton"
                                        type="submit">Editar</button>

                                <button id="button"
                                        name="removeInstitutionButton"
                                        type="submit">Excluir</button>

                            </s:form>
                        </div> <%-- boxsp --%>

            </div> <%-- groupsp --%>

            <div id="footersp">
                <%@include file="/Footer.jsp" %>
            </div>
        </div> <!-- Site Main Division -->
    </body>
</html>


