import "./NavbarCouirer.css"
import logo from "./../../assets/logo.png"

function NavbarCouirer () {


    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <img className="logo" src={logo} alt="logo"/>
                    <ul className="nav-list">
                        <li className="nav-list__item">Замовлення</li>

                    </ul>
                </div>
            </div>
        </nav>

    );

}
export default NavbarCouirer;