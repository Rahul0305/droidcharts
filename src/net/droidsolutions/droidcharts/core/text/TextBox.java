/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
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
 * ------------
 * TextBox.java
 * ------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: TextBox.java,v 1.12 2005/11/16 15:58:41 taqua Exp $
 *
 * Changes
 * -------
 * 09-Mar-2004 : Version 1 (DG);
 * 22-Mar-2004 : Added equals() method and implemented Serializable (DG);
 * 09-Nov-2004 : Renamed getAdjustedHeight() --> calculateExtendedHeight() in 
 *               Spacer class (DG);
 * 22-Feb-2005 : Replaced Spacer with RectangleInsets (DG);
 *
 */

package net.droidsolutions.droidcharts.core.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.common.RectangleAnchor;
import net.droidsolutions.droidcharts.common.RectangleInsets;
import net.droidsolutions.droidcharts.common.Size2D;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * A box containing a text block.
 * 
 * @author David Gilbert
 */
public class TextBox implements Serializable {

	private static final Paint BLACK = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		BLACK.setColor(Color.BLACK);
	}

	private static final Paint GRAY = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		GRAY.setColor(Color.GRAY);
	}

	private static final Paint WHITE = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		WHITE.setColor(Color.WHITE);
	}
	/** For serialization. */
	private static final long serialVersionUID = 3360220213180203706L;

	/** The outline paint. */
	private transient Paint outlinePaint;

	/** The outline stroke. */
	private transient Float outlineStroke;

	/** The interior space. */
	private RectangleInsets interiorGap;

	/** The background paint. */
	private transient Paint backgroundPaint;

	/** The shadow paint. */
	private transient Paint shadowPaint;

	/** The shadow x-offset. */
	private double shadowXOffset = 2.0;

	/** The shadow y-offset. */
	private double shadowYOffset = 2.0;

	/** The text block. */
	private TextBlock textBlock;

	/**
	 * Creates an empty text box.
	 */
	public TextBox() {
		this((TextBlock) null);
	}

	/**
	 * Creates a text box.
	 * 
	 * @param text
	 *            the text.
	 */
	public TextBox(final String text) {
		this((TextBlock) null);
		if (text != null) {
			this.textBlock = new TextBlock();
			this.textBlock.addLine(text, new Font("SansSerif", Typeface.NORMAL,
					10), BLACK);
		}
	}

	/**
	 * Creates a new text box.
	 * 
	 * @param block
	 *            the text block.
	 */
	public TextBox(final TextBlock block) {
		this.outlinePaint = BLACK;
		this.outlineStroke = 1.0f;
		this.interiorGap = new RectangleInsets(1.0, 3.0, 1.0, 3.0);
		this.backgroundPaint = WHITE;
		this.shadowPaint = GRAY;
		this.shadowXOffset = 2.0;
		this.shadowYOffset = 2.0;
		this.textBlock = block;
	}

	/**
	 * Returns the outline paint.
	 * 
	 * @return The outline paint.
	 */
	public Paint getOutlinePaint() {
		return this.outlinePaint;
	}

	/**
	 * Sets the outline paint.
	 * 
	 * @param paint
	 *            the paint.
	 */
	public void setOutlinePaint(final Paint paint) {
		this.outlinePaint = paint;
	}

	/**
	 * Returns the outline stroke.
	 * 
	 * @return The outline stroke.
	 */
	public Float getOutlineStroke() {
		return this.outlineStroke;
	}

	/**
	 * Sets the outline stroke.
	 * 
	 * @param stroke
	 *            the stroke.
	 */
	public void setOutlineStroke(final Float stroke) {
		this.outlineStroke = stroke;
	}

	/**
	 * Returns the interior gap.
	 * 
	 * @return The interior gap.
	 */
	public RectangleInsets getInteriorGap() {
		return this.interiorGap;
	}

	/**
	 * Sets the interior gap.
	 * 
	 * @param gap
	 *            the gap.
	 */
	public void setInteriorGap(final RectangleInsets gap) {
		this.interiorGap = gap;
	}

	/**
	 * Returns the background paint.
	 * 
	 * @return The background paint.
	 */
	public Paint getBackgroundPaint() {
		return this.backgroundPaint;
	}

	/**
	 * Sets the background paint.
	 * 
	 * @param paint
	 *            the paint.
	 */
	public void setBackgroundPaint(final Paint paint) {
		this.backgroundPaint = paint;
	}

	/**
	 * Returns the shadow paint.
	 * 
	 * @return The shadow paint.
	 */
	public Paint getShadowPaint() {
		return this.shadowPaint;
	}

	/**
	 * Sets the shadow paint.
	 * 
	 * @param paint
	 *            the paint.
	 */
	public void setShadowPaint(final Paint paint) {
		this.shadowPaint = paint;
	}

	/**
	 * Returns the x-offset for the shadow effect.
	 * 
	 * @return The offset.
	 */
	public double getShadowXOffset() {
		return this.shadowXOffset;
	}

	/**
	 * Sets the x-offset for the shadow effect.
	 * 
	 * @param offset
	 *            the offset (in Java2D units).
	 */
	public void setShadowXOffset(final double offset) {
		this.shadowXOffset = offset;
	}

	/**
	 * Returns the y-offset for the shadow effect.
	 * 
	 * @return The offset.
	 */
	public double getShadowYOffset() {
		return this.shadowYOffset;
	}

	/**
	 * Sets the y-offset for the shadow effect.
	 * 
	 * @param offset
	 *            the offset (in Java2D units).
	 */
	public void setShadowYOffset(final double offset) {
		this.shadowYOffset = offset;
	}

	/**
	 * Returns the text block.
	 * 
	 * @return The text block.
	 */
	public TextBlock getTextBlock() {
		return this.textBlock;
	}

	/**
	 * Sets the text block.
	 * 
	 * @param block
	 *            the block.
	 */
	public void setTextBlock(final TextBlock block) {
		this.textBlock = block;
	}

	/**
	 * Draws the text box.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param x
	 *            the x-coordinate.
	 * @param y
	 *            the y-coordinate.
	 * @param anchor
	 *            the anchor point.
	 */
	public void draw(final Canvas g2, final float x, final float y,
			final RectangleAnchor anchor) {
		final Size2D d1 = this.textBlock.calculateDimensions(g2);
		final double w = this.interiorGap.extendWidth(d1.getWidth());
		final double h = this.interiorGap.extendHeight(d1.getHeight());
		final Size2D d2 = new Size2D(w, h);
		final Rectangle2D bounds = RectangleAnchor.createRectangle(d2, x, y,
				anchor);

		if (this.shadowPaint != null) {
			final Rectangle2D shadow = new Rectangle2D.Double(bounds.getX()
					+ this.shadowXOffset, bounds.getY() + this.shadowYOffset,
					bounds.getWidth(), bounds.getHeight());
			shadowPaint.setStyle(Paint.Style.FILL);
			g2.drawRect((float) shadow.getMinX(), (float) shadow.getMinY(),
					(float) shadow.getMaxX(), (float) shadow.getMaxY(),
					shadowPaint);

		}
		if (this.backgroundPaint != null) {
			this.backgroundPaint.setStyle(Paint.Style.FILL);

			g2.drawRect((float) bounds.getMinX(), (float) bounds.getMinY(),
					(float) bounds.getMaxX(), (float) bounds.getMaxY(),
					backgroundPaint);
		}

		if (this.outlinePaint != null && this.outlineStroke != null) {
			this.outlinePaint.setStyle(Paint.Style.STROKE);
			this.outlinePaint.setStrokeWidth(outlineStroke);
			g2.drawRect((float) bounds.getMinX(), (float) bounds.getMinY(),
					(float) bounds.getMaxX(), (float) bounds.getMaxY(),
					outlinePaint);
			
		}

		this.textBlock.draw(g2, (float) bounds.getCenterX(), (float) bounds
				.getCenterY(), TextBlockAnchor.CENTER, BLACK);

	}

	/**
	 * Returns the height of the text box.
	 * 
	 * @param g2
	 *            the graphics device.
	 * 
	 * @return The height (in Java2D units).
	 */
	public double getHeight(final Canvas g2) {
		final Size2D d = this.textBlock.calculateDimensions(g2);
		return this.interiorGap.extendHeight(d.getHeight());
	}

}
