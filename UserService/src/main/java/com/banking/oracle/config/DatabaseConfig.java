package com.banking.oracle.config;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	@Bean
	Connection getConnection() {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

			Connection CONN = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking_project", "root", "password");

			return CONN;
		} catch (Exception e) {
			System.out.println("Database Not Connected : " + e);
			return null;
		}
	}

}