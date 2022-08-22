package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;

@Mixin(QuadrupedEntityModel.class)
public abstract class QuadrupedEntityModelMixin<T extends Entity> extends AnimalModel<T>
{
    @Shadow
    @Final
    protected ModelPart head;

    @Shadow
    @Final
    protected ModelPart rightHindLeg;

    @Shadow
    @Final
    protected ModelPart leftHindLeg;

    @Shadow
    @Final
    protected ModelPart rightFrontLeg;

    @Shadow
    @Final
    protected ModelPart leftFrontLeg;

    @Unique
    private static final float NORM = 0.6662F;

    /**
     * @author Mo0dss
     * @reason FMA & shortcuts
     */
    @Overwrite
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
    {
        float halfPi = (float) MathUtils.dividePi(180D);

        this.head.pitch = headPitch * halfPi;
        this.head.yaw = headYaw * halfPi;

        float upperPitch = MathHelper.cos(limbAngle * NORM) * 1.4F * limbDistance;
        float lowerPitch = MathHelper.cos((float) MathUtils.fma(limbAngle, NORM, Math.PI)) * 1.4F * limbDistance;

        this.rightHindLeg.pitch = upperPitch;
        this.rightFrontLeg.pitch = lowerPitch;

        this.leftHindLeg.pitch = lowerPitch;
        this.leftFrontLeg.pitch = upperPitch;
    }
}
