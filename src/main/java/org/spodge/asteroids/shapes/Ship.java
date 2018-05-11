package org.spodge.asteroids.shapes;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public final class Ship<T extends PApplet> extends IPShape {
 /**Constants for an isosceles triangle with a base length of 1 and a side length of 1.5.*/
 public static final float
  A = (float)Math.acos(1f/3.0f),
  B = 1.5f/(float)Math.sin(A),
  C = A,
  HEIGHT = (float)Math.sin(C)*1.5f,
  WIDTH = 1f,
  SCALE_FACTOR = 100f;

 /**Centroid of the unit triangle, used for translations and rotations.*/
 public static final PVector CENTROID = new PVector((HEIGHT+WIDTH)/3.0f, (2*HEIGHT)/3.0f);
 public final PVector adjustedCentroid;
 public final PShape ship;
 /**Constants relating to the circumcircle of the triangle constructed from points A, B and C.*/
 public final float
  scaleFactor,
  LINE_a,
  diameter;

 public Ship(T parentApplet, float scaleFactor) {
  super(parentApplet);
  this.ship = parentApplet.createShape(PConstants.GROUP);
  this.scaleFactor = scaleFactor;
  this.adjustedCentroid = CENTROID.copy().mult(this.scaleFactor);
  this.LINE_a = (float)Math.sqrt(
   Math.pow(scaleFactor*WIDTH/2f-scaleFactor*WIDTH, 2) + Math.pow(scaleFactor*HEIGHT, 2)
  );
  this.diameter = (float)(LINE_a/Math.sin(A));
  parentApplet.pushMatrix();
  parentApplet.fill(0);
  parentApplet.stroke(255);
  parentApplet.strokeWeight(2);
  //Line from B to A
  this.ship.addChild(parentApplet.createShape(
   PShape.LINE,
   0,
   this.scaleFactor*HEIGHT,
   this.scaleFactor*(WIDTH/2.0f),
   0
  ));
  //Line from C to A
  this.ship.addChild(parentApplet.createShape(
   PShape.LINE,
   this.scaleFactor*WIDTH,
   this.scaleFactor*HEIGHT,
   this.scaleFactor*(WIDTH/2.0f),
   0
  ));
  //Arc
  parentApplet.pushMatrix();
  parentApplet.noFill();
  this.ship.addChild(parentApplet.createShape(
   PShape.ARC,
   this.scaleFactor*(WIDTH/2.0f),
   this.scaleFactor*HEIGHT,
   this.scaleFactor*WIDTH,
   this.scaleFactor*(HEIGHT/4.0f),
   PConstants.PI,
   PConstants.TWO_PI
  ));
 }

 public Ship(T applet) {
  this(applet, SCALE_FACTOR);
 }

 public PShape getShape() {
  return this.ship;
 }

 public float getLength() {
  return scaleFactor*HEIGHT;
 }

 public float getWidth() {
  return scaleFactor*WIDTH;
 }
}