import React from "react";
import Comment from "./Comment";

const mockComments = [
  {
    name: "hhihi",
    description: "hihihi"
  },
  {
    name: "쿠앙쿠앙3465",
    description: "앙쿠346앙쿠"
  },
  {
    name: "모쿠모123쿠",
    description: "쿠모123쿠모"
  },
]

function CommentList(props) {
  return (
    <div>
      {
        mockComments.map(e => {
          return <Comment name={e.name} description={e.description} />
        })
      }
    </div>
  )
}

export default CommentList;