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
 * -----------------------------
 * GradientPaintTransformer.java
 * -----------------------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: GradientPaintTransformer.java,v 1.3 2005/10/18 13:18:34 mungady Exp $
 *
 * Changes
 * -------
 * 28-Oct-2003 : Version 1 (DG);
 * 
 */

package net.droidsolutions.droidcharts.common;

/**
 * The interface for a class that can transform a <code>GradientPaint</code> in
 * some way.
 * 
 * @author David Gilbert
 */
public interface GradientPaintTransformer {

	/**
	 * Transforms a <code>GradientPaint</code> instance to "fit" some target
	 * shape.
	 * 
	 * @param paint
	 *            the original paint.
	 * @param target
	 *            the reference area.
	 * 
	 * @return A transformed paint.
	 */
	// public GradientPaint transform(GradientPaint paint, Shape target);

}
