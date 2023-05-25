import React, {useEffect, useState} from "react";
import "./ShoePage.css";
import sneaker from "./../../assets/sneaker.png"
import {useLocation} from "react-router";
function ShoePage() {

    const location = useLocation();
    const { id, name, price, image} = location.state || {};
    const [shoesData, setShoesData] = useState([]);


    useEffect(() => {
        fetch(`http://localhost:8080/showShoePage?name=${name}`)
            .then((response) => response.json())
            .then((data) => setShoesData(data))
            .catch((error) => {
                console.error("Error:", error);
            });

    }, []);
    return (

        <div className="shoe-page">
            <h2 className="shoe-page-header">{name}</h2>
            <div className="shoe-page-form">
                <img src={sneaker} alt="sneaker"/>
                <div className="shoe-page-form-properties">
                    <div  className="shoe-page-form-sizes">
                        {shoesData.map(shoe => (
                            <span key={shoe.id} className="shoe-page-form-size">{shoe.size}</span>
                        ))}

                    </div>

                    <span className="shoe-page-form-price"><strong>{price}$</strong></span>
                </div>

                <button className="shoe-page-form-btn">Додати до кошика</button>
            </div>
        </div>

    )
}

export default ShoePage;