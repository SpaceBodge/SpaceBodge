package org.choose_a_name.main.physics;

import org.choose_a_name.main.internal.Controller;
import org.choose_a_name.main.internal.IControllable;
import org.choose_a_name.main.internal.IControllerLayout;
import org.choose_a_name.main.internal.IDrawable;
import org.choose_a_name.main.shapes.Ship;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public final class Player extends IPhysicsObject implements IControllable<Player>, IDrawable {
 public enum PlayerController implements IControllerLayout { UP, DOWN, LEFT, RIGHT }
 private final Controller<Player> controller;
 public final Ship ship;

 public Player(PApplet parentApplet, float scaleFactor) {
  super();
  this.ship = new Ship(parentApplet, scaleFactor);
  this.controller = new Controller<>(PlayerController.UP);
  //default player acceleration
  controller.registerPressCallback(PlayerController.RIGHT, () -> isAccelerating = true);
  controller.registerReleaseCallback(PlayerController.RIGHT, () -> isAccelerating = false);
 }

 public Player(PApplet parentApplet) {
  this(parentApplet, Ship.SCALE_FACTOR);
 }

 @Override
 public void update() {
  //Input loop for held keys
  if (controller.isHeld(PlayerController.LEFT)) updateHeading(-PConstants.PI/50f);
  if (controller.isHeld(PlayerController.RIGHT)) updateHeading(PConstants.PI/50f);
  //Update velocity by heading if user is moving forward, otherwise continue with the velocity according to the last
  //heading that was activated with forward movement
  if (controller.isHeld(PlayerController.UP)) {
   if (getInternalVelocity() < 10f) {
    updateVelocity(0.01f);
   }
   float
    xVel = (float)(vel.x + getInternalVelocity()*Math.cos(av.x-PConstants.HALF_PI)),
    yVel =(float)(vel.y + getInternalVelocity()*Math.sin(av.x-PConstants.HALF_PI)),
    newXVel = vel.x,
    newYVel = vel.y;
   if (xVel < 10f && xVel > -10f) newXVel = xVel;
   if (yVel < 10f && yVel > -10f) newYVel = yVel;
   vel.set(newXVel , newYVel);
  }
//  boundCheck();
  //Update position by actual velocity
  pos.set(pos.x+vel.x, pos.y+vel.y);
 }

 @Override
 public Controller<Player> getController() {
  return this.controller;
 }

 @Override
 public void draw() {
  PApplet applet = ship.getParentApplet();
  applet.pushMatrix();
  applet.translate(applet.width/2f, applet.height/2f);
  applet.stroke(255);
  applet.noFill();
  applet.translate(pos.x-ship.adjustedCentroid.x, pos.y-ship.adjustedCentroid.y);
//  float
//   shipX = (float) (applet.width/2.0f*Math.sin(System.currentTimeMillis()/1000d)),
//   shipY = (float) (applet.height/2.0f*Math.cos(System.currentTimeMillis()/1000d));
  applet.rotate(av.x);
  applet.shape(ship.getShape());
  applet.popMatrix();
 }

 public PVector getPos() {
  return pos.copy();
 }
}
