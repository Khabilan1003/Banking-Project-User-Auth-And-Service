package com.banking.oracle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.oracle.dao.branch.Branch;
import com.banking.oracle.dao.branch.BranchDAO;

@RestController
@RequestMapping("/branch")
public class BranchController {
	@Autowired
	private BranchDAO branchDAO;
	
	@GetMapping("/")
	public ResponseEntity<List<Branch>> getAllBranches(){
		List<Branch> branches = branchDAO.get();
		
		return new ResponseEntity<>(branches , HttpStatus.OK);
	}
}