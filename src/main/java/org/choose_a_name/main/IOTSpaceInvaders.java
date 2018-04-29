package org.choose_a_name.main;

import org.choose_a_name.main.internal.Controller;
import org.choose_a_name.main.physics.Player;
import org.choose_a_name.main.shapes.Asteroid;
import processing.core.*;

import static org.choose_a_name.main.MathUtils.*;

public class IOTSpaceInvaders extends PApplet {
// Ship<PApplet> ship;
 Player player;
 Asteroid<PApplet> asteroid;

 public static void main(String... args) {
  PApplet.main("org.choose_a_name.main.IOTSpaceInvaders");
 }

 @Override
 public void settings() {
  size(800, 600, P2D);
 }

 @Override
 public void setup() {
//  ship = new Ship<>(this, 50);
  player = new Player(this, 20);
  asteroid = new Asteroid<>(this, (int)randomFloat(5, 25), randomFloat(25, 70)+0f);
  loop();
 }

 //Main game loop
 @Override
 public void draw() {
  smooth();
  background(0);
  player.update();
  player.draw();
  pushMatrix();
  translate(0-asteroid.centroid.x, 0-asteroid.centroid.y);
  shape(asteroid.getShape(), 0f, 0f);
  popMatrix();
  asteroid = new Asteroid<>(this, (int) randomFloat(5, 25), randomFloat(25, 70) + 0f);
  //debug loop
//  int lastSecond = 0, currentSecond = 0;
//  if ((currentSecond = (int) Math.floor(System.currentTimeMillis()/1000f)) > lastSecond) {
//   lastSecond = currentSecond;
//  }
 }

 @Override
 public void exit() {
  noLoop();
  super.exit();
 }

 @Override
 public void keyPressed() {
  if (keyCode == UP) player.getController().fireEvent(Player.PlayerController.UP, Controller.State.PRESSED);
  if (keyCode == DOWN) player.getController().fireEvent(Player.PlayerController.DOWN, Controller.State.PRESSED);
  if (keyCode == LEFT) player.getController().fireEvent(Player.PlayerController.LEFT, Controller.State.PRESSED);
  if (keyCode == RIGHT) player.getController().fireEvent(Player.PlayerController.RIGHT, Controller.State.PRESSED);
 }

 @Override
 public void keyReleased() {
  if (keyCode == UP) player.getController().fireEvent(Player.PlayerController.UP, Controller.State.RELEASED);
  if (keyCode == DOWN) player.getController().fireEvent(Player.PlayerController.DOWN, Controller.State.RELEASED);
  if (keyCode == LEFT) player.getController().fireEvent(Player.PlayerController.LEFT, Controller.State.RELEASED);
  if (keyCode == RIGHT) player.getController().fireEvent(Player.PlayerController.RIGHT, Controller.State.RELEASED);
 }
}
