import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowLeft, faStar as faFullStar, faX } from "@fortawesome/free-solid-svg-icons";
import { faStar, faUser } from "@fortawesome/free-regular-svg-icons";
import React, { useState, useEffect } from "react";
import { Link, useParams} from 'react-router-dom';
import ScannerService from "../Services/ScannerService";
import CommentService from "../Services/CommentService";
import ReviewService from "../Services/ReviewService";
import UserService from "../Services/UserService.js";
import './Scanner.css'
import Footer from '../Components/Footer';
import OtherReviews from "../Components/OtherReviews";
import CanPictureWithRating from '../Components/CanPictureWithRating';
import YourReview from "../Components/YourReview";
import CanName from "../Components/CanName";


const Can = props => {
  //Get can by id
  const { barcode }= useParams();

  const initialCanState = {
    id: null,
    name: "",
    description: "",
    imagePath: "",
    category: ""
  };
  const [currentCan, setCurrentCan] = useState(initialCanState);

  const getCan = barcode => {
    ScannerService.get(barcode)
      .then(response => {
        setCurrentCan(response.data);
        console.log(response.data);
        let othercommentprops = {
          energyCanId: response.data.id,
          userId: localStorage.getItem('userId'),
          approved: 1,
          deleted: 0
        };
        let commentprops = {
          energyCanId: response.data.id,
          userId: localStorage.getItem('userId'),
          deleted: 0
        };
        let reviewprops = {
          userId: localStorage.getItem('userId'),
          energyCanId: response.data.id
        };
        findByUserAndEnergyCan(commentprops);
        findByEnergyCan(othercommentprops);
        getReviewByUserAndEnergyCan(reviewprops);
      })
      .catch(e => {
        console.log(e);
      });
  };

  //Get review
  const initialReviewState = {
    rating: 0,
    userEntity: {
      id: localStorage.getItem('userId')
    },
    energyCanEntity: currentCan
  };

  const [review, setReview] = useState(initialReviewState);

  const getReviewByUserAndEnergyCan = reviewprops => {
    ReviewService.findByUserAndEnergyCan(reviewprops)
      .then(response => {
        setReview(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };

  const initialCommentState = {
    id: null,
    created: "",
    comment: "",
    approved: 0,
    deleted: 0,
    userEntity: {
      id: localStorage.getItem('userId'),
      name: ""
    },
    energyCanEntity: currentCan,
    reviewEntity: review
  };
  const [currentComment, setCurrentComment] = useState(initialCommentState);

  const findByUserAndEnergyCan = commentprops => {
    CommentService.findByUserAndEnergyCan(commentprops)
      .then(response => {
        setCurrentComment(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };


  //Get other comments
  const [otherComments, setOtherComments] = useState(initialCommentState);

  const findByEnergyCan = commentprops => {
    CommentService.findByEnergyCan(commentprops)
      .then(response => {
        setOtherComments(response.data);
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  };
  
  //Get user

  useEffect(() => {
    if (barcode)
      getCan(barcode);
  }, [barcode]);

  return (
    <div className="container flex flex-col">
      <div className="padding pt-4 px-4 pb-10 flex-grow">
        <div className="flex flex-row">
          <div className="basis-2/12"><Link to="/log" ><FontAwesomeIcon icon={faX} /></Link></div>
          <div className="basis-9/12"></div>
          <div className="basis-1/12"><FontAwesomeIcon icon={faStar} /></div>
        </div>
        {review[0] && <CanPictureWithRating review={review} can={currentCan} />}
      <div className="flex flex-row">
        <div className="gap basis-full"></div>
      </div>
      <CanName can={currentCan} />
      <div className="flex flex-row">
        <div className="gap basis-full"></div>
      </div>
      {review[0] && <YourReview currentComment={currentComment} review={review} currentCan={currentCan} />}
      <div className="flex flex-row">
        <div className="gap basis-full"></div>
      </div>
        {otherComments[0] && <OtherReviews otherComments={otherComments} />}
      </div>
      <footer>
        <Footer />
      </footer>
    </div>
  );
}

export default Can;