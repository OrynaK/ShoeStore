import "./ClientCabinet.css"
import {useEffect, useState} from "react";
import {useLocation} from "react-router";

function ClientCabinet() {

    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem('user');
        return storedUser ? JSON.parse(storedUser) : null;
    });


    return (
        <div className="cabinet">
            <h1 className="cabinet-header">Особистий кабінет</h1>
            <h2 className="cabinet-data">Особисті данні</h2>
            <h2 className="cabinet-basket-header">Кошик</h2>
        <div className="cabinet-form">
            <span className="cabinet-form-span">{user && user.name}</span>
            <span className="cabinet-form-span">{user && user.surname}</span>
            <span className="cabinet-form-span">Номер телефону</span>
            <span className="cabinet-form-span"><em>{user && user.phoneNumber}</em></span>
            <span className="cabinet-form-span">Email</span>
            <span className="cabinet-form-span">{user && user.email}</span>
            <span className="cabinet-form-span">Роль</span>
            <span className="cabinet-form-span"><em>{user && user.role}</em></span>
            <button className="cabinet-form-btn">Змiнити даннi</button>
        </div>
            <div className="cabinet-basket">
                <div className="basket-form">
                        <h3 className="basket-form-header">Кошик пустий!</h3>
                </div>

            </div>
            </div>

    )
}

export default ClientCabinet;