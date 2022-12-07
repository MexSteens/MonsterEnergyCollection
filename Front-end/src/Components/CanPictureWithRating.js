const CanPictureWithRating = ({can, review}) => {
    console.log(can);
    return <div className="can-picture-with-rating">
    <div className="flex flex-row my-4">
      <div className="basis-3/5"><img src={`../Images/${can.imagePath}`} alt="monster-energy-can" /></div>
      <div className="basis-2/5 relative">
        <div className="average-rating text-center"><h1>Average Rating:<br />{can.runningRating}</h1></div>
        <div className="country text-center absolute left-0 right-0"><h1>Country:<br />Germany</h1></div>
        <div className="your-rating text-center absolute bottom-0 left-0 right-0"><h1>Your Rating:<br /> {review[0]?.rating || "Loading..."}</h1></div>
      </div>
    </div>
    {/* <div className="flex flex-row">
      <div className="gap basis-full"></div>
    </div>
    {can && <CanName can={can} />} */}
  </div>
}

export default CanPictureWithRating;