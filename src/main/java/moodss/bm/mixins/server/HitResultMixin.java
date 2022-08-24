package moodss.bm.mixins.server;

import moodss.bm.MathUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HitResult.class)
public class HitResultMixin
{
    @Shadow
    @Final
    protected Vec3 location;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double distanceTo(Entity entity)
    {
        double x1 = this.location.x - entity.getX();
        double y1 = this.location.y - entity.getY();
        double z1 = this.location.z - entity.getZ();

        return MathUtils.fma(x1, x1, MathUtils.fma(y1, y1, z1 * z1));
    }
}
