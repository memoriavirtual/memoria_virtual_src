package br.usp.memoriavirtual.controle;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import br.usp.memoriavirtual.modelo.fachadas.remoto.EnviarConviteRemote;

public class EnviarConviteMB {

    @EJB
    private EnviarConviteRemote enviarConviteEJB;
    private String emails = "";
    private String validade = "";
    private String nivelAcesso = "";
    
    public String enviarConvite(){
    	boolean sucesso = true;
    	enviarConviteEJB.enviarConvite(null, validade, nivelAcesso);
    	return sucesso ? "sucesso" : "falha";
    }
    
    public List<SelectItem> getNiveisPermitidos(){
    	List<SelectItem> itens = new ArrayList<SelectItem>();
    	itens.add(new SelectItem("Teste")); //itens.add(new SelectItem(quadra.getId, quadra.getDescricao)); // o primeiro parametro � o valor que vc passa para o mb e o segundo � o label que ficar� na p�gina jsp 
    	return itens;
    }
    
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
	public String getNivelAcesso() {
		return nivelAcesso;
	}
	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}
    
    


}
