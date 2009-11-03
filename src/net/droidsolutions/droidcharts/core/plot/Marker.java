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
 * -----------
 * Marker.java
 * -----------
 * (C) Copyright 2002-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Nicolas Brodu;
 *
 * Changes
 * -------
 * 02-Jul-2002 : Added extra constructor, standard header and Javadoc
 *               comments (DG);
 * 20-Aug-2002 : Added the outline stroke attribute (DG);
 * 02-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 16-Oct-2002 : Added new constructor (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 21-May-2003 : Added labels (DG);
 * 11-Sep-2003 : Implemented Cloneable (NB);
 * 05-Nov-2003 : Added checks to ensure some attributes are never null (DG);
 * 11-Feb-2003 : Moved to org.jfree.chart.plot package, plus significant API
 *               changes to support IntervalMarker in plots (DG);
 * 14-Jun-2004 : Updated equals() method (DG);
 * 21-Jan-2005 : Added settings to control direction of horizontal and
 *               vertical label offsets (DG);
 * 01-Jun-2005 : Modified to use only one label offset type - this will be
 *               applied to the domain or range axis as appropriate (DG);
 * 06-Jun-2005 : Fix equals() method to handle GradientPaint (DG);
 * 19-Aug-2005 : Changed constructor from public --> protected (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 05-Sep-2006 : Added MarkerChangeListener support (DG);
 * 26-Sep-2007 : Fix for serialization bug 1802195 (DG);
 *
 */

package net.droidsolutions.droidcharts.core.plot;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.common.LengthAdjustmentType;
import net.droidsolutions.droidcharts.common.RectangleAnchor;
import net.droidsolutions.droidcharts.common.RectangleInsets;
import net.droidsolutions.droidcharts.common.TextAnchor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * The base class for markers that can be added to plots to highlight a value or
 * range of values. <br>
 * <br>
 * An event notification mechanism was added to this class in JFreeChart version
 * 1.0.3.
 */
public abstract class Marker implements Cloneable, Serializable {

	/** For serialization. */
	private static final long serialVersionUID = -734389651405327166L;

	/** The paint (null is not allowed). */
	private transient Paint paint;

	/** The stroke (null is not allowed). */
	private transient float stroke;

	/** The outline paint. */
	private transient Paint outlinePaint;

	/** The outline stroke. */
	private transient float outlineStroke;

	/** The alpha transparency. */
	private float alpha;

	/** The label. */
	private String label = null;

	/** The label font. */
	private Font labelFont;

	/** The label paint. */
	private transient Paint labelPaint;

	/** The label position. */
	private RectangleAnchor labelAnchor;

	/** The text anchor for the label. */
	private TextAnchor labelTextAnchor;

	/** The label offset from the marker rectangle. */
	private RectangleInsets labelOffset;

	/**
	 * The offset type for the domain or range axis (never <code>null</code>).
	 */
	private LengthAdjustmentType labelOffsetType;

	/**
	 * Creates a new marker with default attributes.
	 */
	protected Marker() {
		this(Color.GRAY);
	}

	/**
	 * Constructs a new marker.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 */
	protected Marker(int paint) {
		this(paint, 0.5f, Color.GRAY, 0.5f, 0.80f);
	}

	/**
	 * Constructs a new marker.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * @param stroke
	 *            the stroke (<code>null</code> not permitted).
	 * @param outlinePaint
	 *            the outline paint (<code>null</code> permitted).
	 * @param outlineStroke
	 *            the outline stroke (<code>null</code> permitted).
	 * @param alpha
	 *            the alpha transparency (must be in the range 0.0f to 1.0f).
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>paint</code> or <code>stroke</code> is
	 *             <code>null</code>, or <code>alpha</code> is not in the
	 *             specified range.
	 */
	protected Marker(int paint, float stroke, int outlinePaint,
			float outlineStroke, float alpha) {

		if (alpha < 0.0f || alpha > 1.0f)
			throw new IllegalArgumentException(
					"The 'alpha' value must be in the range 0.0f to 1.0f");
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(paint);
		this.paint = p;
		this.stroke = stroke;
		p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(outlinePaint);
		this.outlinePaint = p;
		this.outlineStroke = outlineStroke;
		this.alpha = alpha;

		this.labelFont = new Font("SansSerif", Typeface.NORMAL, 9);
		p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(Color.BLACK);
		this.labelPaint = p;
		this.labelAnchor = RectangleAnchor.TOP_LEFT;
		this.labelOffset = new RectangleInsets(3.0, 3.0, 3.0, 3.0);
		this.labelOffsetType = LengthAdjustmentType.CONTRACT;
		this.labelTextAnchor = TextAnchor.CENTER;

	}

	/**
	 * Returns the paint.
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @see #setPaint(Paint)
	 */
	public Paint getPaint() {
		return this.paint;
	}

	/**
	 * Sets the paint and sends a {@link MarkerChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * 
	 * @see #getPaint()
	 */
	public void setPaint(Paint paint) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.paint = paint;
	}

	/**
	 * Returns the stroke.
	 * 
	 * @return The stroke (never <code>null</code>).
	 * 
	 * @see #setStroke(Stroke)
	 */
	public float getStroke() {
		return this.stroke;
	}

	/**
	 * Sets the stroke and sends a {@link MarkerChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param stroke
	 *            the stroke (<code>null</code> not permitted).
	 * 
	 * @see #getStroke()
	 */
	public void setStroke(float stroke) {

		this.stroke = stroke;
	}

	/**
	 * Returns the outline paint.
	 * 
	 * @return The outline paint (possibly <code>null</code>).
	 * 
	 * @see #setOutlinePaint(Paint)
	 */
	public Paint getOutlinePaint() {
		return this.outlinePaint;
	}

	/**
	 * Sets the outline paint and sends a {@link MarkerChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @see #getOutlinePaint()
	 */
	public void setOutlinePaint(Paint paint) {
		this.outlinePaint = paint;
	}

	/**
	 * Returns the outline stroke.
	 * 
	 * @return The outline stroke (possibly <code>null</code>).
	 * 
	 * @see #setOutlineStroke(Stroke)
	 */
	public float getOutlineStroke() {
		return this.outlineStroke;
	}

	/**
	 * Sets the outline stroke and sends a {@link MarkerChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param stroke
	 *            the stroke (<code>null</code> permitted).
	 * 
	 * @see #getOutlineStroke()
	 */
	public void setOutlineStroke(float stroke) {
		this.outlineStroke = stroke;
	}

	/**
	 * Returns the alpha transparency.
	 * 
	 * @return The alpha transparency.
	 * 
	 * @see #setAlpha(float)
	 */
	public float getAlpha() {
		return this.alpha;
	}

	/**
	 * Sets the alpha transparency that should be used when drawing the marker,
	 * and sends a {@link MarkerChangeEvent} to all registered listeners. The
	 * alpha transparency is a value in the range 0.0f (completely transparent)
	 * to 1.0f (completely opaque).
	 * 
	 * @param alpha
	 *            the alpha transparency (must be in the range 0.0f to 1.0f).
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>alpha</code> is not in the specified range.
	 * 
	 * @see #getAlpha()
	 */
	public void setAlpha(float alpha) {
		if (alpha < 0.0f || alpha > 1.0f)
			throw new IllegalArgumentException(
					"The 'alpha' value must be in the range 0.0f to 1.0f");
		this.alpha = alpha;
	}

	/**
	 * Returns the label (if <code>null</code> no label is displayed).
	 * 
	 * @return The label (possibly <code>null</code>).
	 * 
	 * @see #setLabel(String)
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Sets the label (if <code>null</code> no label is displayed) and sends a
	 * {@link MarkerChangeEvent} to all registered listeners.
	 * 
	 * @param label
	 *            the label (<code>null</code> permitted).
	 * 
	 * @see #getLabel()
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Returns the label font.
	 * 
	 * @return The label font (never <code>null</code>).
	 * 
	 * @see #setLabelFont(Font)
	 */
	public Font getLabelFont() {
		return this.labelFont;
	}

	/**
	 * Sets the label font and sends a {@link MarkerChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param font
	 *            the font (<code>null</code> not permitted).
	 * 
	 * @see #getLabelFont()
	 */
	public void setLabelFont(Font font) {
		if (font == null) {
			throw new IllegalArgumentException("Null 'font' argument.");
		}
		this.labelFont = font;
	}

	/**
	 * Returns the label paint.
	 * 
	 * @return The label paint (never </code>null</code>).
	 * 
	 * @see #setLabelPaint(Paint)
	 */
	public Paint getLabelPaint() {
		return this.labelPaint;
	}

	/**
	 * Sets the label paint and sends a {@link MarkerChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * 
	 * @see #getLabelPaint()
	 */
	public void setLabelPaint(Paint paint) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.labelPaint = paint;
	}

	/**
	 * Returns the label anchor. This defines the position of the label anchor,
	 * relative to the bounds of the marker.
	 * 
	 * @return The label anchor (never <code>null</code>).
	 * 
	 * @see #setLabelAnchor(RectangleAnchor)
	 */
	public RectangleAnchor getLabelAnchor() {
		return this.labelAnchor;
	}

	/**
	 * Sets the label anchor and sends a {@link MarkerChangeEvent} to all
	 * registered listeners. The anchor defines the position of the label
	 * anchor, relative to the bounds of the marker.
	 * 
	 * @param anchor
	 *            the anchor (<code>null</code> not permitted).
	 * 
	 * @see #getLabelAnchor()
	 */
	public void setLabelAnchor(RectangleAnchor anchor) {
		if (anchor == null) {
			throw new IllegalArgumentException("Null 'anchor' argument.");
		}
		this.labelAnchor = anchor;
	}

	/**
	 * Returns the label offset.
	 * 
	 * @return The label offset (never <code>null</code>).
	 * 
	 * @see #setLabelOffset(RectangleInsets)
	 */
	public RectangleInsets getLabelOffset() {
		return this.labelOffset;
	}

	/**
	 * Sets the label offset and sends a {@link MarkerChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param offset
	 *            the label offset (<code>null</code> not permitted).
	 * 
	 * @see #getLabelOffset()
	 */
	public void setLabelOffset(RectangleInsets offset) {
		if (offset == null) {
			throw new IllegalArgumentException("Null 'offset' argument.");
		}
		this.labelOffset = offset;
	}

	/**
	 * Returns the label offset type.
	 * 
	 * @return The type (never <code>null</code>).
	 * 
	 * @see #setLabelOffsetType(LengthAdjustmentType)
	 */
	public LengthAdjustmentType getLabelOffsetType() {
		return this.labelOffsetType;
	}

	/**
	 * Sets the label offset type and sends a {@link MarkerChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param adj
	 *            the type (<code>null</code> not permitted).
	 * 
	 * @see #getLabelOffsetType()
	 */
	public void setLabelOffsetType(LengthAdjustmentType adj) {
		if (adj == null) {
			throw new IllegalArgumentException("Null 'adj' argument.");
		}
		this.labelOffsetType = adj;
	}

	/**
	 * Returns the label text anchor.
	 * 
	 * @return The label text anchor (never <code>null</code>).
	 * 
	 * @see #setLabelTextAnchor(TextAnchor)
	 */
	public TextAnchor getLabelTextAnchor() {
		return this.labelTextAnchor;
	}

	/**
	 * Sets the label text anchor and sends a {@link MarkerChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param anchor
	 *            the label text anchor (<code>null</code> not permitted).
	 * 
	 * @see #getLabelTextAnchor()
	 */
	public void setLabelTextAnchor(TextAnchor anchor) {
		if (anchor == null) {
			throw new IllegalArgumentException("Null 'anchor' argument.");
		}
		this.labelTextAnchor = anchor;
	}

}
