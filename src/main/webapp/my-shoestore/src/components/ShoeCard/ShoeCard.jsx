import React from "react";
import "./ShoeCard.css";
import sneaker from "./../../assets/sneaker.png"

function ShoeCard() {


    return (
        <div className="shoe-card">
            <div className="shoe-card-form">
                <img className="shoe-card-form--img" src={sneaker} alt="shoe" width="170px" height="100px"/>
                <h3 className="shoe-card-form--header">Pidkraduli</h3>
                <span className="shoe-card-form--price"><strong>350$</strong></span>
            </div>
        </div>
    );
}

export default ShoeCard;