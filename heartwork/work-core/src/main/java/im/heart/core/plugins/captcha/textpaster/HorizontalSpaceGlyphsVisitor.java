package im.heart.core.plugins.captcha.textpaster;

import java.awt.geom.Rectangle2D;
/**
 * 
 * @Desc：绘制水平线
 * @作者 LKG
 */
public class HorizontalSpaceGlyphsVisitor implements GlyphsVisitors {

    private int spaceBetweenGlyphs=0;

    public HorizontalSpaceGlyphsVisitor(int spaceBetweenGlyphs) {
        this.spaceBetweenGlyphs = spaceBetweenGlyphs;
    }
    @Override
    public void visit(Glyphs glyphs, Rectangle2D backroundBounds) {

        for(int i=1;i< glyphs.size();i++){
            double tx = glyphs.getBoundsX(i-1)+ glyphs.getBoundsWidth(i-1)- glyphs.getBoundsX(i)+spaceBetweenGlyphs;
            double ty = 0;
            glyphs.translate(i,tx,ty);

        }
    }
}
