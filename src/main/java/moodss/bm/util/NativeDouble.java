package moodss.bm.util;

import org.lwjgl.system.MemoryUtil;

public class NativeDouble extends AbstractNativeMemory
{
    public static final int BYTES_PER_DOUBLE = Double.SIZE / Byte.SIZE;

    public static void set(NativeDouble nativeDouble, double... values)
    {
        for (int i = 0; i < nativeDouble.size(); i++)
        {
            nativeDouble.set(i, values[i]);
        }
    }

    public static void multiply(NativeDouble nativeDouble, double... values)
    {
        for (int i = 0; i < nativeDouble.size(); i++)
        {
            nativeDouble.set(i, nativeDouble.get(i) * values[i]);
        }
    }

    public static NativeDouble create(double... values)
    {
        NativeDouble df = new NativeDouble(values.length);
        NativeDouble.set(df, values);
        return df;
    }

    public NativeDouble(long size)
    {
        super(size);
    }

    public void set(long index, double value)
    {
        MemoryUtil.memPutDouble(getOffsetAddress(index), value);
    }

    public double get(long index)
    {
        return MemoryUtil.memGetDouble(getOffsetAddress(index));
    }

    @Override
    public long stride()
    {
        return BYTES_PER_DOUBLE;
    }
}
