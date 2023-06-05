import React, { useEffect, useState } from "react";
import "./AccountOrders.css";
import { useNavigate } from "react-router";

function AccountOrders() {
    const [orders, setOrders] = useState([]);
    useEffect(() => {
        fetch("http://localhost:8080/getAllOrders")
            .then((response) => response.json())
            .then((data) => setOrders(data))
            .catch((error) => {
                console.error("Error:", error);
            });
        console.log(orders);
    }, []);


    return (
        <div className="worker-orders">
            <table className="worker-orders-table">
                <thead>
                <tr>
                    <th className="worker-orders-table-th">Замовлення</th>
                    <th className="worker-orders-table-th">Час</th>
                    <th className="worker-orders-table-th">Адреса доставки</th>
                    <th className="worker-orders-table-th">Загальна вартість</th>
                    <th className="worker-orders-table-th">Задіяні користувачі</th>
                    <th className="worker-orders-table-th">Статус</th>
                </tr>
                </thead>
                <tbody>
                {orders.length > 0 ? (
                    orders.map((order) => (
                        <tr key={order.id}>
                            <td className="worker-orders-table-td">{order.id}</td>
                            <td className="my-orders-table-td">
                                {new Date(order.date).toLocaleDateString('uk-UA')} {order.time[0] + ":" + order.time[1] + ":" + +order.time[2]}
                            </td>
                            <td className="my-orders-table-td">
                                {order.address.country}, м.{order.address.city},
                                         {order.address.street}, буд.{order.address.houseNumber},
                                        п.{order.address.entrance}, кв. {order.address.apartmentNumber}

                            </td>
                            <td className="worker-orders-table-td">{order.totalCost}</td>
                            <td className="worker-orders-table-td">
                                {Object.entries(order.usersInOrder).map(([role, userOrder]) => (
                                    <tr>
                                        <td key={userOrder.userId}>
                                            {userOrder.userId} - {role} </td>
                                    </tr>

                                ))}
                            </td>

                            <td className="worker-orders-table-td">
                                <li>{order.status}</li>
                            </td>

                        </tr>
                    ))
                ) : (
                    <h2 className="worker-orders-table-header">Замовлень нема</h2>
                )}
                </tbody>
            </table>
        </div>
    );
}

export default AccountOrders;

