<%-- 
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Fabricio Zancanella and Elisa Yumi Nakagawa
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>

<html>
    <head>
        <script language='javascript' type='text/javascript' src='${initParam.appPath}/catalog/heritage/common.js' ></script>

        <templates:HeadDefault />

        <sx:head debug="true" cache="false" compressed="false" />

        
        <title>${initParam.applicationName}</title>
    </head>
            

    <body>
        <div id="sitemain"> <!-- Site main division -->

            <%@include file="/templates/logo.jsp" %>

            <div id="topmenusp">                
                <%@include file="/catalog/TopMenu.jsp" %>
            </div> <%-- topmenusp --%>

            <div id="groupsp">
                <div id="group">

                    <div id="sidebarsp">
                        <c:set var="currentTab" value="HeritageTab"/>
                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Informações Gerais</h1>

                        <div class="boxsp">
                            <div class="box">
                                <s:form action="SaveHeritageTab" id="myForm" name="myForm">
                                    
                                    <s:select label="Tipo do bem"
                                              name="selectedHeritageTypes"
                                              multiple="true"
                                              cssStyle="width:200px;"
                                              list="heritageTypes"></s:select>

                                    <s:textfield label="Número de registro"
                                                 name="heritageControlNumber"
                                                 cssStyle="width:200px;"
                                                 />

                                    <s:textfield label="Título"
                                                 name="heritageTitle"
                                                 cssStyle="width:500px;"
                                                 />

                                    <s:textfield label="Título original"
                                                 name="heritageOriginalTitle"
                                                 cssStyle="width:500px;"
                                                 />

                                    <s:textfield label="Título alternativo"
                                                 name="heritageAlternativeTitle"
                                                 cssStyle="width:500px;"
                                                 />

                                    <s:textfield label="Acervo"
                                                 name="heritageCollection"
                                                 cssStyle="width:500px;"
                                                 />

                                    <s:textfield label="Localização física"
                                                 name="heritagePhysicalLocation"
                                                 cssStyle="width:500px;"
                                                 />

                                    <s:textfield label="Complemento"
                                                 name="heritageComplementTitle"
                                                 cssStyle="width:500px;"
                                                 />

                                    <s:select label="Situação"
                                              name="heritageSituation"
                                              list="situations"
                                              multiple="false"
                                              headerKey="NONE"
                                              headerValue="---Selecione---"
                                              cssStyle="width:200px;">
                                    </s:select>


                       <%--             <button id="save"
                                            type="submit"
                                            name="button"
                                            value="save">
                                        Save Heritage
                                    </button>--%>
                                 </s:form>
                             
                            </div> <%-- box --%>
                        </div> <%-- boxsp --%>
                    </div> <%-- maincontentsp --%>

                </div> <%-- group --%>
            </div> <%-- groupsp --%>

            <div id="footersp">

                <%@include file="/Footer.jsp" %>

            </div>
        </div> <!-- Site Main Division -->
    </body>
</html>
