import React, {useState} from 'react';
import './Basket.css';
import {Link} from "react-router-dom";

function Basket() {
    const orders = [
        { name: 'Air', color: 'black', size: '37', price: '350' },
        { name: 'adidas', color: 'black', size: '39', price: '300' },
        { name: 'nike', color: 'white', size: '37', price: '400' },
    ];
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
            {orders.map((order, index) => (
                <tr key={index}>
                    <td className="admin-orders-table-td">{order.name}</td>
                    <td className="admin-orders-table-td">{order.color}</td>
                    <td className="admin-orders-table-td">{order.size}</td>
                    <td className="admin-orders-table-td">{order.price}</td>
                    <button className="basket-table-btn-red">Видалити</button>
                </tr>

            ))}

            </tbody>
            <Link to={`/makeorder`}>
                <button className="basket-table-btn-green">Оформити замовлення</button>
            </Link>
        </table>

        </div>
    )
}

export default Basket;