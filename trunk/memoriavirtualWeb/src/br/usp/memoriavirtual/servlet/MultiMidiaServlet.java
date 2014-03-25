package br.usp.memoriavirtual.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.BeanContainerDeMidia;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.FacesUtil;



public class MultiMidiaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final int TAMANHO_PADRAO_BUFFER = 8192 ;//
	
    
    public MultiMidiaServlet() {
        super();
    }

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean type = new Boolean(request.getParameter("type"));
		Boolean thumb = new Boolean(request.getParameter("thumb"));
		
		String nameBean = request.getParameter("bean");
		
		if(null != nameBean){//Imagem do Controle
			Integer indice = null;
			try{
				indice= new Integer (request.getParameter("indice"));
			}catch(Exception e){
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_NOT_FOUND);//404
				return;
			}
			
			
			//Recuperar Referencia do Bean em quest�o
			FacesContext facesContext = FacesUtil.getFacesContext(request, response);
			ELResolver resolver = facesContext.getApplication().getELResolver();   
			BeanContainerDeMidia  bean = (BeanContainerDeMidia)resolver.getValue(facesContext.getELContext(), null, nameBean);
			
			List<Multimidia> midias = bean.getMidias();
			
			if(midias.size() <= indice){
				response.sendError(HttpServletResponse.SC_NOT_FOUND);//404
				return;
			}

			if(type)
				this.enviarType(response , midias.get(indice) );
			else 
				this.enviarStream(response , midias.get(indice) , thumb );
			
		}else{//Testa se  imagem do Banco
			
			
		}
		
	}
	protected void enviarType ( HttpServletResponse response, Multimidia midia ) throws IOException{
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
        out.println(midia.getContentType());
        out.flush();
        close(out);
	}
	protected void enviarStream ( HttpServletResponse response, Multimidia m, Boolean thumb ) throws IOException{
		
		Multimidia midia = (thumb)?m.getThumb() : m;
				
		//iniciando a resposta
		response.reset();
		response.setBufferSize(TAMANHO_PADRAO_BUFFER);
		response.setContentType(getServletContext().getMimeType(midia.getNome()));
		response.setHeader("Content-Length", String.valueOf(midia.getContent().length ));
		response.setHeader("Content-Disposition", "inline; filename=\"" + midia.getNome() + "\"");
		
		//inicia os streams
		BufferedInputStream in = null;
        BufferedOutputStream out = null;
		
        try {
            // Come�a os trabalhos
        	InputStream byteInputStream = new ByteArrayInputStream(midia.getContent(), 0 ,midia.getContent().length );
        	
            in = new BufferedInputStream(  byteInputStream,TAMANHO_PADRAO_BUFFER);
            out = new BufferedOutputStream(response.getOutputStream(),TAMANHO_PADRAO_BUFFER);

            // Enviando a midia
            byte[] buffer = new byte[TAMANHO_PADRAO_BUFFER];
            int tamanho;
            while ((tamanho = in.read(buffer)) > 0) {
                out.write(buffer, 0, tamanho);
            }
            out.flush();
        } finally {
        	
            //fechando as streams
            close(out);
            close(in);
        }
        
	}
	
	private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	

}
