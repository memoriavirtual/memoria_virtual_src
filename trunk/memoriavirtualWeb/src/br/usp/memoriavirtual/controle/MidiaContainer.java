package br.usp.memoriavirtual.controle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.Part;

import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.modelo.entidades.Multimidia;
import br.usp.memoriavirtual.utils.MensagensDeErro;

public class MidiaContainer {

	protected Part part;
	protected ArrayList<Multimidia> midias = new ArrayList<Multimidia>();
	protected ArrayList<Integer> ApresentaMidias = new ArrayList<Integer>();
	protected ContainerMultimidia containerMultimidia = new ContainerMultimidia();

	public MidiaContainer() {

	}

	public String uploadFile() throws IOException {

		if (part.getSize() > 0) {
			String fileName = getFileName(part);

			InputStream inputStream = null;
			ByteArrayOutputStream out = null;
			try {
				inputStream = part.getInputStream();
				out = new ByteArrayOutputStream();

				int read = 0;
				final byte[] bytes = new byte[128];
				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				out.toByteArray();
				Multimidia m = new Multimidia();
				m.setContentType(part.getContentType());
				m.setNome(fileName);
				m.setContent(out.toByteArray());
				m.setContainerMultimidia(this.containerMultimidia);
				this.midias.add(m);
				this.ApresentaMidias.add(this.ApresentaMidias.size());

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null)
					out.close();
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} else {
			MensagensDeErro.getErrorMessage("cadastrarBemErro", "resultado");
		}

		return null;
	}
	
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
	
	public String removerMidia(Multimidia m) {
		this.midias.remove(m);
		return null;
	}
	
	public String imageDisplay(Multimidia m){
		return m.isImagem() ? "block" : "none";
	}
	
	public String videoDisplay(Multimidia m){
		return m.isVideo() ? "block" : "none";
	}
	
	public String midiaDisplay(Multimidia m){
		return (!m.isImagem() && !m.isVideo()) ? "block" : "none";
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public ArrayList<Multimidia> getMidias() {
		return midias;
	}

	public void setMidias(ArrayList<Multimidia> midias) {
		this.midias = midias;
	}

	public ArrayList<Integer> getApresentaMidias() {
		return ApresentaMidias;
	}

	public void setApresentaMidias(ArrayList<Integer> apresentaMidias) {
		ApresentaMidias = apresentaMidias;
	}

	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}
}
