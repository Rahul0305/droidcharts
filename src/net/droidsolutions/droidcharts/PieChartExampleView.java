package net.droidsolutions.droidcharts;

import java.util.List;

import net.droidsolutions.droidcharts.awt.Font;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.core.ChartFactory;
import net.droidsolutions.droidcharts.core.JFreeChart;
import net.droidsolutions.droidcharts.core.data.CategoryDataset;
import net.droidsolutions.droidcharts.core.data.DefaultPieDataset;
import net.droidsolutions.droidcharts.core.data.PieDataset;
import net.droidsolutions.droidcharts.core.plot.PiePlot;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;

public class PieChartExampleView extends View {
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
	public PieChartExampleView(Context context) {
		super(context);
		mHandler = new Handler();
	}

	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		canvas.getClipBounds(mRect);

		final PieDataset dataset = createDataset();
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
	 * @return A sample dataset.
	 */
	private static PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("One", new Double(1));
		dataset.setValue("Two", new Double(2));
		dataset.setValue("Three", new Double(3));
		dataset.setValue("Four", new Double(4));
		dataset.setValue("Five", new Double(5));
		dataset.setValue("Six", new Double(6));
		return dataset;
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return a chart.
	 */
	private JFreeChart createChart(final PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("Pie Chart Demo 1", // chart
				// title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();

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
		// chart.setBorderPaint(borderPaint);
		chart.setBackgroundPaint(dkGray);

		plot.setLabelFont(new Font("SansSerif", Typeface.BOLD, 12));
		plot.setNoDataMessage("No data available");
		plot.setCircular(false);
		plot.setLabelGap(0.02);
		plot.setLabelBackgroundPaint(lightGray);
		plot.setBackgroundPaint(dkGray);
		
		Resources res = getResources();
		int[] colors = new int[] {
				res.getColor(R.color.color0),
				res.getColor(R.color.color1),
				res.getColor(R.color.color2),
				res.getColor(R.color.color3),
				res.getColor(R.color.color4),
				res.getColor(R.color.color5),
				res.getColor(R.color.color6),
				res.getColor(R.color.color7)};
		
		int[] outlineColors = new int[] {
				res.getColor(R.color.white),
				res.getColor(R.color.white),
				res.getColor(R.color.white),
				res.getColor(R.color.white),
				res.getColor(R.color.white),
				res.getColor(R.color.white),
				res.getColor(R.color.white),
				res.getColor(R.color.white)};
		PieRenderer renderer = new PieRenderer(colors,outlineColors,3f);
		renderer.setColor(plot, dataset);
		return chart;
	}

	/*
	 * A simple renderer for setting custom colors for a pie chart.
	 */

	public static class PieRenderer {
		private int[] fillColor;
		private int[] outlineColor;
		private float outlineStroke;

		public PieRenderer(int[] color,int[] outlineColor, float outlineStroke) {
			this.fillColor = color;
			this.outlineColor = outlineColor;
			this.outlineStroke = outlineStroke;
		}

		public void setColor(PiePlot plot, PieDataset dataset) {
			List<Comparable> keys = dataset.getKeys();
			int aInt;

			for (int i = 0; i < keys.size(); i++) {
				aInt = i % this.fillColor.length;

				Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
				fillPaint.setColor(fillColor[aInt]);
				plot.setSectionPaint(keys.get(i), fillPaint);
				
				
				Paint outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
				outlinePaint.setColor(outlineColor[aInt]);
				plot.setSectionOutlinePaint(keys.get(i), outlinePaint);
				
				plot.setSectionOutlineStroke(keys.get(i), outlineStroke);
			}
		}
	}
}
