<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logoff</title>
</head>
<body>
<f:view>
	<div id="conteudo">
		<h:commandButton action="#{realizarLogoffMB.realizarLogoff}" value="Logoff" />	
	<!--end #conteudo--></div>
	<!-- end #container-->
</f:view>
</body>
</html>