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
 * -----------------
 * TextFragment.java
 * -----------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: TextFragment.java,v 1.16 2004/05/19 08:21:22 mungady Exp $
 *
 * Changes
 * -------
 * 07-Nov-2003 : Version 1 (DG);
 * 25-Nov-2003 : Fixed bug in the dimension calculation (DG);
 * 22-Dec-2003 : Added workaround for Java bug 4245442 (DG);
 * 29-Jan-2004 : Added paint attribute (DG);
 * 22-Mar-2004 : Added equals() method and implemented Serializable (DG);
 * 01-Apr-2004 : Changed java.awt.geom.Dimension2D to org.jfree.ui.Size2D because of 
 *               JDK bug 4976448 which persists on JDK 1.3.1 (DG);
 *
 */

package net.droidsolutions.droidcharts.core.text;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.common.RefineryUtilities;
import net.droidsolutions.droidcharts.common.Size2D;
import net.droidsolutions.droidcharts.common.TextAnchor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * A text item, with an associated font, that fits on a single line (see
 * {@link TextLine}). Instances of the class are immutable.
 */
public class TextFragment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The default font. */
	public static final Font DEFAULT_FONT = new Font("Serif", Typeface.BOLD, 12);

	/** The default text color. */
	public static final Paint DEFAULT_PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		DEFAULT_PAINT.setARGB(00, 0, 0, 0);
	}

	/** The text. */
	private String text;

	/** The font. */
	private Font font;

	/** The text color. */
	private Paint paint;

	/**
	 * Creates a new text fragment.
	 * 
	 * @param text
	 *            the text (<code>null</code> not permitted).
	 */
	public TextFragment(final String text) {
		this(text, DEFAULT_FONT, DEFAULT_PAINT);
	}

	/**
	 * Creates a new text fragment.
	 * 
	 * @param text
	 *            the text (<code>null</code> not permitted).
	 * @param font
	 *            the font (<code>null</code> not permitted).
	 */
	public TextFragment(final String text, final Font font) {
		this(text, font, DEFAULT_PAINT);
	}

	/**
	 * Creates a new text fragment.
	 * 
	 * @param text
	 *            the text (<code>null</code> not permitted).
	 * @param font
	 *            the font (<code>null</code> not permitted).
	 * @param paint
	 *            the text color (<code>null</code> not permitted).
	 */
	public TextFragment(final String text, final Font font, final Paint paint) {
		if (text == null) {
			throw new IllegalArgumentException("Null 'text' argument.");
		}
		if (font == null) {
			throw new IllegalArgumentException("Null 'font' argument.");
		}
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.text = text;
		this.font = font;
		this.paint = paint;
	}

	/**
	 * Returns the text.
	 * 
	 * @return The text (possibly <code>null</code>).
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Returns the font.
	 * 
	 * @return The font (never <code>null</code>).
	 */
	public Font getFont() {
		return this.font;
	}

	/**
	 * Returns the text paint.
	 * 
	 * @return The text paint (never <code>null</code>).
	 */
	public Paint getPaint() {
		return this.paint;
	}

	/**
	 * Draws the text fragment.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param anchorX
	 *            the x-coordinate of the anchor point.
	 * @param anchorY
	 *            the y-coordinate of the anchor point.
	 * @param anchor
	 *            the location of the text that is aligned to the anchor point.
	 * @param rotateX
	 *            the x-coordinate of the rotation point.
	 * @param rotateY
	 *            the y-coordinate of the rotation point.
	 * @param angle
	 *            the angle.
	 */
	public void draw(final Canvas g2, final float anchorX, final float anchorY,
			final TextAnchor anchor, final float rotateX, final float rotateY,
			final double angle, Paint paint) {

		RefineryUtilities.drawRotatedString(this.text, g2, anchorX, anchorY,
				paint, (float)angle);

	}

	/**
	 * Calculates the dimensions of the text fragment.
	 * 
	 * @param g2
	 *            the graphics device.
	 * 
	 * @return The width and height of the text.
	 */
	public Size2D calculateDimensions(final Canvas g2) {
		Rectangle2D rec = TextUtilities.getTextBounds(text, paint);
		return new Size2D(rec.getWidth(), rec.getHeight());
	}

	/**
	 * Calculates the vertical offset between the baseline and the specified
	 * text anchor.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param anchor
	 *            the anchor.
	 * 
	 * @return the offset.
	 */
	public float calculateBaselineOffset(final Canvas g2,
			final TextAnchor anchor) {
		// TODO MAKE SOMETHIMG MEANINGFULL
		return 0.0f;
	}

	/**
	 * Tests this instance for equality with an arbitrary object.
	 * 
	 * @param obj
	 *            the object to test against (<code>null</code> permitted).
	 * 
	 * @return A boolean.
	 */
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof TextFragment) {
			final TextFragment tf = (TextFragment) obj;
			if (!this.text.equals(tf.text)) {
				return false;
			}
			if (!this.font.equals(tf.font)) {
				return false;
			}
			if (!this.paint.equals(tf.paint)) {
				return false;
			}
			return true;
		}
		return false;
	}

}
