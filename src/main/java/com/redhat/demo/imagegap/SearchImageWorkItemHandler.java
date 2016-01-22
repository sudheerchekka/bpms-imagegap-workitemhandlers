package com.redhat.demo.imagegap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class SearchImageWorkItemHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem,
			WorkItemManager workItemManager) {
		try {

			String name = (String) workItem.getParameter("Name");
			Map<String, Object> results = new HashMap<String, Object>();
			
			System.out.println("***** Searching Image in Database *****: name : " + name);

			try {
				Connection dbConnection = null;
				PreparedStatement preparedStatement = null;

				String selectSQL = "SELECT posterId, posterUrl FROM bpms62.MOVIE_EPISODE_POSTER WHERE posterTags = ?";

				try {
					dbConnection = getDBConnection();
					preparedStatement = dbConnection
							.prepareStatement(selectSQL);
					preparedStatement.setString(1, name);

					// execute select SQL statement
					ResultSet rs = preparedStatement.executeQuery();

					while (rs.next()) {

						int posterId = rs.getInt("posterId");
						String posterUrl = rs.getString("posterUrl");
						//String posterDescription = rs.getString("posterDescription");
						//String posterTags = rs.getString("posterTags");
						System.out.println("***** Poster Name=" + name + " posterId=" + posterId + " posterUrl=" + posterUrl);

						if (posterUrl != null) {
							results.put("PosterId", posterId);
							results.put("PosterUrl", posterUrl);
							//results.put("posterDescription", posterDescription);
							//results.put("posterTags", posterTags);
						}
					}

				} catch (SQLException e) {

					System.out.println(e.getMessage());

				} finally {

					if (preparedStatement != null) {
						preparedStatement.close();
					}

					if (dbConnection != null) {
						dbConnection.close();
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			// Complete Work Item with results
			workItemManager.completeWorkItem(workItem.getId(), results);

		} catch (Exception e) {
			System.out
					.println("***** Search Image in Database aborted for WorkItemID "
							+ workItem.getId());
			workItemManager.abortWorkItem(workItem.getId());
			e.printStackTrace();
		}
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out
				.println("***** Search Image in Database aborted for WorkItemID "
						+ workItem.getId());

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
