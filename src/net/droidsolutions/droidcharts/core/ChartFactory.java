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
 * -----------------
 * ChartFactory.java
 * -----------------
 * (C) Copyright 2001-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Serge V. Grachov;
 *                   Joao Guilherme Del Valle;
 *                   Bill Kelemen;
 *                   Jon Iles;
 *                   Jelai Wang;
 *                   Richard Atkinson;
 *                   David Browning (for Australian Institute of Marine
 *                       Science);
 *                   Benoit Xhenseval;
 *
 * Changes
 * -------
 * 19-Oct-2001 : Version 1, most methods transferred from JFreeChart.java (DG);
 * 22-Oct-2001 : Added methods to create stacked bar charts (DG);
 *               Renamed DataSource.java --> Dataset.java etc. (DG);
 * 31-Oct-2001 : Added 3D-effect vertical bar and stacked-bar charts,
 *               contributed by Serge V. Grachov (DG);
 * 07-Nov-2001 : Added a flag to control whether or not a legend is added to
 *               the chart (DG);
 * 17-Nov-2001 : For pie chart, changed dataset from CategoryDataset to
 *               PieDataset (DG);
 * 30-Nov-2001 : Removed try/catch handlers from chart creation, as the
 *               exception are now RuntimeExceptions, as suggested by Joao
 *               Guilherme Del Valle (DG);
 * 06-Dec-2001 : Added createCombinableXXXXXCharts methods (BK);
 * 12-Dec-2001 : Added createCandlestickChart() method (DG);
 * 13-Dec-2001 : Updated methods for charts with new renderers (DG);
 * 08-Jan-2002 : Added import for
 *               com.jrefinery.chart.combination.CombinedChart (DG);
 * 31-Jan-2002 : Changed the createCombinableVerticalXYBarChart() method to use
 *               renderer (DG);
 * 06-Feb-2002 : Added new method createWindPlot() (DG);
 * 23-Apr-2002 : Updates to the chart and plot constructor API (DG);
 * 21-May-2002 : Added new method createAreaChart() (JI);
 * 06-Jun-2002 : Added new method createGanttChart() (DG);
 * 11-Jun-2002 : Renamed createHorizontalStackedBarChart()
 *               --> createStackedHorizontalBarChart() for consistency (DG);
 * 06-Aug-2002 : Updated Javadoc comments (DG);
 * 21-Aug-2002 : Added createPieChart(CategoryDataset) method (DG);
 * 02-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 09-Oct-2002 : Added methods including tooltips and URL flags (DG);
 * 06-Nov-2002 : Moved renderers into a separate package (DG);
 * 18-Nov-2002 : Changed CategoryDataset to TableDataset (DG);
 * 21-Mar-2003 : Incorporated HorizontalCategoryAxis3D, see bug id 685501 (DG);
 * 13-May-2003 : Merged some horizontal and vertical methods (DG);
 * 24-May-2003 : Added support for timeline in createHighLowChart (BK);
 * 07-Jul-2003 : Added createHistogram() method contributed by Jelai Wang (DG);
 * 27-Jul-2003 : Added createStackedAreaXYChart() method (RA);
 * 05-Aug-2003 : added new method createBoxAndWhiskerChart (DB);
 * 08-Sep-2003 : Changed ValueAxis API (DG);
 * 07-Oct-2003 : Added stepped area XY chart contributed by Matthias Rose (DG);
 * 06-Nov-2003 : Added createWaterfallChart() method (DG);
 * 20-Nov-2003 : Set rendering order for 3D bar charts to fix overlapping
 *               problems (DG);
 * 25-Nov-2003 : Added createWaferMapChart() method (DG);
 * 23-Dec-2003 : Renamed createPie3DChart() --> createPieChart3D for
 *               consistency (DG);
 * 20-Jan-2004 : Added createPolarChart() method (DG);
 * 28-Jan-2004 : Fixed bug (882890) with axis range in
 *               createStackedXYAreaChart() method (DG);
 * 25-Feb-2004 : Renamed XYToolTipGenerator --> XYItemLabelGenerator (DG);
 * 11-Mar-2004 : Updated for pie chart changes (DG);
 * 27-Apr-2004 : Added new createPieChart() method contributed by Benoit
 *               Xhenseval (see RFE 942195) (DG);
 * 11-May-2004 : Split StandardCategoryItemLabelGenerator
 *               --> StandardCategoryToolTipGenerator and
 *               StandardCategoryLabelGenerator (DG);
 * 06-Jan-2005 : Removed deprecated methods (DG);
 * 27-Jan-2005 : Added new constructor to LineAndShapeRenderer (DG);
 * 28-Feb-2005 : Added docs to createBubbleChart() method (DG);
 * 17-Mar-2005 : Added createRingPlot() method (DG);
 * 21-Apr-2005 : Replaced Insets with RectangleInsets (DG);
 * 29-Nov-2005 : Removed signal chart (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 26-Jan-2006 : Corrected API docs for createScatterPlot() (DG);
 * 23-Aug-2006 : Modified createStackedXYAreaChart() to use
 *               StackedXYAreaRenderer2, because StackedXYAreaRenderer doesn't
 *               handle negative values (DG);
 * 27-Sep-2006 : Update createPieChart() method for deprecated code (DG);
 * 29-Nov-2006 : Update createXYBarChart() to use a time based tool tip
 *               generator is a DateAxis is requested (DG);
 * 17-Jan-2007 : Added createBoxAndWhiskerChart() method from patch 1603937
 *               submitted by Darren Jung (DG);
 * 10-Jul-2007 : Added new methods to create pie charts with locale for
 *               section label and tool tip formatting (DG);
 * 14-Aug-2008 : Added ChartTheme facility (DG);
 * 23-Oct-2008 : Check for legacy theme in setChartTheme() and reset default
 *               bar painters (DG);
 * 20-Dec-2008 : In createStackedAreaChart(), set category margin to 0.0 (DG);
 *
 */

package net.droidsolutions.droidcharts.core;

import net.droidsolutions.droidcharts.common.RectangleInsets;
import net.droidsolutions.droidcharts.common.TextAnchor;
import net.droidsolutions.droidcharts.core.axis.CategoryAxis;
import net.droidsolutions.droidcharts.core.axis.NumberAxis;
import net.droidsolutions.droidcharts.core.axis.ValueAxis;
import net.droidsolutions.droidcharts.core.data.CategoryDataset;
import net.droidsolutions.droidcharts.core.data.PieDataset;
import net.droidsolutions.droidcharts.core.label.ItemLabelAnchor;
import net.droidsolutions.droidcharts.core.label.ItemLabelPosition;
import net.droidsolutions.droidcharts.core.label.StandardPieSectionLabelGenerator;
import net.droidsolutions.droidcharts.core.plot.CategoryPlot;
import net.droidsolutions.droidcharts.core.plot.PiePlot;
import net.droidsolutions.droidcharts.core.plot.PlotOrientation;
import net.droidsolutions.droidcharts.core.renderer.BarRenderer;

/**
 * A collection of utility methods for creating some standard charts with
 * JFreeChart.
 */
public abstract class ChartFactory {

	/**
	 * Creates a bar chart. The chart object returned by this method uses a
	 * {@link CategoryPlot} instance as the plot, with a {@link CategoryAxis}
	 * for the domain axis, a {@link NumberAxis} as the range axis, and a
	 * {@link BarRenderer} as the renderer.
	 * 
	 * @param title
	 *            the chart title (<code>null</code> permitted).
	 * @param categoryAxisLabel
	 *            the label for the category axis (<code>null</code> permitted).
	 * @param valueAxisLabel
	 *            the label for the value axis (<code>null</code> permitted).
	 * @param dataset
	 *            the dataset for the chart (<code>null</code> permitted).
	 * @param orientation
	 *            the plot orientation (horizontal or vertical) (
	 *            <code>null</code> not permitted).
	 * @param legend
	 *            a flag specifying whether or not a legend is required.
	 * @param tooltips
	 *            configure chart to generate tool tips?
	 * @param urls
	 *            configure chart to generate URLs?
	 * 
	 * @return A bar chart.
	 */
	public static JFreeChart createBarChart(String title,
			String categoryAxisLabel, String valueAxisLabel,
			CategoryDataset dataset, PlotOrientation orientation,
			boolean legend, boolean tooltips, boolean urls) {

		if (orientation == null) {
			throw new IllegalArgumentException("Null 'orientation' argument.");
		}
		CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
		ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

		BarRenderer renderer = new BarRenderer();
		if (orientation == PlotOrientation.HORIZONTAL) {
			ItemLabelPosition position1 = new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
			renderer.setBasePositiveItemLabelPosition(position1);
			ItemLabelPosition position2 = new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER_RIGHT);
			renderer.setBaseNegativeItemLabelPosition(position2);
		} else if (orientation == PlotOrientation.VERTICAL) {
			ItemLabelPosition position1 = new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);
			renderer.setBasePositiveItemLabelPosition(position1);
			ItemLabelPosition position2 = new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
			renderer.setBaseNegativeItemLabelPosition(position2);
		}

		CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
				renderer);
		plot.setOrientation(orientation);
		JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
				plot, legend);
		return chart;

	}
	
	 /**
     * Creates a pie chart with default settings.
     * <P>
     * The chart object returned by this method uses a {@link PiePlot} instance
     * as the plot.
     *
     * @param title  the chart title (<code>null</code> permitted).
     * @param dataset  the dataset for the chart (<code>null</code> permitted).
     * @param legend  a flag specifying whether or not a legend is required.
     * @param tooltips  configure chart to generate tool tips?
     * @param urls  configure chart to generate URLs?
     *
     * @return A pie chart.
     */
    public static JFreeChart createPieChart(String title,
                                            PieDataset dataset,
                                            boolean legend,
                                            boolean tooltips,
                                            boolean urls) {

        PiePlot plot = new PiePlot(dataset);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator());
        plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
      
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        return chart;
    }

}
