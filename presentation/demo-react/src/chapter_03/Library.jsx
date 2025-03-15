import React from "react";
import Book from "./Book";

function Library(props) {
  return (
    <div>
      <Book name="처음 만난 파이썬" numOfPage={300} />
      <Book name="처음 만난 AWS" numOfPage={500} />
      <Book name="처음 만난 React" numOfPage={600} />
    </div>
  )
}

export default Library;