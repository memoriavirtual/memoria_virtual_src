<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GlassFish JSP Page</title>
</head>
<body>
	<f:view>
		<h:form>
			<ul>
				<li><h:outputText value="Login:" /> <h:inputText
						value="#{realizarLoginMB.usuario}" required="true"
						requiredMessage="Campo obrigatorio" />
				</li>
				<li><h:outputText value="Senha:" /> <h:inputText
						value="#{realizarLoginMB.senha}" />
				</li>
			</ul>
			<h:commandButton action="#{realizarLoginMB.autenticarUsuario}"
				value="Logar" />
		</h:form>
	</f:view>
</body>
</html>
