package org.spodge.asteroids;

public class MathUtils {
 /**Pseudo random float generation. Uses lossy casting from double to float*/
 public static float randomFloat(float min, float max) {
  return ((float)Math.random()*(max-min))+min;
 }

 /**Distance between two points on an orthogonal 2D plane*/
 public static float length(float x1, float y1, float x2, float y2) {
  return (float)Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
 }
}
