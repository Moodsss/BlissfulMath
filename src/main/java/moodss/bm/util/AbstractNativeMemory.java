package moodss.bm.util;

public abstract class AbstractNativeMemory implements NativeMemory
{
    private final long sizeInBytes;
    private final long address;

    public AbstractNativeMemory(long size)
    {
        if (size <= 0)
        {
            throw new IllegalArgumentException("Size must be a positive non-zero integer!");
        }

        this.sizeInBytes = size * stride();
        this.address = UnsafeUtil.allocateMemory(this.sizeInBytes);

        // allocateMemory returns uninitialised memory. We need to clear it.
        UnsafeUtil.setMemory(this.address, this.sizeInBytes, (byte) 0);
    }

    @Override
    public long address()
    {
        return this.address;
    }

    @Override
    public long sizeInBytes()
    {
        return this.sizeInBytes;
    }
}
