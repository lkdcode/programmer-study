export const onCreate = (content, id) => {
  return {
    id: id,
    content: content,
    date: new Date(),
    isDone: false,
  };
};

export const onUpdate = (e, data) => {
  return data.map((item) =>
    item.id === e.target.value ? { ...item, isDone: !item.isDone } : item
  );
};

export const onDelete = (e, data) => {
  return data.map((item) => item.id !== e.target.value);
};
