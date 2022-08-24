package moodss.bm;

import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import net.minecraft.Util;

public class MathUtils
{
    public static final boolean USE_FMA = Util.make(() -> {
        try
        {
            Math.class.getDeclaredMethod("fma", float.class, float.class, float.class);
            return true;
        }
        catch(NoSuchMethodException ignored)
        {}
        return false;
    });

    protected static final Double2DoubleFunction PI_DIVISION_CACHE = CacheUtils.memoize((Double2DoubleFunction) key -> Math.PI / key);

    protected static final Double2DoubleFunction PI_MULTIPLICATION_CACHE = CacheUtils.memoize((Double2DoubleFunction) key -> Math.PI * key);

    public static float fma(float a, float b, float c)
    {
        if (USE_FMA)
        {
            return Math.fma(a, b, c);
        }

        return a * b + c;
    }

    public static double fma(double a, double b, double c)
    {
        if (USE_FMA)
        {
            return Math.fma(a, b, c);
        }

        return a * b + c;
    }

    public static float fmn(float a, float b, float c)
    {
        if (USE_FMA)
        {
            return Math.fma(a, b, -c);
        }

        return a * b - c;
    }

    public static double fmn(double a, double b, double c)
    {
        if (USE_FMA)
        {
            return Math.fma(a, b, -c);
        }

        return a * b - c;
    }

    /**
     * Use for constant values.
     */
    public static double dividePi(double value)
    {
        return PI_DIVISION_CACHE.applyAsDouble(value);
    }

    /**
     * Use for constant values.
     */
    public static double multiplyPi(double value)
    {
        return PI_MULTIPLICATION_CACHE.applyAsDouble(value);
    }
}
