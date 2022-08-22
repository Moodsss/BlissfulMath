package moodss.bm.util;

public interface NativeMemory
{
    long stride();

    default long getOffsetAddress(long index)
    {
        return address() + (index * sizeInBytes());
    }

    default long size()
    {
        return sizeInBytes() / stride();
    }

    long address();

    long sizeInBytes();

    default void destroy()
    {
        UnsafeUtil.freeMemory(address());
    }
}
