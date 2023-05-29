
import React, {useState} from 'react';
import "./LoginForm.css";
import {useNavigate} from 'react-router';

function LoginForm() {
    const[email, setEmail]=useState('')
    const[password, setPassword]=useState('')
    const [error, setError] = useState('');


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
            }
            setError("Network response was not ok.");
        }).then((data) => {
            const emailValue = data.email ? data.email.trim() : '';
            const passwordValue = data.password ? data.password.trim() : '';
            if (!emailValue || !passwordValue) {
                setError('Wrong password or login. Try again');
            } else {
                localStorage.setItem('user', JSON.stringify(data));
                window.dispatchEvent(new Event('storage'));
               navigate('/clientcabinet');
            }
        }).catch((error) => {
            console.error("There was a problem with the fetch operation:", error);
        });
    }


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

                    <label className="login-form-label">
                        Пароль</label>
                    <input className="login-form-input"
                        type="password"
                        name="password"
                        value={password}
                        onChange={event => setPassword(event.target.value)}
                    />
                    {error && <div>{error}</div>}

                    <button className="btn-login" onClick={handleSubmit} type="submit">Увійти</button>
                </form>
            </div>
        </div>
    )
}

export default LoginForm;