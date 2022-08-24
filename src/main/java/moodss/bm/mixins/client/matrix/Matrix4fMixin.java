package moodss.bm.mixins.client.matrix;

import com.mojang.math.Matrix4f;
import moodss.bm.MathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Matrix4f.class)
public class Matrix4fMixin
{
    @Shadow
    protected float m00;

    @Shadow
    protected float m01;

    @Shadow
    protected float m02;

    @Shadow
    protected float m03;


    @Shadow
    protected float m10;

    @Shadow
    protected float m11;

    @Shadow
    protected float m12;

    @Shadow
    protected float m13;


    @Shadow
    protected float m20;

    @Shadow
    protected float m21;

    @Shadow
    protected float m22;

    @Shadow
    protected float m23;


    @Shadow
    protected float m30;

    @Shadow
    protected float m31;

    @Shadow
    protected float m32;

    @Shadow
    protected float m33;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float determinant()
    {
        float x1 = MathUtils.fmn(this.m00, this.m11, this.m01 * this.m10);
        float x2 = MathUtils.fmn(this.m00, this.m12, this.m02 * this.m10);
        float x3 = MathUtils.fmn(this.m00, this.m13, this.m03 * this.m10);

        float x4 = MathUtils.fmn(this.m01, this.m12, this.m02 * this.m11);
        float x5 = MathUtils.fmn(this.m01, this.m13, this.m03 * this.m11);
        float x6 = MathUtils.fmn(this.m02, this.m13, this.m03 * this.m12);

        float y1 = MathUtils.fmn(this.m20, this.m31, this.m21 * this.m30);
        float y2 = MathUtils.fmn(this.m20, this.m32, this.m22 * this.m30);
        float y3 = MathUtils.fmn(this.m20, this.m33, this.m23 * this.m30);

        float z1 = MathUtils.fmn(this.m21, this.m32, this.m22 * this.m31);
        float z2 = MathUtils.fmn(this.m21, this.m33, this.m23 * this.m31);
        float z3 = MathUtils.fmn(this.m22, this.m33, this.m23 * this.m32);

        return MathUtils.fmn(x1, z3, MathUtils.fma(x2, z2, MathUtils.fma(x3, z1, MathUtils.fmn(x4, y3, MathUtils.fma(x5, y2, x6 * y1)))));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float adjugateAndDet()
    {
        float x1 = MathUtils.fmn(this.m00, this.m11, this.m01 * this.m10);
        float x2 = MathUtils.fmn(this.m00, this.m12, this.m02 * this.m10);
        float x3 = MathUtils.fmn(this.m00, this.m13, this.m03 * this.m10);

        float x4 = MathUtils.fmn(this.m01, this.m12, this.m02 * this.m11);
        float x5 = MathUtils.fmn(this.m01, this.m13, this.m03 * this.m11);
        float x6 = MathUtils.fmn(this.m02, this.m13, this.m03 * this.m12);

        float y1 = MathUtils.fmn(this.m20, this.m31, this.m21 * this.m30);
        float y2 = MathUtils.fmn(this.m20, this.m32, this.m22 * this.m30);
        float y3 = MathUtils.fmn(this.m20, this.m33, this.m23 * this.m30);

        float z1 = MathUtils.fmn(this.m21, this.m32, this.m22 * this.m31);
        float z2 = MathUtils.fmn(this.m21, this.m33, this.m23 * this.m31);
        float z3 = MathUtils.fmn(this.m22, this.m33, this.m23 * this.m32);

        float determinant = MathUtils.fmn(x1, z3, MathUtils.fma(x2, z2, MathUtils.fma(x3, z1, MathUtils.fmn(x4, y3, MathUtils.fma(x5, y2, x6 * y1)))));

        float a00 = MathUtils.fmn(this.m11, z3, MathUtils.fma(this.m12, z2, this.m13 * z1));
        float a01 = -MathUtils.fma(this.m01, z3, MathUtils.fmn(this.m02, z2, this.m03 * z1));
        float a02 = MathUtils.fmn(this.m31, x6, MathUtils.fma(this.m32, x5, this.m33 * x4));
        float a03 = -MathUtils.fma(this.m21, x6, MathUtils.fmn(this.m22, x5, this.m23 * x4));

        float a10 = -MathUtils.fma(this.m10, z3, MathUtils.fmn(this.m12, y3, this.m13 * y1));
        float a11 = MathUtils.fmn(this.m00, z3, MathUtils.fma(this.m02, y3, this.m03 * y2));
        float a12 = -MathUtils.fma(this.m30, x6, MathUtils.fmn(this.m32, x3, this.m33 * x2));
        float a13 = MathUtils.fmn(this.m20, x6, MathUtils.fma(this.m22, x3, this.m23 * x2));

        float a20 = MathUtils.fmn(this.m10, z2, MathUtils.fma(this.m11, y3, this.m13 * y1));
        float a21 = -MathUtils.fma(this.m00, z2, MathUtils.fmn(this.m01, y3, this.m03 * y1));
        float a22 = MathUtils.fmn(this.m30, x5, MathUtils.fma(this.m31, x3, this.m33 * x1));
        float a23 = -MathUtils.fma(this.m20, x5, MathUtils.fmn(this.m21, x3, this.m23 * x1));

        float a30 = -MathUtils.fma(this.m10, z1, MathUtils.fmn(this.m11, y2, this.m12 * y1));
        float a31 = MathUtils.fmn(this.m00, z1, MathUtils.fma(this.m01, y2, this.m02 * y1));
        float a32 = -MathUtils.fma(this.m30, x4, MathUtils.fmn(this.m31, x2, this.m32 * x1));
        float a33 = MathUtils.fmn(this.m20, x4, MathUtils.fma(this.m21, x2, this.m22 * x1));

      //  float a00 = this.a11 * z3 - this.a12 * z2 + this.a13 * z1;
      // float a10 = -this.a10 * z3 + this.a12 * y3 - this.a13 * y2;
      //  float a20 = this.a10 * z2 - this.a11 * y3 + this.a13 * y1;
      //  float a30 = -this.a10 * z1 + this.a11 * y2 - this.a12 * y1;
      // float a01 = -this.a01 * z3 + this.a02 * z2 - this.a03 * z1;
      // float a11 = this.a00 * z3 - this.a02 * y3 + this.a03 * y2;
      // float a21 = -this.a00 * z2 + this.a01 * y3 - this.a03 * y1;
      // float a31 = this.a00 * z1 - this.a01 * y2 + this.a02 * y1;
      //  float a02 = this.a31 * x6 - this.a32 * x5 + this.a33 * x4;
      //  float a12 = -this.a30 * x6 + this.a32 * x3 - this.a33 * x2;
      //  float a22 = this.a30 * x5 - this.a31 * x3 + this.a33 * x1;
      //  float a32 = -this.a30 * x4 + this.a31 * x2 - this.a32 * x1;
      //  float a03 = -this.a21 * x6 + this.a22 * x5 - this.a23 * x4;
      //  float a13 = this.a20 * x6 - this.a22 * x3 + this.a23 * x2;
      //  float a23 = -this.a20 * x5 + this.a21 * x3 - this.a23 * x1;
      //  float a33 = this.a20 * x4 - this.a21 * x2 + this.a22 * x1;

        this.m00 = a00;
        this.m01 = a01;
        this.m02 = a02;
        this.m03 = a03;

        this.m10 = a10;
        this.m11 = a11;
        this.m12 = a12;
        this.m13 = a13;

        this.m20 = a20;
        this.m21 = a21;
        this.m22 = a22;
        this.m23 = a23;

        this.m30 = a30;
        this.m31 = a31;
        this.m32 = a32;
        this.m33 = a33;

        return determinant;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void multiply(Matrix4f matrix)
    {
        float a00 = MathUtils.fma(this.m00, ((Matrix4fAccessor) (Object) matrix).getM00(), MathUtils.fma(this.m01, ((Matrix4fAccessor) (Object) matrix).getM10(), MathUtils.fma(this.m02, ((Matrix4fAccessor) (Object) matrix).getM20(), this.m03 * ((Matrix4fAccessor) (Object) matrix).getM30())));
        float a01 = MathUtils.fma(this.m00, ((Matrix4fAccessor) (Object) matrix).getM01(), MathUtils.fma(this.m01, ((Matrix4fAccessor) (Object) matrix).getM11(), MathUtils.fma(this.m02, ((Matrix4fAccessor) (Object) matrix).getM21(), this.m03 * ((Matrix4fAccessor) (Object) matrix).getM31())));
        float a02 = MathUtils.fma(this.m00, ((Matrix4fAccessor) (Object) matrix).getM02(), MathUtils.fma(this.m01, ((Matrix4fAccessor) (Object) matrix).getM12(), MathUtils.fma(this.m02, ((Matrix4fAccessor) (Object) matrix).getM22(), this.m03 * ((Matrix4fAccessor) (Object) matrix).getM32())));
        float a03 = MathUtils.fma(this.m00, ((Matrix4fAccessor) (Object) matrix).getM03(), MathUtils.fma(this.m01, ((Matrix4fAccessor) (Object) matrix).getM13(), MathUtils.fma(this.m02, ((Matrix4fAccessor) (Object) matrix).getM23(), this.m03 * ((Matrix4fAccessor) (Object) matrix).getM33())));

        float a10 = MathUtils.fma(this.m10, ((Matrix4fAccessor) (Object) matrix).getM00(), MathUtils.fma(this.m11, ((Matrix4fAccessor) (Object) matrix).getM10(), MathUtils.fma(this.m12, ((Matrix4fAccessor) (Object) matrix).getM20(), this.m13 * ((Matrix4fAccessor) (Object) matrix).getM30())));
        float a11 = MathUtils.fma(this.m10, ((Matrix4fAccessor) (Object) matrix).getM01(), MathUtils.fma(this.m11, ((Matrix4fAccessor) (Object) matrix).getM11(), MathUtils.fma(this.m12, ((Matrix4fAccessor) (Object) matrix).getM21(), this.m13 * ((Matrix4fAccessor) (Object) matrix).getM31())));
        float a12 = MathUtils.fma(this.m10, ((Matrix4fAccessor) (Object) matrix).getM02(), MathUtils.fma(this.m11, ((Matrix4fAccessor) (Object) matrix).getM12(), MathUtils.fma(this.m12, ((Matrix4fAccessor) (Object) matrix).getM22(), this.m13 * ((Matrix4fAccessor) (Object) matrix).getM32())));
        float a13 = MathUtils.fma(this.m10, ((Matrix4fAccessor) (Object) matrix).getM03(), MathUtils.fma(this.m11, ((Matrix4fAccessor) (Object) matrix).getM13(), MathUtils.fma(this.m12, ((Matrix4fAccessor) (Object) matrix).getM23(), this.m13 * ((Matrix4fAccessor) (Object) matrix).getM33())));

        float a20 = MathUtils.fma(this.m20, ((Matrix4fAccessor) (Object) matrix).getM00(), MathUtils.fma(this.m21, ((Matrix4fAccessor) (Object) matrix).getM10(), MathUtils.fma(this.m22, ((Matrix4fAccessor) (Object) matrix).getM20(), this.m23 * ((Matrix4fAccessor) (Object) matrix).getM30())));
        float a21 = MathUtils.fma(this.m20, ((Matrix4fAccessor) (Object) matrix).getM01(), MathUtils.fma(this.m21, ((Matrix4fAccessor) (Object) matrix).getM11(), MathUtils.fma(this.m22, ((Matrix4fAccessor) (Object) matrix).getM21(), this.m23 * ((Matrix4fAccessor) (Object) matrix).getM31())));
        float a22 = MathUtils.fma(this.m20, ((Matrix4fAccessor) (Object) matrix).getM02(), MathUtils.fma(this.m21, ((Matrix4fAccessor) (Object) matrix).getM12(), MathUtils.fma(this.m22, ((Matrix4fAccessor) (Object) matrix).getM22(), this.m23 * ((Matrix4fAccessor) (Object) matrix).getM32())));
        float a23 = MathUtils.fma(this.m20, ((Matrix4fAccessor) (Object) matrix).getM03(), MathUtils.fma(this.m21, ((Matrix4fAccessor) (Object) matrix).getM13(), MathUtils.fma(this.m22, ((Matrix4fAccessor) (Object) matrix).getM23(), this.m23 * ((Matrix4fAccessor) (Object) matrix).getM33())));

        float a30 = MathUtils.fma(this.m30, ((Matrix4fAccessor) (Object) matrix).getM00(), MathUtils.fma(this.m31, ((Matrix4fAccessor) (Object) matrix).getM10(), MathUtils.fma(this.m32, ((Matrix4fAccessor) (Object) matrix).getM20(), this.m33 * ((Matrix4fAccessor) (Object) matrix).getM30())));
        float a31 = MathUtils.fma(this.m30, ((Matrix4fAccessor) (Object) matrix).getM01(), MathUtils.fma(this.m31, ((Matrix4fAccessor) (Object) matrix).getM11(), MathUtils.fma(this.m32, ((Matrix4fAccessor) (Object) matrix).getM21(), this.m33 * ((Matrix4fAccessor) (Object) matrix).getM31())));
        float a32 = MathUtils.fma(this.m30, ((Matrix4fAccessor) (Object) matrix).getM02(), MathUtils.fma(this.m31, ((Matrix4fAccessor) (Object) matrix).getM12(), MathUtils.fma(this.m32, ((Matrix4fAccessor) (Object) matrix).getM22(), this.m33 * ((Matrix4fAccessor) (Object) matrix).getM32())));
        float a33 = MathUtils.fma(this.m30, ((Matrix4fAccessor) (Object) matrix).getM03(), MathUtils.fma(this.m31, ((Matrix4fAccessor) (Object) matrix).getM13(), MathUtils.fma(this.m32, ((Matrix4fAccessor) (Object) matrix).getM23(), this.m33 * ((Matrix4fAccessor) (Object) matrix).getM33())));

    //    float a00 = this.a00 * ((Matrix4fAccessor) (Object) matrix).getA00() + this.a01 * ((Matrix4fAccessor) (Object) matrix).getA10() + this.a02 * ((Matrix4fAccessor) (Object) matrix).getA20() + this.a03 * ((Matrix4fAccessor) (Object) matrix).getA30();
    //    float a01 = this.a00 * ((Matrix4fAccessor) (Object) matrix).getA01() + this.a01 * ((Matrix4fAccessor) (Object) matrix).getA11() + this.a02 * ((Matrix4fAccessor) (Object) matrix).getA21() + this.a03 * ((Matrix4fAccessor) (Object) matrix).getA31();
    //    float a02 = this.a00 * ((Matrix4fAccessor) (Object) matrix).getA02() + this.a01 * ((Matrix4fAccessor) (Object) matrix).getA12() + this.a02 * ((Matrix4fAccessor) (Object) matrix).getA22() + this.a03 * ((Matrix4fAccessor) (Object) matrix).getA32();
    //    float a03 = this.a00 * ((Matrix4fAccessor) (Object) matrix).getA03() + this.a01 * ((Matrix4fAccessor) (Object) matrix).getA13() + this.a02 * ((Matrix4fAccessor) (Object) matrix).getA23() + this.a03 * ((Matrix4fAccessor) (Object) matrix).getA33();

    //    float a10 = this.a10 * ((Matrix4fAccessor) (Object) matrix).getA00() + this.a11 * ((Matrix4fAccessor) (Object) matrix).getA10() + this.a12 * ((Matrix4fAccessor) (Object) matrix).getA20() + this.a13 * ((Matrix4fAccessor) (Object) matrix).getA30();
    //    float a11 = this.a10 * ((Matrix4fAccessor) (Object) matrix).getA01() + this.a11 * ((Matrix4fAccessor) (Object) matrix).getA11() + this.a12 * ((Matrix4fAccessor) (Object) matrix).getA21() + this.a13 * ((Matrix4fAccessor) (Object) matrix).getA31();
    //    float a12 = this.a10 * ((Matrix4fAccessor) (Object) matrix).getA02() + this.a11 * ((Matrix4fAccessor) (Object) matrix).getA12() + this.a12 * ((Matrix4fAccessor) (Object) matrix).getA22() + this.a13 * ((Matrix4fAccessor) (Object) matrix).getA32();
    //    float a13 = this.a10 * ((Matrix4fAccessor) (Object) matrix).getA03() + this.a11 * ((Matrix4fAccessor) (Object) matrix).getA13() + this.a12 * ((Matrix4fAccessor) (Object) matrix).getA23() + this.a13 * ((Matrix4fAccessor) (Object) matrix).getA33();

    //    float a20 = this.a20 * ((Matrix4fAccessor) (Object) matrix).getA00() + this.a21 * ((Matrix4fAccessor) (Object) matrix).getA10() + this.a22 * ((Matrix4fAccessor) (Object) matrix).getA20() + this.a23 * ((Matrix4fAccessor) (Object) matrix).getA30();
    //    float a21 = this.a20 * ((Matrix4fAccessor) (Object) matrix).getA01() + this.a21 * ((Matrix4fAccessor) (Object) matrix).getA11() + this.a22 * ((Matrix4fAccessor) (Object) matrix).getA21() + this.a23 * ((Matrix4fAccessor) (Object) matrix).getA31();
    //    float a22 = this.a20 * ((Matrix4fAccessor) (Object) matrix).getA02() + this.a21 * ((Matrix4fAccessor) (Object) matrix).getA12() + this.a22 * ((Matrix4fAccessor) (Object) matrix).getA22() + this.a23 * ((Matrix4fAccessor) (Object) matrix).getA32();
    //    float a23 = this.a20 * ((Matrix4fAccessor) (Object) matrix).getA03() + this.a21 * ((Matrix4fAccessor) (Object) matrix).getA13() + this.a22 * ((Matrix4fAccessor) (Object) matrix).getA23() + this.a23 * ((Matrix4fAccessor) (Object) matrix).getA33();

    //    float a30 = this.a30 * ((Matrix4fAccessor) (Object) matrix).getA00() + this.a31 * ((Matrix4fAccessor) (Object) matrix).getA10() + this.a32 * ((Matrix4fAccessor) (Object) matrix).getA20() + this.a33 * ((Matrix4fAccessor) (Object) matrix).getA30();
    //    float a31 = this.a30 * ((Matrix4fAccessor) (Object) matrix).getA01() + this.a31 * ((Matrix4fAccessor) (Object) matrix).getA11() + this.a32 * ((Matrix4fAccessor) (Object) matrix).getA21() + this.a33 * ((Matrix4fAccessor) (Object) matrix).getA31();
    //    float a32 = this.a30 * ((Matrix4fAccessor) (Object) matrix).getA02() + this.a31 * ((Matrix4fAccessor) (Object) matrix).getA12() + this.a32 * ((Matrix4fAccessor) (Object) matrix).getA22() + this.a33 * ((Matrix4fAccessor) (Object) matrix).getA32();
    //    float a33 = this.a30 * ((Matrix4fAccessor) (Object) matrix).getA03() + this.a31 * ((Matrix4fAccessor) (Object) matrix).getA13() + this.a32 * ((Matrix4fAccessor) (Object) matrix).getA23() + this.a33 * ((Matrix4fAccessor) (Object) matrix).getA33();

        this.m00 = a00;
        this.m01 = a01;
        this.m02 = a02;
        this.m03 = a03;

        this.m10 = a10;
        this.m11 = a11;
        this.m12 = a12;
        this.m13 = a13;

        this.m20 = a20;
        this.m21 = a21;
        this.m22 = a22;
        this.m23 = a23;

        this.m30 = a30;
        this.m31 = a31;
        this.m32 = a32;
        this.m33 = a33;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void multiplyWithTranslation(float x, float y, float z)
    {
        this.m03 += MathUtils.fma(this.m00, x, MathUtils.fma(this.m01, y, this.m02 * z));
        this.m13 += MathUtils.fma(this.m10, x, MathUtils.fma(this.m11, y, this.m12 * z));
        this.m23 += MathUtils.fma(this.m20, x, MathUtils.fma(this.m21, y, this.m22 * z));
        this.m33 += MathUtils.fma(this.m30, x, MathUtils.fma(this.m31, y, this.m32 * z));
    }
}
