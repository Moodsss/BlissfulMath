package moodss.bm.mixins.client.vec;

import com.mojang.math.Matrix3f;
import com.mojang.math.Vector3f;
import moodss.bm.MathUtils;
import moodss.bm.mixins.client.matrix.Matrix3fAccessor;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vector3f.class)
public abstract class Vec3fMixin
{
    @Shadow
    private float x;

    @Shadow
    private float y;

    @Shadow
    private float z;

    @Shadow
    public abstract void mul(float x, float y, float z);

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float dot(Vector3f other)
    {
        return MathUtils.fma(this.x, other.x(), MathUtils.fma(this.y, other.y(), this.z * other.z()));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public boolean normalize()
    {
        float dot = dot((Vector3f) (Object) this);

        if(dot < 1.0E-5)
        {
            return false;
        }

        float norm = 1F / Mth.fastInvSqrt(dot);

        this.mul(norm, norm, norm);
        return true;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void cross(Vector3f vector)
    {
        float lX = this.x;
        float lY = this.y;
        float lZ = this.z;

        float rX = vector.x();
        float rY = vector.y();
        float rZ = vector.z();

        this.x = MathUtils.fmn(lY, rZ, lZ * rY);
        this.y = MathUtils.fmn(lZ, rX, lX * rZ);
        this.z = MathUtils.fmn(lX, rY, lY * rX);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void lerp(Vector3f vector, float delta)
    {
        float amount = 1.0F - delta;

        this.x = MathUtils.fma(this.x, amount, vector.x() * delta);
        this.y = MathUtils.fma(this.y, amount, vector.y() * delta);
        this.z = MathUtils.fma(this.z, amount, vector.z() * delta);
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

        this.x = MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getM00(), x, MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getM01(), y, ((Matrix3fAccessor) (Object) matrix).getM02() * z));
        this.y = MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getM10(), x, MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getM11(), y, ((Matrix3fAccessor) (Object) matrix).getM12() * z));
        this.z = MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getM20(), x, MathUtils.fma(((Matrix3fAccessor) (Object) matrix).getM21(), y, ((Matrix3fAccessor) (Object) matrix).getM22() * z));

      //  this.x = ((Matrix3fAccessor) (Object) matrix).getA00() * x + ((Matrix3fAccessor) (Object) matrix).getA01() * y + ((Matrix3fAccessor) (Object) matrix).getA02() * z;
      //  this.y = ((Matrix3fAccessor) (Object) matrix).getA10() * x + ((Matrix3fAccessor) (Object) matrix).getA11() * y + ((Matrix3fAccessor) (Object) matrix).getA12() * z;
      //  this.z = ((Matrix3fAccessor) (Object) matrix).getA20() * x + ((Matrix3fAccessor) (Object) matrix).getA21() * y + ((Matrix3fAccessor) (Object) matrix).getA22() * z;
    }
}
