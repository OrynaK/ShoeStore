import React from "react";
import { Link } from "react-router-dom";
import "./ShoeCard.css";
import sneaker from "./../../assets/sneaker.png"

function ShoeCard(props) {
    const { id, name, price, image } = props;

    return (
        <Link to={`/shoe/${id}`} className="shoe-card">
            <div className="shoe-card-form">
                <img className="shoe-card-form--img" src={sneaker} alt="shoe" width="170px" height="100px"/>
                <h3 className="shoe-card-form--header">{name}</h3>
                <span className="shoe-card-form--price"><strong>{price}$</strong></span>
            </div>
        </Link>
    );
}

export default ShoeCard;
