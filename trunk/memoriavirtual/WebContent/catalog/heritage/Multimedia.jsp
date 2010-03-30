<%-- 
    Document   : Authority
    Created on : 12.07.2009, 16:32:25
    Author     : Fabricio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>


<html>
    <head>
        <script language='javascript' type='text/javascript' src='${initParam.appPath}/catalog/heritage/common.js'></script>

        <templates:HeadDefault />
        <title>Memoria</title>
    </head>
    <body class="mainBody">
        <templates:BodyDefault mainHeader="Authoring"
                               topMenu="Authoring"
                               sidebar="${sidebar}"
                               footer="icmc usp">
            <s:form namespace="/heritage" action="SaveMultimediaData">
                <s:submit label="Insere Documento Multimídia"
                          name="acquisitionDocument"
                          value="Insere Documento Multimídia" />

                <s:textarea label="Documentos Multimídia Inseridos" name="addedAcquisitionDocuments" />



                <s:submit label="Apagar"
                          name="acquisitionDocument"
                          value="Apagar" />
                <s:submit key="Salvar" />
                <s:submit key="Salvar" />
            </s:form>
        </templates:BodyDefault>
    </body>
</html>
