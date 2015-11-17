package com.github.photobug.storage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.nutz.img.Images;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Descriptor;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDescriptor;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.github.photobug.Helper;
import com.github.photobug.bean.PhotoMeta;

public class LocalPhotoOperator implements PhotoOperator {

	Log log = Logs.get();

	static DateFormat dateFormat4dir = new SimpleDateFormat("yyyyMMdd");
	
	private PropertiesProxy config = Mvcs.getIoc().get(PropertiesProxy.class, "config");
	
	private String path = Helper.getPath(config.get("photo-dir"));

	public BufferedImage clipScale(BufferedImage img, int width, int height) {
		return Images.clipScale(img, width, height);
	}

	public BufferedImage zoomScale(BufferedImage img, int width, int height, Color color) {
		return Images.zoomScale(img, width, height, color);
	}


	public String save(File img, String format) {
		Calendar now = Calendar.getInstance();
		String datePath = dateFormat4dir.format(now.getTime());
		
		
		String uniqueName = Helper.generateUniqueKey();

		String key = datePath + File.separator + uniqueName + format;
		String filePath = path + File.separator + key;
		
		Files.createDirIfNoExists(path + File.separator + datePath);
		
		File newFile = new File(filePath);
		img.renameTo(newFile);
		return key;
	}

	public PhotoMeta getPhotoMeta(File img) {
		PhotoMeta meta = new PhotoMeta();

		DateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		try {
			// @see
			// https://github.com/drewnoakes/metadata-extractor/wiki/SampleOutput
			Metadata metadata = ImageMetadataReader.readMetadata(img);

			ExifIFD0Directory ExifIFD0_Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			if (ExifIFD0_Directory != null) {
				ExifIFD0Descriptor ExifIFD0_Descriptor = new ExifIFD0Descriptor(ExifIFD0_Directory);
				meta.setCamera(Strings.sNull(ExifIFD0_Descriptor.getDescription(0x010f)) + " " + Strings.sNull(ExifIFD0_Descriptor.getDescription(0x0110)));
			}

			ExifSubIFDDirectory ExifSubIFD_Directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			if (ExifSubIFD_Directory != null) {
				ExifSubIFDDescriptor ExifSubIFD_Descriptor = new ExifSubIFDDescriptor(ExifSubIFD_Directory);
				meta.setAperture(ExifSubIFD_Descriptor.getApertureValueDescription());
				meta.setExposure(ExifSubIFD_Descriptor.getExposureTimeDescription());
				meta.setIso(ExifSubIFD_Descriptor.getIsoEquivalentDescription());
				String dateTime = ExifSubIFD_Descriptor.getDescription(0x9003);
				if (dateTime != null) {
					Date time;
					try {
						time = format.parse(dateTime);
						meta.setTime(time);
					} catch (ParseException e) {
					}
				}
			}
		} catch (ImageProcessingException e) {
			log.error("error while reading metadata from photo", e);
		} catch (IOException e) {
		}
		return meta;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
