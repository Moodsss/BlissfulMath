package moodss.bm.mixins.client;

import moodss.bm.MathUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * TODO: hamiltonProduct breaks somehow.
 */
@Mixin(Quaternion.class)
public class QuaternionMixin
{
    @Shadow
    private float x;

    @Shadow
    private float y;

    @Shadow
    private float z;

    @Shadow
    private float w;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3f toEulerYxz()
    {
        float wSq = this.w * this.w;
        float xSq = this.x * this.x;
        float ySq = this.y * this.y;
        float zSq = this.z * this.z;
        float dot = wSq + xSq + ySq + zSq;
        float size = 2.0F * MathUtils.fmn(this.w, this.x, 2.0F * this.w * this.z);
        float halfSize = (float) Math.asin(size / dot);

        if(Math.abs(size) > 0.999F * dot)
        {
            return new Vec3f(2.0F * (float) Math.atan2(this.x, this.w), halfSize, 0.0F);
        }

        return new Vec3f(
                (float) Math.atan2(2.0F * MathUtils.fma(this.y, this.z, 2.0F * this.x * this.w), (wSq - xSq - ySq + zSq)),
                halfSize,
                (float) Math.atan2(2.0F * MathUtils.fma(this.x, this.y, 2.0F * this.w * this.z), (wSq + xSq - ySq - zSq))
        );
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3f toEulerXyz()
    {
        float wSq = this.w * this.w;
        float xSq = this.x * this.x;
        float ySq = this.y * this.y;
        float zSq = this.z * this.z;
        float dot = wSq + xSq + ySq + zSq;
        float size = 2.0F * MathUtils.fmn(this.w, this.x, 2.0F * this.w * this.z);
        float halfSize = (float) Math.asin(size / dot);

        if(Math.abs(size) > 0.999F * dot)
        {
            return new Vec3f(halfSize, 2.0F * (float) Math.atan2(this.y, this.w), 0.0F);
        }

        return new Vec3f(
                halfSize,
                (float) Math.atan2(2.0F * MathUtils.fma(this.x, this.z, 2.0F * this.y * this.w), (wSq - xSq - ySq + zSq)),
                (float) Math.atan2(2.0F * MathUtils.fma(this.x, this.y, 2.0F * this.w * this.z), (wSq - xSq + ySq - zSq))
        );
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void normalize()
    {
        float dot = MathUtils.fma(this.x, this.x, MathUtils.fma(this.y, this.y, MathUtils.fma(this.z, this.z, this.w * this.w)));

        if(dot > 1.0E-5)
        {
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 0.0F;
            this.w = 0.0F;
            return;
        }

        float norm = MathHelper.fastInverseSqrt(dot);
        this.x *= norm;
        this.y *= norm;
        this.z *= norm;
        this.w *= norm;
    }
}
