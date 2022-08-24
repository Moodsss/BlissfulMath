package moodss.bm.mixins.client;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import moodss.bm.MathUtils;
import net.minecraft.util.Mth;
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
    //X
    private float i;

    @Shadow
    //Y
    private float j;

    @Shadow
    //Z
    private float k;

    @Shadow
    //W
    private float r;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vector3f toYXZ()
    {
        float wSq = this.r * this.r;
        float xSq = this.i * this.i;
        float ySq = this.j * this.j;
        float zSq = this.k * this.k;
        float dot = wSq + xSq + ySq + zSq;
        float size = 2.0F * MathUtils.fmn(this.r, this.i, 2.0F * this.r * this.k);
        float halfSize = (float) Math.asin(size / dot);

        if(Math.abs(size) > 0.999F * dot)
        {
            return new Vector3f(2.0F * (float) Math.atan2(this.i, this.r), halfSize, 0.0F);
        }

        return new Vector3f(
                (float) Math.atan2(2.0F * MathUtils.fma(this.j, this.k, 2.0F * this.i * this.r), (wSq - xSq - ySq + zSq)),
                halfSize,
                (float) Math.atan2(2.0F * MathUtils.fma(this.i, this.j, 2.0F * this.r * this.k), (wSq + xSq - ySq - zSq))
        );
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vector3f toXYZ()
    {
        float wSq = this.r * this.r;
        float xSq = this.i * this.i;
        float ySq = this.j * this.j;
        float zSq = this.k * this.k;
        float dot = wSq + xSq + ySq + zSq;
        float size = 2.0F * MathUtils.fmn(this.r, this.i, 2.0F * this.r * this.k);
        float halfSize = (float) Math.asin(size / dot);

        if(Math.abs(size) > 0.999F * dot)
        {
            return new Vector3f(halfSize, 2.0F * (float) Math.atan2(this.j, this.r), 0.0F);
        }

        return new Vector3f(
                halfSize,
                (float) Math.atan2(2.0F * MathUtils.fma(this.i, this.k, 2.0F * this.j * this.r), (wSq - xSq - ySq + zSq)),
                (float) Math.atan2(2.0F * MathUtils.fma(this.i, this.j, 2.0F * this.r * this.k), (wSq - xSq + ySq - zSq))
        );
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void normalize()
    {
        float dot = MathUtils.fma(this.i, this.i, MathUtils.fma(this.j, this.j, MathUtils.fma(this.k, this.k, this.r * this.r)));

        if(dot > 1.0E-5)
        {
            this.i = 0.0F;
            this.j = 0.0F;
            this.k = 0.0F;
            this.r = 0.0F;
            return;
        }

        float norm = Mth.fastInvSqrt(dot);
        this.i *= norm;
        this.j *= norm;
        this.k *= norm;
        this.r *= norm;
    }
}
