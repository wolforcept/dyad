package code.auxis;

import java.awt.Point;
import java.awt.Rectangle;

import code.enums.Facing;
import code.general.SpellButton;

public class Auxi {

	/**
	 * @param angle
	 *            angle to calculate facing, in degrees
	 */
	public static Facing getFacing(double a) {

		double angle = a < 0 ? a + 360 : a;
		angle %= 360;

		if (angle < 45 || angle > 315)
			return Facing.EAST;

		if (angle < 135)
			return Facing.NORTH;

		if (angle < 225)
			return Facing.WEST;

		return Facing.SOUTH;
	}

	public static double point_distance(double x1, double y1, double x2,
			double y2) {
		return (int) Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	public static double point_direction(double x1, double y1, double x2,
			double y2) {
		return Math.atan2((y1 - y2), (x2 - x1));
	}

	public static boolean collides(Point p, SpellButton b) {
		Rectangle r = new Rectangle(b.getW(), b.getH());
		r.translate(b.getX(), b.getY());
		return r.contains(p);
	}

	@SuppressWarnings("incomplete-switch")
	public static void rotatePointMatrixWithDir(Point[] a, Facing facing) {
		switch (facing) {
		case NORTH:
			for (int i = 0; i < a.length; i++) {
				a[i].setLocation(a[i].y, -a[i].x);
			}
			break;
		case WEST:
			for (int i = 0; i < a.length; i++) {
				a[i].setLocation(-a[i].x, -a[i].y);
			}
			break;
		case SOUTH:
			for (int i = 0; i < a.length; i++) {
				a[i].setLocation(-a[i].y, a[i].x);
			}
			break;
		}
	}

	public static int getAngleFromFacing(Facing facing) {
		switch (facing) {
		case EAST:
			return 0;
		case NORTH:
			return 90;
		case WEST:
			return 180;
		default:
			return 270;
		}
	}
}
