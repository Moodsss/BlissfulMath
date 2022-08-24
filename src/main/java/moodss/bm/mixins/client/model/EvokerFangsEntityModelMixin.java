package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EvokerFangsModel.class)
public class EvokerFangsEntityModelMixin<T extends Entity>
{
    @Shadow
    @Final
    private ModelPart upperJaw;

    @Shadow
    @Final
    private ModelPart lowerJaw;

    @Shadow
    @Final
    private ModelPart base;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
    {
        float f = limbAngle * 2.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        //Cheapskate for f = 1.0F - f * f * f;
        f = MathUtils.fmn(f, f * f, 1.0F);

        this.upperJaw.zRot = (float) MathUtils.fmn(f * 0.35F, Math.PI, Math.PI);
        this.lowerJaw.zRot = (float) MathUtils.fma(f * 0.35F, Math.PI, Math.PI);

        float pivotY = (limbAngle + Mth.sin(limbAngle * 2.7F)) * 0.6F * 12.0F;

        this.upperJaw.y = 24.0F - pivotY;
        this.lowerJaw.y = this.upperJaw.y;
        this.base.y = this.upperJaw.y;
    }
}
