package im.heart.core.plugins.captcha.textpaster;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.security.SecureRandom;
import java.util.Random;

public class ShearGlyphsRandomVisitor implements GlyphsVisitors {

    private double maxShearX;
    private double maxShearY;
    private Random myRandom = new SecureRandom();

    public ShearGlyphsRandomVisitor(double maxShearX, double maxShearY) {
        this.maxShearX = maxShearX;
        this.maxShearY = maxShearY;
    }
    @Override
    public void visit(Glyphs gv, Rectangle2D backroundBounds) {
        for(int i=0;i<gv.size();i++){
            AffineTransform af = new AffineTransform();
            af.setToShear(maxShearX*myRandom.nextGaussian(),maxShearY*myRandom.nextGaussian());
            gv.addAffineTransform(i, af);
        }
    }
}
