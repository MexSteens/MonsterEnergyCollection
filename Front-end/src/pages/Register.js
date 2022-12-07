import './Log.css'
import LoginRegisterService from '../Services/LoginRegisterService'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const Register = () => {
    const navigate = useNavigate();

    const initialUserState = {
        name: "",
        username: "",
        password1: "",
        password2: "",
        created: ""
    }

    const handleInputChange = event => {
        const { name, value } = event.target;
        setUser({ ...user, [name]: value });
    };

    const [user, setUser] = useState(initialUserState)

    const [errorMessage, setErrorMessage] = useState([]);

    const saveComment = () => {
        var data = {
            name: user.name,
            username: user.username,
            password: user.password2,
        };

        LoginRegisterService.create(data)
        .then(response => {
            console.log(response.data);
            setUser(response.data);
            navigate('/login',{state:{name:'Your account is succesfully created! Please login to continue to the app'}})
        })
        .catch(e => {
            console.log(e);
            setErrorMessage(e.response.data[0].defaultMessage.split(','));
            console.log(errorMessage[0]);
        });
    };

    const submitForm = () => {
        setErrorMessage([]);
        if (user.name == "" || user.username == "" || user.password1 == "" || user.password2 == "") {
            setErrorMessage(["Not all fields are filled in"]);
        }
        else if (user.password1 != user.password2){
            setErrorMessage(["Passwords do not match"]);
        }
        else {
            saveComment();
        }
        
    }

    return <div className="padding flex flex-col items-center justify-center px-6 py-8 mx-auto h-screen">
    <div class="w-full rounded-lg shadow border md:mt-0 sm:max-w-md xl:p-0 bg-black border-register-login">
            <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
                <h1 class="text-xl font-bold text-center leading-tight tracking-tight md:text-2xl text-white">
                    Create an account
                </h1>
                <div class="space-y-4 md:space-y-6">
                    {errorMessage.map((errormessage) => <h1 class="errormessage-register text-sm text-center font-bold leading-tight tracking-tight md:text-2xl text-red-600">
                        {errormessage}
                    </h1>)}
                    {/* <div className="div">{errorMessage}</div> */}
                    <div>
                        <label for="name" class="block mb-2 text-sm font-medium  text-white">Your name</label>
                        <input value={user.name} onChange={handleInputChange} type="name" name="name" id="name" class="border sm:text-sm rounded-lg block w-full p-2.5 input-background placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500" placeholder="Rick Sanchez" required="" />
                    </div>
                    <div>
                        <label for="username" class="block mb-2 text-sm font-medium  text-white">Your username</label>
                        <input value={user.username} onChange={handleInputChange} type="username" name="username" id="username" class="border sm:text-sm rounded-lg block w-full p-2.5 input-background placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500" placeholder="PickleRick" required="" />
                    </div>
                    <div>
                        <label for="password" class="block mb-2 text-sm font-medium  text-white">Password</label>
                        <input value={user.password1} onChange={handleInputChange} type="password" name="password1" id="password" placeholder="••••••••" class="border sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 input-background placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500" required="" />
                    </div>
                    <div>
                        <label for="confirm-password" class="block mb-2 text-sm font-medium  text-white">Confirm password</label>
                        <input value={user.password2} onChange={handleInputChange} type="password" name="password2" id="confirm-password" placeholder="••••••••" class="border sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 input-background placeholder-gray-400 text-white focus:ring-blue-500 focus:border-blue-500" required="" />
                    </div>
                    <div class="flex items-start">
                        <div class="flex items-center h-5">
                          <input id="terms" type="checkbox" class="w-4 h-4 border rounded focus:ring-3 bg-gray-700 border-gray-600 focus:ring-primary-600 ring-offset-gray-800" required="" />
                        </div>
                        <div class="ml-3 text-sm">
                          <label for="terms" class="font-light text-white">I accept the <a class="font-medium hover:underline text-sky-400" href="http://bitly.ws/eZuG">Terms and Conditions</a></label>
                        </div>
                    </div>
                    <button onClick={submitForm} type="button" name="register-button" class="w-full text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-lg text-lg px-4 py-2.5 text-center focus:ring-primary-800 general-button-selected">Create an account</button>
                    <p class="text-sm font-light text-white">
                        Already have an account? <a href="/login" class="font-medium hover:underline text-sky-400">Login here</a>
                    </p>
                </div>
            </div>
        </div>
        </div>
}

export default Register;