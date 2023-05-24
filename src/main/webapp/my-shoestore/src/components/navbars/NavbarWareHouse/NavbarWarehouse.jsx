import React from "react";
import "./NavbarWarehouse.css"
import logo from "../../../assets/logo.png"
import {useNavigate} from "react-router";
import {NavLink} from "react-router-dom";

function NavbarWarehouse () {
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
                        <NavLink to="/warehouseorders" className="nav-list__item">
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
export default NavbarWarehouse;