<%--
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Fabricio
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>

<html>
    <head>
        <script language='javascript' type='text/javascript' src='${initParam.appPath}/catalog/heritage/common.js'></script>

        <script type="text/javascript">

        </script>

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
                    <div id="sidebarsp">
                        <c:set var="currentTab" value="AcquisitionDocumentTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Documento de Aquisição</h1>

                        <div class="boxsp">

                                <s:form id="myForm" action="SaveAcquisitionDocumentTab" >

                                    <button type="button"
                                            title="Insere Documento de Aquisição"
                                            name="insertButton"
                                            value="Insere Documento de Aquisição" >Insere Documento de Aquisição</button>

                                    <p><br /></p>

                                    <s:textarea label="Documentos de Aquisição Inseridos" 
                                                name="addedAcquisitionDocuments"
                                                cssStyle="width:400px;"/>

                                    <p><br /></p>

                                    <button id="eraseButton"
                                            type="button"
                                            title="Apagar"
                                            name="eraseButton"
                                            value="Apagar" >Apagar</button>

                                    <button id="saveButton"
                                            type="button"
                                            name="saveButton"
                                            value="Salvar" >Salvar</button>

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
