package br.usp.memoriavirtual.controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import br.usp.memoriavirtual.modelo.entidades.ContainerMultimidia;
import br.usp.memoriavirtual.utils.MVModeloCamposMultimidia;

public abstract class BeanContainerDeMidia {

	protected Part part;
	protected List<MVModeloCamposMultimidia> campos = new ArrayList<MVModeloCamposMultimidia>();
	protected ContainerMultimidia containerMultimidia = new ContainerMultimidia();

	public BeanContainerDeMidia() {

	}

	public abstract String uploadFile() throws IOException;

	public abstract String removerMidia(Long id);

	public abstract String imagemDisplay(Long id);

	public abstract String videoDisplay(Long id);

	public abstract String midiaDisplay(Long id);
	
	public abstract String getContentType(Long id);

	public String limpar() {
		this.campos.clear();
		this.containerMultimidia = new ContainerMultimidia();
		return null;
	}

	protected String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

	// getters e setters comecam aqui
	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public ContainerMultimidia getContainerMultimidia() {
		return containerMultimidia;
	}

	public void setContainerMultimidia(ContainerMultimidia containerMultimidia) {
		this.containerMultimidia = containerMultimidia;
	}

	public List<MVModeloCamposMultimidia> getCampos() {
		return campos;
	}

	public void setCampos(List<MVModeloCamposMultimidia> campos) {
		this.campos = campos;
	}

}
