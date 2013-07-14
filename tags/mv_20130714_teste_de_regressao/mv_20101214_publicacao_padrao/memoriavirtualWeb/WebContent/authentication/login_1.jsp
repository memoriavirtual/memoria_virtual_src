<%-- 
    Document   : login
    Created on : 30.06.2009, 13:06:53
    Author     : Fabricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>

<%@include file="/templates/Header.jsp" %>

<html>
    <head>
        <templates:HeadDefault />
        <title>${initParam.applicationName}</title>

        <script type="text/javascript" language="JavaScript">
        
        function setshw(par, lay, size) {
          var obj = document.getElementById(setshw.arguments[1]);
          if(navigator.appName.substring(0,9)=="Microsoft")
          {
            obj.style.left = event.x - event.offsetX + document.body.scrollLeft;
            obj.style.top  = event.y - event.offsetY + document.body.scrollTop + size;
          }
          else
          {
            obj.style.left = par.offsetLeft + "px";
            obj.style.top  = par.offsetTop + size + "px";
          }
          obj.style.display ='block';
        }
        function shwlay(lay)
        {
          var obj = document.getElementById(shwlay.arguments[0]);
          obj.style.display='block';
        }
        function hidlay(lay)
        {
          var obj = document.getElementById(hidlay.arguments[0]);
          obj.style.display='none';
        }
        
        </script>
    </head>

<body>
    <div id="sitemain"> <!-- Site main division -->
        <%@include file="/templates/logo.jsp" %>
                            
        <div id="groupsp">
            <div id="maincontentsp">

                    <h1>Login</h1>
                    <div class="boxsp">
                        <div class="box">
                            <s:form namespace="/authentication" action="EvalLogin">
                                <p><s:text name="login.message"/><br /><br /></p>
                                <s:textfield key="login.username" />
                                <br />
                                <s:password key="login.password" />
                                <br />
                          <%--      <s:select key="login.institution"
                                          list="listOfInstitutions"
                                          listKey="id"
                                          listValue="name"
                                          emptyOption="false" />--%>
                                <s:submit id="button"
                                          value="Entrar"/>
                            </s:form>



                        </div> <%-- box --%>
                    </div> <%-- boxsp --%>

            </div> <%-- maincontentsp --%>
        </div> <%-- groupsp --%>
        <div id="footersp">
                <a href="#">Sobre nós</a>&nbsp;|  <a href="#">O Projeto</a>&nbsp;|  <a href="#">Contato</a>&nbsp;| Copyright &copy; 2009 LABES - Laboratório de Engenharia de Software * ICMC-USP.
        </div>
    </div> <!-- Site Main Division -->
</body>

</html>
