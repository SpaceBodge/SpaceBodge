package org.choose_a_name.main.shapes;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import static org.choose_a_name.main.MathUtils.randomFloat;

public class Asteroid<T extends PApplet> extends IPshape {
 private final int vertices;
 private final float radius;
 private final PShape asteroid;
 public final PVector centroid;
// public final PVector pos = new PVector();

 public Asteroid(T applet, int vertices, float radius) {
//  super(parentApplet, pos, arv);
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

// @Override
// //TODO FINISH
// public boolean collidesWith(PhysicsObject po) {
//  return false;
// }

// @Override
// public void draw() {
//  parentApplet.pushMatrix();
//  parentApplet.translate(pos.x-centroid.x, pos.y-centroid.y);
////  parentApplet.rotate(arv.x);
//  parentApplet.shape(asteroid, 0, 0);
//  parentApplet.popMatrix();
// }

 @Override
 public PShape getShape() { return this.asteroid; }

// @Override
// public float getDiameter() { return 2f*this.radius; }

// @Override
// public void update() {
//  //Update heading by angular velocity
//  arv.x += arv.y;
//  //Update position by velocity
//  pos.set(pos.x+vel.x, pos.y+vel.y);
//  boundCheck();
// }

 public int getVertices() { return this.vertices; }

 public float getRadius() { return this.radius; }

// public List<Asteroid<T>> split() {
//  ArrayList<Asteroid<T>> children = new ArrayList<>();
//  int numChildren = (int)randomFloat(2, 5);
//  for (int i=0; i < numChildren; i++) {
//   Asteroid<T> child = new Asteroid<T>(
//    parentApplet,
//    pos.copy(),
//    new PVector(0, randomFloat(0, PConstants.PI/40f)),
//    (int)randomFloat(20, 30),
//    randomFloat(0.2f*radius, 0.8f*radius)
//   );
//   child.updateVelocity(new PVector(randomFloat(-2.5f, 2.5f), randomFloat(-2.5f, 2.5f)));
//   children.add(child);
//  }
////  ((Asteroids)parentApplet).recurrentTaskExecutor.getOperands().remove(this);
//  return children;
// }
}