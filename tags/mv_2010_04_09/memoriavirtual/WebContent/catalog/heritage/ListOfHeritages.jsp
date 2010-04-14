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

                            <s:form name="myForm" action="RenderHeritageTab" >

                                <s:hidden name="from" value="Outside" />


                                <s:select id= "listToSelectHeritage"
                                              name="selectedHeritage"
                                              list="listOfHeritages"
                                              listKey = "id"
                                              listValue = "title"
                                              size="4"
                                              multiple="false"
                                              label="Listagem de bens patrimoniais"
                                              cssStyle="height:150px;width:800px;" />

                                    <button id="newButton"
                                            type="submit"
                                            name="button"
                                            value="new">
                                        Novo Cadastro
                                    </button>

                                    <button id="editButton"
                                            type="submit"
                                            name="button"
                                            value="edit">
                                        Editar
                                    </button>


                                    <button id="removeButton"
                                            type="submit"
                                            name="button"
                                            value="remove">
                                        Excluir
                                    </button>

                            </s:form>
                        </div> <%-- boxsp --%>

            </div> <%-- groupsp --%>

            <div id="footersp">
                <%@include file="/Footer.jsp" %>
            </div>
        </div> <!-- Site Main Division -->
    </body>
</html>
