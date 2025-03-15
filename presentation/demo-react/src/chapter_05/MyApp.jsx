import React from "react";
import Welcome from "./Welcome";

function MyApp(props) {
  return (
    <div>
      <Welcome name="Apple" />
      <Welcome name="Kiwi" />
      <Welcome name="Tomato" />
    </div>
  )
}

export default MyApp;