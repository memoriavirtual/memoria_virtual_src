<%--
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Elisa & Fabricio
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

                    <ul>
                        <li><a href="index.html" class="first">Principal</a></li>
                        <li><a href="#" onMouseOver="setshw(this,'topmenusub1',35);" onMouseOut="hidlay('topmenusub1');">Instituição</a></li>
                        <li><a href="#" onMouseOver="setshw(this,'topmenusub2',35);" onMouseOut="hidlay('topmenusub2');">Usuário</a></li>
                        <li><a href="#" onMouseOver="setshw(this,'topmenusub3',35);" onMouseOut="hidlay('topmenusub3');">Bem Patrimonial</a></li>
                        <li><a href="#" onMouseOver="setshw(this,'topmenusub4',35);" onMouseOut="hidlay('topmenusub4');">Autoria</a></li>
                        <li><a href="#" onMouseOver="setshw(this,'topmenusub5',35);" onMouseOut="hidlay('topmenusub5');">Descritores</a></li>
                        <li><a href="logout.html">Sair</a></li>
                    </ul>

                    <div id="topmenusub1" class="SubMenu" onMouseOver="shwlay('topmenusub1');" onMouseOut="hidlay('topmenusub1');">
                        <ul>
                            <li><a href="cadastro_instit.html">Cadastrar</a></li>
                            <li><a href="busca_instit.html">Buscar</a></li>
                        </ul>
                    </div>

                    <div id="topmenusub2" class="SubMenu" onMouseOver="shwlay('topmenusub2');" onMouseOut="hidlay('topmenusub2');">
                        <ul>
                            <li><a href="usu_cadastro.html">Cadastro</a></li>
                            <li><a href="usu_perm.html">Permissões</a></li>
                            <li><a href="usu_busca.html">Buscar</a></li>
                        </ul>
                    </div>

                    <div id="topmenusub3" class="SubMenu" onMouseOver="shwlay('topmenusub3');" onMouseOut="hidlay('topmenusub3');">
                        <ul>
                            <li><a href="patr_cad.html">Cadastro</a></li>
                            <li><a href="patr_busca.html">Buscar</a></li>
                        </ul>
                    </div>

                    <div id="topmenusub4" class="SubMenu" onMouseOver="shwlay('topmenusub4');" onMouseOut="hidlay('topmenusub4');">
                        <ul>
                            <li><a href="aut_cad.html">Cadastro</a></li>
                            <li><a href="aut_busca.html">Buscar</a></li>
                        </ul>
                    </div>

                    <div id="topmenusub5" class="SubMenu" onMouseOver="shwlay('topmenusub5');" onMouseOut="hidlay('topmenusub5');">
                        <ul>
                            <li><a href="desc_cad.html">Cadastro</a></li>
                            <li><a href="desc_busca.html">Buscar</a></li>
                        </ul>
                    </div>


            </div> <%-- topmenusp --%>

            <div id="groupsp">
                    <div id="sidebarsp">
                        <c:set var="currentTab" value="HistoryAndOriginTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Histórico e Procedência</h1>

                        <div class="boxsp">

                                <s:form id="myForm"
                                        namespace="/heritage/"
                                        action="SaveHistoryAndOriginTab" >

                                <s:select id="authorities"
                                              label="Selecione tipo de aquisição"
                                              name="acquisitionType"
                                              multiple="false"
                                              list="#{0:'Compra',
                                                      1:'Permuta',
                                                      2:'Doação',
                                                      3:'Comodato',
                                                      4:'Posse'}"
                                              cssStyle="width:400px;"
                                              onchange="showAuthorityData ('authorities');" />

                                <s:textfield label="Procedência da aquisição"
                                                 name="acquisitionOrigin"
                                                 id="acquisitionOrigin"
                                                 />

                                <s:textfield label="Valor da aquisição"
                                                 name="acquisitionValue"
                                                 id="acquisitionValue"
                                                 />

                                <s:textfield label="Data da aquisição"
                                                 name="acquisitionDate"
                                                 id="acquisitionDate"
                                                 />

                                <s:textfield label="Proprietário atual"
                                                 name="acquisitionCurrentOwner"
                                                 id="acquisitionCurrentOwner"
                                                 />

                                <s:textarea label="Histórico"
                                                 name="historic"
                                                 id="historic"
                                                 rows="3"
                                                 cols="60"
                                                 />

                                <s:textfield label="Instrumento de pesquisa"
                                                 name="findingAid"
                                                 id="findingAid"
                                                 />

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
