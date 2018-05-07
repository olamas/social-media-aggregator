import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import {BootstrapTable,
       TableHeaderColumn} from 'react-bootstrap-table';
import 'react-bootstrap-table/css/react-bootstrap-table.css'

class Board extends Component {

  constructor(props){
        super(props);
        this.state={
          filter:''
        }
   }

  handleClick(event){
           var apiBaseUrl = "http://localhost:8080/twitter/filter";
           var self = this;
           var http = new XMLHttpRequest();
           http.open('POST', apiBaseUrl, true);
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
              http.send();
   }



  render() {
    return (
      <MuiThemeProvider>
          <div>
          <TextField
                 hintText="Enter Filter value"
                 floatingLabelText="filter"
                 onChange = {(event,newValue) => this.setState({filter:newValue})}
                 />
               <RaisedButton label="Filter" primary={true} onClick={(event) => this.handleClick(event)}/>
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