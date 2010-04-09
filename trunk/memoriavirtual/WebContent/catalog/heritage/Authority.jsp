<%--
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Fabricio
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>

<%@taglib prefix="sx" uri="/struts-dojo-tags" %>

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

                        var opt = document.createElement ("OPTION");
                        opt.text = sourceSelect.options [i].text;
                        opt.value = sourceSelect.options [i].value;

                        sourceSelect.remove (i);

                        destinationSelect.options.add (opt);
                    }
                }
            }

            function showAuthorityData (sourceName) {

                var source = document.getElementById (sourceName);

                var id = 0;

                var found = false;

                for (var i = 0; i < source.options.length; i++) {

                    if (source.options [i].selected == true) {
                        id = source.options [i].value;
                        found = true;
                        break;
                    }
                }

                if (found == false) return;

                var nameRef = document.getElementById("authorName");
                var nicknameRef=document.getElementById("nickName");
                var birthDateRef=document.getElementById ("birthDate");
                var deathDateRef=document.getElementById ("deathDate");

                nameRef.value = document.getElementById("Authority" + id + "Name").innerHTML;
                nicknameRef.value = document.getElementById("Authority" + id + "NickName").innerHTML;
                birthDateRef.value = document.getElementById("Authority" + id + "BirthDate").innerHTML;
                deathDateRef.value = document.getElementById("Authority" + id + "deathDate").innerHTML;
            }



            function finish () {
                selectAllOptions('authoritiesSelected');
                selectAllOptions('coAuthoritiesSelected');
            }

    function selectAllOptions (selectId) {
        var source = document.getElementById (selectId);

        for (var i = 0; i < source.options.length; i++) {
            source.options [i].selected = true;
        }

    }
        </script>

        <templates:HeadDefault />
        <title>${initParam.applicationName}</title>
    </head>

    <body onload="showAuthorityData ('authorities');">
        <div id="sitemain"> <!-- Site main division -->

            <%@include file="/templates/logo.jsp" %>



             <div id="topmenusp">
                <%@include file="/catalog/TopMenu.jsp" %>
            </div> <%-- topmenusp --%>




            <div id="groupsp">
                    <div id="sidebarsp" onmouseover="finish();">
                        <c:set var="currentTab" value="AuthorityTab"/>

                        <%@include file="SideMenu.jsp" %>
                    </div>

                    <div id="maincontentsp" style="margin-left:240px;">
                        <h1>Bem Patrimonial > Autoria</h1>

                        <div class="boxsp">


                            <div class="hiddenData">
                                <s:iterator value="authorities">
                                    <ol id="Authority<s:property value='id' />" >
                                        <li id="Authority<s:property value='id' />Name"><s:property value="name" /></li>
                                        <li id="Authority<s:property value='id' />NickName"><s:property value="nickname" /></li>
                                        <li id="Authority<s:property value='id' />BirthDate"><s:property value="birthDate" /></li>
                                        <li id="Authority<s:property value='id' />DeathDate"><s:property value="deathDate" /></li>
                                    </ol>
                                </s:iterator>
                            </div>


                            <s:form id="myForm"
                                    namespace="/heritage"
                                    action="SaveAuthorityTab" 
                                    onsubmit="">


                                    <s:select id="authorities"
                                              label="Selecione Autor"
                                              name="authorities"
                                              multiple="false"
                                              list="authorities"
                                              listKey="id"
                                              listValue="name"
                                              cssStyle="width:400px;"
                                              onchange="showAuthorityData ('authorities');" />


                                    <s:div cssStyle="float:left;" >
                                        <s:textfield label="Nome do Autor"
                                                     name="authorName"
                                                     id="authorName"
                                                     disabled="true"
                                                     />

                                        <s:textfield label="Apelido"
                                                     name="nickName"
                                                     id="nickName"
                                                     disabled="true" />
                                    </s:div>

                                    <s:div >

                                        <s:textfield label="Data de Nascimento"
                                                     name="birthDate"
                                                     id="birthDate"
                                                     disabled="true" />

                                        <s:textfield label="Data de Morte"
                                                     name="deathDate"
                                                     id="deathDate"
                                                     disabled="true" />

                                    </s:div>
                                    <button id="transferButton"
                                            type="button"
                                            onclick="transferOptions ('authorities', 'authoritiesSelected');
                                            showAuthorityData ('authorities');">Adicionar Autor</button>

                                    <button id="CoAuthorityTransferButton"
                                            type="button"
                                            onclick="transferOptions ('authorities', 'coAuthoritiesSelected');
                                            showAuthorityData ('authorities');">Adicionar Co-Autor</button>

                                    <p><br/></p>

                                    <s:select id="authoritiesSelected"
                                                label="Autores Selecionados"
                                                name="authoritiesSelected"
                                                multiple="true"
                                                list="authoritiesSelected"
                                                listKey="id"
                                                listValue="name"
                                                cssStyle="width:400px;"/>

                                    <button id="transferButton"
                                            type="button"
                                            onclick="transferOptions ('authoritiesSelected', 'authorities');
                                            showAuthorityData ('authorities');">Remover Autor</button>

                                    <p><br/></p>
                                    
                                    <s:select id="coAuthoritiesSelected"
                                              label="Co-Autores Selecionados"
                                              name="coAuthoritiesSelected"
                                              multiple="true"
                                              list="coAuthoritiesSelected"
                                              listKey="id"
                                              listValue="name"
                                              cssStyle="width:400px;"/>

                                    <button id="CoAuthorityTransferButton"
                                            type="button"
                                            onclick="transferOptions ('coAuthoritiesSelected', 'authorities');
                                            showAuthorityData ('authorities');">Remover Co-Autor</button>

                                    <br />

                            </s:form>

                        </div> <%-- boxsp --%>
                    </div> <%-- maincontentsp --%>
            </div> <%-- groupsp --%>

            <div id="footersp">

                <%@include file="/Footer.jsp" %>

            </div>
        </div> <!-- Site Main Division -->
    </body>
</html>
