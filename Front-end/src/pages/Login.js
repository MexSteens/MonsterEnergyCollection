import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import LoginRegisterServie from '../Services/LoginRegisterService';

const Login = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const handleInputChange = event => {
        const { name, value } = event.target;
        setUser({ ...user, [name]: value });
    };

    const initialUser = {
        username: "",
        password: ""
    } 

    const [user, setUser] = useState(initialUser)

    const login = () => {
        const data = {
            username: user.username,
            password: user.password
        }
        LoginRegisterServie.login(data)
        .then(response => {
            console.log(response.data);
            localStorage.setItem("jwt", response.data.accessToken);
            setUser(response.data);
            localStorage.setItem("userId", response.data.userId);
            navigation(response.data.role[0].role);
        })
        .catch(e => {
            displayErrorMessage(e)
            console.log(e);
        });
    }

    const navigation = (role) => {
        if (role == "USER") {
            navigate('/home')
        }
        else if (role == "MODERATOR") {
            navigate('/approval')
        }
        else if (role == "ADMIN") {
            console.log('navigating now')
            navigate('/roles')
        }

    }

    const [errorMessage, setErrorMessage] = useState('')

    const displayErrorMessage = (prop) => {
        if (user.username == '')
        {
            setErrorMessage("Username is not filled in, please fill in a username")
        }
        else if (user.password == '') {
            setErrorMessage("Password is not filled in, please fill in a password")
        }
        else if (prop.message = "Request failed with status code 400") {
            setErrorMessage("invalid credentials, please make sure you are using the right username with the corresponding password")
        }

    }

    return <div className="padding flex flex-col items-center justify-center px-6 py-8 mx-auto h-screen">
    <div class="w-full rounded-lg shadow border md:mt-0 sm:max-w-md xl:p-0 bg-black border-register-login">
            <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
                { location.state && <h1 name="register-confirm" class="text-xl text-center font-bold leading-tight tracking-tight md:text-2xl text-white">
                    {location.state.name}
                </h1> }
                { errorMessage && <h1 class="text-xl text-center font-bold leading-tight tracking-tight md:text-2xl text-red-600">
                    {errorMessage}
                </h1> }
                <h1 class="text-xl text-center font-bold leading-tight tracking-tight md:text-2xl text-white">
                    Login
                </h1>
                <div class="space-y-4 md:space-y-6">
                    <div>
                        <label for="username" class="block mb-2 text-sm font-medium  text-white">Your username</label>
                        <input value={user.username} onChange={handleInputChange} type="username" name="username" id="username" class="border sm:text-sm rounded-lg block w-full p-2.5 input-background placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500" placeholder="PickeRick" required="" />
                    </div>
                    <div>
                        <label for="password" class="block mb-2 text-sm font-medium  text-white">Password</label>
                        <input value={user.password} onChange={handleInputChange} type="password" name="password" id="password" placeholder="••••••••" class="border sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 input-background placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500" required="" />
                    </div>
                    <button onClick={login} name="login-button" type="button" class="w-full text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-lg px-4 py-2.5 text-center focus:ring-primary-800 general-button-selected">Login to your account</button>
                    <p class="text-sm font-light text-white">
                        Don't have an account? <a href="/register" class="font-medium hover:underline text-sky-400">Register here</a>
                    </p>
                </div>
            </div>
        </div>
        </div>
}

export default Login;