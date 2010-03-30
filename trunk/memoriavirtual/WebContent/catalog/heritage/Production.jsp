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
                        <c:set var="currentTab" value="ProductionTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Produção</h1>

                        <div class="boxsp">

                                <s:form id="myForm" action="SaveProductionTab" >
                                    
                                    <s:textfield label="Local" name="heritageLocal" />

                                    <s:textfield label="Data ou período de produção" name="heritageDate" />

                                    <s:textfield label="Edição" name="heritageEditionNumber" />

                                    <s:textfield label="Reimpressão" name="heritageReissueNumber" />

                                    <s:textfield label="Outras Responsabilidades" name="heritageOtherResponsibilities" />

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
