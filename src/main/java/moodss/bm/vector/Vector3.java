package moodss.bm.vector;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import moodss.bm.MathUtils;
import moodss.bm.util.NativeFloat;
import net.minecraft.Util;
import net.minecraft.util.Mth;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Vector3 implements Externalizable
{
    /**
     * Returns a CODEC for {@link #Vector3(float, float, float)}
     */
    public static final Codec<Vector3> CODEC = Codec.FLOAT
            .listOf()
            .comapFlatMap(quat -> Util.fixedSize(quat, 3).map(vec -> new Vector3(vec.get(0), vec.get(1), vec.get(2))), vec -> ImmutableList.of(vec.getX(), vec.getY(), vec.getZ()));

    /**
     * Returns a empty {@link #Vector3(float, float, float)}. (0,0,0)
     */
    public static final Vector3 ZERO = new Vector3(0.0F, 0.0F, 0.0F);

    /**
     * Returns a positive X {@link #Vector3(float, float, float)}. (1,0,0)
     */
    public static Vector3 POSITIVE_X = new Vector3(1.0F, 0.0F, 0.0F);

    /**
     * Returns a positive Y {@link #Vector3(float, float, float)}. (0,1,0)
     */
    public static Vector3 POSITIVE_Y = new Vector3(0.0F, 1.0F, 0.0F);

    /**
     * Returns a positive Z {@link #Vector3(float, float, float)}. (0,0,1)
     */
    public static Vector3 POSITIVE_Z = new Vector3(0.0F, 0.0F, 1.0F);

    /**
     * Returns a negative X {@link #Vector3(float, float, float)}. (-1,0,0)
     */
    public static Vector3 NEGATIVE_X = new Vector3(-1.0F, 0.0F, 0.0F);

    /**
     * Returns a positive Y {@link #Vector3(float, float, float)}. (0,-1,0)
     */
    public static Vector3 NEGATIVE_Y = new Vector3(0.0F, -1.0F, 0.0F);

    /**
     * Returns a negative Z {@link #Vector3(float, float, float)}. (0,0,-1)
     */
    public static Vector3 NEGATIVE_Z = new Vector3(0.0F, 0.0F, -1.0F);

    //Testing purposes
    public static void main(String[] args)
    {
        Vector3 vector = new Vector3(50F, 35F, 20F);

        Vector3.normalize(vector);

        System.out.println(vector);
    }

    private final NativeFloat data;

    /**
     * @param x Initial value for the X component of the vector.
     * @param y Initial value for the Y component of the vector.
     * @param z Initial value for the Z component of the vector.
     */
    public Vector3(float x, float y, float z)
    {
        this.data = NativeFloat.create(x, y, z);
    }

    /**
     * Default constructor
     */
    public Vector3()
    {
        this.data = new NativeFloat(3);
    }

    /**
     * Converts the vector into a unit vector.
     */
    public void normalize()
    {
        float length = length();
        if (length == 0)
        {
            return;
        }

        float num = 1.0F / length;

        NativeFloat.multiply(this.data, num, num, num);
    }

    /**
     * Sets the X component of the vector.
     *
     * @param x The X component of the vector.
     */
    public void setX(float x)
    {
        this.data.set(0, x);
    }

    /**
     * Sets the Y component of the vector.
     *
     * @param y The Y component of the vector.
     */
    public void setY(float y)
    {
        this.data.set(1, y);
    }

    /**
     * Sets the Z component of the vector.
     *
     * @param z The Z component of the vector.
     */
    public void setZ(float z)
    {
        this.data.set(2, z);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeFloat(this.getX());
        out.writeFloat(this.getY());
        out.writeFloat(this.getZ());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException
    {
        this.setX(in.readFloat());
        this.setY(in.readFloat());
        this.setZ(in.readFloat());
    }

    /**
     * @param o Object to make the comparison with.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        if (Float.compare(vector3.getX(), this.getX()) != 0) return false;
        if (Float.compare(vector3.getY(), this.getY()) != 0) return false;
        return Float.compare(vector3.getZ(), this.getZ()) == 0;
    }

    /**
     * Returns the hash code for this instance.
     *
     * @return A 32-bit signed integer hash code.
     */
    @Override
    public int hashCode()
    {
        int result = (this.getX() != +0.0f ? Float.floatToIntBits(this.getX()) : 0);
        result = 31 * result + (this.getY() != +0.0f ? Float.floatToIntBits(this.getY()) : 0);
        result = 31 * result + (this.getZ() != +0.0f ? Float.floatToIntBits(this.getZ()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("x", this.getX())
                .add("y", this.getY())
                .add("z", this.getZ())
                .toString();
    }

    /**
     * Calculates the length of the vector.
     *
     * @return The length of the vector.
     */
    public float length()
    {
        return Mth.fastInvSqrt(this.lengthSquared());
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public float lengthSquared()
    {
        return MathUtils.fma(this.getX(), this.getX(), MathUtils.fma(this.getY(), this.getY(), this.getZ() * this.getZ()));
    }

    /**
     * @return The X component of the vector.
     */
    public float getX()
    {
        return this.data.get(0);
    }

    /**
     * @return The Y component of the vector.
     */
    public float getY()
    {
        return this.data.get(1);
    }

    /**
     * @return The Z component of the vector.
     */
    public float getZ()
    {
        return this.data.get(2);
    }

    /**
     * Adds two vectors.
     *
     * @param left The first vector to add.
     * @param right The second vector to add.
     * @return The sum of the two vectors.
     */
    public static Vector3 add(Vector3 left, Vector3 right)
    {
        return new Vector3(left.getX() + right.getX(), left.getY() + right.getY(), left.getZ() + right.getZ());
    }

    /**
     * Subtracts two vectors.
     *
     * @param left The first vector to subtract.
     * @param right The second vector to subtract.
     * @return The difference of the two vectors.
     */
    public static Vector3 subtract(Vector3 left, Vector3 right)
    {
        return new Vector3(left.getX() - right.getX(), left.getY() - right.getY(), left.getZ() - right.getZ());
    }

    /**
     * Scales a vector by the given value.
     *
     * @param value The vector to scale.
     * @param scale The amount by which to scale the vector.
     * @return The scaled vector.
     */
    public static Vector3 multiply(Vector3 value, float scale)
    {
        return new Vector3(value.getX() * scale, value.getY() * scale, value.getZ() * scale);
    }

    /**
     * Modulates a vector by another.
     *
     * @param left The first vector to modulate.
     * @param right The second vector to modulate.
     * @return The multiplied vector.
     */
    public static Vector3 modulate(Vector3 left, Vector3 right)
    {
        return new Vector3(left.getX() * right.getX(), left.getY() * right.getY(), left.getZ() * right.getZ());
    }

    /**
     * Scales a vector by the given value.
     *
     * @param value The vector to scale.
     * @param scale The amount by which to scale the vector.
     * @return The scaled vector.
     */
    public static Vector3 divide(Vector3 value, float scale)
    {
        if(scale != 0.0F)
        {
            return new Vector3(value.getX() / scale, value.getY() / scale, value.getZ() / scale);
        }

        return value;
    }

    /**
     * Reverses the direction of a given vector.
     *
     * @param value The vector to negate.
     * @return A vector facing in the opposite direction.
     */
    public static Vector3 negate(Vector3 value)
    {
        return new Vector3(-value.getX(), -value.getY(), -value.getZ());
    }

    /**
     * Restricts a value to be within a specified range.
     *
     * @param value The value to clamp.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The clamped value.
     */
    public static Vector3 clamp(Vector3 value, Vector3 min, Vector3 max)
    {
        float x = value.getX();
        x = Math.min(x, max.getX());
        x = Math.max(x, min.getX());

        float y = value.getY();
        y = Math.min(y, max.getY());
        y = Math.max(y, min.getY());

        float z = value.getZ();
        z = Math.min(z, max.getZ());
        z = Math.max(z, min.getZ());

        return new Vector3(x, y, z);
    }

    /**
     * Converts the vector into a unit vector.
     *
     * @param vector The vector to normalize.
     * @return The normalized vector.
     */
    public static Vector3 normalize(Vector3 vector)
    {
        vector.normalize();
        return vector;
    }

    /**
     * Calculates the dot product of two vectors.
     *
     * @param left First source vector.
     * @param right Second source vector.
     * @return The dot product of the two vectors.
     */
    public static float dot(Vector3 left, Vector3 right)
    {
        return MathUtils.fma(left.getX(), right.getX(), MathUtils.fma(left.getY(), right.getY(), left.getZ() * right.getZ()));
    }
}
