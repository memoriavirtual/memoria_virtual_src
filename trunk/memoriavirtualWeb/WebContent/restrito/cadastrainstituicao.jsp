<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="../js/jquery-1.6.2.js" type="text/javascript"></script>
<script src="../js/mask.js" type="text/javascript"></script>
<script src="../js/maskedinput-1.3.js" type="text/javascript"></script>
<script src="../js/back.js" type="text/javascript"></script>

</head>
<body>
 	<h:messages globalOnly="true"/>
 	<f:view>
 		<h:form>
 		
 			Nome <h:inputText id="nome" value="#{cadastraInstituicaoMB.nome}" required="true"/>
 			Localização <h:inputText id="localizacao" value="#{cadastraInstituicaoMB.localizacao}" required="true"/>
 			Endereço <h:inputText id="endereco" value="#{cadastraInstituicaoMB.endereco}"/>
 			Cidade <h:inputText id="cidade" value="#{cadastraInstituicaoMB.cidade}"/>
 			Estado <h:inputText id="estado" value="#{cadastraInstituicaoMB.estado}"/>
 			CEP <h:inputText id="cep" value="#{cadastraInstituicaoMB.cep}"/>
 			Telefone <h:inputText id="telefone" value="#{cadastraInstituicaoMB.telefone}"/>
 			<h:commandButton id="salvar" value="Salvar" action="#{cadastraInstituicao.efetuaCadastro}" />
 			<h:commandButton id="cancelar" value="Cancelar" onclick="goBack()"/>
 			
 			
 		</h:form>
 	</f:view>
 			
 			
</body>
</html>