/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * -------------------
 * ShapeUtilities.java
 * -------------------
 * (C)opyright 2003-2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: ShapeUtilities.java,v 1.17 2005/11/02 16:31:50 mungady Exp $
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 16-Mar-2004 : Moved rotateShape() from RefineryUtilities.java to here (DG);
 * 13-May-2004 : Added new shape creation methods (DG);
 * 30-Sep-2004 : Added createLineRegion() method (DG);
 *               Moved drawRotatedShape() method from RefineryUtilities class 
 *               to this class (DG);
 * 04-Oct-2004 : Renamed ShapeUtils --> ShapeUtilities (DG);
 * 26-Oct-2004 : Added a method to test the equality of two Line2D 
 *               instances (DG);
 * 10-Nov-2004 : Added new translateShape() and equal(Ellipse2D, Ellipse2D)
 *               methods (DG);
 * 11-Nov-2004 : Renamed translateShape() --> createTranslatedShape() (DG);
 * 07-Jan-2005 : Minor Javadoc fix (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for 1.0.0 release (DG);
 * 21-Jan-2005 : Modified return type of RectangleAnchor.coordinates() 
 *               method (DG);
 * 22-Feb-2005 : Added equality tests for Arc2D and GeneralPath (DG);
 * 16-Mar-2005 : Fixed bug where equal(Shape, Shape) fails for two Polygon
 *               instances (DG);
 *
 */

package net.droidsolutions.droidcharts.common;

import net.droidsolutions.droidcharts.awt.AffineTransform;
import net.droidsolutions.droidcharts.awt.Point2D;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.awt.Shape;

/**
 * Utility methods for {@link Shape} objects.
 * 
 * @author David Gilbert
 */
public class ShapeUtilities {

	/**
	 * Prevents instantiation.
	 */
	private ShapeUtilities() {
	}

	/**
	 * Creates and returns a translated shape.
	 * 
	 * @param shape
	 *            the shape (<code>null</code> not permitted).
	 * @param transX
	 *            the x translation (in Java2D space).
	 * @param transY
	 *            the y translation (in Java2D space).
	 * 
	 * @return The translated shape.
	 */
	public static Shape createTranslatedShape(final Shape shape,
			final double transX, final double transY) {
		if (shape == null) {
			throw new IllegalArgumentException("Null 'shape' argument.");
		}
		final AffineTransform transform = AffineTransform.getTranslateInstance(
				transX, transY);
		return transform.createTransformedShape(shape);
	}

	/**
	 * Translates a shape to a new location such that the anchor point (relative
	 * to the rectangular bounds of the shape) aligns with the specified (x, y)
	 * coordinate in Java2D space.
	 * 
	 * @param shape
	 *            the shape (<code>null</code> not permitted).
	 * @param anchor
	 *            the anchor (<code>null</code> not permitted).
	 * @param locationX
	 *            the x-coordinate (in Java2D space).
	 * @param locationY
	 *            the y-coordinate (in Java2D space).
	 * 
	 * @return A new and translated shape.
	 */
	public static Shape createTranslatedShape(final Shape shape,
			final RectangleAnchor anchor, final double locationX,
			final double locationY) {
		if (shape == null) {
			throw new IllegalArgumentException("Null 'shape' argument.");
		}
		if (anchor == null) {
			throw new IllegalArgumentException("Null 'anchor' argument.");
		}
		Point2D anchorPoint = RectangleAnchor.coordinates(shape.getBounds2D(),
				anchor);
		final AffineTransform transform = AffineTransform.getTranslateInstance(
				locationX - anchorPoint.getX(), locationY - anchorPoint.getY());
		return transform.createTransformedShape(shape);
	}

	/**
	 * Rotates a shape about the specified coordinates.
	 * 
	 * @param base
	 *            the shape (<code>null</code> permitted, returns
	 *            <code>null</code>).
	 * @param angle
	 *            the angle (in radians).
	 * @param x
	 *            the x coordinate for the rotation point (in Java2D space).
	 * @param y
	 *            the y coordinate for the rotation point (in Java2D space).
	 * 
	 * @return the rotated shape.
	 */
	public static Shape rotateShape(final Shape base, final double angle,
			final float x, final float y) {
		if (base == null) {
			return null;
		}
		final AffineTransform rotate = AffineTransform.getRotateInstance(angle,
				x, y);
		final Shape result = rotate.createTransformedShape(base);
		return result;
	}

	/**
	 * Returns a point based on (x, y) but constrained to be within the bounds
	 * of a given rectangle.
	 * 
	 * @param x
	 *            the x-coordinate.
	 * @param y
	 *            the y-coordinate.
	 * @param area
	 *            the constraining rectangle (<code>null</code> not permitted).
	 * 
	 * @return A point within the rectangle.
	 * 
	 * @throws NullPointerException
	 *             if <code>area</code> is <code>null</code>.
	 */
	public static Point2D getPointInRectangle(double x, double y,
			final Rectangle2D area) {

		x = Math.max(area.getMinX(), Math.min(x, area.getMaxX()));
		y = Math.max(area.getMinY(), Math.min(y, area.getMaxY()));
		return new Point2D.Double(x, y);

	}

}
