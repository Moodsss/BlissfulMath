package moodss.bm.mixins.client.vec;

import moodss.bm.MathUtils;
import moodss.bm.mixins.client.matrix.Matrix3fAccessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vec3f.class)
public abstract class Vec3fMixin
{
    @Shadow
    private float x;

    @Shadow
    private float y;

    @Shadow
    private float z;

    @Shadow
    public abstract void multiplyComponentwise(float x, float y, float z);

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float dot(Vec3f other)
    {
        return MathUtils.fma(this.x, other.getX(), MathUtils.fma(this.y, other.getY(), this.z * other.getZ()));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public boolean normalize()
    {
        float dot = dot((Vec3f) (Object) this);

        if(dot < 1.0E-5)
        {
            return false;
        }

        float norm = 1F / MathHelper.fastInverseSqrt(dot);

        this.multiplyComponentwise(norm, norm, norm);
        return true;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void cross(Vec3f vector)
    {
        float lX = this.x;
        float lY = this.y;
        float lZ = this.z;

        float rX = vector.getX();
        float rY = vector.getY();
        float rZ = vector.getZ();

        this.x = MathUtils.fmn(lY, rZ, lZ * rY);
        this.y = MathUtils.fmn(lZ, rX, lX * rZ);
        this.z = MathUtils.fmn(lX, rY, lY * rX);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void lerp(Vec3f vector, float delta)
    {
        float amount = 1.0F - delta;

        this.x = MathUtils.fma(this.x, amount, vector.getX() * delta);
        this.y = MathUtils.fma(this.y, amount, vector.getY() * delta);
        this.z = MathUtils.fma(this.z, amount, vector.getZ() * delta);
    }

    /**
     * @author Mo0dss
     * @reason Introduce Native Memory
     */
    @Overwrite
    public void transform(Matrix3f matrix)
    {
        float x = this.x;
        float y = this.y;
        float z = this.z;

        this.x = MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getA00(), x, MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getA01(), y, ((Matrix3fAccessor) (Object) matrix).getA02() * z));
        this.y = MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getA10(), x, MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getA11(), y, ((Matrix3fAccessor) (Object) matrix).getA12() * z));
        this.z = MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getA20(), x, MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getA21(), y, ((Matrix3fAccessor) (Object) matrix).getA22() * z));

      //  this.x = ((Matrix3fAccessor) (Object) matrix).getA00() * x + ((Matrix3fAccessor) (Object) matrix).getA01() * y + ((Matrix3fAccessor) (Object) matrix).getA02() * z;
      //  this.y = ((Matrix3fAccessor) (Object) matrix).getA10() * x + ((Matrix3fAccessor) (Object) matrix).getA11() * y + ((Matrix3fAccessor) (Object) matrix).getA12() * z;
      //  this.z = ((Matrix3fAccessor) (Object) matrix).getA20() * x + ((Matrix3fAccessor) (Object) matrix).getA21() * y + ((Matrix3fAccessor) (Object) matrix).getA22() * z;
    }
}
