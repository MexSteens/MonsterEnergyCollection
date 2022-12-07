import { faXmark } from "@fortawesome/free-solid-svg-icons";
import CircularSlider from '@fseehawer/react-circular-slider';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar as faFullStar } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import './Result.css';
import ReviewService from "../Services/ReviewService";
import scannedEnergyCanService from "../Services/ScannedEnergyCanService";
import CommentService from "../Services/CommentService"
import { useState, useEffect } from "react";
import ScannerService from "../Services/ScannerService";
import { useParams} from 'react-router-dom';
import { useNavigate } from "react-router-dom";
import PostNewCan from "../Components/PostNewCan";





const Result = () => {
  const navigate = useNavigate();
  //can get
  const { barcode }= useParams();
  const [loaded, setLoaded] = useState(true);

  const initialCanState = {
    id: null,
    title: "",
    description: "",
  };
  const [currentCan, setCurrentCan] = useState(initialCanState);

  const getCan = barcode => {
    ScannerService.get(barcode)
      .then(response => {
        setCurrentCan(response.data);
        console.log(response.data);
        setLoaded(true);
      })
      .catch(e => {
        setLoaded(false);
        console.log(e);
      });
  };

  useEffect(() => {
    if (barcode)
      getCan(barcode);
  }, [barcode]);

  // Review create  
  const initialReviewState = {
    id: null,
    rating: 0,
    userEntity: {
      id:  localStorage.getItem('userId')
    },
    energyCanEntity: currentCan
  };

  const [review, setReview] = useState(initialReviewState);
  const [submitted, setSubmitted] = useState(false);

  const saveReview = () => {
    var data = {
      rating: review.rating,
      userEntity: {
        id:  localStorage.getItem('userId')
      },
      energyCanEntity: currentCan
    };

    ReviewService.create(data)
      .then(response => {
        setReview({
          id: response.data.id,
          rating: response.data.rating,
          userEntity: response.data.userEntity,
          energyCanId: response.data.energyCanId
        });
        console.log("review " + review.id)
        console.log("response " + response.data.id)
        var commentdata = {
          comment: comment.comment,
          userEntity: {
            id:  localStorage.getItem('userId')
          },
          approved: 0,
          deleted: 0,
          energyCanEntity: currentCan,
          reviewEntity: {
            id: response.data.id
          }
        };
        saveComment(commentdata)
        setSubmitted(true);
        console.log(response.data);

      })
      .catch(e => {
        console.log(e);
      });
  };

  //Comment create
  const initialCommentState = {
    comment: "",
    approved: 0,
    deleted: 0,
    userEntity: {
      id:  localStorage.getItem('userId'),
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

  const saveComment = (data) => {
    CommentService.create(data)
      .then(response => {
        setComment({
          comment: response.data.comment,
          userEntity: response.data.userEntity,
          approved: response.data.approved,
          deleted: response.data.deleted,
          created: response.data.created,
          energyCanEntity: response.data.energyCanEntity,
          reviewEntity: response.data.reviewEntity
        });
        setSubmitted(true);
        console.log(response.data);
        navigate("/can/" + currentCan.id)
      })
      .catch(e => {
        console.log(e);
      });
  };

  const initialScannedEnergyCan = {
    energyCanEntity: currentCan,
    userEntity: {
      id: localStorage.getItem("userId")
    }
  }

  const [scannedEnergyCan, setScannedEnergyCan] = useState(initialScannedEnergyCan)

  const saveScannedEnergyCan = () => {
    const data = {
      energyCanEntity: currentCan,
      userEntity: {
        id: localStorage.getItem("userId")
      }
    }
    scannedEnergyCanService.create(data)
      .then(response => {
        setScannedEnergyCan(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  const onClick = () => {
    saveReview();
    saveScannedEnergyCan()
  }


  return <div className="container flex flex-col h-screen submit-form">
    <div className="padding p-4">
      {!loaded && <PostNewCan barcode={barcode} />}
      {loaded && <div className="rating-comment">
    <div className="flex flex-row">
      <div className="basis-2/12"><Link to="/scanner"><FontAwesomeIcon icon={faXmark}  className="text-2xl"/></Link></div>
      <div className="basis-8/12"></div>
      <div className="basis-2/12"><button name="post" class="bg-white font-bold text-black py-1 px-4 rounded-full text-sm" onClick={onClick}>Done</button></div>
    </div>
      <div className="flex flex-row mt-8">
        <div className="basis-3/5 opacity-20"><img src={`../Images/${currentCan.imagePath}`} alt="monster-energy-can" /></div>
        <div className="basis-2/5">
          <div className="average-rating mb-12 text-center opacity-20"><h1>Give rating to see what other people rated</h1></div>
          <div className="country mb-12 text-center opacity-20"><h1>Country: Germany</h1></div>
          <div className="your-rating text-center opacity-20">Give rating to see your rating</div>
        </div>    
      </div>
      <div className="rating-circle-div bg-white text-center"><div className="slider"><CircularSlider
                                        label="Rating"
                                        width={300}                        
                                        dataIndex={10}
                                        data={['0.0', 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, '1.0', 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, '2.0', 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, '3.0', 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9, '4.0', 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8, 4.9, '5.0']}
                                        labelColor="#fff"
                                        knobColor="#ec9706"
                                        knobSize={40}
                                        progressColorFrom="#a15400"
                                        progressColorTo="#ff8500"
                                        progressSize={15}
                                        trackColor="#eeeeee"
                                        trackSize={15}
                                        onChange={value => {review.rating = value; }}
                                        ><FontAwesomeIcon icon={faFullStar} x='7' y='6' width='25px' height='25px'/></CircularSlider></div></div>
      <div className="comment-box">
        <h1 className="text-2xl text-center">Leave your review</h1>
        <input type="text" id="large-input" class="block p-4 w-full text-gray-900 bg-gray-50 rounded-lg border border-gray-300 sm:text-md focus:ring-blue-500 focus:border-blue-500" value={comment.comment} onChange={handleInputChange} name="comment" />
      </div>
    </div>}
    </div>
    {loaded && <div className="background-coloring"></div>}
    </div>
};

export default Result;