import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar as faFullStar } from "@fortawesome/free-solid-svg-icons";
import { faThumbsUp } from "@fortawesome/free-regular-svg-icons";
import moment from 'moment';

const Review = ({comment}) => {
    const timeago = moment(comment.created).fromNow();

    return <div className="review-component">
        <div className="flex flex-row">
          <div className="small-gap basis-full"></div>
        </div>
    <div className="flex flex-row mt-2">
        <p className="text-lg" name="review"><span name="review-stars"><FontAwesomeIcon icon={faFullStar} className="full-star" /> {comment.reviewEntity.rating} </span> &nbsp;&nbsp; {comment.comment}</p>
    </div>
    <div className="flex flex-row mb-2">
        <div className="basis-1/12">
        <img src="https://www.multisignaal.nl/wp-content/uploads/2021/08/blank-profile-picture-973460_1280.png" alt="profile" className="profile-picture rounded-full" />
        </div>
        <div className="profile-div basis-8/12 ml-2">
        <h1 name="account">{comment.userEntity.name}</h1>
        <h3 className="text-sm">{timeago}</h3>
        </div>
        <div className="basis-2/12 ml-4">
        <h1 className="text-lg share-middle"><span><FontAwesomeIcon icon={faThumbsUp} className="text-2xl" /> 12</span></h1>
        </div>
    </div>
    <div className="flex flex-row">
          <div className="small-gap basis-full"></div>
        </div>
  </div>
}

export default Review;