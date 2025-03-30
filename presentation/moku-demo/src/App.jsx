import "./App.css";

import Header from "./components/Header";
import Button from "./components/Button";
import Board from "./components/Board";

function App() {
  return (
    <div className="App">
      <Header />
      <Button />
      <Board size={15} />
    </div>
  );
}

export default App;
