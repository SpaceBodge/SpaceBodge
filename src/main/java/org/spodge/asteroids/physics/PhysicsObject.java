package org.spodge.asteroids.physics;

import processing.core.PApplet;
import processing.core.PVector;

/**TODO Documentation
 * TODO implement proper acceleration
 * TODO proper angular velocity (heading and acceleration)
 * TODO velocity and momentum based physics
 * TODO a polling based threaded system
 *
 * Other ideas:
 *  - Proper force based on weight distribution
 *  - Realistic shearing of polygons
 *  - Microgravity
 */
public abstract class PhysicsObject {
 public enum PhysicsModels {
  VELOCITY() {

  },
  MOMENTUM() {

  };

  PhysicsModels() {

  }
 }

 public final PVector pos, vel, av;
// private final PVector iVel, iAv;
 private float internalVelocity;
// protected boolean isAccelerating;
 protected final PApplet parentApplet;

 public PhysicsObject(PApplet parentApplet, PVector pos, PVector vel, PVector av) {
  this.parentApplet = parentApplet;
  this.pos = pos;
  this.vel = vel;
  this.av = av;
 }

 public PhysicsObject(PApplet parentApplet) {
  this(parentApplet, new PVector(), new PVector(), new PVector());
 }

 //do not implement physics after 20+ hours of no sleep
 @Deprecated
 public void updateVelocity(float offset) {
  internalVelocity += offset;
 }

 public void updateVelocity(PVector offset) {
  this.vel.add(offset);
 }

 public void updateHeading(float offset) {
  this.av.x += offset;
 }

 public float getInternalVelocity() {
  return this.internalVelocity;
 }

 abstract void update();

 public PApplet getApplet() {
  return this.parentApplet;
 }
}
