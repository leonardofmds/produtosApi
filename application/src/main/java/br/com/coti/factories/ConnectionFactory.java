package br.com.coti.factories;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class ConnectionFactory {

	public static Connection getConnection() throws Exception {
		
		var host = "jdbc:postgresql://localhost:5432/bd_produtosapi";
		var user = "admin";
		var pass = "senha123";
		
		return DriverManager.getConnection(host,user,pass);
	}
	
}
