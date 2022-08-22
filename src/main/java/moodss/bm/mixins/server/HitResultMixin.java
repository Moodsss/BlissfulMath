package moodss.bm.mixins.server;

import moodss.bm.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HitResult.class)
public class HitResultMixin
{
    @Shadow
    @Final
    protected Vec3d pos;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public double squaredDistanceTo(Entity entity)
    {
        double x1 = this.pos.x - entity.getX();
        double y1 = this.pos.y - entity.getY();
        double z1 = this.pos.z - entity.getZ();

        return MathUtils.fma(x1, x1, MathUtils.fma(y1, y1, z1 * z1));
    }
}
