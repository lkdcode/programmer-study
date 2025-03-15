import React from "react";
import styled from "styled-components";

const Button = styled.button`
  color: ${props => props.dark ? "white" : "dark"};
  backgroud: ${props => props.dark ? "black" : "white"};
  border: 1px solid black;
`

function SampleButton(props) {
  return (
    <div>
      <Button> Normal</Button>
      <Button dark> Dark</Button>
    </div>
  )
}

export default SampleButton;