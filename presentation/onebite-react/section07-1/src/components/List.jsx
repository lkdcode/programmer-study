import { useEffect } from "react";
import "../style/List.css";
import ListItem from "./ListItem";

const List = ({ todoList }) => {
  return (
    <section className="List">
      <h4>Todo List 🎯</h4>
      <input placeholder="검색어를 입력해주세요." />
      <div className="Item_Wrapper">
        {todoList.map((item, index) => {
          return <ListItem key={index} title={item.title} date={item.date} />;
        })}
      </div>
    </section>
  );
};

export default List;
