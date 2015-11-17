package photobug;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Descriptor;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDescriptor;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class MetadataExtractorTest {
	@Test
	public void testExtcate() throws ImageProcessingException, IOException {
		String path = MetadataExtractorTest.class.getResource("/Apple_iPhone_4.jpg").toString().split("[:]")[1];
		System.out.println(path);
		File photo = new File(path);
		
		Metadata metadata = ImageMetadataReader.readMetadata(photo);
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
		        System.out.println(tag.getTagTypeHex() + "----" +  tag.toString());
		    }
		}
//		
//		String camera = "";
//		String aperture = "";
//		String exposure = "";
//		String iso = "";
//		try {
//			Metadata metadata = ImageMetadataReader.readMetadata(photo);
//
//			ExifSubIFDDirectory ExifSubIFD_Directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
//			ExifSubIFDDescriptor ExifSubIFD_Descriptor = new ExifSubIFDDescriptor(ExifSubIFD_Directory);
//			aperture = ExifSubIFD_Descriptor.getApertureValueDescription();
//			exposure = ExifSubIFD_Descriptor.getExposureTimeDescription();
//			iso = ExifSubIFD_Descriptor.getIsoEquivalentDescription();
//
//			ExifIFD0Directory ExifIFD0_Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
//			ExifIFD0Descriptor ExifIFD0_Descriptor = new ExifIFD0Descriptor(ExifIFD0_Directory);
//			camera = ExifIFD0_Descriptor.getDescription(0x010f) + " " + ExifIFD0_Descriptor.getDescription(0x0110);
//					
//			System.out.println(camera);
//			System.out.println(aperture);
//			System.out.println(exposure);
//			System.out.println(iso);
//			
//			
//		} catch (ImageProcessingException e) {
//		} catch (IOException e) {
//		}

	}

}
