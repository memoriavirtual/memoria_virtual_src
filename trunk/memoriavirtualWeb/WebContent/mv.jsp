<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
    	               
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Memória Virtual</title>

<link href="css/mv.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.5.2.min.js"></script>
<script src="js/jquery.corner.js"></script>
<script src="js/abas.js"></script>
<script src="js/link.js"></script>
</head>
<body>
<f:view>
<div id="recipiente">
	
    <div id="cabecalho">
      	<img src="imagens/logo_mv_catalogacao.png">
        <div id="Abas">
        	<ul>
            	<li class="descritor"><a href="descritor.html" >Descritor<script>$('.descritor a').corner("top");</script></a></li>
                <li class="autoria"><a href="autoria.html" >Autoria<script>$('.autoria a').corner("top");</script></a></li>
                <li class="bempatrimonial"><a href="bempatrimonial.html" >Bem Patrimonial<script>$('.bempatrimonial a').corner("top");</script></a></li>
                <li class="usuario"><a href="usuario.html" >Usu&aacute;rio<script>$('.usuario a').corner("top");</script></a></li>
                <li class="instituicao"><a href="instituicao.html" >Institui&ccedil;&atilde;o<script>$('.instituicao a').corner("top");</script></a></li>
            </ul>
            
        </div><!-- fim #Abas -->
        <div id="usuario">&lt;
        <h:outputText value="#{realizarLoginMB.usuario}" />
        &gt;, <a href="#">sair</a></div>    	
    </div> <!-- fim #cabecalho -->
    <!--div style="background:#03F; width:100px; height:50px;" class="teste"><script>$('.teste').corner();</script></div-->
    
    <div id="conteudo">
		<div id="CaixaCorpo">
 		</div><!-- fim .CaixaCorpo -->
	</div><!-- fim #conteudo -->
	<div id="rodape">
    	Copyright &copy; 2009 LABES - Laborat&oacute;rio de Engenharia de Software - ICMC-USP
    </div> <!-- fim #rodape -->
</div><!-- fim #recipiente -->
</f:view>
</body>
</html>