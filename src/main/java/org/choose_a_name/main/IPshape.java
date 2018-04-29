package org.choose_a_name.main;

import processing.core.PApplet;
import processing.core.PShape;

public abstract class IPshape <T extends PApplet> extends PShape {
 public final T parentApplet;
 protected abstract PShape getShape();
 public IPshape(T parentApplet) { this.parentApplet = parentApplet; }
}
