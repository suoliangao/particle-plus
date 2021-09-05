package top.suoliangao.particleplus.particle.effect;

import top.suoliangao.particleplus.util.ImageUtil;

public class TextEffect extends ImageEffect {

    protected TextEffect (String text, String fontName, float fontSize) {
        super (ImageUtil.getTextImage(text, fontName, fontSize));
        this.type = EffectTypes.TEXT;
    }

}
