/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvirtual.catalog.heritage;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author fabricio
 */
public class RenderTab extends ActionSupport {
    private String to;

    public String getTo() {
        return to;
    }




    public void setTo (String to) {
        this.to = to;
    }

    @Override
    public String execute () {
        if (to.equals ("HeritageTab"))
            return "RenderHeritageTab";

        if (to.equals ("AuthorityTab"))
            return "RenderAuthorityTab";

        if (to.equals ("ProductionTab"))
            return "RenderProductionTab";

        if (to.equals ("DescriptionTab"))
            return "RenderDescriptionTab";

        if (to.equals ("InterventionTab"))
            return "RenderInterventionTab";

        if (to.equals ("UseConditionTab"))
            return "RenderUseConditionTab";

        if (to.equals ("HistoryAndOriginTab"))
            return "RenderHistoryAndOriginTab";

        if (to.equals ("AcquisitionDocumentTab"))
            return "RenderAcquisitionDocumentTab";

        if (to.equals("SubjectAndDescriptorsTab"))
            return "RenderSubjectAndDescriptorsTab";

        if (to.equals("MultimediaTab"))
            return "RenderMultimediaTab";

        if (to.equals ("InformationSourceTab"))
            return"RenderInformationSourceTab";

        if (to.equals("ResearchResponsibleTab"))
            return "RenderResearchResponsibleTab";

        return "input";
    }
}
