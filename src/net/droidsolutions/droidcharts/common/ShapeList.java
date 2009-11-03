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
 * ShapeList.java
 * --------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: ShapeList.java,v 1.4 2005/10/18 13:24:19 mungady Exp $
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 
 */

package net.droidsolutions.droidcharts.common;

import net.droidsolutions.droidcharts.awt.Shape;

/**
 * A table of {@link Shape} objects.
 * 
 * @author David Gilbert
 */
public class ShapeList extends AbstractObjectList {

	/**
	 * Creates a new list.
	 */
	public ShapeList() {
		super();
	}

	/**
	 * Returns a {@link Shape} object from the list.
	 * 
	 * @param index
	 *            the index (zero-based).
	 * 
	 * @return The object.
	 */
	public Shape getShape(final int index) {
		return (Shape) get(index);
	}

	/**
	 * Sets the {@link Shape} for an item in the list. The list is expanded if
	 * necessary.
	 * 
	 * @param index
	 *            the index (zero-based).
	 * @param shape
	 *            the {@link Shape}.
	 */
	public void setShape(final int index, final Shape shape) {
		set(index, shape);
	}

	/**
	 * Returns an independent copy of the list.
	 * 
	 * @return A clone.
	 * 
	 * @throws CloneNotSupportedException
	 *             if an item in the list does not support cloning.
	 */
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Tests the list for equality with another object (typically also a list).
	 * 
	 * @param o
	 *            the other object.
	 * 
	 * @return A boolean.
	 */
	public boolean equals(final Object o) {

		if (o == null) {
			return false;
		}

		if (o == this) {
			return true;
		}

		if (o instanceof ShapeList) {
			return super.equals(o);
		}

		return false;

	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return the hashcode
	 */
	public int hashCode() {
		return super.hashCode();
	}

}
