<%--
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Elisa & Fabricio
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
                        <c:set var="currentTab" value="HistoryAndOriginTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Histórico e Procedência</h1>

                        <div class="boxsp">

                                <s:form id="myForm"
                                        namespace="/heritage/"
                                        action="SaveHistoryAndOriginTab" >

                                <s:select id="authorities"
                                              label="Selecione tipo de aquisição"
                                              name="acquisitionType"
                                              multiple="false"
                                              list="#{0:'Compra',
                                                      1:'Permuta',
                                                      2:'Doação',
                                                      3:'Comodato',
                                                      4:'Posse'}"
                                              cssStyle="width:400px;"
                                              onchange="showAuthorityData ('authorities');" />

                                <s:textfield label="Procedência da aquisição"
                                                 name="acquisitionOrigin"
                                                 id="acquisitionOrigin"
                                                 />

                                <s:textfield label="Valor da aquisição"
                                                 name="acquisitionValue"
                                                 id="acquisitionValue"
                                                 />

                                <s:textfield label="Data da aquisição"
                                                 name="acquisitionDate"
                                                 id="acquisitionDate"
                                                 />

                                <s:textfield label="Proprietário atual"
                                                 name="acquisitionCurrentOwner"
                                                 id="acquisitionCurrentOwner"
                                                 />

                                <s:textarea label="Histórico"
                                                 name="historic"
                                                 id="historic"
                                                 rows="3"
                                                 cols="60"
                                                 />

                                <s:textfield label="Instrumento de pesquisa"
                                                 name="findingAid"
                                                 id="findingAid"
                                                 />

                                    <br />
                                    <s:submit name="Salvar" value="Salvar" />

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
