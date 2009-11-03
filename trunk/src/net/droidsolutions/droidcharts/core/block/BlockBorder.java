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
 * ----------------
 * BlockBorder.java
 * ----------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 22-Oct-2004 : Version 1 (DG);
 * 04-Feb-2005 : Added equals() and implemented Serializable (DG);
 * 23-Feb-2005 : Added attribute for border color (DG);
 * 06-May-2005 : Added new convenience constructors (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 16-Mar-2007 : Implemented BlockFrame (DG);
 *
 */

package net.droidsolutions.droidcharts.core.block;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.common.RectangleInsets;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * A border for a block. This class is immutable.
 */
public class BlockBorder implements BlockFrame, Serializable {

	/** For serialization. */
	private static final long serialVersionUID = 4961579220410228283L;

	/** An empty border. */

	public static final Paint paintWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		paintWhite.setARGB(00, 256, 256, 256);
	}

	public static final Paint paintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		paintBlack.setARGB(00, 0, 0, 0);
	}
	public static final BlockBorder NONE = new BlockBorder(
			RectangleInsets.ZERO_INSETS, paintWhite);

	/** The space reserved for the border. */
	private RectangleInsets insets;

	/** The border color. */
	private transient Paint paint;

	/**
	 * Creates a default border.
	 */
	public BlockBorder() {
		this(paintBlack);
	}

	/**
	 * Creates a new border with the specified color.
	 * 
	 * @param paint
	 *            the color (<code>null</code> not permitted).
	 */
	public BlockBorder(Paint paint) {
		this(new RectangleInsets(1, 1, 1, 1), paint);
	}

	/**
	 * Creates a new border with the specified line widths (in black).
	 * 
	 * @param top
	 *            the width of the top border.
	 * @param left
	 *            the width of the left border.
	 * @param bottom
	 *            the width of the bottom border.
	 * @param right
	 *            the width of the right border.
	 */
	public BlockBorder(double top, double left, double bottom, double right) {
		this(new RectangleInsets(top, left, bottom, right), paintBlack);
	}

	/**
	 * Creates a new border with the specified line widths (in black).
	 * 
	 * @param top
	 *            the width of the top border.
	 * @param left
	 *            the width of the left border.
	 * @param bottom
	 *            the width of the bottom border.
	 * @param right
	 *            the width of the right border.
	 * @param paint
	 *            the border paint (<code>null</code> not permitted).
	 */
	public BlockBorder(double top, double left, double bottom, double right,
			Paint paint) {
		this(new RectangleInsets(top, left, bottom, right), paint);
	}

	/**
	 * Creates a new border.
	 * 
	 * @param insets
	 *            the border insets (<code>null</code> not permitted).
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 */
	public BlockBorder(RectangleInsets insets, Paint paint) {
		if (insets == null) {
			throw new IllegalArgumentException("Null 'insets' argument.");
		}
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.insets = insets;
		this.paint = paint;
		this.paint.setStyle(Paint.Style.FILL);
	}

	/**
	 * Returns the space reserved for the border.
	 * 
	 * @return The space (never <code>null</code>).
	 */
	public RectangleInsets getInsets() {
		return this.insets;
	}

	/**
	 * Returns the paint used to draw the border.
	 * 
	 * @return The paint (never <code>null</code>).
	 */
	public Paint getPaint() {
		return this.paint;
	}

	/**
	 * Draws the border by filling in the reserved space.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area.
	 */
	public void draw(Canvas g2, Rectangle2D area) {
		// this default implementation will just fill the available
		// border space with a single color
		double t = this.insets.calculateTopInset(area.getHeight());
		double b = this.insets.calculateBottomInset(area.getHeight());
		double l = this.insets.calculateLeftInset(area.getWidth());
		double r = this.insets.calculateRightInset(area.getWidth());
		double x = area.getX();
		double y = area.getY();
		double w = area.getWidth();
		double h = area.getHeight();

		Rectangle2D rect = new Rectangle2D.Double();
		if (t > 0.0) {
			rect.setRect(x, y, w, t);
			g2.drawRect((float) rect.getMinX(), (float) rect.getMinY(),
					(float) rect.getMaxX(), (float) rect.getMaxY(), paint);
		}
		if (b > 0.0) {
			rect.setRect(x, y + h - b, w, b);
			g2.drawRect((float) rect.getMinX(), (float) rect.getMinY(),
					(float) rect.getMaxX(), (float) rect.getMaxY(), paint);
		}
		if (l > 0.0) {
			rect.setRect(x, y, l, h);
			g2.drawRect((float) rect.getMinX(), (float) rect.getMinY(),
					(float) rect.getMaxX(), (float) rect.getMaxY(), paint);
		}
		if (r > 0.0) {
			rect.setRect(x + w - r, y, r, h);
			g2.drawRect((float) rect.getMinX(), (float) rect.getMinY(),
					(float) rect.getMaxX(), (float) rect.getMaxY(), paint);
		}
	}

}
