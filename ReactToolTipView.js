'use strict';
var {
  requireNativeComponent,
  TouchableHighlight,
  View,
  NativeModules,
  findNodeHandle,
  UIManager,
} = require('react-native');
var React = require('react');
var RCTToolTipView = requireNativeComponent('RCTToolTipView', ToolTip);

export default class ToolTip extends React.Component {

tooltipView

constructor(props){
  super(props)
}

  getOptionTexts() {
    return this.props.actions.map((option) => option.text);
  }

  // Assuming there is no actions with the same text
  getCallback(optionText) {
    var selectedOption = this.props.actions.find((option) => option.text === optionText);
    if (selectedOption) {
      return selectedOption.onPress;
    }
    return null;
  }

  getTouchableHighlightProps(event) {
    var props = {};

    Object.keys(TouchableHighlight.propTypes).forEach((key) => props[key] = this.props[key]);

    if (this.props.longPress) {
      props.onLongPress = this.showMenu.bind(this);
    } else {
      props.onPress = this.showMenu.bind(this);
    }
    return props;
  }

  showMenu(event) {
      UIManager.dispatchViewManagerCommand(
                 findNodeHandle(this.toolTipText),
                 UIManager.RCTToolTipView.Commands.show,
                 this.getOptionTexts());
  }

  handleToolTipTextChange(event) {
        var callback = this.getCallback(event.nativeEvent.text);

        if (callback) {
          callback(event);
        }
  }

  render(){
    return (
          <RCTToolTipView ref={(view)=>{
            this.toolTipText = view
          }} onChange={this.handleToolTipTextChange.bind(this)}>
                    <TouchableHighlight {...this.getTouchableHighlightProps()} >
                                    <View>
                                    {this.props.children}
                                  </View>
                     </TouchableHighlight>
          </RCTToolTipView>
          )
  }
}
