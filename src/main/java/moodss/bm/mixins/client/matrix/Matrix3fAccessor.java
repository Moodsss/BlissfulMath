package moodss.bm.mixins.client.matrix;

import net.minecraft.util.math.Matrix3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Matrix3f.class)
public interface Matrix3fAccessor
{
    @Accessor("a00")
    float getA00();

    @Accessor("a01")
    float getA01();

    @Accessor("a02")
    float getA02();


    @Accessor("a10")
    float getA10();

    @Accessor("a11")
    float getA11();

    @Accessor("a12")
    float getA12();


    @Accessor("a20")
    float getA20();

    @Accessor("a21")
    float getA21();

    @Accessor("a22")
    float getA22();
}
