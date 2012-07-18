package br.usp.memoriavirtual.filtro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.BeanComMidia;
import br.usp.memoriavirtual.controle.CadastrarInstituicaoMB;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.FacesUtil;

public class ArquivosUploadFiltro implements Filter ,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -413155777348292080L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String nomeArquivo = "";// nome do arquivo
		byte[] bytes = null;
		ServletInputStream in = request.getInputStream();
		byte[] linha = new byte[128];
		int i = in.readLine(linha, 0, 128);
		int tamanhoBorda = i - 2;
		String borda = new String(linha, 0, tamanhoBorda);
		System.out.println(borda);
		while (i != -1) {
			String novaLinha = new String(linha, 0, i);
			System.out.println(novaLinha);
			if (novaLinha.startsWith("Content-Disposition: form-data; name=\"")) {
				String s = new String(linha, 0, i - 2);
				System.out.println(s);
				int pos = s.indexOf("filename=\"");
				if (pos != -1) {
					String caminhoDoArquivo = s.substring(pos + 10,
							s.length() - 1);// So tem significa do para clientes
											// windows
					pos = caminhoDoArquivo.lastIndexOf("\\");
					if (pos != -1)
						nomeArquivo = caminhoDoArquivo.substring(pos + 1);// nome
																			// do
																			// arquivo
																			// clientes
																			// windows
					else
						nomeArquivo = caminhoDoArquivo;// nome do arquivo
														// clientes UNIX
				}
				i = in.readLine(linha, 0, 128);
				String contentType = new String(linha, 0, i);
				System.out.println(contentType);
				contentType = contentType.substring( contentType.indexOf("Content-Type: ") +14 , contentType.indexOf("\n"));
				System.out.println(contentType);
				i = in.readLine(linha, 0, 128);
				System.out.println(new String(linha, 0, i));
				i = in.readLine(linha, 0, 128);
				System.out.println(new String(linha, 0, i));
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				novaLinha = new String(linha, 0, i);
				while (i != -1 && !novaLinha.startsWith(borda)) {
					buffer.write(linha, 0, i);
					i = in.readLine(linha, 0, 128);
					if(i == -1)
						break;
					novaLinha = new String(linha, 0, i);
					
				}
				bytes = buffer.toByteArray();
				
				
				
				
				Multimidia multimidia = new Multimidia(nomeArquivo, bytes,contentType, null);

				// Antecipando a instancia do ManegedBean antes mesmo que a
				// página
				// esteja carregada
				FacesContext facesContext = FacesUtil.getFacesContext(request,
						response);
				ELResolver resolver = facesContext.getApplication()
						.getELResolver();
				BeanComMidia bean = (CadastrarInstituicaoMB) resolver
						.getValue(facesContext.getELContext(), null,
								"cadastrarInstituicaoMB");

				bean.adicionarMidia(multimidia);
				
				
			}
			i = in.readLine(linha, 0, 128);
		}
	}
	/*
	 * 
	 * //byte bytes[] = new byte[tamanhoForm]; /* int byteLido = 0;
	 * 
	 * int totalBytesLidos = 0;
	 * 
	 * while(totalBytesLidos < tamanhoForm){
	 * 
	 * byteLido = in.read(bytes, totalBytesLidos, tamanhoForm);
	 * 
	 * totalBytesLidos += byteLido;
	 * 
	 * }
	 * 
	 * byte b[]=new byte[1024]; int data=in.read(b);
	 * 
	 * while(data!=-1) { out.write(b);
	 * 
	 * data=in.read(b); }
	 * 
	 * byte bytes[] = out.toByteArray();
	 * 
	 * int pos ; //System.out.println(new String(bytes)); String cabecalho = new
	 * String (bytes , 0 , 300);
	 * 
	 * 
	 * String nomeArquivo = cabecalho.substring( pos =
	 * cabecalho.indexOf("filename=\"") + 10,pos = cabecalho.indexOf('"', pos));
	 * System.out.println(nomeArquivo); String contentType =
	 * cabecalho.substring( cabecalho.indexOf("Content-Type:") +
	 * 13,pos = cabecalho.indexOf("\n" , pos)); System.out.println(contentType);
	 * 
	 * int bordaInicio = tipoForm.lastIndexOf('=');
	 * 
	 * String borda = tipoForm.substring(bordaInicio + 1);
	 * 
	 * pos++;
	 * 
	 * pos = cabecalho.indexOf("\n", pos) + 1;
	 * 
	 * //System.out.println(cabecalho.substring(pos, cabecalho.length() -1));
	 * 
	 * String rodape = new String (bytes , bytes.length - 301 , 300);
	 * 
	 * int bordaFim = rodape.indexOf(borda) ;
	 * 
	 * String midia = new String(bytes, pos, bytes.length -301 - (pos +1) +
	 * (bordaFim +1) );
	 * 
	 * InputStream in1 = new ByteArrayInputStream(midia.getBytes());
	 * BufferedImage bImageFromConvert = ImageIO.read(in1);
	 * 
	 * ImageIO.write(bImageFromConvert, "jpg", new File(
	 * "/home/bigmac/servlet.jpg"));
	 * 
	 * //System.out.println(data);
	 * 
	 * /* int bordaFim = midia.indexOf(borda, pos) - 4;
	 * 
	 * int posInicioMidia = midia.substring(0, pos).getBytes().length;
	 * 
	 * int posFimMidia = midia.substring(0,bordaFim).getBytes().length;
	 * 
	 * String content = new String(bytes, posInicioMidia, posFimMidia);
	 * 
	 * System.out.println(new String(content.getBytes()));
	 * 
	 * Multimidia multimidia = new Multimidia(nomeArquivo, contentType,
	 * content.getBytes(), tamanhoForm , null);
	 * 
	 * // Antecipando a instancia do ManegedBean antes mesmo que a página //
	 * esteja carregada FacesContext facesContext = FacesUtil
	 * .getFacesContext(request, response); ELResolver resolver =
	 * facesContext.getApplication().getELResolver(); CadastrarInstituicaoMB
	 * bean = (CadastrarInstituicaoMB) resolver
	 * .getValue(facesContext.getELContext(), null, "cadastrarInstituicaoMB");
	 * 
	 * bean.adicionarArquivo(multimidia);
	 */

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
