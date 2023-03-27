import React from "react";
import "./NavbarWarehouse.css"
import logo from "../../../assets/logo.png"

function NavbarWarehouse () {


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
        /*top: 270px;*/
    );


}
export default NavbarWarehouse;