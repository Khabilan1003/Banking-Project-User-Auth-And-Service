package com.banking.oracle.dao.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleDAOImplementation implements RoleDAO {
	@Autowired
	Connection conn;

	@Override
	public boolean create(Role role) {
		try {
			String sql = "INSERT INTO `role` (" + "`roleName`) " + "VALUES (?)";
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setString(1, role.getRoleName());

			int rows = pst.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean update(Role role) {
		if (role == null || role.getRoleId() == null) {
			throw new IllegalArgumentException("Role and roleId must not be null");
		}

		List<String> setClauses = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();

		if (role.getRoleName() != null) {
			setClauses.add("roleName = ?");
			parameters.add(role.getRoleName());
		}

		String sql = "UPDATE `role` SET " + String.join(", ", setClauses) + " WHERE roleId = ?";
		parameters.add(role.getRoleId());

		try (PreparedStatement pst = conn.prepareStatement(sql)) {
			for (int i = 0; i < parameters.size(); i++) {
				pst.setObject(i + 1, parameters.get(i));
			}

			int rows = pst.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(Integer roleId) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from role where roleId=?");
			pst.setInt(1, roleId);
			int rows = pst.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Role> get() {
		List<Role> roles = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM role");
			while (result.next()) {
				roles.add(new Role(result.getInt("roleId"), result.getString("roleName")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roles;
	}

	@Override
	public Role getByRoleId(Integer roleId) {
		Role role = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM role WHERE roleId = " + roleId);
			if (result.next()) {
				role = new Role(result.getInt("roleId"), result.getString("roleName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return role;
	}
}