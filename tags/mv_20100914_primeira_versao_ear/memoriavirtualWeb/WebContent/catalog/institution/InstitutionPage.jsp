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
                  
                <div id="maincontentsp" style="margin-left:0px;">

                    <h1>Instituição > Novo Cadastro </h1>

                    <div class="boxsp">

                        <s:form id="myForm" namespace="/institution" action="SaveInstitutionPage" >
                            
                            <s:textfield label="Nome" name="name" />
                            <s:textfield label="Sigla" name="acronym" />
                            <s:textfield label="Unidade" name="unit"/>
                            <s:textfield label="País" name="country" />
                            <s:textfield label="Endereço" name="address" />
                            <s:textfield label="CEP" name="postalCode" />
                            <s:textfield label="Cidade" name="city" />
                            <s:textfield label="Caixa Postal" name="postOfficeBox" />
                            <s:textfield label="Estado" name="state"/>
                            <br />
                            <p>
                            <s:textfield label="Telefone" name="telephone" />
                            <a href="<s:url namespace="/institution" action="SaveAddInstitution" /> ">Adiciona Telefone</a>
                            </p>
                            <br />
                            <br />
                            <s:textfield label="Fax" name="fax" />
                            <s:textfield label="Localidade" name="locality" />
                            <s:textfield label="E-Mail" name="email" />
                            <s:textfield label="Responsável" name="unitResponsible" />
                            <s:textfield label="Cargo do Responsável" name="unitResponsibleFunction" />
                            <s:textfield label="URL" name="url"/>
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