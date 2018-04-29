package org.choose_a_name.main;

import org.choose_a_name.main.shapes.Ship;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public final class Player extends IPhysicsObject {
 public final Ship ship;

 public Player(PApplet parentApplet) {
  super();
  this.ship = new Ship(parentApplet);
 }

 public PVector getPos() { return pos.copy(); }

 @Override
 public void update() {

 }
}
