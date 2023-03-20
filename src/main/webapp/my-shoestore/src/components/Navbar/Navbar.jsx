
import "./Navbar.css"
import logo from "./../../assets/logo.png"

function Navbar () {


    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <img className="logo" src={logo} alt="logo"/>
                    <ul className="nav-list">
                        <li className="nav-list__item">Main</li>
                        <li className="nav-list__item">Shoes</li>
                        <li className="nav-list__item">Payment and Delivery</li>
                        <li className="nav-list__item">Contacts</li>
                        <li className="nav-list__item">Login/Registration</li>
                        <li className="nav-list__item">Cabinet</li>
                    </ul>
                </div>
            </div>
        </nav>

    );

}
export default Navbar;