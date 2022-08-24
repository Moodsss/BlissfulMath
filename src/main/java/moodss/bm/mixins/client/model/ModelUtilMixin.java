package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ModelUtils.class)
public class ModelUtilMixin
{
    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static float rotlerpRad(float angleOne, float angleTwo, float progress)
    {
        float f = (progress - angleTwo) % Mth.HALF_PI;

        if (f < -Math.PI)
        {
            f += Mth.HALF_PI;
        }

        if (f >= Math.PI)
        {
            f -= Mth.HALF_PI;
        }

        return MathUtils.fma(angleOne, f, angleTwo);
    }
}
