/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
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
 * ---------------
 * ChartColor.java
 * ---------------
 * (C) Copyright 2003-2008, by Cameron Riley and Contributors.
 *
 * Original Author:  Cameron Riley;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 23-Jan-2003 : Version 1, contributed by Cameron Riley (DG);
 * 25-Nov-2004 : Changed first 7 colors to softer shades (DG);
 * 03-Nov-2005 : Removed orange color, too close to yellow - see bug
 *               report 1328408 (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 02-Feb-2007 : Removed author tags all over JFreeChart sources (DG);
 *
 */

package net.droidsolutions.droidcharts.core;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class to extend the number of Colors available to the charts. This extends
 * the java.awt.Color object and extends the number of final Colors publically
 * accessible.
 */
public class ChartColor extends Color {

	/** A very dark red color. */
	public static final int VERY_DARK_RED = Color.argb(0x90, 0x80, 0x00, 0x00);

	/** A dark red color. */
	public static final int DARK_RED = Color.argb(0x90, 0xc0, 0x00, 0x00);

	/** A light red color. */
	public static final int LIGHT_RED = Color.argb(0x90, 0xFF, 0x40, 0x40);

	/** A very light red color. */
	public static final int VERY_LIGHT_RED = Color.argb(0x90, 0xFF, 0x80, 0x80);

	/** A very dark yellow color. */
	public static final int VERY_DARK_YELLOW = Color.argb(0x90, 0x80, 0x80,
			0x00);

	/** A dark yellow color. */
	public static final int DARK_YELLOW = Color.argb(0x90, 0xC0, 0xC0, 0x00);

	/** A light yellow color. */
	public static final int LIGHT_YELLOW = Color.argb(0x90, 0xFF, 0xFF, 0x40);

	/** A very light yellow color. */
	public static final int VERY_LIGHT_YELLOW = Color.argb(0x90, 0xFF, 0xFF,
			0x80);

	/** A very dark green color. */
	public static final int VERY_DARK_GREEN = Color
			.argb(0x90, 0x00, 0x80, 0x00);

	/** A dark green color. */
	public static final int DARK_GREEN = Color.argb(0x90, 0x00, 0xC0, 0x00);

	/** A light green color. */
	public static final int LIGHT_GREEN = Color.argb(0x90, 0x40, 0xFF, 0x40);

	/** A very light green color. */
	public static final int VERY_LIGHT_GREEN = Color.argb(0x90, 0x80, 0xFF,
			0x80);

	/** A very dark cyan color. */
	public static final int VERY_DARK_CYAN = Color.argb(0x90, 0x00, 0x80, 0x80);

	/** A dark cyan color. */
	public static final int DARK_CYAN = Color.argb(0x90, 0x00, 0xC0, 0xC0);

	/** A light cyan color. */
	public static final int LIGHT_CYAN = Color.argb(0x90, 0x40, 0xFF, 0xFF);

	/** Aa very light cyan color. */
	public static final int VERY_LIGHT_CYAN = Color
			.argb(0x90, 0x80, 0xFF, 0xFF);

	/** A very dark blue color. */
	public static final int VERY_DARK_BLUE = Color.argb(0x90, 0x00, 0x00, 0x80);

	/** A dark blue color. */
	public static final int DARK_BLUE = Color.argb(0x90, 0x00, 0x00, 0xC0);

	/** A light blue color. */
	public static final int LIGHT_BLUE = Color.argb(0x90, 0x40, 0x40, 0xFF);

	/** A very light blue color. */
	public static final int VERY_LIGHT_BLUE = Color
			.argb(0x90, 0x80, 0x80, 0xFF);

	/** A very dark magenta/purple color. */
	public static final int VERY_DARK_MAGENTA = Color.argb(0x90, 0x80, 0x00,
			0x80);

	/** A dark magenta color. */
	public static final int DARK_MAGENTA = Color.argb(0x90, 0xC0, 0x00, 0xC0);

	/** A light magenta color. */
	public static final int LIGHT_MAGENTA = Color.argb(0x90, 0xFF, 0x40, 0xFF);

	/** A very light magenta color. */
	public static final int VERY_LIGHT_MAGENTA = Color.argb(0x90, 0xFF, 0x80,
			0xFF);

	/**
	 * Convenience method to return an array of <code>Paint</code> objects that
	 * represent the pre-defined colors in the <code>Color<code> and
	 * <code>ChartColor</code> objects.
	 * 
	 * @return An array of objects with the <code>Paint</code> interface.
	 */
	public static Paint[] createDefaultPaintArray() {

		return new Paint[] {

		createPaint(Color.argb(0x90, 0xFF, 0x55, 0x55)),
				createPaint(Color.argb(0x90, 0x55, 0x55, 0xFF)),
				createPaint(Color.argb(0x90, 0x55, 0xFF, 0x55)),
				createPaint(Color.argb(0x90, 0xFF, 0xFF, 0x55)),
				createPaint(Color.argb(0x90, 0xFF, 0x55, 0xFF)),
				createPaint(Color.argb(0x90, 0x55, 0xFF, 0xFF)),

				createPaint(ChartColor.DARK_RED),
				createPaint(ChartColor.DARK_BLUE),
				createPaint(ChartColor.DARK_GREEN),
				createPaint(ChartColor.DARK_YELLOW),
				createPaint(ChartColor.DARK_MAGENTA),
				createPaint(ChartColor.DARK_CYAN),

				createPaint(ChartColor.LIGHT_RED),
				createPaint(ChartColor.LIGHT_BLUE),
				createPaint(ChartColor.LIGHT_GREEN),
				createPaint(ChartColor.LIGHT_YELLOW),
				createPaint(ChartColor.LIGHT_MAGENTA),
				createPaint(ChartColor.LIGHT_CYAN),
				createPaint(ChartColor.VERY_DARK_RED),
				createPaint(ChartColor.VERY_DARK_BLUE),
				createPaint(ChartColor.VERY_DARK_GREEN),
				createPaint(ChartColor.VERY_DARK_YELLOW),
				createPaint(ChartColor.VERY_DARK_MAGENTA),
				createPaint(ChartColor.VERY_DARK_CYAN),
				createPaint(ChartColor.VERY_LIGHT_RED),
				createPaint(ChartColor.VERY_LIGHT_BLUE),
				createPaint(ChartColor.VERY_LIGHT_GREEN),
				createPaint(ChartColor.VERY_LIGHT_YELLOW),
				createPaint(ChartColor.VERY_LIGHT_MAGENTA),
				createPaint(ChartColor.VERY_LIGHT_CYAN) };
	}

	private static Paint createPaint(int color) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		return paint;
	}

}
