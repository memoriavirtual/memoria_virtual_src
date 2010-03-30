<%--
    Document   : CreateHeritage
    Created on : 17.06.2009, 08:27:50
    Author     : Fabricio Zancanella and Elisa Yumi Nakagawa
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="/templates/Header.jsp" %>

<html>
    <head>
        <%--<script language='javascript' type='text/javascript' src='${initParam.appPath}/catalog/heritage/common.js'></script>--%>

        <script type="text/javascript" languaged="javascript">
            var counter = 0;

            var STATES = {VISUALIZATION:0, EDIT:1, ENROLLMENT:2};

            var state;

            var addInterventionButton;
            var editInterventionButton;
            var removeInterventionButton;
        
            var actionButton;
        
            var intervetionDateField;
            var intervetionResponsibleField;
            var intervetionDescriptionField;

            var operationMessageText;

            var listOfInterventionsSelect;

            var savePage;

            var currentHiddenData = null;

            // ONLOAD EVENT
            window.onload = function () {

<%--                var hiddenInterventionData = document.getElementById ("hiddenInterventionData");

                if ( hiddenInterventionData.hasChildNodes() )
                {
                    while ( hiddenInterventionData.childNodes.length >= 1 )
                    {
                        hiddenInterventionData.removeChild( hiddenInterventionData.firstChild );
                    }
                }--%>

                listOfInterventionsSelect = document.getElementById ('listOfInterventions');
                listOfInterventionsSelect.onclick = function () {
                    // Transfere os dados do item selecionado para os campos de texto,
                    // para visualizacao.
                    optionToTextFields ('listOfInterventions');
                }

                addInterventionButton = document.getElementById('addInterventionButton');
                addInterventionButton.onclick = function () {
                    setToEnrollmentMode ();
                }
            
                editInterventionButton = document.getElementById('editInterventionButton');
                editInterventionButton.onclick = function () {
                    setToEditMode ();
                }
            
                removeInterventionButton = document.getElementById ('removeInterventionButton');
                removeInterventionButton.onclick = function () {
                    var interventionToBeRemovedId =
                        listOfInterventionsSelect.options [listOfInterventionsSelect.selectedIndex].value;

                    removeOrderedList ('hiddenInterventionData', interventionToBeRemovedId);
                    showInterventions ();
                }
            
                actionButton = document.getElementById ('actionButton');
                actionButton.onclick = function () {

                    addOrderedList ('hiddenInterventionData',
                    'N' + counter,
                    ['N' + counter,
                        'NEW',
                        document.getElementById ('interventionDate').value,
                        document.getElementById ('interventionResponsible').value,
                        document.getElementById ('interventionDescription').value],
                    ['N' + counter + 'Id',
                        'N' + counter + 'Status',
                        'N' + counter + 'Date',
                        'N' + counter + 'Responsible',
                        'N' + counter + 'Description']);

                    counter++;

                    showInterventions ();

                }
            
                savePage = document.getElementById("savePage");
            
                savePage.onclick = function () {
      
                    prepareListToSend ('hiddenInterventionData');
                
                }

                interventionDateField = document.getElementById("interventionDate");
                interventionResponsibleField = document.getElementById ("interventionResponsible");
                interventionDescriptionField = document.getElementById ("interventionDescription");

                operationMessageText = document.getElementById ("operationMessage");

                setToVisualizationMode ();

                showInterventions ();
            }

            function setToVisualizationMode () {
                state = STATES.VISUALIZATION;
                operationMessageText.textContent = "VISUALIZAÇÃO";
                
                interventionDateField.disabled = true;
                interventionResponsibleField.disabled = true;
                interventionDescriptionField.disabled = true;
            }
            
            function setToEditMode () {
                state = STATES.EDIT;

                operationMessageText.textContent = "EDIÇÃO";
                
                interventionDateField.disabled = false;
                interventionResponsibleField.disabled = false;
                interventionDescriptionField.disabled = false;
            }
            
            function setToEnrollmentMode () {
                state = STATES.ENROLLMENT;

                operationMessageText.textContent = "CADASTRAMENTO";

                interventionDateField.disabled = false;
                interventionDateField.value = "";

                interventionResponsibleField.disabled = false;
                interventionResponsibleField.value = "";

                interventionDescriptionField.disabled = false;
                interventionDescriptionField.value = "";
            }



            function optionToTextFields (optionIdName) {
                // Pega o valor da opção (chave).
                var optionKey = document.getElementById(optionIdName).options [listOfInterventionsSelect.selectedIndex].value;

                // Obtém a lista de dados.
                var orderedListOfData = document.getElementById (optionKey);

                var list = orderedListOfData.getElementsByTagName("LI");

                document.getElementById("interventionDate").value = list [2].textContent;

                document.getElementById("interventionResponsible").value = list [3].textContent;

                document.getElementById("interventionDescription").value = list [4].textContent;
            }

            /**
             * Adiciona uma lista ordenada com os respectivos LIs num elemento pai.
             * parentIdName: Nome do elemento pai aonde será colocada a OL.
             * orderedListIdName: Id que deve ser colocado na OL.
             * listOfContents: Lista com o conteúdo dos LIs.
             * listOfIds: Lista com os Ids das LIs.
             */
            function addOrderedList (parentIdName, orderedListIdName, listOfContents, listOfIds) {
                var parent = document.getElementById(parentIdName);

                var ol = document.createElement("ol");

                ol.id = orderedListIdName;

                var li;

                for (var i = 0; i < listOfContents.length; i++) {
                    li = document.createElement ("li");

                    li.id = listOfIds [i];

                    li.innerHTML = listOfContents [i];

                    ol.appendChild(li);
                }

                parent.appendChild(ol);
            }

            /**
             * Remove lista ordenada de um elemento pai.
             * parentIdName: Id do elemento pai aonde a lista está localizada.
             * idName: Id da lista ordenada.
             */
            function removeOrderedList (parentIdName, idName) {
                var parent = document.getElementById (parentIdName);

                parent.removeChild(document.getElementById(idName));
            }

            function prepareListToSend (parentIdName) {
                // Obtem o conjunto de listas ordenadas.
                var listContainer = document.getElementById(parentIdName);

                // Obtem a lista de itens da lista ordenada.
                var olElements = listContainer.getElementsByTagName ("OL");

                var result = "";

                for (var i = 0; i < olElements.length; i++) {
                    var liElements = olElements [i].getElementsByTagName("LI");

                    var id = liElements [0].textContent;
                    var status = liElements [1].textContent;
                    var date = liElements [2].textContent;
                    var responsible = liElements [3].textContent;
                    var description = liElements [4].textContent;

                    result = id + "&;&" + status + "&;&" + date + "&;&" + responsible + "&;&" + description;

                    var form = document.getElementById ('myForm');

                    var input = document.createElement ('input');

                    input.setAttribute('name', 'result');
                    input.setAttribute('type', 'hidden');
                    input.value = result;

                    form.appendChild(input);
                }
            }


            /**
             * Mostra intervenções que estão carregadas temporariamente na página
             * na lista para seleção.
             */
            function showInterventions () {
                var listOfInterventions = document.getElementById ("listOfInterventions");

                // Apaga todas as intervenções correntes.
                while (listOfInterventions.firstChild != null) {
                    listOfInterventions.removeChild (listOfInterventions.firstChild);
                }

                // Carrega todas as intervenções.
                var hiddenInterventionData = document.getElementById ("hiddenInterventionData")

                //
                var interventionSet = hiddenInterventionData.childNodes;

                var fields = new Array (4);

                for (var i = 0; i < interventionSet.length; i++) {
                    if (interventionSet [i].id == null) continue;

                    if (interventionSet [i].nodeName.toUpperCase () == "OL")
                    
                    var listElements = interventionSet [i].childNodes;

                var index = 0;

                for (var j = 0; j < listElements.length; j++) {
                    if (listElements [j].nodeName == null) continue;

                    if (listElements [j].nodeName.toUpperCase () == "LI") {
                        fields [index ++] = listElements [j].textContent;
                    }
                }

                var myOption = document.createElement("option");

                myOption.setAttribute ('value',fields [0]);
                myOption.textContent = fields [2] + " - "+ fields [3] + " - " + fields [4];

                listOfInterventions.appendChild(myOption);
            }
        }
        

        </script>

        <templates:HeadDefault />
        <title>${initParam.applicationName}</title>
    </head>

    <body>
        <div id="sitemain"> <!-- Site main division -->

            <%@include file="/templates/logo.jsp" %>

            <div id="topmenusp">

                <%@include file="/catalog/TopMenu.jsp" %>

            </div> <%-- topmenusp --%>

            <div id="groupsp">
                <div id="sidebarsp">
                    <c:set var="currentTab" value="InterventionTab"/>
                    <%@include file="SideMenu.jsp" %>
                </div>


                <div id="maincontentsp" style="margin-left:240px;">
                    <h1>Bem Patrimonial > Intervenção</h1>

                    <div class="boxsp">

                        <div id="hiddenInterventionData" class="hiddenInterventionData" style="display:none; font-size:10px;">
                            <s:iterator value="interventions">
                                <ol id="<s:property value='id' />" >
                                    <li id="I<s:property value='id' />Id"><s:property value="id" /></li>
                                    <li id="I<s:property value='id' />Status"><s:property value="status" /></li>
                                    <li id="I<s:property value='id' />Date"><s:property value="date" /></li>
                                    <li id="I<s:property value='id' />Responsible"><s:property value="responsible" /></li>
                                    <li id="I<s:property value='id' />Description"><s:property value="description" /></li>
                                </ol>
                            </s:iterator>
                        </div>

                        <s:form id="myForm" 
                                namespace="/heritage"
                                action="SaveInterventionTab"
                                onsubmit="document.listOfInterventions">

                            <s:hidden name="to" value="HeritageTab" />

                            <s:hidden name="listOfInterventionIds" id="listOfInterventionIdsHidden" />
                            <s:hidden name="listOfInterventionDates" id="listOfInterventionDatesHidden" />
                            <s:hidden name="listOfInterventionResponsibles" id="listOfInterventionResponsiblesHidden" />

                            <div id="interventionData">

                                <s:select id= "listOfInterventions"
                                          name="interventions"
                                          list="{}"
                                          size="4"
                                          label="Intervenções sobre o bem patrimonial"
                                          cssStyle="height:80px;width:500px;"/>

                                <button type="button"
                                        id="addInterventionButton"
                                        name="addInterventionButton">
                                    Cadastrar
                                </button>

                                <button type="button"
                                        id="editInterventionButton"
                                        name="editInterventionButton">
                                    Editar
                                </button>

                                <button type="button"
                                        id="removeInterventionButton"
                                        name="removeInterventionButton">
                                    Remover
                                </button>

                            </div>

                            <div id = "interventionFieldsDivision"
                                 style="margin:40px;
                                 border:grey;
                                 border-style:outset;
                                 padding:20px;
                                 float:right;
                                 z-index: 10;">

                                <p id="operationMessage"
                                   style="font-weight:bold;margin-bottom:20px;">CADASTRE UMA NOVA INTERVENÇÃO:</p>

                                <s:textfield id="interventionDate"
                                             label="Data"
                                             name="date"
                                             cssStyle="width:400px;" />

                                <s:textfield id="interventionResponsible"
                                             label="Responsável"
                                             name="responsible"

                                             cssStyle="width:400px;" />

                                <s:textarea  id="interventionDescription"
                                             label="Descrição"
                                             name="description"
                                             rows="5"
                                             cols="47" />

                                <button type="button"
                                        id="actionButton"
                                        name="actionButton"
                                        style="margin-top:20px;">
                                    Cadastrar
                                </button>

                            </div>
                            <br />
                            <s:submit id="savePage" name="savePage" value="Salvar Pagina" />

                        </s:form>

                    </div> <%-- boxsp --%>
                </div> <%-- maincontentsp --%>
            </div> <%-- groupsp --%>

            <div id="footersp">

                <%@include file="/Footer.jsp" %>

            </div> <%-- Footersp --%>
        </div> <%-- Site Main Division --%>
    </body>
</html>
