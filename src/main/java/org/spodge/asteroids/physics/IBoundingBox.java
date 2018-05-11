package org.spodge.asteroids.physics;

import processing.core.PVector;

/**TODO Documentation
 *
 * @param <T>
 */
public interface IBoundingBox<T extends PhysicsObject> {
 //Old api will be removed soon
 @Deprecated
 default float getRadius() {
  return -1f; /*unimplemented by default*/
 }

 /**TODO Documentation
  * TODO Change to use distance from the furthest vertex from the centroid of the polygon, as the radius
  */
 default boolean checkScreenBound() {
  T po = (T)this;
  float diameter = 2*getRadius();
  if (po.pos.x > po.parentApplet.width/2f + diameter) {
   po.pos.x = -po.parentApplet.width/2f - diameter;
   return true;
  }
  if (po.pos.x < -po.parentApplet.width/2f - diameter) {
   po.pos.x = po.parentApplet.width/2f + diameter;
   return true;
  }
  if (po.pos.y > po.parentApplet.height/2f + diameter) {
   po.pos.y = -po.parentApplet.height/2f - diameter;
   return true;
  }
  if (po.pos.y < -po.parentApplet.height/2f - diameter) {
   po.pos.y = po.parentApplet.height/2f + diameter;
   return true;
  }
  return false;
 }

 /**TODO Documentation
  * TODO implement simple collision detection using the separating axis theorem, for all PShapes
  *
  * @param ipo
  * @return
  */
 boolean collidesWith(PhysicsObject ipo);

 /**TODO Documentation
  * TODO automatically calculate the centroid of PShapes (statically)
  *
  * @return
  */
 default PVector getCentroid() {
  //Currently unimplemented
  return null;
 }
}
