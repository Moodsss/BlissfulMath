package moodss.bm.mixins.client;

import moodss.bm.MathUtils;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ParticleUtil.class)
public class ParticleUtilMixin
{
    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static void spawnParticle(Direction.Axis axis, World world, BlockPos pos, double variance, ParticleEffect effect, UniformIntProvider range)
    {
        Vec3d vec = Vec3d.ofCenter(pos);
        int distance = range.get(world.random);

        boolean offsetX = axis == Direction.Axis.X;
        boolean offsetY = axis == Direction.Axis.Y;
        boolean offsetZ = axis == Direction.Axis.Z;

        for(int i = 0; i < distance; i++)
        {
            double x = MathUtils.fma(offsetX ? 0.5 : variance, MathHelper.nextDouble(world.random, -1.0, 1.0), vec.getX());
            double y = MathUtils.fma(offsetY ? 0.5 : variance, MathHelper.nextDouble(world.random, -1.0, 1.0), vec.getY());
            double z = MathUtils.fma(offsetZ ? 0.5 : variance, MathHelper.nextDouble(world.random, -1.0, 1.0), vec.getZ());

            double motionX = offsetX ? MathHelper.nextDouble(world.random, -1.0, 1.0) : 0D;
            double motionY = offsetY ? MathHelper.nextDouble(world.random, -1.0, 1.0) : 0D;
            double motionZ = offsetZ ? MathHelper.nextDouble(world.random, -1.0, 1.0) : 0D;

            world.addParticle(effect,
                    x, y, z,
                    motionX, motionY, motionZ);
        }
    }
}
