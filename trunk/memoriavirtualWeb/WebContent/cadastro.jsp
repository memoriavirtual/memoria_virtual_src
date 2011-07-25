<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
    	               
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<f:view>
		<h:form>
			<h:panelGrid>
				<h:messages globalOnly="true"/>
				<h:outputText value="Email:" />
				<h:inputText value="#{cadastrarUsuarioMB.email}" required="true" />

 				<h:outputText value="Nome completo:" />
				<h:inputText value="#{cadastrarUsuarioMB.senha}" required="true" />

			</h:panelGrid>
			<h:commandButton action="#{cadastrarUsuarioMB.completarCadastro}" value="Cadastrar" />
		</h:form>
	</f:view>
</body>
</html>