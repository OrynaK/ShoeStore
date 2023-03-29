import React from "react";
import "./PaymentAndDelivery.css"

function PaymentAndDelivery() {

    return (
        <div className="payment-and-delivery">
            <h1 className="payment-and-delivery-header">Оплата та доставка</h1>
            <div className="payment-and-delivery-form">
                <h2 className="payment-and-delivery-form-header">Оплата</h2>
                <span>Оплата здійснюється <strong>безготівковим</strong> способом. Оплатити можна карткою Приват банку або Моно банку</span>
                <h2 className="payment-and-delivery-form-header">Доставка</h2>
                <span>Доставка здійснюється кур'єром прямо до вашої квартири. Вказуйте правильну адресу при оформлені замовлення</span>
                <h2 className="payment-and-delivery-form-header">Якщо є питтаня дзвоніть</h2>
                <a href="/" target="_blank">+38 050 873 91 44</a>
            </div>
        </div>
    );
}
export default PaymentAndDelivery;