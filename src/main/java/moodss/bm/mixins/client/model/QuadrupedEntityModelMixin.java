package moodss.bm.mixins.client.model;

import moodss.bm.MathUtils;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.*;

@Mixin(QuadrupedModel.class)
public abstract class QuadrupedEntityModelMixin<T extends Entity> extends AgeableListModel<T>
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
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
    {
        float halfPi = (float) MathUtils.dividePi(180D);

        this.head.yRot = headPitch * halfPi;
        this.head.zRot = headYaw * halfPi;

        float upperPitch = Mth.cos(limbAngle * NORM) * 1.4F * limbDistance;
        float lowerPitch = Mth.cos((float) MathUtils.fma(limbAngle, NORM, Math.PI)) * 1.4F * limbDistance;

        this.rightHindLeg.xRot = upperPitch;
        this.rightFrontLeg.xRot = lowerPitch;

        this.leftHindLeg.xRot = lowerPitch;
        this.leftFrontLeg.xRot = upperPitch;
    }
}
