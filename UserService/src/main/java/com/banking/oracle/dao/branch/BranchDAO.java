package com.banking.oracle.dao.branch;

import java.util.List;

public interface BranchDAO {
	List<Branch> get();
	
	Branch getByBranchCode(Integer branchCode);

	boolean update(Branch branch);

	boolean delete(Integer branchCode);

	boolean create(Branch branch);
}
