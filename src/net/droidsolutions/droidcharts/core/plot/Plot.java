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
 * ---------
 * Plot.java
 * ---------
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Sylvain Vieujot;
 *                   Jeremy Bowman;
 *                   Andreas Schneider;
 *                   Gideon Krause;
 *                   Nicolas Brodu;
 *                   Michal Krause;
 *                   Richard West, Advanced Micro Devices, Inc.;
 *                   Peter Kolb - patch 2603321;
 *
 * Changes
 * -------
 * 21-Jun-2001 : Removed redundant JFreeChart parameter from constructors (DG);
 * 18-Sep-2001 : Updated header info and fixed DOS encoding problem (DG);
 * 19-Oct-2001 : Moved series paint and stroke methods from JFreeChart
 *               class (DG);
 * 23-Oct-2001 : Created renderer for LinePlot class (DG);
 * 07-Nov-2001 : Changed type names for ChartChangeEvent (DG);
 *               Tidied up some Javadoc comments (DG);
 * 13-Nov-2001 : Changes to allow for null axes on plots such as PiePlot (DG);
 *               Added plot/axis compatibility checks (DG);
 * 12-Dec-2001 : Changed constructors to protected, and removed unnecessary
 *               'throws' clauses (DG);
 * 13-Dec-2001 : Added tooltips (DG);
 * 22-Jan-2002 : Added handleClick() method, as part of implementation for
 *               crosshairs (DG);
 *               Moved tooltips reference into ChartInfo class (DG);
 * 23-Jan-2002 : Added test for null axes in chartChanged() method, thanks
 *               to Barry Evans for the bug report (number 506979 on
 *               SourceForge) (DG);
 *               Added a zoom() method (DG);
 * 05-Feb-2002 : Updated setBackgroundPaint(), setOutlineStroke() and
 *               setOutlinePaint() to better handle null values, as suggested
 *               by Sylvain Vieujot (DG);
 * 06-Feb-2002 : Added background image, plus alpha transparency for background
 *               and foreground (DG);
 * 06-Mar-2002 : Added AxisConstants interface (DG);
 * 26-Mar-2002 : Changed zoom method from empty to abstract (DG);
 * 23-Apr-2002 : Moved dataset from JFreeChart class (DG);
 * 11-May-2002 : Added ShapeFactory interface for getShape() methods,
 *               contributed by Jeremy Bowman (DG);
 * 28-May-2002 : Fixed bug in setSeriesPaint(int, Paint) for subplots (AS);
 * 25-Jun-2002 : Removed redundant imports (DG);
 * 30-Jul-2002 : Added 'no data' message for charts with null or empty
 *               datasets (DG);
 * 21-Aug-2002 : Added code to extend series array if necessary (refer to
 *               SourceForge bug id 594547 for details) (DG);
 * 17-Sep-2002 : Fixed bug in getSeriesOutlineStroke() method, reported by
 *               Andreas Schroeder (DG);
 * 23-Sep-2002 : Added getLegendItems() abstract method (DG);
 * 24-Sep-2002 : Removed firstSeriesIndex, subplots now use their own paint
 *               settings, there is a new mechanism for the legend to collect
 *               the legend items (DG);
 * 27-Sep-2002 : Added dataset group (DG);
 * 14-Oct-2002 : Moved listener storage into EventListenerList.  Changed some
 *               abstract methods to empty implementations (DG);
 * 28-Oct-2002 : Added a getBackgroundImage() method (DG);
 * 21-Nov-2002 : Added a plot index for identifying subplots in combined and
 *               overlaid charts (DG);
 * 22-Nov-2002 : Changed all attributes from 'protected' to 'private'.  Added
 *               dataAreaRatio attribute from David M O'Donnell's code (DG);
 * 09-Jan-2003 : Integrated fix for plot border contributed by Gideon
 *               Krause (DG);
 * 17-Jan-2003 : Moved to com.jrefinery.chart.plot (DG);
 * 23-Jan-2003 : Removed one constructor (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 14-Jul-2003 : Moved the dataset and secondaryDataset attributes to the
 *               CategoryPlot and XYPlot classes (DG);
 * 21-Jul-2003 : Moved DrawingSupplier from CategoryPlot and XYPlot up to this
 *               class (DG);
 * 20-Aug-2003 : Implemented Cloneable (DG);
 * 11-Sep-2003 : Listeners and clone (NB);
 * 29-Oct-2003 : Added workaround for font alignment in PDF output (DG);
 * 03-Dec-2003 : Modified draw method to accept anchor (DG);
 * 12-Mar-2004 : Fixed clipping bug in drawNoDataMessage() method (DG);
 * 07-Apr-2004 : Modified string bounds calculation (DG);
 * 04-Nov-2004 : Added default shapes for legend items (DG);
 * 25-Nov-2004 : Some changes to the clone() method implementation (DG);
 * 23-Feb-2005 : Implemented new LegendItemSource interface (and also
 *               PublicCloneable) (DG);
 * 21-Apr-2005 : Replaced Insets with RectangleInsets (DG);
 * 05-May-2005 : Removed unused draw() method (DG);
 * 06-Jun-2005 : Fixed bugs in equals() method (DG);
 * 01-Sep-2005 : Moved dataAreaRatio from here to ContourPlot (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 30-Jun-2006 : Added background image alpha - see bug report 1514904 (DG);
 * 05-Sep-2006 : Implemented the MarkerChangeListener interface (DG);
 * 11-Jan-2007 : Added some argument checks, event notifications, and many
 *               API doc updates (DG);
 * 03-Apr-2007 : Made drawBackgroundImage() public (DG);
 * 07-Jun-2007 : Added new fillBackground() method to handle GradientPaint
 *               taking into account orientation (DG);
 * 25-Mar-2008 : Added fireChangeEvent() method - see patch 1914411 (DG);
 * 15-Aug-2008 : Added setDrawingSupplier() method with notify flag (DG);
 * 13-Jan-2009 : Added notify flag (DG);
 * 19-Mar-2009 : Added entity support - see patch 2603321 by Peter Kolb (DG);
 *
 */

package net.droidsolutions.droidcharts.core.plot;

import java.io.Serializable;

import net.droidsolutions.droidcharts.awt.Ellipse2D;
import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.awt.Point2D;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.awt.Shape;
import net.droidsolutions.droidcharts.common.Align;
import net.droidsolutions.droidcharts.common.RectangleEdge;
import net.droidsolutions.droidcharts.common.RectangleInsets;
import net.droidsolutions.droidcharts.core.JFreeChart;
import net.droidsolutions.droidcharts.core.LegendItemCollection;
import net.droidsolutions.droidcharts.core.LegendItemSource;
import net.droidsolutions.droidcharts.core.axis.AxisLocation;
import net.droidsolutions.droidcharts.core.data.general.DatasetGroup;
import net.droidsolutions.droidcharts.core.entity.EntityCollection;
import net.droidsolutions.droidcharts.core.entity.PlotEntity;
import net.droidsolutions.droidcharts.core.event.PlotChangeEvent;
import net.droidsolutions.droidcharts.core.text.G2TextMeasurer;
import net.droidsolutions.droidcharts.core.text.TextBlock;
import net.droidsolutions.droidcharts.core.text.TextBlockAnchor;
import net.droidsolutions.droidcharts.core.text.TextUtilities;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * The base class for all plots in JFreeChart. The {@link JFreeChart} class
 * delegates the drawing of axes and data to the plot. This base class provides
 * facilities common to most plot types.
 */
public abstract class Plot implements LegendItemSource, Cloneable, Serializable {

	/** For serialization. */
	private static final long serialVersionUID = -8831571430103671324L;

	/** Useful constant representing zero. */
	public static final Number ZERO = new Integer(0);

	/** The default insets. */
	public static final RectangleInsets DEFAULT_INSETS = new RectangleInsets(
			0.0, 0.0, 0.0, 0.0);

	/** The default outline stroke. */
	public static final float DEFAULT_OUTLINE_STROKE = 0.5f;

	/** The default outline color. */
	public static final Paint DEFAULT_OUTLINE_PAINT = new Paint(
			Paint.ANTI_ALIAS_FLAG);
	static {
		DEFAULT_OUTLINE_PAINT.setColor(Color.DKGRAY);
	}

	/** The default foreground alpha transparency. */
	public static final int DEFAULT_FOREGROUND_ALPHA = 200;

	/** The default background alpha transparency. */
	public static final int DEFAULT_BACKGROUND_ALPHA = 200;

	/** The default background color. */
	public static final Paint DEFAULT_BACKGROUND_PAINT = new Paint(
			Paint.ANTI_ALIAS_FLAG);
	static {
		DEFAULT_BACKGROUND_PAINT.setColor(Color.WHITE);
	}
	/** The minimum width at which the plot should be drawn. */
	public static final int MINIMUM_WIDTH_TO_DRAW = 10;

	/** The minimum height at which the plot should be drawn. */
	public static final int MINIMUM_HEIGHT_TO_DRAW = 10;

	/** A default box shape for legend items. */
	public static final Shape DEFAULT_LEGEND_ITEM_BOX = new Rectangle2D.Double(
			-4.0, -4.0, 8.0, 8.0);

	/** A default circle shape for legend items. */
	public static final Shape DEFAULT_LEGEND_ITEM_CIRCLE = new Ellipse2D.Double(
			-4.0, -4.0, 8.0, 8.0);

	/** The parent plot (<code>null</code> if this is the root plot). */
	private Plot parent;

	/** The dataset group (to be used for thread synchronisation). */
	private DatasetGroup datasetGroup;

	/** The message to display if no data is available. */
	private String noDataMessage;

	/** The font used to display the 'no data' message. */
	private Font noDataMessageFont;

	/** The paint used to draw the 'no data' message. */
	private transient Paint noDataMessagePaint;

	/** Amount of blank space around the plot area. */
	private RectangleInsets insets;

	/**
	 * A flag that controls whether or not the plot outline is drawn.
	 * 
	 * @since 1.0.6
	 */
	private boolean outlineVisible;

	/** The Stroke used to draw an outline around the plot. */
	private transient float outlineStroke;

	/** The Paint used to draw an outline around the plot. */
	private transient Paint outlinePaint;

	/** An optional color used to fill the plot background. */
	private transient Paint backgroundPaint;

	/** The alignment for the background image. */
	private int backgroundImageAlignment = Align.FIT;

	/** The alpha value used to draw the background image. */
	private float backgroundImageAlpha = 0.5f;

	/** The alpha-transparency for the plot. */
	private int foregroundAlpha;

	/** The alpha transparency for the background paint. */
	private int backgroundAlpha;

	/** The drawing supplier. */
	private DrawingSupplier drawingSupplier;

	/**
	 * A flag that controls whether or not the plot will notify listeners of
	 * changes (defaults to true, but sometimes it is useful to disable this).
	 * 
	 * @since 1.0.13
	 */
	private boolean notify;

	/**
	 * Creates a new plot.
	 */
	protected Plot() {

		this.parent = null;
		this.insets = DEFAULT_INSETS;
		this.backgroundPaint = DEFAULT_BACKGROUND_PAINT;
		this.backgroundAlpha = DEFAULT_BACKGROUND_ALPHA;

		this.outlineVisible = true;
		this.outlineStroke = DEFAULT_OUTLINE_STROKE;
		this.outlinePaint = DEFAULT_OUTLINE_PAINT;
		this.foregroundAlpha = DEFAULT_FOREGROUND_ALPHA;

		this.noDataMessage = null;
		this.noDataMessageFont = new Font("SansSerif", Typeface.NORMAL, 12);
		this.noDataMessagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.noDataMessagePaint.setARGB(0, 255, 255, 255);

		this.drawingSupplier = new DefaultDrawingSupplier();

	}

	/**
	 * Returns the dataset group for the plot (not currently used).
	 * 
	 * @return The dataset group.
	 * 
	 * @see #setDatasetGroup(DatasetGroup)
	 */
	public DatasetGroup getDatasetGroup() {
		return this.datasetGroup;
	}

	/**
	 * Sets the dataset group (not currently used).
	 * 
	 * @param group
	 *            the dataset group (<code>null</code> permitted).
	 * 
	 * @see #getDatasetGroup()
	 */
	protected void setDatasetGroup(DatasetGroup group) {
		this.datasetGroup = group;
	}

	/**
	 * Returns the string that is displayed when the dataset is empty or
	 * <code>null</code>.
	 * 
	 * @return The 'no data' message (<code>null</code> possible).
	 * 
	 * @see #setNoDataMessage(String)
	 * @see #getNoDataMessageFont()
	 * @see #getNoDataMessagePaint()
	 */
	public String getNoDataMessage() {
		return this.noDataMessage;
	}

	/**
	 * Sets the message that is displayed when the dataset is empty or
	 * <code>null</code>, and sends a {@link PlotChangeEvent} to all registered
	 * listeners.
	 * 
	 * @param message
	 *            the message (<code>null</code> permitted).
	 * 
	 * @see #getNoDataMessage()
	 */
	public void setNoDataMessage(String message) {
		this.noDataMessage = message;
	}

	/**
	 * Returns the font used to display the 'no data' message.
	 * 
	 * @return The font (never <code>null</code>).
	 * 
	 * @see #setNoDataMessageFont(Font)
	 * @see #getNoDataMessage()
	 */
	public Font getNoDataMessageFont() {
		return this.noDataMessageFont;
	}

	/**
	 * Sets the font used to display the 'no data' message and sends a
	 * {@link PlotChangeEvent} to all registered listeners.
	 * 
	 * @param font
	 *            the font (<code>null</code> not permitted).
	 * 
	 * @see #getNoDataMessageFont()
	 */
	public void setNoDataMessageFont(Font font) {
		if (font == null) {
			throw new IllegalArgumentException("Null 'font' argument.");
		}
		this.noDataMessageFont = font;
	}

	/**
	 * Returns the paint used to display the 'no data' message.
	 * 
	 * @return The paint (never <code>null</code>).
	 * 
	 * @see #setNoDataMessagePaint(Paint)
	 * @see #getNoDataMessage()
	 */
	public Paint getNoDataMessagePaint() {
		return this.noDataMessagePaint;
	}

	/**
	 * Sets the paint used to display the 'no data' message and sends a
	 * {@link PlotChangeEvent} to all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> not permitted).
	 * 
	 * @see #getNoDataMessagePaint()
	 */
	public void setNoDataMessagePaint(Paint paint) {
		if (paint == null) {
			throw new IllegalArgumentException("Null 'paint' argument.");
		}
		this.noDataMessagePaint = paint;
	}

	/**
	 * Returns a short string describing the plot type.
	 * <P>
	 * Note: this gets used in the chart property editing user interface, but
	 * there needs to be a better mechanism for identifying the plot type.
	 * 
	 * @return A short string describing the plot type (never <code>null</code>
	 *         ).
	 */
	public abstract String getPlotType();

	/**
	 * Returns the parent plot (or <code>null</code> if this plot is not part of
	 * a combined plot).
	 * 
	 * @return The parent plot.
	 * 
	 * @see #setParent(Plot)
	 * @see #getRootPlot()
	 */
	public Plot getParent() {
		return this.parent;
	}

	/**
	 * Sets the parent plot. This method is intended for internal use, you
	 * shouldn't need to call it directly.
	 * 
	 * @param parent
	 *            the parent plot (<code>null</code> permitted).
	 * 
	 * @see #getParent()
	 */
	public void setParent(Plot parent) {
		this.parent = parent;
	}

	/**
	 * Returns the root plot.
	 * 
	 * @return The root plot.
	 * 
	 * @see #getParent()
	 */
	public Plot getRootPlot() {

		Plot p = getParent();
		if (p == null) {
			return this;
		} else {
			return p.getRootPlot();
		}

	}

	/**
	 * Returns <code>true</code> if this plot is part of a combined plot
	 * structure (that is, {@link #getParent()} returns a non-<code>null</code>
	 * value), and <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if this plot is part of a combined plot
	 *         structure.
	 * 
	 * @see #getParent()
	 */
	public boolean isSubplot() {
		return (getParent() != null);
	}

	/**
	 * Returns the insets for the plot area.
	 * 
	 * @return The insets (never <code>null</code>).
	 * 
	 * @see #setInsets(RectangleInsets)
	 */
	public RectangleInsets getInsets() {
		return this.insets;
	}

	/**
	 * Sets the insets for the plot and sends a {@link PlotChangeEvent} to all
	 * registered listeners.
	 * 
	 * @param insets
	 *            the new insets (<code>null</code> not permitted).
	 * 
	 * @see #getInsets()
	 * @see #setInsets(RectangleInsets, boolean)
	 */
	public void setInsets(RectangleInsets insets) {
		setInsets(insets, true);
	}

	/**
	 * Sets the insets for the plot and, if requested, and sends a
	 * {@link PlotChangeEvent} to all registered listeners.
	 * 
	 * @param insets
	 *            the new insets (<code>null</code> not permitted).
	 * @param notify
	 *            a flag that controls whether the registered listeners are
	 *            notified.
	 * 
	 * @see #getInsets()
	 * @see #setInsets(RectangleInsets)
	 */
	public void setInsets(RectangleInsets insets, boolean notify) {
		if (insets == null) {
			throw new IllegalArgumentException("Null 'insets' argument.");
		}
		if (!this.insets.equals(insets)) {
			this.insets = insets;

		}

	}

	/**
	 * Returns the background color of the plot area.
	 * 
	 * @return The paint (possibly <code>null</code>).
	 * 
	 * @see #setBackgroundPaint(Paint)
	 */
	public Paint getBackgroundPaint() {
		return this.backgroundPaint;
	}

	/**
	 * Sets the background color of the plot area and sends a
	 * {@link PlotChangeEvent} to all registered listeners.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @see #getBackgroundPaint()
	 */
	public void setBackgroundPaint(Paint paint) {

		if (paint == null) {
			if (this.backgroundPaint != null) {
				this.backgroundPaint = null;
			}
		} else {
			if (this.backgroundPaint != null) {
				if (this.backgroundPaint.equals(paint)) {
					return; // nothing to do
				}
			}
			this.backgroundPaint = paint;
		}

	}

	/**
	 * Returns the alpha transparency of the plot area background.
	 * 
	 * @return The alpha transparency.
	 * 
	 * @see #setBackgroundAlpha(float)
	 */
	public float getBackgroundAlpha() {
		return this.backgroundAlpha;
	}

	/**
	 * Sets the alpha transparency of the plot area background, and notifies
	 * registered listeners that the plot has been modified.
	 * 
	 * @param alpha
	 *            the new alpha value (in the range 0.0f to 1.0f).
	 * 
	 * @see #getBackgroundAlpha()
	 */
	public void setBackgroundAlpha(int alpha) {
		if (this.backgroundAlpha != alpha) {
			this.backgroundAlpha = alpha;
		}
	}

	/**
	 * Returns the drawing supplier for the plot.
	 * 
	 * @return The drawing supplier (possibly <code>null</code>).
	 * 
	 * @see #setDrawingSupplier(DrawingSupplier)
	 */
	public DrawingSupplier getDrawingSupplier() {
		DrawingSupplier result = null;
		Plot p = getParent();
		if (p != null) {
			result = p.getDrawingSupplier();
		} else {
			result = this.drawingSupplier;
		}
		return result;
	}

	/**
	 * Sets the drawing supplier for the plot and sends a
	 * {@link PlotChangeEvent} to all registered listeners. The drawing supplier
	 * is responsible for supplying a limitless (possibly repeating) sequence of
	 * <code>Paint</code>, <code>Stroke</code> and <code>Shape</code> objects
	 * that the plot's renderer(s) can use to populate its (their) tables.
	 * 
	 * @param supplier
	 *            the new supplier.
	 * 
	 * @see #getDrawingSupplier()
	 */
	public void setDrawingSupplier(DrawingSupplier supplier) {
		this.drawingSupplier = supplier;
	}

	/**
	 * Sets the drawing supplier for the plot and, if requested, sends a
	 * {@link PlotChangeEvent} to all registered listeners. The drawing supplier
	 * is responsible for supplying a limitless (possibly repeating) sequence of
	 * <code>Paint</code>, <code>Stroke</code> and <code>Shape</code> objects
	 * that the plot's renderer(s) can use to populate its (their) tables.
	 * 
	 * @param supplier
	 *            the new supplier.
	 * @param notify
	 *            notify listeners?
	 * 
	 * @see #getDrawingSupplier()
	 * 
	 * @since 1.0.11
	 */
	public void setDrawingSupplier(DrawingSupplier supplier, boolean notify) {
		this.drawingSupplier = supplier;

	}

	/**
	 * Returns the flag that controls whether or not the plot outline is drawn.
	 * The default value is <code>true</code>. Note that for historical reasons,
	 * the plot's outline paint and stroke can take on <code>null</code> values,
	 * in which case the outline will not be drawn even if this flag is set to
	 * <code>true</code>.
	 * 
	 * @return The outline visibility flag.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #setOutlineVisible(boolean)
	 */
	public boolean isOutlineVisible() {
		return this.outlineVisible;
	}

	/**
	 * Sets the flag that controls whether or not the plot's outline is drawn,
	 * and sends a {@link PlotChangeEvent} to all registered listeners.
	 * 
	 * @param visible
	 *            the new flag value.
	 * 
	 * @since 1.0.6
	 * 
	 * @see #isOutlineVisible()
	 */
	public void setOutlineVisible(boolean visible) {
		this.outlineVisible = visible;
	}

	/**
	 * Returns the stroke used to outline the plot area.
	 * 
	 * @return The stroke (possibly <code>null</code>).
	 * 
	 * @see #setOutlineStroke(Stroke)
	 */
	public float getOutlineStroke() {
		return this.outlineStroke;
	}

	/**
	 * Sets the stroke used to outline the plot area and sends a
	 * {@link PlotChangeEvent} to all registered listeners. If you set this
	 * attribute to <code>null</code>, no outline will be drawn.
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
	 * Returns the color used to draw the outline of the plot area.
	 * 
	 * @return The color (possibly <code>null<code>).
	 * 
	 * @see #setOutlinePaint(Paint)
	 */
	public Paint getOutlinePaint() {
		return this.outlinePaint;
	}

	/**
	 * Sets the paint used to draw the outline of the plot area and sends a
	 * {@link PlotChangeEvent} to all registered listeners. If you set this
	 * attribute to <code>null</code>, no outline will be drawn.
	 * 
	 * @param paint
	 *            the paint (<code>null</code> permitted).
	 * 
	 * @see #getOutlinePaint()
	 */
	public void setOutlinePaint(Paint paint) {
		if (paint == null) {
			if (this.outlinePaint != null) {
				this.outlinePaint = null;
			}
		} else {
			if (this.outlinePaint != null) {
				if (this.outlinePaint.equals(paint)) {
					return; // nothing to do
				}
			}
			this.outlinePaint = paint;
		}
	}

	/**
	 * Returns the alpha-transparency for the plot foreground.
	 * 
	 * @return The alpha-transparency.
	 * 
	 * @see #setForegroundAlpha(float)
	 */
	public int getForegroundAlpha() {
		return this.foregroundAlpha;
	}

	/**
	 * Sets the alpha-transparency for the plot and sends a
	 * {@link PlotChangeEvent} to all registered listeners.
	 * 
	 * @param alpha
	 *            the new alpha transparency.
	 * 
	 * @see #getForegroundAlpha()
	 */
	public void setForegroundAlpha(int alpha) {
		if (this.foregroundAlpha != alpha) {
			this.foregroundAlpha = alpha;
		}
	}

	/**
	 * Returns the legend items for the plot. By default, this method returns
	 * <code>null</code>. Subclasses should override to return a
	 * {@link LegendItemCollection}.
	 * 
	 * @return The legend items for the plot (possibly <code>null</code>).
	 */
	public LegendItemCollection getLegendItems() {
		return null;
	}

	/**
	 * Returns a flag that controls whether or not change events are sent to
	 * registered listeners.
	 * 
	 * @return A boolean.
	 * 
	 * @see #setNotify(boolean)
	 * 
	 * @since 1.0.13
	 */
	public boolean isNotify() {
		return this.notify;
	}

	/**
	 * Draws the plot within the specified area. The anchor is a point on the
	 * chart that is specified externally (for instance, it may be the last
	 * point of the last mouse click performed by the user) - plots can use or
	 * ignore this value as they see fit. <br>
	 * <br>
	 * Subclasses need to provide an implementation of this method, obviously.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the plot area.
	 * @param anchor
	 *            the anchor point (<code>null</code> permitted).
	 * @param parentState
	 *            the parent state (if any).
	 * @param info
	 *            carries back plot rendering info.
	 */
	public abstract void draw(Canvas g2, Rectangle2D area, Point2D anchor,
			PlotState parentState, PlotRenderingInfo info);

	/**
	 * Draws the plot background (the background color and/or image).
	 * <P>
	 * This method will be called during the chart drawing process and is
	 * declared public so that it can be accessed by the renderers used by
	 * certain subclasses. You shouldn't need to call this method directly.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area within which the plot should be drawn.
	 */
	public void drawBackground(Canvas g2, Rectangle2D area) {
		// some subclasses override this method completely, so don't put
		// anything here that *must* be done
		fillBackground(g2, area);
	}

	/**
	 * Fills the specified area with the background paint.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area.
	 * 
	 * @see #getBackgroundPaint()
	 * @see #getBackgroundAlpha()
	 * @see #fillBackground(Graphics2D, Rectangle2D, PlotOrientation)
	 */
	protected void fillBackground(Canvas g2, Rectangle2D area) {
		fillBackground(g2, area, PlotOrientation.VERTICAL);
	}

	/**
	 * Fills the specified area with the background paint. If the background
	 * paint is an instance of <code>GradientPaint</code>, the gradient will run
	 * in the direction suggested by the plot's orientation.
	 * 
	 * @param g2
	 *            the graphics target.
	 * @param area
	 *            the plot area.
	 * @param orientation
	 *            the plot orientation (<code>null</code> not permitted).
	 * 
	 * @since 1.0.6
	 */
	protected void fillBackground(Canvas g2, Rectangle2D area,
			PlotOrientation orientation) {
		if (orientation == null) {
			throw new IllegalArgumentException("Null 'orientation' argument.");
		}
		if (this.backgroundPaint == null) {
			return;
		}
		Paint p = this.backgroundPaint;
		/*
		 * if (p instanceof GradientPaint) { GradientPaint gp = (GradientPaint)
		 * p; if (orientation == PlotOrientation.VERTICAL) { p = new
		 * GradientPaint((float) area.getCenterX(), (float) area.getMaxY(),
		 * gp.getColor1(), (float) area.getCenterX(), (float) area.getMinY(),
		 * gp.getColor2()); } else if (orientation ==
		 * PlotOrientation.HORIZONTAL) { p = new GradientPaint((float)
		 * area.getMinX(), (float) area.getCenterY(), gp.getColor1(), (float)
		 * area.getMaxX(), (float) area.getCenterY(), gp.getColor2()); } }
		 */
		int oldAlpha = p.getAlpha();
		Paint.Style oldStyle = p.getStyle();

		p.setAlpha(150);
		p.setStyle(Paint.Style.FILL);
		g2.drawRect((float) area.getMinX(), (float) area.getMinY(),
				(float) area.getMaxX(), (float) area.getMaxY(), p);
		p.setAlpha(oldAlpha);
		p.setStyle(oldStyle);

	}

	/**
	 * Draws the plot outline. This method will be called during the chart
	 * drawing process and is declared public so that it can be accessed by the
	 * renderers used by certain subclasses. You shouldn't need to call this
	 * method directly.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area within which the plot should be drawn.
	 */
	public void drawOutline(Canvas g2, Rectangle2D area) {
		if (!this.outlineVisible) {
			return;
		}
		if ((this.outlinePaint != null)) {

			float oldStroke = outlinePaint.getStrokeWidth();
			Paint.Style oldStyle = outlinePaint.getStyle();

			outlinePaint.setStrokeWidth(outlineStroke);
			outlinePaint.setStyle(Paint.Style.STROKE);
			g2.drawRect((float) area.getMinX(), (float) area.getMinY(),
					(float) area.getMaxX(), (float) area.getMaxY(),
					outlinePaint);
			outlinePaint.setStrokeWidth(outlineStroke);
			outlinePaint.setStyle(oldStyle);

		}
	}

	/**
	 * Draws a message to state that there is no data to plot.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area within which the plot should be drawn.
	 */
	protected void drawNoDataMessage(Canvas g2, Rectangle2D area) {
		g2.save();
		g2.clipRect((float) area.getMinX(), (float) area.getMinY(),
				(float) area.getMaxX(), (float) area.getMaxY());
		String message = this.noDataMessage;
		if (message != null) {

			// g2.setFont(this.noDataMessageFont);

			// noDataMessagePaint
			TextBlock block = TextUtilities.createTextBlock(this.noDataMessage,
					this.noDataMessageFont, this.noDataMessagePaint,
					0.9f * (float) area.getWidth(), new G2TextMeasurer(
							noDataMessagePaint));

			//noDataMessagePaint.setAlpha(alpha);
			block.draw(g2, (float) area.getCenterX(),
					(float) area.getCenterY(), TextBlockAnchor.CENTER,
					noDataMessagePaint);
		}
		g2.restore();
	}

	/**
	 * Creates a plot entity that contains a reference to the plot and the data
	 * area as shape.
	 * 
	 * @param dataArea
	 *            the data area used as hot spot for the entity.
	 * @param plotState
	 *            the plot rendering info containing a reference to the
	 *            EntityCollection.
	 * @param toolTip
	 *            the tool tip (defined in the respective Plot subclass) (
	 *            <code>null</code> permitted).
	 * @param urlText
	 *            the url (defined in the respective Plot subclass) (
	 *            <code>null</code> permitted).
	 * 
	 * @since 1.0.13
	 */
	protected void createAndAddEntity(Rectangle2D dataArea,
			PlotRenderingInfo plotState, String toolTip, String urlText) {
		if (plotState != null && plotState.getOwner() != null) {
			EntityCollection e = plotState.getOwner().getEntityCollection();
			if (e != null) {
				e.add(new PlotEntity(dataArea, this, toolTip, urlText));
			}
		}
	}

	/**
	 * Handles a 'click' on the plot. Since the plot does not maintain any
	 * information about where it has been drawn, the plot rendering info is
	 * supplied as an argument so that the plot dimensions can be determined.
	 * 
	 * @param x
	 *            the x coordinate (in Java2D space).
	 * @param y
	 *            the y coordinate (in Java2D space).
	 * @param info
	 *            an object containing information about the dimensions of the
	 *            plot.
	 */
	public void handleClick(int x, int y, PlotRenderingInfo info) {
		// provides a 'no action' default
	}

	/**
	 * Performs a zoom on the plot. Subclasses should override if zooming is
	 * appropriate for the type of plot.
	 * 
	 * @param percent
	 *            the zoom percentage.
	 */
	public void zoom(double percent) {
		// do nothing by default.
	}

	/**
	 * Adjusts the supplied x-value.
	 * 
	 * @param x
	 *            the x-value.
	 * @param w1
	 *            width 1.
	 * @param w2
	 *            width 2.
	 * @param edge
	 *            the edge (left or right).
	 * 
	 * @return The adjusted x-value.
	 */
	protected double getRectX(double x, double w1, double w2, RectangleEdge edge) {

		double result = x;
		if (edge == RectangleEdge.LEFT) {
			result = result + w1;
		} else if (edge == RectangleEdge.RIGHT) {
			result = result + w2;
		}
		return result;

	}

	/**
	 * Adjusts the supplied y-value.
	 * 
	 * @param y
	 *            the x-value.
	 * @param h1
	 *            height 1.
	 * @param h2
	 *            height 2.
	 * @param edge
	 *            the edge (top or bottom).
	 * 
	 * @return The adjusted y-value.
	 */
	protected double getRectY(double y, double h1, double h2, RectangleEdge edge) {

		double result = y;
		if (edge == RectangleEdge.TOP) {
			result = result + h1;
		} else if (edge == RectangleEdge.BOTTOM) {
			result = result + h2;
		}
		return result;

	}

	/**
	 * Resolves a domain axis location for a given plot orientation.
	 * 
	 * @param location
	 *            the location (<code>null</code> not permitted).
	 * @param orientation
	 *            the orientation (<code>null</code> not permitted).
	 * 
	 * @return The edge (never <code>null</code>).
	 */
	public static RectangleEdge resolveDomainAxisLocation(
			AxisLocation location, PlotOrientation orientation) {

		if (location == null) {
			throw new IllegalArgumentException("Null 'location' argument.");
		}
		if (orientation == null) {
			throw new IllegalArgumentException("Null 'orientation' argument.");
		}

		RectangleEdge result = null;

		if (location == AxisLocation.TOP_OR_RIGHT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.RIGHT;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.TOP;
			}
		} else if (location == AxisLocation.TOP_OR_LEFT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.LEFT;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.TOP;
			}
		} else if (location == AxisLocation.BOTTOM_OR_RIGHT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.RIGHT;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.BOTTOM;
			}
		} else if (location == AxisLocation.BOTTOM_OR_LEFT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.LEFT;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.BOTTOM;
			}
		}
		// the above should cover all the options...
		if (result == null) {
			throw new IllegalStateException("resolveDomainAxisLocation()");
		}
		return result;

	}

	/**
	 * Resolves a range axis location for a given plot orientation.
	 * 
	 * @param location
	 *            the location (<code>null</code> not permitted).
	 * @param orientation
	 *            the orientation (<code>null</code> not permitted).
	 * 
	 * @return The edge (never <code>null</code>).
	 */
	public static RectangleEdge resolveRangeAxisLocation(AxisLocation location,
			PlotOrientation orientation) {

		if (location == null) {
			throw new IllegalArgumentException("Null 'location' argument.");
		}
		if (orientation == null) {
			throw new IllegalArgumentException("Null 'orientation' argument.");
		}

		RectangleEdge result = null;

		if (location == AxisLocation.TOP_OR_RIGHT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.TOP;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.RIGHT;
			}
		} else if (location == AxisLocation.TOP_OR_LEFT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.TOP;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.LEFT;
			}
		} else if (location == AxisLocation.BOTTOM_OR_RIGHT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.BOTTOM;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.RIGHT;
			}
		} else if (location == AxisLocation.BOTTOM_OR_LEFT) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				result = RectangleEdge.BOTTOM;
			} else if (orientation == PlotOrientation.VERTICAL) {
				result = RectangleEdge.LEFT;
			}
		}

		// the above should cover all the options...
		if (result == null) {
			throw new IllegalStateException("resolveRangeAxisLocation()");
		}
		return result;

	}

}
