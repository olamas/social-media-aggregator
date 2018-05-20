import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {BootstrapTable,TableHeaderColumn} from 'react-bootstrap-table';
import 'react-bootstrap-table/css/react-bootstrap-table.css'

class Board extends Component {

  constructor(props){
        super(props);
        this.state={
          filter:'',
          token: props.match.params.token,
          username: props.match.params.username
        }
   }

  handleClick(event){
           var apiBaseUrl = "http://localhost:8080/twitter/filter";
           var self = this;
           var http = new XMLHttpRequest();
           var basicAuth = 'Bearer ' + this.state.token;
           var params = {userName: this.state.username,
                            filterText: this.state.filter,
                            fromUser:null,
                            validFilter:false};

           http.open('POST', apiBaseUrl, true);
           http.setRequestHeader("Content-type", "application/json");
           http.setRequestHeader("Authorization",basicAuth);
           http.onreadystatechange = function (response) {
                  console.log(response);
                  if (http.readyState == 4) {
                      if(http.status == 200){

                      }
                      else if(http.status == 204){

                      }
                      else{

                      }
                  }
              };
              http.send(JSON.stringify(params));
   }

  render() {
    return (
     <MuiThemeProvider>
          <div>
            <h1>Hello {this.state.username}!</h1>
            <h2>{this.state.token}!</h2>
          </div>
          <div>
              <TextField
                     hintText="Enter Filter value"
                     floatingLabelText="filter"
                     onChange = {(event,newValue) => this.setState({filter:newValue})}
                     />
                   <RaisedButton label="Filter" primary={true} onClick={(event) => this.handleClick(event)}/>
          </div>
          <div>
            <h1> </h1>
          </div>
          <div>
            <BootstrapTable data={this.props.data}>
              <TableHeaderColumn isKey dataField='id'>
                id
              </TableHeaderColumn>
              <TableHeaderColumn dataField='text'>
                Text
              </TableHeaderColumn>
              <TableHeaderColumn dataField='fromUser'>
                From
              </TableHeaderColumn>
            </BootstrapTable>
          </div>
      </MuiThemeProvider>
    );
  }
}
export default Board;