package br.usp.memoriavirtual.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.usp.memoriavirtual.controle.CadastrarInstituicaoMB;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.FacesUtil;

import com.sun.mail.iap.ByteArray;

public class CadastrarInstituicaoUpload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -413155777348292080L;
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String tipoForm = request.getContentType();
		
		if (tipoForm != null && tipoForm.indexOf("multipart/form-data") >= 0){
			
			ServletInputStream in = request.getInputStream();
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			int tamanhoForm = request.getContentLength();
			
			
			
			//byte bytes[] = new byte[tamanhoForm];
			/*
			int byteLido = 0;
			
			int totalBytesLidos = 0;
			
			while(totalBytesLidos < tamanhoForm){
				
				byteLido = in.read(bytes, totalBytesLidos, tamanhoForm);
				
				totalBytesLidos += byteLido;
				
			}
			*/
			byte b[]=new byte[1024];  
	        int data=in.read(b);          
	          
	        while(data!=-1)  
	        {  
	        	out.write(b);  
	              
	            data=in.read(b);  
	        } 
			
	        byte bytes[] = out.toByteArray();
	        
			int pos ;
			//System.out.println(new String(bytes));
			String cabecalho = new String (bytes , 0 , 300);
			
			
			String nomeArquivo = cabecalho.substring(
				pos =	cabecalho.indexOf("filename=\"") + 10,pos = cabecalho.indexOf('"', pos));
			System.out.println(nomeArquivo);
			String contentType = cabecalho.substring(
				pos =	cabecalho.indexOf("Content-Type:", pos) + 13,pos = cabecalho.indexOf("\n" , pos));
			System.out.println(contentType);
			
			int bordaInicio = tipoForm.lastIndexOf('=');
			
			String borda = tipoForm.substring(bordaInicio + 1);  
			
			pos++;
			
			pos =	cabecalho.indexOf("\n", pos) + 1;
			
			//System.out.println(cabecalho.substring(pos, cabecalho.length() -1));
			
			String rodape = new String (bytes , bytes.length - 301 , 300);
			
			int bordaFim = rodape.indexOf(borda) ;
			
			String midia = new String(bytes, pos, bytes.length -301 - (pos +1) + (bordaFim +1) );
			
			InputStream in1 = new ByteArrayInputStream(midia.getBytes());
			BufferedImage bImageFromConvert = ImageIO.read(in1);
 
			ImageIO.write(bImageFromConvert, "jpg", new File(
					"/home/bigmac/servlet.jpg"));
			
			//System.out.println(data);
			
			/*
			int bordaFim = midia.indexOf(borda, pos) - 4;
			
			int posInicioMidia  = midia.substring(0, pos).getBytes().length;
			
			int posFimMidia =  midia.substring(0,bordaFim).getBytes().length;
			
			String  content = new String(bytes, posInicioMidia, posFimMidia);
			
			System.out.println(new String(content.getBytes()));
			
			Multimidia multimidia = new Multimidia(nomeArquivo, contentType, content.getBytes(),
					tamanhoForm , null);

			// Antecipando a instancia do ManegedBean antes mesmo que a página
			// esteja carregada
			FacesContext facesContext = FacesUtil
					.getFacesContext(request, response);
			ELResolver resolver = facesContext.getApplication().getELResolver();
			CadastrarInstituicaoMB bean = (CadastrarInstituicaoMB) resolver
					.getValue(facesContext.getELContext(), null,
							"cadastrarInstituicaoMB");

			bean.adicionarArquivo(multimidia);*/
		}
		
		
	}
}
