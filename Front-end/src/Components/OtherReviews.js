import { useState } from "react";
import Review from "./Review";

const OtherReview = (otherComments) => {

    //recent comments
    const recentComments = otherComments.otherComments.slice().reverse();
    console.log(recentComments);

    const [popular, setPopular] = useState(true);
    const [recent, setRecent] = useState(false);
    const [popularSelected, setPopularSelected] = useState("general-button-selected text-black");
    const [recentSelected, setRecentSelected] = useState("general-button text-white");

    const popularFilter = () => {
        setRecent(false);
        setRecentSelected("general-button text-white")
        setPopular(true);
        setPopularSelected("general-button-selected text-black")
    }

    const recentFilter = () => {
        setPopular(false);
        setPopularSelected("general-button text-white")
        setRecent(true);
        setRecentSelected("general-button-selected text-black")
    }
    return <div className="other-reviews">
        <div className="flex flex-row your-review">
          <div className="basis-full mt-2">
            <h1 className="text-2xl text-center">Other reviews</h1>
          </div>
        </div>
        <div className="flex justify-center my-2">
            <div className="popular basis-2/12">
            <button onClick={popularFilter} className={popularSelected + " py-1 px-4 rounded-full text-sm"} id="popular">Popular</button>
            </div>
            <div className="basis-2"></div>
            <div className="recent basis-2/12">
            <button onClick={recentFilter} className={recentSelected + " py-1 px-4 bg-black rounded-full text-sm"} id="recent">Recent</button>
            </div>
        </div>
        {otherComments.otherComments[0] && popular && otherComments.otherComments.map((comment, i) => {return (<Review comment={comment}/>)})}
        {otherComments.otherComments[0] && recent && recentComments.map((comment, i) => {return (<Review comment={comment}/>)})}
        </div>
}

export default OtherReview;