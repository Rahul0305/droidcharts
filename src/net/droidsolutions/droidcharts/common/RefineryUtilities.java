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
 * ----------------------
 * RefineryUtilities.java
 * ----------------------
 * (C) Copyright 2000-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Jon Iles;
 *
 * $Id: RefineryUtilities.java,v 1.25 2004/05/21 14:32:21 mungady Exp $
 *
 * Changes (from 26-Oct-2001)
 * --------------------------
 * 26-Oct-2001 : Changed package to com.jrefinery.ui.*;
 * 26-Nov-2001 : Changed name to SwingRefinery.java to make it obvious that this is not part of
 *               the Java APIs (DG);
 * 10-Dec-2001 : Changed name (again) to JRefineryUtilities.java (DG);
 * 28-Feb-2002 : Moved system properties classes into com.jrefinery.ui.about (DG);
 * 19-Apr-2002 : Renamed JRefineryUtilities-->RefineryUtilities.  Added drawRotatedString(...)
 *               method (DG);
 * 21-May-2002 : Changed frame positioning methods to accept Window parameters, as suggested by
 *               Laurence Vanhelsuwe (DG);
 * 27-May-2002 : Added getPointInRectangle method (DG);
 * 26-Jun-2002 : Removed unnecessary imports (DG);
 * 12-Jul-2002 : Added workaround for rotated text (JI);
 * 14-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 08-May-2003 : Added a new drawRotatedString() method (DG);
 * 09-May-2003 : Added a drawRotatedShape() method (DG);
 * 10-Jun-2003 : Updated aligned and rotated string methods (DG);
 * 29-Oct-2003 : Added workaround for font alignment in PDF output (DG);
 * 07-Nov-2003 : Added rotateShape() method (DG);
 * 16-Mar-2004 : Moved rotateShape() method to ShapeUtils.java (DG);
 * 07-Apr-2004 : Modified text bounds calculation with TextUtilities.getTextBounds() (DG);
 * 21-May-2004 : Fixed bug 951870 - precision in drawAlignedString() method (DG);
 *
 */

package net.droidsolutions.droidcharts.common;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * A collection of utility methods relating to user interfaces.
 */
public abstract class RefineryUtilities {

	/**
	 * Draws a string that is aligned by one anchor point and rotated about
	 * another anchor point.
	 * 
	 * @param text
	 *            the text.
	 * @param g2
	 *            the graphics device.
	 * @param x
	 *            the location of the text anchor.
	 * @param y
	 *            the location of the text anchor.
	 * @param textAnchor
	 *            the text anchor.
	 * @param rotationX
	 *            the x-coordinate for the rotation anchor point.
	 * @param rotationY
	 *            the y-coordinate for the rotation anchor point.
	 * @param angle
	 *            the rotation angle.
	 */
	public static void drawRotatedString(final String text, final Canvas g2,
			final float x, final float y, final Paint paint) {

		g2.drawText(text, x, y, paint);

	}

}
