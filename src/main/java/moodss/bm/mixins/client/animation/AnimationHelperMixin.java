package moodss.bm.mixins.client.animation;

import moodss.bm.MathUtils;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AnimationHelper.class)
public class AnimationHelperMixin
{
    /**
     * @author Mo0dss
     * @reason Reduce calls
     */
    @Overwrite
    public static Vec3f method_41829(float x, float y, float z)
    {
        float norm = (float) MathUtils.dividePi(180D);

        return new Vec3f(x * norm, y * norm, z * norm);
    }
}
