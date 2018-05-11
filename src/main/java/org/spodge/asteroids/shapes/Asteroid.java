package org.spodge.asteroids.shapes;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import static org.spodge.asteroids.MathUtils.randomFloat;

public class Asteroid<T extends PApplet> extends IPShape {
 private final int vertices;
 private final float radius;
 private final PShape asteroid;
 public final PVector centroid;

 public Asteroid(T applet, int vertices, float radius) {
  super(applet);
  this.vertices = vertices;
  this.radius = radius;
  //Sum of the vector coordinates to find the centroid
  this.centroid = new PVector(0f, 0f);
  this.asteroid = applet.createShape();
  applet.pushMatrix();
  asteroid.beginShape();
  asteroid.noFill();
  asteroid.stroke(255);
  //Vertex generation
  for (int i=0; i < vertices; i++) {
   float
    angle = (i/(vertices+0f))*PConstants.TWO_PI,
    offset = this.radius - randomFloat(0, 15),
    xPos = (float)(offset*Math.cos(angle)),
    yPos = (float)(offset*Math.sin(angle));
   asteroid.vertex(xPos, yPos);
   centroid.x += xPos;
   centroid.y += yPos;
  }
  /**Find average plane coordinates to find centroid. {@code (vertices+0f)} forcefully converts
   * the {@code vertices} from an {@code int} to a {@code float}.
   */
  centroid.x /= (vertices+0f);
  centroid.y /= (vertices+0f);
  asteroid.endShape(PConstants.CLOSE);
  applet.popMatrix();
 }

 @Override
 public PShape getShape() {
  return this.asteroid;
 }

 public int getVertices() {
  return this.vertices;
 }

 public float getRadius() {
  return this.radius;
 }
}