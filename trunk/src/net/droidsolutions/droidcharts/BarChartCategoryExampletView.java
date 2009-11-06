package net.droidsolutions.droidcharts;

import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.core.ChartFactory;
import net.droidsolutions.droidcharts.core.JFreeChart;
import net.droidsolutions.droidcharts.core.axis.CategoryAxis;
import net.droidsolutions.droidcharts.core.axis.CategoryLabelPositions;
import net.droidsolutions.droidcharts.core.axis.NumberAxis;
import net.droidsolutions.droidcharts.core.data.CategoryDataset;
import net.droidsolutions.droidcharts.core.data.DefaultCategoryDataset;
import net.droidsolutions.droidcharts.core.plot.CategoryPlot;
import net.droidsolutions.droidcharts.core.plot.PlotOrientation;
import net.droidsolutions.droidcharts.core.renderer.BarRenderer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;

public class BarChartCategoryExampletView extends View {

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
	public BarChartCategoryExampletView(Context context) {
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
	 * Returns a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private CategoryDataset createDataset() {

		// row keys...
		final String series1 = "First";
		final String series2 = "Second";
		final String series3 = "Third";

		// column keys...
		final String category1 = "Category 1";
		final String category2 = "Category 2";
		final String category3 = "Category 3";
		final String category4 = "Category 4";
		final String category5 = "Category 5";

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(1.0, series1, category1);
		dataset.addValue(4.0, series1, category2);
		dataset.addValue(3.0, series1, category3);
		dataset.addValue(5.0, series1, category4);
		dataset.addValue(5.0, series1, category5);

		dataset.addValue(5.0, series2, category1);
		dataset.addValue(7.0, series2, category2);
		dataset.addValue(6.0, series2, category3);
		dataset.addValue(8.0, series2, category4);
		dataset.addValue(4.0, series2, category5);

		dataset.addValue(4.0, series3, category1);
		dataset.addValue(3.0, series3, category2);
		dataset.addValue(2.0, series3, category3);
		dataset.addValue(3.0, series3, category4);
		dataset.addValue(6.0, series3, category5);

		return dataset;

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(final CategoryDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart("Bar Chart Demo", // chart
				// title
				"Category", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

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
		plot.setBackgroundPaint(dkGray);
		plot.setDomainGridlinePaint(lightGray);
		plot.setRangeGridlinePaint(lightGray);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setLabelAngle(90);

		// disable bar outlines...
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		Paint blue = new Paint(Paint.ANTI_ALIAS_FLAG);
		blue.setColor(Color.rgb(125, 138, 46));
		// blue.set
		blue.setAlpha(200);
		blue.setStrokeWidth(10);
		Paint green = new Paint(Paint.ANTI_ALIAS_FLAG);
		green.setColor(Color.rgb(255, 240, 165));
		green.setAlpha(200);

		Paint red = new Paint(Paint.ANTI_ALIAS_FLAG);
		red.setColor(Color.rgb(182, 73, 38));
		red.setAlpha(200);

		renderer.setSeriesPaint(0, blue);
		renderer.setSeriesPaint(1, green);
		renderer.setSeriesPaint(2, red);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		// domainAxis.setLabelAngle(90);

		 domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}
}