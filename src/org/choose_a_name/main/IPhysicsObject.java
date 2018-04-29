package org.choose_a_name.main;

import processing.core.PVector;

public abstract class IPhysicsObject {
 public final PVector pos, vel, av;
 public IPhysicsObject(PShape shape, PVector pos, PVector vel, PVector av) {
  this.pos = pos;
  this.vel = vel;
  this.av = av;
 }
 public IPhysicsObject() { this(new PVector(), new PVector(), new PVector()); }

 public void update() {
  pos.x += vel.x;
  pos.y += vel.y;
  pos.z += vel.z;

 }
}
