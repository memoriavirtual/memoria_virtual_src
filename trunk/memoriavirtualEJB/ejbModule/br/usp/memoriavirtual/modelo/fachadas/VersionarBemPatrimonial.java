package br.usp.memoriavirtual.modelo.fachadas;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.BemPatrimonial;
import br.usp.memoriavirtual.modelo.entidades.bempatrimonial.VersaoBemPatrimonial;
import br.usp.memoriavirtual.modelo.fachadas.remoto.VersionarBemPatrimonialRemote;

@Stateless(mappedName = "VersionarBemPatrimonial")
public class VersionarBemPatrimonial implements VersionarBemPatrimonialRemote{

	@PersistenceContext(unitName = "memoriavirtual")
	private EntityManager entityManager;
	
	private VersaoBemPatrimonial versaoBem = new VersaoBemPatrimonial();
	
	@Override
	public void SalvarVersaoBemPatrimonial(BemPatrimonial bem) throws ModeloException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try{
			out = new ObjectOutputStream(bos);   
			out.writeObject(bem);
			out.flush();
			byte[] bpByte = bos.toByteArray();
			versaoBem.setBemPatrimonial(bem);
			versaoBem.setBemPatrimonialBytes(bpByte);
			bem.getVersoes().add(versaoBem);
			bos.close();
		} catch (Exception e){
			e.printStackTrace();
			throw new ModeloException(e);
		}
		
		try{
			entityManager.persist(versaoBem);
		} catch(Exception e){
			e.printStackTrace();
			throw new ModeloException(e);
		}
	}
	
	public void excluirVersoesDeBemPatrimonial(BemPatrimonial bem){
		
	}
}
