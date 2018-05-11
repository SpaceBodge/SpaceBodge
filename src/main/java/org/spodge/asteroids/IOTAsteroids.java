package org.spodge.asteroids;

import org.spodge.asteroids.internal.Controller;
import org.spodge.asteroids.physics.PhysicsAsteroid;
import org.spodge.asteroids.physics.Player;
import processing.core.*;

import java.util.LinkedList;

import static org.spodge.asteroids.MathUtils.*;

public class IOTAsteroids extends PApplet {
 final Controller globalController;
 Player player;
 LinkedList<PhysicsAsteroid> asteroids;

 public IOTAsteroids() {
  this.globalController = new Controller();
 }

 public static void main(String... args) {
  PApplet.main("org.spodge.main.IOTAsteroids");
 }

 @Override
 public void settings() {
  size(800, 600, "processing.awt.PGraphicsJava2D");
 }

 @Override
 public void setup() {
  surface.setResizable(true);
  //Setup player and register key associations
  player = new Player(this, globalController, 20);
  globalController.associateKeyEvent(UP, Player.PlayerEvents.UP);
  globalController.associateKeyEvent(LEFT, Player.PlayerEvents.LEFT);
  globalController.associateKeyEvent(RIGHT, Player.PlayerEvents.RIGHT);
  globalController.associateKeyEvent(' ', Player.PlayerEvents.FIRE);
  //Create initial asteroids
  final PApplet selfRef = this;
  asteroids = new LinkedList<PhysicsAsteroid>() {{
   for (int i=25; i > -1; i--) {
    PhysicsAsteroid asteroid = new PhysicsAsteroid(
     selfRef,
     new PVector(randomFloat(-width/2.0f, width/2.0f), randomFloat(-height/2.0f, height/2.0f)),
     new PVector(randomFloat(0, PConstants.PI/40f), 0, 0),
     (int)randomFloat(5, 25),
     (randomFloat(25, 70)+0f)
    );
    asteroid.updateVelocity(new PVector(randomFloat(-2.5f, 2.5f), randomFloat(-2.5f, 2.5f)));
    add(asteroid);
   }
  }};
  loop();
 }

 //Main game loop
 @Override
 public void draw() {
  smooth();
  background(0);
  fill(255);
  translate(width/2f, height/2f);
  player.update();
  player.draw();
  asteroids.forEach(asteroid -> {
   asteroid.update();
   asteroid.draw();
  });
 }

 @Override
 public void exit() {
  noLoop();
  super.exit();
 }

 @Override
 public void keyPressed() {
  int code = (key == CODED) ? keyCode : key;
  globalController.fireKey(code, Controller.State.PRESSED);
 }

 @Override
 public void keyReleased() {
  int code = (key == CODED) ? keyCode : key;
  globalController.fireKey(code, Controller.State.RELEASED);
 }
}
