import React, { Component } from 'react';
import AppBar from 'material-ui/AppBar';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import axios from 'axios';
import qs from 'qs';
import ajax from '@fdaciuk/ajax';

class Login extends Component {
    constructor(props){
      super(props);
      this.state={
        username:'',
        password:''
      }
    }

    handleClick(event){
         var apiBaseUrl = "http://localhost:8080/oauth/token";
         var self = this;
         const data={"username":this.state.username,"password":this.state.password,"grant_type":"password"};
         console.log(data);
         var basicAuth = 'Basic ' + btoa('social-client:social-secret');

         const options = {
          headers: { 'content-type': 'application/x-www-form-urlencoded','Authorization': basicAuth}
        };

         axios.post(apiBaseUrl,qs.stringify(data),options).then(function (response) {
                console.log(response);
                if(response.data.code == 200){
                    console.log("Login successfull");

                }
                else if(response.data.code == 204){
                    console.log("Username password do not match");
                    alert("username password do not match")
                }
                else{
                    console.log("Username does not exists");
                    alert("Username does not exist");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <div>
                <MuiThemeProvider>
                  <div>
                  <AppBar title="Login" />
                   <TextField
                     hintText="Enter your Username"
                     floatingLabelText="Username"
                     onChange = {(event,newValue) => this.setState({username:newValue})}
                     />
                   <br/>
                     <TextField
                       type="password"
                       hintText="Enter your Password"
                       floatingLabelText="Password"
                       onChange = {(event,newValue) => this.setState({password:newValue})}
                       />
                     <br/>
                     <RaisedButton label="Submit" primary={true} style={style} onClick={(event) => this.handleClick(event)}/>
                 </div>
                 </MuiThemeProvider>
              </div>
             );
    }
}
const style = {
 margin: 15,
};
export default Login;