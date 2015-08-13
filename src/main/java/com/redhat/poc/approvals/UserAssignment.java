package com.redhat.poc.approvals;

public class UserAssignment {
	
	public String userName = "";
	public int assignedPerc = 0;
	public int assignedTaskCnt = 0;
	
	public UserAssignment(String userName, int assignedPerc, int assignedTaskCnt) {
		this.userName = userName;
		this.assignedPerc = assignedPerc;
		this.assignedTaskCnt = assignedTaskCnt;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAssignedPerc() {
		return assignedPerc;
	}
	public void setAssignedPerc(int assignedPerc) {
		this.assignedPerc = assignedPerc;
	}
	public int getAssignedTaskCnt() {
		return assignedTaskCnt;
	}
	public void setAssignedTaskCnt(int assignedTaskCnt) {
		this.assignedTaskCnt = assignedTaskCnt;
	}
}
