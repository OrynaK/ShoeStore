import "./NavbarAdmin.css"
import logo from "../../../assets/logo.png";
import {NavLink} from "react-router-dom";
function NavbarAdmin() {

    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <NavLink to="/main">
                    <img className="logo" src={logo} alt="logo"/>
                        </NavLink>
                    <ul className="nav-list">
                        <NavLink to="/main" className="nav-list__item">
                            Головна
                        </NavLink>
                        <NavLink to="/addNewShoe" className="nav-list__item">
                            Додати товар
                        </NavLink>
                        <NavLink to="/main" className="nav-list__item">
                            Видалити товар
                        </NavLink>
                        <NavLink to="/userslist" className="nav-list__item">
                            Список користувачів
                        </NavLink>
                        <NavLink to="/adminorders" className="nav-list__item">
                            Замовлення
                        </NavLink>
                        <NavLink to="/clientcabinet" className="nav-list__item">
                            Особистий кабінет
                        </NavLink>

                    </ul>
                </div>
            </div>
        </nav>

    );

}

export default NavbarAdmin;