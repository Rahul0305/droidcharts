/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
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
 * --------------
 * PaintList.java
 * --------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PaintList.java,v 1.5 2005/10/18 13:24:19 mungady Exp $
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 27-Jun-2005 : Fixed equals() method to handle GradientPaint (DG);
 * 
 */

package net.droidsolutions.droidcharts.common;

import android.graphics.Paint;

/**
 * A table of {@link Paint} objects.
 * 
 * @author David Gilbert
 */
public class PaintList extends AbstractObjectList {

	/**
	 * Creates a new list.
	 */
	public PaintList() {
		super();
	}

	/**
	 * Returns a {@link Paint} object from the list.
	 * 
	 * @param index
	 *            the index (zero-based).
	 * 
	 * @return The object.
	 */
	public Paint getPaint(final int index) {
		return (Paint) get(index);
	}

	/**
	 * Sets the {@link Paint} for an item in the list. The list is expanded if
	 * necessary.
	 * 
	 * @param index
	 *            the index (zero-based).
	 * @param paint
	 *            the {@link Paint}.
	 */
	public void setPaint(final int index, final Paint paint) {
		set(index, paint);
	}

}
