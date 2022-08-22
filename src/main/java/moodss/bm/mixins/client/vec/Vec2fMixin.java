package moodss.bm.mixins.client.vec;

import moodss.bm.MathUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vec2f.class)
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
    public float dot(Vec2f vec)
    {
        return MathUtils.fma(this.x, vec.x, this.y * vec.y);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec2f normalize()
    {
        float length = this.length();

        if(length < 1.0E-4F)
        {
            return Vec2f.ZERO;
        }

        float norm = 1F / length;

        return new Vec2f(this.x * norm, this.y * norm);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float length()
    {
        return MathHelper.sqrt(this.lengthSquared());
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
    public float distanceSquared(Vec2f vec)
    {
        float x1 = vec.x - this.x;
        float y1 = vec.y - this.y;

        return MathUtils.fma(x1, x1, y1 * y1);
    }
}
