
import {useState} from "react";
import "./LoginForm.css";
import React from "react";

function LoginForm() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [user, setUser] = useState('');
    const [error, setError] = useState('');

    function handleSubmit(event) {
        event.preventDefault();
        const param = {email, password};
        fetch("http://localhost:8080/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(param)
        }).then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error("Network response was not ok.");
        }).then((data) => {
            const emailValue = data.email ? data.email.trim() : '';
            const passwordValue = data.password ? data.password.trim() : '';
            if (!emailValue || !passwordValue) {
                setError('Wrong password or login. Try again');
            } else {
                setError('All ok. REDIRECTING');
               //ось тут треба додати в сесію користувача який прийшов в респонсі
            }
        }).catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
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
                    <input value={error} />

                    <button onClick={handleSubmit} type="submit">Log in</button>
                </form>
            </div>
        </div>
    )
}

export default LoginForm;