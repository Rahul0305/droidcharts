package net.droidsolutions.droidcharts.core.renderer;

import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.awt.RectangularShape;
import net.droidsolutions.droidcharts.common.RectangleEdge;
import android.graphics.Canvas;
import android.graphics.Paint;

public class AndroidStyleBarRenderer extends GradientBarPainter {

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

			itemPaint.setStyle(Paint.Style.FILL);
			// itemPaint.setStrokeWidth(3);

			g2.drawRect((int) regions[0].getMinX(), (int) regions[0].getMinY(),
					(int) regions[0].getMaxX(), (int) regions[0].getMaxY(),
					itemPaint);
			g2.drawRect((int) regions[1].getMinX(), (int) regions[1].getMinY(),
					(int) regions[1].getMaxX(), (int) regions[1].getMaxY(),
					itemPaint);
			g2.drawRect((int) regions[2].getMinX(), (int) regions[2].getMinY(),
					(int) regions[2].getMaxX(), (int) regions[2].getMaxY(),
					itemPaint);
			g2.drawRect((int) regions[3].getMinX(), (int) regions[3].getMinY(),
					(int) regions[3].getMaxX(), (int) regions[3].getMaxY(),
					itemPaint);

		} else if (base == RectangleEdge.LEFT || base == RectangleEdge.RIGHT) {
			Rectangle2D[] regions = splitHorizontalBar(bar, this.g1, this.g2,
					this.g3);
			itemPaint.setStyle(Paint.Style.FILL);
			// itemPaint.setStrokeWidth(3);

			g2.drawRect((int) regions[0].getMinX(), (int) regions[0].getMinY(),
					(int) regions[0].getMaxX(), (int) regions[0].getMaxY(),
					itemPaint);
			g2.drawRect((int) regions[1].getMinX(), (int) regions[1].getMinY(),
					(int) regions[1].getMaxX(), (int) regions[1].getMaxY(),
					itemPaint);
			g2.drawRect((int) regions[2].getMinX(), (int) regions[2].getMinY(),
					(int) regions[2].getMaxX(), (int) regions[2].getMaxY(),
					itemPaint);
			g2.drawRect((int) regions[3].getMinX(), (int) regions[3].getMinY(),
					(int) regions[3].getMaxX(), (int) regions[3].getMaxY(),
					itemPaint);

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

	}
}
