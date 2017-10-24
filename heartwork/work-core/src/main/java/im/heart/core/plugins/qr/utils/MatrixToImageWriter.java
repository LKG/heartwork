package im.heart.core.plugins.qr.utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;

/**
 * 
 * @作者 LKG 
 * @desc：MatrixToImageWriter  配置 ，直接copy com.google.zxing.javase
 * 
 */
public class MatrixToImageWriter {

	private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();

	private MatrixToImageWriter() {
	}

	/**
	 * Renders a {@link BitMatrix} as an image, where "false" bits are rendered
	 * as white, and "true" bits are rendered as black.
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		return toBufferedImage(matrix, DEFAULT_CONFIG);
	}
	public static BufferedImage tolOGOBufferedImage(BitMatrix matrix,BufferedImage logoImage) {
		return tolOGOBufferedImage(matrix, DEFAULT_CONFIG,logoImage);
	}
	

	public static BufferedImage tolOGOBufferedImage(BitMatrix matrix,MatrixToImageConfig config,BufferedImage logoImage) {
		logoImage=ImageUtils.ratioImage(logoImage);
		int[][] srcPixels = new int[15][15];
		for (int i = 0; i < logoImage.getWidth(); i++) {
			for (int j = 0; j < logoImage.getHeight(); j++) {
				srcPixels[i][j] = logoImage.getRGB(i, j);
			}
		}
		// 二维矩阵转为一维像素数组
		int width = matrix.getWidth();//二维码宽度
		int height = matrix.getHeight();//二维码
		BufferedImage image = new BufferedImage(width, height,
				config.getBufferedImageColorModel());
		int onColor = config.getPixelOnColor();
		int offColor = config.getPixelOffColor();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
			}
		}
		return image;
	}
	/**
	 * As {@link #toBufferedImage(BitMatrix)}, but allows customization of the
	 * output.
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix,
			MatrixToImageConfig config) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				config.getBufferedImageColorModel());
		int onColor = config.getPixelOnColor();
		int offColor = config.getPixelOffColor();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
			}
		}
		return image;
	}
	

	/**
	 * Writes a {@link BitMatrix} to a file.
	 *
	 * @see #toBufferedImage(BitMatrix)
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		writeToFile(matrix, format, file, DEFAULT_CONFIG);
	}

	/**
	 * As {@link #writeToFile(BitMatrix, String, File)}, but allows
	 * customization of the output.
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file,
			MatrixToImageConfig config) throws IOException {
		BufferedImage image = toBufferedImage(matrix, config);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	/**
	 * Writes a {@link BitMatrix} to a stream.
	 *
	 * @see #toBufferedImage(BitMatrix)
	 */
	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		writeToStream(matrix, format, stream, DEFAULT_CONFIG);
	}

	/**
	 * As {@link #writeToStream(BitMatrix, String, OutputStream)}, but allows
	 * customization of the output.
	 */
	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream, MatrixToImageConfig config) throws IOException {
		BufferedImage image = toBufferedImage(matrix, config);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}

}
