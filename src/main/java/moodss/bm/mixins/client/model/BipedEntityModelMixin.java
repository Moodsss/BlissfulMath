package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HumanoidModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AgeableListModel<T>
{
    @Shadow
    protected abstract HumanoidArm getAttackArm(T entity);

    @Shadow
    protected abstract ModelPart getArm(HumanoidArm arm);

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
    public void setupAttackAnimation(T entity, float animationProgress)
    {
        if(this.attackTime < 0)
        {
            return;
        }

        HumanoidArm arm = this.getAttackArm(entity);
        ModelPart part = this.getArm(arm);

        float handSwingingProgress = this.attackTime;
        float handSwingingProgressSq = Mth.sqrt(handSwingingProgress);

        float bodyYawSin = Mth.sin(this.body.yRot) * 5.0F;
        float bodyYawCos = Mth.cos(this.body.yRot) * 5.0F;

        this.body.yRot = Mth.sin(handSwingingProgressSq * Mth.HALF_PI) * 0.2F;

        if(arm == HumanoidArm.LEFT)
        {
            this.body.yRot *= -1F;
        }

        this.rightArm.z = bodyYawSin;
        this.rightArm.x = -bodyYawCos;

        this.leftArm.z = -bodyYawSin;
        this.leftArm.x = bodyYawCos;

        this.rightArm.yRot += this.body.yRot;
        this.leftArm.yRot += this.body.yRot;

        this.leftArm.xRot += this.body.yRot;

        handSwingingProgress = 1F - this.attackTime;
        handSwingingProgress *= handSwingingProgress * handSwingingProgress;
        handSwingingProgress = 1F - handSwingingProgress;

        float handSwingingProgressPiSin = Mth.sin(handSwingingProgress * Mth.HALF_PI);
        float handSwingingProgressWithHead = Mth.sin(this.attackTime * Mth.HALF_PI) * -(this.head.xRot - 0.7F) * 0.75F;

        part.xRot -= MathUtils.fma(handSwingingProgressPiSin, 1.2F, handSwingingProgressWithHead);
        part.yRot += this.body.yRot * 2F;
        part.zRot += Mth.sin(this.attackTime * Mth.HALF_PI) * -0.4F;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float rotlerpRad(float angleOne, float angleTwo, float magnitude)
    {
        float f = (magnitude - angleTwo) % Mth.HALF_PI;

        if (f < -Math.PI)
        {
            f += Mth.HALF_PI;
        }

        if (f >= Math.PI)
        {
            f -= Mth.HALF_PI;
        }

        return MathUtils.fma(angleOne, f, angleTwo);
    }
    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    private float quadraticArmUpdate(float f)
    {
        return MathUtils.fma(-65F, f, f * f);
    }
}
