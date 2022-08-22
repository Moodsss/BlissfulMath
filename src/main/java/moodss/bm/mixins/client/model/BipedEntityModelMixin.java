package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T>
{
    @Shadow
    protected abstract Arm getPreferredArm(T entity);

    @Shadow
    protected abstract ModelPart getArm(Arm arm);

    @Shadow
    @Final
    public ModelPart body;

    @Shadow
    @Final
    public ModelPart rightArm;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Shadow
    @Final
    public ModelPart head;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void animateArms(T entity, float animationProgress)
    {
        if(this.handSwingProgress < 0)
        {
            return;
        }

        Arm arm = this.getPreferredArm(entity);
        ModelPart part = this.getArm(arm);

        float handSwingingProgress = this.handSwingProgress;
        float handSwingingProgressSq = MathHelper.sqrt(handSwingingProgress);

        float bodyYawSin = MathHelper.sin(this.body.yaw) * 5.0F;
        float bodyYawCos = MathHelper.cos(this.body.yaw) * 5.0F;

        this.body.yaw = MathHelper.sin(handSwingingProgressSq * MathHelper.TAU) * 0.2F;

        if(arm == Arm.LEFT)
        {
            this.body.yaw *= -1F;
        }

        this.rightArm.pivotZ = bodyYawSin;
        this.rightArm.pivotX = -bodyYawCos;

        this.leftArm.pivotZ = -bodyYawSin;
        this.leftArm.pivotX = bodyYawCos;

        this.rightArm.yaw += this.body.yaw;
        this.leftArm.yaw += this.body.yaw;

        this.leftArm.pitch += this.body.yaw;

        handSwingingProgress = 1F - this.handSwingProgress;
        handSwingingProgress *= handSwingingProgress * handSwingingProgress;
        handSwingingProgress = 1F - handSwingingProgress;

        float handSwingingProgressPiSin = MathHelper.sin(handSwingingProgress * MathHelper.TAU);
        float handSwingingProgressWithHead = MathHelper.sin(this.handSwingProgress * MathHelper.TAU) * -(this.head.pitch - 0.7F) * 0.75F;

        part.pitch -= MathUtils.fma(handSwingingProgressPiSin, 1.2F, handSwingingProgressWithHead);
        part.yaw += this.body.yaw * 2F;
        part.roll += MathHelper.sin(this.handSwingProgress * MathHelper.TAU) * -0.4F;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float lerpAngle(float angleOne, float angleTwo, float magnitude)
    {
        float f = (magnitude - angleTwo) % MathHelper.TAU;

        if (f < -Math.PI)
        {
            f += MathHelper.TAU;
        }

        if (f >= Math.PI)
        {
            f -= MathHelper.TAU;
        }

        return MathUtils.fma(angleOne, f, angleTwo);
    }
    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    private float method_2807(float f)
    {
        return MathUtils.fma(-65F, f, f * f);
    }
}
