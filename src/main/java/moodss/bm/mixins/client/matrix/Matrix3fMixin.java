package moodss.bm.mixins.client.matrix;

import com.mojang.math.Matrix3f;
import moodss.bm.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Matrix3f.class)
public class Matrix3fMixin
{
    @Shadow
    protected float m00;

    @Shadow
    protected float m01;

    @Shadow
    protected float m02;


    @Shadow
    protected float m10;

    @Shadow
    protected float m11;

    @Shadow
    protected float m12;


    @Shadow
    protected float m20;

    @Shadow
    protected float m21;

    @Shadow
    protected float m22;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float determinant()
    {
        float x = MathUtils.fmn(this.m11, this.m22, this.m12 * this.m21);
        float y = -MathUtils.fmn(this.m10, this.m22, this.m12 * this.m20);
        float z = MathUtils.fmn(this.m10, this.m21, this.m11 * this.m20);

        return MathUtils.fma(this.m00, x, MathUtils.fmn(this.m01, y, this.m02 * z));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float adjugateAndDet()
    {
        float x1 = MathUtils.fmn(this.m11, this.m22, this.m12 * this.m21);
        float y1 = -MathUtils.fmn(this.m10, this.m22, this.m12 * this.m20);
        float z1 = MathUtils.fmn(this.m10, this.m21, this.m11 * this.m20);

        float x2 = -MathUtils.fmn(this.m01, this.m22, this.m02 * this.m21);
        float y2 = MathUtils.fmn(this.m00, this.m22, this.m02 * this.m20);
        float z2 = -MathUtils.fmn(this.m00, this.m21, this.m01 * this.m20);

        float x3 = MathUtils.fmn(this.m01, this.m12, this.m02 * this.m11);
        float y3 = -MathUtils.fmn(this.m00, this.m12, this.m02 * this.m10);
        float z3 = MathUtils.fmn(this.m00, this.m11, this.m01 * this.m10);

        float determinant = MathUtils.fma(this.m00, x1, MathUtils.fmn(this.m01, y1, this.m02 * z1));

        this.m00 = x1;
        this.m10 = y1;
        this.m20 = z1;

        this.m01 = x2;
        this.m11 = y2;
        this.m21 = z2;

        this.m02 = x3;
        this.m12 = y3;
        this.m22 = z3;

        return determinant;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void mul(Matrix3f other)
    {
        float x1 = MathUtils.fma(this.m00, ((Matrix3fAccessor) (Object) other).getM00(), MathUtils.fma(this.m01,
                        ((Matrix3fAccessor) (Object) other).getM10(), this.m02 * ((Matrix3fAccessor) (Object) other).getM20()));

        float y1 = MathUtils.fma(this.m00, ((Matrix3fAccessor) (Object) other).getM01(), MathUtils.fma(this.m01,
                ((Matrix3fAccessor) (Object) other).getM11(), this.m02 * ((Matrix3fAccessor) (Object) other).getM21()));

        float z1 = MathUtils.fma(this.m00, ((Matrix3fAccessor) (Object) other).getM02(), MathUtils.fma(this.m01,
                ((Matrix3fAccessor) (Object) other).getM11(), this.m02 * ((Matrix3fAccessor) (Object) other).getM22()));

        float x2 = MathUtils.fma(this.m10, ((Matrix3fAccessor) (Object) other).getM00(), MathUtils.fma(this.m11,
                ((Matrix3fAccessor) (Object) other).getM10(), this.m12 * ((Matrix3fAccessor) (Object) other).getM20()));

        float y2 = MathUtils.fma(this.m10, ((Matrix3fAccessor) (Object) other).getM01(), MathUtils.fma(this.m11,
                ((Matrix3fAccessor) (Object) other).getM11(), this.m12 * ((Matrix3fAccessor) (Object) other).getM21()));

        float z2 = MathUtils.fma(this.m10, ((Matrix3fAccessor) (Object) other).getM02(), MathUtils.fma(this.m11,
                ((Matrix3fAccessor) (Object) other).getM12(), this.m12 * ((Matrix3fAccessor) (Object) other).getM22()));

        float x3 = MathUtils.fma(this.m20, ((Matrix3fAccessor) (Object) other).getM00(), MathUtils.fma(this.m21,
                ((Matrix3fAccessor) (Object) other).getM10(), this.m22 * ((Matrix3fAccessor) (Object) other).getM20()));

        float y3 = MathUtils.fma(this.m20, ((Matrix3fAccessor) (Object) other).getM01(), MathUtils.fma(this.m21,
                ((Matrix3fAccessor) (Object) other).getM11(), this.m22 * ((Matrix3fAccessor) (Object) other).getM21()));

        float z3 = MathUtils.fma(this.m20, ((Matrix3fAccessor) (Object) other).getM02(), MathUtils.fma(this.m21,
                ((Matrix3fAccessor) (Object) other).getM12(), this.m22 * ((Matrix3fAccessor) (Object) other).getM22()));

        this.m00 = x1;
        this.m01 = y1;
        this.m02 = z1;

        this.m10 = x2;
        this.m11 = y2;
        this.m12 = z2;

        this.m20 = x3;
        this.m21 = y3;
        this.m22 = z3;
    }
}
