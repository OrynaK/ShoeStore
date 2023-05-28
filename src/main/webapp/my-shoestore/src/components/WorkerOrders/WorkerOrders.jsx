import React, {useEffect, useState} from "react";
import "./WorkerOrders.css";
function WorkerOrders() {
    const [orders, setOrders] = useState([]);
    const role = JSON.parse(localStorage.getItem("user"))?.role;
    const userId = JSON.parse(localStorage.getItem("user"))?.id;

    useEffect(() => {
        if(userId) {
            fetch(`http://localhost:8080//getOrdersByRole?userRole=${role}`)
                .then((response) => response.json())
                .then((data) => setOrders(data))
                .catch((error) => {

                    console.error("Error:", error);
                });
        }
    }, [userId]);
    console.log(orders)

    function handleSetWorker(orderId, userId) {
        const setWorker = {
            orderId: orderId,
            userId: userId
        };

        fetch("http://localhost:8080/changeStatus", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(setWorker),
        })
            .then((response) => {
                if (response.ok) {

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
                    <th className="worker-orders-table-th">Статус</th>
                    <th className="worker-orders-table-th">Прийняти замовлення</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order) => (
                    <tr key={order.id}>

                        <td className="worker-orders-table-td">{order.id}</td>
                        <td className="worker-orders-table-td">
                            <li>
                                {order.status}
                            </li>
                        </td>
                        <td className="worker-orders-table-td">
                                <button className="worker-orders-table-btn-green" onClick={() => handleSetWorker(order.id, userId)}>
                                    Прийняти замовлення
                                </button>
                                <button className="worker-orders-table-btn-red" onClick={() => handleSetWorker(order.id, userId)}>
                                    Відхилити замовлення
                                </button>
                        </td>

                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    )
}

export default WorkerOrders;
