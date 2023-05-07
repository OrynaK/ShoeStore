import React from "react";
import "./ShoePage.css";
import sneaker from "./../../assets/sneaker.png"
function ShoePage(props) {
    const { id, name, price, image } = props;
    return (
        <div className="shoe-page">
            <h2 className="shoe-page-header">{name}</h2>
            <div className="shoe-page-form">
                <img src={sneaker} alt="sneaker"/>
                <div className="shoe-page-form-properties">
                    <div  className="shoe-page-form-sizes">
                        <span className="shoe-page-form-size">36</span>
                        <span className="shoe-page-form-size">37</span>
                        <span className="shoe-page-form-size">38</span>
                    </div>

                    <span className="shoe-page-form-price"><strong>{price}$</strong></span>
                </div>

                <button className="shoe-page-form-btn">Додати до кошика</button>
            </div>
        </div>
    )
}

export default ShoePage;