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
 * ----------------
 * TitleEntity.java
 * ----------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Peter Kolb;
 * Contributor(s):   ;
 *
 * Changes:
 * --------
 * 15-Feb-2009 : Version 1 (PK);
 *
 */

package net.droidsolutions.droidcharts.core.entity;

import net.droidsolutions.droidcharts.awt.Shape;
import net.droidsolutions.droidcharts.core.title.Title;

/**
 * A class that captures information about a Title of a chart.
 * 
 * @since 1.0.13
 */
public class TitleEntity extends ChartEntity {

	/** For serialization. */
	private static final long serialVersionUID = -4445994133561919083L;
	// same as for ChartEntity!

	/** The Title for the entity. */
	private Title title;

	/**
	 * Creates a new chart entity.
	 * 
	 * @param area
	 *            the area (<code>null</code> not permitted).
	 * @param title
	 *            the title (<code>null</code> not permitted).
	 */
	public TitleEntity(Shape area, Title title) {
		// defer argument checks...
		this(area, title, null);
	}

	/**
	 * Creates a new chart entity.
	 * 
	 * @param area
	 *            the area (<code>null</code> not permitted).
	 * @param title
	 *            the title (<code>null</code> not permitted).
	 * @param toolTipText
	 *            the tool tip text (<code>null</code> permitted).
	 */
	public TitleEntity(Shape area, Title title, String toolTipText) {
		// defer argument checks...
		this(area, title, toolTipText, null);
	}

	/**
	 * Creates a new entity.
	 * 
	 * @param area
	 *            the area (<code>null</code> not permitted).
	 * @param title
	 *            the title (<code>null</code> not permitted).
	 * @param toolTipText
	 *            the tool tip text (<code>null</code> permitted).
	 * @param urlText
	 *            the URL text for HTML image maps (<code>null</code>
	 *            permitted).
	 */
	public TitleEntity(Shape area, Title title, String toolTipText,
			String urlText) {
		super(area, toolTipText, urlText);
		if (title == null) {
			throw new IllegalArgumentException("Null 'title' argument.");
		}

		this.title = title;
	}

	/**
	 * Returns the title that occupies the entity area.
	 * 
	 * @return The title (never <code>null</code>).
	 */
	public Title getTitle() {
		return this.title;
	}

	/**
	 * Returns a string representation of the chart entity, useful for
	 * debugging.
	 * 
	 * @return A string.
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("TitleEntity: ");
		buf.append("tooltip = ");
		buf.append(getToolTipText());
		return buf.toString();
	}

}
