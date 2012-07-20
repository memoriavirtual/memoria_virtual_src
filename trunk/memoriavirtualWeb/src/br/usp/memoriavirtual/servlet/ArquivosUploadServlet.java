package br.usp.memoriavirtual.servlet;

import java.io.IOException;
import java.io.InputStream;

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
import br.usp.memoriavirtual.controle.CadastrarInstituicaoMB;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.FacesUtil;

@WebServlet(urlPatterns = "/uploadarquivo/*")
@MultipartConfig
public class ArquivosUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -413155777348292080L;

	public ArquivosUploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String nomeBean = request.getRequestURL().toString();
		nomeBean = nomeBean.substring(nomeBean.indexOf("bean") + 4,
				nomeBean.length());
		
		
		for (Part part : request.getParts()) {

			InputStream is = request.getPart(part.getName()).getInputStream();
			int i = is.available();
			byte[] b = new byte[i];
			is.read(b);

			String nomeArquivo = getNome(part);
			String tipoArquivo = part.getHeader("content-Type");

			is.close();
			
			
			 Multimidia multimidia = new Multimidia(nomeArquivo, b,
			 tipoArquivo,
			 null);
			
			 // Antecipando a instancia do ManegedBean antes mesmo que a
			 // página
			 // esteja carregada
			 FacesContext facesContext = FacesUtil
			 .getFacesContext(request, response);
			 ELResolver resolver = facesContext.getApplication().getELResolver();
			 BeanComMidia bean = (CadastrarInstituicaoMB) resolver.getValue(
			 facesContext.getELContext(), null, nomeBean);
			
			 bean.adicionarMidia(multimidia);
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

		// String nomeArquivo = "";// nome do arquivo
		// byte[] bytes = null;
		// int tamanhoBuffer = request.getContentLength();
		//
		// String nomeBean = request.getRequestURL().toString();
		// System.out.println(nomeBean);
		// nomeBean = nomeBean.substring(nomeBean.indexOf("bean") + 4,
		// nomeBean.length());
		//
		// // System.out.println(nomeBean);
		// ServletInputStream in = request.getInputStream();
		// byte[] linha = new byte[128];
		// int i = in.readLine(linha, 0, 128);
		// tamanhoBuffer -= i;
		// int tamanhoBorda = i - 2;
		//
		// String contentType = "";
		// String borda = new String(linha, 0, tamanhoBorda);
		//
		// ByteArrayOutputStream buffer = null;
		// // System.out.println(borda);
		// while (i != -1) {
		// String novaLinha = new String(linha, 0, i);
		// // System.out.println(novaLinha);
		// if (novaLinha.startsWith("Content-Disposition: form-data; name=\""))
		// {
		// String s = new String(linha, 0, i - 2);
		// // System.out.println(s);
		// int pos = s.indexOf("filename=\"");
		// if (pos != -1) {
		// String caminhoDoArquivo = s.substring(pos + 10,
		// s.length() - 1);// So tem significa do para clientes
		// // windows
		// pos = caminhoDoArquivo.lastIndexOf("\\");
		// if (pos != -1)
		// nomeArquivo = caminhoDoArquivo.substring(pos + 1);// nome
		// // do
		// // arquivo
		// // clientes
		// // windows
		// else
		// nomeArquivo = caminhoDoArquivo;// nome do arquivo
		// // clientes UNIX
		// }
		// i = in.readLine(linha, 0, 128);
		// tamanhoBuffer -= i;
		// contentType = new String(linha, 0, i);
		// // System.out.println(contentType);
		// contentType = contentType.substring(
		// contentType.indexOf("Content-Type: ") + 14,
		// contentType.indexOf("\n"));
		// // System.out.println(contentType);
		// i = in.readLine(linha, 0, 128);
		// tamanhoBuffer -= i;
		//
		// // System.out.println(new String(linha, 0, i));
		// i = in.readLine(linha, 0, 128);
		// tamanhoBuffer -= i;
		// // System.out.println(new String(linha, 0, i));
		// buffer = new ByteArrayOutputStream();
		// novaLinha = new String(linha, 0, i);
		// while (i != -1 && !novaLinha.startsWith(borda)) {
		//
		// synchronized(this){
		// buffer.write(linha, 0, i);
		// try {
		// i = in.readLine(linha, 0, 128);
		// tamanhoBuffer -= i;
		// System.out.println(tamanhoBuffer);
		// novaLinha = new String(linha, 0, i);
		// } catch (IOException e) {
		// try {
		// this.wait(10);
		// } catch (InterruptedException e1) {
		// continue;
		// }
		// continue;
		//
		// } catch (IndexOutOfBoundsException e) {
		// novaLinha = "";
		// continue;
		// }
		// }
		// }
		//
		// }
		// i = in.readLine(linha, 0, 128);
		// tamanhoBuffer -= i;
		// System.out.println(tamanhoBuffer);
		// }
		//
		// bytes = buffer.toByteArray();
		//

		//
		// response.reset();
		//
		// response.setStatus( HttpServletResponse.SC_CREATED);
		//
	}

}
