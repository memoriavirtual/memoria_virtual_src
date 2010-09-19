<%-- 
    Document   : HeritageMenu
    Created on : 16.07.2009, 21:25:13
    Author     : Fabricio Zancanella
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>

<script type="text/javascript">
    function insertHidden (parentForm, idName, hiddenName, hiddenValue)
    {

        var child = document.createElement ('input');
        var existingId = document.getElementById('to');

        if (existingId != null) {
            existingId.parentNode.removeChild (existingId);
        }
        child.setAttribute ('id',idName);
        child.setAttribute ('type', 'hidden');
        child.setAttribute ('name', hiddenName);
        child.setAttribute ('value', hiddenValue);
        document.getElementById (parentForm).appendChild (child);
        document.myForm.submit();

    }
</script>

<ul>
    <%-- HeritageTab Link --%>
        <li <c:if test="${currentTab eq 'HeritageTab'}" >id="itemSelected"</c:if>>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'HeritageTab');">Informações Gerais</a>
        </li>

    <%-- AuthorityTab Link --%>
        <li <c:if test="${currentTab eq 'AuthorityTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'AuthorityTab');">Autoria</a>
        </li>

    <%-- ProductionTab Link --%>

        <li <c:if test="${currentTab eq 'ProductionTab'}" >id="itemSelected"</c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'ProductionTab');">Produção</a>
        </li>

    <%-- DescriptionTab Link --%>


        <li <c:if test="${currentTab eq 'DescriptionTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'DescriptionTab');">Descrição</a>
        </li>

    <%-- InterventionTab Link --%>

        <li <c:if test="${currentTab eq 'InterventionTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'InterventionTab');">Intervenção</a>
        </li>

    <%-- UseConditionTab Link --%>

        <li <c:if test="${currentTab eq 'UseConditionTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'UseConditionTab');">Condições de Uso</a>
        </li>

    <%-- HistoryAndOriginTab Link --%>

        <li <c:if test="${currentTab eq 'HistoryAndOriginTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'HistoryAndOriginTab');">Histórico e Procedência</a>
        </li>

    <%-- AcquisitionDocumentTab Link --%>

        <li <c:if test="${currentTab eq 'AcquisitionDocumentTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'AcquisitionDocumentTab');">Documento de Aquisição</a>
        </li>

    <%-- SubjectAndDescriptorsTab Link --%>

        <li <c:if test="${currentTab eq 'SubjectAndDescriptorsTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'SubjectAndDescriptorsTab');">Assunto e Descritores</a>
        </li>

    <%-- MultimediaTab Link --%>

        <li <c:if test="${currentTab eq 'MultimediaTab'}" >id="itemSelected"></c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'MultimediaTab');">Áudio Visual</a>
        </li>

    <%-- InformationSourceTab Link --%>

        <li <c:if test="${currentTab eq 'InformationSourceTab'}" >id="itemSelected"</c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'InformationSourceTab');">Fonte de Informação</a>
        </li>

    <%-- ResearchResponsibleTab --%>

        <li <c:if test="${currentTab eq 'ResearchResponsibleTab'}" >id="itemSelected"</c:if>
            <a onclick="insertHidden ('myForm', 'to', 'to', 'ResearchResponsibleTab');">Responsável pela Pesquisa</a>
        </li>

        <li>
            <button name="button"
                    value="save"
                    onclick="insertHidden ('myForm', 'to', 'to', 'SaveHeritage');" >
                Salvar
            </button>
        </li>
</ul>

