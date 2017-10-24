package im.heart.core.plugins.qr.utils;


import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

/**
 * 
 * @author lkg
 * @desc 图片处理工具类
 */
public class ImageUtils {
	private static final String BASE64TOKEN = "base64,";
	private static final int RATIO_HEIGHT = 20;
	private static final int RATIO_WIDTH = 20;
	/**
	 * 
	 * @Desc：readDataURIImage
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage readDataURIImage(URI uri) throws IOException {
		String uriString = uri.toString();
		if (!uriString.startsWith("data:image/")) {
			throw new IOException("Unsupported data URI MIME type");
		}
		int base64Start = uriString.indexOf(BASE64TOKEN);
		if (base64Start < 0) {
			throw new IOException("Unsupported data URI encoding");
		}
		String base64DataEncoded = uriString.substring(base64Start
				+ BASE64TOKEN.length());
		String base64Data = URLDecoder.decode(base64DataEncoded, "UTF-8");
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Data);
		return ImageIO.read(new ByteArrayInputStream(imageBytes));
	}
	
	

	/**
	 * @Desc：转换图片 文件BufferedImage copy 自 com.google.zxing.client.j2se
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage readImage(File file) throws IOException {
		return readImage(file.toURI());
	}

	/**
	 * 
	 * @Desc：转换图片 BufferedImage copy 自 com.google.zxing.client.j2se
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage readImage(URI uri) throws IOException {
		if ("data".equals(uri.getScheme())) {
			return readDataURIImage(uri);
		}
		BufferedImage result;
		try {
			result = ImageIO.read(uri.toURL());
		} catch (IllegalArgumentException iae) {
			throw new IOException("Resource not found: " + uri, iae);
		}
		if (result == null) {
			throw new IOException("Could not load " + uri);
		}
		return result;
	}

	/**
	 * 
	 * @Desc：转换byte 流为BufferedImage
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage tBoufferedImageByByte(byte[] bytes)
			throws IOException {
		if (bytes != null) {
			try {
				InputStream inputStream = new ByteArrayInputStream(bytes);
				BufferedImage bufferedImage = ImageIO.read(inputStream);
				return bufferedImage;
			} catch (IOException e) {
				throw new IOException("Could not Byte  to BufferedImage");
			}
		}

		throw new IOException("Could not load " + bytes);
	}
	
	public static BufferedImage tBoufferedRatioImageByByte(byte[] bytes)
			throws IOException {
		return ratioImage(tBoufferedImageByByte(bytes));
	}
	
	
	/**
	 * 
	 * @Desc：图片缩放
	 * @param logoImage
	 * @return
	 */
	public static BufferedImage ratioImage(BufferedImage logoImage){
		int height=RATIO_HEIGHT;
		int width=RATIO_WIDTH;
		double ratio = 0.0; // 缩放比例
		Image destImage = logoImage.getScaledInstance(height, width,
				BufferedImage.SCALE_SMOOTH);
		if ((logoImage.getHeight() > height) || (logoImage.getWidth() > width)) {
			if (logoImage.getHeight() > logoImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue()
						/ logoImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue()
						/ logoImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(logoImage, null);
		}
		return (BufferedImage) destImage;
	} 
}
