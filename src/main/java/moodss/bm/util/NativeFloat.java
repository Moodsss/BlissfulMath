package moodss.bm.util;

import org.lwjgl.system.MemoryUtil;

public class NativeFloat extends AbstractNativeMemory
{
    public static final int BYTES_PER_FLOAT = Float.SIZE / Byte.SIZE;

    public static void set(NativeFloat directFloat, float... values)
    {
        for (int i = 0; i < directFloat.size(); i++)
        {
            directFloat.set(i, values[i]);
        }
    }

    public static void multiply(NativeFloat directFloat, float... values)
    {
        for (int i = 0; i < directFloat.size(); i++)
        {
            directFloat.set(i, directFloat.get(i) * values[i]);
        }
    }

    public static NativeFloat create(float... values)
    {
        NativeFloat df = new NativeFloat(values.length);
        NativeFloat.set(df, values);
        return df;
    }

    public NativeFloat(long size)
    {
        super(size);
    }

    public void set(long index, float value)
    {
        MemoryUtil.memPutFloat(getOffsetAddress(index), value);
    }

    public float get(long index)
    {
        return MemoryUtil.memGetFloat(getOffsetAddress(index));
    }

    @Override
    public long stride()
    {
        return BYTES_PER_FLOAT;
    }
}
