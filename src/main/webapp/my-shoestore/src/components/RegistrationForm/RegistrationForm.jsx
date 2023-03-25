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
            <h1>Registration</h1>
            <div className="registration-form">

                <form onSubmit={handleSubmit}>
                    <label>
                        First Name:</label>
                    <input
                        type="text"
                        name="name"
                        value={name}
                        onChange={event => setName(event.target.value)}
                    />

                    <label>
                        Last Name:</label>
                    <input
                        type="text"
                        name="surname"
                        value={surname}
                        onChange={event => setSurname(event.target.value)}
                    />

                    <label>
                        Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={email}
                        onChange={event => setEmail(event.target.value)}
                    />

                    <label>
                        Phone Number:</label>
                    <input
                        type="tel"
                        name="phoneNumber"
                        value={phoneNumber}
                        onChange={event => setPhoneNumber(event.target.value)}
                    />

                    <label>
                        Password:</label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={event => setPassword(event.target.value)}
                    />

                    {/*<label>*/}
                    {/*    City:</label>*/}
                    {/*<input*/}
                    {/*    type="text"*/}
                    {/*    name="city"*/}
                    {/*    value={city}*/}
                    {/*    onChange={event => setCity(event.target.value)}*/}
                    {/*/>*/}

                    {/*<label>*/}
                    {/*    Address:</label>*/}
                    {/*<input*/}
                    {/*    type="text"*/}
                    {/*    name="address"*/}
                    {/*    value={address}*/}
                    {/*    onChange={event => setAddress(event.target.value)}*/}
                    {/*/>*/}

                    <button onClick={handleSubmit} type="submit">Submit</button>
                </form>
            </div>
        </div>
    );

}

export default RegistrationForm;