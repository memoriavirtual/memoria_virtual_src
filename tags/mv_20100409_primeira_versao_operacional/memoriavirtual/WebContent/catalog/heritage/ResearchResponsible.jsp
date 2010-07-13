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


            function transferOptions (sourceSelectName, destinationSelectName)
            {
                var sourceSelect = document.getElementById (sourceSelectName);
                var destinationSelect = document.getElementById (destinationSelectName);

                for (var i = 0; i < sourceSelect.options.length; i++) {

                    if (sourceSelect.options [i].selected == true) {
                        destinationSelect.add (sourceSelect.options [i]);
                    }
                }
            }
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
                        <c:set var="currentTab" value="ResearchResponsibleTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Responsável pela Pesquisa</h1>

                        <div class="boxsp">

                                <s:form id="myForm"
                                        namespace="/heritage"
                                        action="SaveResearchResponsibleTab" >

                                    <s:textfield label="Dada da pesquisa"   
                                                 name="researchDate"
                                                 id="researchDate"
                                                 />

                                    <s:textfield label="Nome do pesquisador" 
                                                 name="responsibleName"
                                                 cssStyle="width:400px;"
                                                 id="responsibleName"
                                                 />

                                    <s:textarea label="Notas do Pesquisador"
                                                 name="researcherNotes"
                                                 id="researcherNotes"
                                                 cssStyle="width:400px; height:100px;"
                                                 />

                                    <button id="transferButton"
                                            type="button"
                                            onclick="transferOptions ('researchdate', 'researchConducted');">Adicionar Pesquisa</button>

                                    <p><br/></p>

                                    <s:select id="researchConducted"
                                                label="Pesquisas Conduzidas"
                                                name="researchConducted"
                                                multiple="true"
                                                list="{}"
                                                cssStyle="width:400px; height: 100px;"/>

                                   <button id="editButton"
                                            type="button"
                                            onclick="transferOptions ('researchConducted', 'researchdate');">Editar Pesquisa</button>

                                    <button id="removeButton"
                                            type="button"
                                            onclick="transferOptions ('researchConducted', 'researchdate');">Remover Pesquisa</button>


                                    <br />


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
