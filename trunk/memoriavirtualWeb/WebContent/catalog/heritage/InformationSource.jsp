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
                        <c:set var="currentTab" value="InformationSourceTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Fonte de Informação</h1>

                        <div class="boxsp">

                                <s:form id="myForm" 
                                        namespace="/heritage"
                                        action="SaveInformationSourceTab" >

                                    <s:select id="informationSource"
                                              label="Selecione Fonte"
                                              name="informationSource"
                                              multiple="false"
                                              list="{}"
                                              listKey="id"
                                              listValue="name"
                                              cssStyle="width:400px;"
                                              onchange="showAuthorityData ('authorities');" />

                                    <p><br/></p>

                                    <s:textarea rows="5"
                                                cols="60"
                                                label="Bibliografia"
                                                 name=""
                                                 id=""
                                                 />

                                    <s:textfield label="Tipo da bibliografia"
                                                 name=""
                                                 id=""
                                                 />

                                    <button id="transferButton"
                                            type="button"
                                            onclick="transferOptions ('researchdate', 'researchConducted');">Adicionar Fonte</button>

                                    <p><br/></p>

                                    <s:select id="researchConducted"
                                                label="Fontes de informação"
                                                name="researchConducted"
                                                multiple="true"
                                                list="{}"
                                                cssStyle="width:500px;"/>

                                   <button id="editButton"
                                            type="button"
                                            onclick="transferOptions ('researchConducted', 'researchdate');">Editar Fonte</button>

                                    <button id="removeButton"
                                            type="button"
                                            onclick="transferOptions ('researchConducted', 'researchdate');">Remover Fonte</button>


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
