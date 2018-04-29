package org.choose_a_name.main.shapes;

import org.choose_a_name.main.IPshape;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public final class Ship<T extends PApplet> extends IPshape {
 /**Constants for an isosceles triangle with a base length of 1
  * and a side length of 1.5.
  */
 public static final float
  A = (float)Math.acos(1f/3.0f),
  B = 1.5f/(float)Math.sin(A),
  C = A,
  HEIGHT = (float)Math.sin(C)*1.5f,
  WIDTH = 1f,
  SCALE_FACTOR = 100f;

 /**Centroid of the unit triangle, used for translations and rotations*/
 public static final PVector CENTROID = new PVector((HEIGHT+WIDTH)/3.0f, (2*HEIGHT)/3.0f);
 public final PVector adjustedCentroid;
 public final PShape ship;
 /**Constants relating to the circumcircle of the triangle
  * constructed from points A, B and C.
  */
 public final float
  scaleFactor,
  LINE_a,
  diameter;

 private final T parentApplet;

// public final IFunction.BiVFunction<HookablePApplet.KeyEventType, Integer> firingCallback = (eventType, keyCode) -> {
//  if (eventType == HookablePApplet.KeyEventType.PRESSED && keyCode == ' ') {
//   ((Asteroids)parentApplet).recurrentTaskExecutor.getOperands().add((ScreenObject<Asteroids>) new Bullet<T>(
//    parentApplet,
//    pos.copy(),
//    arv.copy()
//   ));
//  }
// };

 public Ship(T applet, float scaleFactor) {
  super(applet);
  parentApplet = applet;
//  super(parentApplet, pos, arv);
  this.ship = applet.createShape(PConstants.GROUP);
  this.scaleFactor = scaleFactor;
  this.adjustedCentroid = CENTROID.copy().mult(this.scaleFactor);
  this.LINE_a = (float)Math.sqrt(
   Math.pow(scaleFactor*WIDTH/2f-scaleFactor*WIDTH, 2) + Math.pow(scaleFactor*HEIGHT, 2)
  );
  this.diameter = (float)(LINE_a/Math.sin(A));
  applet.pushMatrix();
  applet.fill(0);
  applet.stroke(255);
  applet.strokeWeight(2);
  //Line from B to A
  this.ship.addChild(applet.createShape(
   PShape.LINE,
   0,
   this.scaleFactor*HEIGHT,
   this.scaleFactor*(WIDTH/2.0f),
   0
  ));
  //Line from C to A
  this.ship.addChild(applet.createShape(
   PShape.LINE,
   this.scaleFactor*WIDTH,
   this.scaleFactor*HEIGHT,
   this.scaleFactor*(WIDTH/2.0f),
   0
  ));
  //Arc
  applet.pushMatrix();
  applet.noFill();
  this.ship.addChild(applet.createShape(
   PShape.ARC,
   this.scaleFactor*(WIDTH/2.0f),
   this.scaleFactor*HEIGHT,
   this.scaleFactor*WIDTH,
   this.scaleFactor*(HEIGHT/4.0f),
   PConstants.PI,
   PConstants.TWO_PI
  ));
  applet.popMatrix();
//  parentApplet.rotate(this.arv.y);
  applet.popMatrix();
  //Register firing callback
//  parentApplet.registerKeyEventCallback(firingCallback);
 }

 public Ship(T applet) { this(applet, SCALE_FACTOR); }

// @Override
// public boolean collidesWith(PhysicsObject po) {
//  if (po instanceof Asteroid) {
//   Asteroid asteroid = (Asteroid)po;
//   float length = length(pos.x+adjustedCentroid.x, pos.y+adjustedCentroid.y, asteroid.pos.x, asteroid.pos.y);
//   if (length >= asteroid.getRadius()) {
//    return true;
//   }
//  }
//  return false;
// }

// @Override
// public void update() {
//  //Input loop for held keys
//  if (parentApplet.isKeyHeld(PConstants.LEFT)) updateHeading(-PConstants.PI/50f);
//  if (parentApplet.isKeyHeld(PConstants.RIGHT)) updateHeading(PConstants.PI/50f);
//  //Update velocity by heading if user is moving forward, otherwise continue with the velocity according to the last
//  //heading that was activated with forward movement
//  if (parentApplet.isKeyHeld(PConstants.UP)) {
//   if (getInternalVelocity() < 10f) {
//    updateVelocity(0.1f);
//   }
//   //TODO Fix weird bug with velocity bumps
//   float
//    xVel = (float)(vel.x + getInternalVelocity()*Math.cos(arv.x-PConstants.HALF_PI)),
//    yVel =(float)(vel.y + getInternalVelocity()*Math.sin(arv.x-PConstants.HALF_PI)),
//    newXVel = vel.x,
//    newYVel = vel.y;
//   if (xVel < 10f && xVel > -10f) newXVel = xVel;
//   if (yVel < 10f && yVel > -10f) newYVel = yVel;
//   vel.set(newXVel , newYVel);
//  }
//  boundCheck();
//  //Update position by actual velocity
//  pos.set(pos.x+vel.x, pos.y+vel.y);
// }

// @Override
// public float getDiameter() { return this.diameter; }

// @Override
// public void draw() {
//  parentApplet.pushMatrix();
//  parentApplet.translate(pos.x-adjustedCentroid.x, pos.y-adjustedCentroid.y);
////  parentApplet.rotate(arv.x);
//  parentApplet.shape(ship);
//  parentApplet.popMatrix();
// }

 public PShape getShape() { return this.ship; }

 public float getLength() { return scaleFactor*HEIGHT; }

 public float getWidth() { return scaleFactor*WIDTH; }
}