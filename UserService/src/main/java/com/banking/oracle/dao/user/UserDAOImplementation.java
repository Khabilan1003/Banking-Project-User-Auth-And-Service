package com.banking.oracle.dao.user;

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
public class UserDAOImplementation implements UserDAO {
	@Autowired
	Connection conn;

	@Override
	public Integer create(User user) {
	    int generatedId = -1; // Initialize the ID to -1 to indicate failure
	    try {
	        String sql = "INSERT INTO `b2_user_tbl` ("
	                + "`firstname` , `lastname` , `username` , `password` , `branchCode` , `phoneNumber` , `email`) "
	                + "VALUES (?,?,?,?,?,?,?)";
	        
	        // Use RETURN_GENERATED_KEYS to get the auto-generated ID
	        PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

	        pst.setString(1, user.getFirstname());
	        pst.setString(2, user.getLastname());
	        pst.setString(3, user.getUsername());
	        pst.setString(4, user.getPassword());
	        pst.setInt(5, user.getBranchCode());
	        pst.setString(6, user.getPhoneNumber());
	        pst.setString(7, user.getEmail());

	        int rows = pst.executeUpdate();
	        
	        // Check if the insert was successful and retrieve the generated keys
	        if (rows > 0) {
	            ResultSet rs = pst.getGeneratedKeys();
	            if (rs.next()) {
	                generatedId = rs.getInt(1); // Retrieve the generated ID
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return generatedId; // Return the generated ID or -1 in case of failure
	}
	
	@Override
	public User get(Integer userId) {
		User user = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM b2_user_tbl WHERE userId = " + userId);
			if (result.next()) {
				user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getInt(6), result.getString(7), result.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public User get(String username) {
		User user = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM b2_user_tbl WHERE username = " + username);
			if (result.next()) {
				user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getInt(6), result.getString(7), result.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM b2_user_tbl");
			while (result.next()) {
				users.add(new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getInt(6), result.getString(7), result.getString(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	@Override
	public boolean update(User user) {
		if (user == null || user.getUserId() == null) {
			throw new IllegalArgumentException("User and UserId must not be null");
		}

		List<String> setClauses = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();

		if (user.getFirstname() != null) {
			setClauses.add("firstname = ?");
			parameters.add(user.getFirstname());
		}
		if (user.getLastname() != null) {
			setClauses.add("lastname = ?");
			parameters.add(user.getLastname());
		}
		if (user.getUsername() != null) {
			setClauses.add("username = ?");
			parameters.add(user.getUsername());
		}
		if (user.getPassword() != null) {
			setClauses.add("password = ?");
			parameters.add(user.getPassword());
		}
		if (user.getPhoneNumber() != null) {
			setClauses.add("phoneNumber = ?");
			parameters.add(user.getPhoneNumber());
		}
		if (user.getEmail() != null) {
			setClauses.add("email = ?");
			parameters.add(user.getEmail());
		}
		if (user.getBranchCode() != null) {
			setClauses.add("branchCode = ?");
			parameters.add(user.getBranchCode());
		}

		String sql = "UPDATE `b2_user_tbl` SET " + String.join(", ", setClauses) + " WHERE userId = ?";
		parameters.add(user.getUserId());

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
	public boolean deleteById(Integer userId) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from b2_user_tbl where userId=?");
			pst.setInt(1, userId);
			int rows = pst.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<User> getByBranchCode(Integer branchCode) {
		List<User> users = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM b2_user_tbl where branchCode = " + branchCode);
			while (result.next()) {
				users.add(new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getInt(6), result.getString(7), result.getString(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}
}
