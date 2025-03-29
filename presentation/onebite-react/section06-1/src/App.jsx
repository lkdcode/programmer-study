import { useState } from "react";
import "./App.css";
import Controller from "./components/Controller";
import Viewer from "./components/Viewer";

function App() {
  const [count, setCount] = useState(0);

  const onClicnCount = (e) => {
    setCount(count + e);
  };

  return (
    <>
      <section className="App">
        <h1 className="Title">Simple Counter</h1>
        <Viewer count={count} />
        <Controller onClicnCount={onClicnCount} />
      </section>
    </>
  );
}

export default App;
