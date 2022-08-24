package moodss.bm.mixins.server;

import moodss.bm.MathUtils;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Direction.class)
public class DirectionMixin
{
    @Shadow
    @Final
    private static Direction[] VALUES;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static Direction getNearest(float x, float y, float z)
    {
        Direction currentFacing = Direction.NORTH;
        float prevRotation = Float.MIN_VALUE;

        for(Direction direction : VALUES)
        {
            float rotation = MathUtils.fma(x, direction.getStepX(), MathUtils.fma(y, direction.getStepY(), z * direction.getStepZ()));

            if(rotation > prevRotation)
            {
                prevRotation = rotation;
                currentFacing = direction;
            }
        }

        return currentFacing;
    }
}
