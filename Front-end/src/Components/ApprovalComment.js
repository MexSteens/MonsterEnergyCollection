import { useEffect } from "react";
import { useState } from "react";
import CommentService from "../Services/CommentService"
import ReviewWithoutLines from "./ReviewWithoutLines";
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
// Set the backend location
const ENDPOINT = "http://localhost:8080/ws";

const ApprovalComment = (comment) => {

    const initialCommentState = {
            id: null,
            approved: 1,
    }

    const [currentComment, setCurrentComment] = useState(initialCommentState)

    //update comment
    const approveComment = () => {
        CommentService.updateByApproval(comment.comment.id)
        .then(response => {
            setCurrentComment(response.data)
            sendMessage(comment.comment)
            console.log(response.data);
            window.location.reload(true);
        })
        .catch(e => {
            console.log(e);
        });
    };

    //delete comment
    const removeComment = () => {
        CommentService.remove(comment.comment.id)
        .then(response => {
            console.log(response.data);
            setCurrentComment(null);
            window.location.reload(true);
        })
        .catch(e => {
            console.log(e);
        });
    };

    const [stompClient, setStompClient] = useState(null);

    function startConnection() {
        // use SockJS as the websocket client
        const socket = SockJS(ENDPOINT);
        // Set stomp to use websockets
        const stompClient = Stomp.over(socket);
        // connect to the backend
        stompClient.connect({}, () => {
            // subscribe to the backend
            stompClient.subscribe('/topic/greetings', (data) => {
            console.log(data);
            });
        });
        // maintain the client for sending and receiving
        setStompClient(stompClient);
    }

    // send the data using Stomp
    function sendMessage(prop) {
        stompClient.send("/app/hello", {}, JSON.stringify(prop));
    };

    useEffect(() => {
        startConnection();
    }, []) 

    return <div className="container-comment p-2 rounded-lg shadow border border-register-login bg-black" name={comment.comment.userEntity.username}>
        <ReviewWithoutLines comment={comment.comment}/>
        <div className="flex items-center justify-center py-2 rounded-b">
            <button onClick={approveComment} type="button" name="approve-comment" className="w-full text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-lg px-4 py-2 text-center focus:ring-primary-800 general-button-selected mr-2">
                Approve
            </button>
            <button onClick={removeComment} type="button" name="delete-comment" className="w-full text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-lg px-4 py-2 text-center focus:ring-primary-800 bg-red-600 ml-2">
                Remove
            </button>
        </div>
    </div>
}

export default ApprovalComment;