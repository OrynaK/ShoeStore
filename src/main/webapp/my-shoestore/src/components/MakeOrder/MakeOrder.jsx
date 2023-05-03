import React from "react";
import './MakeOrder.css';

function MakeOrder() {

    return (
        <div className="make-order">
            <h1 className="make-order-header">Оформити замовлення</h1>
            <div className="make-order-form">
                <form className="make-order-form--form"></form>
                <label className="make-order-form-label">
                    Номер картки:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="card"
                />
                <label className="make-order-form-label">
                   Місто:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="city"
                />
                <label className="make-order-form-label">
                    Вулиця:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="street"
                />
                <label className="make-order-form-label">
                    Номер будинку:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="house number"
                />
                <label className="make-order-form-label">
                    Під'їзд:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="entrance"
                />
                <label className="make-order-form-label">
                    Квартира:</label>
                <input className="make-order-form-input"
                       type="text"
                       name="apartment number"
                />
                <button className="make-order-form-btn" type="submit">Оплатити</button>
            </div>
        </div>
    )
}

export default MakeOrder;