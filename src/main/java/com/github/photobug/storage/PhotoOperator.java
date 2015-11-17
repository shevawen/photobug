package com.github.photobug.storage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import com.github.photobug.bean.PhotoMeta;

public interface PhotoOperator {
	public abstract BufferedImage clipScale(BufferedImage img, int width, int height);
	public abstract BufferedImage zoomScale(BufferedImage img, int width, int height, Color color);
	public abstract String save(File img, String format);
	public abstract PhotoMeta getPhotoMeta(File img);
	
}
