package moodss.bm.mixins.client.vec;

import moodss.bm.MathUtils;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vec3d.class)
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
    public Vec3d normalize()
    {
        double length = this.length();

        if(length < 1.0E-4)
        {
            return Vec3d.ZERO;
        }

        double norm = 1F / length;

        return new Vec3d(this.x * norm, this.y * norm, this.z * norm);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double dotProduct(Vec3d vec)
    {
        return MathUtils.fma(this.x, vec.x, MathUtils.fma(this.y, vec.y, this.z * vec.z));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3d crossProduct(Vec3d vec)
    {
        return new Vec3d(
                MathUtils.fmn(this.y, vec.z, this.z * vec.y),
                MathUtils.fmn(this.z, vec.x, this.x * vec.z),
                MathUtils.fmn(this.x, vec.y, this.y * vec.x));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double distanceTo(Vec3d vec)
    {
        return Math.sqrt(this.squaredDistanceTo(vec));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double squaredDistanceTo(Vec3d vec)
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
    public double squaredDistanceTo(double x, double y, double z)
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
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double lengthSquared()
    {
        return this.dotProduct((Vec3d) (Object) this);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double horizontalLength()
    {
        return Math.sqrt(this.horizontalLengthSquared());
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double horizontalLengthSquared()
    {
        return MathUtils.fma(this.x, this.x, this.z * this.z);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3d rotateX(float angle)
    {
        float cosAngle = MathHelper.cos(angle);
        float sinAngle = MathHelper.sin(angle);

        double x = this.x;
        double y = MathUtils.fma(this.y, cosAngle, this.z * sinAngle);
        double z = MathUtils.fmn(this.z, cosAngle, this.y * sinAngle);

        return new Vec3d(x, y, z);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3d rotateY(float angle)
    {
        float cosAngle = MathHelper.cos(angle);
        float sinAngle = MathHelper.sin(angle);

        double x = MathUtils.fma(this.x, cosAngle, this.z * sinAngle);
        double y = this.y;
        double z = MathUtils.fmn(this.z, cosAngle, this.x * sinAngle);

        return new Vec3d(x, y, z);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3d rotateZ(float angle)
    {
        float cosAngle = MathHelper.cos(angle);
        float sinAngle = MathHelper.sin(angle);

        double x = MathUtils.fma(this.x, cosAngle, this.y * sinAngle);
        double y = MathUtils.fmn(this.y, cosAngle, this.x * sinAngle);
        double z = this.z;

        return new Vec3d(x, y, z);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3d withBias(Direction direction, double value)
    {
        double x1 = MathUtils.fma(value, direction.getOffsetX(), this.x);
        double y1 = MathUtils.fma(value, direction.getOffsetY(), this.y);
        double z1 = MathUtils.fma(value, direction.getOffsetZ(), this.z);

        return new Vec3d(x1, y1, z1);
    }
}
