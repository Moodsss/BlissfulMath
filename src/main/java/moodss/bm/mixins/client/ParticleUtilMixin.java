package moodss.bm.mixins.client;

import moodss.bm.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ParticleUtils.class)
public class ParticleUtilMixin
{
    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static void spawnParticlesAlongAxis(Direction.Axis axis, Level world, BlockPos pos, double variance, ParticleOptions effect, UniformInt range)
    {
        Vec3 vec = Vec3.atCenterOf(pos);
        int distance = range.sample(world.random);

        boolean offsetX = axis == Direction.Axis.X;
        boolean offsetY = axis == Direction.Axis.Y;
        boolean offsetZ = axis == Direction.Axis.Z;

        for(int i = 0; i < distance; i++)
        {
            double x = MathUtils.fma(offsetX ? 0.5 : variance, Mth.nextDouble(world.random, -1.0, 1.0), vec.x());
            double y = MathUtils.fma(offsetY ? 0.5 : variance, Mth.nextDouble(world.random, -1.0, 1.0), vec.y());
            double z = MathUtils.fma(offsetZ ? 0.5 : variance, Mth.nextDouble(world.random, -1.0, 1.0), vec.z());

            double motionX = offsetX ? Mth.nextDouble(world.random, -1.0, 1.0) : 0D;
            double motionY = offsetY ? Mth.nextDouble(world.random, -1.0, 1.0) : 0D;
            double motionZ = offsetZ ? Mth.nextDouble(world.random, -1.0, 1.0) : 0D;

            world.addParticle(effect,
                    x, y, z,
                    motionX, motionY, motionZ);
        }
    }
}
