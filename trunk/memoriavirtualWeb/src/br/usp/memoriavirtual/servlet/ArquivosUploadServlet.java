package br.usp.memoriavirtual.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.usp.memoriavirtual.controle.BeanComMidia;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.FacesUtil;

@WebServlet(urlPatterns = "/uploadarquivo/*")
@MultipartConfig
public class ArquivosUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -413155777348292080L;

	private static final int TAMANHO_PADRAO_BUFFER = 8192;

	public ArquivosUploadServlet() {
		super();
	}	
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String nomeBean = request.getRequestURL().toString();
		nomeBean = nomeBean.substring(nomeBean.indexOf("bean") + 4,
				nomeBean.length());

		for (Part part : request.getParts()) {

			BufferedInputStream in = new BufferedInputStream(request.getPart(
					part.getName()).getInputStream(),
					ArquivosUploadServlet.TAMANHO_PADRAO_BUFFER);

			int i = in.available();

			byte[] b = new byte[i];
			
			int tamanhoBuffer = ArquivosUploadServlet.TAMANHO_PADRAO_BUFFER;
			int totalBytelido = 0;
			int tamanho = 0;
			while (totalBytelido < i) {
				try{
					tamanho = in.read(b, totalBytelido,tamanhoBuffer );
					totalBytelido += tamanho;
				}
				catch(IOException e){
					tamanho = 0;
					continue;
				}catch (IndexOutOfBoundsException e){
					tamanhoBuffer--;
					if(tamanhoBuffer <0)
						break;
					tamanho = 0;
					continue;
				}
					
			}
			

			String nomeArquivo = getNome(part);
			String tipoArquivo = part.getHeader("content-Type");

			in.close();

			Multimidia multimidia = new Multimidia(nomeArquivo, b, tipoArquivo,
					null);

			// Antecipando a instancia do ManegedBean antes mesmo que a
			// página
			// esteja carregada
			FacesContext facesContext = FacesUtil.getFacesContext(request,
					response);
			ELResolver resolver = facesContext.getApplication().getELResolver();
			BeanComMidia bean = (BeanComMidia) resolver.getValue(
					facesContext.getELContext(), null, nomeBean);

			bean.adicionarMidia(multimidia);

			response.setStatus(HttpServletResponse.SC_CREATED);
		}

	}

	private String getNome(Part part) {

		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		this.doGet(request, response);
	}

}
