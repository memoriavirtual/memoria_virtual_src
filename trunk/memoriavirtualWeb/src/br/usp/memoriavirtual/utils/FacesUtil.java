package br.usp.memoriavirtual.utils;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author MAC
 */
public class FacesUtil {
	/**
	 * @param request
	 * @param response
	 * @return
	 */
    // Getters -----------------------------------------------------------------------------------

    public static FacesContext getFacesContext(
        HttpServletRequest request, HttpServletResponse response)
    {
        // Pega o  FacesContext atual.
        FacesContext contexto = FacesContext.getCurrentInstance();

        // checa FacesContext atual.
        if (contexto == null) {

            // Cria novo ciclo de vida .
            LifecycleFactory fabricaCicloVida = (LifecycleFactory)
                FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY); 
            Lifecycle cicloVida = fabricaCicloVida.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

            // Cria novo FacesContext.
            FacesContextFactory contextFactory  = (FacesContextFactory)
                FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            contexto = contextFactory.getFacesContext(
                request.getSession().getServletContext(), request, response, cicloVida);

            // Cria novo view.
            UIViewRoot view = contexto.getApplication().getViewHandler().createView(
                contexto, "");
            contexto.setViewRoot(view);                

            // Seta o contexto atual com o novo contexto.
            FacesContextWrapper.setCurrentInstance(contexto);
        }

        return contexto;
    }

    // Helpers -----------------------------------------------------------------------------------

    
    private static abstract class FacesContextWrapper extends FacesContext {
        protected static void setCurrentInstance(FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }     

}