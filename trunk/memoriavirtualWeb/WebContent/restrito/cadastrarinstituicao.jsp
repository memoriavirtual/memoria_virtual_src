<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script src="../js/jquery.js" type="text/javascript"></script>
<script src="../js/mask.js" type="text/javascript"></script>
<script src="../js/maskedinput.js" type="text/javascript"></script>
<link 	href="../css/conteudo.css" rel="stylesheet" type="text/css" >

</head>
<body>
	<f:view>	
	<div id="formu">
		<div id="titulo">
			<h2>Cadatrar Instituição</h2>
			<h3>Preencha o formulario abaixo e clique em salvar para cadastrar uma nova insituição</h3>
		</div>
		<div id="informu">
 		<h:messages globalOnly="true"/>
 		<h:form>
 		
 			<label>Nome* </label><h:inputText id="nome" value="#{cadastrarInstituicaoMB.nome}" required="true"/><br/>
 			<label>Localização Geográfica* </label><h:inputText id="localizacao" value="#{cadastrarInstituicaoMB.localizacao}" required="true"/><br/>
 			<label>Endereço </label><h:inputText id="endereco" value="#{cadastrarInstituicaoMB.endereco}"/><br/>
 			<label>Cidade </label><h:inputText id="cidade" value="#{cadastrarInstituicaoMB.cidade}"/><br/>
 			<label>Estado </label><h:selectOneMenu id="estado" value="#{cadastrarInstituicaoMB.estado}"> <f:selectItem itemValue ="" itemLabel="Escolha"/> <f:selectItems value="#{cadastrarInstituicaoMB.estados}" /></h:selectOneMenu><br/>
 			<label>CEP </label><h:inputText id="cep" value="#{cadastrarInstituicaoMB.cep}"></h:inputText><br/>
 			<label>Telefone </label><h:inputText id="telefone" value="#{cadastrarInstituicaoMB.telefone}"/><br/>
 			<label>Os campos marcados com asterisco (*) são de preenchimento obrigatório</label><br/>
 			<h:commandButton id="salvar" value="Salvar" action="#{cadastrarInstituicaoMB.CadastrarInstituicao}" />
 			<h:commandButton id="cancelar" value="Cancelar"/><br/>
 			
 			
 		</h:form>
 		</div>
 	</div>
 	</f:view>
 			
 			
</body>
</html>