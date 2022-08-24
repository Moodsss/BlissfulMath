package moodss.bm.mixins.client.vec;

import moodss.bm.MathUtils;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vec3.class)
public class Vec3dMixin
{
    @Shadow
    @Final
    public double x;

    @Shadow
    @Final
    public double z;

    @Shadow
    @Final
    public double y;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3 normalize()
    {
        double length = this.length();

        if(length < 1.0E-4)
        {
            return Vec3.ZERO;
        }

        double norm = 1F / length;

        return new Vec3(this.x * norm, this.y * norm, this.z * norm);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double dot(Vec3 vec)
    {
        return MathUtils.fma(this.x, vec.x, MathUtils.fma(this.y, vec.y, this.z * vec.z));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3 cross(Vec3 vec)
    {
        return new Vec3(
                MathUtils.fmn(this.y, vec.z, this.z * vec.y),
                MathUtils.fmn(this.z, vec.x, this.x * vec.z),
                MathUtils.fmn(this.x, vec.y, this.y * vec.x));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double distanceTo(Vec3 vec)
    {
        return Math.sqrt(this.distanceToSqr(vec));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double distanceToSqr(Vec3 vec)
    {
        double x1 = vec.x - this.x;
        double y1 = vec.y - this.y;
        double z1 = vec.z - this.z;

        return MathUtils.fma(x1, x1, MathUtils.fma(y1, y1, z1 * z1));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double distanceToSqr(double x, double y, double z)
    {
        double x1 = x - this.x;
        double y1 = y - this.y;
        double z1 = z - this.z;

        return MathUtils.fma(x1, x1, MathUtils.fma(y1, y1, z1 * z1));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double length()
    {
        return Math.sqrt(this.lengthSqr());
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double lengthSqr()
    {
        return this.dot((Vec3) (Object) this);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double horizontalDistance()
    {
        return Math.sqrt(this.horizontalDistanceSqr());
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double horizontalDistanceSqr()
    {
        return MathUtils.fma(this.x, this.x, this.z * this.z);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3 xRot(float angle)
    {
        float cosAngle = Mth.cos(angle);
        float sinAngle = Mth.sin(angle);

        double x = this.x;
        double y = MathUtils.fma(this.y, cosAngle, this.z * sinAngle);
        double z = MathUtils.fmn(this.z, cosAngle, this.y * sinAngle);

        return new Vec3(x, y, z);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3 yRot(float angle)
    {
        float cosAngle = Mth.cos(angle);
        float sinAngle = Mth.sin(angle);

        double x = MathUtils.fma(this.x, cosAngle, this.z * sinAngle);
        double y = this.y;
        double z = MathUtils.fmn(this.z, cosAngle, this.x * sinAngle);

        return new Vec3(x, y, z);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3 zRot(float angle)
    {
        float cosAngle = Mth.cos(angle);
        float sinAngle = Mth.sin(angle);

        double x = MathUtils.fma(this.x, cosAngle, this.y * sinAngle);
        double y = MathUtils.fmn(this.y, cosAngle, this.x * sinAngle);
        double z = this.z;

        return new Vec3(x, y, z);
    }
}
