<%--
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Fabricio
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>

<c:set var="topMenu">
    <%@include file="/catalog/TopMenu.jsp" %>
</c:set>

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
                        <c:set var="currentTab" value="DescriptionTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Descrição</h1>

                        <div class="boxsp">

                                <s:form id="myForm" action="SaveDescriptionTab" >

                                    <s:textfield label="Conteúdo" name="heritageContent" />

                                    <s:textfield label="Notas" name="heritageNote" />

                                    <s:textfield label="Dimensões" name="heritageDimensions" />

                                    <s:textfield label="Características Físicas" name="heritagePhysicalFeatures" />

                                    <s:textfield label="Suporte de Auxílio para Uso" name="heritageSupport" />

                                    <s:textfield label="Estado de Conservação" name="heritageCondition" />

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
