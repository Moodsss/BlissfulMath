package moodss.bm.mixins.server;

import moodss.bm.MathUtils;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Box.class)
public class BoxMixin
{
    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    @Nullable
    private static Direction traceCollisionSide(double[] traceDistanceResult, @Nullable Direction approachDirection,
                                                double deltaX, double deltaY, double deltaZ, double begin,
                                                double minX, double maxX,
                                                double minZ, double maxZ,
                                                Direction resultDirection,
                                                double startX, double startY, double startZ)
    {
        double d = (begin - startX) / deltaX;

        double y = MathUtils.fma(d, deltaY, startY);
        double z = MathUtils.fma(d, deltaZ, startZ);

        double threshold = 1.0E-7;

        if (0.0 < d && d < traceDistanceResult[0] && minX - threshold < y && y < maxX + threshold && minZ - threshold < z && z < maxZ + threshold)
        {
            traceDistanceResult[0] = d;
            return resultDirection;
        }

        return approachDirection;
    }
}
