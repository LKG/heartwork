package im.heart.core.plugins.captcha.textpaster;

import java.awt.geom.Rectangle2D;
import java.security.SecureRandom;
import java.util.Random;
/**
 * 
 * @Desc：图像扭曲
 * @作者 LKG
 */
public class RotateGlyphsRandomVisitor implements GlyphsVisitors {
	private double maxAngle = Math.PI / 8;
	private Random myRandom = new SecureRandom();

	public RotateGlyphsRandomVisitor() {
	}

	public RotateGlyphsRandomVisitor(double maxAngle) {
		this.maxAngle = maxAngle;
	}
	@Override
	public void visit(Glyphs gv, Rectangle2D backroundBounds) {
		for (int i = 0; i < gv.size(); i++) {
			gv.rotate(i, maxAngle * myRandom.nextGaussian());
		}
	}
}
