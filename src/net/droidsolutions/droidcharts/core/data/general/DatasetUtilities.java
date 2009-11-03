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
 * DatasetUtilities.java
 * ---------------------
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Andrzej Porebski (bug fix);
 *                   Jonathan Nash (bug fix);
 *                   Richard Atkinson;
 *                   Andreas Schroeder;
 *                   Rafal Skalny (patch 1925366);
 *                   Jerome David (patch 2131001);
 *
 * Changes (from 18-Sep-2001)
 * --------------------------
 * 18-Sep-2001 : Added standard header and fixed DOS encoding problem (DG);
 * 22-Oct-2001 : Renamed DataSource.java --> Dataset.java etc. (DG);
 * 15-Nov-2001 : Moved to package com.jrefinery.data.* in the JCommon class
 *               library (DG);
 *               Changed to handle null values from datasets (DG);
 *               Bug fix (thanks to Andrzej Porebski) - initial value now set
 *               to positive or negative infinity when iterating (DG);
 * 22-Nov-2001 : Datasets with containing no data now return null for min and
 *               max calculations (DG);
 * 13-Dec-2001 : Extended to handle HighLowDataset and IntervalXYDataset (DG);
 * 15-Feb-2002 : Added getMinimumStackedRangeValue() and
 *               getMaximumStackedRangeValue() (DG);
 * 28-Feb-2002 : Renamed Datasets.java --> DatasetUtilities.java (DG);
 * 18-Mar-2002 : Fixed bug in min/max domain calculation for datasets that
 *               implement the CategoryDataset interface AND the XYDataset
 *               interface at the same time.  Thanks to Jonathan Nash for the
 *               fix (DG);
 * 23-Apr-2002 : Added getDomainExtent() and getRangeExtent() methods (DG);
 * 13-Jun-2002 : Modified range measurements to handle
 *               IntervalCategoryDataset (DG);
 * 12-Jul-2002 : Method name change in DomainInfo interface (DG);
 * 30-Jul-2002 : Added pie dataset summation method (DG);
 * 01-Oct-2002 : Added a method for constructing an XYDataset from a Function2D
 *               instance (DG);
 * 24-Oct-2002 : Amendments required following changes to the CategoryDataset
 *               interface (DG);
 * 18-Nov-2002 : Changed CategoryDataset to TableDataset (DG);
 * 04-Mar-2003 : Added isEmpty(XYDataset) method (DG);
 * 05-Mar-2003 : Added a method for creating a CategoryDataset from a
 *               KeyedValues instance (DG);
 * 15-May-2003 : Renamed isEmpty --> isEmptyOrNull (DG);
 * 25-Jun-2003 : Added limitPieDataset methods (RA);
 * 26-Jun-2003 : Modified getDomainExtent() method to accept null datasets (DG);
 * 27-Jul-2003 : Added getStackedRangeExtent(TableXYDataset data) (RA);
 * 18-Aug-2003 : getStackedRangeExtent(TableXYDataset data) now handles null
 *               values (RA);
 * 02-Sep-2003 : Added method to check for null or empty PieDataset (DG);
 * 18-Sep-2003 : Fix for bug 803660 (getMaximumRangeValue for
 *               CategoryDataset) (DG);
 * 20-Oct-2003 : Added getCumulativeRangeExtent() method (DG);
 * 09-Jan-2003 : Added argument checking code to the createCategoryDataset()
 *               method (DG);
 * 23-Mar-2004 : Fixed bug in getMaximumStackedRangeValue() method (DG);
 * 31-Mar-2004 : Exposed the extent iteration algorithms to use one of them and
 *               applied noninstantiation pattern (AS);
 * 11-May-2004 : Renamed getPieDatasetTotal --> calculatePieDatasetTotal (DG);
 * 15-Jul-2004 : Switched getX() with getXValue() and getY() with getYValue();
 * 24-Aug-2004 : Added argument checks to createCategoryDataset() method (DG);
 * 04-Oct-2004 : Renamed ArrayUtils --> ArrayUtilities (DG);
 * 06-Oct-2004 : Renamed findDomainExtent() --> findDomainBounds(),
 *               findRangeExtent() --> findRangeBounds() (DG);
 * 07-Jan-2005 : Renamed findStackedRangeExtent() --> findStackedRangeBounds(),
 *               findCumulativeRangeExtent() --> findCumulativeRangeBounds(),
 *               iterateXYRangeExtent() --> iterateXYRangeBounds(),
 *               removed deprecated methods (DG);
 * 03-Feb-2005 : The findStackedRangeBounds() methods now return null for
 *               empty datasets (DG);
 * 03-Mar-2005 : Moved createNumberArray() and createNumberArray2D() methods
 *               from DatasetUtilities --> DataUtilities (DG);
 * 22-Sep-2005 : Added new findStackedRangeBounds() method that takes base
 *               argument (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 15-Mar-2007 : Added calculateStackTotal() method (DG);
 * 27-Mar-2008 : Fixed bug in findCumulativeRangeBounds() method (DG);
 * 28-Mar-2008 : Fixed sample count in sampleFunction2D() method, renamed
 *               iterateXYRangeBounds() --> iterateRangeBounds(XYDataset), and
 *               fixed a bug in findRangeBounds(XYDataset, false) (DG);
 * 28-Mar-2008 : Applied a variation of patch 1925366 (from Rafal Skalny) for
 *               slightly more efficient iterateRangeBounds() methods (DG);
 * 08-Apr-2008 : Fixed typo in iterateRangeBounds() (DG);
 * 08-Oct-2008 : Applied patch 2131001 by Jerome David, with some modifications
 *               and additions and some new unit tests (DG);
 * 12-Feb-2009 : Added sampleFunction2DToSeries() method (DG);
 * 27-Mar-2009 : Added new methods to find domain and range bounds taking into
 *               account hidden series (DG);
 * 01-Apr-2009 : Handle a StatisticalCategoryDataset in
 *               iterateToFindRangeBounds() (DG);
 *
 */

package net.droidsolutions.droidcharts.core.data.general;

import java.util.Iterator;
import java.util.List;

import net.droidsolutions.droidcharts.core.data.BoxAndWhiskerCategoryDataset;
import net.droidsolutions.droidcharts.core.data.CategoryDataset;
import net.droidsolutions.droidcharts.core.data.CategoryRangeInfo;
import net.droidsolutions.droidcharts.core.data.IntervalCategoryDataset;
import net.droidsolutions.droidcharts.core.data.IntervalXYDataset;
import net.droidsolutions.droidcharts.core.data.OHLCDataset;
import net.droidsolutions.droidcharts.core.data.Range;
import net.droidsolutions.droidcharts.core.data.RangeInfo;
import net.droidsolutions.droidcharts.core.data.StatisticalCategoryDataset;
import net.droidsolutions.droidcharts.core.data.XYDataset;

/**
 * A collection of useful static methods relating to datasets.
 */
public final class DatasetUtilities {

	/**
	 * Private constructor for non-instanceability.
	 */
	private DatasetUtilities() {
		// now try to instantiate this ;-)
	}

	/**
	 * Returns <code>true</code> if the dataset is empty (or <code>null</code>),
	 * and <code>false</code> otherwise.
	 * 
	 * @param dataset
	 *            the dataset (<code>null</code> permitted).
	 * 
	 * @return A boolean.
	 */
	public static boolean isEmptyOrNull(CategoryDataset dataset) {

		if (dataset == null) {
			return true;
		}

		int rowCount = dataset.getRowCount();
		int columnCount = dataset.getColumnCount();
		if (rowCount == 0 || columnCount == 0) {
			return true;
		}

		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				if (dataset.getValue(r, c) != null) {
					return false;
				}

			}
		}

		return true;

	}

	/*
	 * Finds the bounds of the y-values in the specified dataset, including only
	 * those series that are listed in visibleSeriesKeys.
	 * 
	 * @param dataset the dataset (<code>null</code> not permitted).
	 * 
	 * @param visibleSeriesKeys the keys for the visible series
	 * (<code>null</code> not permitted).
	 * 
	 * @param includeInterval include the y-interval (if the dataset has a
	 * y-interval).
	 * 
	 * @return The data bounds.
	 * 
	 * @since 1.0.13
	 */
	public static Range findRangeBounds(CategoryDataset dataset,
			List visibleSeriesKeys, boolean includeInterval) {
		if (dataset == null) {
			throw new IllegalArgumentException("Null 'dataset' argument.");
		}
		Range result = null;
		if (dataset instanceof CategoryRangeInfo) {
			CategoryRangeInfo info = (CategoryRangeInfo) dataset;
			result = info.getRangeBounds(visibleSeriesKeys, includeInterval);
		} else {
			result = iterateToFindRangeBounds(dataset, visibleSeriesKeys,
					includeInterval);
		}
		return result;
	}

	/**
	 * Iterates over the data item of the category dataset to find the range
	 * bounds.
	 * 
	 * @param dataset
	 *            the dataset (<code>null</code> not permitted).
	 * @param includeInterval
	 *            a flag that determines whether or not the y-interval is taken
	 *            into account.
	 * @param visibleSeriesKeys
	 *            the visible series keys.
	 * 
	 * @return The range (possibly <code>null</code>).
	 * 
	 * @since 1.0.13
	 */
	public static Range iterateToFindRangeBounds(CategoryDataset dataset,
			List visibleSeriesKeys, boolean includeInterval) {

		if (dataset == null) {
			throw new IllegalArgumentException("Null 'dataset' argument.");
		}
		if (visibleSeriesKeys == null) {
			throw new IllegalArgumentException(
					"Null 'visibleSeriesKeys' argument.");
		}

		double minimum = Double.POSITIVE_INFINITY;
		double maximum = Double.NEGATIVE_INFINITY;
		int columnCount = dataset.getColumnCount();
		if (includeInterval && dataset instanceof BoxAndWhiskerCategoryDataset) {
			// handle special case of BoxAndWhiskerDataset
			BoxAndWhiskerCategoryDataset bx = (BoxAndWhiskerCategoryDataset) dataset;
			Iterator iterator = visibleSeriesKeys.iterator();
			while (iterator.hasNext()) {
				Comparable seriesKey = (Comparable) iterator.next();
				int series = dataset.getRowIndex(seriesKey);
				int itemCount = dataset.getColumnCount();
				for (int item = 0; item < itemCount; item++) {
					Number lvalue = bx.getMinRegularValue(series, item);
					if (lvalue == null) {
						lvalue = bx.getValue(series, item);
					}
					Number uvalue = bx.getMaxRegularValue(series, item);
					if (uvalue == null) {
						uvalue = bx.getValue(series, item);
					}
					if (lvalue != null) {
						minimum = Math.min(minimum, lvalue.doubleValue());
					}
					if (uvalue != null) {
						maximum = Math.max(maximum, uvalue.doubleValue());
					}
				}
			}
		} else if (includeInterval
				&& dataset instanceof IntervalCategoryDataset) {
			// handle the special case where the dataset has y-intervals that
			// we want to measure
			IntervalCategoryDataset icd = (IntervalCategoryDataset) dataset;
			Number lvalue, uvalue;
			Iterator iterator = visibleSeriesKeys.iterator();
			while (iterator.hasNext()) {
				Comparable seriesKey = (Comparable) iterator.next();
				int series = dataset.getRowIndex(seriesKey);
				for (int column = 0; column < columnCount; column++) {
					lvalue = icd.getStartValue(series, column);
					uvalue = icd.getEndValue(series, column);
					if (lvalue != null && !Double.isNaN(lvalue.doubleValue())) {
						minimum = Math.min(minimum, lvalue.doubleValue());
					}
					if (uvalue != null && !Double.isNaN(uvalue.doubleValue())) {
						maximum = Math.max(maximum, uvalue.doubleValue());
					}
				}
			}
		} else if (includeInterval
				&& dataset instanceof StatisticalCategoryDataset) {
			// handle the special case where the dataset has y-intervals that
			// we want to measure
			StatisticalCategoryDataset scd = (StatisticalCategoryDataset) dataset;
			Iterator iterator = visibleSeriesKeys.iterator();
			while (iterator.hasNext()) {
				Comparable seriesKey = (Comparable) iterator.next();
				int series = dataset.getRowIndex(seriesKey);
				for (int column = 0; column < columnCount; column++) {
					Number meanN = scd.getMeanValue(series, column);
					if (meanN != null) {
						double std = 0.0;
						Number stdN = scd.getStdDevValue(series, column);
						if (stdN != null) {
							std = stdN.doubleValue();
							if (Double.isNaN(std)) {
								std = 0.0;
							}
						}
						double mean = meanN.doubleValue();
						if (!Double.isNaN(mean)) {
							minimum = Math.min(minimum, mean - std);
							maximum = Math.max(maximum, mean + std);
						}
					}
				}
			}
		} else {
			// handle the standard case (plain CategoryDataset)
			Iterator iterator = visibleSeriesKeys.iterator();
			while (iterator.hasNext()) {
				Comparable seriesKey = (Comparable) iterator.next();
				int series = dataset.getRowIndex(seriesKey);
				for (int column = 0; column < columnCount; column++) {
					Number value = dataset.getValue(series, column);
					if (value != null) {
						double v = value.doubleValue();
						if (!Double.isNaN(v)) {
							minimum = Math.min(minimum, v);
							maximum = Math.max(maximum, v);
						}
					}
				}
			}
		}
		if (minimum == Double.POSITIVE_INFINITY) {
			return null;
		} else {
			return new Range(minimum, maximum);
		}
	}

	public static Range findRangeBounds(XYDataset dataset,
			boolean includeInterval) {
		if (dataset == null) {
			throw new IllegalArgumentException("Null 'dataset' argument.");
		}
		Range result = null;
		if (dataset instanceof RangeInfo) {
			RangeInfo info = (RangeInfo) dataset;
			result = info.getRangeBounds(includeInterval);
		} else {
			result = iterateRangeBounds(dataset, includeInterval);
		}
		return result;
	}

	/**
	 * Returns the range of values in the range for the dataset.
	 * 
	 * @param dataset
	 *            the dataset (<code>null</code> not permitted).
	 * @param includeInterval
	 *            a flag that determines whether or not the y-interval is taken
	 *            into account.
	 * 
	 * @return The range (possibly <code>null</code>).
	 */
	public static Range findRangeBounds(CategoryDataset dataset,
			boolean includeInterval) {
		if (dataset == null) {
			throw new IllegalArgumentException("Null 'dataset' argument.");
		}
		Range result = null;
		if (dataset instanceof RangeInfo) {
			RangeInfo info = (RangeInfo) dataset;
			result = info.getRangeBounds(includeInterval);
		} else {
			result = iterateRangeBounds(dataset, includeInterval);
		}
		return result;
	}

	/**
	 * Returns the range of values in the range for the dataset.
	 * 
	 * @param dataset
	 *            the dataset (<code>null</code> not permitted).
	 * 
	 * @return The range (possibly <code>null</code>).
	 */
	public static Range findRangeBounds(CategoryDataset dataset) {
		return findRangeBounds(dataset, true);
	}

	/**
	 * Iterates over the data item of the category dataset to find the range
	 * bounds.
	 * 
	 * @param dataset
	 *            the dataset (<code>null</code> not permitted).
	 * @param includeInterval
	 *            a flag that determines whether or not the y-interval is taken
	 *            into account.
	 * 
	 * @return The range (possibly <code>null</code>).
	 * 
	 * @since 1.0.10
	 */
	public static Range iterateRangeBounds(CategoryDataset dataset,
			boolean includeInterval) {
		double minimum = Double.POSITIVE_INFINITY;
		double maximum = Double.NEGATIVE_INFINITY;
		int rowCount = dataset.getRowCount();
		int columnCount = dataset.getColumnCount();
		if (includeInterval && dataset instanceof IntervalCategoryDataset) {
			// handle the special case where the dataset has y-intervals that
			// we want to measure
			IntervalCategoryDataset icd = (IntervalCategoryDataset) dataset;
			Number lvalue, uvalue;
			for (int row = 0; row < rowCount; row++) {
				for (int column = 0; column < columnCount; column++) {
					lvalue = icd.getStartValue(row, column);
					uvalue = icd.getEndValue(row, column);
					if (lvalue != null && !Double.isNaN(lvalue.doubleValue())) {
						minimum = Math.min(minimum, lvalue.doubleValue());
					}
					if (uvalue != null && !Double.isNaN(uvalue.doubleValue())) {
						maximum = Math.max(maximum, uvalue.doubleValue());
					}
				}
			}
		} else {
			// handle the standard case (plain CategoryDataset)
			for (int row = 0; row < rowCount; row++) {
				for (int column = 0; column < columnCount; column++) {
					Number value = dataset.getValue(row, column);
					if (value != null) {
						double v = value.doubleValue();
						if (!Double.isNaN(v)) {
							minimum = Math.min(minimum, v);
							maximum = Math.max(maximum, v);
						}
					}
				}
			}
		}
		if (minimum == Double.POSITIVE_INFINITY) {
			return null;
		} else {
			return new Range(minimum, maximum);
		}
	}

	/**
	 * Iterates over the data items of the xy dataset to find the range bounds.
	 * 
	 * @param dataset
	 *            the dataset (<code>null</code> not permitted).
	 * @param includeInterval
	 *            a flag that determines, for an {@link IntervalXYDataset},
	 *            whether the y-interval or just the y-value is used to
	 *            determine the overall range.
	 * 
	 * @return The range (possibly <code>null</code>).
	 * 
	 * @since 1.0.10
	 */
	public static Range iterateRangeBounds(XYDataset dataset,
			boolean includeInterval) {
		double minimum = Double.POSITIVE_INFINITY;
		double maximum = Double.NEGATIVE_INFINITY;
		int seriesCount = dataset.getSeriesCount();

		// handle three cases by dataset type
		if (includeInterval && dataset instanceof IntervalXYDataset) {
			// handle special case of IntervalXYDataset
			IntervalXYDataset ixyd = (IntervalXYDataset) dataset;
			for (int series = 0; series < seriesCount; series++) {
				int itemCount = dataset.getItemCount(series);
				for (int item = 0; item < itemCount; item++) {
					double lvalue = ixyd.getStartYValue(series, item);
					double uvalue = ixyd.getEndYValue(series, item);
					if (!Double.isNaN(lvalue)) {
						minimum = Math.min(minimum, lvalue);
					}
					if (!Double.isNaN(uvalue)) {
						maximum = Math.max(maximum, uvalue);
					}
				}
			}
		} else if (includeInterval && dataset instanceof OHLCDataset) {
			// handle special case of OHLCDataset
			OHLCDataset ohlc = (OHLCDataset) dataset;
			for (int series = 0; series < seriesCount; series++) {
				int itemCount = dataset.getItemCount(series);
				for (int item = 0; item < itemCount; item++) {
					double lvalue = ohlc.getLowValue(series, item);
					double uvalue = ohlc.getHighValue(series, item);
					if (!Double.isNaN(lvalue)) {
						minimum = Math.min(minimum, lvalue);
					}
					if (!Double.isNaN(uvalue)) {
						maximum = Math.max(maximum, uvalue);
					}
				}
			}
		} else {
			// standard case - plain XYDataset
			for (int series = 0; series < seriesCount; series++) {
				int itemCount = dataset.getItemCount(series);
				for (int item = 0; item < itemCount; item++) {
					double value = dataset.getYValue(series, item);
					if (!Double.isNaN(value)) {
						minimum = Math.min(minimum, value);
						maximum = Math.max(maximum, value);
					}
				}
			}
		}
		if (minimum == Double.POSITIVE_INFINITY) {
			return null;
		} else {
			return new Range(minimum, maximum);
		}
	}

}
