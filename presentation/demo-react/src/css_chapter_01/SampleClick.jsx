import React,{ useState } from "react";
import styled from "styled-components";
import SampleButton from "./SampleButton";

function SmapleClick(props){
  const [count, setCount] = useState(0);



  return (
    <div>
      <button onClick={()=>setCount(count+1)}>
        Click
      </button>
      <span>{count}</span>
      <SampleButton />
    </div>
  )
}

export default SmapleClick;