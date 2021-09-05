package top.suoliangao.particleplus.particle.effect;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import top.suoliangao.particleplus.util.ExternalResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ImageEffect extends ParticlePlusEffect {

    protected int width, height;
    protected double pixelDensity = 10;
    protected BufferedImage image;
    protected int randomSampling;

    public ImageEffect (String name) {
        super(EffectTypes.IMAGE);
        this.randomSampling = 0;
        this.image = ExternalResourceManager.getImage(name);
    }

    public ImageEffect (BufferedImage image) {
        super(EffectTypes.IMAGE);
        this.randomSampling = 0;
        this.image = image;
    }

    public ImageEffect (ByteBuf buf) {
        super (buf);
    }

//    public static ImageEffect fromLocalFile (String name) {
//        BufferedImage image = ExternalResourceManager.getImage(name);
//        return fromBufferedImage (image);
//    }
//
//    public static ImageEffect fromBufferedImage (BufferedImage image) {
//        ImageEffect eff = new ImageEffect();
//        eff.image = image;
//        return eff;
//    }
//
//    public static ImageEffect fromBufferedImage (BufferedImage image, int randomSampling) {
//        ImageEffect eff = new ImageEffect();
//        eff.image = image;
//        return eff;
//    }
    @Override
    public double[] getParticles() {
        if (this.image == null) return new double[0];
        int nParticles = 0;
        List<Double> ls = new LinkedList<Double>();
        if (randomSampling == 0) {
            for (int i = 0; i < this.width; i ++) {
                for (int j = 0; j < this.height; j++) {
                    Color c = new Color (this.image.getRGB(i, j), true);
                    if (c.getAlpha() == 0) continue;
                    double a = c.getAlpha() == 255 ? 1 : c.getAlpha() / 255d;
                    nParticles ++;
                    ls.add(i/this.pixelDensity); // x pos
                    ls.add((this.height-1)*this.pixelDensity - j/this.pixelDensity); // y pos
                    ls.add(c.getAlpha() == 255 ? 1 : c.getAlpha() / 255d);
                    ls.add(c.getRed()/255d);
                    ls.add(c.getGreen()/255d);
                    ls.add(c.getBlue()/255d);
                }
            }
        }
        double[] out = new double[6*nParticles];
        Iterator<Double> iter = ls.iterator();
        for (int i = 0; i < out.length; i+=6) {
            out[i+0] = iter.next();
            out[i+1] = iter.next();
            out[i+2] = 0;
            double a = iter.next();
            out[i+3] = iter.next() * a;
            out[i+4] = iter.next() * a;
            out[i+5] = iter.next() * a;
        }
        return out;
    }

    @Override
    public ByteBuf buildByteBuffer () {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(this.type.getType());
        return buf;
    }

}
