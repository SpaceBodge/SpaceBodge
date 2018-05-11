package org.spodge.asteroids.shapes;

import processing.core.PApplet;
import processing.core.PShape;

public abstract class IPShape<T extends PApplet> extends PShape {
 public final T parentApplet;

 protected abstract PShape getShape();

 public T getParentApplet() {
  return parentApplet;
 }

 public IPShape(T parentApplet) {
  this.parentApplet = parentApplet;
 }
}
