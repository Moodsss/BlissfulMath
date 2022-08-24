package moodss.bm;

import it.unimi.dsi.fastutil.doubles.*;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
public class CacheUtils
{
    public static Int2IntFunction memoize(final Int2IntFunction function)
    {
        return new Int2IntFunction()
        {
            private final Int2IntMap cache = new Int2IntOpenHashMap();

            @Override
            public int get(int key)
            {
                return this.cache.computeIfAbsent(key, function);
            }
        };
    }

    public static Double2DoubleFunction memoize(final Double2DoubleFunction function)
    {
        return new Double2DoubleFunction()
        {
            private final Double2DoubleMap cache = new Double2DoubleArrayMap();

            @Override
            public double get(double key)
            {
                return this.cache.computeIfAbsent(key, function);
            }
        };
    }

    public static Double2DoubleFunction memoize(final Double2DoubleFunction function, int maxSize)
    {
        return new Double2DoubleFunction()
        {
            private final Double2DoubleLinkedOpenHashMap cache = new Double2DoubleLinkedOpenHashMap();

            @Override
            public double get(double key)
            {
                if(this.cache.size() == maxSize)
                {
                    this.cache.removeFirstDouble();
                }

                return this.cache.computeIfAbsent(key, function);
            }
        };
    }
}
