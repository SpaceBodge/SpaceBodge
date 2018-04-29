package org.choose_a_name.main;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Player extends IPshape {
 private PVector pos;

 public Player(PApplet parentApplet) {
  super(parentApplet);
 }


 @Override
 public PShape getShape() {
  return null;
 }
}
