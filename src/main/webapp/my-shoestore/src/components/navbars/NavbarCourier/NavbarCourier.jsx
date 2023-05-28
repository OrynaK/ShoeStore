import "./NavbarCourier.css"
import logo from "../../../assets/logo.png"
import {useNavigate} from "react-router";
import {NavLink} from "react-router-dom";
import React from "react";

function NavbarCourier () {
    const navigate = useNavigate();
    function handleSubmit() {
        localStorage.clear();

        navigate('/main'); // Перенаправлення на головну сторінку
        window.location.reload(); // Оновлення сторінки
    }


    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <NavLink to="/main">
                        <img className="logo" src={logo} alt="logo"/>
                    </NavLink>
                    <ul className="nav-list">
                        <NavLink to="/workerorders" className="nav-list__item">
                            Замовлення
                        </NavLink>
                        <NavLink to="/myOrders" className="nav-list__item">
                            Мої Замовлення
                        </NavLink>
                        <NavLink to="/clientcabinet" className="nav-list__item">
                            Особистий кабінет
                        </NavLink>
                        <button className="btn-exit" onClick={handleSubmit}>Вихід</button>
                    </ul>
                </div>
            </div>
        </nav>

    );

}
export default NavbarCourier;