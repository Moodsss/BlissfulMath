package moodss.bm.mixins.server;

import moodss.bm.MathUtils;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mth.class)
public abstract class MathHelperMixin
{
    @Shadow
    public static float wrapDegrees(float degrees)
    {
        return 0;
    }

    @Shadow
    @Final
    private static double FRAC_BIAS;

    @Shadow
    @Final
    private static double[] ASIN_TAB;

    @Shadow
    @Final
    private static double[] COS_TAB;

    @Shadow
    public static double fastInvSqrt(double p_14194_) {
        return 0;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static double clampedLerp(double start, double end, double delta)
    {
        if (delta < 0.0D)
        {
            return start;
        }

        if (delta > 1.0D)
        {
            return end;
        }

        return lerp(delta, start, end);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static float clampedLerp(float start, float end, float delta)
    {
        if (delta < 0.0F)
        {
            return start;
        }

        if(delta > 1.0F)
        {
            return end;
        }

        return lerp(delta, start, end);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static float rotLerp(float delta, float start, float end)
    {
        return MathUtils.fma(delta, wrapDegrees(end - start), start);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Deprecated
    @Overwrite
    public static float rotlerp(float start, float end, float delta)
    {
        float value = end - start;

        while(value < -180.0F)
        {
            value += 360.0F;
        }

        while(value >= 180.0F)
        {
            value -= 360.0F;
        }

        return MathUtils.fma(value, delta, start);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static float lerp(float delta, float start, float end)
    {
        return MathUtils.fma(end - start, delta, start);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static double lerp(double delta, double start, double end)
    {
        return MathUtils.fma(end - start, delta, start);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static double lengthSquared(double a, double b)
    {
        return MathUtils.fma(a, a, b * b);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static double lengthSquared(double a, double b, double c)
    {
        return MathUtils.fma(c, c, MathUtils.fma(b, b, a * a));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA, cancel out everything if we hit a wall
     */
    @Overwrite
    public static double atan2(double y, double x)
    {
        double dot = MathUtils.fma(x, x, y * y);

        if (Double.isNaN(dot))
        {
            return Double.NaN;
        }

        boolean flippedY = y < 0.0;
        if (flippedY)
        {
            y = -y;
        }

        boolean flippedX = x < 0.0;
        if (flippedX)
        {
            x = -x;
        }

        boolean nonIdenticalSize = y > x;
        if (nonIdenticalSize)
        {
            double e = x;
            x = y;
            y = e;
        }

        double dotSq = fastInvSqrt(dot);
        x *= dotSq;
        y *= dotSq;

        double shiftedY = FRAC_BIAS + y;
        int bits = (int) Double.doubleToRawLongBits(shiftedY);

        double arc = ASIN_TAB[bits];
        double cos = COS_TAB[bits];

        double shiftedBackY = shiftedY - FRAC_BIAS;

        double k = MathUtils.fmn(y, cos, x * shiftedBackY);

        double l = (6.0 + k * k) * k * 0.16666666666666666;
        double output = arc + l;

        if (nonIdenticalSize)
        {
            output = Math.PI / 2 - output;
        }

        if (flippedX)
        {
            output = Math.PI - output;
        }

        if (flippedY)
        {
            output = -output;
        }

        return output;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static float fastInvCubeRoot(float x)
    {
        int i = Float.floatToIntBits(x);
        i = 1419967116 - i / 3;
        float f = Float.intBitsToFloat(i);
        f = 0.6666667F * f + 1.0F / (3.0F * f * f * x);

        return MathUtils.fma(0.6666667F, f, 1.0F / (3.0F * f * f * x));
    }
}
