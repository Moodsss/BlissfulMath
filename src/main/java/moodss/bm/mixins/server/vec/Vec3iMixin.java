package moodss.bm.mixins.server.vec;

import moodss.bm.MathUtils;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
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
    public Vec3i offset(Direction direction, int distance)
    {
        if(distance != 0)
        {
            return new Vec3i(
                    MathUtils.fma(direction.getOffsetX(), distance, this.x),
                    MathUtils.fma(direction.getOffsetY(), distance, this.y),
                    MathUtils.fma(direction.getOffsetZ(), distance, this.z));
        }

        return (Vec3i) (Object) this;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public Vec3i crossProduct(Vec3i vec)
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
    public double getSquaredDistanceFromCenter(double x, double y, double z)
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
    public double getSquaredDistance(double x, double y, double z)
    {
        double x1 = this.x - x;
        double y1 = this.y - y;
        double z1 = this.z - z;

        return MathUtils.fma(x1, x1, MathUtils.fma(y1, y1, z1 * z1));
    }
}
