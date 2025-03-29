import { useEffect } from "react";

const Even = () => {
  useEffect(() => {
    return () => {
      console.log("unmount");
    };
  }, []);

  return <div>Even</div>;
};

export default Even;
