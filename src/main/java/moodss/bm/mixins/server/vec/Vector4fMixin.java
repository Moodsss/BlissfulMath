package moodss.bm.mixins.server.vec;

import moodss.bm.MathUtils;
import moodss.bm.mixins.client.matrix.Matrix4fAccessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vector4f;
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
    public float dotProduct(Vector4f other)
    {
        return MathUtils.fma(this.x, other.getX(), MathUtils.fma(this.y, other.getY(), MathUtils.fma(this.z, other.getZ(), this.w * other.getW())));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public boolean normalize()
    {
        float dot = this.dotProduct((Vector4f) (Object) this);

        if (dot < 1.0E-5)
        {
            return false;
        }

        float norm = 1F / MathHelper.fastInverseSqrt(dot);
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

        this.x = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA00(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA01(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA02(), z, ((Matrix4fAccessor) (Object) matrix).getA03() * w)));

        this.y = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA10(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA11(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA12(), z, ((Matrix4fAccessor) (Object) matrix).getA13() * w)));

        this.z = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA20(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA21(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA22(), z, ((Matrix4fAccessor) (Object) matrix).getA23() * w)));

        this.w = MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA30(), x,
                MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA31(), y,
                        MathUtils.fma(((Matrix4fAccessor) (Object) matrix).getA32(), z, ((Matrix4fAccessor) (Object) matrix).getA33() * w)));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void lerp(Vector4f to, float delta)
    {
        float amount = 1.0F - delta;

        this.x = MathUtils.fma(this.x, amount, to.getX() * delta);
        this.y = MathUtils.fma(this.y, amount, to.getY() * delta);
        this.z = MathUtils.fma(this.z, amount, to.getZ() * delta);
        this.w = MathUtils.fma(this.w, amount, to.getW() * delta);
    }
}
