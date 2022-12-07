import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ScannerService from '../Services/ScannerService';

const PostNewCan = (barcode) => {
    const navigate = useNavigate();

    const initialCanState = {
        id: null,
        title: "",
        name: "",
        description: "",
        barcode: null
    };
      
    const [currentCan, setCurrentCan] = useState(initialCanState);

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentCan({ ...currentCan, [name]: value });
      };
    
    const postCan = () => {
        const data = {
            name: currentCan.name,
            barcode: barcode.barcode
        }
        ScannerService.create(data)
          .then(response => {
            setCurrentCan(response.data);
            console.log(response.data);
            navigate("/home", {state:{name:'The new can has been succesfully requested!'}})
          })
          .catch(e => {
            console.log(e);
          });
    };

    return <div className="post-new-can">
        <div className="head-information text-center mb-10">
            <h1>This can does not seem to be in our database, please give it a name and we will possibly approve the new can!</h1>
        </div>
        <div className="form">
            <div className="search-field flex flex-row">
                <div className="can-name basis-10/12">
            <input
                className="border w-full py-2 rounded-tl-lg rounded-bl-lg px-2 text-black focus:ring-blue-500 focus:border-blue-500"
                placeholder="Monster Energy Ultra Red"
                name="name"
                type="name"
                id="name"
                value={currentCan.name}
                onChange={handleInputChange}
            />
            </div>
            <div className="search-button basis-2/12">
                <button
                className="text-white focus:ring-4 focus:outline-none focus:ring-primary-300 font-bold rounded-tr-lg rounded-br-lg text-lg px-4 py-2 text-center focus:ring-primary-800 general-button-selected"
                type="button"
                onClick={postCan}
                name="create-can"
                >
                Create
                </button>
            </div>
            </div>
        </div>
    </div>
}

export default PostNewCan