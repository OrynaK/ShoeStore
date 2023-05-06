
import "./NavbarClient.css"
import logo from "../../../assets/logo.png"
import {NavLink} from "react-router-dom";

function NavbarClient () {


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

                        <NavLink to="/paymentanddelivery" className="nav-list__item">Оплата та доставка</NavLink>
                        <NavLink to="/contacts" className="nav-list__item">Контакти</NavLink>
                        <NavLink to="/loginform" className="nav-list__item">Вхід</NavLink>
                        <NavLink to="registrationform" className="nav-list__item">Реєстрація</NavLink>
                        <NavLink to="/clientcabinet" className="nav-list__item">Особистий кабінет</NavLink>
                        <NavLink to="/basket" className="nav-list__item">Кошик</NavLink>
                    </ul>
                </div>
            </div>
        </nav>

    );

}
export default NavbarClient;