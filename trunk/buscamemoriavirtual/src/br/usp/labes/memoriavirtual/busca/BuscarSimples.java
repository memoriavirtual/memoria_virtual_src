/**
 * 
 */
package br.usp.labes.memoriavirtual.busca;
/**
 * 
 */
import java.sql.*;
import br.usp.labes.memoriavirtual.database.*;

/**
 * @author paul
 *
 */
public class BuscarSimples {

	private String busca;

	public BuscarSimples() {
		this.busca = null;
	}
	
	public void setBusca(String busca) { this.busca = busca; }
	
	public String getBusca() { return this.busca; }
	
	public static ResultSet find(Database db, String busca) throws Exception {
		return db.query("SELECT * FROM heritage WHERE collection LIKE '%"+busca+"%'");
	}
	
	public static ResultSet findBy(Database db, String busca) throws Exception {
		return db.query("SELECT * FROM heritage WHERE collection LIKE '%"+busca+"%'");
	}
	
	public static ResultSet findByPrimaryKey(Database db,String busca) throws Exception {
		return db.query("SELECT * FROM heritage WHERE collection LIKE '%"+busca+"%'");
	}
		
	public ResultSet Buscar() throws Exception { 
		Database db = new Database();
		ResultSet rs = null;
		
		String busca = this.busca;
		
		if(busca != null){
			rs=findBy(db,busca);
		}
		
		db.close();
		return rs;
	}
	public static BuscarSimples next(ResultSet rs) throws Exception {
		
		if (rs.next()) {
			BuscarSimples buscarSimples = new BuscarSimples();
			
			buscarSimples.setBusca(rs.getString("busca"));
			
			return buscarSimples;
		}
		return null;
	}

	public String toString() {
		return( 
			this.busca.toString()
			);
	}

	public static String unitTest(String busca) throws Exception {
		BuscarSimples buscarSimples = new BuscarSimples();
		
		buscarSimples.setBusca(busca);

		return buscarSimples.getBusca();
	}
	
	public static ResultSet unitTest01(String busca) throws Exception {
		BuscarSimples buscarSimples = new BuscarSimples();
		ResultSet rs = null;
		
		buscarSimples.setBusca(busca);
		rs = buscarSimples.Buscar();
		return rs;
	}
	
	public static void unitTest02(ResultSet rs) throws Exception {
		
		while (rs.next()) {
			System.out.println(rs.getString(1));			
		}	
	}


	public static void main(String args[]) throws Exception {
		String busca = "sao";
		
		busca =	BuscarSimples.unitTest(busca);		
		System.out.println("Result of UnitTest: "+busca);		
		
		ResultSet rs = BuscarSimples.unitTest01(busca);
		
		BuscarSimples.unitTest02(rs);
		
	}

}
