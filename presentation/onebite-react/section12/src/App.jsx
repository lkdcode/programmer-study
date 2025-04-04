import "./App.css";

import Home from "./pages/Home";
import Diary from "./pages/Diary";
import New from "./pages/New";
import { Route, Routes } from "react-router-dom";
import NotFound from "./pages/NotFound";
import Edit from "./pages/Edit";
import { createContext, useReducer, useRef } from "react";

const mockData = [
  {
    id: 1,
    createdDate: new Date().getTime(),
    emoitionId: 1,
    content: "1번 일기 내용",
  },

  {
    id: 2,
    createdDate: new Date().getTime(),
    emoitionId: 2,
    content: "2번 일기 내용",
  },

  {
    id: 3,
    createdDate: new Date().getTime(),
    emoitionId: 3,
    content: "3번 일기 내용",
  },
];

function reducer(state, action) {
  switch (action.type) {
    case "CREATE":
      return [action.data, ...state];
    case "UPDATE":
      return state.map((item) =>
        String(item.id) === String(action.data.id) ? action.data : item
      );
    case "DELETE":
      return state.filter((item) => String(item.id) !== String(action.data.id));
    default:
      return state;
  }
}

const DiaryStateContext = createContext();
const DiaryDispatchContext = createContext();

function App() {
  const [data, dispatch] = useReducer(reducer, mockData);

  const idRef = useRef(4);

  const onCreate = (createdDate, emoitionId, content) => {
    dispatch({
      type: "CREATE",
      data: {
        id: idRef.current++,
        createdDate,
        emoitionId,
        content,
      },
    });
  };

  const onUpdate = (id, createdDate, emoitionId, content) => {
    dispatch({
      type: "UPDATE",
      data: {
        id,
        createdDate,
        emoitionId,
        content,
      },
    });
  };

  const onDelete = (id) => {
    dispatch({
      type: "DELETE",
      data: {
        id,
      },
    });
  };

  return (
    <>
      <button
        onClick={() => {
          onCreate(new Date().getTime(), 1, "Hell");
        }}
      >
        hihi
      </button>

      <button
        onClick={() => {
          onUpdate(1, new Date().getTime(), 3, "hihihihihihhi");
        }}
      >
        bibi
      </button>

      <button
        onClick={() => {
          onDelete(1);
        }}
      >
        DELETE
      </button>
      <DiaryStateContext.Provider value={data}>
        <DiaryDispatchContext.Provider
          value={{
            onCreate,
            onUpdate,
            onDelete,
          }}
        >
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/new" element={<New />} />
            <Route path="/diary/:id" element={<Diary />} />
            <Route path="/edit/:id" element={<Edit />} />
            <Route path="/*" element={<NotFound />} />
          </Routes>
        </DiaryDispatchContext.Provider>
      </DiaryStateContext.Provider>
    </>
  );
}

export default App;
