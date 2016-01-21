package com.redhat.demo.imagegap.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE_EPISODE_POSTER", catalog = "bpms62")
public class MovieEpisodePoster {
	@Id
	int posterId;
	String posterUrl;
	String posterDescription;
	public int getPosterId() {
		return posterId;
	}
	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}
	public String getPosterUrl() {
		return posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	public String getPosterDescription() {
		return posterDescription;
	}
	public void setPosterDescription(String posterDescription) {
		this.posterDescription = posterDescription;
	}
	public String getPosterTags() {
		return posterTags;
	}
	public void setPosterTags(String posterTags) {
		this.posterTags = posterTags;
	}
	String posterTags;
}
