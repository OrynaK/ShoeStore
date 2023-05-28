import React, {useEffect, useState} from "react";
import './MakeOrder.css';
import {Link} from "react-router-dom";
import {useNavigate} from "react-router";

function MakeOrder() {
    const [country, setCountry] = useState('');
    const [city, setCity] = useState('');
    const [street, setStreet] = useState('');
    const [houseNumber, setHouseNumber] = useState('');
    const [entrance, setEntrance] = useState('');
    const [apartmentNumber, setApartmentNumber] = useState('');
    const [shoesInCart, setShoesInCart] = useState([]);
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
        fetchShoesInCart().then(r => console.log('Shoes in cart fetched'));
    }, [userId]);

    const handleSubmit = event => {
        event.preventDefault();

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

        fetch("http://localhost:8080/makeorder", {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(makeOrderDTO)
        }).then((response) => {
            if (response.ok) {
                alert("Order was created successfully");
                navigate('/clientorders');
            }
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
                    Номер картки:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="card"
                />
                <label className="make-order-form-label">
                    Країна:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="city"
                       value={country}
                       onChange={event => setCountry(event.target.value)}
                />
                <label className="make-order-form-label">
                   Місто:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="city"
                       value={city}
                       onChange={event => setCity(event.target.value)}
                />
                <label className="make-order-form-label">
                    Вулиця:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="street"
                       value={street}
                       onChange={event => setStreet(event.target.value)}
                />
                <label className="make-order-form-label">
                    Номер будинку:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="house number"
                       value={houseNumber}
                       onChange={event => setHouseNumber(event.target.value)}
                />
                <label className="make-order-form-label">
                    Під'їзд:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="entrance"
                       value={entrance}
                       onChange={event => setEntrance(event.target.value)}
                />
                <label className="make-order-form-label">
                    Квартира:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="apartment number"
                       value={apartmentNumber}
                       onChange={event => setApartmentNumber(event.target.value)}
                />
                <button className="make-order-form-btn" type="submit" onClick={handleSubmit}>Оплатити</button>
            </div>
        </div>
    )
}

export default MakeOrder;