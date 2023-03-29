import React, {useState} from 'react';

import "./RegistrationForm.css"

function RegistrationForm() {
    const[name, setName]=useState('')
    const[surname, setSurname]=useState('')
    const[email, setEmail]=useState('')
    const[phoneNumber, setPhoneNumber]=useState('')
    const[password, setPassword]=useState('')
    const[city, setCity]=useState('')
    const[address, setAddress]=useState('');
    const handleClick = (e) => {
        e.preventDefault()
        const user = {name, surname, email, phoneNumber, password, city, address}
        console.log(user)
    }

    const handleSubmit = event => {
        event.preventDefault();
        const user = {name, surname, email, phoneNumber, password, city, address}
        fetch("http://localhost:8080/registration", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(user)
        }).then(() => {
                console.log("New student added")
            }
        )
    };
    return (
        <div className="registration">
            <h1 className="registration-header">Реєстрація</h1>
            <div className="registration-form">

                <form onSubmit={handleSubmit}>
                    <label>
                        First Name:</label>
                    <input className="registration-form-input"
                        type="text"
                        name="name"
                        value={name}
                        onChange={event => setName(event.target.value)}
                    />

                    <label>
                        Last Name:</label>
                    <input className="registration-form-input"
                        type="text"
                        name="surname"
                        value={surname}
                        onChange={event => setSurname(event.target.value)}
                    />

                    <label>
                        Email:</label>
                    <input className="registration-form-input"
                        type="email"
                        name="email"
                        value={email}
                        onChange={event => setEmail(event.target.value)}
                    />

                    <label>
                        Phone Number:</label>
                    <input className="registration-form-input"
                        type="tel"
                        name="phoneNumber"
                        value={phoneNumber}
                        onChange={event => setPhoneNumber(event.target.value)}
                    />

                    <label>
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