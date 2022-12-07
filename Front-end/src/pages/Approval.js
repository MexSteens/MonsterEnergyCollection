import { useEffect } from "react";
import { useState } from "react";
import ApprovalComment from "../Components/ApprovalComment";
import CommentService from "../Services/CommentService";

const Approval = () => {
    const initialCommentState = {
        id: null,
        created: "",
        comment: "",
        approved: 1,
        deleted: 0,
        userEntity: {
          id: localStorage.getItem('userId'),
          name: ""
        },
        energyCanEntity: {},
        reviewEntity: {}
    }
    

    const [comment, setComment] = useState(initialCommentState);

    const findByApproval = () => {
        CommentService.findByApproval()
          .then(response => {
            setComment(response.data);
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
            window.location.reload(true);
          });
      };

    useEffect(() => {
        findByApproval();
    }, [])  

    return <div className="container flex flex-col h-screen">
    <div className="padding p-4">
        <div className="header">
            <h1 className="text-white text-2xl text-center mb-8">The following comments / cans need your approval</h1>
        </div>
        <div className="comments-and-cans">
            {comment[0] && comment.map((com, i) => {return (<ApprovalComment comment={com} />)})}
        </div>
        </div>
        </div>
}

export default Approval;