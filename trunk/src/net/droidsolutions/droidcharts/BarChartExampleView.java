package net.droidsolutions.droidcharts;

import java.text.DecimalFormat;

import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.common.RectangleInsets;
import net.droidsolutions.droidcharts.common.TextAnchor;
import net.droidsolutions.droidcharts.core.ChartFactory;
import net.droidsolutions.droidcharts.core.JFreeChart;
import net.droidsolutions.droidcharts.core.axis.CategoryAxis;
import net.droidsolutions.droidcharts.core.axis.CategoryLabelPositions;
import net.droidsolutions.droidcharts.core.axis.NumberAxis;
import net.droidsolutions.droidcharts.core.axis.ValueAxis;
import net.droidsolutions.droidcharts.core.data.CategoryDataset;
import net.droidsolutions.droidcharts.core.data.general.DatasetUtilities;
import net.droidsolutions.droidcharts.core.label.ItemLabelAnchor;
import net.droidsolutions.droidcharts.core.label.ItemLabelPosition;
import net.droidsolutions.droidcharts.core.label.StandardCategoryItemLabelGenerator;
import net.droidsolutions.droidcharts.core.plot.CategoryPlot;
import net.droidsolutions.droidcharts.core.plot.PlotOrientation;
import net.droidsolutions.droidcharts.core.renderer.BarRenderer;
import net.droidsolutions.droidcharts.core.renderer.CategoryItemRenderer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;

public class BarChartExampleView extends View {
	/** The view bounds. */
	private Rect mRect = new Rect();
	/** The user interface thread handler. */
	private Handler mHandler;

	/**
	 * Creates a new graphical view.
	 * 
	 * @param context
	 *            the context
	 * @param chart
	 *            the chart to be drawn
	 */
	public BarChartExampleView(Context context) {
		super(context);
		mHandler = new Handler();
	}

	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		canvas.getClipBounds(mRect);

		final CategoryDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		chart.draw(canvas, new Rectangle2D.Double(0, 0, mRect.width(), mRect
				.height()));
		Paint p = new Paint();
		p.setColor(Color.RED);
	}

	/**
	 * Schedule a user interface repaint.
	 */
	public void repaint() {
		mHandler.post(new Runnable() {
			public void run() {
				invalidate();
			}
		});
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return a sample dataset.
	 */
	private CategoryDataset createDataset() {
		final double[][] data = new double[][] { { 40.0, 30.0, -20.5, 30.0, 6.0 } };
		return DatasetUtilities.createCategoryDataset("Series ", "Category ",
				data);
	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return a sample chart.
	 */
	private JFreeChart createChart(final CategoryDataset dataset) {

		final JFreeChart chart = ChartFactory.createBarChart("", // chart title
				"Category", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // the plot orientation
				false, // include legend
				true, false);

		// set the background color for the chart...
		Paint white = new Paint(Paint.ANTI_ALIAS_FLAG);
		white.setColor(Color.WHITE);

		Paint dkGray = new Paint(Paint.ANTI_ALIAS_FLAG);
		dkGray.setColor(Color.DKGRAY);

		Paint lightGray = new Paint(Paint.ANTI_ALIAS_FLAG);
		lightGray.setColor(Color.LTGRAY);
		lightGray.setStrokeWidth(10);

		Paint black = new Paint(Paint.ANTI_ALIAS_FLAG);
		black.setColor(Color.BLACK);

		Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		borderPaint.setColor(Color.WHITE);
		borderPaint.setStrokeWidth(5);
		chart.setBorderPaint(borderPaint);
		chart.setBackgroundPaint(dkGray);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		Resources res = getResources();

		final CategoryItemRenderer renderer = new CustomRenderer(new int[] {
				res.getColor(R.color.color0), res.getColor(R.color.color1),
				res.getColor(R.color.color2), res.getColor(R.color.color3),
				res.getColor(R.color.color4), res.getColor(R.color.color5),
				res.getColor(R.color.color6), res.getColor(R.color.color7) });
		final ItemLabelPosition p = new ItemLabelPosition(
				ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER,
				45.0);
		renderer.setBasePositiveItemLabelPosition(p);
		plot.setRenderer(renderer);
		plot.setBackgroundPaint(dkGray);
		plot.setDomainGridlinePaint(lightGray);
		plot.setRangeGridlinePaint(lightGray);

		// change the margin at the top of the range axis...
		final ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setLowerMargin(0.15);
		rangeAxis.setUpperMargin(0.15);
		rangeAxis.setLabelAngle(90);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
		DecimalFormat decimalformat1 = new DecimalFormat("$##,###.00");
		barRenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
						"{2}", decimalformat1));
		barRenderer.setBaseItemLabelsVisible(true);
		barRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE11, TextAnchor.CENTER));
		barRenderer.setBaseNegativeItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE7, TextAnchor.CENTER));
		barRenderer.setItemLabelsVisible(true);
		
		chart.getCategoryPlot().setRenderer(renderer);

		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		return chart;

	}

	/*
	 * A custom renderer that returns a different color for each item in a
	 * single series.
	 */
	class CustomRenderer extends BarRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/** The colors. */
		private Paint[] paints;

		/**
		 * Creates a new renderer.
		 * 
		 * @param colors
		 *            the colors.
		 */
		public CustomRenderer(final int[] colors) {
			paints = new Paint[colors.length];
			int i = 0;
			for (int col : colors) {
				Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
				paint.setAlpha(200);
				paint.setColor(col);
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
				this.paints[i] = paint;
				i++;
			}
		}

		/**
		 * Returns the paint for an item. Overrides the default behaviour
		 * inherited from AbstractSeriesRenderer.
		 * 
		 * @param row
		 *            the series.
		 * @param column
		 *            the category.
		 * 
		 * @return The item color.
		 */
		public Paint getItemPaint(final int row, final int column) {
			return this.paints[column % this.paints.length];
		}
	}

}
