import React, {useCallback, useEffect, useState} from 'react';
import './Basket.css';
import {Link} from "react-router-dom";

function Basket() {
    const [shoesInCart, setShoesInCart] = useState([]);
    const userId = JSON.parse(localStorage.getItem("user"))?.id;
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

    const deleteShoeFromCart = useCallback(async (shoeId) => {
        const response = await fetch(`http://localhost:8080/deleteShoeFromCart?userId=${userId}&shoeId=${shoeId}`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
        });

        if (response.ok) {
            // Оновлюємо список взуття у кошику після видалення
            fetchShoesInCart().then();
        } else {
            console.log('Error while deleting shoe from cart');
        }
    }, [userId]);

    return (
        <div className="basket">
            <h2 className="basket-header">Кошик</h2>
            {shoesInCart.length > 0 ? (
                <table className="basket-table">
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
                            <td className="basket-table-td">{shoe.name}</td>
                            <td className="basket-table-td">{shoe.color}</td>
                            <td className="basket-table-td">{shoe.size}</td>
                            <td className="basket-table-td">{shoe.amount}</td>
                            <td className="basket-table-td">{shoe.price}</td>
                            <td className="basket-table-td">
                                <button
                                    className="basket-table-btn-red"
                                    onClick={() => deleteShoeFromCart(shoe.id)}
                                >
                                    Видалити
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>

            ) : (
                <h2 className="basket-header-empty">Кошик пустий</h2>
            )}
            {shoesInCart.length > 0 && (
                <Link to={`/makeorder`}>
                    <button className="basket-table-btn-green">Оформити замовлення</button>
                </Link>
            )}
        </div>
    );

}

export default Basket;
