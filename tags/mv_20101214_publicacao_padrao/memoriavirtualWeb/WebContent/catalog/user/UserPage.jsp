<%-- 
    Document   : AddUser
    Created on : 10/01/2010, 20:49:14
    Author     : Elisa
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

                <div id="maincontentsp" style="margin-left:0px;">

                    <h1>Usuário > Novo Cadastro </h1>

                    <div class="boxsp">

                        <s:form id="myForm" namespace="/user" action="SaveUserPage" >
                            
                                <s:textfield label="Número de identificação"
                                                 name="userIdentificationNumber"
                                                 id="UserIdentificationNumber"
                                                 cssStyle="width:200px;"
                                                 />

                                <s:textfield label="Nome"
                                                 name="name"
                                                 id="name"
                                                 cssStyle="width:500px"
                                                 />

                                <s:textarea label="Endereço"
                                                 name="address"
                                                 id="address"
                                                 rows="2"
                                                 cols="60"
                                                 />

                                <s:textfield label="E-mail"
                                                 name="email"
                                                 id="email"
                                                 cssStyle="width:300px;"
                                                 />

                                <s:textfield label="Telefone (DDD) + Número"
                                                 name="telephone"
                                                 id="telephone"
                                                 cssStyle="width:200px;"
                                                 />

                                

                                <s:textfield label="Data de cadastro (XX/XX/XXXX)"
                                                 name="registryDate"
                                                 id="registryDate"
                                                 cssStyle="width:150px;"
                                                 />

                                <s:textfield label="Data da exclusão (XX/XX/XXXX)"
                                                 name="dismissDate"
                                                 id="dismissDate"
                                                 cssStyle="width:150px;"
                                                 />

                                <s:textfield label="Login"
                                                 name="login"
                                                 id="login"
                                                 cssStyle="width:150px;"
                                                 />

                                <s:textfield label="Senha"
                                                 name="password"
                                                 id="password"
                                                 cssStyle="width:150px;"
                                                 />

                                <s:select label="Tipo do usuário"
                                              name="userType"
                                              cssStyle="width:150px;"
                                              list="%{#{0:'Catalogador',
                                                        1:'Revisor',
                                                        2:'Gerente'}}"
                                              />

                                <s:checkbox label="Situação"
                                            name="active"
                                            cssStyle="width:150px;" />

                                <s:select label="Institution"
                                          name="institutionSelected"
                                          list="institutions"
                                          listKey="id"
                                          listValue="name"
                                          cssStyle="width:150px;" />



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