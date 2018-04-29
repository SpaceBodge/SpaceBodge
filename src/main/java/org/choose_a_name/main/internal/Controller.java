package org.choose_a_name.main.internal;

import java.util.HashMap;
import java.util.LinkedList;

public class Controller<L extends IControllable<L>> {
 public enum State { PRESSED, RELEASED }
 public final IControllerLayout layout;

 private final HashMap<IControllerLayout, LinkedList<Function>> pressCallbacks, releaseCallbacks;
 private final HashMap<IControllerLayout, Boolean> activationMap;

 public Controller(IControllerLayout layout) {
  this.layout = layout;
  this.pressCallbacks = new HashMap<>();
  this.releaseCallbacks = new HashMap<>();
  this.activationMap = new HashMap<>();
 }

 public void registerPressCallback(IControllerLayout event, Function function) {
  LinkedList<Function> orderedCallbacks = pressCallbacks.get(event);
  if (orderedCallbacks == null) orderedCallbacks = new LinkedList<>();
  orderedCallbacks.add(function);
  pressCallbacks.put(event, orderedCallbacks);
 }

 public void registerReleaseCallback(IControllerLayout event, Function function) {
  LinkedList<Function> orderedCallbacks = releaseCallbacks.get(event);
  if (orderedCallbacks == null) orderedCallbacks = new LinkedList<>();
  orderedCallbacks.add(function);
  releaseCallbacks.put(event, orderedCallbacks);
 }

 public void fireEvent(IControllerLayout event, State state) {
  if (state == State.PRESSED) {
   if (pressCallbacks.containsKey(event)) {
    pressCallbacks.get(event).forEach(function -> function.invoke());
  }
   activationMap.put(event, true);
  } else if (state == State.RELEASED) {
   if (releaseCallbacks.containsKey(event)) {
    releaseCallbacks.get(event).forEach(function -> function.invoke());
   }
   activationMap.remove(event);
  }
 }

 public boolean isHeld(IControllerLayout event) {
  return activationMap.containsKey(event);
 }
}
