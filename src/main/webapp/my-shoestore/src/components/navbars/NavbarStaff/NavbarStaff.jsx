import "./NavbarStaff.css"
import logo from "../../../assets/logo.png"
import {useNavigate} from "react-router";
import {NavLink} from "react-router-dom";
import React from "react";

function NavbarStaff() {
    const navigate = useNavigate();
    const role = JSON.parse(localStorage.getItem("user"))?.role;
    function handleSubmit() {
        localStorage.clear();

        navigate('/main'); // Перенаправлення на головну сторінку
        window.location.reload(); // Оновлення сторінки
    }


    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <img className="logo" src={logo} alt="logo"/>
                    <ul className="nav-list">

                        <NavLink to="/workerorders" className="nav-list__item">
                            Доступні Замовлення
                        </NavLink>
                        <NavLink to="/myOrders" className="nav-list__item">
                            Мої Замовлення
                        </NavLink>
                        <NavLink to="/clientcabinet" className="nav-list__item">
                            Особистий кабінет
                        </NavLink>
                        {role === 'WAREHOUSE' ? (
                            <>
                                <NavLink to="/changeShoesAmount" className="nav-list__item">
                                    Змінити кількість взуття
                                </NavLink>
                                <button className="btn-exit" onClick={handleSubmit}>
                                    Вихід
                                </button>
                            </>
                        ) : (
                            <button className="btn-exit" onClick={handleSubmit}>
                                Вихід
                            </button>
                        )}


                    </ul>
                </div>
            </div>
        </nav>

    );

}

export default NavbarStaff;