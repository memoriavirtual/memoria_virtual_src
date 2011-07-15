<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Eexpires" content="0">
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Memória Virtual</title>
<link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
	<f:view>
		<div id="container">

			<div id="conteudo">
				<h:messages globalOnly="false" />
				<h:outputText value="Usuário" />
				<h:form>
					<div>
						<h:inputText value="#{realizarLoginMB.usuario}" required="true" />
					</div>
					<h:outputText value="Senha" />
					<div>
						<h:inputSecret value="#{realizarLoginMB.senha}" />
					</div>
					<br>
					<div>
						<h:commandButton action="#{realizarLoginMB.autenticarUsuario}"
							value="Entrar" />
					</div>
				</h:form>
			</div>
			<!--end #conteudo-->
		</div>
		<!-- end #container-->
	</f:view>
</body>
</html>
