<%--
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Elisa & Fabricio
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
                        <c:set var="currentTab" value="UseConditionTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Condições de Uso</h1>

                        <div class="boxsp">

                                <s:form id="myForm" namespace="/heritage" action="SaveUseConditionTab" >

                                <s:select id="accessConditions"
                                              label="Condições de acesso"
                                              name="accessConditions"
                                              multiple="false"
                                              list="#{0:'Livre',
                                                      1:'Sob Consulta',
                                                      2:'Não Acessível',
                                                      3:'Acesso à Cópia'}"
                                              cssStyle="width:400px;"
                                              onchange="showAuthorityData ('authorities');" />

                                <s:select id="reproductionConditions"
                                          label="Condições de reprodução"
                                          name="reproductionConditions"
                                          multiple="false"
                                          list="#{0:'Sim', 1:'Sob Consulta', 2:'Não'}"
                                          cssStyle="width:400px;"
                                          onchange="showAuthorityData ('authorities');" />

                                <s:textarea label="Uso e aproveitamento"
                                                 name="usage"
                                                 id="usage"
                                                 rows="3"
                                                 cols="60"
                                                 />

                                <s:select id="protection"
                                              label="Proteção"
                                              name="protection"
                                              multiple="false"
                                              list="#{0:'Sim', 1:'Em Processo', 2:'Não'}"
                                              cssStyle="width:400px;"
                                              onchange="showAuthorityData ('authorities');" />
                                
                                
                                <s:select label="Instituição"
                                          list="listOfInstitutions"
                                          listKey="id"
                                          listValue="name"
                                          name="institutionSelected"
                                          multiple="false"
                                          id="institutionSelected" />

                                <s:textfield label="Legislação"
                                                 name="legislation"
                                                 id="legislation"
                                                 />

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
