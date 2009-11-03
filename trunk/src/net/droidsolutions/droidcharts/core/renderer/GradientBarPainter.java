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
 * -----------------------
 * GradientBarPainter.java
 * -----------------------
 * (C) Copyright 2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 19-Jun-2008 : Version 1 (DG);
 * 15-Aug-2008 : Use outline paint and shadow paint (DG);
 *
 */

package net.droidsolutions.droidcharts.core.renderer;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.awt.RectangularShape;
import net.droidsolutions.droidcharts.common.RectangleEdge;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;

/**
 * An implementation of the {@link BarPainter} interface that uses several
 * gradient fills to enrich the appearance of the bars.
 * 
 * @since 1.0.11
 */
public class GradientBarPainter implements BarPainter, Serializable {

	/** The division point between the first and second gradient regions. */
	protected double g1;

	/** The division point between the second and third gradient regions. */
	protected double g2;

	/** The division point between the third and fourth gradient regions. */
	protected double g3;

	/**
	 * Creates a new instance.
	 */
	public GradientBarPainter() {
		this(0.10, 0.20, 0.80);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param g1
	 * @param g2
	 * @param g3
	 */
	public GradientBarPainter(double g1, double g2, double g3) {
		this.g1 = g1;
		this.g2 = g2;
		this.g3 = g3;
	}

	/**
	 * Paints a single bar instance.
	 * 
	 * @param g2
	 *            the graphics target.
	 * @param renderer
	 *            the renderer.
	 * @param row
	 *            the row index.
	 * @param column
	 *            the column index.
	 * @param bar
	 *            the bar
	 * @param base
	 *            indicates which side of the rectangle is the base of the bar.
	 */
	public void paintBar(Canvas g2, BarRenderer renderer, int row, int column,
			RectangularShape bar, RectangleEdge base) {

		Paint itemPaint = renderer.getItemPaint(row, column);

		if (base == RectangleEdge.TOP || base == RectangleEdge.BOTTOM) {
			Rectangle2D[] regions = splitVerticalBar(bar, this.g1, this.g2,
					this.g3);

			int color[] = new int[2];
			color[0] = itemPaint.getColor();
			color[1] = Color.WHITE;
			GradientDrawable grad = new GradientDrawable(
					Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[0].getWidth(), (int) regions[0]
					.getHeight());
			grad.setBounds((int) regions[0].getMinX(), (int) regions[0]
					.getMinY(), (int) regions[0].getMaxX(), (int) regions[0]
					.getMaxY());
			grad.draw(g2);

			grad = new GradientDrawable(Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[1].getWidth(), (int) regions[1]
					.getHeight());
			grad.setBounds((int) regions[1].getMinX(), (int) regions[1]
					.getMinY(), (int) regions[1].getMaxX(), (int) regions[1]
					.getMaxY());
			grad.draw(g2);

			grad = new GradientDrawable(Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[2].getWidth(), (int) regions[2]
					.getHeight());
			grad.setBounds((int) regions[2].getMinX(), (int) regions[2]
					.getMinY(), (int) regions[2].getMaxX(), (int) regions[2]
					.getMaxY());
			grad.draw(g2);

			grad = new GradientDrawable(Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[3].getWidth(), (int) regions[3]
					.getHeight());
			grad.setBounds((int) regions[3].getMinX(), (int) regions[3]
					.getMinY(), (int) regions[3].getMaxX(), (int) regions[3]
					.getMaxY());
			grad.draw(g2);
		} else if (base == RectangleEdge.LEFT || base == RectangleEdge.RIGHT) {
			Rectangle2D[] regions = splitHorizontalBar(bar, this.g1, this.g2,
					this.g3);
			int color[] = new int[2];
			color[0] = itemPaint.getColor();
			color[1] = Color.WHITE;
			GradientDrawable grad = new GradientDrawable(
					Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[0].getWidth(), (int) regions[0]
					.getHeight());
			grad.setBounds((int) regions[0].getMinX(), (int) regions[0]
					.getMinY(), (int) regions[0].getMaxX(), (int) regions[0]
					.getMaxY());
			grad.draw(g2);

			grad = new GradientDrawable(Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[1].getWidth(), (int) regions[1]
					.getHeight());
			grad.setBounds((int) regions[1].getMinX(), (int) regions[1]
					.getMinY(), (int) regions[1].getMaxX(), (int) regions[1]
					.getMaxY());
			grad.draw(g2);

			grad = new GradientDrawable(Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[2].getWidth(), (int) regions[2]
					.getHeight());
			grad.setBounds((int) regions[2].getMinX(), (int) regions[2]
					.getMinY(), (int) regions[2].getMaxX(), (int) regions[2]
					.getMaxY());
			grad.draw(g2);

			grad = new GradientDrawable(Orientation.TOP_BOTTOM, color);
			grad.setSize((int) regions[3].getWidth(), (int) regions[3]
					.getHeight());
			grad.setBounds((int) regions[3].getMinX(), (int) regions[3]
					.getMinY(), (int) regions[3].getMaxX(), (int) regions[3]
					.getMaxY());
			grad.draw(g2);

		}

		// draw the outline...
		if (renderer.isDrawBarOutline()
		/* && state.getBarWidth() > renderer.BAR_OUTLINE_WIDTH_THRESHOLD */) {
			float stroke = renderer.getItemOutlineStroke(row, column);
			Paint paint = renderer.getItemOutlinePaint(row, column);
			if (stroke != 0.0f && paint != null) {
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(stroke);
				g2.drawRect((float) bar.getMinX(), (float) bar.getMinY(),
						(float) bar.getMaxX(), (float) bar.getMaxY(), paint);
			}
		}

	}

	/**
	 * Paints a single bar instance.
	 * 
	 * @param g2
	 *            the graphics target.
	 * @param renderer
	 *            the renderer.
	 * @param row
	 *            the row index.
	 * @param column
	 *            the column index.
	 * @param bar
	 *            the bar
	 * @param base
	 *            indicates which side of the rectangle is the base of the bar.
	 * @param pegShadow
	 *            peg the shadow to the base of the bar?
	 */
	public void paintBarShadow(Canvas g2, BarRenderer renderer, int row,
			int column, RectangularShape bar, RectangleEdge base,
			boolean pegShadow) {

		// handle a special case - if the bar colour has alpha == 0, it is
		// invisible so we shouldn't draw any shadow
		Paint itemPaint = renderer.getItemPaint(row, column);

		if (itemPaint.getAlpha() == 0) {
			return;
		}

		RectangularShape shadow = createShadow(bar,
				renderer.getShadowXOffset(), renderer.getShadowYOffset(), base,
				pegShadow);
		Paint p = renderer.getShadowPaint();
		p.setStyle(Paint.Style.FILL);
		g2.drawRect((float) shadow.getMinX(), (float) shadow.getMinY(),
				(float) shadow.getMaxX(), (float) shadow.getMaxY(), p);

	}

	/**
	 * Creates a shadow for the bar.
	 * 
	 * @param bar
	 *            the bar shape.
	 * @param xOffset
	 *            the x-offset for the shadow.
	 * @param yOffset
	 *            the y-offset for the shadow.
	 * @param base
	 *            the edge that is the base of the bar.
	 * @param pegShadow
	 *            peg the shadow to the base?
	 * 
	 * @return A rectangle for the shadow.
	 */
	private Rectangle2D createShadow(RectangularShape bar, double xOffset,
			double yOffset, RectangleEdge base, boolean pegShadow) {
		double x0 = bar.getMinX();
		double x1 = bar.getMaxX();
		double y0 = bar.getMinY();
		double y1 = bar.getMaxY();
		if (base == RectangleEdge.TOP) {
			x0 += xOffset;
			x1 += xOffset;
			if (!pegShadow) {
				y0 += yOffset;
			}
			y1 += yOffset;
		} else if (base == RectangleEdge.BOTTOM) {
			x0 += xOffset;
			x1 += xOffset;
			y0 += yOffset;
			if (!pegShadow) {
				y1 += yOffset;
			}
		} else if (base == RectangleEdge.LEFT) {
			if (!pegShadow) {
				x0 += xOffset;
			}
			x1 += xOffset;
			y0 += yOffset;
			y1 += yOffset;
		} else if (base == RectangleEdge.RIGHT) {
			x0 += xOffset;
			if (!pegShadow) {
				x1 += xOffset;
			}
			y0 += yOffset;
			y1 += yOffset;
		}
		return new Rectangle2D.Double(x0, y0, (x1 - x0), (y1 - y0));
	}

	/**
	 * Splits a bar into subregions (elsewhere, these subregions will have
	 * different gradients applied to them).
	 * 
	 * @param bar
	 *            the bar shape.
	 * @param a
	 *            the first division.
	 * @param b
	 *            the second division.
	 * @param c
	 *            the third division.
	 * 
	 * @return An array containing four subregions.
	 */
	protected Rectangle2D[] splitVerticalBar(RectangularShape bar, double a,
			double b, double c) {
		Rectangle2D[] result = new Rectangle2D[4];
		double x0 = bar.getMinX();
		double x1 = Math.rint(x0 + (bar.getWidth() * a));
		double x2 = Math.rint(x0 + (bar.getWidth() * b));
		double x3 = Math.rint(x0 + (bar.getWidth() * c));
		result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(), x1
				- x0, bar.getHeight());
		result[1] = new Rectangle2D.Double(x1, bar.getMinY(), x2 - x1, bar
				.getHeight());
		result[2] = new Rectangle2D.Double(x2, bar.getMinY(), x3 - x2, bar
				.getHeight());
		result[3] = new Rectangle2D.Double(x3, bar.getMinY(), bar.getMaxX()
				- x3, bar.getHeight());
		return result;
	}

	/**
	 * Splits a bar into subregions (elsewhere, these subregions will have
	 * different gradients applied to them).
	 * 
	 * @param bar
	 *            the bar shape.
	 * @param a
	 *            the first division.
	 * @param b
	 *            the second division.
	 * @param c
	 *            the third division.
	 * 
	 * @return An array containing four subregions.
	 */
	protected Rectangle2D[] splitHorizontalBar(RectangularShape bar, double a,
			double b, double c) {
		Rectangle2D[] result = new Rectangle2D[4];
		double y0 = bar.getMinY();
		double y1 = Math.rint(y0 + (bar.getHeight() * a));
		double y2 = Math.rint(y0 + (bar.getHeight() * b));
		double y3 = Math.rint(y0 + (bar.getHeight() * c));
		result[0] = new Rectangle2D.Double(bar.getMinX(), bar.getMinY(), bar
				.getWidth(), y1 - y0);
		result[1] = new Rectangle2D.Double(bar.getMinX(), y1, bar.getWidth(),
				y2 - y1);
		result[2] = new Rectangle2D.Double(bar.getMinX(), y2, bar.getWidth(),
				y3 - y2);
		result[3] = new Rectangle2D.Double(bar.getMinX(), y3, bar.getWidth(),
				bar.getMaxY() - y3);
		return result;
	}

}
