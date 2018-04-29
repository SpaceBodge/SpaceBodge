package org.choose_a_name.main;

import processing.core.PVector;

public abstract class IPhysicsObject {
 public final PVector pos, av;
 public abstract void update();
 public IPhysicsObject(PVector pos, PVector av) {
  this.pos = pos;
  this.av = av;
 }
}
