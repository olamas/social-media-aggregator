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
      loginscreen:[],
      loginmessage:'',
      buttonLabel:'Register',
      isLogin:true
    }
  }
  componentWillMount(){
    var loginscreen=[];
    loginscreen.push(<Login parentContext={this} appContext={this.props.parentContext}/>);
    this.setState({
                  loginscreen:loginscreen
                  })
  }
  render() {
    return (
      <div className="loginscreen">
        {this.state.loginscreen}
      </div>
    );
  }
}
const style = {
  margin: 15,
};
export default Loginscreen;