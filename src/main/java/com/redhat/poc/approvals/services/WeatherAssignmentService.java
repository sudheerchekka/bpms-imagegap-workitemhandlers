package com.redhat.poc.approvals.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.redhat.poc.approvals.UserAssignment;

public class WeatherAssignmentService {
	
	private static WeatherAssignmentService WEATHERASSGMGTSERVICE;
	
	private LinkedList<UserAssignment> userAssignmentList = new LinkedList<UserAssignment>();
	private int totalTasks = 0;
	private int totalUsers = 0;
	private static String supervisorRole = "supervisory";
	
	protected  WeatherAssignmentService(){
		
		userAssignmentList.add(new UserAssignment("ApproverA",50,0));		
		userAssignmentList.add(new UserAssignment("ApproverB",40,0));		
		userAssignmentList.add(new UserAssignment("ApproverC",10,0));
		totalUsers = 3;
		
	}

	public static WeatherAssignmentService getService (){
		
		if ( WEATHERASSGMGTSERVICE == null){
			WEATHERASSGMGTSERVICE = new WeatherAssignmentService();
		}
		
		return WEATHERASSGMGTSERVICE;
	}
	public Map<String, Object> assignWorkItem(String taskAssignment, String groupAssignment) {
		
		double taskPerc = 0;
		
		
		String taskUser = null, taskRole = "";
		UserAssignment currAssignment=null, newAssignment=null;
		
		totalTasks = totalTasks + 1;

		// ***
		// Assign at least one task to all users
		// ***
		if (totalTasks <= totalUsers) {
			currAssignment = userAssignmentList.pop();
			newAssignment = new UserAssignment(currAssignment.userName,currAssignment.getAssignedPerc(),currAssignment.assignedTaskCnt + 1);
			userAssignmentList.addLast(newAssignment);
			taskUser = currAssignment.userName;
			taskRole = groupAssignment;

			System.out.println("Updated Weather User assignment for user " + newAssignment.userName + " [ Perc " + newAssignment.assignedPerc + " Tasks " + newAssignment.assignedTaskCnt + " Total Tasks " + totalTasks + "] ");
			
		} else {
			//Iterate through Weather Users
			System.out.println("Iterate thru Weather Users to Assign by available Perc - Task Assignment " + taskAssignment + " Starting User " + userAssignmentList.get(0).userName);

			for (int i = 0; i < userAssignmentList.size(); i++) {
				System.out.println("*** Current Weather Users to Assign by available Perc - Task Assignment " + taskAssignment + " User " + userAssignmentList.get(i).userName + " [ Perc " + userAssignmentList.get(i).assignedPerc + " Tasks " + userAssignmentList.get(i).assignedTaskCnt + " Total Tasks " + totalTasks + "] ");
				if (taskAssignment == null || taskAssignment != userAssignmentList.get(i).userName ) {
				// Business Logic to assign based on percentages
					if (taskUser == null) {
					  taskPerc = ((userAssignmentList.get(i).assignedTaskCnt + 1) * 100) / totalTasks;
					  System.out.println("*** Calc Perc for for user " + userAssignmentList.get(i).userName + " [ Perc " + userAssignmentList.get(i).assignedPerc + " Calc Perc " + taskPerc + " Total Tasks " + totalTasks + "] ");
					
					  if (taskPerc <= userAssignmentList.get(i).assignedPerc || userAssignmentList.get(i).assignedTaskCnt == 0) {
						  System.out.println("*** Update Weather User assignment for user " + userAssignmentList.get(i).userName);

						  userAssignmentList.get(i).assignedTaskCnt =  userAssignmentList.get(i).assignedTaskCnt + 1;
						  taskUser = userAssignmentList.get(i).userName;
						  taskRole = groupAssignment;

						  System.out.println("*** Updated Weather User assignment for user " + taskUser + " [ Perc " + userAssignmentList.get(i).assignedPerc + " Tasks " + userAssignmentList.get(i).assignedTaskCnt + " Total Tasks " + totalTasks + "] ");
						
						  if (taskAssignment == null ) break;
					  }
					}
				} else {
					if (taskAssignment == userAssignmentList.get(i).userName) {
						userAssignmentList.get(i).assignedTaskCnt = userAssignmentList.get(i).assignedTaskCnt - 1;
						totalTasks = totalTasks - 1;
						System.out.println("*** Removed Weather User assignment for user " + userAssignmentList.get(i).userName + " [ Perc " + userAssignmentList.get(i).assignedPerc + " Tasks " + userAssignmentList.get(i).assignedTaskCnt + " Total Tasks " + totalTasks + "] ");
					}
				}	
			}	 
		}
		
		Map<String, Object> results = new HashMap<String, Object>();
		
		if (taskUser != null) {
			
			results.put("AssignedActorId", taskUser);
			results.put("AssignedGroupId", taskRole);
			System.out.println("assigned actor id  in if" + results.get("AssignedActorId"));
			System.out.println("assigned Group id in if" + results.get("AssignedGroupId"));
			
		} else {
			results.put("AssignedGroupId", supervisorRole);
			System.out.println("assigned actor id  in elsev" + results.get("AssignedActorId"));
			System.out.println("assigned Group id in else " + results.get("AssignedGroupId"));
			
		}
		
		
		return results;
		
		
	}

}