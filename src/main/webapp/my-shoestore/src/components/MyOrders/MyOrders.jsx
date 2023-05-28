import React, {useEffect, useState} from "react";
import "./MyOrders.css";
function MyOrders() {
    const [orders, setOrders] = useState([]);
    const role = JSON.parse(localStorage.getItem("user"))?.role;
    const userId = JSON.parse(localStorage.getItem("user"))?.id;

    useEffect(() => {
        if(userId) {
            fetch(`http://localhost:8080/myOrders?userId=${userId}`)
                .then((response) => response.json())
                .then((data) => setOrders(data))
                .catch((error) => {
                    console.log(orders)
                    console.error("Error:", error);
                });
        }
    }, [userId]);
    const handleStatusChange = (index, newStatus) => {
        const updatedOrders = [...orders];
        updatedOrders[index].status = newStatus;
        setOrders(updatedOrders);
    };
    return (
        <div className="my-orders">

            <table className="my-orders-table">
                <thead>
                <tr>
                    <th className="my-orders-table-th">ID</th>
                    <th className="my-orders-table-th">Дата та час</th>
                    <th className="my-orders-table-th">Статус</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order, index) => (
                    <tr key={index}>
                        <td className="my-orders-table-td">{order.id}</td>
                        <td className="my-orders-table-td">{order.date}  {order.time}</td>
                        <td className="my-orders-table-td">
                            <li>
                                {order.status}
                            </li>
                            <ul>
                                <button className="my-orders-table-btn-green" onClick={() => handleStatusChange(index, 'Accepted')}>
                                    Прийняти замовлення
                                </button>
                                <button className="my-orders-table-btn-red" onClick={() => handleStatusChange(index, 'Rejected')}>
                                    Відхилити замовлення
                                </button>
                            </ul>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    )
}
export default MyOrders;