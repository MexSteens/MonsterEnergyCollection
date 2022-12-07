import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faStar as faFullStar } from "@fortawesome/free-solid-svg-icons"
import moment from "moment/moment";
import ReviewService from '../Services/ReviewService'
import { useEffect, useState } from "react";

const LogCard = (scannedEnergyCan) => {
    console.log(scannedEnergyCan);

    const initialReviewState = {
        rating: 0,
        userEntity: {
          id: localStorage.getItem('userId')
        },
        energyCanEntity: {
            id: null
        }
      };
    
      const [review, setReview] = useState(initialReviewState);

      const reviewprops = {
        energyCanId: scannedEnergyCan.scannedEnergyCan.energyCanEntity.id,
        userId: localStorage.getItem('userId')
      }
    
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

      useEffect(() => {
        getReviewByUserAndEnergyCan(reviewprops)
      }, [])

    const scannedOn = moment(scannedEnergyCan.scannedEnergyCan.created).format('DD-MM-YYYY')

    return <div className="logcard py-3 pr-1" name="logcard">
        <div className="flex flex-row">
            <div className="basis-3/12">
                <img src={`../Images/${scannedEnergyCan.scannedEnergyCan.energyCanEntity.imagePath}`} alt="energy-can" />
            </div>
            <div className="basis-7/12 parent">
                <div className="flex flex-col">
                    <h4 className="text-sm uppercase">{scannedEnergyCan.scannedEnergyCan.energyCanEntity.category}</h4>
                    <h1 className="text-xl">{scannedEnergyCan.scannedEnergyCan.energyCanEntity.name}</h1>
                    <h4 className="text-base scanned-date">Scanned on: {scannedOn}</h4>
                </div>
            </div>
            <div className="basis-2/12">
                <span className="rating p-0.5"><FontAwesomeIcon icon={faFullStar} className="star" /> { review[0] && review[0].rating }</span>
            </div>
        </div>
    </div>
}

export default LogCard