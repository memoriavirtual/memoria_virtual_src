<%-- 
    Document   : DescriptorMainPage
    Created on : 10/01/2010, 18:41:13
    Author     : Elisa
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

                            <s:form id="myForm" namespace="descriptor" action="RenderDescriptorPage" >

                                <s:hidden name="from" value="DescriptorMainPage" />

                                <s:select id= "listOfDescriptors"
                                              name="descriptorSelected"
                                              list="listOfDescriptors"
                                              listKey="id"
                                              listValue="descriptor"
                                              size="4"
                                              label="Listagem de descritores"
                                              cssStyle="height:150px;width:800px;"
                                              onchange="optionToTextFields ('listOfInterventions', 'interventionData')"/>

                                <button name="newButton"
                                        type="submit">Novo Cadastro</button>

                                <button name="editButton"
                                        type="submit">Editar</button>

                                <button name="removeButton"
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




