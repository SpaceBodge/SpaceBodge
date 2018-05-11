package org.spodge.asteroids.physics;

import org.spodge.asteroids.internal.Controller;
import org.spodge.asteroids.internal.IControllable;
import org.spodge.asteroids.internal.IControllerEvent;
import org.spodge.asteroids.internal.IDrawable;
import org.spodge.asteroids.shapes.Ship;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Player extends PhysicsObject implements IControllable<Player>, IBoundingBox, IDrawable {
 public enum PlayerEvents implements IControllerEvent { UP, LEFT, RIGHT, FIRE }
 private final Controller controller;
 private final LinkedList<Bullet> bullets;
 public final Ship ship;

 public Player(PApplet parentApplet, Controller controller, float scaleFactor) {
  super(parentApplet);
  this.controller = controller;
  this.bullets = new LinkedList<>();
  this.ship = new Ship(parentApplet, scaleFactor);
  //Register event callbacks
  controller.registerCallback(PlayerEvents.FIRE, Controller.State.PRESSED, this::fire);
  //default player acceleration
//  controller.registerPressCallback(PlayerEvents.RIGHT, () -> isAccelerating = true);
//  controller.registerReleaseCallback(PlayerEvents.RIGHT, () -> isAccelerating = false);
 }

 private void fire() {
  bullets.add(new Bullet(
   this.parentApplet,
   this.pos.copy().sub(new PVector(ship.adjustedCentroid.x/2f, ship.adjustedCentroid.y)),
   new PVector(12f, 12f),
//   this.vel.copy().add(1f, 1f),
   new PVector(this.av.x, 0f, 0f)
  ));
 }

 //TODO switch
 @Override
 public void update() {
  //Input loop for held keys
  if (controller.isHeld(PlayerEvents.LEFT)) updateHeading(-PConstants.PI/70f);
  if (controller.isHeld(PlayerEvents.RIGHT)) updateHeading(PConstants.PI/70f);
  //Update velocity by heading if user is moving forward, otherwise continue with the velocity according to the last
  //heading that was activated with forward movement
  if (controller.isHeld(PlayerEvents.UP)) {
   if (getInternalVelocity() < 10f) {
    updateVelocity(0.01f);
   }
   float
    xVel = (float)(vel.x + getInternalVelocity()*Math.cos(av.x-PConstants.HALF_PI)),
    yVel = (float)(vel.y + getInternalVelocity()*Math.sin(av.x-PConstants.HALF_PI)),
    newXVel = vel.x,
    newYVel = vel.y;
   //Prevent the ship speed from passing 10 pixels per frame
   if (xVel < 10f && xVel > -10f) newXVel = xVel;
   if (yVel < 10f && yVel > -10f) newYVel = yVel;
   vel.set(newXVel , newYVel);
  }
  checkScreenBound();
  //Update position by actual velocity
  pos.set(pos.x+vel.x, pos.y+vel.y);
  //Update bullets
  final List<Bullet> toRemove = new ArrayList<>();
  bullets.forEach(bullet -> {
   if (!bullet.shouldDraw()) toRemove.add(bullet);
   else bullet.update();
  });
  bullets.removeAll(toRemove);
 }

 @Override
 public void draw() {
  PApplet applet = ship.getParentApplet();
  applet.pushMatrix();
  applet.stroke(255);
  applet.noFill();
  applet.translate(pos.x-ship.adjustedCentroid.x, pos.y-ship.adjustedCentroid.y);
  applet.rotate(av.x);
  applet.shape(ship.getShape());
  applet.popMatrix();
  //Draw bullets
  bullets.forEach(bullet -> bullet.draw());
 }

 @Override
 public boolean collidesWith(PhysicsObject ipo) {
  return false;
 }
}
