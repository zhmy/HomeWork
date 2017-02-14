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
  TouchableOpacity,
  ListView,
} from 'react-native';

import ReactTestView from './ReactTestView'
import ReactToolTipView from './ReactToolTipView'
import {Navigation} from 'react-native-navigation';

export class ZmyNative extends Component {
    finish() {
    let ZmyNativeModule = NativeModules.ZmyNativeModule;
    ZmyNativeModule.finishActivity('sss');
    }
  render() {
    return (
      <View style={styles.container}>

      <ReactToolTipView
              actions={[{
                                          text: '关闭',
                                          onPress: () => {
                                            this.finish()
                                          }
                                      }]}
              underlayColor='transparent'>

              <Text style={styles.welcome}>
                Welcome to React Native 哈哈!
              </Text>

              </ReactToolTipView>

              <TouchableOpacity onPress={()=>{
                                                     this.props.navigator.push({
                                                   screen: 'ZmyNative',
                                                 });
                                                      }}>

        <Text style={styles.welcome}>
          Welcome to React Native!
          ssss
        </Text>
        </TouchableOpacity>
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

export class hello extends Component {

    ZmyNativeModule;

    componentDidMount() {
         ZmyNativeModule = NativeModules.ZmyNativeModule;
         ZmyNativeModule.getDataFromIntent(success => {
             console.warn(success);
         }, error => {
             console.warn(error);
         });

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

    startNative(text) {
        ZmyNativeModule.startActivityByString(text)
    }

    startNativeForResult(text) {
        ZmyNativeModule.startActivityForResult(text, 100, (succ)=>{ToastAndroid.show(succ, ToastAndroid.SHORT)}, (err)=>{console.warn(err)})
    }


state = {
    trueSwitchIsOn: true,
    falseSwitchIsOn: false,
    tooltipActions : [{
                   text: '打开RnTestActivity',
                   onPress: () => {
                     this.startNative('com.zmy.gradledemo.rn.RnTestActivity')
                   }
               }, {
                                     text: '打开RnTestActivity For Result',
                                     onPress: () => {
this.startNativeForResult('com.zmy.gradledemo.rn.RnTestActivity')
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

export class helloList extends Component {
// 初始化模拟数据
  constructor(props) {
    super(props);
    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    let data=[]
    for(let i=0;i<1000;i++){
        data.push({text:'lksdjf' + Math.random()})
    }
    this.state = {
      dataSource: ds.cloneWithRows(data)
    };
  }

    componentWillMount() {
//        console.warn('inmm')
        DeviceEventEmitter.addListener("key1", info => {
//        console.warn(info)
        this.startNative2();
        });

    }


  startNative2() {
//  console.warn(this.props.navigator)
//     this.props.navigator.push({
//   screen: 'ZmyNative',
// });
        Navigation.startSingleScreenApp({
        screen: {
          screen: 'ZmyNative',
        },
        });
      }

  render() {
    return (
      <View style={{flex: 1, paddingTop: 22}}>
        <ListView
          style={{backgroundColor:'red'}}
          dataSource={this.state.dataSource}
          renderRow={(rowData) => {
          return <TouchableOpacity onPress={()=>{this.startNative2()}}><Text style={{fontSize:20}}>{rowData.text}</Text></TouchableOpacity>
          }
          }
        />
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

//AppRegistry.registerComponent('ZmyNative', () => ZmyNative);
//AppRegistry.registerComponent('hello', () => hello);
//AppRegistry.registerComponent('TiebaNext', () => helloList);

Navigation.registerComponent('ZmyNative', () => ZmyNative);
Navigation.registerComponent('TiebaNext', () => helloList);
