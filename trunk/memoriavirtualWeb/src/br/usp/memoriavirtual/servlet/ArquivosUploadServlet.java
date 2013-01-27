package br.usp.memoriavirtual.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
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
				try {
					tamanho = in.read(b, totalBytelido, tamanhoBuffer);
					totalBytelido += tamanho;
				} catch (IOException e) {
					tamanho = 0;
					continue;
				} catch (IndexOutOfBoundsException e) {
					tamanhoBuffer--;
					if (tamanhoBuffer < 0)
						break;
					tamanho = 0;
					continue;
				}

			}

			Multimidia multimidia = null;
			String nomeArquivo = getNome(part);
			String tipoArquivo = part.getHeader("content-Type");

			in.close();
			if (tipoArquivo.startsWith("image")) {
				
				multimidia = this.processingImage(b, nomeArquivo, tipoArquivo);
			
			} else if (tipoArquivo.startsWith("video")) {
			
				multimidia = this.processingVideo(b, nomeArquivo, tipoArquivo);
			
			}else if (tipoArquivo.startsWith("audio")) {
				
				multimidia = this.processingAudio(b, nomeArquivo, tipoArquivo); 
			
			}else if (tipoArquivo.startsWith("application/pdf")) {
				
				String pathToWeb = getServletContext().getRealPath(File.separator);
				File f = new File(pathToWeb + "resources/imagens/file-pdf-icon.png");
				
				BufferedImage imagem = ImageIO.read(f);

				int new_w = imagem.getWidth(), new_h = imagem.getHeight();
				
				// System.out.println(imagem.getHeight());
				BufferedImage new_img = new BufferedImage(new_w, new_h,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = new_img.createGraphics();

				g.drawImage(imagem, 0, 0, new_w, new_h, null);

				ByteArrayOutputStream aux = new ByteArrayOutputStream();
				ImageIO.write(new_img, "JPG", aux);

				byte[] c = aux.toByteArray();

				//System.out.println(b.length);
				//System.out.println(c.length);
				multimidia = new Multimidia(nomeArquivo, b, tipoArquivo, null,
						new Multimidia("Thumb"
								+ nomeArquivo.substring(0,
										nomeArquivo.length() - 4) + ".jpg", c,
								"image/jpg", null, null));
			}

			// multimidia.setThumb(multimidia);
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
	protected Multimidia processingImage(byte[] stream, String nomeArquivo, String tipoArquivo ) throws IOException{
		
		
		BufferedImage imagem = ImageIO
				.read(new ByteArrayInputStream(stream));

		int new_w = 168, new_h = 168;
		new_h = (int) (((double) new_w / imagem.getWidth()) * imagem
				.getHeight());
		// System.out.println(imagem.getHeight());
		BufferedImage new_img = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = new_img.createGraphics();

		g.drawImage(imagem, 0, 0, new_w, new_h, null);

		ByteArrayOutputStream aux = new ByteArrayOutputStream();
		ImageIO.write(new_img, "JPG", aux);

		
		
		return new Multimidia(nomeArquivo, stream, tipoArquivo, null,
				new Multimidia("Thumb"
						+ nomeArquivo.substring(0,
								nomeArquivo.length() - 4) + ".jpg", aux.toByteArray(),
						"image/jpg", null, null));
	}
	
	protected Multimidia processingVideo(byte[] stream, String nomeArquivo, String tipoArquivo ) throws IOException{
	
		String pathToWeb = getServletContext().getRealPath(File.separator);
		File f = new File(pathToWeb + "resources/imagens/File-Video-icon.png");
		
		BufferedImage imagem = ImageIO.read(f);

		int new_w = imagem.getWidth(), new_h = imagem.getHeight();
		
		BufferedImage new_img = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = new_img.createGraphics();

		g.drawImage(imagem, 0, 0, new_w, new_h, null);

		ByteArrayOutputStream aux = new ByteArrayOutputStream();
		ImageIO.write(new_img, "JPG", aux);
		
		return  new Multimidia(nomeArquivo, stream, tipoArquivo, null,
				new Multimidia("Thumb"
						+ nomeArquivo.substring(0,
								nomeArquivo.length() - 4) + ".jpg", aux.toByteArray(),
						"image/jpg", null, null));
	}
	protected Multimidia processingAudio(byte[] stream, String nomeArquivo, String tipoArquivo ) throws IOException{
		String pathToWeb = getServletContext().getRealPath(File.separator);
		File f = new File(pathToWeb + "resources/imagens/File Audio.png");
		
		BufferedImage imagem = ImageIO.read(f);

		int new_w = imagem.getWidth(), new_h = imagem.getHeight();
		
		
		BufferedImage new_img = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = new_img.createGraphics();

		g.drawImage(imagem, 0, 0, new_w, new_h, null);

		ByteArrayOutputStream aux = new ByteArrayOutputStream();
		ImageIO.write(new_img, "JPG", aux);

		
		return new Multimidia(nomeArquivo, stream, tipoArquivo, null,
				new Multimidia("Thumb"
						+ nomeArquivo.substring(0,
								nomeArquivo.length() - 4) + ".jpg", aux.toByteArray(),
						"image/jpg", null, null));
	}
	protected Multimidia processingPDF(byte[] stream, String nomeArquivo, String tipoArquivo ) throws IOException{
		String pathToWeb = getServletContext().getRealPath(File.separator);
		File f = new File(pathToWeb + "resources/imagens/file-pdf-icon.png");
		
		BufferedImage imagem = ImageIO.read(f);

		int new_w = imagem.getWidth(), new_h = imagem.getHeight();
		
		BufferedImage new_img = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = new_img.createGraphics();

		g.drawImage(imagem, 0, 0, new_w, new_h, null);

		ByteArrayOutputStream aux = new ByteArrayOutputStream();
		ImageIO.write(new_img, "JPG", aux);
		
		return new Multimidia(nomeArquivo, stream, tipoArquivo, null,
				new Multimidia("Thumb"
						+ nomeArquivo.substring(0,
								nomeArquivo.length() - 4) + ".jpg", aux.toByteArray(),
						"image/jpg", null, null));
	}
}
