import "./App.css";

import Button from "./components/Button";
import Board from "./components/Board";
import axios from "axios";

function App() {
  const baseUrl = import.meta.env.DEV_BASE_URL;

  const onGet = () => {
    axios
      .get(`${baseUrl}/api`)
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.log("Error: ", error);
      });
  };

  const onPost = () => {
    axios
      .post(`${baseUrl}/api`)
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.log("Error: ", error);
      });
  };

  const onDelete = () => {
    axios
      .delete(`${baseUrl}/api`)
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.log("Error: ", error);
      });
  };

  const onPut = () => {
    axios
      .put(`${baseUrl}/api`)
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.log("Error: ", error);
      });
  };

  return (
    <div className="App">
      <Button title={"시작하기"} onClickFun={onGet} />
      <Board size={15} />
    </div>
  );
}

export default App;
