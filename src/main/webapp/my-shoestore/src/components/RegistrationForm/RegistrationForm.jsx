import React, {useState} from 'react';

import "./RegistrationForm.css"
import {useNavigate} from "react-router";

function RegistrationForm() {
    const [id, setId] = useState('')
    const [name, setName] = useState('')
    const [surname, setSurname] = useState('')
    const [email, setEmail] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [password, setPassword] = useState('')
    const [city, setCity] = useState('')
    const [address, setAddress] = useState('');
    const navigate = useNavigate();


    const handleSubmit = event => {
        event.preventDefault();
        const user = {id, name, surname, email, phoneNumber, password, city, address, role: 'CLIENT'}
        fetch("http://localhost:8080/registration", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        }).then(response => response.json())
            .then(data => {
                localStorage.setItem('user', JSON.stringify(data));
                window.dispatchEvent(new Event('storage'));
                navigate('/clientcabinet');
            })
            .catch(error => {
                console.error("There was a problem with the fetch operation:", error);
            });
    };

    return (
        <div className="registration">
            <h1 className="registration-header">Реєстрація</h1>
            <div className="registration-form">

                <form className="registration-form--form" onSubmit={handleSubmit}>
                    <label className="registration-form-label">
                        Ім'я</label>
                    <input className="registration-form-input"
                           type="text"
                           name="name"
                           value={name}
                           onChange={event => setName(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Прізвище</label>
                    <input className="registration-form-input"
                           type="text"
                           name="surname"
                           value={surname}
                           onChange={event => setSurname(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Електронна пошта</label>
                    <input className="registration-form-input"
                           type="email"
                           name="email"
                           value={email}
                           onChange={event => setEmail(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Номер телефону</label>
                    <input className="registration-form-input"
                           type="tel"
                           name="phoneNumber"
                           value={phoneNumber}
                           onChange={event => setPhoneNumber(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Пароль</label>
                    <input className="registration-form-input"
                           type="password"
                           name="password"
                           value={password}
                           onChange={event => setPassword(event.target.value)}
                    />

                    <button className="registration-form-btn" onClick={handleSubmit} type="submit">Зареєструватись
                    </button>
                </form>
            </div>
        </div>
    );

}

export default RegistrationForm;