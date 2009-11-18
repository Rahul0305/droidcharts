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
 * -------------------
 * MarkerAxisBand.java
 * -------------------
 * (C) Copyright 2000-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Sep-2002 : Updated Javadoc comments (DG);
 * 01-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 08-Nov-2002 : Moved to new package com.jrefinery.chart.axis (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 13-May-2003 : Renamed HorizontalMarkerAxisBand --> MarkerAxisBand (DG);
 * 29-Oct-2003 : Added workaround for font alignment in PDF output (DG);
 * 21-Jan-2004 : Update for renamed method in ValueAxis (DG);
 * 07-Apr-2004 : Changed text bounds calculation (DG);
 *
 */

package net.droidsolutions.droidcharts.core.axis;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.common.RectangleEdge;
import net.droidsolutions.droidcharts.core.plot.IntervalMarker;
import net.droidsolutions.droidcharts.core.text.TextUtilities;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * A band that can be added to a number axis to display regions.
 */
public class MarkerAxisBand implements Serializable {

	/** For serialization. */
	private static final long serialVersionUID = -1729482413886398919L;

	/** The axis that the band belongs to. */
	private NumberAxis axis;

	/** The top outer gap. */
	private double topOuterGap;

	/** The top inner gap. */
	private double topInnerGap;

	/** The bottom outer gap. */
	private double bottomOuterGap;

	/** The bottom inner gap. */
	private double bottomInnerGap;

	/** The font. */
	private Font font;

	/** Storage for the markers. */
	private List markers;

	/**
	 * Constructs a new axis band.
	 * 
	 * @param axis
	 *            the owner.
	 * @param topOuterGap
	 *            the top outer gap.
	 * @param topInnerGap
	 *            the top inner gap.
	 * @param bottomOuterGap
	 *            the bottom outer gap.
	 * @param bottomInnerGap
	 *            the bottom inner gap.
	 * @param font
	 *            the font.
	 */
	public MarkerAxisBand(NumberAxis axis, double topOuterGap,
			double topInnerGap, double bottomOuterGap, double bottomInnerGap,
			Font font) {
		this.axis = axis;
		this.topOuterGap = topOuterGap;
		this.topInnerGap = topInnerGap;
		this.bottomOuterGap = bottomOuterGap;
		this.bottomInnerGap = bottomInnerGap;
		this.font = font;
		this.markers = new java.util.ArrayList();
	}

	/**
	 * Adds a marker to the band.
	 * 
	 * @param marker
	 *            the marker.
	 */
	public void addMarker(IntervalMarker marker) {
		this.markers.add(marker);
	}

	/**
	 * Returns the height of the band.
	 * 
	 * @param g2
	 *            the graphics device.
	 * 
	 * @return The height of the band.
	 */
	public double getHeight(Canvas g2) {

		double result = 0.0;
		if (this.markers.size() > 0) {

			Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
			p.setTextSize(font.getSize());
			Rectangle2D rec = TextUtilities.getTextBounds("123g", p);
			result = this.topOuterGap + this.topInnerGap + rec.getHeight()
					+ this.bottomInnerGap + this.bottomOuterGap;
		}
		return result;

	}

	/**
	 * A utility method that draws a string inside a rectangle.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param bounds
	 *            the rectangle.
	 * @param font
	 *            the font.
	 * @param text
	 *            the text.
	 */
	private void drawStringInRect(Canvas g2, Rectangle2D bounds, Font font,
			String text) {

		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(font.getSize());

		Rectangle2D r = TextUtilities.getTextBounds(text, p);
		double x = bounds.getX();
		if (r.getWidth() < bounds.getWidth()) {
			x = x + (bounds.getWidth() - r.getWidth()) / 2;
		}

		g2.drawText(text, (float) x,
				(float) (bounds.getMaxY() - this.bottomInnerGap), p);
	}

	/**
	 * Draws the band.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param plotArea
	 *            the plot area.
	 * @param dataArea
	 *            the data area.
	 * @param x
	 *            the x-coordinate.
	 * @param y
	 *            the y-coordinate.
	 */
	public void draw(Canvas g2, Rectangle2D plotArea, Rectangle2D dataArea,
			double x, double y) {

		double h = getHeight(g2);
		Iterator iterator = this.markers.iterator();
		while (iterator.hasNext()) {
			IntervalMarker marker = (IntervalMarker) iterator.next();
			double start = Math.max(marker.getStartValue(), this.axis
					.getRange().getLowerBound());
			double end = Math.min(marker.getEndValue(), this.axis.getRange()
					.getUpperBound());
			double s = this.axis.valueToJava2D(start, dataArea,
					RectangleEdge.BOTTOM);
			double e = this.axis.valueToJava2D(end, dataArea,
					RectangleEdge.BOTTOM);
			Rectangle2D r = new Rectangle2D.Double(s, y + this.topOuterGap, e
					- s, h - this.topOuterGap - this.bottomOuterGap);

			Paint p = marker.getPaint();
			p.setAlpha((int) (marker.getAlpha() ));
			p.setStyle(Paint.Style.FILL);
			g2.drawRect((float) r.getMinX(), (float) r.getMinY(), (float) r
					.getMaxX(), (float) r.getMaxY(), p);

			p = marker.getOutlinePaint();
			p.setStyle(Paint.Style.STROKE);
			g2.drawRect((float) r.getMinX(), (float) r.getMinY(), (float) r
					.getMaxX(), (float) r.getMaxY(), p);
			p.setAlpha(255);

			Paint stringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			stringPaint.setColor(Color.BLACK);
			drawStringInRect(g2, r, this.font, marker.getLabel());
		}

	}

}
