# SpaceBodge

IoT Blockchain Machine Learning Asteriods clone powered by embedded controllers

- Setting up the server
- Setting up the client
- Setting up the React Native client (optional)

To get the server up-and-running, you'll need some hardware and software installed:
- Java 8
- Gradle
- Esplorer (for programming NodeMCU)
- esptool.py (for flashing the ESP8266)
- NodeMCU hardware, breadboard, connectors and buttons
- NodeMCU firmware with `websocket` support enabled

Make sure you've got submodules up-to-date `git submodule update --init`, then apply the following patch in the root directory (fixes issues with NodeMCU WebSockets):

```patch
diff --git a/src/main/java/org/java_websocket/WebSocketImpl.java b/src/main/java/org/java_websocket/WebSocketImpl.java
index 35e28d2..3a62e74 100644
--- a/src/main/java/org/java_websocket/WebSocketImpl.java
+++ b/src/main/java/org/java_websocket/WebSocketImpl.java
@@ -250,64 +250,24 @@ public class WebSocketImpl implements WebSocket {
 			HandshakeState handshakestate;
 			try {
 				if( role == Role.SERVER ) {
-					if( draft == null ) {
-						for( Draft d : knownDrafts ) {
-							d = d.copyInstance();
-							try {
-								d.setParseMode( role );
-								socketBuffer.reset();
-								Handshakedata tmphandshake = d.translateHandshake( socketBuffer );
-								if( !( tmphandshake instanceof ClientHandshake ) ) {
-									closeConnectionDueToWrongHandshake( new InvalidDataException( CloseFrame.PROTOCOL_ERROR, "wrong http function" ) );
-									return false;
-								}
-								ClientHandshake handshake = ( ClientHandshake ) tmphandshake;
-								handshakestate = d.acceptHandshakeAsServer( handshake );
-								if( handshakestate == HandshakeState.MATCHED ) {
-									resourceDescriptor = handshake.getResourceDescriptor();
-									ServerHandshakeBuilder response;
-									try {
-										response = wsl.onWebsocketHandshakeReceivedAsServer( this, d, handshake );
-									} catch ( InvalidDataException e ) {
-										closeConnectionDueToWrongHandshake( e );
-										return false;
-									} catch ( RuntimeException e ) {
-										wsl.onWebsocketError( this, e );
-										closeConnectionDueToInternalServerError( e );
-										return false;
-									}
-									write( d.createHandshake( d.postProcessHandshakeResponseAsServer( handshake, response ), role ) );
-									draft = d;
-									open( handshake );
-									return true;
-								}
-							} catch ( InvalidHandshakeException e ) {
-								// go on with an other draft
-							}
-						}
-						if( draft == null ) {
-							closeConnectionDueToWrongHandshake( new InvalidDataException( CloseFrame.PROTOCOL_ERROR, "no draft matches" ) );
-						}
-						return false;
-					} else {
-						// special case for multiple step handshakes
-						Handshakedata tmphandshake = draft.translateHandshake( socketBuffer );
-						if( !( tmphandshake instanceof ClientHandshake ) ) {
-							flushAndClose( CloseFrame.PROTOCOL_ERROR, "wrong http function", false );
-							return false;
-						}
-						ClientHandshake handshake = ( ClientHandshake ) tmphandshake;
-						handshakestate = draft.acceptHandshakeAsServer( handshake );
-
-						if( handshakestate == HandshakeState.MATCHED ) {
-							open( handshake );
-							return true;
-						} else {
-							close( CloseFrame.PROTOCOL_ERROR, "the handshake did finaly not match" );
-						}
-						return false;
-					}
-				} else if( role == Role.CLIENT ) {
+                    System.out.println("Start HACK");
+                    Draft d = knownDrafts.get(0);
+                    d.setParseMode( role );
+                    socketBuffer.reset();
+                    Handshakedata tmphandshake = d.translateHandshake( socketBuffer );
+                    ClientHandshake handshake = ( ClientHandshake ) tmphandshake;
+                    handshakestate = d.acceptHandshakeAsServer( handshake );
+                    try {
+                        ServerHandshakeBuilder response = wsl.onWebsocketHandshakeReceivedAsServer( this, d, handshake );
+                        write( d.createHandshake( d.postProcessHandshakeResponseAsServer( handshake, response ), role ) );
+                        draft = d;
+                        open( handshake );
+                    } catch (InvalidDataException e) {
+                        e.printStackTrace();
+                    }
+                    System.out.println("End HACK");
+                    return true;
+				} else if(role == Role.CLIENT) {
 					draft.setParseMode( role );
 					Handshakedata tmphandshake = draft.translateHandshake( socketBuffer );
 					if( !( tmphandshake instanceof ServerHandshake ) ) {

```

Then, use `mvn package -DskipTests` and move the `.jar` file in the build directory to `./libs` in the root of the repository. Then, `gradle shadowJar` and a `*-all.jar` will be produced in the build libraries. This binary is the binary you will use the run the game and  the server.

To set up the client, connect your NodeMCU to your computer, run Esplorer, load up the serial terminal, open up `src/main/lua/4_button_controller.lua` and load it into the NodeMCU (make sure to change the endpoint address). If everything ran correctly, you should see `Connected`, otherwise, try again.

Lastly, if you would like to run the React Native client, ensure you have `node` installed on your system and Expo installed on your phone, then simply run `npm start` and scan the provided QR code (note you will need to change the endpoint address)

This repository is licensed under the MIT license
