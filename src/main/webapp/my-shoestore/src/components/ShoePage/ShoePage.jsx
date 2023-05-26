import React, {useEffect, useState} from "react";
import "./ShoePage.css";
import sneaker from "./../../assets/sneaker.png"
import { useLocation, useParams } from "react-router";

function ShoePage() {
    const { id, name, price, image } = useLocation().state || {};
    const [shoes, setShoes] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/showShoePage?name=${name}`)
            .then((response) => response.json())
            .then((data) => {
                setShoes(data);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }, [name]);

    return (
        <div className="shoe-page">
            <h2 className="shoe-page-header">{name}</h2>

                        {shoes.map(shoe => (
                            <div key={shoe.id} className="shoe-page-form">
                                <img src={sneaker} alt="sneaker" />
                                <div className="shoe-page-form-properties">
                                            <span className="shoe-page-form-size">{shoe.size}</span>
                                    <span className="shoe-page-form-price"><strong>{shoe.price}$</strong></span>
                                </div>
                                <button className="shoe-page-form-btn">Додати до кошика</button>
                            </div>
                        ))}

        </div>
    );
}

export default ShoePage;


