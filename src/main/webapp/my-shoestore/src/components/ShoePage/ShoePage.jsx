import React, {useEffect, useState} from "react";
import "./ShoePage.css";
import sneaker from "./../../assets/sneaker.png"
import {useLocation, useNavigate} from "react-router";

function ShoePage() {
    const {id, name, price, imageName} = useLocation().state || {};
    const [shoes, setShoes] = useState([]);
    const [cart, setCart] = useState([]);
    const [amount, setAmount] = useState('');
    const userId = JSON.parse(localStorage.getItem("user"))?.id;
    console.log(userId);
    const navigate = useNavigate();
    useEffect(() => {
        fetch(`http://localhost:8080/showShoePage?name=${name}`)
            .then((response) => response.json())
            .then((data) => {
                setShoes(data);
                console.log(data);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
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
                console.log(data);
                navigate('/basket');
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
                    <label className="registration-form-label">
                       Виберіть кількість</label>
                    <input className="registration-form-input"
                           type="amount"
                           name="amount"
                           value={amount}
                           onChange={event => setAmount(event.target.value)}
                    />
                    <button
                        className="shoe-page-form-btn"
                        onClick={() => handleSubmit(shoe.id, shoe.price, amount)}
                    >
                        Додати до кошика
                    </button>
                </div>
            ))}

        </div>
    );
}

export default ShoePage;


