<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">
    	               
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar usuario</title>
</head>
<body>
	<f:view>
		<h:form>
			<h:panelGrid>
				<h:outputText value="Email:" />
				<h:inputText value="#{cadastrarUsuarioMB.email}"  />

				<h:outputText value="Nome completo:" />
				<h:inputText value="#{cadastrarUsuarioMB.nomeCompleto}"  />

				<h:outputText value="Telefone:" />
				<h:inputText value="#{cadastrarUsuarioMB.telefone}"  />
				
 				<h:outputText value="Senha:" />
				<h:inputText value="#{cadastrarUsuarioMB.senha}"  />
				
				<h:outputText value="Confirmacao de senha:" />
				<h:inputText value="#{cadastrarUsuarioMB.confirmacaoSenha}"  />
				
				<h:inputHidden value="#{cadastrarUsuarioMB.validacao}" />
				
			</h:panelGrid>
			<h:commandButton action="#{cadastrarUsuarioMB.completarCadastro}" value="Cadastrar" />
			<h:messages globalOnly="true"/>
		</h:form>
	</f:view>
</body>
</html>