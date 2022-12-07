import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComment } from "@fortawesome/free-regular-svg-icons";
import { faStar as faFullStar } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import CommentService from "../Services/CommentService"

const YourReview = ({currentComment, review, currentCan}) => {

    //Delete and save your comment

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
        energyCanEntity: currentCan,
        reviewEntity: review
      };

    const [comment, setComment] = useState(initialCommentState);


    const handleInputChange = event => {
        const { name, value } = event.target;
        setComment({ ...comment, [name]: value });
    };

    //remove comment
    const removeComment = () => {
        CommentService.remove(currentComment[0].id)
        .then(response => {
            console.log(response.data);
            setComment(null);
            window.location.reload(true);
        })
        .catch(e => {
            console.log(e);
        });
    };

    //save comment
    const saveComment = () => {
        var data = {
        comment: comment.comment,
        userEntity: {
            id: localStorage.getItem('userId')
        },
        approved: 0,
        deleted: 0,
        energyCanEntity: currentCan,
        reviewEntity: review[0]
        };

        CommentService.create(data)
        .then(response => {
            console.log(response.data);
            setComment(response.data);
            console.log(comment);
            window.location.reload(true); //fix this
        })
        .catch(e => {
            console.log(e);
        });
    };

    //update comment
    const updateComment = () => {
        var data = {
        comment: comment.comment,
        userEntity: {
            id: localStorage.getItem('userId')
        },
        approved: 0,
        deleted: 0,
        energyCanEntity: currentCan,
        reviewEntity: review[0]
        };

        CommentService.update(currentComment[0].id, data)
        .then(response => {
            console.log(response.data);
            window.location.reload(true); //fix this
        })
        .catch(e => {
            console.log(e);
        });
    };

    //modal
    const [showModal, setShowModal] = useState(false);
  
    const deleteComment = () => {
        setShowModal(false);
        removeComment();
    }

    //edit
    const [editComment, setEditComment] = useState(false);

    return <div className="your-review-component">
    <div className="your-review flex flex-row">
      <div className="basis-full mt-2">
        <h1 className="text-2xl text-center">Your review</h1>
      </div>
    </div>
    {currentComment[0]?.deleted != 1 && currentComment[0] != null && (<div className="your-review-visible">
    <div className="flex justify-center my-2">
          <div className="edit basis-2/12">
            <button onClick={() => setEditComment(true)} class="general-button text-white py-1 px-4 bg-black rounded-full text-sm" name="edit">Edit</button>
          </div>
          <div className="basis-2"></div>
          <div className="delete basis-2/12">
            <button onClick={() => setShowModal(true)} class="general-button text-white py-1 px-4 bg-black rounded-full text-sm" name="delete">Delete</button>

            {/* Modal */}

            {showModal ? (
              <>
                <div
                  className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none"
                >
                  <div className="relative w-auto my-6 mx-auto max-w-3xl">
                    {/*content*/}
                    <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
                      {/*header*/}
                      {/*body*/}
                      <div className="relative p-6 flex-auto">
                        <p className="my-4 text-slate-500 text-lg leading-relaxed text-center">
                          Are you sure you want to<br />delete your comment?
                        </p>
                      </div>
                      {/*footer*/}
                      <div className="flex items-center justify-end p-6 border-t border-solid border-slate-200 rounded-b">
                      <button onClick={() => deleteComment()} type="button" name="confirm-delete" className="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                          Yes, I'm sure
                      </button>
                      <button onClick={() => setShowModal(false)} type="button" className="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-500 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600">No, cancel</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
              </>
            ) : null}

            {/* end Modal */}

          </div>
          <div className="basis-2"></div>
          <div className="status basis-5/12">
            {currentComment[0]?.approved == 1 && (<button class="general-button text-white py-1 px-4 bg-black rounded-full text-sm">Status: posted</button>)}
            {currentComment[0]?.approved == 0 && (<button class="general-button text-white py-1 px-4 bg-black rounded-full text-sm">Status: pending</button>)}
          </div>
    </div>
    {editComment == false && <div className="flex flex-row my-3">
      <div className="basis-full">
        <p className="text-lg"><span><FontAwesomeIcon icon={faFullStar} className="full-star" /> {review[0]?.rating || "Loading..."} </span> &nbsp;&nbsp; {currentComment[0]?.comment || "Loading..."}</p>
      </div>
    </div>}
    {editComment == true && <div className="submit-form">
    <div className="flex justify-center my-2 text-center">
        <h1>Edit your review</h1>
    </div>
    <div className="flex flex-col justify-center my-3">
    <input defaultValue={currentComment[0].comment} onChange={handleInputChange} name="comment" type="text" placeholder="Edit your review here" id="large-input" class="block p-4 w-full text-gray-900 bg-gray-50 rounded-lg border border-gray-300 sm:text-md focus:ring-blue-500 focus:border-blue-500" />
      <div className="mx-auto">
        <button onClick={() => setEditComment(false)} type="button" className="mt-2 bg-red-600 general-button-cancel text-black py-2 px-4 font-medium rounded-md text-md mr-0.5">Cancel edit</button>
        <button onClick={updateComment} type="button" name="submit-edit" className="mt-2 general-button-selected text-black py-2 px-4 font-medium rounded-md text-md ml-0.5">Edit review</button>
      </div>
    </div>
    </div> }
    </div>)}
    {currentComment[0]?.deleted == 1 || currentComment[0] == null && (<div className="your-review-notvisible">
    <div className="flex justify-center my-2 text-center">
        <h1>You haven't left a review yet...</h1>
    </div>
    <div className="flex flex-col justify-center my-3">
    <input value={comment.comment} onChange={handleInputChange} name="comment" type="text" placeholder="leave your review here" id="large-input" class="block p-4 w-full text-gray-900 bg-gray-50 rounded-lg border border-gray-300 sm:text-md focus:ring-blue-500 focus:border-blue-500" />
      <div className="mx-auto">
        <button onClick={saveComment} type="button" name="submit-comment" className="mt-2 general-button-selected text-black py-2 px-4 font-medium rounded-md text-md">Submit review</button>
      </div>
    </div>
    </div>)}
    </div>
}

export default YourReview;