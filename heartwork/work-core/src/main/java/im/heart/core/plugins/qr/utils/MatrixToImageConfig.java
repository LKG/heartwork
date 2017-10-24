package im.heart.core.plugins.qr.utils;


import java.awt.image.BufferedImage;

/**
 * 
 * @Desc：：MatrixToImageConfig 配置 ，直接copy com.google.zxing.javase
 * @作者 LKG
 */
public class MatrixToImageConfig {
	  public static final int BLACK = 0xFF000000;
	  public static final int WHITE = 0xFFFFFFFF;
	  
	  private final int onColor;//前景色
	  private final int offColor;//背景色

	  /**
	   * Creates a default config with on color {@link #BLACK} and off color {@link #WHITE}, generating normal
	   * black-on-white barcodes.
	   */
	  public MatrixToImageConfig() {
	    this(BLACK, WHITE);
	  }

	  /**
	   * @param onColor pixel on color, specified as an ARGB value as an int
	   * @param offColor pixel off color, specified as an ARGB value as an int
	   */
	  public MatrixToImageConfig(int onColor, int offColor) {
	    this.onColor = onColor;
	    this.offColor = offColor;
	  }

	  public int getPixelOnColor() {
	    return onColor;
	  }

	  public int getPixelOffColor() {
	    return offColor;
	  }

	  int getBufferedImageColorModel() {
	    // Use faster BINARY if colors match default
	    return onColor == BLACK && offColor == WHITE ? BufferedImage.TYPE_BYTE_BINARY : BufferedImage.TYPE_INT_RGB;
	  }
}
