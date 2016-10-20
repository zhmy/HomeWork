/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  DeviceEventEmitter,
  NativeModules,
  Switch,
  Dimensions,
  ToastAndroid,
  TouchableHighlight,
} from 'react-native';

import ReactTestView from './ReactTestView'
import ReactToolTipView from './ReactToolTipView'

export class ZmyNative extends Component {
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Nativehaha!
          ssss
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Switch value={true}></Switch>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake or press menu button for dev menu
        </Text>
      </View>
    );
  }
}

class hello extends Component {

    componentDidMount() {
        var BGNativeModuleExample = NativeModules.BGNativeExampleModule;
//        console.log(BGNativeModuleExample);
//        BGNativeModuleExample.testPrint("Jack", {
//            height: '1.78m',
//            weight: '7kg'
//        });

        // BGNativeModuleExample.getDataFromIntent(data => {
        //     console.warn(data);
        // });

//        BGNativeModuleExample.getNativeClass(name => {
//          console.warn("nativeClass: ", name);
//        });

//        BGNativeModuleExample.testPromises(true)
//        .then(result => {
//            console.log("result is ", result);
//        })
//        .catch(result => {
//            console.log("result = ", result);
//        });

//        console.log("BGModuleName const value = ", BGNativeModuleExample.BGModuleName);

//        DeviceEventEmitter.addListener(BGNativeModuleExample.TestEventName, info => {
//          console.warn(info);
//        });

    }

state = {
    trueSwitchIsOn: true,
    falseSwitchIsOn: false,
    tooltipActions : [{
                   text: '删除',
                   onPress: () => {
                     console.warn('wahhah');
                   }
               }],
  };



  render() {
    return (
      <View style={styles.container}>


        <ReactToolTipView
        actions={this.state.tooltipActions}
        underlayColor='transparent'>

        <Text style={styles.welcome}>
          Welcome to React Native 哈哈!
        </Text>

        </ReactToolTipView>

        <Switch value={this.state.falseSwitchIsOn} onValueChange={(value) => {
         ToastAndroid.show('我是Toast change ' + value, ToastAndroid.SHORT)
        this.setState({falseSwitchIsOn: value})}}></Switch>

        <ReactToolTipView underlayColor='transparent' actions={this.state.tooltipActions}>
        <ReactTestView style={styles.test} text='我是原生view'>
            <Text>12312431refd</Text>
        </ReactTestView>
        </ReactToolTipView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  test: {
    margin: 10,
    },
});

AppRegistry.registerComponent('ZmyNative', () => ZmyNative);
AppRegistry.registerComponent('hello', () => hello);
