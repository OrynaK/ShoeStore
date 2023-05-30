import React, {useState} from 'react';

import "./RegistrationForm.css"
import {useNavigate} from "react-router";

function RegistrationForm() {
    const [name, setName] = useState('')
    const [surname, setSurname] = useState('')
    const [email, setEmail] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [password, setPassword] = useState('')
    const [inputErrors, setInputErrors] = useState({});
    const navigate = useNavigate();

    const handleSubmit = event => {
        event.preventDefault();
        const userDTO = {name, surname, email, phoneNumber, password, role: 'CLIENT'}
        fetch("http://localhost:8080/registration", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(userDTO)
        }).then((response) => {
            if (response.ok) {
                return response.json().then((data) => {
                    if (data) {
                        localStorage.setItem('user', JSON.stringify(data));
                        window.dispatchEvent(new Event('storage'));
                        navigate('/clientcabinet');
                    } else {
                        console.error("Received undefined data.");
                    }
                });
            } else if (response.status === 400) {
                return response.json().then(data => {
                    setInputErrors(data);
                });
            } else {
                throw new Error("Network response was not ok.");
            }
        }).catch(error => {
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
                    <div>{inputErrors.name && <p className="input-error">{inputErrors.name}</p>}</div>

                    <label className="registration-form-label">
                        Прізвище</label>
                    <input className="registration-form-input"
                           type="text"
                           name="surname"
                           value={surname}
                           onChange={event => setSurname(event.target.value)}
                    />
                    <div>{inputErrors.surname && <p className="input-error">{inputErrors.surname}</p>}</div>

                    <label className="registration-form-label">
                        Електронна пошта</label>
                    <input className="registration-form-input"
                           type="email"
                           name="email"
                           value={email}
                           onChange={event => setEmail(event.target.value)}
                    />
                    <div>{inputErrors.email && <p className="input-error">{inputErrors.email}</p>}</div>

                    <label className="registration-form-label">
                        Номер телефону</label>
                    <input className="registration-form-input"
                           type="tel"
                           name="phoneNumber"
                           value={phoneNumber}
                           min="0"
                           step="1"
                           onKeyDown={(event) => {
                               if (!/\d/.test(event.key) && event.key !== "Backspace") {
                                   event.preventDefault();
                               }
                           }}
                           onChange={event => setPhoneNumber(event.target.value)}
                    />
                    <div>{inputErrors.phoneNumber && <p className="input-error">{inputErrors.phoneNumber}</p>}</div>

                    <label className="registration-form-label">
                        Пароль</label>
                    <input className="registration-form-input"
                           type="password"
                           name="password"
                           value={password}
                           onChange={event => setPassword(event.target.value)}
                    />
                    <div>{inputErrors.password && <p className="input-error">{inputErrors.password}</p>}</div>

                    <button className="registration-form-btn" onClick={handleSubmit} type="submit">Зареєструватись
                    </button>
                </form>
            </div>
        </div>
    );

}

export default RegistrationForm;