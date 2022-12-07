import { faHouse, faBook, faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";

const Footer = () => {
    return <div class="footer-div columns-3 flex">
        <div class="flex-auto"><Link to="/home"><FontAwesomeIcon icon={faHouse} class="icon-footer" /> </Link></div>
        <div class="flex justify-center">
            <div class="footer-middle rounded-full w-24 h-24">
            <Link to="/scanner"><FontAwesomeIcon icon={faPlus} class="icon-footer-middle" /></Link>
            </div>
        </div>
        <div class="flex-auto"><Link to="/log"><FontAwesomeIcon icon={faBook} class="icon-footer"/></Link></div>
    </div>;
} 

export default Footer;