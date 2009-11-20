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
 * ---------------------------
 * DefaultDrawingSupplier.java
 * ---------------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Jeremy Bowman;
 *
 * Changes
 * -------
 * 16-Jan-2003 : Version 1 (DG);
 * 17-Jan-2003 : Added stroke method, renamed DefaultPaintSupplier
 *               --> DefaultDrawingSupplier (DG)
 * 27-Jan-2003 : Incorporated code from SeriesShapeFactory, originally
 *               contributed by Jeremy Bowman (DG);
 * 25-Mar-2003 : Implemented Serializable (DG);
 * 20-Aug-2003 : Implemented Cloneable and PublicCloneable (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 13-Jun-2007 : Added fillPaintSequence (DG);
 *
 */

package net.droidsolutions.droidcharts.core.plot;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Ellipse2D;
import net.droidsolutions.droidcharts.awt.Polygon;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.awt.Shape;
import net.droidsolutions.droidcharts.core.ChartColor;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * A default implementation of the {@link DrawingSupplier} interface. All
 * {@link Plot} instances have a new instance of this class installed by
 * default.
 */
public class DefaultDrawingSupplier implements DrawingSupplier, Cloneable,
		Serializable {

	/** For serialization. */
	private static final long serialVersionUID = -7339847061039422538L;

	/** The default fill paint sequence. */
	public static final Paint[] DEFAULT_PAINT_SEQUENCE = ChartColor
			.createDefaultPaintArray();

	public static final Paint white = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		white.setColor(Color.WHITE);
	}

	public static final Paint ltGray = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		ltGray.setColor(Color.LTGRAY);
	}

	/** The default outline paint sequence. */
	public static final Paint[] DEFAULT_OUTLINE_PAINT_SEQUENCE = new Paint[] { ltGray };

	/** The default fill paint sequence. */
	public static final Paint[] DEFAULT_FILL_PAINT_SEQUENCE = new Paint[] { white };

	/** The default stroke sequence. */
	public static final float[] DEFAULT_STROKE_SEQUENCE = new float[] { 5.f };

	/** The default outline stroke sequence. */
	public static final float[] DEFAULT_OUTLINE_STROKE_SEQUENCE = new float[] { 1.0f };

	/** The default shape sequence. */
	public static final Shape[] DEFAULT_SHAPE_SEQUENCE = createStandardSeriesShapes();

	/** The paint sequence. */
	private transient Paint[] paintSequence;

	/** The current paint index. */
	private int paintIndex;

	/** The outline paint sequence. */
	private transient Paint[] outlinePaintSequence;

	/** The current outline paint index. */
	private int outlinePaintIndex;

	/** The fill paint sequence. */
	private transient Paint[] fillPaintSequence;

	/** The current fill paint index. */
	private int fillPaintIndex;

	/** The stroke sequence. */
	private transient float[] strokeSequence;

	/** The current stroke index. */
	private int strokeIndex;

	/** The outline stroke sequence. */
	private transient float[] outlineStrokeSequence;

	/** The current outline stroke index. */
	private int outlineStrokeIndex;

	/** The shape sequence. */
	private transient Shape[] shapeSequence;

	/** The current shape index. */
	private int shapeIndex;

	/**
	 * Creates a new supplier, with default sequences for fill paint, outline
	 * paint, stroke and shapes.
	 */
	public DefaultDrawingSupplier() {

		this(DEFAULT_PAINT_SEQUENCE, DEFAULT_FILL_PAINT_SEQUENCE,
				DEFAULT_OUTLINE_PAINT_SEQUENCE, DEFAULT_STROKE_SEQUENCE,
				DEFAULT_OUTLINE_STROKE_SEQUENCE, DEFAULT_SHAPE_SEQUENCE);

	}

	/**
	 * Creates a new supplier.
	 * 
	 * @param paintSequence
	 *            the fill paint sequence.
	 * @param outlinePaintSequence
	 *            the outline paint sequence.
	 * @param strokeSequence
	 *            the stroke sequence.
	 * @param outlineStrokeSequence
	 *            the outline stroke sequence.
	 * @param shapeSequence
	 *            the shape sequence.
	 */
	public DefaultDrawingSupplier(Paint[] paintSequence,
			Paint[] outlinePaintSequence, float[] strokeSequence,
			float[] outlineStrokeSequence, Shape[] shapeSequence) {

		this.paintSequence = paintSequence;
		this.fillPaintSequence = DEFAULT_FILL_PAINT_SEQUENCE;
		this.outlinePaintSequence = outlinePaintSequence;
		this.strokeSequence = strokeSequence;
		this.outlineStrokeSequence = outlineStrokeSequence;
		this.shapeSequence = shapeSequence;

	}

	/**
	 * Creates a new supplier.
	 * 
	 * @param paintSequence
	 *            the paint sequence.
	 * @param fillPaintSequence
	 *            the fill paint sequence.
	 * @param outlinePaintSequence
	 *            the outline paint sequence.
	 * @param strokeSequence
	 *            the stroke sequence.
	 * @param outlineStrokeSequence
	 *            the outline stroke sequence.
	 * @param shapeSequence
	 *            the shape sequence.
	 * 
	 * @since 1.0.6
	 */
	public DefaultDrawingSupplier(Paint[] paintSequence,
			Paint[] fillPaintSequence, Paint[] outlinePaintSequence,
			float[] strokeSequence, float[] outlineStrokeSequence,
			Shape[] shapeSequence) {

		this.paintSequence = paintSequence;
		this.fillPaintSequence = fillPaintSequence;
		this.outlinePaintSequence = outlinePaintSequence;
		this.strokeSequence = strokeSequence;
		this.outlineStrokeSequence = outlineStrokeSequence;
		this.shapeSequence = shapeSequence;
	}

	/**
	 * Returns the next paint in the sequence.
	 * 
	 * @return The paint.
	 */
	public Paint getNextPaint() {
		Paint result = this.paintSequence[this.paintIndex
				% this.paintSequence.length];
		this.paintIndex++;
		return result;
	}

	/**
	 * Returns the next outline paint in the sequence.
	 * 
	 * @return The paint.
	 */
	public Paint getNextOutlinePaint() {
		Paint result = this.outlinePaintSequence[this.outlinePaintIndex
				% this.outlinePaintSequence.length];
		this.outlinePaintIndex++;
		return result;
	}

	/**
	 * Returns the next fill paint in the sequence.
	 * 
	 * @return The paint.
	 * 
	 * @since 1.0.6
	 */
	public Paint getNextFillPaint() {
		Paint result = this.fillPaintSequence[this.fillPaintIndex
				% this.fillPaintSequence.length];
		this.fillPaintIndex++;
		return result;
	}

	/**
	 * Returns the next stroke in the sequence.
	 * 
	 * @return The stroke.
	 */
	public float getNextStroke() {
		float result = this.strokeSequence[this.strokeIndex
				% this.strokeSequence.length];
		this.strokeIndex++;
		return result;
	}

	/**
	 * Returns the next outline stroke in the sequence.
	 * 
	 * @return The stroke.
	 */
	public float getNextOutlineStroke() {
		float result = this.outlineStrokeSequence[this.outlineStrokeIndex
				% this.outlineStrokeSequence.length];
		this.outlineStrokeIndex++;
		return result;
	}

	/**
	 * Returns the next shape in the sequence.
	 * 
	 * @return The shape.
	 */
	public Shape getNextShape() {
		Shape result = this.shapeSequence[this.shapeIndex
				% this.shapeSequence.length];
		this.shapeIndex++;
		return result;
	}

	/**
	 * Creates an array of standard shapes to display for the items in series on
	 * charts.
	 * 
	 * @return The array of shapes.
	 */
	public static Shape[] createStandardSeriesShapes() {

		Shape[] result = new Shape[10];

		double size = 6.0;
		double delta = size / 2.0;
		int[] xpoints = null;
		int[] ypoints = null;

		// square
		result[0] = new Rectangle2D.Double(-delta, -delta, size, size);
		// circle
		result[1] = new Ellipse2D.Double(-delta, -delta, size, size);

		// up-pointing triangle
		xpoints = intArray(0.0, delta, -delta);
		ypoints = intArray(-delta, delta, delta);
		result[2] = new Polygon(xpoints, ypoints, 3);

		// diamond
		xpoints = intArray(0.0, delta, 0.0, -delta);
		ypoints = intArray(-delta, 0.0, delta, 0.0);
		result[3] = new Polygon(xpoints, ypoints, 4);

		// horizontal rectangle
		result[4] = new Rectangle2D.Double(-delta, -delta / 2, size, size / 2);

		// down-pointing triangle
		xpoints = intArray(-delta, +delta, 0.0);
		ypoints = intArray(-delta, -delta, delta);
		result[5] = new Polygon(xpoints, ypoints, 3);

		// horizontal ellipse
		result[6] = new Ellipse2D.Double(-delta, -delta / 2, size, size / 2);

		// right-pointing triangle
		xpoints = intArray(-delta, delta, -delta);
		ypoints = intArray(-delta, 0.0, delta);
		result[7] = new Polygon(xpoints, ypoints, 3);

		// vertical rectangle
		result[8] = new Rectangle2D.Double(-delta / 2, -delta, size / 2, size);

		// left-pointing triangle
		xpoints = intArray(-delta, delta, delta);
		ypoints = intArray(0.0, -delta, +delta);
		result[9] = new Polygon(xpoints, ypoints, 3);

		return result;

	}

	/**
	 * Helper method to avoid lots of explicit casts in getShape(). Returns an
	 * array containing the provided doubles cast to ints.
	 * 
	 * @param a
	 *            x
	 * @param b
	 *            y
	 * @param c
	 *            z
	 * 
	 * @return int[3] with converted params.
	 */
	private static int[] intArray(double a, double b, double c) {
		return new int[] { (int) a, (int) b, (int) c };
	}

	/**
	 * Helper method to avoid lots of explicit casts in getShape(). Returns an
	 * array containing the provided doubles cast to ints.
	 * 
	 * @param a
	 *            x
	 * @param b
	 *            y
	 * @param c
	 *            z
	 * @param d
	 *            t
	 * 
	 * @return int[4] with converted params.
	 */
	private static int[] intArray(double a, double b, double c, double d) {
		return new int[] { (int) a, (int) b, (int) c, (int) d };
	}

}
