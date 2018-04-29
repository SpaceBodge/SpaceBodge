package org.choose_a_name.main.physics;

import processing.core.PShape;
import processing.core.PVector;

public abstract class IPhysicsObject {
 public final PVector pos, vel, av;
// private final PVector iVel, iAv;
 private float internalVelocity;
 protected boolean isAccelerating;

 public IPhysicsObject(PVector pos, PVector vel, PVector av) {
  this.pos = pos;
  this.vel = vel;
  this.av = av;
 }
 public IPhysicsObject() { this(new PVector(), new PVector(), new PVector()); }

 public void updateVelocity(float offset) {
  internalVelocity += offset;
 }

 public void updateHeading(float offset) {
  this.av.x += offset;
 }

 public float getInternalVelocity() {
  return this.internalVelocity;
 }

 abstract void update();
}
