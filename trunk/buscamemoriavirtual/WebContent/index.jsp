<%-- 
    Document   : index
    Created on : Mar 24, 2010, 12:42:03 AM
    Author     : paul
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Memoria Virtual</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="head">
  <div id="title"> <a href="index.jsp" >Memoria Virtual</a> |<i><font size="2">Contribuindo para o Acesso ao Patrimônio Histórico e Cultural.</font></i> </div>
      <div id="menu">
        <ul>
          <li class="active">
            <a href="http://memoriavirtual.icmc.usp.br/www/memoriavirtual/" title="portal" target="_blank">Portal</a>
          </li>
        </ul>
      </div>
</div>
<div id="body_wrapper">
  <div id="body">
    <div id="split">
      <div class="top"></div>
      <div id="left">
        <div class="content">
          <br /><br />
                        <fieldset>
                            <legend ><strong>Buscar Memoria Virtual</strong></legend>
                                <br /><br />
                                <br /><br />
                                <form action="jsp/displayResultList.jsp">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input style="WIDTH: 60%; HEIGHT: 22px" size=25 name=buscar value="" align="center">
                                <br /><br />
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="reset" value="Reset Pesquisa" name="novaPesquisa"src="" OnClick="javascript: history.back();" style="WIDTH: 15%; HEIGHT: 24px" size=25>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="submit" value="Pesquisa na Memorial Virtual" name="pesquisa" style="WIDTH: 28%; HEIGHT: 24px" size=25>
                                </form>
                                <br /><br />
                                <br /><br />
                        </fieldset>
           <br /><br />
        </div>
      </div>
	 <div class="clearer"></div>
    <div class="bottom"></div>
    </div>
    <div class="clearer"></div>
  </div>
  <div class="clearer"></div>
</div>
<div id="body_bottom"></div>
<div id="footer"> 
	<br />
	<p><a href="jsp/equipe.jsp">Sobre N&oacute;s</a> |&nbsp;<a href="jsp/sobreMV.jsp">O Projeto</a> |&nbsp;<a href="jsp/sobreMV.jsp">Contato </a>| <a href="mailto:kibe_paul@yahoo.com?subject=Memoria Virtual &body=Comentarios, Duvidas e Sugestoes!" >Copyright &copy; 2010 </a><a href="http://www.labes.icmc.usp.br/ ">LABES - Laborat&oacute;rio de Engenharia de&nbsp;&nbsp; Software</a> * <a href="http://www.icmc.usp.br/">ICMC-USP</a>.</p>
	<br />
</div>
<br /><br />
</body>
</html>
