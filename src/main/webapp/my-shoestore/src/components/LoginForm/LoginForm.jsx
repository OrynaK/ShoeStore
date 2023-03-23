
import {useState} from "react";
import "./LoginForm.css";
import React from "react";

function LoginForm() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    function handleSubmit(event) {
        event.preventDefault();
        // здесь можно добавить логику для проверки введенных данных в базе данных
        if (email === 'example@mail.com' && password === 'password') {
            setError('');
            // здесь можно добавить логику для успешного входа
        } else {
            setError('Wrong password or login. Try again');
        }
    }

    return (
        <div className="login">
            <h1>Log in</h1>
            <div className="login-form">

                <form onSubmit={handleSubmit}>
                    <label>
                        Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={email}
                        onChange={event => setEmail(event.target.value)}
                    />

                    <label>
                        Password:</label>
                    <input
                        type="password"
                        name="password"
                        value={password}
                        onChange={event => setPassword(event.target.value)}
                    />


                    <button onClick={handleSubmit} type="submit">Log in</button>
                </form>
            </div>
        </div>
    )
}

export default LoginForm;