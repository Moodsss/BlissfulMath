package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EvokerFangsEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EvokerFangsEntityModel.class)
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
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
    {
        float f = limbAngle * 2.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        //Cheapskate for f = 1.0F - f * f * f;
        f = MathUtils.fmn(f, f * f, 1.0F);

        this.upperJaw.roll = (float) MathUtils.fmn(f * 0.35F, Math.PI, Math.PI);
        this.lowerJaw.roll = (float) MathUtils.fma(f * 0.35F, Math.PI, Math.PI);

        float pivotY = (limbAngle + MathHelper.sin(limbAngle * 2.7F)) * 0.6F * 12.0F;

        this.upperJaw.pivotY = 24.0F - pivotY;
        this.lowerJaw.pivotY = this.upperJaw.pivotY;
        this.base.pivotY = this.upperJaw.pivotY;
    }
}
