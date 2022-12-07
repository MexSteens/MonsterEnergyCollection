import { useState, useEffect } from "react";
import UserCard from "../Components/UserCard";
import UserService from "../Services/UserService";

const Roles = () => {
  const initialUserState = {
        id: null,
        created: "",
        name: "",
        username: "",
        roles: {
            role: ""
        }
    }

    const [user, setUser] = useState(initialUserState);

    const getAllUsers = () => {
        UserService.getAll()
        .then(response => {
            setUser(response.data);
            console.log(response.data)
        })
        .catch(e => {
            console.log(e);
            window.location.reload();
        })
    }

    useEffect(() => {
      getAllUsers()
    }, []) 
    

    const [searchUser, setSearchUser] = useState("");

    const onChangeSearchTitle = e => {
        const searchUser = e.target.value;
        setSearchUser(searchUser);
    };

    const findByTitle = () => {
        UserService.findByUsername(searchUser)
          .then(response => {
            setUser(response.data);
            console.log(response.data);
          })
          .catch(e => {
            console.log(e);
          });
    };



    return <div className="container flex flex-col">
    <div className="padding p-4">
        <div className="header">
            <h1 className="text-white text-2xl text-center mb-8">You can change the permissions of the following users</h1>
        </div>
        <div className="search-field flex flex-row mb-3">
            <div className="search-bar basis-10/12">
          <input
            type="text"
            className="border w-full py-2 rounded-tl-lg rounded-bl-lg px-2 text-black focus:ring-blue-500 focus:border-blue-500"
            placeholder="Search by Username"
            value={searchUser}
            onChange={onChangeSearchTitle}
            name="searchbar"
          />
          </div>
          <div className="search-button basis-2/12">
            <button
              className="text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-tr-lg rounded-br-lg text-lg px-4 py-2 text-center focus:ring-primary-800 general-button-selected"
              type="button"
              onClick={findByTitle}
              name="search-button"
            >
              Search
            </button>
          </div>
        </div>
        <div className="users">
            {user[0] && user.map((users, i) => {return (<UserCard user={users} />)})}
        </div>
        </div>
        </div>
}

export default Roles;