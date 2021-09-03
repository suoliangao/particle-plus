package top.suoliangao.particleplus.util;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import java.util.Arrays;



/**
 * This class is a double based coordinate transformation calculation lib.
 * @author SuoLianGao
 */
public class MathUtil {
	
	private MathUtil() {}
	// constants
	/** The 3x3 identity matrix */
	public static final double[] IDENTITY_MATRIX = new double[] {1,0,0,0,1,0,0,0,1};
	public static final int X_AXIS = 1, Y_AXIS = 2, Z_AXIS = 3;
	// vector operation
	/**
	 * The norm(length) of a vector.
	 * @param x : X component of a vector.
	 * @param y : Y component of a vector.
	 * @param z : Z component of a vector.
	 * @return norm(length) of the vector.
	 */
	public static double norm (double x, double y, double z) {
		return sqrt(x*x + y*y + z*z);
	}
	/**
	 * Normalize a vector.<br>
	 * <b>THIS METHOD CHANGES THE INPUT VARIABLES!!!</b>
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void normalize (double x, double y, double z) {
		double n = norm (x, y, z);
		x /= n; y /= n; z /= n;
	}
	/**
	 * Normalize vector(s).<br>
	 * <b>THIS METHOD CHANGES THE INPUT ARRAY!!!</b>
	 * @param vectors : Input vectors.
	 * @throws InvalidInputException
	 */
	public static void normalize (double[] vectors) throws InvalidInputException {
		if (vectors.length%3 != 0)
			throw new InvalidInputException();
		for (int i = 0; i < vectors.length; i += 3) {
			double n = norm (vectors[i+0], vectors[i+1], vectors[i+2]);
			vectors[i+0] /= n; vectors[i+1] /= n; vectors[i+2] /= n;
		}
	}
	/**
	 * Calculate and return the normalized vector.
	 * @param x
	 * @param y
	 * @param z
	 * @return Normalized vector.
	 */
	public static double[] normal (double x, double y, double z) {
		double n = norm (x, y, z);
		return new double[] {x/n, y/n, z/n};
	}
	/**
	 * Calculate and return normalized vector(s).
	 * @param vectors : Input vectors.
	 * @return Normalized vector(s).<br>
	 * <b>null</b> if the input value was invalid.
	 */
	public static double[] normal (double[] vectors) {
		try {
			double[] o = vectors.clone();
			normalize(o);
			return o;
		} catch (Exception e) {
			return null;
		}
	}
	// rotation
	/**
	 * Euler rotation.<br>
	 * x -> y -> z
	 * @param x	: Rotation around x-axis clockwise in degrees.
	 * @param y : Rotation around y-axis clockwise in degrees.
	 * @param z : Rotation around z-axis clockwise in degrees.
	 * @return Rotation matrix around the axis.<br>
	 * <b>null</b> if the input value was invalid.
	 */
	public static double[] rotationMatrix (double x, double y, double z) {
		try {
			double[] I = IDENTITY_MATRIX.clone();
			applyTransform(I, rotationMatirx(X_AXIS, x));
			applyTransform(I, rotationMatirx(Y_AXIS, y));
			applyTransform(I, rotationMatirx(Z_AXIS, z));
			return I;
		} catch (Exception e) {
			return null;
		}
		
	}
	/**
	 * Gives the rotation matrix around basis axis.
	 * @param axis : x, y, or z axis.
	 * @param angle : Rotation angle in degrees clockwise.
	 * @return Rotation matrix around the axis.<br>
	 * <b>null</b> if the input value was invalid.
	 */
	public static double[] rotationMatirx (int axis, double angle) {
		double a = toRadians(angle);
		switch (axis) {
		case X_AXIS:
			return new double[] {1, 0, 0, 0, cos(a), sin(a), 0, -sin(a), cos(a)};
		case Y_AXIS:
			return new double[] {cos(a), 0, -sin(a), 0, 1, 0, sin(a), 0, cos(a)};
		case Z_AXIS:
			return new double[] {cos(a), sin(a), 0, -sin(a), cos(a), 0, 0, 0, 1};
		}
		return null;
	}
	
	/**
	 * Gives the rotation matrix around arbitrary axis (giving a unit vector doesn't accelerate the calculation).
	 * @param angle : Rotation angle in degrees clockwise.
	 * @param axisX	: X component of the axis vector.
	 * @param axisY : Y component of the axis vector.
	 * @param axisZ : Z component of the axis vector.
	 * @return Rotation matrix around the axis.<br>
	 * <b>null</b> if the input value was invalid.
	 */
	public static double[] rotationMatrix (double angle, double axisX, double axisY, double axisZ) {
		double[] d = normal (axisX, axisY, axisZ);
		double a = -toRadians(angle), x = d[0], y = d[1], z = d[2];
		d = new double[] {cos(a/2), sin(a/2)*x, sin(a/2)*y, sin(a/2)*z};
		return new double[] {
				2*(d[0] * d[0] + d[1] * d[1]) - 1, 2*(d[1] * d[2] - d[0] * d[3]) - 0, 2*(d[1] * d[3] + d[0] * d[2]) - 0,
				2*(d[1] * d[2] + d[0] * d[3]) - 0, 2*(d[0] * d[0] + d[2] * d[2]) - 1, 2*(d[2] * d[3] - d[0] * d[1]) - 0,
				2*(d[1] * d[3] - d[0] * d[2]) - 0, 2*(d[2] * d[3] + d[0] * d[1]) - 0, 2*(d[0] * d[0] + d[3] * d[3]) - 1
		};
	}
	
	/**
	 * Calculate and return the transformed vector(s).
	 * @param vectors :<br>
	 * A list of double value, the length must divisible by 3. 
	 * Every adjacent 3 element represent x, y, z component of a vector in turn.<br>
	 * <b>e.g.</b> [1,2,3,4,5,6] represent 2 vectors, <1,2,3> and <4,5,6>.
	 * @param matrix :<br>
	 * The transformation matrix with length 9.<br>
	 * [e1.x, e2.x, e3.x,<br>
	 *  e1.y, e2.y, e3.y,<br>
	 *  e1.z, e2.z, e3.z]<br>
	 *  Where e1, e2, e3 are the basis vectors.<br>
	 * <b>e.g.</b> [1,0,0,0,1,0,0,0,1] represent the 3x3 identity matrix.<br>
	 * @return :
	 * A list of double values represent the transformed vectors.<br>
	 * <b>null</b> if the input value was invalid.
	 */
	public static double[] transform (double[] vectors, double[] matrix) {
		if (matrix.length != 9 || vectors.length%3 != 0)
			return null;
		double[] out = new double[vectors.length];
		for (int i = 0; i < out.length; i += 3) {
			out[i+0] = vectors[i+0]*matrix[0] + vectors[i+1]*matrix[3] + vectors[i+2]*matrix[6];
			out[i+1] = vectors[i+0]*matrix[1] + vectors[i+1]*matrix[4] + vectors[i+2]*matrix[7];
			out[i+2] = vectors[i+0]*matrix[2] + vectors[i+1]*matrix[5] + vectors[i+2]*matrix[8];
		}
		return out;
	}
	// transformation
	/**
	 * Apply transformation to vector(s).<br>
	 * <b>THIS METHOD CHANGES THE INPUT ARRAY!!!</b>
	 * @param vectors :<br>
	 * A list of double value, the length must divisible by 3. 
	 * Every adjacent 3 element represent x, y, z component of a vector in turn.<br>
	 * <b>e.g.</b> [1,2,3,4,5,6] represent 2 vectors, <1,2,3> and <4,5,6>.
	 * @param matrix :<br>
	 * The transformation matrix with length 9.<br>
	 * [e1.x, e2.x, e3.x,<br>
	 *  e1.y, e2.y, e3.y,<br>
	 *  e1.z, e2.z, e3.z]<br>
	 *  Where e1, e2, e3 are the basis vectors.<br>
	 * <b>e.g.</b> [1,0,0,0,1,0,0,0,1] represent the 3x3 identity matrix.<br>
	 * @throws InvalidInputException
	 */
	public static void applyTransform (double[] vectors, double[] matrix) throws InvalidInputException {
		if (matrix.length != 9 || vectors.length%3 != 0)
			throw new InvalidInputException();
		double[] old = vectors.clone();
		for (int i = 0; i < old.length; i += 3) {
			vectors[i+0] = old[i+0]*matrix[0] + old[i+1]*matrix[3] + old[i+2]*matrix[6];
			vectors[i+1] = old[i+0]*matrix[1] + old[i+1]*matrix[4] + old[i+2]*matrix[7];
			vectors[i+2] = old[i+0]*matrix[2] + old[i+1]*matrix[5] + old[i+2]*matrix[8];
		}
	}
	
	public static class InvalidInputException extends Exception {}
}
