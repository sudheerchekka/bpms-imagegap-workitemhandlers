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

import com.redhat.demos.imagegapanalysis.MovieEpisodeRequest;

public class LinkImageWorkItemHandler implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem,
			WorkItemManager workItemManager) {
		try {
			System.out.println("***** Linking image in Database *****");

			// Poster
			Integer posterId = (Integer) workItem.getParameter("posterId");
			String posterUrl = (String) workItem.getParameter("posterUrl");
			String posterDescription = (String) workItem
					.getParameter("posterDescription");
			String posterTags = (String) workItem.getParameter("posterTags");

			// Request
			MovieEpisodeRequest movieEpisodeRequest = (MovieEpisodeRequest) workItem
					.getParameter("MovieEpisodeRequest");
			String name = movieEpisodeRequest.getName();
			java.util.Date airDate = movieEpisodeRequest.getAirDate();
			java.sql.Date airDateSql = new java.sql.Date(airDate.getTime());
			String releaseYear = movieEpisodeRequest.getReleaseYear();

			Map<String, Object> results = new HashMap<String, Object>();

			try {
				Connection conn = this.getDBConnection();
				conn.setAutoCommit(true);

				String insertPosterSQL = "INSERT INTO bpms62.MOVIE_EPISODE_POSTER(posterUrl, posterDescription, posterTags) VALUES(?,?,?,?)";
				String selectPosterSQL = "SELECT posterId, posterUrl FROM bpms62.MOVIE_EPISODE_POSTER WHERE posterUrl = ?";
				String insertRequestSQL = "INSERT INTO bpms62.MOVIE_EPISODE_REQUEST(name, airDate , releaseYear, posterId) VALUES(?,?,?,?,?)";

				if (posterId == null && posterUrl != null) {
					PreparedStatement ps1 = conn
							.prepareStatement(insertPosterSQL);
					ps1.setString(0, posterUrl);
					ps1.setString(1, posterDescription);
					ps1.setString(2, posterTags);
					ps1.execute();
				}
				
				PreparedStatement ps2 = conn
						.prepareStatement(selectPosterSQL);
				ps2.setString(1, posterUrl);
				ResultSet rs = ps2.executeQuery();

				PreparedStatement ps3 = conn
						.prepareStatement(insertRequestSQL);
				
				while (rs.next()) {
					int posterIdToLink = rs.getInt("posterId");
					System.out.println("***** Poster Id to link to Image: " + posterIdToLink);
					
					ps3.setString(0, name);
					ps3.setDate(1, airDateSql);
					ps3.setString(2, releaseYear);
					ps3.setInt(3, posterId);
					ps3.execute();

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			workItemManager.completeWorkItem(workItem.getId(), results);

		} catch (Exception e) {
			System.out
					.println("***** Link image in Database aborted for WorkItemID "
							+ workItem.getId());
			workItemManager.abortWorkItem(workItem.getId());
			e.printStackTrace();
		}
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out
				.println("***** Link image in Database aborted for WorkItemID "
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
