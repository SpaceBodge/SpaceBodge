package org.choose_a_name.main;

import org.choose_a_name.main.internal.Controller;
import org.choose_a_name.main.physics.Player;
import org.choose_a_name.main.shapes.Asteroid;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketServerFactory;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import processing.core.*;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

 Socket client;
InputStream in;
OutputStream out;
WebSocketServer server;

 @Override
 public void setup() {
//  ship = new Ship<>(this, 50);
  player = new Player(this, 20);
  asteroid = new Asteroid<>(this, (int)randomFloat(5, 25), randomFloat(25, 70)+0f);


  server = new WebSocketServer(new InetSocketAddress(8080)) {
   @Override
   public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
   System.out.println("onOpen");
   }

   @Override
   public void onClose(WebSocket webSocket, int i, String s, boolean b) {
System.out.println("onClose");
   }

   @Override
   public void onMessage(WebSocket conn, String message) {
    System.out.println(message);
    Controller<Player> controller = player.getController();
    if(message.equals("1")) {
     controller.fireEvent(Player.PlayerController.UP, Controller.State.PRESSED);
    } else if(message.equals("-1")) {
     controller.fireEvent(Player.PlayerController.UP, Controller.State.RELEASED);
    } else if(message.equals("2")) {
     controller.fireEvent(Player.PlayerController.RIGHT, Controller.State.PRESSED);
    } else if(message.equals("-2")) {
     controller.fireEvent(Player.PlayerController.RIGHT, Controller.State.RELEASED);
    } else if(message.equals("3")) {
     controller.fireEvent(Player.PlayerController.DOWN, Controller.State.PRESSED);
    } else if(message.equals("-3")) {
     controller.fireEvent(Player.PlayerController.DOWN, Controller.State.RELEASED);
    } else if(message.equals("4")) {
     controller.fireEvent(Player.PlayerController.LEFT, Controller.State.PRESSED);
    } else if(message.equals("-4")) {
     controller.fireEvent(Player.PlayerController.LEFT, Controller.State.RELEASED);
    } else {
     System.out.println("Invalid message: " + message);
    }
   }

   @Override
   public void onError(WebSocket webSocket, Exception e) {
e.printStackTrace();
   }

   @Override
   public void onStart() {
System.out.println("Started on " + this.getPort());
   }
  };

  server.start();
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
  try {
  server.stop();
  } catch(Exception e) {
      e.printStackTrace();
  }
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
