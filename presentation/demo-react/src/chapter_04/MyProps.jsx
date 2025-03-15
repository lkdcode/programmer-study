import React from "react";

function MyProps(props) {
  return (
    <div>
      <h1>{`${props.name}`}</h1>
      <h2>{`${props.age}`}</h2>
      <h3>{`${props.job}`}</h3>
    </div>
  )
}

export default MyProps;