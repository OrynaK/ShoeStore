import "./ClientCabinet.css"
import React, {useState} from "react";

function ClientCabinet() {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem("user");
        return storedUser ? JSON.parse(storedUser) : null;
    });
    const [formData, setFormData] = useState({
        id: user && user.id,
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
        console.log(formData)
        fetch("http://localhost:8080/updateUserInfo", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(formData)
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data)
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
        const {name, value} = event.target;
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
                    <div className="cabinet-form--form">
                        <p className="cabinet-form-data">
                            Ім'я: {user && user.name}
                        </p>
                        <p className="cabinet-form-data">
                            Прізвище: {user && user.surname}
                        </p>
                        <p className="cabinet-form-data">
                            Номер телефону: {user && user.phoneNumber}
                        </p>
                        <p className="cabinet-form-data">
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
                </div>

            )}

        </div>


    );
}

export default ClientCabinet;




