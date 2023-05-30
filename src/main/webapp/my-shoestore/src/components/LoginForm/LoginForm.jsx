import React, {useEffect, useState} from 'react';
import "./LoginForm.css";
import {useNavigate} from 'react-router';

function LoginForm() {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('');
    const [inputErrors, setInputErrors] = useState({});


    const navigate = useNavigate();

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
            } else if (response.status === 400) {
                return response.json().then(data => {
                    setInputErrors(data);
                });
            } else {
                throw new Error("Network response was not ok.");
            }
        })
            .then((data) => {
                const emailValue = data.email ? data.email.trim() : '';
                const passwordValue = data.password ? data.password.trim() : '';

                if (!emailValue || !passwordValue) {
                    setError('Невірний логін або пароль');
                } else {
                    localStorage.setItem('user', JSON.stringify(data));
                    window.dispatchEvent(new Event('storage'));
                    navigate('/clientcabinet');
                }
            })
            .catch((error) => {
                console.error("There was a problem with the fetch operation:", error);
            });

    }

    useEffect(() => {
        setInputErrors(''); // Забрати помилку, коли вона не потрібна
        setError('');
    }, [email, password]);

    return (
        <div className="login">
            <h1 className="login-header">Вхід</h1>
            <div className="login-form">

                <form className="login-form--form">
                    <label className="login-form-label">
                        Email</label>
                    <input className="login-form-input"
                           type="email"
                           name="email"
                           value={email}
                           onChange={event => setEmail(event.target.value)}
                    />
                    <div>
                        {inputErrors.email && <p className="input-error">{inputErrors.email}</p>}
                    </div>
                    <label className="login-form-label">
                        Пароль</label>
                    <input className="login-form-input"
                           type="password"
                           name="password"
                           value={password}
                           onChange={event => setPassword(event.target.value)}
                    />
                    <div>
                        {inputErrors.password && <p className="input-error">{inputErrors.password}</p>}
                    </div>
                    {error && <p className="input-error">{error}</p>}

                    <button className="btn-login" onClick={handleSubmit} type="submit">Увійти</button>
                </form>
            </div>
        </div>
    )
}

export default LoginForm;