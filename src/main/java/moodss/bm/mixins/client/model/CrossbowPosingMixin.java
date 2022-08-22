package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CrossbowPosing.class)
public abstract class CrossbowPosingMixin
{
    @Shadow
    public static void swingArms(ModelPart leftArm, ModelPart rightArm, float animationProgress)
    {}

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static <T extends MobEntity> void meleeAttack(ModelPart leftArm, ModelPart rightArm, T actor, float swingProgress, float animationProgress)
    {
        float swingingProgress = MathHelper.sin((float) (swingProgress * Math.PI));
        float swingingProgressNorm = MathHelper.sin((float) MathUtils.multiplyPi((1.0F - (1.0F - swingingProgress) * (1.0F - swingingProgress))));

        rightArm.roll = 0F;
        leftArm.roll = 0F;

        leftArm.yaw = MathHelper.HALF_PI;
        rightArm.yaw = -MathHelper.HALF_PI;

        if (actor.getMainArm() == Arm.RIGHT)
        {
            leftArm.pitch = MathUtils.fma(MathHelper.cos(animationProgress * 0.09F), 0.15F, -1.8849558F);
            rightArm.pitch = MathHelper.cos(animationProgress * 0.19F) * 0.5F;
            leftArm.pitch += MathUtils.fmn(swingingProgress, 2.2F, swingingProgressNorm * 0.4F);
            rightArm.pitch += MathUtils.fmn(swingingProgress, 2.2F, swingingProgressNorm * 0.4F);
        }
        else
        {
            leftArm.pitch = MathHelper.cos(animationProgress * 0.19F) * 0.5F;
            rightArm.pitch = MathUtils.fma(MathHelper.cos(animationProgress * 0.09F), 0.15F, -1.8849558F);
            leftArm.pitch += MathUtils.fmn(swingingProgress, 1.2F, swingingProgressNorm * 0.4F);
            rightArm.pitch += MathUtils.fmn(swingingProgress, 1.2F, swingingProgressNorm * 0.4F);
        }

        swingArms(leftArm, rightArm, animationProgress);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static void meleeAttack(ModelPart leftArm, ModelPart rightArm, boolean attacking, float swingProgress, float animationProgress)
    {
        float swingingProgress = MathHelper.sin((float) (swingProgress * Math.PI));
        float swingingProgressNorm = MathHelper.sin((float) MathUtils.multiplyPi((1.0F - (1.0F - swingingProgress) * (1.0F - swingingProgress))));

        rightArm.roll = 0F;
        leftArm.roll = 0F;

        float yaw = MathUtils.fmn(swingingProgress, 0.6F, 0.1F);

        rightArm.yaw = -yaw;
        leftArm.yaw = yaw;

        float pitch = (float) -MathUtils.dividePi(attacking ? 1.5F : 2.25F);

        rightArm.pitch = pitch;
        leftArm.pitch = pitch;

        float nextPitch = MathUtils.fmn(swingingProgress, 1.2F, swingingProgressNorm * 0.4F);

        rightArm.pitch += nextPitch;
        leftArm.pitch += nextPitch;

        swingArms(rightArm, leftArm, animationProgress);
    }
}
