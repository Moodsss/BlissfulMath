package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.ModelUtil;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ModelUtil.class)
public class ModelUtilMixin
{
    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static float interpolateAngle(float angleOne, float angleTwo, float progress)
    {
        float f = (progress - angleTwo) % MathHelper.TAU;

        if (f < -Math.PI)
        {
            f += MathHelper.TAU;
        }

        if (f >= Math.PI)
        {
            f -= MathHelper.TAU;
        }

        return MathUtils.fma(angleOne, f, angleTwo);
    }
}
