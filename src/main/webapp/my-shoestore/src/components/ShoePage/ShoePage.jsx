import React, {useEffect, useState} from "react";
import "./ShoePage.css";
import sneaker from "./../../assets/sneaker.png"
import { useLocation, useParams } from "react-router";

function ShoePage() {
    const { id, name, price, image } = useLocation().state || {};
    const [shoeSizes, setShoeSizes] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/showShoePage?name=${name}`)
            .then((response) => response.json())
            .then((data) => {
                // Отримано дані з бекенду, обробка та встановлення у стан компонента
                const sizes = data.map((shoe) => shoe.size);
                setShoeSizes(sizes);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }, [name]);

    return (
        <div className="shoe-page">
            <h2 className="shoe-page-header">{name}</h2>
            <div className="shoe-page-form">
                <img src={sneaker} alt="sneaker" />
                <div className="shoe-page-form-properties">
                    <div className="shoe-page-form-sizes">
                        {shoeSizes.map((size) => (
                            <span key={size} className="shoe-page-form-size">{size}</span>
                        ))}
                    </div>
                    <span className="shoe-page-form-price"><strong>{price}$</strong></span>
                </div>
                <button className="shoe-page-form-btn">Додати до кошика</button>
            </div>
        </div>
    );
}

export default ShoePage;


