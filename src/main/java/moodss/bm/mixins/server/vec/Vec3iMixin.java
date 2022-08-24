package moodss.bm.mixins.server.vec;

import moodss.bm.MathUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Vec3i.class)
public class Vec3iMixin
{
    @Shadow
    private int x;

    @Shadow
    private int y;

    @Shadow
    private int z;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3i relative(Direction direction, int distance)
    {
        if(distance != 0)
        {
            return new Vec3i(
                    MathUtils.fma(direction.getStepX(), distance, this.x),
                    MathUtils.fma(direction.getStepY(), distance, this.y),
                    MathUtils.fma(direction.getStepZ(), distance, this.z));
        }

        return (Vec3i) (Object) this;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3i cross(Vec3i vec)
    {
        return new Vec3i(
                MathUtils.fmn(this.y, vec.getZ(), this.z * vec.getY()),
                MathUtils.fmn(this.z, vec.getX(), this.x * vec.getZ()),
                MathUtils.fmn(this.x, vec.getY(), this.y * vec.getX())
        );
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double distToCenterSqr(double x, double y, double z)
    {
        double x1 = this.x + 0.5D - x;
        double y1 = this.y + 0.5D - y;
        double z1 = this.z + 0.5D - z;

        return MathUtils.fma(x1, x1, MathUtils.fma(y1, y1, z1 * z1));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double distToLowCornerSqr(double x, double y, double z)
    {
        double x1 = this.x - x;
        double y1 = this.y - y;
        double z1 = this.z - z;

        return MathUtils.fma(x1, x1, MathUtils.fma(y1, y1, z1 * z1));
    }
}
