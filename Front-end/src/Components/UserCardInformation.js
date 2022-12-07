import moment from 'moment';


const UserCardInformation = (user) => {
    const timeago = moment(user.user.user.created).fromNow();

    return <div className="user-card-information">
        <div className="text-center font-black text-red-600">ROLE: {user.user.user.roles[0].role}</div>
        <div className="flex flex-row mb-2">
            <div className="basis-2/12">
            <img src="https://www.multisignaal.nl/wp-content/uploads/2021/08/blank-profile-picture-973460_1280.png" alt="profile" className="profile-picture rounded-full" />
            </div>
            <div className="profile-div basis-9/12 ml-4">
                <h1 className='text-base'>username: {user.user.user.username}</h1>
                <h1 className='text-base'>name: {user.user.user.name}</h1>
                <h1 className='text-base'>joined: {timeago}</h1>
            </div>
        </div>
    </div>
}

export default UserCardInformation;