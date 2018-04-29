import React from 'react';
import { StyleSheet, Text, View, Button, ToastAndroid } from 'react-native';

export default class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = { connected: false };

    this.onPressUp = this.onPressUp.bind(this);
    this.onPressDown = this.onPressDown.bind(this);
    this.onPressRight = this.onPressRight.bind(this);
    this.onPressLeft = this.onPressLeft.bind(this);
    this.onPressConnect = this.onPressConnect.bind(this);
    this.onPressDisconnect = this.onPressDisconnect.bind(this);
  }

  onPressConnect() {
    let ws = new WebSocket('ws://FIXME');
    ws.onopen = () => {
      ToastAndroid.show(
        'Connected',
        ToastAndroid.SHORT,
      );
      this.setState({ connected: true });
    }
    ws.onerror = (e) => {
      ToastAndroid.show('Error: ' + e.message, ToastAndroid.SHORT);
    }
    this.setState({ ws: ws });
  }

  onPressDisconnect() {
    this.state.ws.close();
    this.state.ws.onclose = () => {
      ToastAndroid.show(
        'Disconnected',
        ToastAndroid.SHORT
      )
    }
    this.setState({
      connected: false
    });
  }

  onPressUp() {
    this.state.ws.send('1');
    setTimeout(() => {
      this.state.ws.send('-1');
    }, 100);
  }

  onPressDown() {
    this.state.ws.send('3');
    setTimeout(() => {
      this.state.ws.send('-3');
    }, 100);
  }

  onPressRight() {
    this.state.ws.send('4');
    setTimeout(() => {
      this.state.ws.send('-4');
    }, 100);
  }

  onPressLeft() {
    this.state.ws.send('2');
    setTimeout(() => {
      this.state.ws.send('-2');
    }, 100);
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.wrapper}>
          <Button
            title={this.state.connected ? 'Disconnect' : 'Connect'}
            color={this.state.connected ? '#dd2c00' : '#64dd17'}
            onPress={this.state.connected ? this.onPressDisconnect : this.onPressConnect}
            style={styles.button} />
          <Text style={{ paddingTop: 10, color: '#808080' }}>{this.state.connected ? 'Connected' : 'Disconnected'}</Text>
        </View>

        <View style={styles.wrapper}>
          <Button
            title="Up"
            onPress={this.onPressUp}
            style={styles.button} />
        </View>

        <View style={{flexDirection: "row"}}>
           <View style={styles.wrapper}>
             <Button
               title="Right"
               onPress={this.onPressRight}
               style={styles.button} />
           </View>

           <View style={styles.wrapper}>
             <Button
               title="Left"
               onPress={this.onPressLeft}
               style={styles.button} />
           </View>
         </View>

        <View style={styles.wrapper}>
          <Button
            title="Down"
            onPress={this.onPressDown}
            style={styles.button} />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
  wrapper: {
    padding: 10
  }
});
