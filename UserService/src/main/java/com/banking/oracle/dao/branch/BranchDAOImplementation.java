package com.banking.oracle.dao.branch;

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
public class BranchDAOImplementation implements BranchDAO {
	@Autowired
	Connection conn;
	
	@Override
	public List<Branch> get() {
		List<Branch> branches = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM b2_branch_tbl");
			while (result.next()) {
				branches.add(new Branch(result.getInt("branchCode"), result.getString("branchName") , result.getString("ifscCode")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return branches;
	}

	@Override
	public Branch getByBranchCode(Integer branchCode) {
		Branch branch = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM b2_branch_tbl");
			if (result.next()) {
				branch = new Branch(result.getInt("branchCode"), result.getString("branchName") , result.getString("ifscCode"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return branch;
	}

	@Override
	public boolean update(Branch branch) {
		if (branch == null || branch.getBranchCode() == null) {
			throw new IllegalArgumentException("Branch and branchCode must not be null");
		}

		List<String> setClauses = new ArrayList<>();
		List<Object> parameters = new ArrayList<>();

		if (branch.getBranchName() != null) {
			setClauses.add("branchName = ?");
			parameters.add(branch.getBranchName());
		}
		if (branch.getIfscCode() != null) {
			setClauses.add("ifscCode = ?");
			parameters.add(branch.getIfscCode());
		}

		String sql = "UPDATE `b2_branch_tbl` SET " + String.join(", ", setClauses) + " WHERE branchCode = ?";
		parameters.add(branch.getBranchCode());

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
	public boolean delete(Integer branchCode) {
		try {
			PreparedStatement pst = conn.prepareStatement("delete from b2_branch_tbl where branchCode=?");
			pst.setInt(1, branchCode);
			int rows = pst.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean create(Branch branch) {
		try {
			String sql = "INSERT INTO `b2_branch_tbl` (" + "`branchCode` , `branchName` , `ifscCode`) " + "VALUES (?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);

			pst.setInt(1, branch.getBranchCode());
			pst.setString(2, branch.getBranchName());
			pst.setString(3, branch.getIfscCode());

			int rows = pst.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
