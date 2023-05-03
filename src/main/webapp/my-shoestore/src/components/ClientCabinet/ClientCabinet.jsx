import "./ClientCabinet.css"
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";

function ClientCabinet() {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem("user");
        return storedUser ? JSON.parse(storedUser) : null;
    });
    const [formData, setFormData] = useState({
        user_id: user && user.user_id,
        name: user && user.name,
        surname: user && user.surname,
        phoneNumber: user && user.phoneNumber,
        email: user && user.email,
        password: user && user.password
    });

    const [isEditing, setIsEditing] = useState(false);
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [error, setError] = useState('');

    const handleFormSubmit = (event) => {
        console.log(newPassword);

        if (oldPassword !== user.password) {
            setError('Wrong password');
            return;
        }
        if (newPassword !== '') {
            setFormData((prevFormData) => ({
                ...prevFormData,
                password: newPassword
            }));
        }
        fetch("http://localhost:8080/updateUserInfo", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        })
            .then((response) => response.json())
            .then((data) => {
                localStorage.setItem("user", JSON.stringify(data));
                setUser(data);
                setIsEditing(false);
                setError('');
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData((formData) => ({
            ...formData,
            [name]: value
        }));
        if (name === 'newPassword') {
            setNewPassword(value);
        }
        if (name === 'oldPassword') {
            setOldPassword(value);
        }
    };

    return (
        <div className="cabinet">
            <h1 className="cabinet-header">Особистий кабінет</h1>
            <h2 className="cabinet-data">Особисті данні</h2>
            {isEditing ? (
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
                    <input
                        className="cabinet-form-input"
                        type="password"
                        name="newPassword"
                        placeholder="Новий пароль"
                        onChange={handleInputChange}
                    />
                    <input
                        className="cabinet-form-input"
                        type="password"
                        name="oldPassword"
                        placeholder="Для підтвердження введіть старий пароль"
                        onChange={handleInputChange}
                    />
                    <button type="submit" className="cabinet-form-btn" onClick={() => {
                        handleFormSubmit();
                        setIsEditing(false);
                    }}>

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
                    {error && <div>{error}</div>}
                </div>
            )}
            <div className="cabinet-basket">
                <h2 className="cabinet-basket-header">Кошик</h2>
                <div className="basket-form">
                    <h3 className="basket-form-header">Кошик пустий!</h3>
                    <Link to={`/makeorder`}>
                        <button className="basket-form-btn">Оформити замовлення</button>
                    </Link>

                </div>
            </div>
        </div>


    );
}

export default ClientCabinet;




