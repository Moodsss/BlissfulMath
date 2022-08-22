package moodss.bm.mixins.server;

import moodss.bm.MathUtils;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Direction.class)
public class DirectionMixin
{
    @Shadow
    @Final
    private static Direction[] ALL;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static Direction getFacing(float x, float y, float z)
    {
        Direction currentFacing = Direction.NORTH;
        float prevRotation = Float.MIN_VALUE;

        for(Direction direction : ALL)
        {
            float rotation = MathUtils.fma(x, direction.getOffsetX(), MathUtils.fma(y, direction.getOffsetY(), z * direction.getOffsetZ()));

            if(rotation > prevRotation)
            {
                prevRotation = rotation;
                currentFacing = direction;
            }
        }

        return currentFacing;
    }
}
