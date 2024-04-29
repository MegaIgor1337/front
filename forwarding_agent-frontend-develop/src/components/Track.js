import React from "react"
import  track  from '../images/track.png'
import "../css/welcomePage.css"; 

class Track extends React.Component {
  render() {
    return (
       <img src={track} alt="Track" className="img-track" />
    );
  }
}

export default Track