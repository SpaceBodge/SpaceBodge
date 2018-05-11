package org.spodge.asteroids.physics;

import org.spodge.asteroids.internal.IDrawable;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import static org.spodge.asteroids.MathUtils.*;

public class Bullet extends PhysicsObject implements IDrawable, IBoundingBox {

 private boolean shouldDraw = true;
 private final PApplet parentApplet;

 public Bullet(PApplet parentApplet, PVector pos, PVector vel, PVector av) {
  super(parentApplet, pos, vel, av);
  this.parentApplet = parentApplet;
//  this.vel.x = (float)(20*Math.cos(av.x-PConstants.HALF_PI));
//  this.vel.y = (float)(20*Math.sin(av.x-PConstants.HALF_PI));
 }

 @Override
 public void draw() {
  parentApplet.pushMatrix();
  parentApplet.strokeWeight(5);
  parentApplet.stroke(240, 46, 46);

  parentApplet.point(pos.x, pos.y);
  parentApplet.popMatrix();
 }

 @Override
 public final boolean checkScreenBound() {
  boolean result = IBoundingBox.super.checkScreenBound();
  shouldDraw = !result;
  return result;
 }

 //TODO Outdated model, update to new collision model
 @Override
 public boolean collidesWith(PhysicsObject ipo) {
  if (ipo instanceof PhysicsAsteroid) {
   PhysicsAsteroid asteroid = (PhysicsAsteroid)ipo;
   if (length(pos.x, pos.y, asteroid.pos.x, asteroid.pos.y) <= asteroid.getRadius()) {
    shouldDraw = false;
    return true;
   }
  }
  return false;
 }

 @Override
 public void update() {
  pos.x += vel.x*Math.cos(av.x-PConstants.HALF_PI);
  pos.y += vel.y*Math.sin(av.x-PConstants.HALF_PI);
 }

 public boolean shouldDraw() {
  return shouldDraw;
 }
}
