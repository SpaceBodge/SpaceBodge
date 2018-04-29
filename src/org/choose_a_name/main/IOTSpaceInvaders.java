package org.choose_a_name.main;

import org.choose_a_name.main.shapes.Asteroid;
import org.choose_a_name.main.shapes.Ship;
import processing.core.*;

import static org.choose_a_name.main.MathUtils.*;

public class IOTSpaceInvaders extends HookableApplet {
 Ship<HookableApplet> ship;
 Asteroid<HookableApplet> asteroid;

 public static void main(String... args) {
  PApplet.main("org.choose_a_name.main.IOTSpaceInvaders");
 }

 @Override
 public void settings() {
  size(800, 600, P2D);
 }

 @Override
 public void setup() {
  ship = new Ship<>(this, 50);
  asteroid = new Asteroid<>(this, (int)randomFloat(5, 25), randomFloat(25, 70)+0f);
  loop();
 }

 @Override
 public void draw() {
  smooth();
  background(0);
  stroke(255);
  noFill();
  translate(width/2.0f, height/2.0f);
  float
   shipX = (float) (width/2.0f*Math.sin(System.currentTimeMillis()/1000d)),
   shipY = (float) (height/2.0f*Math.cos(System.currentTimeMillis()/1000d));
  pushMatrix();
  shape(ship.getShape(), shipX,  shipY);
  popMatrix();
  pushMatrix();
  translate(0-asteroid.centroid.x, 0-asteroid.centroid.y);
  shape(asteroid.getShape(), 0f, 0f);
  popMatrix();
//  if (System.currentTimeMillis()%1000f == 0) {
   asteroid = new Asteroid<>(this, (int) randomFloat(5, 25), randomFloat(25, 70) + 0f);
//  }
 }

 @Override
 public void exit() {
  noLoop();
  super.exit();
 }
}
