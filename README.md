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

Make sure you've got submodules up-to-date `git submodule update --init`, apply our patches using `git apply Java-WebSocket.patch`

Then, use `mvn package -DskipTests` and move the `.jar` file in the build directory to `./libs` in the root of the repository. Then, `gradle shadowJar` and a `*-all.jar` will be produced in the build libraries. This binary is the binary you will use the run the game and  the server.

To set up the client, connect your NodeMCU to your computer, run Esplorer, load up the serial terminal, open up `src/main/lua/4_button_controller.lua` and load it into the NodeMCU (make sure to change the endpoint address). If everything ran correctly, you should see `Connected`, otherwise, try again.

This repository is licensed under the MIT license
