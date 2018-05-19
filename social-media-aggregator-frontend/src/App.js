import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Loginscreen from './components/login/Loginscreen';
import BoardPage from './components/aggregator/BoardPage';
import {Route,Link} from 'react-router-dom';

class App extends Component {
  constructor(props){
       super(props);
       this.state={loginPage:[]}
  }

  componentWillMount(){
       var loginPage =[];
       loginPage.push(<Loginscreen parentContext={this}/>);
       this.setState({loginPage:loginPage})
  }

  render() {
      return (
        <div className="App">
         <div className="App-intro">
               <Route path="/login" component={Loginscreen}/>
               <Route path="/board" component={BoardPage}/>
          </div>
        </div>
      );
  }
}
const style = {
  margin: 15,
};

export default App;
