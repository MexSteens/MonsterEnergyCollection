import { useState } from "react";
import UserCardInformation from "./UserCardInformation";
import UserService from "../Services/UserService";


const UserCard = (user) => {
    const initialUserState = user;

    const [currentUser, setCurrentUser] = useState(initialUserState);

    const updateUserRole = (role) => {
        UserService.updateUserRole(user.user.id, role)
        .then(response => {
            setCurrentUser(response.data);
            console.log(response.data);
            window.location.reload();
        })
        .catch(e => {
            console.log(e);
        })
    }

    
    const updateUserRoleUser = () => {
        updateUserRole('USER')
    }

    const updateUserRoleModerator = () => {
        updateUserRole('MODERATOR')
    }

    const updateUserRoleAdmin = () => {
        updateUserRole('ADMIN')
    }


    return <div className="user-card mb-2 p-2 rounded-lg shadow border border-register-login bg-black" name={user.user.username}>
        <UserCardInformation user={user} />
        <div className="flex items-center justify-center py-2 rounded-b">
            <button onClick={updateUserRoleUser} type="button" name="user-button" className="w-full text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-lg px-4 py-2 text-center focus:ring-primary-800 general-button-selected mr-2">
                USER
            </button>
            <button onClick={updateUserRoleModerator} type="button" name="moderator-button" className="w-full text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-lg px-4 py-2 text-center focus:ring-primary-800 bg-violet-600 mr-2">
                MODERATOR
            </button>
            <button onClick={updateUserRoleAdmin} type="button" name="admin-button" className="w-full text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-lg px-4 py-2 text-center focus:ring-primary-800 bg-sky-600">
                ADMIN
            </button>
        </div>
    </div>
}

export default UserCard;