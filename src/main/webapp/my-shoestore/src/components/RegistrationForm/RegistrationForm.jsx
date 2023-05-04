import React, {useState} from 'react';

import "./RegistrationForm.css"
import {useNavigate} from "react-router";

function RegistrationForm() {
    const[name, setName]=useState('')
    const[surname, setSurname]=useState('')
    const[email, setEmail]=useState('')
    const[phoneNumber, setPhoneNumber]=useState('')
    const[password, setPassword]=useState('')
    const[city, setCity]=useState('')
    const[address, setAddress]=useState('');
    const navigate = useNavigate();


    const handleSubmit = event => {
        event.preventDefault();
        const user = {name, surname, email, phoneNumber, password, city, address, role:'CLIENT'}
        fetch("http://localhost:8080/registration", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        }).then(() => {
            localStorage.setItem('user', JSON.stringify(user));
            window.dispatchEvent(new Event('storage'));
            navigate('/clientcabinet');
            }
        )
    };
    return (
        <div className="registration">
            <h1 className="registration-header">Реєстрація</h1>
            <div className="registration-form">

                <form className="registration-form--form" onSubmit={handleSubmit}>
                    <label className="registration-form-label">
                        First Name:</label>
                    <input className="registration-form-input"
                        type="text"
                        name="name"
                        value={name}
                        onChange={event => setName(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Last Name:</label>
                    <input className="registration-form-input"
                        type="text"
                        name="surname"
                        value={surname}
                        onChange={event => setSurname(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Email:</label>
                    <input className="registration-form-input"
                        type="email"
                        name="email"
                        value={email}
                        onChange={event => setEmail(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Phone Number:</label>
                    <input className="registration-form-input"
                        type="tel"
                        name="phoneNumber"
                        value={phoneNumber}
                        onChange={event => setPhoneNumber(event.target.value)}
                    />

                    <label className="registration-form-label">
                        Password:</label>
                    <input className="registration-form-input"
                        type="password"
                        name="password"
                        value={password}
                        onChange={event => setPassword(event.target.value)}
                    />

                    <button className="registration-form-btn" onClick={handleSubmit} type="submit">Submit</button>
                </form>
            </div>
        </div>
    );

}

export default RegistrationForm;