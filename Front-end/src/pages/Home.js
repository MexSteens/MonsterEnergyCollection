import Footer from '../Components/Footer';
import "../App.css";
import { useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import ScannedEnergyCanService from '../Services/ScannedEnergyCanService';
import NewestReview from '../Components/NewestReview.js';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
// Set the backend location
const ENDPOINT = "http://localhost:8080/ws";

const Home = () => {
    const location = useLocation();

    const initialEnergyCan = {
      id: null,
      name: "",
      description: "",
      imagePath: "",
      category: ""
    }

    const intialScannedEnergyCan = {
      id: null,
      userEntity: {
        id: localStorage.getItem('userId')
      },
      energyCanEntity: initialEnergyCan,
      created: ""
    }

    const initialComment = {
      id: null,
      comment: "",
      userEntity: null,
      reviewEntity: null
    }
  
    const [scannedEnergyCan, setScannedEnergyCan] = useState([intialScannedEnergyCan])

    const [newComment, SetNewComment] = useState(initialComment)
    console.log(scannedEnergyCan[0]);

  
    const getScannedEnergyCanByUserId = (canprops) => {
      ScannedEnergyCanService.findByUser(canprops)
        .then(response => {
          setScannedEnergyCan(response.data);
          console.log(response.data);
          console.log(scannedEnergyCan[0]);
        })
        .catch(e => {
          console.log(e);
        });
    };

    const canprops = {
      userId: localStorage.getItem('userId')
    }


    const [stompClient, setStompClient] = useState(null);
    const [msgToSend, setSendMessage] = useState("Enter your message here!");


    useEffect(() => {
      getScannedEnergyCanByUserId(canprops)
      startConnection()
    }, []);

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
          onMessageReceived(data);
        });
      });
      // maintain the client for sending and receiving
      setStompClient(stompClient);
    }

    // send the data using Stomp
    function sendMessage() {
      stompClient.send("/app/hello", {}, JSON.stringify({'name': msgToSend}));

    };

    // display the received data
    const onMessageReceived = (data) =>
    {
      const result = JSON.parse(data.body);
      console.log(result);
      alert(result.comment)
      console.log(scannedEnergyCan[0]);
      Array.from(scannedEnergyCan).forEach(element => {
        if (element.energyCanEntity.id == result.energyCanEntity.id) {
          console.log(result);
          const comment = {
            id: result.id,
            comment: result.comment,
            energyCanEntity: result.energyCanEntity,
            reviewEntity: result.reviewEntity,
            userEntity: result.userEntity
          }
          SetNewComment(comment)
          console.log(newComment);
          console.log(comment);
        }
      });
    };

    // when changing end connection


    return <div className="outlet flex flex-col h-screen">
      <div className="padding p-4 flex-grow">
        { location.state && <h1 class="text-xl text-center font-bold leading-tight tracking-tight md:text-2xl text-white mb-4 text-green">
                    {location.state.name}
                </h1> }
      <h1 className="text-3xl font-bold underline text-center">Hey there!</h1>
      { !scannedEnergyCan[0] && <h1 className="text-base font-bold text-center">Thanks for using my app. You can get started by pressing the "+" button below and get your very first scan!</h1>}
      {newComment[0] && <NewestReview comment={newComment}/>}
      <input onChange={(event) => setSendMessage(event.target.value)} className="text-black"></input>
      <button  onClick={sendMessage}>Send Message</button>
    </div>
    <footer>
        <Footer />
      </footer>
    </div>;
  };
  
  export default Home;