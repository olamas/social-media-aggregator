import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import RaisedButton from 'material-ui/RaisedButton';
import Login from './Login';

class Loginscreen extends Component {

  constructor(props){
    super(props);
    this.state={
      username:'',
      password:'',
      isLogin:true
    }
  }

  render() {
    return (
      <div className="loginscreen">
        <Login parentContext={this} appContext={this.props.parentContext}/>
      </div>
    );
  }
}
const style = {
  margin: 15,
};
export default Loginscreen;