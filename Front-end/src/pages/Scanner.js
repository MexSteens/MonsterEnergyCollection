import './Scanner.css'
import React, { Component } from 'react'
import Scanner from '../Components/Scanner'
// import {Fab, TextareaAutosize, Paper} from '@material-ui/core'
// import {ArrowBack} from '@material-ui/icons'
import { Navigate, useNavigate } from 'react-router-dom'
import { withRouter } from '../Components/withRouter';
import Footer from '../Components/Footer';


class Test extends Component {
  constructor(){
    super()
    this.redirect=this.redirect.bind(this);
}
  
  state = {
    results: [],
  }

  redirect() {
    this.props.navigate("/result/" + this.state.results[0].codeResult.code)
  }

  _scan = () => {
    this.setState({ scanning: !this.state.scanning })
  }

  _onDetected = result => {
    this.setState({ results: [] })
    this.setState({ results: this.state.results.concat([result]) })
  }

  render() {
    if (this.state.results.length !== 0) {
      console.log("hello");
      this.redirect();
    }

    return (
      <div class="container flex flex-col h-screen">
        <div className="padding p-4 flex-grow">
        <h1 class="text-center">point your camera at the barcode</h1>
        
        <div class="border-2 border-green-600"><Scanner onDetected={this._onDetected} /></div>
        </div>
        
        {/* <h1 class="text-center">result: {this.state.results[0] ? this.state.results[0].codeResult.code : 'No data scanned'}</h1> */}
        <footer>
        <Footer />
        </footer>
      </div>
    )
  }
}

export default withRouter(Test);