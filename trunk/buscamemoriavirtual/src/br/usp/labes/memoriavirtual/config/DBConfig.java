package br.usp.labes.memoriavirtual.config;

public interface DBConfig {
	public String driver = "org.postgresql.Driver";
	public String url = "jdbc:postgres://127.0.0.1:5432/memoria_virtual/";
	public String username = "postgres";
	public String password = "zion";
}
