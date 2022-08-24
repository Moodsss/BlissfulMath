package moodss.bm.mixins.client.vec;

import moodss.bm.MathUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vec2.class)
public class Vec2fMixin
{
    @Shadow
    @Final
    public float x;

    @Shadow
    @Final
    public float y;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float dot(Vec2 vec)
    {
        return MathUtils.fma(this.x, vec.x, this.y * vec.y);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec2 normalized()
    {
        float length = this.length();

        if(length < 1.0E-4F)
        {
            return Vec2.ZERO;
        }

        float norm = 1F / length;

        return new Vec2(this.x * norm, this.y * norm);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float length()
    {
        return Mth.sqrt(this.lengthSquared());
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float lengthSquared()
    {
        return MathUtils.fma(this.x, this.x, this.y * this.y);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float distanceToSqr(Vec2 vec)
    {
        float x1 = vec.x - this.x;
        float y1 = vec.y - this.y;

        return MathUtils.fma(x1, x1, y1 * y1);
    }
}
