package com.redhat.demo.imagegap;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class SearchImageWorkItemHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		try {

			String keyword = (String) workItem.getParameter("keyword");
			System.out.println("***** Searching Image in Database...");

			try {
				System.out.println("*********From direct connection**************");
				Connection conn = this.getDirectConnection();
				conn.setAutoCommit(true);
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO ARS_TRAVELLER(NAME,EMAIL,USER_ID,PASSWORD,GENDER,"
								+ "AGE,MOBILE_NO,ADDRESS,CREATED_ON,UNIQUECODE,VERIFIED) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				stmt.setString(0, workItem.getParameter("Name").toString());
				stmt.setString(1, workItem.getParameter("Email").toString());
				stmt.setString(2, workItem.getParameter("UserId").toString());
				stmt.setString(3, workItem.getParameter("Password").toString());
				stmt.setString(4, workItem.getParameter("Gender").toString());
				stmt.setInt(5, Integer.parseInt(workItem.getParameter("Age").toString()));
				stmt.setString(6, workItem.getParameter("MobileNo").toString());
				stmt.setString(7, workItem.getParameter("Address").toString());
				stmt.setDate(8, new Date((new java.util.Date()).getTime()));
				stmt.setString(9, ((new java.util.Date()).getTime() + ""));
				stmt.setBoolean(10, false);
				stmt.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			// TODO: JDBC Query

			workItemManager.completeWorkItem(workItem.getId(), null);

		} catch (Exception e) {
			System.out.println("***** Search Image in Database aborted for WorkItemID " + workItem.getId());
			workItemManager.abortWorkItem(workItem.getId());
			e.printStackTrace();
		}
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("***** Search Image in Database aborted for WorkItemID " + workItem.getId());

	}

	private Connection getDirectConnection() {
		Connection conn = null;
		try {
			String url = "jdbc:mysql://localhost:3306/bpms62";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "bpmsDBA", "jboss123!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return conn;
	}

}
