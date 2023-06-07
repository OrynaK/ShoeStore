import React, {useEffect, useState} from "react";
import './MakeOrder.css';
import {useNavigate} from "react-router";

function MakeOrder() {
    const [country, setCountry] = useState('');
    const [city, setCity] = useState('');
    const [street, setStreet] = useState('');
    const [houseNumber, setHouseNumber] = useState('');
    const [entrance, setEntrance] = useState('');
    const [apartmentNumber, setApartmentNumber] = useState('');
    const [cardNumber, setCardNumber] = useState('');

    const [shoesInCart, setShoesInCart] = useState([]);
    const [inputErrors, setInputErrors] = useState({});

    const userId = JSON.parse(localStorage.getItem("user"))?.id;
    const navigate = useNavigate();
    const fetchShoesInCart = async () => {
        const response = await fetch(`http://localhost:8080/cart`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userId)
        });

        if (response.ok) {
            const data = await response.json();
            setShoesInCart(data);
        } else {
            console.log('Error while fetching shoes in cart');
        }
    };

    useEffect(() => {
        fetchShoesInCart().then(() => console.log('Shoes in cart fetched'));
    }, [userId]);

    const handleSubmit = event => {
        event.preventDefault();

        if(!cardNumber) {
            setInputErrors(prevErrors => ({
                ...prevErrors,
                cardNumber: "Поле обов'язкове для заповнення"
            }));
        }

        const makeOrderDTO = {
            userId: userId,
            country: country,
            city: city,
            street: street,
            houseNumber: houseNumber,
            entrance: entrance,
            apartmentNumber: apartmentNumber,

            shoeOrder: shoesInCart.map(shoe => ({
                id: shoe.id,
                price: shoe.price,
                amount: shoe.amount
            }))
        };

        fetch("http://localhost:8080/makeOrder", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(makeOrderDTO)
        }).then((response) => {
            if (response.ok) {
                alert("Order was created successfully");
                navigate('/myorders');
            } else if (response.status === 400) {
                response.json().then(data => {
                    setInputErrors(prevErrors => ({
                        ...prevErrors,
                        ...data,
                        cardNumber: "Поле обов'язкове для заповнення"
                    }));
                });
            }
        }).catch((error) => {
            console.log("Error while making order: " + error);
        });
    };

    return (
        <div className="make-order">
            <h1 className="make-order-header">Оформити замовлення</h1>
            <div className="make-order-form">
                <div className="basket">
                    <table className="make-order-table">
                        <thead>
                        <tr>
                            <th className="basket-table-th">Назва</th>
                            <th className="basket-table-th">Колір</th>
                            <th className="basket-table-th">Розмір</th>
                            <th className="basket-table-th">Кількість</th>
                            <th className="basket-table-th">Ціна</th>
                            <th className="basket-table-th"></th>
                        </tr>
                        </thead>
                        <tbody>
                        {shoesInCart.map((shoe, index) => (
                            <tr key={index}>
                                <td className="admin-orders-table-td">{shoe.name}</td>
                                <td className="admin-orders-table-td">{shoe.color}</td>
                                <td className="admin-orders-table-td">{shoe.size}</td>
                                <td className="admin-orders-table-td">{shoe.amount}</td>
                                <td className="admin-orders-table-td">{shoe.price}</td>
                                <td className="admin-orders-table-td">
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
                <form className="make-order-form--form"></form>
                <label className="make-order-form-label">
                    Номер картки</label>
                <input className="make-order-form-input"
                       type="text"
                       name="card"
                       value={cardNumber}
                       onKeyDown={(event) => {
                           if (!/\d/.test(event.key) && event.key !== "Backspace") {
                               event.preventDefault();
                           }
                       }}
                       onChange={event => setCardNumber(event.target.value)}
                />
                <div>
                    {inputErrors.cardNumber && <p className="input-error">{inputErrors.cardNumber}</p>}
                </div>
                <label className="make-order-form-label">
                    Країна</label>
                <input className="make-order-form-input"
                       type="text"
                       name="city"
                       value={country}
                       onChange={event => setCountry(event.target.value)}
                />
                <div>
                    {inputErrors.country && <p className="input-error">{inputErrors.country}</p>}
                </div>
                <label className="make-order-form-label">
                    Місто</label>
                <input className="make-order-form-input"
                       type="text"
                       name="city"
                       value={city}
                       onChange={event => setCity(event.target.value)}
                />
                <div>
                    {inputErrors.city && <p className="input-error">{inputErrors.city}</p>}
                </div>
                <label className="make-order-form-label">
                    Вулиця</label>
                <input className="make-order-form-input"
                       type="text"
                       name="street"
                       value={street}
                       onChange={event => setStreet(event.target.value)}
                />
                <div>
                    {inputErrors.street && <p className="input-error">{inputErrors.street}</p>}
                </div>
                <label className="make-order-form-label">
                    Номер будинку</label>
                <input className="make-order-form-input"
                       type="text"
                       name="house number"
                       value={houseNumber}
                       onChange={event => setHouseNumber(event.target.value)}
                />
                <div>
                    {inputErrors.houseNumber && (
                        <p className="input-error">{inputErrors.houseNumber}</p>
                    )}
                </div>
                <label className="make-order-form-label">
                    Під'їзд</label>
                <input className="make-order-form-input"
                       type="number"
                       name="entrance"
                       value={entrance}
                       min="1"
                       step="1"
                       onKeyDown={(event) => {
                           if (!/\d/.test(event.key) && event.key !== "Backspace") {
                               event.preventDefault();
                           }
                       }}
                       onChange={event => setEntrance(event.target.value)}
                />
                <div>
                    {inputErrors.entrance && (
                        <p className="input-error">{inputErrors.entrance}</p>
                    )}
                </div>
                <label className="make-order-form-label">
                    Квартира</label>
                <input className="make-order-form-input"
                       type="number"
                       name="apartment number"
                       value={apartmentNumber}
                       min="1"
                       step="1"
                       onKeyDown={(event) => {
                           if (!/\d/.test(event.key) && event.key !== "Backspace") {
                               event.preventDefault();
                           }
                       }}
                       onChange={event => setApartmentNumber(event.target.value)}
                />
                <div>
                    {inputErrors.apartmentNumber && (
                        <p className="input-error">{inputErrors.apartmentNumber}</p>
                    )}
                </div>
                <button className="make-order-form-btn" type="submit" onClick={handleSubmit}>Оплатити</button>
            </div>
        </div>
    )
}

export default MakeOrder;