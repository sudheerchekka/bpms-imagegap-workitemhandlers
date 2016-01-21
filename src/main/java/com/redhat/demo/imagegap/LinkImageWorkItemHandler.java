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

public class LinkImageWorkItemHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		try {
			System.out.println("***** Linking image in Database *****");

			// TODO: JDBC Inserts - MOVIE_EPISODE_POSTER & MOVIE_EPISODE_REQUEST
			try {
				Connection conn = this.getDBConnection();
				conn.setAutoCommit(true);
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO MOVIE_EPISODE_POSTER(poster_id,poster_url,poster_description,poster_tags) VALUES(?,?,?,?)");
				stmt.setString(0, workItem.getParameter("PosterId").toString());
				stmt.setString(1, workItem.getParameter("PosterUrl").toString());
				stmt.setString(2, workItem.getParameter("PosterDescription").toString());
				stmt.setString(3, workItem.getParameter("PosterTags").toString());
				stmt.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			workItemManager.completeWorkItem(workItem.getId(), null);

		} catch (Exception e) {
			System.out.println("***** Link image in Database aborted for WorkItemID " + workItem.getId());
			workItemManager.abortWorkItem(workItem.getId());
			e.printStackTrace();
		}
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("***** Link image in Database aborted for WorkItemID " + workItem.getId());

	}

	private Connection getDBConnection() {
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
