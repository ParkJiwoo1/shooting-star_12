import './App.css';
import signup from './log/signup';
import React from 'react';
import { Route } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Route exact path="/" component={signup}/>
    </div>
  )
}

export default App;
