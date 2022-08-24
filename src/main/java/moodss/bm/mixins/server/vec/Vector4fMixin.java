package moodss.bm.mixins.server.vec;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import moodss.bm.MathUtils;
import moodss.bm.mixins.client.matrix.Matrix4fAccessor;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vector4f.class)
public class Vector4fMixin
{
    @Shadow
    private float x;

    @Shadow
    private float y;

    @Shadow
    private float w;

    @Shadow
    private float z;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float dot(Vector4f other)
    {
        return MathUtils.fma(this.x, other.x(), MathUtils.fma(this.y, other.y(), MathUtils.fma(this.z, other.z(), this.w * other.w())));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public boolean normalize()
    {
        float dot = this.dot((Vector4f) (Object) this);

        if (dot < 1.0E-5)
        {
            return false;
        }

        float norm = 1F / Mth.fastInvSqrt(dot);
        this.x *= norm;
        this.y *= norm;
        this.z *= norm;
        this.w *= norm;

        return true;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void transform(Matrix4f matrix)
    {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        this.x = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM00(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM01(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM02(), z, ((Matrix4fAccessor) (Object) matrix).getM03() * w)));

        this.y = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM10(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM11(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM12(), z, ((Matrix4fAccessor) (Object) matrix).getM13() * w)));

        this.z = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM20(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM21(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM22(), z, ((Matrix4fAccessor) (Object) matrix).getM23() * w)));

        this.w = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM30(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM31(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getM32(), z, ((Matrix4fAccessor) (Object) matrix).getM33() * w)));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void lerp(Vector4f to, float delta)
    {
        float amount = 1.0F - delta;

        this.x = MathUtils.fma(this.x, amount, to.x() * delta);
        this.y = MathUtils.fma(this.y, amount, to.y() * delta);
        this.z = MathUtils.fma(this.z, amount, to.z() * delta);
        this.w = MathUtils.fma(this.w, amount, to.w() * delta);
    }
}
