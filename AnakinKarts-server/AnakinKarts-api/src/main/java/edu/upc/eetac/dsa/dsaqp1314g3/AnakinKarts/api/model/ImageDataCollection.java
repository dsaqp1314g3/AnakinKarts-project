package edu.upc.eetac.dsa.dsaqp1314g3.AnakinKarts.api.model;

import java.util.ArrayList;
import java.util.List;

public class ImageDataCollection {
	private List<ImageData> images = new ArrayList<ImageData>();

	public List<ImageData> getImages() {
		return images;
	}

	public void setImages(List<ImageData> images) {
		this.images = images;
	}

	public void addImage(ImageData image) {
		images.add(image);
	}

}