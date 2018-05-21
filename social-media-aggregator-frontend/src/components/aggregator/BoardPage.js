import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import TimerMixin from 'react-timer-mixin';
import {BootstrapTable,TableHeaderColumn} from 'react-bootstrap-table';
import 'react-bootstrap-table/css/react-bootstrap-table.css'
import 'react-bootstrap-table/dist/react-bootstrap-table-all.min.css';


class Board extends Component {

  constructor(props){
        super(props);
        this.state={
          items: [],
          filter:'',
          token: props.match.params.token,
          username: props.match.params.username
        }
   }

   componentDidMount() {
        var self = this;
        TimerMixin.setInterval(
               () => {
               self.fetchData(); },5000);
   }

   fetchData() {
        var apiBaseUrl = "http://localhost:8080/twitter/tweets/";
        var self = this;
        var http = new XMLHttpRequest();
        var basicAuth = 'Bearer ' + this.state.token;
        http.open('GET', apiBaseUrl+this.state.username, true);
        http.setRequestHeader("Content-type", "application/json");
        http.setRequestHeader("Authorization",basicAuth);
        http.onreadystatechange = function (response) {
               console.log(response);
               if (http.readyState == 4) {
                   if(http.status == 200){
                      var data = JSON.parse(http.responseText);
                      self.setState({items: data, filterText: self.state.filter,token:self.state.token,username:self.state.username});
                   }
                   else if(http.status == 204){

                   }
                   else{

                   }
               }
           };
        http.send();
   }



   handleClick(event){
           var filter = this.state.filter;
           if (filter.length !== 0) {
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
           else{
                alert("Filter can not be empty");
           }
   }

   createCustomModalHeader(onClose, onSave) {
       const headerStyle = {
         fontWeight: 'bold',
         fontSize: 'large',
         textAlign: 'center',
         backgroundColor: '#eeeeee'
       };
       return (
         <div className='modal-header' style={ headerStyle }>
           <h3>That is my custom header</h3>
           <button className='btn btn-info' onClick={ onClose }>Close it!</button>
         </div>
       );
   }


   render() {
    function format(cell, row){
      return '<p>' + cell.italics() + '</p>';
    }

    const options = {
          insertModalHeader: this.createCustomModalHeader
    };

    return (
     <MuiThemeProvider>
          <AppBar title={'Social Media Agregator  User: '+this.state.username} />
          <div>
            <h1></h1>
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
            <BootstrapTable data={this.state.items}  tableStyle={ { border: '2.5px solid' } }
                                                            containerStyle={ { border: '2.5px solid' } }>
              <TableHeaderColumn isKey dataField='idStr'  width="150" dataAlign="center" hidden>
                id
              </TableHeaderColumn>
              <TableHeaderColumn dataField='filter' width="150" dataAlign="left" columnTitle>
                 Filter
              </TableHeaderColumn>
              <TableHeaderColumn dataField='text' width="1000" dataFormat={format} tdStyle={ { whiteSpace: 'normal' } } headerAlign='center'  columnTitle>
                Tweet
              </TableHeaderColumn>
              <TableHeaderColumn dataField='fromUser' width="50" headerAlign='right' dataAlign="center" columnTitle thStyle={ { 'fontWeight': 'lighter' } }>
                From
              </TableHeaderColumn>
            </BootstrapTable>
          </div>
      </MuiThemeProvider>
    );
  }
}
export default Board;