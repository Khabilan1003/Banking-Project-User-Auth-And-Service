package com.banking.oracle.dao.userroles;

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
public class UserRolesImplementation implements UserRolesDAO {
	@Autowired
	Connection conn;

	@Override
	public List<UserRoles> getByUserId(Integer userId) {
		List<UserRoles> userRoles = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM userRoles where userId = " + userId);
			while (result.next()) {
				userRoles.add(new UserRoles(result.getInt("userId"), result.getInt("roleId")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userRoles;
	}

	@Override
	public boolean create(List<UserRoles> userRoles) {
		String sql = "INSERT INTO `userRoles` (`userId`, `roleId`) VALUES (?,?)";
		System.out.println("User Roles Create Method");
		System.out.println(userRoles.size());
		try {
			for (UserRoles userRole : userRoles) {
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setInt(1, userRole.getUserId());
				pst.setInt(2, userRole.getRoleId());

				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteByUserId(Integer userId) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from userRoles where userId=?");
			pst.setInt(1, userId);
			int rows = pst.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
