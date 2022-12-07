const CanName = (can) => {
    return <div className="name flex flex-row mb-2">
    <div className="basis-full mt-2">
      <h4 className="text-sm uppercase">{can.can.category}</h4>
      <h1 className="text-3xl">{can.can.name}</h1>
    </div>
    {/* <div className="share basis-1/12">
      <h4 className="text-xl share-middle"><FontAwesomeIcon icon={faShare}/></h4>
    </div> */}
  </div>
}

export default CanName;