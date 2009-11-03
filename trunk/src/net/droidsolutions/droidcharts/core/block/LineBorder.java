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
 * LineBorder.java
 * ---------------
 * (C) Copyright 2007, 2008, by Christo Zietsman and Contributors.
 *
 * Original Author:  Christo Zietsman;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes:
 * --------
 * 16-Mar-2007 : Version 1, contributed by Christo Zietsman with
 *               modifications by DG (DG);
 * 13-Jun-2007 : Don't draw if area doesn't have positive dimensions (DG);
 *
 */

package net.droidsolutions.droidcharts.core.block;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Line2D;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.common.RectangleInsets;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * A line border for any {@link AbstractBlock}.
 * 
 * @since 1.0.5
 */
public class LineBorder implements BlockFrame, Serializable {

	/** For serialization. */
	static final long serialVersionUID = 4630356736707233924L;

	/** The line color. */
	private transient Paint paint;

	/** The line stroke. */
	private transient float stroke;

	/** The insets. */
	private RectangleInsets insets;

	/**
	 * Creates a default border.
	 */
	public LineBorder() {
		this(Color.BLACK, 1.0f, new RectangleInsets(1.0, 1.0, 1.0, 1.0));
	}

	/**
	 * Creates a new border with the specified color.
	 * 
	 * @param paint
	 *            the color (<code>null</code> not permitted).
	 * @param stroke
	 *            the border stroke (<code>null</code> not permitted).
	 * @param insets
	 *            the insets (<code>null</code> not permitted).
	 */
	public LineBorder(int paint, float stroke, RectangleInsets insets) {

		if (insets == null) {
			throw new IllegalArgumentException("Null 'insets' argument.");
		}

		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(paint);

		this.paint = p;
		this.stroke = stroke;
		this.insets = insets;
	}

	/**
	 * Returns the paint.
	 * 
	 * @return The paint (never <code>null</code>).
	 */
	public Paint getPaint() {
		return this.paint;
	}

	/**
	 * Returns the insets.
	 * 
	 * @return The insets (never <code>null</code>).
	 */
	public RectangleInsets getInsets() {
		return this.insets;
	}

	/**
	 * Returns the stroke.
	 * 
	 * @return The stroke (never <code>null</code>).
	 */
	public float getStroke() {
		return this.stroke;
	}

	/**
	 * Draws the border by filling in the reserved space (in black).
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area.
	 */
	public void draw(Canvas g2, Rectangle2D area) {
		double w = area.getWidth();
		double h = area.getHeight();
		// if the area has zero height or width, we shouldn't draw anything
		if (w <= 0.0 || h <= 0.0) {
			return;
		}
		double t = this.insets.calculateTopInset(h);
		double b = this.insets.calculateBottomInset(h);
		double l = this.insets.calculateLeftInset(w);
		double r = this.insets.calculateRightInset(w);
		double x = area.getX();
		double y = area.getY();
		double x0 = x + l / 2.0;
		double x1 = x + w - r / 2.0;
		double y0 = y + h - b / 2.0;
		double y1 = y + t / 2.0;
		getPaint().setStrokeWidth(getStroke());

		Line2D line = new Line2D.Double();
		if (t > 0.0) {
			line.setLine(x0, y1, x1, y1);
			g2.drawLine((float) line.getX1(), (float) line.getY1(),
					(float) line.getX2(), (float) line.getY2(), getPaint());
			// g2.draw(line);
		}
		if (b > 0.0) {
			line.setLine(x0, y0, x1, y0);
			g2.drawLine((float) line.getX1(), (float) line.getY1(),
					(float) line.getX2(), (float) line.getY2(), getPaint());
		}
		if (l > 0.0) {
			line.setLine(x0, y0, x0, y1);
			g2.drawLine((float) line.getX1(), (float) line.getY1(),
					(float) line.getX2(), (float) line.getY2(), getPaint());
		}
		if (r > 0.0) {
			line.setLine(x1, y0, x1, y1);
			g2.drawLine((float) line.getX1(), (float) line.getY1(),
					(float) line.getX2(), (float) line.getY2(), getPaint());
		}
	}

}
