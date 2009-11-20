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
 * ------------------
 * LegendGraphic.java
 * ------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Oct-2004 : Version 1 (DG);
 * 21-Jan-2005 : Modified return type of RectangleAnchor.coordinates()
 *               method (DG);
 * 20-Apr-2005 : Added new draw() method (DG);
 * 13-May-2005 : Fixed to respect margin, border and padding settings (DG);
 * 01-Sep-2005 : Implemented PublicCloneable (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 13-Dec-2006 : Added fillPaintTransformer attribute, so legend graphics can
 *               display gradient paint correctly, updated equals() and
 *               corrected clone() (DG);
 * 01-Aug-2007 : Updated API docs (DG);
 *
 */

package net.droidsolutions.droidcharts.core.title;

import net.droidsolutions.droidcharts.awt.Ellipse2D;
import net.droidsolutions.droidcharts.awt.Line2D;
import net.droidsolutions.droidcharts.awt.Path2D;
import net.droidsolutions.droidcharts.awt.PathIterator;
import net.droidsolutions.droidcharts.awt.Point2D;
import net.droidsolutions.droidcharts.awt.Polygon;
import net.droidsolutions.droidcharts.awt.Rectangle;
import net.droidsolutions.droidcharts.awt.Rectangle2D;
import net.droidsolutions.droidcharts.awt.Shape;
import net.droidsolutions.droidcharts.common.GradientPaintTransformer;
import net.droidsolutions.droidcharts.common.RectangleAnchor;
import net.droidsolutions.droidcharts.common.ShapeUtilities;
import net.droidsolutions.droidcharts.common.Size2D;
import net.droidsolutions.droidcharts.common.StandardGradientPaintTransformer;
import net.droidsolutions.droidcharts.core.block.AbstractBlock;
import net.droidsolutions.droidcharts.core.block.Block;
import net.droidsolutions.droidcharts.core.block.LengthConstraintType;
import net.droidsolutions.droidcharts.core.block.RectangleConstraint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * The graphical item within a legend item.
 */
public class LegendGraphic extends AbstractBlock implements Block {

	/** For serialization. */
	static final long serialVersionUID = -1338791523854985009L;

	/**
	 * A flag that controls whether or not the shape is visible - see also
	 * lineVisible.
	 */
	private boolean shapeVisible;

	/**
	 * The shape to display. To allow for accurate positioning, the center of
	 * the shape should be at (0, 0).
	 */
	private transient Shape shape;

	/**
	 * Defines the location within the block to which the shape will be aligned.
	 */
	private RectangleAnchor shapeLocation;

	/**
	 * Defines the point on the shape's bounding rectangle that will be aligned
	 * to the drawing location when the shape is rendered.
	 */
	private RectangleAnchor shapeAnchor;

	/** A flag that controls whether or not the shape is filled. */
	private boolean shapeFilled;

	/** The fill paint for the shape. */
	private transient Paint fillPaint;

	/**
	 * The fill paint transformer (used if the fillPaint is an instance of
	 * GradientPaint).
	 * 
	 * @since 1.0.4
	 */
	private GradientPaintTransformer fillPaintTransformer;

	/** A flag that controls whether or not the shape outline is visible. */
	private boolean shapeOutlineVisible;

	/** The outline paint for the shape. */
	private transient Paint outlinePaint;

	/** The outline stroke for the shape. */
	private transient float outlineStroke;

	/**
	 * A flag that controls whether or not the line is visible - see also
	 * shapeVisible.
	 */
	private boolean lineVisible;

	/** The line. */
	private transient Shape line;

	/** The line stroke. */
	private transient float lineStroke;

	/** The line paint. */
	private transient Paint linePaint;

	/**
	 * Creates a new legend graphic.
	 * 
	 * @param shape
	 *            the shape (<code>null</code> not permitted).
	 * @param fillPaint
	 *            the fill paint (<code>null</code> not permitted).
	 */
	public LegendGraphic(Shape shape, Paint fillPaint) {
		if (shape == null) {
			throw new IllegalArgumentException("Null 'shape' argument.");
		}
		if (fillPaint == null) {
			throw new IllegalArgumentException("Null 'fillPaint' argument.");
		}
		this.shapeVisible = true;
		this.shape = shape;
		this.shapeAnchor = RectangleAnchor.CENTER;
		this.shapeLocation = RectangleAnchor.CENTER;
		this.shapeFilled = true;
		this.fillPaint = fillPaint;
		this.fillPaintTransformer = new StandardGradientPaintTransformer();
		setPadding(2.0, 2.0, 2.0, 2.0);
	}

	/**
	 * Returns a flag that controls whether or not the shape is visible.
	 * 
	 * @return A boolean.
	 * 
	 * @see #setShapeVisible(boolean)
	 */
	public boolean isShapeVisible() {
		return this.shapeVisible;
	}

	/**
	 * Sets a flag that controls whether or not the shape is visible.
	 * 
	 * @param visible
	 *            the flag.
	 * 
	 * @see #isShapeVisible()
	 */
	public void setShapeVisible(boolean visible) {
		this.shapeVisible = visible;
	}

	/**
	 * Returns the shape.
	 * 
	 * @return The shape.
	 * 
	 * @see #setShape(Shape)
	 */
	public Shape getShape() {
		return this.shape;
	}

	/**
	 * Sets the shape.
	 * 
	 * @param shape
	 *            the shape.
	 * 
	 * @see #getShape()
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/**
	 * Returns a flag that controls whether or not the shapes are filled.
	 * 
	 * @return A boolean.
	 * 
	 * @see #setShapeFilled(boolean)
	 */
	public boolean isShapeFilled() {
		return this.shapeFilled;
	}

	/**
	 * Sets a flag that controls whether or not the shape is filled.
	 * 
	 * @param filled
	 *            the flag.
	 * 
	 * @see #isShapeFilled()
	 */
	public void setShapeFilled(boolean filled) {
		this.shapeFilled = filled;
	}

	/**
	 * Returns the paint used to fill the shape.
	 * 
	 * @return The fill paint.
	 * 
	 * @see #setFillPaint(Paint)
	 */
	public Paint getFillPaint() {
		return this.fillPaint;
	}

	/**
	 * Sets the paint used to fill the shape.
	 * 
	 * @param paint
	 *            the paint.
	 * 
	 * @see #getFillPaint()
	 */
	public void setFillPaint(Paint paint) {
		this.fillPaint = paint;
	}

	/**
	 * Returns the transformer used when the fill paint is an instance of
	 * <code>GradientPaint</code>.
	 * 
	 * @return The transformer (never <code>null</code>).
	 * 
	 * @since 1.0.4.
	 * 
	 * @see #setFillPaintTransformer(GradientPaintTransformer)
	 */
	public GradientPaintTransformer getFillPaintTransformer() {
		return this.fillPaintTransformer;
	}

	/**
	 * Sets the transformer used when the fill paint is an instance of
	 * <code>GradientPaint</code>.
	 * 
	 * @param transformer
	 *            the transformer (<code>null</code> not permitted).
	 * 
	 * @since 1.0.4
	 * 
	 * @see #getFillPaintTransformer()
	 */
	public void setFillPaintTransformer(GradientPaintTransformer transformer) {
		if (transformer == null) {
			throw new IllegalArgumentException("Null 'transformer' argument.");
		}
		this.fillPaintTransformer = transformer;
	}

	/**
	 * Returns a flag that controls whether the shape outline is visible.
	 * 
	 * @return A boolean.
	 * 
	 * @see #setShapeOutlineVisible(boolean)
	 */
	public boolean isShapeOutlineVisible() {
		return this.shapeOutlineVisible;
	}

	/**
	 * Sets a flag that controls whether or not the shape outline is visible.
	 * 
	 * @param visible
	 *            the flag.
	 * 
	 * @see #isShapeOutlineVisible()
	 */
	public void setShapeOutlineVisible(boolean visible) {
		this.shapeOutlineVisible = visible;
	}

	/**
	 * Returns the outline paint.
	 * 
	 * @return The paint.
	 * 
	 * @see #setOutlinePaint(Paint)
	 */
	public Paint getOutlinePaint() {
		return this.outlinePaint;
	}

	/**
	 * Sets the outline paint.
	 * 
	 * @param paint
	 *            the paint.
	 * 
	 * @see #getOutlinePaint()
	 */
	public void setOutlinePaint(Paint paint) {
		this.outlinePaint = paint;
	}

	/**
	 * Returns the outline stroke.
	 * 
	 * @return The stroke.
	 * 
	 * @see #setOutlineStroke(Stroke)
	 */
	public float getOutlineStroke() {
		return this.outlineStroke;
	}

	/**
	 * Sets the outline stroke.
	 * 
	 * @param stroke
	 *            the stroke.
	 * 
	 * @see #getOutlineStroke()
	 */
	public void setOutlineStroke(float stroke) {
		this.outlineStroke = stroke;
	}

	/**
	 * Returns the shape anchor.
	 * 
	 * @return The shape anchor.
	 * 
	 * @see #getShapeAnchor()
	 */
	public RectangleAnchor getShapeAnchor() {
		return this.shapeAnchor;
	}

	/**
	 * Sets the shape anchor. This defines a point on the shapes bounding
	 * rectangle that will be used to align the shape to a location.
	 * 
	 * @param anchor
	 *            the anchor (<code>null</code> not permitted).
	 * 
	 * @see #setShapeAnchor(RectangleAnchor)
	 */
	public void setShapeAnchor(RectangleAnchor anchor) {
		if (anchor == null) {
			throw new IllegalArgumentException("Null 'anchor' argument.");
		}
		this.shapeAnchor = anchor;
	}

	/**
	 * Returns the shape location.
	 * 
	 * @return The shape location.
	 * 
	 * @see #setShapeLocation(RectangleAnchor)
	 */
	public RectangleAnchor getShapeLocation() {
		return this.shapeLocation;
	}

	/**
	 * Sets the shape location. This defines a point within the drawing area
	 * that will be used to align the shape to.
	 * 
	 * @param location
	 *            the location (<code>null</code> not permitted).
	 * 
	 * @see #getShapeLocation()
	 */
	public void setShapeLocation(RectangleAnchor location) {
		if (location == null) {
			throw new IllegalArgumentException("Null 'location' argument.");
		}
		this.shapeLocation = location;
	}

	/**
	 * Returns the flag that controls whether or not the line is visible.
	 * 
	 * @return A boolean.
	 * 
	 * @see #setLineVisible(boolean)
	 */
	public boolean isLineVisible() {
		return this.lineVisible;
	}

	/**
	 * Sets the flag that controls whether or not the line is visible.
	 * 
	 * @param visible
	 *            the flag.
	 * 
	 * @see #isLineVisible()
	 */
	public void setLineVisible(boolean visible) {
		this.lineVisible = visible;
	}

	/**
	 * Returns the line centered about (0, 0).
	 * 
	 * @return The line.
	 * 
	 * @see #setLine(Shape)
	 */
	public Shape getLine() {
		return this.line;
	}

	/**
	 * Sets the line. A Shape is used here, because then you can use Line2D,
	 * GeneralPath or any other Shape to represent the line.
	 * 
	 * @param line
	 *            the line.
	 * 
	 * @see #getLine()
	 */
	public void setLine(Shape line) {
		this.line = line;
	}

	/**
	 * Returns the line paint.
	 * 
	 * @return The paint.
	 * 
	 * @see #setLinePaint(Paint)
	 */
	public Paint getLinePaint() {
		return this.linePaint;
	}

	/**
	 * Sets the line paint.
	 * 
	 * @param paint
	 *            the paint.
	 * 
	 * @see #getLinePaint()
	 */
	public void setLinePaint(Paint paint) {
		this.linePaint = paint;
	}

	/**
	 * Returns the line stroke.
	 * 
	 * @return The stroke.
	 * 
	 * @see #setLineStroke(Stroke)
	 */
	public float getLineStroke() {
		return this.lineStroke;
	}

	/**
	 * Sets the line stroke.
	 * 
	 * @param stroke
	 *            the stroke.
	 * 
	 * @see #getLineStroke()
	 */
	public void setLineStroke(float stroke) {
		this.lineStroke = stroke;
	}

	/**
	 * Arranges the contents of the block, within the given constraints, and
	 * returns the block size.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param constraint
	 *            the constraint (<code>null</code> not permitted).
	 * 
	 * @return The block size (in Java2D units, never <code>null</code>).
	 */
	public Size2D arrange(Canvas g2, RectangleConstraint constraint) {
		RectangleConstraint contentConstraint = toContentConstraint(constraint);
		LengthConstraintType w = contentConstraint.getWidthConstraintType();
		LengthConstraintType h = contentConstraint.getHeightConstraintType();
		Size2D contentSize = null;
		if (w == LengthConstraintType.NONE) {
			if (h == LengthConstraintType.NONE) {
				contentSize = arrangeNN(g2);
			} else if (h == LengthConstraintType.RANGE) {
				throw new RuntimeException("Not yet implemented.");
			} else if (h == LengthConstraintType.FIXED) {
				throw new RuntimeException("Not yet implemented.");
			}
		} else if (w == LengthConstraintType.RANGE) {
			if (h == LengthConstraintType.NONE) {
				throw new RuntimeException("Not yet implemented.");
			} else if (h == LengthConstraintType.RANGE) {
				throw new RuntimeException("Not yet implemented.");
			} else if (h == LengthConstraintType.FIXED) {
				throw new RuntimeException("Not yet implemented.");
			}
		} else if (w == LengthConstraintType.FIXED) {
			if (h == LengthConstraintType.NONE) {
				throw new RuntimeException("Not yet implemented.");
			} else if (h == LengthConstraintType.RANGE) {
				throw new RuntimeException("Not yet implemented.");
			} else if (h == LengthConstraintType.FIXED) {
				contentSize = new Size2D(contentConstraint.getWidth(),
						contentConstraint.getHeight());
			}
		}
		return new Size2D(calculateTotalWidth(contentSize.getWidth()),
				calculateTotalHeight(contentSize.getHeight()));
	}

	/**
	 * Performs the layout with no constraint, so the content size is determined
	 * by the bounds of the shape and/or line drawn to represent the series.
	 * 
	 * @param g2
	 *            the graphics device.
	 * 
	 * @return The content size.
	 */
	protected Size2D arrangeNN(Canvas g2) {
		Rectangle2D contentSize = new Rectangle2D.Double();
		if (this.line != null) {
			contentSize.setRect(this.line.getBounds2D());
		}
		if (this.shape != null) {
			contentSize = contentSize.createUnion(this.shape.getBounds2D());
		}
		return new Size2D(contentSize.getWidth(), contentSize.getHeight());
	}

	/**
	 * Draws the graphic item within the specified area.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area.
	 */
	public void draw(Canvas g2, Rectangle2D area) {

		area = trimMargin(area);
		drawBorder(g2, area);
		area = trimBorder(area);
		area = trimPadding(area);

		if (this.lineVisible) {
			Point2D location = RectangleAnchor.coordinates(area,
					this.shapeLocation);
			Shape aLine = ShapeUtilities.createTranslatedShape(getLine(),
					this.shapeAnchor, location.getX(), location.getY());

			linePaint.setStrokeWidth(lineStroke);
			if (aLine instanceof Line2D) {
				Line2D line = (Line2D) aLine;
				g2.drawLine((float) line.getX1(), (float) line.getY1(),
						(float) line.getX2(), (float) line.getY2(), linePaint);
			}
		}

		if (this.shapeVisible) {
			Point2D location = RectangleAnchor.coordinates(area,
					this.shapeLocation);

			Shape s = ShapeUtilities.createTranslatedShape(this.shape,
					this.shapeAnchor, location.getX(), location.getY());

			/*
			 * if (s instanceof Rectangle2D) { Rectangle2D rect = (Rectangle2D)
			 * s; if (this.shapeFilled) { Paint p = this.fillPaint;
			 * 
			 * p.setStyle(Paint.Style.FILL); g2.drawRect((float) rect.getMinX(),
			 * (float) rect.getMinY(), (float) rect.getMaxX(), (float)
			 * rect.getMaxY(), p); } if (this.shapeOutlineVisible) { Paint p =
			 * this.outlinePaint;
			 * 
			 * p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(outlineStroke);
			 * g2.drawRect((float) rect.getMinX(), (float) rect.getMinY(),
			 * (float) rect.getMaxX(), (float) rect.getMaxY(), p); } }
			 */

			if (shape instanceof Rectangle2D) {
				Rectangle2D rect = (Rectangle2D) shape;
				if (this.shapeFilled) {
					Paint p = this.fillPaint;

					p.setStyle(Paint.Style.FILL);
					g2.drawRect((float) location.getX(), (float) location
							.getY(),
							(float) (location.getX() + rect.getWidth()),
							(float) (location.getY() + rect.getHeight()), p);
				}
				if (this.shapeOutlineVisible) {
					Paint p = this.outlinePaint;

					p.setStyle(Paint.Style.STROKE);
					p.setStrokeWidth(outlineStroke);
					g2.drawRect((float) location.getX(), (float) location
							.getY(),
							(float) (location.getX() + rect.getWidth()),
							(float) (location.getY() + rect.getHeight()), p);

				}
			} else if (shape instanceof Ellipse2D) {
				Ellipse2D ellips = (Ellipse2D) shape;
				if (this.shapeFilled) {
					Paint p = this.fillPaint;

					p.setStyle(Paint.Style.FILL);
					g2.drawCircle((float) location.getX(), (float) location
							.getY(), 5, fillPaint);
							
					
				}
				if (this.shapeOutlineVisible) {
					Paint p = this.outlinePaint;

					p.setStyle(Paint.Style.STROKE);
					p.setStrokeWidth(outlineStroke);
					g2.drawCircle((float) location.getX(), (float) location
							.getY(), 5, fillPaint);
							

				}
			}
			else if (shape instanceof Polygon) {
				Path2D polygon = (Path2D) s;
				if (this.shapeFilled) {
					Paint p = this.fillPaint;

					p.setStyle(Paint.Style.FILL_AND_STROKE);
					Path path = convertAwtPathToAndroid(polygon.getPathIterator(null));
					g2.drawPath(path, p);
							
					
				}
				if (this.shapeOutlineVisible) {
					Paint p = this.outlinePaint;

					p.setStyle(Paint.Style.STROKE);
					p.setStrokeWidth(outlineStroke);
					Path path = convertAwtPathToAndroid(polygon.getPathIterator(null));
					g2.drawPath(path, p);

				}
			}
		}

	}

	/**
	 * Draws the block within the specified area.
	 * 
	 * @param g2
	 *            the graphics device.
	 * @param area
	 *            the area.
	 * @param params
	 *            ignored (<code>null</code> permitted).
	 * 
	 * @return Always <code>null</code>.
	 */
	public Object draw(Canvas g2, Rectangle2D area, Object params) {
		draw(g2, area);
		return null;
	}

	@Override
	public void draw(Canvas g2, Rectangle area) {
		draw(g2, area);

	}
	
	private Path convertAwtPathToAndroid(PathIterator pi) {
		Path path = new Path();
		float[] coords = new float[6];
		while (!pi.isDone()) {
			int windingRule = pi.getWindingRule();

			if (windingRule == PathIterator.WIND_EVEN_ODD) {
				path.setFillType(Path.FillType.EVEN_ODD);
			} else {
				path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
			}

			int pathType = pi.currentSegment(coords);

			switch (pathType) {
			case PathIterator.SEG_CLOSE:
				path.close();
				break;
			case PathIterator.SEG_CUBICTO:
				path.cubicTo(coords[0], coords[1], coords[2], coords[3],
						coords[4], coords[5]);
				break;
			case PathIterator.SEG_LINETO:
				path.lineTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_MOVETO:
				path.moveTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_QUADTO:
				path.quadTo(coords[0], coords[1], coords[2], coords[3]);
				break;
			}

			pi.next();
		}
		return path;
	}


}
