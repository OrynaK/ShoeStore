import React, { useEffect, useState } from "react";
import "./WorkerOrders.css";
import { useNavigate } from "react-router";

function WorkerOrders() {
    const [orders, setOrders] = useState([]);
    const role = JSON.parse(localStorage.getItem("user"))?.role;
    const userId = JSON.parse(localStorage.getItem("user"))?.id;
    const navigate = useNavigate();


    useEffect(() => {
        if (userId) {
            fetch(`http://localhost:8080/getOrdersByRole?role=${role}`)
                .then((response) => response.json())
                .then(data => {
                    setOrders(data);
                })
                .catch((error) => {
                    console.error("Error:", error);
                });
        }
    }, [userId]);


    function handleSetWorker(orderId, userId) {
        fetch(`http://localhost:8080/setWorker?orderId=${orderId}&userId=${userId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                if (response.ok) {
                    navigate("/myorders");
                } else {
                    throw new Error("Помилка при виконанні запиту");
                }
            })
            .catch((error) => {
                console.error("Помилка:", error);
            });
    }

    return (
        <div className="worker-orders">
            <table className="worker-orders-table">
                <thead>
                <tr>
                    <th className="worker-orders-table-th">Замовлення</th>
                    {role === "COURIER" &&(
                        <th className="worker-orders-table-th">Адреса доставки</th>
                    )}
                    <th className="worker-orders-table-th">Статус</th>
                    <th className="worker-orders-table-th">Взяти замовлення</th>
                </tr>
                </thead>
                <tbody>
                {orders.length > 0 ? (
                    orders.map((order) => (
                        <tr key={order.id}>
                            <td className="worker-orders-table-td">{order.id}</td>
                            {role === "COURIER" && (
                                <td className="my-orders-table-td">
                                    <tr>
                                        <td>{order.address.country}, м.{order.address.city},
                                            вул.{order.address.street}, буд.{order.address.houseNumber},
                                            п.{order.address.entrance}, кв. {order.address.apartmentNumber}</td>
                                    </tr>
                                </td>
                            )}
                            <td className="worker-orders-table-td">
                                <li>{order.status}</li>
                            </td>
                            <td className="worker-orders-table-td">

                                    <button
                                        className="worker-orders-table-btn-green"
                                        onClick={() => handleSetWorker(order.id, userId)}
                                    >
                                        Взяти замовлення
                                    </button>

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

export default WorkerOrders;

