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
 * -------------------
 * G2TextMeasurer.java
 * -------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: G2TextMeasurer.java,v 1.5 2004/04/26 19:15:42 taqua Exp $
 *
 * Changes
 * -------
 * 07-Jan-2004 : Version 1 (DG);
 *
 */

package net.droidsolutions.droidcharts.core.text;

import net.droidsolutions.droidcharts.awt.Rectangle2D;
import android.graphics.Paint;

/**
 * A {@link TextMeasurer} based on a {@link Graphics2D}.
 */
public class G2TextMeasurer implements TextMeasurer {

	/** The graphics device. */
	Paint p;

	/**
	 * Creates a new text measurer.
	 * 
	 * @param g2
	 *            the graphics device.
	 */
	public G2TextMeasurer(final Paint p) {
		this.p = p;
	}

	/**
	 * Returns the string width.
	 * 
	 * @param text
	 *            the text.
	 * @param start
	 *            the index of the first character to measure.
	 * @param end
	 *            the index of the last character to measure.
	 * 
	 * @return the string width.
	 */
	public float getStringWidth(final String text, final int start,
			final int end) {

		final Rectangle2D bounds = TextUtilities.getTextBounds(text.substring(
				start, end), p);
		final float result = (float) bounds.getWidth();
		return result;
	}

}
