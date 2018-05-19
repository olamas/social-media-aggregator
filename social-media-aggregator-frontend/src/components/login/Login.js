import React, { Component } from 'react';
import AppBar from 'material-ui/AppBar';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import BoardPage from '../aggregator/BoardPage';
import { Redirect } from 'react-router'

class Login extends Component {
    constructor(props){
      super(props);
      this.state={
        username:'',
        password:'',
        token:'',
        redirectToBoard: false
      }
    }

    readBody(xhr) {
        var data;
        if (!xhr.responseType || xhr.responseType === "text") {
            data = xhr.responseText;
        } else if (xhr.responseType === "document") {
            data = xhr.responseXML;
        } else {
            data = xhr.response;
        }
        return data;
    }

    handleClick(event){
         var apiBaseUrl = "http://localhost:8080/oauth/token";
         var self = this;

         var http = new XMLHttpRequest();
         var basicAuth = 'Basic ' + btoa('social-client:social-secret');
         var params = "grant_type=password&username="+this.state.username+"&password="+this.state.password;
         http.open('POST', apiBaseUrl, true);
         http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
         http.setRequestHeader("Authorization",basicAuth);

         http.onreadystatechange = function (response) {
                console.log(response);
                if (http.readyState == 4) {
                    if(http.status == 200){
                       var json = JSON.parse(self.readBody(http));
                       var accessToken = json.access_token;
                       self.setState({redirectToBoard:true});
                    }
                    else if(http.status == 204){
                        console.log("Username password do not match");
                        alert("username password do not match")
                    }
                    else{
                        console.log("Username does not exists");
                        alert("Username does not exist");
                    }
                }

            };
            http.send(params);
    }




    render() {
        const { redirectToBoard } = this.state;
        if (redirectToBoard) {
              return <Redirect to = '/board' />;
        }
        return (
            <div>
                <MuiThemeProvider>
                  <div>
                  <AppBar title="Social Media Agregator Login" />
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