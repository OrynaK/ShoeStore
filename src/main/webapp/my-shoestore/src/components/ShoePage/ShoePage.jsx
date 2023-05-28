import React, {useEffect, useState} from "react";
import "./ShoePage.css";
import {useLocation, useNavigate} from "react-router";

function ShoePage() {
    const {id, name, price, imageName} = useLocation().state || {};
    const [shoes, setShoes] = useState([]);
    const [cart, setCart] = useState([]);
    const [amounts, setAmounts] = useState({});
    const [errors, setErrors] = useState({}); // State to hold errors for each shoe block
    const userId = JSON.parse(localStorage.getItem("user"))?.id;

    const navigate = useNavigate();
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

    const handleSubmit = (shoeId, price, amount) => {
        const shoeCart = {
            userId: userId,
            shoeId: shoeId,
            price: price,
            amount: amount,
        };

        fetch("http://localhost:8080/addShoeToCart", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(shoeCart)
        })
            .then((response) => {
                if (response.ok) {
                    setErrors({}); // Clear errors if the request is successful
                    navigate("/basket");
                    return response.json();
                } else if (response.status === 409) {
                    // Handle shoe already in cart error
                    return response.json().then((data) => {
                        setErrors({[shoeId]: data}); // Set the error for the specific shoe block
                        throw new Error(JSON.stringify(data));
                    });
                } else {
                    throw new Error("An error occurred.");
                }
            })
            .then((data) => {
                setCart(data);
                console.log(data);
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
                    <img src={"./../images/" + imageName} alt="shoe" width="350px" height="250px"/>
                    <div className="shoe-page-form-properties">
                        <span className="shoe-page-form-size">{shoe.size}</span>
                        <span className="shoe-page-form-price"><strong>{shoe.price}$</strong></span>
                    </div>
                    <label className="registration-form-label">
                        Виберіть кількість</label>
                    <input
                        className="registration-form-input"
                        type="number"
                        name={`amount-${shoe.id}`}
                        value={amounts[shoe.id] || '1'}
                        onChange={event => {
                            const inputAmount = parseInt(event.target.value);
                            const newAmount = isNaN(inputAmount) || inputAmount < 1 ? 1 : inputAmount;
                            setAmounts(prevAmounts => ({
                                ...prevAmounts,
                                [shoe.id]: newAmount
                            }));
                        }}
                    />

                    {errors[shoe.id] && (<div>{errors[shoe.id].error}</div>)}
                    <button
                        className="shoe-page-form-btn"
                        onClick={() => handleSubmit(shoe.id, shoe.price, amounts[shoe.id] || '1')}
                    >
                        Додати до кошика
                    </button>
                </div>
            ))}

        </div>
    );
}

export default ShoePage;


