import React from "react";
import {Link, NavLink} from "react-router-dom";
import "./ShoeCard.css";
import sneaker from "./../../assets/sneaker.png"
import {useNavigate} from "react-router";

function ShoeCard(props) {
    const { id, name, price, image } = props;


    return (
        <Link to={`/shoepage/${id}`} state={{ id, name, price, image }} className="shoe-card">
        <div className="shoe-card">
            <div className="shoe-card-form">
                <img className="shoe-card-form--img" src={sneaker} alt="shoe" width="170px" height="100px"/>
                <h3 className="shoe-card-form--header">{name}</h3>
                <span className="shoe-card-form--price"><strong>{price}$</strong></span>
            </div>
        </div>
        </Link>
    );
}

export default ShoeCard;
