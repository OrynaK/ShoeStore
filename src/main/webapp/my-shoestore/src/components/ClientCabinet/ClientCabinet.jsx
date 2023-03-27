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
            <h1>Особистий кабінет</h1>
            <h2 className="cabinet-data">Особисті данні</h2>
            <h2 className="cabinet-basket-header">Кошик</h2>
        <div className="cabinet-form">

            <span>{user && user.name}</span>
            <span>{user && user.surname}</span>
            <span>Номер телефону</span>
            <span><em>+38 050 133 33 56</em></span>
            <span>Email</span>
            <span>Роль</span>
            <span><em>{user && user.role}</em></span>
            <span>Адреса</span>
            <span><em>Харкiв, вул. Х кв 13</em></span>
            <button>Змiнити даннi</button>

        </div>
            <div className="cabinet-basket">
                <div className="basket-form">
                        <h3>Кошик пустий!</h3>
                </div>

            </div>
            </div>

    )
}

export default ClientCabinet;