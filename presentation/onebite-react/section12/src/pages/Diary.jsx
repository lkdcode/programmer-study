import { useParams, useSearchParams } from "react-router-dom";

const Diary = () => {
  const params = useParams();
  const [queryString, setQueryString] = useSearchParams();
  console.log(queryString);

  return <div>Diary: {params.id}</div>;
};

export default Diary;
