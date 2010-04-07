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
	private int id;
	private String title;
	private String body;
	private String date;
	private String author;

	public BuscarSimples() {
		this.id = 0;
		this.title = null;
		this.body = null;
		this.date = null;
		this.author = null;
	}
	
	public BuscarSimples(int id) throws Exception {
		Database db = new Database();
		ResultSet rs = findByPrimaryKey(db, id);
		if (rs.next()) {
			this.id = id;
			this.title = rs.getString("title");
			this.body = rs.getString("body");
			this.date = rs.getString("date");
			this.author = rs.getString("author");
		}
		db.close();
	}
	
	public void setId(int id) { this.id = id; }
	public void setTitle(String title) { this.title = title; }
	public void setBody(String body) { this.body = body; }
	public void setDate(String date) { this.date = date; }
	public void setAuthor(String author) { this.author = author; }
	
	public int getId() { return this.id; }
	public String getTitle() { return this.title; }
	public String getBody() { return this.body; }
	public String getDate() { return this.date; }
	public String getAuthor() { return this.author; }	
	
	public static ResultSet find(Database db) throws Exception {
		return db.query("select * from XXXX");
	}
	
	public static ResultSet findByAll(Database db,int id, String title, String body, String date, String author) throws Exception {
		return db.query("select * from XXXX where xxxx ='"+id+"', 'xxxx ="+title+"', 'xxxx ="+body+" 'xxxx ="+date+"', 'xxxx ="+author+"'");
	}
	
	public static ResultSet findByPrimaryKey(Database db,int id) throws Exception {
		return db.query("select * from XXXX where xxxx = "+id);
	}
	
	public static ResultSet findAuthor(Database db,String author) throws Exception {
		return db.query("select * from XXXX where xxxx = "+author);
	}
	
	public static ResultSet findDate(Database db, String date) throws Exception {
		return db.query("select * from XXXX where xxxx = "+date);
	}
	
	public ResultSet Buscar() throws Exception { 
		Database db = new Database();
		ResultSet rs = null;
		int id = this.id;
		String title = this.title;
		String body = this.body;
		String date = this.date;
		String author = this.author;
		
		if(id>0){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 && (title != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 && (title != null) && (body != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 && (title != null) && (body != null) && (author != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 && (title != null) && (body != null) && (author != null) && (date != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 || (title != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 || (title != null) || (body != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 || (title != null) || (body != null) || (author != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		else if(id>0 || (title != null) || (body != null) || (author != null) || (date != null)){
			rs=findByAll(db,id,title,body,date,author);
		}
		
		db.close();
		return rs;
	}
	public static BuscarSimples next(ResultSet rs) throws Exception {
		
		if (rs.next()) {
			BuscarSimples buscarSimples = new BuscarSimples();
			buscarSimples.setId(rs.getInt("id"));
			buscarSimples.setTitle(rs.getString("title"));
			buscarSimples.setBody(rs.getString("body"));
			buscarSimples.setDate(rs.getString("date"));
			buscarSimples.setAuthor(rs.getString("author"));
			
			return buscarSimples;
		}
		return null;
	}

	public String toString() {
		return( 
			this.id+", "+
			this.title+", "+
			this.body+", "+
			this.date+", "+
			this.author.toString()
			);
	}

	public static int unitTest01() throws Exception {
		BuscarSimples buscarSimples = new BuscarSimples();
		
		buscarSimples.setTitle("titulo");
		buscarSimples.setBody("body");
		buscarSimples.setDate("2009-10-10");
		buscarSimples.setAuthor("Paul");

		return buscarSimples.getId();
	}

	public static void unitTest02(int id) throws Exception {
		BuscarSimples buscarSimples = new BuscarSimples(id);
		System.out.println(buscarSimples.toString());
	}

	public static void main(String args[]) throws Exception {
		int id = BuscarSimples.unitTest01();
		BuscarSimples.unitTest02(id);
	}

}
