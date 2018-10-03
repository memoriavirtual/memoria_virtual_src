package br.usp.memoriavirtual.modelo.fachadas;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

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
	public void salvarVersaoBemPatrimonial(BemPatrimonial bem) throws ModeloException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try{
			out = new ObjectOutputStream(bos);   
			out.writeObject(bem);
			out.flush();
			byte[] bpByte = bos.toByteArray();
			versaoBem.setBemPatrimonial(bem);
			versaoBem.setBemPatrimonialBytes(bpByte);
//			bem.getVersoes().add(versaoBem);
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
	
	@Override
	public ArrayList<Long> listarIdsDeVersoes(BemPatrimonial bem) throws ModeloException{
		ArrayList<Long> ids = new ArrayList<Long>();
		
//		List<VersaoBemPatrimonial> versoes = bem.getVersoes();
	
//		for(int i = 0; i < versoes.size(); i++){
//			ids.add(versoes.get(i).getId());
//		}
//		
		return ids;
	}
	
	@Override
	public ArrayList<Timestamp> listarDatasDeVersoes(BemPatrimonial bem){
		ArrayList<Timestamp> datas = new ArrayList<Timestamp>();
		
	//	List<VersaoBemPatrimonial> versoes = bem.getVersoes();
//	
//		for(int i = 0; i < versoes.size(); i++){
//			datas.add(versoes.get(i).getTimestamp());
//		}
//		
		return datas;
	}
}
