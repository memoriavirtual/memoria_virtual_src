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
                        <c:set var="currentTab" value="SubjectAndDescriptorsTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Assunto e Descritores</h1>

                        <div class="boxsp">

                                <s:form id="myForm" action="SaveSubjectAndDescriptorsTab" >

                                    <s:textfield label="Assunto"
                                                 name=""
                                                 id=""
                                                 />

                                    <s:select id="authorities"
                                              label="Selecione Descritor"
                                              name="authorities"
                                              multiple="false"
                                              list="{}"
                                              listKey="id"
                                              listValue="name"
                                              cssStyle="width:400px;"
                                              onchange="showAuthorityData ('authorities');" />

                                    <button id="transferButton"
                                            type="button"
                                            onclick="transferOptions ('researchdate', 'researchConducted');">Adicionar Descritor</button>

                                    <s:select id="researchConducted"
                                                label="Descritores do bem patrimonial"
                                                name="researchConducted"
                                                multiple="true"
                                                list="{}"
                                                cssStyle="height:100px;width:500px;"/>

                                    <button id="removeButton"
                                            type="button"
                                            onclick="transferOptions ('researchConducted', 'researchdate');">Remover Descritor</button>


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
