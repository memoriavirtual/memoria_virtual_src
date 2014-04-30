package br.usp.memoriavirtual.modelo.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.usp.memoriavirtual.utils.MVModeloAcao;
import br.usp.memoriavirtual.utils.MVModeloStatusAprovacao;

@Entity
@SequenceGenerator(name = "APROVACAO_ID", sequenceName = "APROVACAO_SEQ", allocationSize = 1)
public class Aprovacao implements Serializable {

	private static final long serialVersionUID = 7916023027694966362L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APROVACAO_ID")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date criadaEm = new Date();
	
	@Temporal(TemporalType.DATE)
	private Date alteradaEm = null;
	
	@Temporal(TemporalType.DATE)
	private Date expiraEm = new Date();

	@ManyToOne
	@JoinColumn(name = "ANALISTA")
	private Usuario analista = new Usuario();
	
	@ManyToOne
	@JoinColumn(name = "SOLICITANTE")
	private Usuario solicitante = new Usuario();
	
	private String dados = "";
	private MVModeloAcao acao;
	private MVModeloStatusAprovacao status = MVModeloStatusAprovacao.aguardando;

	public Aprovacao() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCriadaEm() {
		return criadaEm;
	}

	public void setCriadaEm(Date criadaEm) {
		this.criadaEm = criadaEm;
	}

	public Date getAlteradaEm() {
		return alteradaEm;
	}

	public void setAlteradaEm(Date alteradaEm) {
		this.alteradaEm = alteradaEm;
	}

	public Date getExpiraEm() {
		return expiraEm;
	}

	public void setExpiraEm(Date expiraEm) {
		this.expiraEm = expiraEm;
	}

	public Usuario getAnalista() {
		return analista;
	}

	public void setAnalista(Usuario analista) {
		this.analista = analista;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante) {
		this.solicitante = solicitante;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}

	public MVModeloAcao getAcao() {
		return acao;
	}

	public void setAcao(MVModeloAcao acao) {
		this.acao = acao;
	}

	public MVModeloStatusAprovacao getStatus() {
		return status;
	}

	public void setStatus(MVModeloStatusAprovacao status) {
		this.status = status;
	}
}
