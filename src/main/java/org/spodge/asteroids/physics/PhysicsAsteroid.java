package org.spodge.asteroids.physics;

import org.spodge.asteroids.internal.IDrawable;
import org.spodge.asteroids.shapes.Asteroid;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static org.spodge.asteroids.MathUtils.*;

public class PhysicsAsteroid extends PhysicsObject implements IDrawable, IBoundingBox {
 private final Asteroid shape;

 public PhysicsAsteroid(PApplet parentApplet, PVector pos, PVector av, int vertices, float radius) {
  super(parentApplet, pos, new PVector(), av);
  this.shape = new Asteroid(parentApplet, vertices, radius);
 }

 @Override
 public void draw() {
  parentApplet.pushMatrix();
  parentApplet.fill(255);
  parentApplet.translate(pos.x-shape.centroid.x, pos.y-shape.centroid.y);
  parentApplet.shape(shape.getShape());
  parentApplet.popMatrix();
 }

 @Override
 public void update() {
  //Update position by velocity
  pos.set(pos.x+vel.x, pos.y+vel.y);
  checkScreenBound();
 }

 @Override
 public float getRadius() {
  return shape.getRadius();
 }

 @Override
 public boolean collidesWith(PhysicsObject ipo) {
  //currently unused for asteroids
  return false;
 }

 public List<PhysicsAsteroid> split() {
  ArrayList<PhysicsAsteroid> children = new ArrayList<>();
  int numChildren = (int)randomFloat(2, 5);
  for (int i=0; i < numChildren; i++) {
   PhysicsAsteroid child = new PhysicsAsteroid(
    parentApplet,
    pos.copy(),
    new PVector(0, randomFloat(0, PConstants.PI/40f)),
    (int)randomFloat(20, 30),
    randomFloat(0.2f*shape.getRadius(), 0.8f*shape.getRadius())
   );
   child.updateVelocity(new PVector(randomFloat(-2.5f, 2.5f), randomFloat(-2.5f, 2.5f)));
   children.add(child);
  }
  return children;
 }
}
