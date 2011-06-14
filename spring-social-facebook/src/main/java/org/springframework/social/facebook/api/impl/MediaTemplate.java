/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.facebook.api.impl;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.social.facebook.api.Photo;
import org.springframework.social.facebook.api.Video;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

class MediaTemplate extends AbstractFacebookOperations implements MediaOperations {

	private final GraphApi graphApi;
	
	private final RestTemplate restTemplate;

	public MediaTemplate(GraphApi graphApi, RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.graphApi = graphApi;
		this.restTemplate = restTemplate;
	}

	public List<Album> getAlbums() {
		return getAlbums("me");
	}

	public List<Album> getAlbums(String userId) {
		requireUserAuthorization();
		return graphApi.fetchConnections(userId, "albums", AlbumList.class).getList();
	}

	public Album getAlbum(String albumId) {
		requireUserAuthorization();
		return graphApi.fetchObject(albumId, Album.class);
	}
	
	public String createAlbum(String name, String description) {
		requireUserAuthorization();
		return createAlbum("me", name, description);
	}
	
	public String createAlbum(String ownerId, String name, String description) {
		requireUserAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.set("name", name);
		data.set("message", description);
		return graphApi.publish(ownerId, "albums", data);
	}
	
	public byte[] getAlbumImage(String albumId) {
		return getAlbumImage(albumId, ImageType.NORMAL);
	}
	
	public byte[] getAlbumImage(String albumId, ImageType imageType) {
		requireUserAuthorization();
		return graphApi.fetchImage(albumId, "picture", imageType);
	}
	
	public List<Photo> getPhotos(String albumId) {
		requireUserAuthorization();
		return graphApi.fetchConnections(albumId, "photos", PhotoList.class).getList();
	}
	
	public Photo getPhoto(String photoId) {
		requireUserAuthorization();
		return graphApi.fetchObject(photoId, Photo.class);
	}
	
	public byte[] getPhotoImage(String photoId) {
		return getPhotoImage(photoId, ImageType.NORMAL);
	}
	
	public byte[] getPhotoImage(String photoId, ImageType imageType) {
		requireUserAuthorization();
		return graphApi.fetchImage(photoId, "picture", imageType);
	}

	public String postPhoto(Resource photo) {
		requireUserAuthorization();
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("source", photo);
		return graphApi.publish("me", "photos", parts);
	}
	
	public String postPhoto(Resource photo, String caption) {
		requireUserAuthorization();
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("source", photo);
		parts.set("message", caption);
		return graphApi.publish("me", "photos", parts);
	}
	
	public String postPhoto(String albumId, Resource photo) {
		requireUserAuthorization();
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("source", photo);
		return graphApi.publish(albumId, "photos", parts);
	}
	
	public String postPhoto(String albumId, Resource photo, String caption) {
		requireUserAuthorization();
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("source", photo);
		parts.set("message", caption);
		return graphApi.publish(albumId, "photos", parts);
	}
	
	public List<Video> getVideos() {
		requireUserAuthorization();
		return getVideos("me");
	}
	
	public List<Video> getVideos(String userId) {
		requireUserAuthorization();
		return graphApi.fetchConnections(userId, "videos", VideoList.class).getList();
	}
	
	public Video getVideo(String videoId) {
		requireUserAuthorization();
		return graphApi.fetchObject(videoId, Video.class);
	}
	
	public byte[] getVideoImage(String videoId) {
		return getVideoImage(videoId, ImageType.NORMAL);
	}
	
	public byte[] getVideoImage(String videoId, ImageType imageType) {
		requireUserAuthorization();
		return graphApi.fetchImage(videoId, "picture", imageType);
	}
	
	@SuppressWarnings("unchecked")
	public String postVideo(Resource video) {
		requireUserAuthorization();
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("file", video);
		Map<String, Object> response = restTemplate.postForObject("https://graph-video.facebook.com/me/videos", parts, Map.class);
		return (String) response.get("id");
	}
	
	@SuppressWarnings("unchecked")
	public String postVideo(Resource video, String title, String description) {
		requireUserAuthorization();
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.set("file", video);
		parts.set("title", title);
		parts.set("description", description);
		Map<String, Object> response = restTemplate.postForObject("https://graph-video.facebook.com/me/videos", parts, Map.class);
		return (String) response.get("id");
	}
}