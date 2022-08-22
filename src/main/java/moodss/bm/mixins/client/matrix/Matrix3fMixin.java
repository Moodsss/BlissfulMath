package moodss.bm.mixins.client.matrix;

import moodss.bm.MathUtils;
import net.minecraft.util.math.Matrix3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Matrix3f.class)
public class Matrix3fMixin
{
    @Shadow
    protected float a00;

    @Shadow
    protected float a01;

    @Shadow
    protected float a02;


    @Shadow
    protected float a10;

    @Shadow
    protected float a11;

    @Shadow
    protected float a12;


    @Shadow
    protected float a20;

    @Shadow
    protected float a21;

    @Shadow
    protected float a22;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float determinant()
    {
        float x = MathUtils.fmn(this.a11, this.a22, this.a12 * this.a21);
        float y = -MathUtils.fmn(this.a10, this.a22, this.a12 * this.a20);
        float z = MathUtils.fmn(this.a10, this.a21, this.a11 * this.a20);

        return MathUtils.fma(this.a00, x, MathUtils.fmn(this.a01, y, this.a02 * z));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float determinantAndAdjugate()
    {
        float x1 = MathUtils.fmn(this.a11, this.a22, this.a12 * this.a21);
        float y1 = -MathUtils.fmn(this.a10, this.a22, this.a12 * this.a20);
        float z1 = MathUtils.fmn(this.a10, this.a21, this.a11 * this.a20);

        float x2 = -MathUtils.fmn(this.a01, this.a22, this.a02 * this.a21);
        float y2 = MathUtils.fmn(this.a00, this.a22, this.a02 * this.a20);
        float z2 = -MathUtils.fmn(this.a00, this.a21, this.a01 * this.a20);

        float x3 = MathUtils.fmn(this.a01, this.a12, this.a02 * this.a11);
        float y3 = -MathUtils.fmn(this.a00, this.a12, this.a02 * this.a10);
        float z3 = MathUtils.fmn(this.a00, this.a11, this.a01 * this.a10);

        float determinant = MathUtils.fma(this.a00, x1, MathUtils.fmn(this.a01, y1, this.a02 * z1));

        this.a00 = x1;
        this.a10 = y1;
        this.a20 = z1;

        this.a01 = x2;
        this.a11 = y2;
        this.a21 = z2;

        this.a02 = x3;
        this.a12 = y3;
        this.a22 = z3;

        return determinant;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void multiply(Matrix3f other)
    {
        float x1 = MathUtils.fma(this.a00, ((Matrix3fAccessor) (Object) other).getA00(), MathUtils.fma(this.a01,
                        ((Matrix3fAccessor) (Object) other).getA10(), this.a02 * ((Matrix3fAccessor) (Object) other).getA20()));

        float y1 = MathUtils.fma(this.a00, ((Matrix3fAccessor) (Object) other).getA01(), MathUtils.fma(this.a01,
                ((Matrix3fAccessor) (Object) other).getA11(), this.a02 * ((Matrix3fAccessor) (Object) other).getA21()));

        float z1 = MathUtils.fma(this.a00, ((Matrix3fAccessor) (Object) other).getA02(), MathUtils.fma(this.a01,
                ((Matrix3fAccessor) (Object) other).getA11(), this.a02 * ((Matrix3fAccessor) (Object) other).getA22()));

        float x2 = MathUtils.fma(this.a10, ((Matrix3fAccessor) (Object) other).getA00(), MathUtils.fma(this.a11,
                ((Matrix3fAccessor) (Object) other).getA10(), this.a12 * ((Matrix3fAccessor) (Object) other).getA20()));

        float y2 = MathUtils.fma(this.a10, ((Matrix3fAccessor) (Object) other).getA01(), MathUtils.fma(this.a11,
                ((Matrix3fAccessor) (Object) other).getA11(), this.a12 * ((Matrix3fAccessor) (Object) other).getA21()));

        float z2 = MathUtils.fma(this.a10, ((Matrix3fAccessor) (Object) other).getA02(), MathUtils.fma(this.a11,
                ((Matrix3fAccessor) (Object) other).getA12(), this.a12 * ((Matrix3fAccessor) (Object) other).getA22()));

        float x3 = MathUtils.fma(this.a20, ((Matrix3fAccessor) (Object) other).getA00(), MathUtils.fma(this.a21,
                ((Matrix3fAccessor) (Object) other).getA10(), this.a22 * ((Matrix3fAccessor) (Object) other).getA20()));

        float y3 = MathUtils.fma(this.a20, ((Matrix3fAccessor) (Object) other).getA01(), MathUtils.fma(this.a21,
                ((Matrix3fAccessor) (Object) other).getA11(), this.a22 * ((Matrix3fAccessor) (Object) other).getA21()));

        float z3 = MathUtils.fma(this.a20, ((Matrix3fAccessor) (Object) other).getA02(), MathUtils.fma(this.a21,
                ((Matrix3fAccessor) (Object) other).getA12(), this.a22 * ((Matrix3fAccessor) (Object) other).getA22()));

        this.a00 = x1;
        this.a01 = y1;
        this.a02 = z1;

        this.a10 = x2;
        this.a11 = y2;
        this.a12 = z2;

        this.a20 = x3;
        this.a21 = y3;
        this.a22 = z3;
    }
}
