import "./ClientCabinet.css"
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router";

function ClientCabinet() {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user');
        return storedUser ? JSON.parse(storedUser) : null;
    });
    const [formData, setFormData] = useState({
        name: user && user.name,
        surname: user && user.surname,
        phoneNumber: user && user.phoneNumber,
        email: user && user.email,
    });
    const [isEditing, setIsEditing] = useState(false); // додали новий стан isEditing

    const handleFormSubmit = (event) => {
        event.preventDefault();
        // Виконати запит до серверу з оновленими даними користувача
        // та оновити локальний стейт користувача
        fetch('http://localhost:8080/clientcabinet', {
            method: 'PATCH',
            body: JSON.stringify(formData),
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => response.json())
            .then((data) => {
                localStorage.setItem('user', JSON.stringify(data));
                setUser(data);
                setIsEditing(false); // закінчили редагування
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((formData) => ({
            ...formData,
            [name]: value,
        }));
    };

    return (
        <div className="cabinet">
            <h1 className="cabinet-header">Особистий кабінет</h1>
            <h2 className="cabinet-data">Особисті данні</h2>
            {isEditing ? ( // перевіряємо, чи користувач редагує дані
                    <form className="cabinet-form" onSubmit={handleFormSubmit}>
                        <input
                            className="cabinet-form-input"
                            type="text"
                            name="name"
                            placeholder="Ім'я"
                            value={formData.name}
                            onChange={handleInputChange}
                        />
                        <input
                            className="cabinet-form-input"
                            type="text"
                            name="surname"
                            placeholder="Прізвище"
                            value={formData.surname}
                            onChange={handleInputChange}
                        />
                        <input
                            className="cabinet-form-input"
                            type="text"
                            name="phoneNumber"
                            placeholder="Номер телефону"
                            value={formData.phoneNumber}
                            onChange={handleInputChange}
                        />
                        <input
                            className="cabinet-form-input"
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={formData.email}
                            onChange={handleInputChange}
                        />
                        <button type="submit" className="cabinet-form-btn" onClick={() => setIsEditing(false)}>
                            Зберегти зміни
                        </button>
                    </form>
            ) : (
                <div className="cabinet-form">
                    <p className="cabinet-form-input">
                        Ім'я: {user && user.name}
                    </p>
                    <p className="cabinet-form-input">
                        Прізвище: {user && user.surname}
                    </p>
                    <p className="cabinet-form-input">
                        Номер телефону: {user && user.phoneNumber}
                    </p>
                    <p className="cabinet-form-input">
                        Email: {user && user.email}
                    </p>
                    <button
                        className="cabinet-form-btn"
                        onClick={() => setIsEditing(true)}
                    >
                        Редагувати дані
                    </button>
                </div>
            )}
    <div className="cabinet-basket">
                 <h2 className="cabinet-basket-header">Кошик</h2>
                 <div className="basket-form">
                     <h3 className="basket-form-header">Кошик пустий!</h3>
                 </div>
             </div>
            </div>


    );
 }

     export default ClientCabinet;




