package io.github.darkkronicle.betterblockoutline.util;

import fi.dy.masa.malilib.util.Color4f;
import lombok.experimental.UtilityClass;

// Really cool article on chroma in RGB https://krazydad.com/tutorials/makecolors.php
// May in future may be cool to use LAB or other human ones.
@UtilityClass
public class ColorUtil {


    public Color4f getRainbow(double percent) {
        return getRainbow(percent, 128, 128, 128, 127, 127, 127);
    }

    /**
     * Gets rainbow from 0-255 values.
     */
    public Color4f getRainbow(double percent, int rMid, int gMid, int bMid, int rRange, int gRange, int bRange) {
        double offSet = Math.PI * 2 / 3;
        double pos = percent * (Math.PI * 2);
        float red   = (float) ((Math.sin(pos             ) * rRange) + rMid);
        float green = (float) ((Math.sin(pos + offSet    ) * gRange) + gMid);
        float blue  = (float) ((Math.sin(pos + offSet * 2) * bRange) + bMid);
        red /= 255f;
        green /= 255f;
        blue /= 255f;
        return new Color4f(red, green, blue, 1);
    }

    public static float getBlink(double percent, float originalAlpha) {
        if (percent == .5) {
            percent = 1;
        } else if (percent < .5) {
            percent = percent * 2;
        } else {
            percent = 1 - ((percent - .5) * 2);
        }
        float easePercent = (float) (-(Math.cos(Math.PI * percent) - 1) / 2);
        return easePercent * originalAlpha;
    }
}
