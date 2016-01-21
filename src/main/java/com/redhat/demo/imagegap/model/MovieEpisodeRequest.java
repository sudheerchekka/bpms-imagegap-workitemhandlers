package com.redhat.demo.imagegap.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE_EPISODE_REQUEST", catalog = "bpms62")
public class MovieEpisodeRequest {
	@Id
	int id;
	String name;
	String airDate;
	String posterUrlId;
	String releaseYear;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAirDate() {
		return airDate;
	}
	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}
	public String getPosterUrlId() {
		return posterUrlId;
	}
	public void setPosterUrlId(String posterUrlId) {
		this.posterUrlId = posterUrlId;
	}
	public String getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}
}
