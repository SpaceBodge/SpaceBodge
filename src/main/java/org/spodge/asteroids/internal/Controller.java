package org.spodge.asteroids.internal;

import java.util.HashMap;
import java.util.LinkedList;

/**TODO Documentation
 * TODO:
 * - Implement a freezing system so callbacks and associations cannot be added once the controller has been configured or
 *   use NonBlockingHashMaps and ConcurrentLinkedQueues
 * - Add options for key repeats and delays
 */
public class Controller {
 /**TODO Documentation
  *
  */
 public enum State { PRESSED, RELEASED }

 private final HashMap<IControllerEvent, LinkedList<Function>> pressCallbacks, releaseCallbacks;
 private final HashMap<IControllerEvent, Boolean> activationMap;
 private final HashMap<Integer, LinkedList<IControllerEvent>> keyCodeEventMap;

 public Controller() {
  this.pressCallbacks = new HashMap<>();
  this.releaseCallbacks = new HashMap<>();
  this.activationMap = new HashMap<>();
  this.keyCodeEventMap = new HashMap<>();
 }

 /**Associates a callback function with the {@param event} passed. Whenever the event is fired, any associated
  * callbacks will be invoked, in the order of association (FILO). The callback will also be invoked if a key event
  * that is associated with {@param event} is fired.
  *
  * @param event - An event, specified as an enum implementing the {@link IControllerEvent} interface. Whenever the enum
  *              representing this event is fired, the callback will be invoked.
  * @param function - Callback to invoke.
  */
 public void registerCallback(IControllerEvent event, State state, Function function) {
  HashMap<IControllerEvent, LinkedList<Function>> callbackMap =
   state == State.PRESSED ?
    pressCallbacks :
    releaseCallbacks;
  LinkedList<Function> orderedCallbacks = callbackMap.get(event);
  if (orderedCallbacks == null) orderedCallbacks = new LinkedList<>();
  orderedCallbacks.add(function);
  callbackMap.put(event, orderedCallbacks);
 }

 /**Will invoke all callbacks associated with the {@param event} passed.
  *
  * @param event The key event to fire
  * @param state The state of the key
  */
 public void fireEvent(IControllerEvent event, State state) {
  if (state == State.PRESSED) {
   if (pressCallbacks.containsKey(event)) pressCallbacks.get(event).forEach(function -> function.invoke());
   activationMap.put(event, true);
  } else if (state == State.RELEASED) {
   if (releaseCallbacks.containsKey(event)) releaseCallbacks.get(event).forEach(function -> function.invoke());
   activationMap.remove(event);
  }
 }

 /**TODO Documentation
  *
  * @param keycode
  * @param event
  */
 public void associateKeyEvent(int keycode, IControllerEvent event) {
  LinkedList<IControllerEvent> mappedEvents = keyCodeEventMap.get(keycode);
  if (mappedEvents == null) mappedEvents = new LinkedList<>();
  mappedEvents.add(event);
  keyCodeEventMap.put(keycode, mappedEvents);
 }

 /**TODO Documentation
  * TODO This method needs to be threaded so it does not block other input
  *
  * @param keyCode
  * @param state
  */
 public void fireKey(int keyCode, final State state) {
  LinkedList<IControllerEvent> mappedEvents = keyCodeEventMap.get(keyCode);
  if (mappedEvents != null) mappedEvents.forEach(event -> fireEvent(event, state));
 }

 /**TODO Documentation
  *
  * @param event
  * @return
  */
 public boolean isHeld(IControllerEvent event) {
  return activationMap.containsKey(event);
 }
}
