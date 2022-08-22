package moodss.bm.mixins.client.matrix;

import moodss.bm.MathUtils;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Matrix4f.class)
public class Matrix4fMixin
{
    @Shadow
    protected float a00;

    @Shadow
    protected float a01;

    @Shadow
    protected float a02;

    @Shadow
    protected float a03;


    @Shadow
    protected float a10;

    @Shadow
    protected float a11;

    @Shadow
    protected float a12;

    @Shadow
    protected float a13;


    @Shadow
    protected float a20;

    @Shadow
    protected float a21;

    @Shadow
    protected float a22;

    @Shadow
    protected float a23;


    @Shadow
    protected float a30;

    @Shadow
    protected float a31;

    @Shadow
    protected float a32;

    @Shadow
    protected float a33;

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float determinant()
    {
        float x1 = MathUtils.fmn(this.a00, this.a11, this.a01 * this.a10);
        float x2 = MathUtils.fmn(this.a00, this.a12, this.a02 * this.a10);
        float x3 = MathUtils.fmn(this.a00, this.a13, this.a03 * this.a10);

        float x4 = MathUtils.fmn(this.a01, this.a12, this.a02 * this.a11);
        float x5 = MathUtils.fmn(this.a01, this.a13, this.a03 * this.a11);
        float x6 = MathUtils.fmn(this.a02, this.a13, this.a03 * this.a12);

        float y1 = MathUtils.fmn(this.a20, this.a31, this.a21 * this.a30);
        float y2 = MathUtils.fmn(this.a20, this.a32, this.a22 * this.a30);
        float y3 = MathUtils.fmn(this.a20, this.a33, this.a23 * this.a30);

        float z1 = MathUtils.fmn(this.a21, this.a32, this.a22 * this.a31);
        float z2 = MathUtils.fmn(this.a21, this.a33, this.a23 * this.a31);
        float z3 = MathUtils.fmn(this.a22, this.a33, this.a23 * this.a32);

        return MathUtils.fmn(x1, z3, MathUtils.fma(x2, z2, MathUtils.fma(x3, z1, MathUtils.fmn(x4, y3, MathUtils.fma(x5, y2, x6 * y1)))));
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public float determinantAndAdjugate()
    {
        float x1 = MathUtils.fmn(this.a00, this.a11, this.a01 * this.a10);
        float x2 = MathUtils.fmn(this.a00, this.a12, this.a02 * this.a10);
        float x3 = MathUtils.fmn(this.a00, this.a13, this.a03 * this.a10);

        float x4 = MathUtils.fmn(this.a01, this.a12, this.a02 * this.a11);
        float x5 = MathUtils.fmn(this.a01, this.a13, this.a03 * this.a11);
        float x6 = MathUtils.fmn(this.a02, this.a13, this.a03 * this.a12);

        float y1 = MathUtils.fmn(this.a20, this.a31, this.a21 * this.a30);
        float y2 = MathUtils.fmn(this.a20, this.a32, this.a22 * this.a30);
        float y3 = MathUtils.fmn(this.a20, this.a33, this.a23 * this.a30);

        float z1 = MathUtils.fmn(this.a21, this.a32, this.a22 * this.a31);
        float z2 = MathUtils.fmn(this.a21, this.a33, this.a23 * this.a31);
        float z3 = MathUtils.fmn(this.a22, this.a33, this.a23 * this.a32);

        float determinant = MathUtils.fmn(x1, z3, MathUtils.fma(x2, z2, MathUtils.fma(x3, z1, MathUtils.fmn(x4, y3, MathUtils.fma(x5, y2, x6 * y1)))));

        float a00 = MathUtils.fmn(this.a11, z3, MathUtils.fma(this.a12, z2, this.a13 * z1));
        float a01 = -MathUtils.fma(this.a01, z3, MathUtils.fmn(this.a02, z2, this.a03 * z1));
        float a02 = MathUtils.fmn(this.a31, x6, MathUtils.fma(this.a32, x5, this.a33 * x4));
        float a03 = -MathUtils.fma(this.a21, x6, MathUtils.fmn(this.a22, x5, this.a23 * x4));

        float a10 = -MathUtils.fma(this.a10, z3, MathUtils.fmn(this.a12, y3, this.a13 * y1));
        float a11 = MathUtils.fmn(this.a00, z3, MathUtils.fma(this.a02, y3, this.a03 * y2));
        float a12 = -MathUtils.fma(this.a30, x6, MathUtils.fmn(this.a32, x3, this.a33 * x2));
        float a13 = MathUtils.fmn(this.a20, x6, MathUtils.fma(this.a22, x3, this.a23 * x2));

        float a20 = MathUtils.fmn(this.a10, z2, MathUtils.fma(this.a11, y3, this.a13 * y1));
        float a21 = -MathUtils.fma(this.a00, z2, MathUtils.fmn(this.a01, y3, this.a03 * y1));
        float a22 = MathUtils.fmn(this.a30, x5, MathUtils.fma(this.a31, x3, this.a33 * x1));
        float a23 = -MathUtils.fma(this.a20, x5, MathUtils.fmn(this.a21, x3, this.a23 * x1));

        float a30 = -MathUtils.fma(this.a10, z1, MathUtils.fmn(this.a11, y2, this.a12 * y1));
        float a31 = MathUtils.fmn(this.a00, z1, MathUtils.fma(this.a01, y2, this.a02 * y1));
        float a32 = -MathUtils.fma(this.a30, x4, MathUtils.fmn(this.a31, x2, this.a32 * x1));
        float a33 = MathUtils.fmn(this.a20, x4, MathUtils.fma(this.a21, x2, this.a22 * x1));

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

        this.a00 = a00;
        this.a01 = a01;
        this.a02 = a02;
        this.a03 = a03;

        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;

        this.a20 = a20;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;

        this.a30 = a30;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;

        return determinant;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void multiply(Matrix4f matrix)
    {
        float a00 = MathUtils.fma(this.a00, ((Matrix4fAccessor) (Object) matrix).getA00(), MathUtils.fma(this.a01, ((Matrix4fAccessor) (Object) matrix).getA10(), MathUtils.fma(this.a02, ((Matrix4fAccessor) (Object) matrix).getA20(), this.a03 * ((Matrix4fAccessor) (Object) matrix).getA30())));
        float a01 = MathUtils.fma(this.a00, ((Matrix4fAccessor) (Object) matrix).getA01(), MathUtils.fma(this.a01, ((Matrix4fAccessor) (Object) matrix).getA11(), MathUtils.fma(this.a02, ((Matrix4fAccessor) (Object) matrix).getA21(), this.a03 * ((Matrix4fAccessor) (Object) matrix).getA31())));
        float a02 = MathUtils.fma(this.a00, ((Matrix4fAccessor) (Object) matrix).getA02(), MathUtils.fma(this.a01, ((Matrix4fAccessor) (Object) matrix).getA12(), MathUtils.fma(this.a02, ((Matrix4fAccessor) (Object) matrix).getA22(), this.a03 * ((Matrix4fAccessor) (Object) matrix).getA32())));
        float a03 = MathUtils.fma(this.a00, ((Matrix4fAccessor) (Object) matrix).getA03(), MathUtils.fma(this.a01, ((Matrix4fAccessor) (Object) matrix).getA13(), MathUtils.fma(this.a02, ((Matrix4fAccessor) (Object) matrix).getA23(), this.a03 * ((Matrix4fAccessor) (Object) matrix).getA33())));

        float a10 = MathUtils.fma(this.a10, ((Matrix4fAccessor) (Object) matrix).getA00(), MathUtils.fma(this.a11, ((Matrix4fAccessor) (Object) matrix).getA10(), MathUtils.fma(this.a12, ((Matrix4fAccessor) (Object) matrix).getA20(), this.a13 * ((Matrix4fAccessor) (Object) matrix).getA30())));
        float a11 = MathUtils.fma(this.a10, ((Matrix4fAccessor) (Object) matrix).getA01(), MathUtils.fma(this.a11, ((Matrix4fAccessor) (Object) matrix).getA11(), MathUtils.fma(this.a12, ((Matrix4fAccessor) (Object) matrix).getA21(), this.a13 * ((Matrix4fAccessor) (Object) matrix).getA31())));
        float a12 = MathUtils.fma(this.a10, ((Matrix4fAccessor) (Object) matrix).getA02(), MathUtils.fma(this.a11, ((Matrix4fAccessor) (Object) matrix).getA12(), MathUtils.fma(this.a12, ((Matrix4fAccessor) (Object) matrix).getA22(), this.a13 * ((Matrix4fAccessor) (Object) matrix).getA32())));
        float a13 = MathUtils.fma(this.a10, ((Matrix4fAccessor) (Object) matrix).getA03(), MathUtils.fma(this.a11, ((Matrix4fAccessor) (Object) matrix).getA13(), MathUtils.fma(this.a12, ((Matrix4fAccessor) (Object) matrix).getA23(), this.a13 * ((Matrix4fAccessor) (Object) matrix).getA33())));

        float a20 = MathUtils.fma(this.a20, ((Matrix4fAccessor) (Object) matrix).getA00(), MathUtils.fma(this.a21, ((Matrix4fAccessor) (Object) matrix).getA10(), MathUtils.fma(this.a22, ((Matrix4fAccessor) (Object) matrix).getA20(), this.a23 * ((Matrix4fAccessor) (Object) matrix).getA30())));
        float a21 = MathUtils.fma(this.a20, ((Matrix4fAccessor) (Object) matrix).getA01(), MathUtils.fma(this.a21, ((Matrix4fAccessor) (Object) matrix).getA11(), MathUtils.fma(this.a22, ((Matrix4fAccessor) (Object) matrix).getA21(), this.a23 * ((Matrix4fAccessor) (Object) matrix).getA31())));
        float a22 = MathUtils.fma(this.a20, ((Matrix4fAccessor) (Object) matrix).getA02(), MathUtils.fma(this.a21, ((Matrix4fAccessor) (Object) matrix).getA12(), MathUtils.fma(this.a22, ((Matrix4fAccessor) (Object) matrix).getA22(), this.a23 * ((Matrix4fAccessor) (Object) matrix).getA32())));
        float a23 = MathUtils.fma(this.a20, ((Matrix4fAccessor) (Object) matrix).getA03(), MathUtils.fma(this.a21, ((Matrix4fAccessor) (Object) matrix).getA13(), MathUtils.fma(this.a22, ((Matrix4fAccessor) (Object) matrix).getA23(), this.a23 * ((Matrix4fAccessor) (Object) matrix).getA33())));

        float a30 = MathUtils.fma(this.a30, ((Matrix4fAccessor) (Object) matrix).getA00(), MathUtils.fma(this.a31, ((Matrix4fAccessor) (Object) matrix).getA10(), MathUtils.fma(this.a32, ((Matrix4fAccessor) (Object) matrix).getA20(), this.a33 * ((Matrix4fAccessor) (Object) matrix).getA30())));
        float a31 = MathUtils.fma(this.a30, ((Matrix4fAccessor) (Object) matrix).getA01(), MathUtils.fma(this.a31, ((Matrix4fAccessor) (Object) matrix).getA11(), MathUtils.fma(this.a32, ((Matrix4fAccessor) (Object) matrix).getA21(), this.a33 * ((Matrix4fAccessor) (Object) matrix).getA31())));
        float a32 = MathUtils.fma(this.a30, ((Matrix4fAccessor) (Object) matrix).getA02(), MathUtils.fma(this.a31, ((Matrix4fAccessor) (Object) matrix).getA12(), MathUtils.fma(this.a32, ((Matrix4fAccessor) (Object) matrix).getA22(), this.a33 * ((Matrix4fAccessor) (Object) matrix).getA32())));
        float a33 = MathUtils.fma(this.a30, ((Matrix4fAccessor) (Object) matrix).getA03(), MathUtils.fma(this.a31, ((Matrix4fAccessor) (Object) matrix).getA13(), MathUtils.fma(this.a32, ((Matrix4fAccessor) (Object) matrix).getA23(), this.a33 * ((Matrix4fAccessor) (Object) matrix).getA33())));

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

        this.a00 = a00;
        this.a01 = a01;
        this.a02 = a02;
        this.a03 = a03;

        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;

        this.a20 = a20;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;

        this.a30 = a30;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
    }

    /**
     * @author Mo0dss
     * @reason Introduce FMA
     */
    @Overwrite
    public void multiplyByTranslation(float x, float y, float z)
    {
        this.a03 += MathUtils.fma(this.a00, x, MathUtils.fma(this.a01, y, this.a02 * z));
        this.a13 += MathUtils.fma(this.a10, x, MathUtils.fma(this.a11, y, this.a12 * z));
        this.a23 += MathUtils.fma(this.a20, x, MathUtils.fma(this.a21, y, this.a22 * z));
        this.a33 += MathUtils.fma(this.a30, x, MathUtils.fma(this.a31, y, this.a32 * z));
    }
}
