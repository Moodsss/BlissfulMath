package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AnimationUtils.class)
public abstract class CrossbowPosingMixin
{
    @Shadow
    public static void bobArms(ModelPart leftArm, ModelPart rightArm, float animationProgress)
    {}

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static <T extends Mob> void swingWeaponDown(ModelPart leftArm, ModelPart rightArm, T actor, float swingProgress, float animationProgress)
    {
        float swingingProgress = Mth.sin((float) (swingProgress * Math.PI));
        float swingingProgressNorm = Mth.sin((float) MathUtils.multiplyPi((1.0F - (1.0F - swingingProgress) * (1.0F - swingingProgress))));

        rightArm.zRot = 0F;
        leftArm.zRot = 0F;

        leftArm.yRot = Mth.HALF_PI;
        rightArm.yRot = -Mth.HALF_PI;

        if (actor.getMainArm() == HumanoidArm.RIGHT)
        {
            leftArm.xRot = MathUtils.fma(Mth.cos(animationProgress * 0.09F), 0.15F, -1.8849558F);
            rightArm.xRot = Mth.cos(animationProgress * 0.19F) * 0.5F;
            leftArm.xRot += MathUtils.fmn(swingingProgress, 2.2F, swingingProgressNorm * 0.4F);
            rightArm.xRot += MathUtils.fmn(swingingProgress, 2.2F, swingingProgressNorm * 0.4F);
        }
        else
        {
            leftArm.xRot = Mth.cos(animationProgress * 0.19F) * 0.5F;
            rightArm.xRot = MathUtils.fma(Mth.cos(animationProgress * 0.09F), 0.15F, -1.8849558F);
            leftArm.xRot += MathUtils.fmn(swingingProgress, 1.2F, swingingProgressNorm * 0.4F);
            rightArm.xRot += MathUtils.fmn(swingingProgress, 1.2F, swingingProgressNorm * 0.4F);
        }

        bobArms(leftArm, rightArm, animationProgress);
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public static void animateZombieArms(ModelPart leftArm, ModelPart rightArm, boolean attacking, float swingProgress, float animationProgress)
    {
        float swingingProgress = Mth.sin((float) (swingProgress * Math.PI));
        float swingingProgressNorm = Mth.sin((float) MathUtils.multiplyPi((1.0F - (1.0F - swingingProgress) * (1.0F - swingingProgress))));

        rightArm.zRot = 0F;
        leftArm.zRot = 0F;

        float yaw = MathUtils.fmn(swingingProgress, 0.6F, 0.1F);

        rightArm.yRot = -yaw;
        leftArm.yRot = yaw;

        float pitch = (float) -MathUtils.dividePi(attacking ? 1.5F : 2.25F);

        rightArm.xRot = pitch;
        leftArm.xRot = pitch;

        float nextPitch = MathUtils.fmn(swingingProgress, 1.2F, swingingProgressNorm * 0.4F);

        rightArm.xRot += nextPitch;
        leftArm.xRot += nextPitch;

        bobArms(rightArm, leftArm, animationProgress);
    }
}
