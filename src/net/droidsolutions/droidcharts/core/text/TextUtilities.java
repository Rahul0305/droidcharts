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
 * ------------------
 * TextUtilities.java
 * ------------------
 * (C) Copyright 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: TextUtilities.java,v 1.10 2004/05/21 17:32:22 mungady Exp $
 *
 * Changes
 * -------
 * 07-Jan-2004 : Version 1 (DG);
 * 24-Mar-2004 : Added 'paint' argument to createTextBlock() method (DG);
 * 07-Apr-2004 : Added getTextBounds() method and useFontMetricsGetStringBounds flag (DG);
 * 08-Apr-2004 : Changed word break iterator to line break iterator in the createTextBlock()
 *               method - see bug report 926074 (DG);
 *
 */

package net.droidsolutions.droidcharts.core.text;

import java.text.BreakIterator;

import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.awt.Rectangle;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.common.TextAnchor;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Some utility methods for working with text.
 */
public abstract class TextUtilities {

	/**
	 * Creates a new text block from the given string.
	 * 
	 * @param text
	 *            the text.
	 * @param font
	 *            the font.
	 * @param paint
	 *            the paint.
	 * @param maxWidth
	 *            the maximum width for each line.
	 * @param measurer
	 *            the text measurer.
	 * 
	 * @return A text block.
	 */
	public static TextBlock createTextBlock(final String text, final Font font,
			final Paint paint, final float maxWidth, final TextMeasurer measurer) {
		return createTextBlock(text, font, paint, maxWidth, Integer.MAX_VALUE,
				measurer);
	}

	/**
	 * Creates a {@link TextBlock} from a <code>String</code>. Line breaks are
	 * added where the <code>String</code> contains '\n' characters.
	 * 
	 * @param text
	 *            the text.
	 * @param font
	 *            the font.
	 * @param paint
	 *            the paint.
	 * 
	 * @return A text block.
	 */
	public static TextBlock createTextBlock(final String text, final Font font,
			final Paint paint) {
		if (text == null) {
			throw new IllegalArgumentException("Null 'text' argument.");
		}
		final TextBlock result = new TextBlock();
		String input = text;
		boolean moreInputToProcess = (text.length() > 0);
		final int start = 0;
		while (moreInputToProcess) {
			final int index = input.indexOf("\n");
			if (index > start) {
				final String line = input.substring(start, index);
				if (index < input.length() - 1) {
					result.addLine(line, font, paint);
					input = input.substring(index + 1);
				} else {
					moreInputToProcess = false;
				}
			} else if (index == start) {
				if (index < input.length() - 1) {
					input = input.substring(index + 1);
				} else {
					moreInputToProcess = false;
				}
			} else {
				result.addLine(input, font, paint);
				moreInputToProcess = false;
			}
		}
		return result;
	}

	/**
	 * Creates a new text block from the given string.
	 * 
	 * @param text
	 *            the text.
	 * @param font
	 *            the font.
	 * @param paint
	 *            the paint.
	 * @param maxWidth
	 *            the maximum width for each line.
	 * @param maxLines
	 *            the maximum number of lines.
	 * @param measurer
	 *            the text measurer.
	 * 
	 * @return A text block.
	 */
	public static TextBlock createTextBlock(final String text, final Font font,
			final Paint paint, final float maxWidth, final int maxLines,
			final TextMeasurer measurer) {
		final TextBlock result = new TextBlock();
		final BreakIterator iterator = BreakIterator.getLineInstance();
		iterator.setText(text);
		int current = 0;
		int lines = 0;
		final int length = text.length();
		while (current < length && lines < maxLines) {
			final int next = nextLineBreak(text, current, maxWidth, iterator,
					measurer);
			if (next == BreakIterator.DONE) {
				result.addLine(text.substring(current), font, paint);
				return result;
			}
			result.addLine(text.substring(current, next), font, paint);
			lines++;
			current = next;
		}
		return result;
	}

	/**
	 * Returns the character index of the next line break.
	 * 
	 * @param text
	 *            the text.
	 * @param start
	 *            the start index.
	 * @param width
	 *            the end index.
	 * @param iterator
	 *            the word break iterator.
	 * @param measurer
	 *            the text measurer.
	 * 
	 * @return The index of the next line break.
	 */
	private static int nextLineBreak(final String text, final int start,
			final float width, final BreakIterator iterator,
			final TextMeasurer measurer) {
		// this method is (loosely) based on code in JFreeReport's TextParagraph
		// class
		int current = start;
		int end;
		float x = 0.0f;
		boolean firstWord = true;
		while (((end = iterator.next()) != BreakIterator.DONE)) {
			x += measurer.getStringWidth(text, current, end);
			if (x > width) {
				if (firstWord) {
					while (measurer.getStringWidth(text, start, end) > width) {
						end--;
					}
					// iterator.setPosition(end);
					return end;
				} else {
					end = iterator.previous();
					return end;
				}
			}
			// we found at least one word that fits ...
			firstWord = false;
			current = end;
		}
		return BreakIterator.DONE;
	}

	/**
	 * Returns the bounds for the specified text.
	 * 
	 * @param text
	 *            the text (<code>null</code> permitted).
	 * @param g2
	 *            the graphics context (not <code>null</code>).
	 * @param fm
	 *            the font metrics (not <code>null</code>).
	 * 
	 * @return The text bounds (possibly <code>null</code>).
	 */
	public static Rectangle2D getTextBounds(final String text, final Paint fm) {

		Rect bounds = new Rect();
		fm.getTextBounds(text, 0, text.length(), bounds);

		return new Rectangle(bounds.width(), bounds.height());
	}

	/**
	 * A flag that controls whether the FontMetrics.getStringBounds() method is
	 * used or a workaround is applied.
	 */
	private static boolean useFontMetricsGetStringBounds = false;

	/**
	 * Returns the flag that controls whether the FontMetrics.getStringBounds()
	 * method is used or not. If you are having trouble with label alignment or
	 * positioning, try changing the value of this flag.
	 * 
	 * @return A boolean.
	 */
	public static boolean getUseFontMetricsGetStringBounds() {
		return useFontMetricsGetStringBounds;
	}

	/**
	 * Sets the flag that controls whether the FontMetrics.getStringBounds()
	 * method is used or not. If you are having trouble with label alignment or
	 * positioning, try changing the value of this flag.
	 * 
	 * @param use
	 *            the flag.
	 */
	public static void setUseFontMetricsGetStringBounds(boolean use) {
		useFontMetricsGetStringBounds = use;
	}

	/**
	 * Draws a string that is aligned by one anchor point and rotated about
	 * another anchor point.
	 * 
	 * @param text
	 *            the text.
	 * @param g2
	 *            the graphics device.
	 * @param x
	 *            the x-coordinate for positioning the text.
	 * @param y
	 *            the y-coordinate for positioning the text.
	 * @param textAnchor
	 *            the text anchor.
	 * @param angle
	 *            the rotation angle (in radians).
	 * @param rotationAnchor
	 *            the rotation anchor.
	 */
	public static void drawRotatedString(final String text, final Canvas g2,
			final float x, final float y, final TextAnchor textAnchor,
			final double angle, final TextAnchor rotationAnchor, Paint paint) {

		if (text == null || text.equals("")) {
			return;
		}
		
		Matrix old = g2.getMatrix();
		Matrix matrix = new Matrix();
		// rotate the Bitmap
		//matrix.postRotate((float) angle);
		matrix.setRotate((float)angle, x, y);
		g2.setMatrix(matrix);
		g2.drawText(text, x, y, paint);
		matrix.setRotate(0);
		g2.setMatrix(old);

	}

	/**
	 * Draws a string such that the specified anchor point is aligned to the
	 * given (x, y) location.
	 * 
	 * @param text
	 *            the text.
	 * @param g2
	 *            the graphics device.
	 * @param x
	 *            the x coordinate (Java 2D).
	 * @param y
	 *            the y coordinate (Java 2D).
	 * @param anchor
	 *            the anchor location.
	 * 
	 * @return The text bounds (adjusted for the text position).
	 */
	public static Rectangle2D drawAlignedString(final String text,
			final Canvas g2, final float x, final float y,
			final TextAnchor anchor, Paint paint) {

		final Rectangle2D textBounds = new Rectangle2D.Double();
		final float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor,
				textBounds, paint);
		// adjust text bounds to match string position
		/*
		 * textBounds.setRect(x + adjust[0], y + adjust[1] + adjust[2],
		 * textBounds.getWidth(), textBounds.getHeight());
		 */
		// TODO ACHUNG
		textBounds.setRect(x + adjust[0], y + adjust[1] + adjust[2], textBounds
				.getWidth(), textBounds.getHeight());
		g2.drawText(text, x, y, paint);
		// g2.drawString(text, x + 0, y + 0);
		return textBounds;
	}

	private static float[] deriveTextBoundsAnchorOffsets(final Canvas g2,
			final String text, final TextAnchor anchor,
			final Rectangle2D textBounds, Paint paint) {

		final float[] result = new float[3];

		final Rectangle2D bounds = TextUtilities.getTextBounds(text, paint);

		final float ascent = paint.ascent();
		result[2] = -ascent;
		final float halfAscent = ascent / 2.0f;
		final float descent = paint.descent();
		// final float leading = paint.g;
		float xAdj = 0.0f;
		float yAdj = 0.0f;

		if (anchor == TextAnchor.TOP_CENTER || anchor == TextAnchor.CENTER
				|| anchor == TextAnchor.BOTTOM_CENTER
				|| anchor == TextAnchor.BASELINE_CENTER
				|| anchor == TextAnchor.HALF_ASCENT_CENTER) {

			xAdj = (float) -bounds.getWidth() / 2.0f;

		} else if (anchor == TextAnchor.TOP_RIGHT
				|| anchor == TextAnchor.CENTER_RIGHT
				|| anchor == TextAnchor.BOTTOM_RIGHT
				|| anchor == TextAnchor.BASELINE_RIGHT
				|| anchor == TextAnchor.HALF_ASCENT_RIGHT) {

			xAdj = (float) -bounds.getWidth();

		}

		if (anchor == TextAnchor.TOP_LEFT || anchor == TextAnchor.TOP_CENTER
				|| anchor == TextAnchor.TOP_RIGHT) {

			yAdj = -descent + (float) bounds.getHeight();

		} else if (anchor == TextAnchor.HALF_ASCENT_LEFT
				|| anchor == TextAnchor.HALF_ASCENT_CENTER
				|| anchor == TextAnchor.HALF_ASCENT_RIGHT) {

			yAdj = halfAscent;

		} else if (anchor == TextAnchor.CENTER_LEFT
				|| anchor == TextAnchor.CENTER
				|| anchor == TextAnchor.CENTER_RIGHT) {

			yAdj = -descent + (float) (bounds.getHeight() / 2.0);

		} else if (anchor == TextAnchor.BASELINE_LEFT
				|| anchor == TextAnchor.BASELINE_CENTER
				|| anchor == TextAnchor.BASELINE_RIGHT) {

			yAdj = 0.0f;

		} else if (anchor == TextAnchor.BOTTOM_LEFT
				|| anchor == TextAnchor.BOTTOM_CENTER
				|| anchor == TextAnchor.BOTTOM_RIGHT) {

			yAdj = -paint.descent();

		}
		if (textBounds != null) {
			textBounds.setRect(bounds);
		}
		result[0] = xAdj;
		result[1] = yAdj;
		return result;

	}

	/**
	 * Returns a shape that represents the bounds of the string after the
	 * specified rotation has been applied.
	 * 
	 * @param text
	 *            the text (<code>null</code> permitted).
	 * @param g2
	 *            the graphics device.
	 * @param x
	 *            the x coordinate for the anchor point.
	 * @param y
	 *            the y coordinate for the anchor point.
	 * @param textAnchor
	 *            the text anchor.
	 * @param angle
	 *            the angle.
	 * @param rotationAnchor
	 *            the rotation anchor.
	 * 
	 * @return The bounds (possibly <code>null</code>).
	 */
	/*
	 * public static Shape calculateRotatedStringBounds(final String text, final
	 * Canvas g2, final float x, final float y, final TextAnchor textAnchor,
	 * final double angle, final TextAnchor rotationAnchor) {
	 * 
	 * if (text == null || text.equals("")) { return null; } final float[]
	 * textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor);
	 * 
	 * final float[] rotateAdj = deriveRotationAnchorOffsets(g2, text,
	 * rotationAnchor);
	 * 
	 * final Shape result = calculateRotatedStringBounds(text, g2, x +
	 * textAdj[0], y + textAdj[1], angle, x + textAdj[0] + rotateAdj[0], y +
	 * textAdj[1] + rotateAdj[1]); return result;
	 * 
	 * }
	 */

}
