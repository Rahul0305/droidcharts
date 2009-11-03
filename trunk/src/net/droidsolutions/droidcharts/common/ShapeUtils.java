/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * ---------------
 * ShapeUtils.java
 * ---------------
 * (C)opyright 2003, 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $$
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 16-Mar-2004 : Moved rotateShape() from RefineryUtilities.java to here (DG);
 * 13-May-2004 : Added new shape creation methods (DG);
 *
 */

package net.droidsolutions.droidcharts.common;

import net.droidsolutions.droidcharts.awt.AffineTransform;
import net.droidsolutions.droidcharts.awt.Shape;

/**
 * Utility methods for {@link Shape} objects.
 */
public abstract class ShapeUtils {

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

}
