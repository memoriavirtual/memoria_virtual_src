package br.usp.labes.memoriavirtual.database;

import java.sql.*;
import br.usp.labes.memoriavirtual.config.*;

public class Database implements DBConfig {
	private Connection conn;
	
	/**
	 * Realizar conexão com a base de dados
	 */
	public Database() throws Exception {
		Class.forName(driver);
		conn = DriverManager.getConnection(url,username, password);
		conn.setAutoCommit(true);
	}

	/**
	 * Qualquer alteracao estrutural será via este metodo
	 * Ex: create table, drop table, insert, update, delete,...
	 */
	public int update(String cmd) throws Exception {
		Statement stmt = conn.createStatement();
		int ret = stmt.executeUpdate(cmd);
		stmt.close();
		return ret;
	}

	/**
	 * Realizar select ou buscar na base de dados
	 */
	public ResultSet query(String sql) throws Exception {
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(sql);
	}
	
	/**
	 * Fechar conexão com a base de dados
	 */
	public void close() throws Exception {
		conn.close();
	}
	
	/**
	 * Iniciar Transaction
	 */
	public void beginTransaction() throws Exception {
		conn.setAutoCommit(false);
	}
	
	/**
	 * Commit Transaction
	 */
	public void commitTransaction() throws Exception {
		conn.commit();
		conn.setAutoCommit(true);
	}
	
	/**
	 * Roll back Transaction
	 */
	public void rollbackTransaction() throws Exception {
		conn.rollback();
		conn.setAutoCommit(true);
	}
}
