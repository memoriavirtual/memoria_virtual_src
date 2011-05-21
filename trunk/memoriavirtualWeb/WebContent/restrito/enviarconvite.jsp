<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Enviar Convite</title>
</head>
<body>
	<f:view>
		<h:form>
			<h:panelGrid>
				<h:outputText value="#{enviarConviteMB.erro}" />
				<h:outputText value="Email(s):" />
				<h:inputTextarea id="emails" rows="4" cols="40" value="#{enviarConviteMB.emails}" />

 				<h:outputText value="Prazo de validade:" />
				<h:selectOneMenu value="#{enviarConviteMB.validade}">
					<f:selectItem itemValue="1" itemLabel="1" />
					<f:selectItem itemValue="2" itemLabel="2" />
					<f:selectItem itemValue="3" itemLabel="3" />
					<f:selectItem itemValue="4" itemLabel="4" />
					<f:selectItem itemValue="5" itemLabel="5" />
					<f:selectItem itemValue="6" itemLabel="6" />
					<f:selectItem itemValue="7" itemLabel="7" />
					<f:selectItem itemValue="8" itemLabel="8" />
					<f:selectItem itemValue="9" itemLabel="9" />
					<f:selectItem itemValue="10" itemLabel="10" />
					<f:selectItem itemValue="11" itemLabel="11" />
					<f:selectItem itemValue="12" itemLabel="12" />
					<f:selectItem itemValue="13" itemLabel="13" />
					<f:selectItem itemValue="14" itemLabel="14" />
					<f:selectItem itemValue="15" itemLabel="15" />
					<f:selectItem itemValue="16" itemLabel="16" />
					<f:selectItem itemValue="17" itemLabel="17" />
					<f:selectItem itemValue="18" itemLabel="18" />
					<f:selectItem itemValue="19" itemLabel="19" />
					<f:selectItem itemValue="20" itemLabel="20" />
					<f:selectItem itemValue="21" itemLabel="21" />
					<f:selectItem itemValue="22" itemLabel="22" />
					<f:selectItem itemValue="23" itemLabel="23" />
					<f:selectItem itemValue="24" itemLabel="24" />
					<f:selectItem itemValue="25" itemLabel="25" />
					<f:selectItem itemValue="26" itemLabel="26" />
					<f:selectItem itemValue="27" itemLabel="27" />
					<f:selectItem itemValue="28" itemLabel="28" />
					<f:selectItem itemValue="29" itemLabel="29" />
					<f:selectItem itemValue="30" itemLabel="30" />
				</h:selectOneMenu>

				<h:outputText value="Instituição:" />
				<h:selectOneMenu value="#{enviarConviteMB.instituicao}">
					<f:selectItems value="#{enviarConviteMB.instituicoesPermitidas}" />
					
				</h:selectOneMenu>
  				<h:outputText value="Nível de Acesso:" />
				<h:selectOneMenu value="#{enviarConviteMB.nivelAcesso}" id="niveisAcesso">
					<f:selectItems value="#{enviarConviteMB.niveisPermitidos}" />
				</h:selectOneMenu>
				
				<h:outputText value="Mensagem personalizada:" />
				<h:inputTextarea id="mensagem" rows="4" cols="40" value="#{enviarConviteMB.mensagem}" />

			</h:panelGrid>
			<h:commandButton action="#{enviarConviteMB.enviarConvite}" value="Enviar" />
		</h:form>
	</f:view>
</body>
</html>
