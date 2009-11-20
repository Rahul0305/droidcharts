/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * ---------------------
 * AbstractRenderer.java
 * ---------------------
 * (C) Copyright 2002-2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Nicolas Brodu;
 *
 * Changes:
 * --------
 * 22-Aug-2002 : Version 1, draws code out of AbstractXYItemRenderer to share
 *               with AbstractCategoryItemRenderer (DG);
 * 01-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 06-Nov-2002 : Moved to the com.jrefinery.chart.renderer package (DG);
 * 21-Nov-2002 : Added a paint table for the renderer to use (DG);
 * 17-Jan-2003 : Moved plot classes into a separate package (DG);
 * 25-Mar-2003 : Implemented Serializable (DG);
 * 29-Apr-2003 : Added valueLabelFont and valueLabelPaint attributes, based on
 *               code from Arnaud Lelievre (DG);
 * 29-Jul-2003 : Amended code that doesn't compile with JDK 1.2.2 (DG);
 * 13-Aug-2003 : Implemented Cloneable (DG);
 * 15-Sep-2003 : Fixed serialization (NB);
 * 17-Sep-2003 : Changed ChartRenderingInfo --> PlotRenderingInfo (DG);
 * 07-Oct-2003 : Moved PlotRenderingInfo into RendererState to allow for
 *               multiple threads using a single renderer (DG);
 * 20-Oct-2003 : Added missing setOutlinePaint() method (DG);
 * 23-Oct-2003 : Split item label attributes into 'positive' and 'negative'
 *               values (DG);
 * 26-Nov-2003 : Added methods to get the positive and negative item label
 *               positions (DG);
 * 01-Mar-2004 : Modified readObject() method to prevent null pointer exceptions
 *               after deserialization (DG);
 * 19-Jul-2004 : Fixed bug in getItemLabelFont(int, int) method (DG);
 * 04-Oct-2004 : Updated equals() method, eliminated use of NumberUtils,
 *               renamed BooleanUtils --> BooleanUtilities, ShapeUtils -->
 *               ShapeUtilities (DG);
 * 15-Mar-2005 : Fixed serialization of baseFillPaint (DG);
 * 16-May-2005 : Base outline stroke should never be null (DG);
 * 01-Jun-2005 : Added hasListener() method for unit testing (DG);
 * 08-Jun-2005 : Fixed equals() method to handle GradientPaint (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 02-Feb-2007 : Minor API doc update (DG);
 * 19-Feb-2007 : Fixes for clone() method (DG);
 * 28-Feb-2007 : Use cached event to signal changes (DG);
 * 19-Apr-2007 : Deprecated seriesVisible and seriesVisibleInLegend flags (DG);
 * 20-Apr-2007 : Deprecated paint, fillPaint, outlinePaint, stroke,
 *               outlineStroke, shape, itemLabelsVisible, itemLabelFont,
 *               itemLabelPaint, positiveItemLabelPosition,
 *               negativeItemLabelPosition and createEntities override
 *               fields (DG);
 * 13-Jun-2007 : Added new autoPopulate flags for core series attributes (DG);
 * 23-Oct-2007 : Updated lookup methods to better handle overridden
 *               methods (DG);
 * 04-Dec-2007 : Modified hashCode() implementation (DG);
 * 29-Apr-2008 : Minor API doc update (DG);
 * 17-Jun-2008 : Added legendShape, legendTextFont and legendTextPaint
 *               attributes (DG);
 * 18-Aug-2008 : Added clearSeriesPaints() and clearSeriesStrokes() (DG);
 * 28-Jan-2009 : Equals method doesn't test Shape equality correctly (DG);
 * 27-Mar-2009 : Added dataBoundsIncludesVisibleSeriesOnly attribute, and
 *               updated renderer events for series visibility changes (DG);
 * 01-Apr-2009 : Factored up the defaultEntityRadius field from the
 *               AbstractXYItemRenderer class (DG);
 */

package net.droidsolutions.droidcharts.core.renderer;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.awt.Point2D;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.awt.Shape;
import net.droidsolutions.droidcharts.common.BooleanList;
import net.droidsolutions.droidcharts.common.ObjectList;
import net.droidsolutions.droidcharts.common.PaintList;
import net.droidsolutions.droidcharts.common.ShapeList;
import net.droidsolutions.droidcharts.common.StrokeList;
import net.droidsolutions.droidcharts.common.TextAnchor;
import net.droidsolutions.droidcharts.core.event.RendererChangeEvent;
import net.droidsolutions.droidcharts.core.label.ItemLabelAnchor;
import net.droidsolutions.droidcharts.core.label.ItemLabelPosition;
import net.droidsolutions.droidcharts.core.plot.DrawingSupplier;
import net.droidsolutions.droidcharts.core.plot.PlotOrientation;
import net.droidsolutions.droidcharts.core.title.LegendTitle;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Base class providing common services for renderers. Most methods that update
 * attributes of the renderer will fire a {@link RendererChangeEvent}, which
 * normally means the plot that owns the renderer will receive notification that
 * the renderer has been changed (the plot will, in turn, notify the chart).
 */
public abstract class AbstractRenderer implements Cloneable, Serializable {

	/** For serialization. */
	private static final long serialVersionUID = -828267569428206075L;

	/** Zero represented as a <code>Double</code>. */
	public static final Double ZERO = new Double(0.0);

	/** The default paint. */
	public static final Paint DEFAULT_PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
	static {
		DEFAULT_PAINT.setColor(Color.BLUE);
	}

	/** The default outline paint. */
	public static final Paint DEFAULT_OUTLINE_PAINT = new Paint(
			Paint.ANTI_ALIAS_FLAG);
	static {
		DEFAULT_OUTLINE_PAINT.setColor(Color.GRAY);
	}

	/** The default stroke. */
	public static final float DEFAULT_STROKE = 1.0f;

	/** The default outline stroke. */
	public static final float DEFAULT_OUTLINE_STROKE = 1.0f;

	/** The default shape. */
	public static final Shape DEFAULT_SHAPE = new Rectangle2D.Double(-3.0,
			-3.0, 6.0, 6.0);

	/** The default value label font. */
	public static final Font DEFAULT_VALUE_LABEL_FONT = new Font("SansSerif",
			Typeface.NORMAL, 10);

	/** The default value label paint. */
	public static final Paint DEFAULT_VALUE_LABEL_PAINT = new Paint(
			Paint.ANTI_ALIAS_FLAG);
	static {
		DEFAULT_VALUE_LABEL_PAINT.setColor(Color.BLACK);
	}

	/** A list of flags that controls whether or not each series is visible. */
	private BooleanList seriesVisibleList;

	/** The default visibility for each series. */
	private boolean baseSeriesVisible;

	/**
	 * A list of flags that controls whether or not each series is visible in
	 * the legend.
	 */
	private BooleanList seriesVisibleInLegendList;

	/** The default visibility for each series in the legend. */
	private boolean baseSeriesVisibleInLegend;

	/** The paint list. */
	private PaintList paintList;

	/**
	 * A flag that controls whether or not the paintList is auto-populated in
	 * the {@link #lookupSeriesPaint(int)} method.
	 * 
	 * @since 1.0.6
	 */
	private boolean autoPopulateSeriesPaint;

	/** The base paint. */
	private transient Paint basePaint;

	/** The fill paint list. */
	private PaintList fillPaintList;

	/**
	 * A flag that controls whether or not the fillPaintList is auto-populated
	 * in the {@link #lookupSeriesFillPaint(int)} method.
	 * 
	 * @since 1.0.6
	 */
	private boolean autoPopulateSeriesFillPaint;

	/** The base fill paint. */
	private transient Paint baseFillPaint;

	/** The outline paint list. */
	private PaintList outlinePaintList;

	/**
	 * A flag that controls whether or not the outlinePaintList is
	 * auto-populated in the {@link #lookupSeriesOutlinePaint(int)} method.
	 * 
	 * @since 1.0.6
	 */
	private boolean autoPopulateSeriesOutlinePaint;

	/** The base outline paint. */
	private transient Paint baseOutlinePaint;

	/** The stroke list. */
	private StrokeList strokeList;

	/**
	 * A flag that controls whether or not the strokeList is auto-populated in
	 * the {@link #lookupSeriesStroke(int)} method.
	 * 
	 * @since 1.0.6
	 */
	private boolean autoPopulateSeriesStroke;

	/** The base stroke. */
	private transient float baseStroke;

	/** The outline stroke list. */
	private StrokeList outlineStrokeList;

	/** The base outline stroke. */
	private transient float baseOutlineStroke;

	/**
	 * A flag that controls whether or not the outlineStrokeList is
	 * auto-populated in the {@link #lookupSeriesOutlineStroke(int)} method.
	 * 
	 * @since 1.0.6
	 */
	private boolean autoPopulateSeriesOutlineStroke;

	/** A shape list. */
	private ShapeList shapeList;

	/**
	 * A flag that controls whether or not the shapeList is auto-populated in
	 * the {@link #lookupSeriesShape(int)} method.
	 * 
	 * @since 1.0.6
	 */
	private boolean autoPopulateSeriesShape;

	/** The base shape. */
	private transient Shape baseShape;

	/** Visibility of the item labels PER series. */
	private BooleanList itemLabelsVisibleList;

	/** The base item labels visible. */
	private Boolean baseItemLabelsVisible;

	/** The item label font list (one font per series). */
	private ObjectList itemLabelFontList;

	/** The base item label font. */
	private Font baseItemLabelFont;

	/** The item label paint list (one paint per series). */
	private PaintList itemLabelPaintList;

	/** The base item label paint. */
	private transient Paint baseItemLabelPaint;

	/** The positive item label position (per series). */
	private ObjectList positiveItemLabelPositionList;

	/** The fallback positive item label position. */
	private ItemLabelPosition basePositiveItemLabelPosition;

	/** The negative item label position (per series). */
	private ObjectList negativeItemLabelPositionList;

	/** The fallback negative item label position. */
	private ItemLabelPosition baseNegativeItemLabelPosition;

	/** The item label anchor offset. */
	private double itemLabelAnchorOffset = 2.0;

	/**
	 * Flags that control whether or not entities are generated for each series.
	 * This will be overridden by 'createEntities'.
	 */
	private BooleanList createEntitiesList;

	/**
	 * The default flag that controls whether or not entities are generated.
	 * This flag is used when both the above flags return null.
	 */
	private boolean baseCreateEntities;

	/**
	 * The per-series legend shape settings.
	 * 
	 * @since 1.0.11
	 */
	private ShapeList legendShape;

	/**
	 * The base shape for legend items. If this is <code>null</code>, the series
	 * shape will be used.
	 * 
	 * @since 1.0.11
	 */
	private transient Shape baseLegendShape;

	/**
	 * The per-series legend text font.
	 * 
	 * @since 1.0.11
	 */
	private ObjectList legendTextFont;

	/**
	 * The base legend font.
	 * 
	 * @since 1.0.11
	 */
	private Font baseLegendTextFont;

	/**
	 * The per series legend text paint settings.
	 * 
	 * @since 1.0.11
	 */
	private PaintList legendTextPaint;

	/**
	 * The default paint for the legend text items (if this is <code>null</code>
	 * , the {@link LegendTitle} class will determine the text paint to use.
	 * 
	 * @since 1.0.11
	 */
	private transient Paint baseLegendTextPaint;

	/**
	 * A flag that controls whether or not the renderer will include the
	 * non-visible series when calculating the data bounds.
	 * 
	 * @since 1.0.13
	 */
	private boolean dataBoundsIncludesVisibleSeriesOnly = true;

	/** The default radius for the entity 'hotspot' */
	private int defaultEntityRadius;

	/**
	 * Default constructor.
	 */
	public AbstractRenderer() {

		// this.seriesVisible = null;
		this.seriesVisibleList = new BooleanList();
		this.baseSeriesVisible = true;

		// this.seriesVisibleInLegend = null;
		this.seriesVisibleInLegendList = new BooleanList();
		this.baseSeriesVisibleInLegend = true;

		// this.paint = null;
		this.paintList = new PaintList();
		this.basePaint = DEFAULT_PAINT;
		this.autoPopulateSeriesPaint = true;

		// this.fillPaint = null;
		this.fillPaintList = new PaintList();
		this.baseFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.baseFillPaint.setColor(Color.WHITE);
		this.autoPopulateSeriesFillPaint = false;

		// this.outlinePaint = null;
		this.outlinePaintList = new PaintList();
		this.baseOutlinePaint = DEFAULT_OUTLINE_PAINT;
		this.autoPopulateSeriesOutlinePaint = false;

		// this.stroke = null;
		this.strokeList = new StrokeList();
		this.baseStroke = DEFAULT_STROKE;
		this.autoPopulateSeriesStroke = true;

		// this.outlineStroke = null;
		this.outlineStrokeList = new StrokeList();
		this.baseOutlineStroke = DEFAULT_OUTLINE_STROKE;
		this.autoPopulateSeriesOutlineStroke = true;

		// this.shape = null;
		this.shapeList = new ShapeList();
		this.baseShape = DEFAULT_SHAPE;
		this.autoPopulateSeriesShape = true;

		// this.itemLabelsVisible = null;
		this.itemLabelsVisibleList = new BooleanList();
		this.baseItemLabelsVisible = Boolean.FALSE;

		// this.itemLabelFont = null;
		this.itemLabelFontList = new ObjectList();
		this.baseItemLabelFont = new Font("SansSerif", Typeface.NORMAL, 10);

		// this.itemLabelPaint = null;
		this.itemLabelPaintList = new PaintList();
		this.baseItemLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.baseItemLabelPaint.setColor(Color.BLACK);

		// this.positiveItemLabelPosition = null;
		this.positiveItemLabelPositionList = new ObjectList();
		this.basePositiveItemLabelPosition = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);

		// this.negativeItemLabelPosition = null;
		this.negativeItemLabelPositionList = new ObjectList();
		this.baseNegativeItemLabelPosition = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);

		// this.createEntities = null;
		this.createEntitiesList = new BooleanList();
		this.baseCreateEntities = true;

		this.defaultEntityRadius = 3;

		this.legendShape = new ShapeList();
		this.baseLegendShape = null;

		this.legendTextFont = new ObjectList();
		this.baseLegendTextFont = null;

		this.legendTextPaint = new PaintList();
		this.baseLegendTextPaint = null;

	}

	/**
	 * Returns the drawing supplier from the plot.
	 * 
	 * @return The drawing supplier.
	 */
	public abstract DrawingSupplier getDrawingSupplier();

	// SERIES VISIBLE (not yet respected by all renderers)

	/**
	 * Returns a boolean that indicates whether or not the specified item should
	 * be drawn (this is typically used to hide an entire series).
	 * 
	 * @param series
	 *            the series index.
	 * @param item
	 *            the item index.
	 * 
	 * @return A boolean.
	 */
	public boolean getItemVisible(int series, int item) {
		return isSeriesVisible(series);
	}

	/**
	 * Returns a boolean that indicates whether or not the specified series
	 * should be drawn.
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return A boolean.
	 */
	public boolean isSeriesVisible(int series) {
		boolean result = this.baseSeriesVisible;

		Boolean b = this.seriesVisibleList.getBoolean(series);
		if (b != null) {
			result = b.booleanValue();
		}

		return result;
	}

	/**
	 * Returns the flag that controls whether a series is visible.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The flag (possibly <code>null</code>).
	 * 
	 * @see #setSeriesVisible(int, Boolean)
	 */
	public Boolean getSeriesVisible(int series) {
		return this.seriesVisibleList.getBoolean(series);
	}

	/**
	 * Sets the flag that controls whether a series is visible and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param visible
	 *            the flag (<code>null</code> permitted).
	 * 
	 * @see #getSeriesVisible(int)
	 */
	public void setSeriesVisible(int series, Boolean visible) {
		setSeriesVisible(series, visible, true);
	}

	/**
	 * Sets the flag that controls whether a series is visible and, if
	 * requested, sends a {@link RendererChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param visible
	 *            the flag (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesVisible(int)
	 */
	public void setSeriesVisible(int series, Boolean visible, boolean notify) {
		this.seriesVisibleList.setBoolean(series, visible);

	}

	/**
	 * Returns the base visibility for all series.
	 * 
	 * @return The base visibility.
	 * 
	 * @see #setBaseSeriesVisible(boolean)
	 */
	public boolean getBaseSeriesVisible() {
		return this.baseSeriesVisible;
	}

	/**
	 * Sets the base visibility and sends a {@link RendererChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param visible
	 *            the flag.
	 * 
	 * @see #getBaseSeriesVisible()
	 */
	public void setBaseSeriesVisible(boolean visible) {
		// defer argument checking...
		setBaseSeriesVisible(visible, true);
	}

	/**
	 * Sets the base visibility and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param visible
	 *            the visibility.
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBaseSeriesVisible()
	 */
	public void setBaseSeriesVisible(boolean visible, boolean notify) {
		this.baseSeriesVisible = visible;

	}

	// SERIES VISIBLE IN LEGEND (not yet respected by all renderers)

	/**
	 * Returns <code>true</code> if the series should be shown in the legend,
	 * and <code>false</code> otherwise.
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return A boolean.
	 */
	public boolean isSeriesVisibleInLegend(int series) {
		boolean result = this.baseSeriesVisibleInLegend;

		Boolean b = this.seriesVisibleInLegendList.getBoolean(series);
		if (b != null) {
			result = b.booleanValue();
		}

		return result;
	}

	/**
	 * Returns the flag that controls whether a series is visible in the legend.
	 * This method returns only the "per series" settings - to incorporate the
	 * override and base settings as well, you need to use the
	 * {@link #isSeriesVisibleInLegend(int)} method.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The flag (possibly <code>null</code>).
	 * 
	 * @see #setSeriesVisibleInLegend(int, Boolean)
	 */
	public Boolean getSeriesVisibleInLegend(int series) {
		return this.seriesVisibleInLegendList.getBoolean(series);
	}

	/**
	 * Sets the flag that controls whether a series is visible in the legend and
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param visible
	 *            the flag (<code>null</code> permitted).
	 * 
	 * @see #getSeriesVisibleInLegend(int)
	 */
	public void setSeriesVisibleInLegend(int series, Boolean visible) {
		setSeriesVisibleInLegend(series, visible, true);
	}

	/**
	 * Sets the flag that controls whether a series is visible in the legend
	 * and, if requested, sends a {@link RendererChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param visible
	 *            the flag (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesVisibleInLegend(int)
	 */
	public void setSeriesVisibleInLegend(int series, Boolean visible,
			boolean notify) {
		this.seriesVisibleInLegendList.setBoolean(series, visible);

	}

	/**
	 * Returns the base visibility in the legend for all series.
	 * 
	 * @return The base visibility.
	 * 
	 * @see #setBaseSeriesVisibleInLegend(boolean)
	 */
	public boolean getBaseSeriesVisibleInLegend() {
		return this.baseSeriesVisibleInLegend;
	}

	/**
	 * Sets the base visibility in the legend and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param visible
	 *            the flag.
	 * 
	 * @see #getBaseSeriesVisibleInLegend()
	 */
	public void setBaseSeriesVisibleInLegend(boolean visible) {
		// defer argument checking...
		setBaseSeriesVisibleInLegend(visible, true);
	}

	/**
	 * Sets the base visibility in the legend and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param visible
	 *            the visibility.
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBaseSeriesVisibleInLegend()
	 */
	public void setBaseSeriesVisibleInLegend(boolean visible, boolean notify) {
		this.baseSeriesVisibleInLegend = visible;

	}

	// PAINT

	/**
	 * Returns the paint used to fill data items as they are drawn.
	 * <p>
	 * The default implementation passes control to the
	 * <code>lookupSeriesPaint()</code> method. You can override this method if
	 * you require different behaviour.
	 * 
	 * @param row
	 *            the row (or series) index (zero-based).
	 * @param column
	 *            the column (or category) index (zero-based).
	 * 
	 * @return The paint (never <code>null</code>).
	 */
	public Paint getItemPaint(int row, int column) {
		return lookupSeriesPaint(row);
	}

	/**
	 * Returns the paint used to fill an item drawn by the renderer.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @since 1.0.6
	 */
	public Paint lookupSeriesPaint(int series) {

		// otherwise look up the paint list
		Paint seriesPaint = getSeriesPaint(series);
		if (seriesPaint == null && this.autoPopulateSeriesPaint) {
			DrawingSupplier supplier = getDrawingSupplier();
			if (supplier != null) {
				seriesPaint = supplier.getNextPaint();
				setSeriesPaint(series, seriesPaint, false);
			}
		}
		if (seriesPaint == null) {
			seriesPaint = this.basePaint;
		}
		return seriesPaint;

	}

	/**
	 * Returns the paint used to fill an item drawn by the renderer.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The paint (possibly <code>null</code>).
	 * 
	 * @see #setSeriesPaint(int, Paint)
	 */
	public Paint getSeriesPaint(int series) {
		return this.paintList.getPaint(series);
	}

	/**
	 * Sets the paint used for a series and sends a {@link RendererChangeEvent}
	 * to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @see #getSeriesPaint(int)
	 */
	public void setSeriesPaint(int series, Paint paint) {
		setSeriesPaint(series, paint, true);
	}

	/**
	 * Sets the paint used for a series and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesPaint(int)
	 */
	public void setSeriesPaint(int series, Paint paint, boolean notify) {
		this.paintList.setPaint(series, paint);

	}

	/**
	 * Clears the series paint settings for this renderer and, if requested,
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param notify
	 *            notify listeners?
	 * 
	 * @since 1.0.11
	 */
	public void clearSeriesPaints(boolean notify) {
		this.paintList.clear();

	}

	/**
	 * Returns the base paint.
	 * 
	 * @return The base paint (never <code>null</code>).
	 * 
	 * @see #setBasePaint(Paint)
	 */
	public Paint getBasePaint() {
		return this.basePaint;
	}

	/**
	 * Sets the base paint and sends a {@link RendererChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * 
	 * @see #getBasePaint()
	 */
	public void setBasePaint(Paint paint) {
		// defer argument checking...
		setBasePaint(paint, true);
	}

	/**
	 * Sets the base paint and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBasePaint()
	 */
	public void setBasePaint(Paint paint, boolean notify) {
		this.basePaint = paint;

	}

	/**
	 * Returns the flag that controls whether or not the series paint list is
	 * automatically populated when {@link #lookupSeriesPaint(int)} is called.
	 * 
	 * @return A boolean.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #setAutoPopulateSeriesPaint(boolean)
	 */
	public boolean getAutoPopulateSeriesPaint() {
		return this.autoPopulateSeriesPaint;
	}

	/**
	 * Sets the flag that controls whether or not the series paint list is
	 * automatically populated when {@link #lookupSeriesPaint(int)} is called.
	 * 
	 * @param auto
	 *            the new flag value.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #getAutoPopulateSeriesPaint()
	 */
	public void setAutoPopulateSeriesPaint(boolean auto) {
		this.autoPopulateSeriesPaint = auto;
	}

	// // FILL PAINT //////////////////////////////////////////////////////////

	/**
	 * Returns the paint used to fill data items as they are drawn. The default
	 * implementation passes control to the {@link #lookupSeriesFillPaint(int)}
	 * method - you can override this method if you require different behaviour.
	 * 
	 * @param row
	 *            the row (or series) index (zero-based).
	 * @param column
	 *            the column (or category) index (zero-based).
	 * 
	 * @return The paint (never <code>null</code>).
	 */
	public Paint getItemFillPaint(int row, int column) {
		return lookupSeriesFillPaint(row);
	}

	/**
	 * Returns the paint used to fill an item drawn by the renderer.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @since 1.0.6
	 */
	public Paint lookupSeriesFillPaint(int series) {

		// otherwise look up the paint table
		Paint seriesFillPaint = getSeriesFillPaint(series);
		if (seriesFillPaint == null && this.autoPopulateSeriesFillPaint) {
			DrawingSupplier supplier = getDrawingSupplier();
			if (supplier != null) {
				seriesFillPaint = supplier.getNextFillPaint();
				setSeriesFillPaint(series, seriesFillPaint, false);
			}
		}
		if (seriesFillPaint == null) {
			seriesFillPaint = this.baseFillPaint;
		}
		return seriesFillPaint;

	}

	/**
	 * Returns the paint used to fill an item drawn by the renderer.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @see #setSeriesFillPaint(int, Paint)
	 */
	public Paint getSeriesFillPaint(int series) {
		return this.fillPaintList.getPaint(series);
	}

	/**
	 * Sets the paint used for a series fill and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @see #getSeriesFillPaint(int)
	 */
	public void setSeriesFillPaint(int series, Paint paint) {
		setSeriesFillPaint(series, paint, true);
	}

	/**
	 * Sets the paint used to fill a series and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesFillPaint(int)
	 */
	public void setSeriesFillPaint(int series, Paint paint, boolean notify) {
		this.fillPaintList.setPaint(series, paint);

	}

	/**
	 * Returns the base fill paint.
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @see #setBaseFillPaint(Paint)
	 */
	public Paint getBaseFillPaint() {
		return this.baseFillPaint;
	}

	/**
	 * Sets the base fill paint and sends a {@link RendererChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * 
	 * @see #getBaseFillPaint()
	 */
	public void setBaseFillPaint(Paint paint) {
		// defer argument checking...
		setBaseFillPaint(paint, true);
	}

	/**
	 * Sets the base fill paint and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBaseFillPaint()
	 */
	public void setBaseFillPaint(Paint paint, boolean notify) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.baseFillPaint = paint;

	}

	/**
	 * Returns the flag that controls whether or not the series fill paint list
	 * is automatically populated when {@link #lookupSeriesFillPaint(int)} is
	 * called.
	 * 
	 * @return A boolean.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #setAutoPopulateSeriesFillPaint(boolean)
	 */
	public boolean getAutoPopulateSeriesFillPaint() {
		return this.autoPopulateSeriesFillPaint;
	}

	/**
	 * Sets the flag that controls whether or not the series fill paint list is
	 * automatically populated when {@link #lookupSeriesFillPaint(int)} is
	 * called.
	 * 
	 * @param auto
	 *            the new flag value.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #getAutoPopulateSeriesFillPaint()
	 */
	public void setAutoPopulateSeriesFillPaint(boolean auto) {
		this.autoPopulateSeriesFillPaint = auto;
	}

	// OUTLINE PAINT //////////////////////////////////////////////////////////

	/**
	 * Returns the paint used to outline data items as they are drawn.
	 * <p>
	 * The default implementation passes control to the
	 * {@link #lookupSeriesOutlinePaint} method. You can override this method if
	 * you require different behaviour.
	 * 
	 * @param row
	 *            the row (or series) index (zero-based).
	 * @param column
	 *            the column (or category) index (zero-based).
	 * 
	 * @return The paint (never <code>null</code>).
	 */
	public Paint getItemOutlinePaint(int row, int column) {
		return lookupSeriesOutlinePaint(row);
	}

	/**
	 * Returns the paint used to outline an item drawn by the renderer.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @since 1.0.6
	 */
	public Paint lookupSeriesOutlinePaint(int series) {

		// otherwise look up the paint table
		Paint seriesOutlinePaint = getSeriesOutlinePaint(series);
		if (seriesOutlinePaint == null && this.autoPopulateSeriesOutlinePaint) {
			DrawingSupplier supplier = getDrawingSupplier();
			if (supplier != null) {
				seriesOutlinePaint = supplier.getNextOutlinePaint();
				setSeriesOutlinePaint(series, seriesOutlinePaint, false);
			}
		}
		if (seriesOutlinePaint == null) {
			seriesOutlinePaint = this.baseOutlinePaint;
		}
		return seriesOutlinePaint;

	}

	/**
	 * Returns the paint used to outline an item drawn by the renderer.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The paint (possibly <code>null</code>).
	 * 
	 * @see #setSeriesOutlinePaint(int, Paint)
	 */
	public Paint getSeriesOutlinePaint(int series) {
		return this.outlinePaintList.getPaint(series);
	}

	/**
	 * Sets the paint used for a series outline and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @see #getSeriesOutlinePaint(int)
	 */
	public void setSeriesOutlinePaint(int series, Paint paint) {
		setSeriesOutlinePaint(series, paint, true);
	}

	/**
	 * Sets the paint used to draw the outline for a series and, if requested,
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesOutlinePaint(int)
	 */
	public void setSeriesOutlinePaint(int series, Paint paint, boolean notify) {
		this.outlinePaintList.setPaint(series, paint);

	}

	/**
	 * Returns the base outline paint.
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @see #setBaseOutlinePaint(Paint)
	 */
	public Paint getBaseOutlinePaint() {
		return this.baseOutlinePaint;
	}

	/**
	 * Sets the base outline paint and sends a {@link RendererChangeEvent} to
	 * all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * 
	 * @see #getBaseOutlinePaint()
	 */
	public void setBaseOutlinePaint(Paint paint) {
		// defer argument checking...
		setBaseOutlinePaint(paint, true);
	}

	/**
	 * Sets the base outline paint and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBaseOutlinePaint()
	 */
	public void setBaseOutlinePaint(Paint paint, boolean notify) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.baseOutlinePaint = paint;

	}

	/**
	 * Returns the flag that controls whether or not the series outline paint
	 * list is automatically populated when
	 * {@link #lookupSeriesOutlinePaint(int)} is called.
	 * 
	 * @return A boolean.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #setAutoPopulateSeriesOutlinePaint(boolean)
	 */
	public boolean getAutoPopulateSeriesOutlinePaint() {
		return this.autoPopulateSeriesOutlinePaint;
	}

	/**
	 * Sets the flag that controls whether or not the series outline paint list
	 * is automatically populated when {@link #lookupSeriesOutlinePaint(int)} is
	 * called.
	 * 
	 * @param auto
	 *            the new flag value.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #getAutoPopulateSeriesOutlinePaint()
	 */
	public void setAutoPopulateSeriesOutlinePaint(boolean auto) {
		this.autoPopulateSeriesOutlinePaint = auto;
	}

	// STROKE

	/**
	 * Returns the stroke used to draw data items.
	 * <p>
	 * The default implementation passes control to the getSeriesStroke method.
	 * You can override this method if you require different behaviour.
	 * 
	 * @param row
	 *            the row (or series) index (zero-based).
	 * @param column
	 *            the column (or category) index (zero-based).
	 * 
	 * @return The stroke (never <code>null</code>).
	 */
	public Float getItemStroke(int row, int column) {
		return lookupSeriesStroke(row);
	}

	/**
	 * Returns the stroke used to draw the items in a series.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The stroke (never <code>null</code>).
	 * 
	 * @since 1.0.6
	 */
	public float lookupSeriesStroke(int series) {

		// otherwise look up the paint table
		Float result = getSeriesStroke(series);
		if (result==null && this.autoPopulateSeriesStroke) {
			DrawingSupplier supplier = getDrawingSupplier();
			if (supplier != null) {
				result = supplier.getNextStroke();
				setSeriesStroke(series, result, false);
			}
		}
		if (result == 0.0f) {
			result = this.baseStroke;
		}
		return result;

	}

	/**
	 * Returns the stroke used to draw the items in a series.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The stroke (possibly <code>null</code>).
	 * 
	 * @see #setSeriesStroke(int, Stroke)
	 */
	public Float getSeriesStroke(int series) {
		return this.strokeList.getStroke(series);
	}

	/**
	 * Sets the stroke used for a series and sends a {@link RendererChangeEvent}
	 * to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param stroke
	 *            the stroke (<code>null</code> permitted).
	 * 
	 * @see #getSeriesStroke(int)
	 */
	public void setSeriesStroke(int series, float stroke) {
		setSeriesStroke(series, stroke, true);
	}

	/**
	 * Sets the stroke for a series and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param stroke
	 *            the stroke (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesStroke(int)
	 */
	public void setSeriesStroke(int series, float stroke, boolean notify) {
		this.strokeList.setStroke(series, stroke);

	}

	/**
	 * Clears the series stroke settings for this renderer and, if requested,
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param notify
	 *            notify listeners?
	 * 
	 * @since 1.0.11
	 */
	public void clearSeriesStrokes(boolean notify) {
		this.strokeList.clear();

	}

	/**
	 * Returns the base stroke.
	 * 
	 * @return The base stroke (never <code>null</code>).
	 * 
	 * @see #setBaseStroke(Stroke)
	 */
	public Float getBaseStroke() {
		return this.baseStroke;
	}

	/**
	 * Sets the base stroke and sends a {@link RendererChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param stroke
	 *            the stroke (<code>null</code> not permitted).
	 * 
	 * @see #getBaseStroke()
	 */
	public void setBaseStroke(float stroke) {
		// defer argument checking...
		setBaseStroke(stroke, true);
	}

	/**
	 * Sets the base stroke and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param stroke
	 *            the stroke (<code>null</code> not permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBaseStroke()
	 */
	public void setBaseStroke(float stroke, boolean notify) {

		this.baseStroke = stroke;

	}

	/**
	 * Returns the flag that controls whether or not the series stroke list is
	 * automatically populated when {@link #lookupSeriesStroke(int)} is called.
	 * 
	 * @return A boolean.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #setAutoPopulateSeriesStroke(boolean)
	 */
	public boolean getAutoPopulateSeriesStroke() {
		return this.autoPopulateSeriesStroke;
	}

	/**
	 * Sets the flag that controls whether or not the series stroke list is
	 * automatically populated when {@link #lookupSeriesStroke(int)} is called.
	 * 
	 * @param auto
	 *            the new flag value.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #getAutoPopulateSeriesStroke()
	 */
	public void setAutoPopulateSeriesStroke(boolean auto) {
		this.autoPopulateSeriesStroke = auto;
	}

	// OUTLINE STROKE

	/**
	 * Returns the stroke used to outline data items. The default implementation
	 * passes control to the {@link #lookupSeriesOutlineStroke(int)} method. You
	 * can override this method if you require different behaviour.
	 * 
	 * @param row
	 *            the row (or series) index (zero-based).
	 * @param column
	 *            the column (or category) index (zero-based).
	 * 
	 * @return The stroke (never <code>null</code>).
	 */
	public Float getItemOutlineStroke(int row, int column) {
		return lookupSeriesOutlineStroke(row);
	}

	/**
	 * Returns the stroke used to outline the items in a series.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The stroke (never <code>null</code>).
	 * 
	 * @since 1.0.6
	 */
	public Float lookupSeriesOutlineStroke(int series) {

		// otherwise look up the stroke table
		float result = getSeriesOutlineStroke(series);
		if (this.autoPopulateSeriesOutlineStroke) {
			DrawingSupplier supplier = getDrawingSupplier();
			if (supplier != null) {
				result = supplier.getNextOutlineStroke();
				setSeriesOutlineStroke(series, result, false);
			}
		}

		return result;

	}

	/**
	 * Returns the stroke used to outline the items in a series.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The stroke (possibly <code>null</code>).
	 * 
	 * @see #setSeriesOutlineStroke(int, Stroke)
	 */
	public Float getSeriesOutlineStroke(int series) {
		return this.outlineStrokeList.getStroke(series) == null ? 1
				: this.outlineStrokeList.getStroke(series);
	}

	/**
	 * Sets the outline stroke used for a series and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param stroke
	 *            the stroke (<code>null</code> permitted).
	 * 
	 * @see #getSeriesOutlineStroke(int)
	 */
	public void setSeriesOutlineStroke(int series, float stroke) {
		setSeriesOutlineStroke(series, stroke, true);
	}

	/**
	 * Sets the outline stroke for a series and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param stroke
	 *            the stroke (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesOutlineStroke(int)
	 */
	public void setSeriesOutlineStroke(int series, float stroke, boolean notify) {
		this.outlineStrokeList.setStroke(series, stroke);

	}

	/**
	 * Returns the base outline stroke.
	 * 
	 * @return The stroke (never <code>null</code>).
	 * 
	 * @see #setBaseOutlineStroke(Stroke)
	 */
	public Float getBaseOutlineStroke() {
		return this.baseOutlineStroke;
	}

	/**
	 * Sets the base outline stroke and sends a {@link RendererChangeEvent} to
	 * all registered listeners.
	 * 
	 * @param stroke
	 *            the stroke (<code>null</code> not permitted).
	 * 
	 * @see #getBaseOutlineStroke()
	 */
	public void setBaseOutlineStroke(float stroke) {
		setBaseOutlineStroke(stroke, true);
	}

	/**
	 * Sets the base outline stroke and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param stroke
	 *            the stroke (<code>null</code> not permitted).
	 * @param notify
	 *            a flag that controls whether or not listeners are notified.
	 * 
	 * @see #getBaseOutlineStroke()
	 */
	public void setBaseOutlineStroke(float stroke, boolean notify) {

		this.baseOutlineStroke = stroke;

	}

	/**
	 * Returns the flag that controls whether or not the series outline stroke
	 * list is automatically populated when
	 * {@link #lookupSeriesOutlineStroke(int)} is called.
	 * 
	 * @return A boolean.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #setAutoPopulateSeriesOutlineStroke(boolean)
	 */
	public boolean getAutoPopulateSeriesOutlineStroke() {
		return this.autoPopulateSeriesOutlineStroke;
	}

	/**
	 * Sets the flag that controls whether or not the series outline stroke list
	 * is automatically populated when {@link #lookupSeriesOutlineStroke(int)}
	 * is called.
	 * 
	 * @param auto
	 *            the new flag value.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #getAutoPopulateSeriesOutlineStroke()
	 */
	public void setAutoPopulateSeriesOutlineStroke(boolean auto) {
		this.autoPopulateSeriesOutlineStroke = auto;
	}

	// SHAPE

	/**
	 * Returns a shape used to represent a data item.
	 * <p>
	 * The default implementation passes control to the getSeriesShape method.
	 * You can override this method if you require different behaviour.
	 * 
	 * @param row
	 *            the row (or series) index (zero-based).
	 * @param column
	 *            the column (or category) index (zero-based).
	 * 
	 * @return The shape (never <code>null</code>).
	 */
	public Shape getItemShape(int row, int column) {
		return lookupSeriesShape(row);
	}

	/**
	 * Returns a shape used to represent the items in a series.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The shape (never <code>null</code>).
	 * 
	 * @since 1.0.6
	 */
	public Shape lookupSeriesShape(int series) {

		// otherwise look up the shape list
		Shape result = getSeriesShape(series);
		if (result == null && this.autoPopulateSeriesShape) {
			DrawingSupplier supplier = getDrawingSupplier();
			if (supplier != null) {
				result = supplier.getNextShape();
				setSeriesShape(series, result, false);
			}
		}
		if (result == null) {
			result = this.baseShape;
		}
		return result;

	}

	/**
	 * Returns a shape used to represent the items in a series.
	 * 
	 * @param series
	 *            the series (zero-based index).
	 * 
	 * @return The shape (possibly <code>null</code>).
	 * 
	 * @see #setSeriesShape(int, Shape)
	 */
	public Shape getSeriesShape(int series) {
		return this.shapeList.getShape(series);
	}

	/**
	 * Sets the shape used for a series and sends a {@link RendererChangeEvent}
	 * to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param shape
	 *            the shape (<code>null</code> permitted).
	 * 
	 * @see #getSeriesShape(int)
	 */
	public void setSeriesShape(int series, Shape shape) {
		setSeriesShape(series, shape, true);
	}

	/**
	 * Sets the shape for a series and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero based).
	 * @param shape
	 *            the shape (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesShape(int)
	 */
	public void setSeriesShape(int series, Shape shape, boolean notify) {
		this.shapeList.setShape(series, shape);

	}

	/**
	 * Returns the base shape.
	 * 
	 * @return The shape (never <code>null</code>).
	 * 
	 * @see #setBaseShape(Shape)
	 */
	public Shape getBaseShape() {
		return this.baseShape;
	}

	/**
	 * Sets the base shape and sends a {@link RendererChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param shape
	 *            the shape (<code>null</code> not permitted).
	 * 
	 * @see #getBaseShape()
	 */
	public void setBaseShape(Shape shape) {
		// defer argument checking...
		setBaseShape(shape, true);
	}

	/**
	 * Sets the base shape and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param shape
	 *            the shape (<code>null</code> not permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBaseShape()
	 */
	public void setBaseShape(Shape shape, boolean notify) {
		if (shape == null) {
			throw new IllegalArgumentException("Null 'shape' argument.");
		}
		this.baseShape = shape;

	}

	/**
	 * Returns the flag that controls whether or not the series shape list is
	 * automatically populated when {@link #lookupSeriesShape(int)} is called.
	 * 
	 * @return A boolean.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #setAutoPopulateSeriesShape(boolean)
	 */
	public boolean getAutoPopulateSeriesShape() {
		return this.autoPopulateSeriesShape;
	}

	/**
	 * Sets the flag that controls whether or not the series shape list is
	 * automatically populated when {@link #lookupSeriesShape(int)} is called.
	 * 
	 * @param auto
	 *            the new flag value.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #getAutoPopulateSeriesShape()
	 */
	public void setAutoPopulateSeriesShape(boolean auto) {
		this.autoPopulateSeriesShape = auto;
	}

	// ITEM LABEL VISIBILITY...

	/**
	 * Returns <code>true</code> if an item label is visible, and
	 * <code>false</code> otherwise.
	 * 
	 * @param row
	 *            the row index (zero-based).
	 * @param column
	 *            the column index (zero-based).
	 * 
	 * @return A boolean.
	 */
	public boolean isItemLabelVisible(int row, int column) {
		return isSeriesItemLabelsVisible(row);
	}

	/**
	 * Returns <code>true</code> if the item labels for a series are visible,
	 * and <code>false</code> otherwise.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return A boolean.
	 */
	public boolean isSeriesItemLabelsVisible(int series) {

		// otherwise look up the boolean table
		Boolean b = this.itemLabelsVisibleList.getBoolean(series);
		if (b == null) {
			b = this.baseItemLabelsVisible;
		}
		if (b == null) {
			b = Boolean.FALSE;
		}
		return b.booleanValue();

	}

	/**
	 * Sets a flag that controls the visibility of the item labels for a series,
	 * and sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param visible
	 *            the flag.
	 */
	public void setSeriesItemLabelsVisible(int series, boolean visible) {
		setSeriesItemLabelsVisible(series, Boolean.valueOf(visible));
	}

	/**
	 * Sets the visibility of the item labels for a series and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param visible
	 *            the flag (<code>null</code> permitted).
	 */
	public void setSeriesItemLabelsVisible(int series, Boolean visible) {
		setSeriesItemLabelsVisible(series, visible, true);
	}

	/**
	 * Sets the visibility of item labels for a series and, if requested, sends
	 * a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param visible
	 *            the visible flag.
	 * @param notify
	 *            a flag that controls whether or not listeners are notified.
	 */
	public void setSeriesItemLabelsVisible(int series, Boolean visible,
			boolean notify) {
		this.itemLabelsVisibleList.setBoolean(series, visible);

	}

	/**
	 * Returns the base setting for item label visibility. A <code>null</code>
	 * result should be interpreted as equivalent to <code>Boolean.FALSE</code>.
	 * 
	 * @return A flag (possibly <code>null</code>).
	 * 
	 * @see #setBaseItemLabelsVisible(boolean)
	 */
	public Boolean getBaseItemLabelsVisible() {
		// this should have been defined as a boolean primitive, because
		// allowing null values is a nuisance...but it is part of the final
		// API now, so we'll have to support it.
		return this.baseItemLabelsVisible;
	}

	/**
	 * Sets the base flag that controls whether or not item labels are visible,
	 * and sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param visible
	 *            the flag.
	 * 
	 * @see #getBaseItemLabelsVisible()
	 */
	public void setBaseItemLabelsVisible(boolean visible) {
		setBaseItemLabelsVisible(Boolean.valueOf(visible));
	}

	/**
	 * Sets the base setting for item label visibility and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param visible
	 *            the flag (<code>null</code> is permitted, and viewed as
	 *            equivalent to <code>Boolean.FALSE</code>).
	 */
	public void setBaseItemLabelsVisible(Boolean visible) {
		setBaseItemLabelsVisible(visible, true);
	}

	/**
	 * Sets the base visibility for item labels and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param visible
	 *            the flag (<code>null</code> is permitted, and viewed as
	 *            equivalent to <code>Boolean.FALSE</code>).
	 * @param notify
	 *            a flag that controls whether or not listeners are notified.
	 * 
	 * @see #getBaseItemLabelsVisible()
	 */
	public void setBaseItemLabelsVisible(Boolean visible, boolean notify) {
		this.baseItemLabelsVisible = visible;

	}

	// // ITEM LABEL FONT //////////////////////////////////////////////////////

	/**
	 * Returns the font for an item label.
	 * 
	 * @param row
	 *            the row index (zero-based).
	 * @param column
	 *            the column index (zero-based).
	 * 
	 * @return The font (never <code>null</code>).
	 */
	public Font getItemLabelFont(int row, int column) {
		Font result = null;
		if (result == null) {
			result = getSeriesItemLabelFont(row);
			if (result == null) {
				result = this.baseItemLabelFont;
			}
		}
		return result;
	}

	/**
	 * Returns the font for all the item labels in a series.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The font (possibly <code>null</code>).
	 * 
	 * @see #setSeriesItemLabelFont(int, Font)
	 */
	public Font getSeriesItemLabelFont(int series) {
		return (Font) this.itemLabelFontList.get(series);
	}

	/**
	 * Sets the item label font for a series and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param font
	 *            the font (<code>null</code> permitted).
	 * 
	 * @see #getSeriesItemLabelFont(int)
	 */
	public void setSeriesItemLabelFont(int series, Font font) {
		setSeriesItemLabelFont(series, font, true);
	}

	/**
	 * Sets the item label font for a series and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero based).
	 * @param font
	 *            the font (<code>null</code> permitted).
	 * @param notify
	 *            a flag that controls whether or not listeners are notified.
	 * 
	 * @see #getSeriesItemLabelFont(int)
	 */
	public void setSeriesItemLabelFont(int series, Font font, boolean notify) {
		this.itemLabelFontList.set(series, font);

	}

	/**
	 * Returns the base item label font (this is used when no other font setting
	 * is available).
	 * 
	 * @return The font (<code>never</code> null).
	 * 
	 * @see #setBaseItemLabelFont(Font)
	 */
	public Font getBaseItemLabelFont() {
		return this.baseItemLabelFont;
	}

	/**
	 * Sets the base item label font and sends a {@link RendererChangeEvent} to
	 * all registered listeners.
	 * 
	 * @param font
	 *            the font (<code>null</code> not permitted).
	 * 
	 * @see #getBaseItemLabelFont()
	 */
	public void setBaseItemLabelFont(Font font) {
		if (font == null) {
			throw new IllegalArgumentException("Null 'font' argument.");
		}
		setBaseItemLabelFont(font, true);
	}

	/**
	 * Sets the base item label font and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param font
	 *            the font (<code>null</code> not permitted).
	 * @param notify
	 *            a flag that controls whether or not listeners are notified.
	 * 
	 * @see #getBaseItemLabelFont()
	 */
	public void setBaseItemLabelFont(Font font, boolean notify) {
		this.baseItemLabelFont = font;

	}

	// // ITEM LABEL PAINT ////////////////////////////////////////////////////

	/**
	 * Returns the paint used to draw an item label.
	 * 
	 * @param row
	 *            the row index (zero based).
	 * @param column
	 *            the column index (zero based).
	 * 
	 * @return The paint (never <code>null</code>).
	 */
	public Paint getItemLabelPaint(int row, int column) {
		Paint result = null;
		if (result == null) {
			result = getSeriesItemLabelPaint(row);
			if (result == null) {
				result = this.baseItemLabelPaint;
			}
		}
		return result;
	}

	/**
	 * Returns the paint used to draw the item labels for a series.
	 * 
	 * @param series
	 *            the series index (zero based).
	 * 
	 * @return The paint (possibly <code>null<code>).
	 * 
	 * @see #setSeriesItemLabelPaint(int, Paint)
	 */
	public Paint getSeriesItemLabelPaint(int series) {
		return this.itemLabelPaintList.getPaint(series);
	}

	/**
	 * Sets the item label paint for a series and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series (zero based index).
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @see #getSeriesItemLabelPaint(int)
	 */
	public void setSeriesItemLabelPaint(int series, Paint paint) {
		setSeriesItemLabelPaint(series, paint, true);
	}

	/**
	 * Sets the item label paint for a series and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero based).
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * @param notify
	 *            a flag that controls whether or not listeners are notified.
	 * 
	 * @see #getSeriesItemLabelPaint(int)
	 */
	public void setSeriesItemLabelPaint(int series, Paint paint, boolean notify) {
		this.itemLabelPaintList.setPaint(series, paint);

	}

	/**
	 * Returns the base item label paint.
	 * 
	 * @return The paint (never <code>null<code>).
	 * 
	 * @see #setBaseItemLabelPaint(Paint)
	 */
	public Paint getBaseItemLabelPaint() {
		return this.baseItemLabelPaint;
	}

	/**
	 * Sets the base item label paint and sends a {@link RendererChangeEvent} to
	 * all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * 
	 * @see #getBaseItemLabelPaint()
	 */
	public void setBaseItemLabelPaint(Paint paint) {
		// defer argument checking...
		setBaseItemLabelPaint(paint, true);
	}

	/**
	 * Sets the base item label paint and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners..
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * @param notify
	 *            a flag that controls whether or not listeners are notified.
	 * 
	 * @see #getBaseItemLabelPaint()
	 */
	public void setBaseItemLabelPaint(Paint paint, boolean notify) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.baseItemLabelPaint = paint;

	}

	// POSITIVE ITEM LABEL POSITION...

	/**
	 * Returns the item label position for positive values.
	 * 
	 * @param row
	 *            the row index (zero-based).
	 * @param column
	 *            the column index (zero-based).
	 * 
	 * @return The item label position (never <code>null</code>).
	 * 
	 * @see #getNegativeItemLabelPosition(int, int)
	 */
	public ItemLabelPosition getPositiveItemLabelPosition(int row, int column) {
		return getSeriesPositiveItemLabelPosition(row);
	}

	/**
	 * Returns the item label position for all positive values in a series.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The item label position (never <code>null</code>).
	 * 
	 * @see #setSeriesPositiveItemLabelPosition(int, ItemLabelPosition)
	 */
	public ItemLabelPosition getSeriesPositiveItemLabelPosition(int series) {

		// otherwise look up the position table
		ItemLabelPosition position = (ItemLabelPosition) this.positiveItemLabelPositionList
				.get(series);
		if (position == null) {
			position = this.basePositiveItemLabelPosition;
		}
		return position;

	}

	/**
	 * Sets the item label position for all positive values in a series and
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param position
	 *            the position (<code>null</code> permitted).
	 * 
	 * @see #getSeriesPositiveItemLabelPosition(int)
	 */
	public void setSeriesPositiveItemLabelPosition(int series,
			ItemLabelPosition position) {
		setSeriesPositiveItemLabelPosition(series, position, true);
	}

	/**
	 * Sets the item label position for all positive values in a series and (if
	 * requested) sends a {@link RendererChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param position
	 *            the position (<code>null</code> permitted).
	 * @param notify
	 *            notify registered listeners?
	 * 
	 * @see #getSeriesPositiveItemLabelPosition(int)
	 */
	public void setSeriesPositiveItemLabelPosition(int series,
			ItemLabelPosition position, boolean notify) {
		this.positiveItemLabelPositionList.set(series, position);

	}

	/**
	 * Returns the base positive item label position.
	 * 
	 * @return The position (never <code>null</code>).
	 * 
	 * @see #setBasePositiveItemLabelPosition(ItemLabelPosition)
	 */
	public ItemLabelPosition getBasePositiveItemLabelPosition() {
		return this.basePositiveItemLabelPosition;
	}

	/**
	 * Sets the base positive item label position.
	 * 
	 * @param position
	 *            the position (<code>null</code> not permitted).
	 * 
	 * @see #getBasePositiveItemLabelPosition()
	 */
	public void setBasePositiveItemLabelPosition(ItemLabelPosition position) {
		// defer argument checking...
		setBasePositiveItemLabelPosition(position, true);
	}

	/**
	 * Sets the base positive item label position and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param position
	 *            the position (<code>null</code> not permitted).
	 * @param notify
	 *            notify registered listeners?
	 * 
	 * @see #getBasePositiveItemLabelPosition()
	 */
	public void setBasePositiveItemLabelPosition(ItemLabelPosition position,
			boolean notify) {
		if (position == null) {
			throw new IllegalArgumentException("Null 'position' argument.");
		}
		this.basePositiveItemLabelPosition = position;

	}

	// NEGATIVE ITEM LABEL POSITION...

	/**
	 * Returns the item label position for negative values. This method can be
	 * overridden to provide customisation of the item label position for
	 * individual data items.
	 * 
	 * @param row
	 *            the row index (zero-based).
	 * @param column
	 *            the column (zero-based).
	 * 
	 * @return The item label position (never <code>null</code>).
	 * 
	 * @see #getPositiveItemLabelPosition(int, int)
	 */
	public ItemLabelPosition getNegativeItemLabelPosition(int row, int column) {
		return getSeriesNegativeItemLabelPosition(row);
	}

	/**
	 * Returns the item label position for all negative values in a series.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The item label position (never <code>null</code>).
	 * 
	 * @see #setSeriesNegativeItemLabelPosition(int, ItemLabelPosition)
	 */
	public ItemLabelPosition getSeriesNegativeItemLabelPosition(int series) {

		// otherwise look up the position list
		ItemLabelPosition position = (ItemLabelPosition) this.negativeItemLabelPositionList
				.get(series);
		if (position == null) {
			position = this.baseNegativeItemLabelPosition;
		}
		return position;

	}

	/**
	 * Sets the item label position for negative values in a series and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param position
	 *            the position (<code>null</code> permitted).
	 * 
	 * @see #getSeriesNegativeItemLabelPosition(int)
	 */
	public void setSeriesNegativeItemLabelPosition(int series,
			ItemLabelPosition position) {
		setSeriesNegativeItemLabelPosition(series, position, true);
	}

	/**
	 * Sets the item label position for negative values in a series and (if
	 * requested) sends a {@link RendererChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param position
	 *            the position (<code>null</code> permitted).
	 * @param notify
	 *            notify registered listeners?
	 * 
	 * @see #getSeriesNegativeItemLabelPosition(int)
	 */
	public void setSeriesNegativeItemLabelPosition(int series,
			ItemLabelPosition position, boolean notify) {
		this.negativeItemLabelPositionList.set(series, position);

	}

	/**
	 * Returns the base item label position for negative values.
	 * 
	 * @return The position (never <code>null</code>).
	 * 
	 * @see #setBaseNegativeItemLabelPosition(ItemLabelPosition)
	 */
	public ItemLabelPosition getBaseNegativeItemLabelPosition() {
		return this.baseNegativeItemLabelPosition;
	}

	/**
	 * Sets the base item label position for negative values and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param position
	 *            the position (<code>null</code> not permitted).
	 * 
	 * @see #getBaseNegativeItemLabelPosition()
	 */
	public void setBaseNegativeItemLabelPosition(ItemLabelPosition position) {
		setBaseNegativeItemLabelPosition(position, true);
	}

	/**
	 * Sets the base negative item label position and, if requested, sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param position
	 *            the position (<code>null</code> not permitted).
	 * @param notify
	 *            notify registered listeners?
	 * 
	 * @see #getBaseNegativeItemLabelPosition()
	 */
	public void setBaseNegativeItemLabelPosition(ItemLabelPosition position,
			boolean notify) {
		if (position == null) {
			throw new IllegalArgumentException("Null 'position' argument.");
		}
		this.baseNegativeItemLabelPosition = position;

	}

	/**
	 * Returns the item label anchor offset.
	 * 
	 * @return The offset.
	 * 
	 * @see #setItemLabelAnchorOffset(double)
	 */
	public double getItemLabelAnchorOffset() {
		return this.itemLabelAnchorOffset;
	}

	/**
	 * Sets the item label anchor offset.
	 * 
	 * @param offset
	 *            the offset.
	 * 
	 * @see #getItemLabelAnchorOffset()
	 */
	public void setItemLabelAnchorOffset(double offset) {
		this.itemLabelAnchorOffset = offset;

	}

	/**
	 * Returns a boolean that indicates whether or not the specified item should
	 * have a chart entity created for it.
	 * 
	 * @param series
	 *            the series index.
	 * @param item
	 *            the item index.
	 * 
	 * @return A boolean.
	 */
	public boolean getItemCreateEntity(int series, int item) {

		Boolean b = getSeriesCreateEntities(series);
		if (b != null) {
			return b.booleanValue();
		} else {
			return this.baseCreateEntities;
		}

	}

	/**
	 * Returns the flag that controls whether entities are created for a series.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * 
	 * @return The flag (possibly <code>null</code>).
	 * 
	 * @see #setSeriesCreateEntities(int, Boolean)
	 */
	public Boolean getSeriesCreateEntities(int series) {
		return this.createEntitiesList.getBoolean(series);
	}

	/**
	 * Sets the flag that controls whether entities are created for a series,
	 * and sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index (zero-based).
	 * @param create
	 *            the flag (<code>null</code> permitted).
	 * 
	 * @see #getSeriesCreateEntities(int)
	 */
	public void setSeriesCreateEntities(int series, Boolean create) {
		setSeriesCreateEntities(series, create, true);
	}

	/**
	 * Sets the flag that controls whether entities are created for a series
	 * and, if requested, sends a {@link RendererChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param create
	 *            the flag (<code>null</code> permitted).
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getSeriesCreateEntities(int)
	 */
	public void setSeriesCreateEntities(int series, Boolean create,
			boolean notify) {
		this.createEntitiesList.setBoolean(series, create);

	}

	/**
	 * Returns the base visibility for all series.
	 * 
	 * @return The base visibility.
	 * 
	 * @see #setBaseCreateEntities(boolean)
	 */
	public boolean getBaseCreateEntities() {
		return this.baseCreateEntities;
	}

	/**
	 * Sets the base flag that controls whether entities are created for a
	 * series, and sends a {@link RendererChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param create
	 *            the flag.
	 * 
	 * @see #getBaseCreateEntities()
	 */
	public void setBaseCreateEntities(boolean create) {
		// defer argument checking...
		setBaseCreateEntities(create, true);
	}

	/**
	 * Sets the base flag that controls whether entities are created and, if
	 * requested, sends a {@link RendererChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param create
	 *            the visibility.
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getBaseCreateEntities()
	 */
	public void setBaseCreateEntities(boolean create, boolean notify) {
		this.baseCreateEntities = create;

	}

	/**
	 * Returns the radius of the circle used for the default entity area when no
	 * area is specified.
	 * 
	 * @return A radius.
	 * 
	 * @see #setDefaultEntityRadius(int)
	 */
	public int getDefaultEntityRadius() {
		return this.defaultEntityRadius;
	}

	/**
	 * Sets the radius of the circle used for the default entity area when no
	 * area is specified.
	 * 
	 * @param radius
	 *            the radius.
	 * 
	 * @see #getDefaultEntityRadius()
	 */
	public void setDefaultEntityRadius(int radius) {
		this.defaultEntityRadius = radius;
	}

	/**
	 * Performs a lookup for the legend shape.
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return The shape (possibly <code>null</code>).
	 * 
	 * @since 1.0.11
	 */
	public Shape lookupLegendShape(int series) {
		Shape result = getLegendShape(series);
		if (result == null) {
			result = this.baseLegendShape;
		}
		if (result == null) {
			result = lookupSeriesShape(series);
		}
		return result;
	}

	/**
	 * Returns the legend shape defined for the specified series (possibly
	 * <code>null</code>).
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return The shape (possibly <code>null</code>).
	 * 
	 * @see #lookupLegendShape(int)
	 * 
	 * @since 1.0.11
	 */
	public Shape getLegendShape(int series) {
		return this.legendShape.getShape(series);
	}

	/**
	 * Sets the shape used for the legend item for the specified series, and
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param shape
	 *            the shape (<code>null</code> permitted).
	 * 
	 * @since 1.0.11
	 */
	public void setLegendShape(int series, Shape shape) {
		this.legendShape.setShape(series, shape);
	}

	/**
	 * Returns the default legend shape, which may be <code>null</code>.
	 * 
	 * @return The default legend shape.
	 * 
	 * @since 1.0.11
	 */
	public Shape getBaseLegendShape() {
		return this.baseLegendShape;
	}

	/**
	 * Sets the default legend shape and sends a {@link RendererChangeEvent} to
	 * all registered listeners.
	 * 
	 * @param shape
	 *            the shape (<code>null</code> permitted).
	 * 
	 * @since 1.0.11
	 */
	public void setBaseLegendShape(Shape shape) {
		this.baseLegendShape = shape;
	}

	/**
	 * Performs a lookup for the legend text font.
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return The font (possibly <code>null</code>).
	 * 
	 * @since 1.0.11
	 */
	public Font lookupLegendTextFont(int series) {
		Font result = getLegendTextFont(series);
		if (result == null) {
			result = this.baseLegendTextFont;
		}
		return result;
	}

	/**
	 * Returns the legend text font defined for the specified series (possibly
	 * <code>null</code>).
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return The font (possibly <code>null</code>).
	 * 
	 * @see #lookupLegendTextFont(int)
	 * 
	 * @since 1.0.11
	 */
	public Font getLegendTextFont(int series) {
		return (Font) this.legendTextFont.get(series);
	}

	/**
	 * Sets the font used for the legend text for the specified series, and
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param font
	 *            the font (<code>null</code> permitted).
	 * 
	 * @since 1.0.11
	 */
	public void setLegendTextFont(int series, Font font) {
		this.legendTextFont.set(series, font);
	}

	/**
	 * Returns the default legend text font, which may be <code>null</code>.
	 * 
	 * @return The default legend text font.
	 * 
	 * @since 1.0.11
	 */
	public Font getBaseLegendTextFont() {
		return this.baseLegendTextFont;
	}

	/**
	 * Sets the default legend text font and sends a {@link RendererChangeEvent}
	 * to all registered listeners.
	 * 
	 * @param font
	 *            the font (<code>null</code> permitted).
	 * 
	 * @since 1.0.11
	 */
	public void setBaseLegendTextFont(Font font) {
		this.baseLegendTextFont = font;
	}

	/**
	 * Performs a lookup for the legend text paint.
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return The paint (possibly <code>null</code>).
	 * 
	 * @since 1.0.11
	 */
	public Paint lookupLegendTextPaint(int series) {
		Paint result = getLegendTextPaint(series);
		if (result == null) {
			result = this.baseLegendTextPaint;
		}
		return result;
	}

	/**
	 * Returns the legend text paint defined for the specified series (possibly
	 * <code>null</code>).
	 * 
	 * @param series
	 *            the series index.
	 * 
	 * @return The paint (possibly <code>null</code>).
	 * 
	 * @see #lookupLegendTextPaint(int)
	 * 
	 * @since 1.0.11
	 */
	public Paint getLegendTextPaint(int series) {
		return this.legendTextPaint.getPaint(series);
	}

	/**
	 * Sets the paint used for the legend text for the specified series, and
	 * sends a {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param series
	 *            the series index.
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @since 1.0.11
	 */
	public void setLegendTextPaint(int series, Paint paint) {
		this.legendTextPaint.setPaint(series, paint);
	}

	/**
	 * Returns the default legend text paint, which may be <code>null</code>.
	 * 
	 * @return The default legend text paint.
	 * 
	 * @since 1.0.11
	 */
	public Paint getBaseLegendTextPaint() {
		return this.baseLegendTextPaint;
	}

	/**
	 * Sets the default legend text paint and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @since 1.0.11
	 */
	public void setBaseLegendTextPaint(Paint paint) {
		this.baseLegendTextPaint = paint;
	}

	/**
	 * Returns the flag that controls whether or not the data bounds reported by
	 * this renderer will exclude non-visible series.
	 * 
	 * @return A boolean.
	 * 
	 * @since 1.0.13
	 */
	public boolean getDataBoundsIncludesVisibleSeriesOnly() {
		return this.dataBoundsIncludesVisibleSeriesOnly;
	}

	/**
	 * Sets the flag that controls whether or not the data bounds reported by
	 * this renderer will exclude non-visible series and sends a
	 * {@link RendererChangeEvent} to all registered listeners.
	 * 
	 * @param visibleOnly
	 *            include only visible series.
	 * 
	 * @since 1.0.13
	 */
	public void setDataBoundsIncludesVisibleSeriesOnly(boolean visibleOnly) {
		this.dataBoundsIncludesVisibleSeriesOnly = visibleOnly;
	}

	/** The adjacent offset. */
	private static final double ADJ = Math.cos(Math.PI / 6.0);

	/** The opposite offset. */
	private static final double OPP = Math.sin(Math.PI / 6.0);

	/**
	 * Calculates the item label anchor point.
	 * 
	 * @param anchor
	 *            the anchor.
	 * @param x
	 *            the x coordinate.
	 * @param y
	 *            the y coordinate.
	 * @param orientation
	 *            the plot orientation.
	 * 
	 * @return The anchor point (never <code>null</code>).
	 */
	protected Point2D calculateLabelAnchorPoint(ItemLabelAnchor anchor,
			double x, double y, PlotOrientation orientation) {
		Point2D result = null;
		if (anchor == ItemLabelAnchor.CENTER) {
			result = new Point2D.Double(x, y);
		} else if (anchor == ItemLabelAnchor.INSIDE1) {
			result = new Point2D.Double(x + OPP * this.itemLabelAnchorOffset, y
					- ADJ * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE2) {
			result = new Point2D.Double(x + ADJ * this.itemLabelAnchorOffset, y
					- OPP * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE3) {
			result = new Point2D.Double(x + this.itemLabelAnchorOffset, y);
		} else if (anchor == ItemLabelAnchor.INSIDE4) {
			result = new Point2D.Double(x + ADJ * this.itemLabelAnchorOffset, y
					+ OPP * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE5) {
			result = new Point2D.Double(x + OPP * this.itemLabelAnchorOffset, y
					+ ADJ * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE6) {
			result = new Point2D.Double(x, y + this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE7) {
			result = new Point2D.Double(x - OPP * this.itemLabelAnchorOffset, y
					+ ADJ * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE8) {
			result = new Point2D.Double(x - ADJ * this.itemLabelAnchorOffset, y
					+ OPP * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE9) {
			result = new Point2D.Double(x - this.itemLabelAnchorOffset, y);
		} else if (anchor == ItemLabelAnchor.INSIDE10) {
			result = new Point2D.Double(x - ADJ * this.itemLabelAnchorOffset, y
					- OPP * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE11) {
			result = new Point2D.Double(x - OPP * this.itemLabelAnchorOffset, y
					- ADJ * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.INSIDE12) {
			result = new Point2D.Double(x, y - this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE1) {
			result = new Point2D.Double(x + 2.0 * OPP
					* this.itemLabelAnchorOffset, y - 2.0 * ADJ
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE2) {
			result = new Point2D.Double(x + 2.0 * ADJ
					* this.itemLabelAnchorOffset, y - 2.0 * OPP
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE3) {
			result = new Point2D.Double(x + 2.0 * this.itemLabelAnchorOffset, y);
		} else if (anchor == ItemLabelAnchor.OUTSIDE4) {
			result = new Point2D.Double(x + 2.0 * ADJ
					* this.itemLabelAnchorOffset, y + 2.0 * OPP
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE5) {
			result = new Point2D.Double(x + 2.0 * OPP
					* this.itemLabelAnchorOffset, y + 2.0 * ADJ
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE6) {
			result = new Point2D.Double(x, y + 2.0 * this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE7) {
			result = new Point2D.Double(x - 2.0 * OPP
					* this.itemLabelAnchorOffset, y + 2.0 * ADJ
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE8) {
			result = new Point2D.Double(x - 2.0 * ADJ
					* this.itemLabelAnchorOffset, y + 2.0 * OPP
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE9) {
			result = new Point2D.Double(x - 2.0 * this.itemLabelAnchorOffset, y);
		} else if (anchor == ItemLabelAnchor.OUTSIDE10) {
			result = new Point2D.Double(x - 2.0 * ADJ
					* this.itemLabelAnchorOffset, y - 2.0 * OPP
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE11) {
			result = new Point2D.Double(x - 2.0 * OPP
					* this.itemLabelAnchorOffset, y - 2.0 * ADJ
					* this.itemLabelAnchorOffset);
		} else if (anchor == ItemLabelAnchor.OUTSIDE12) {
			result = new Point2D.Double(x, y - 2.0 * this.itemLabelAnchorOffset);
		}
		return result;
	}

}
