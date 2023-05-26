import React, {useEffect, useState} from 'react';
import './Basket.css';
import {Link} from "react-router-dom";

function Basket() {
    const [shoesInCart, setShoesInCart] = useState([]);
    const userId = JSON.parse(localStorage.getItem("user"))?.id;

    const fetchShoesInCart = async () => {
        const response = await fetch(`http://localhost:8080/cart`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
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


    return (
        <div className="basket">
            <h2 className="basket-header">Кошик</h2>
            <table className="basket-table">
                <thead>
                <tr>
                    <th className="basket-table-th">Назва</th>
                    <th className="basket-table-th">Колір</th>
                    <th className="basket-table-th">Розмір</th>
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
                        <td className="admin-orders-table-td">{shoe.price}</td>
                        <button className="basket-table-btn-red">Видалити</button>
                    </tr>
                ))}
                </tbody>
                <Link to={`/makeorder`}>
                    <button className="basket-table-btn-green">Оформити замовлення</button>
                </Link>
            </table>
        </div>
    );
}

export default Basket;
