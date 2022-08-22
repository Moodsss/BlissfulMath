package moodss.bm.util;

import net.minecraft.util.Util;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnsafeUtil
{
    //Thank u LWJGL for the base of this :)
    protected static final Unsafe UNSAFE = Util.make(() -> {
        Field[] fields = Unsafe.class.getDeclaredFields();

        /*
        Different runtimes use different names for the Unsafe singleton,
        so we cannot use .getDeclaredField and we scan instead. For example:

        Oracle: theUnsafe
        PERC : m_unsafe_instance
        Android: THE_ONE
        */
        for (Field field : fields)
        {
            if (!field.getType().equals(Unsafe.class))
            {
                continue;
            }

            int modifiers = field.getModifiers();
            if (!(Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)))
            {
                continue;
            }

            try
            {
                field.setAccessible(true);
                return (Unsafe) field.get(null);
            }
            catch (Exception ignored)
            {}
            break;
        }

        throw new UnsupportedOperationException("BlissfulMath requires sun.misc.Unsafe to be available.");
    });

    public static long allocateMemory(long size)
    {
        return UNSAFE.allocateMemory(size);
    }

    public static long reallocateMemory(long address, long newSize)
    {
        return UNSAFE.reallocateMemory(address, newSize);
    }

    public static void freeMemory(long address)
    {
        UNSAFE.freeMemory(address);
    }

    public static void setMemory(long address, long size, byte value)
    {
        UNSAFE.setMemory(address, size, value);
    }
}
