import React, {useEffect, useState} from "react";
import "./ShoePage.css";
import {useLocation} from "react-router";

function ShoePage() {
    const {id, name, price, imageName} = useLocation().state || {};
    const [shoes, setShoes] = useState([]);
    const [cart, setCart] = useState([]);
    const userId = JSON.parse(localStorage.getItem("user"))?.id;
    useEffect(() => {
        fetch(`http://localhost:8080/showShoePage?name=${name}`)
            .then((response) => response.json())
            .then((data) => {
                setShoes(data);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
        console.log(imageName);
    }, [name]);

    const handleSubmit = (shoeId, price, amount) => {
        const shoeCart = {
            userId: userId,
            shoeId: shoeId,
            price: price,
            amount: amount
        };

        fetch("http://localhost:8080/addShoeToCart", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(shoeCart)
        })
            .then((response) => response.json())
            .then((data) => {
                setCart(data);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };


    return (
        <div className="shoe-page">
            <h2 className="shoe-page-header">{name}</h2>

            {shoes.map(shoe => (
                <div key={shoe.id} className="shoe-page-form">
                    <img src={"/./../images/" + {imageName}} alt="sneaker"/>
                    <div className="shoe-page-form-properties">
                        <span className="shoe-page-form-size">{shoe.size}</span>
                        <span className="shoe-page-form-price"><strong>{shoe.price}$</strong></span>
                    </div>
                    <button
                        className="shoe-page-form-btn"
                        onClick={() => handleSubmit(shoe.id, shoe.price, 1)}
                    >
                        Додати до кошика
                    </button>
                </div>
            ))}

        </div>
    );
}

export default ShoePage;


