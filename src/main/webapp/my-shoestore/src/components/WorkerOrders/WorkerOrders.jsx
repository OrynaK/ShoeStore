import React, {useState} from "react";
import "./WorkerOrders.css";
function WorkerOrders() {
    const [orders, setOrders] = useState([
        { client: 'John Doe', order: '1234', status: 'None' },
        { client: 'Jane Smith', order: '5678', status: 'None' },
        { client: 'Bob Johnson', order: '9101', status: 'None' },
    ]);

    const handleStatusChange = (index, newStatus) => {
        const updatedOrders = [...orders];
        updatedOrders[index].status = newStatus;
        setOrders(updatedOrders);
    };
    return (
        <div className="worker-orders">

            <table className="worker-orders-table">
                <thead>
                <tr>
                    <th className="worker-orders-table-th">Клієнт</th>
                    <th className="worker-orders-table-th">Замовлення</th>
                    <th className="worker-orders-table-th">Статус</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order, index) => (
                    <tr key={index}>
                        <td className="worker-orders-table-td">{order.client}</td>
                        <td className="worker-orders-table-td">{order.order}</td>
                        <td className="worker-orders-table-td">
                            <li>
                                {order.status}
                            </li>
                            <ul>
                                <button className="worker-orders-table-btn-green" onClick={() => handleStatusChange(index, 'Accepted')}>
                                    Прийняти замовлення
                                </button>
                                <button className="worker-orders-table-btn-red" onClick={() => handleStatusChange(index, 'Rejected')}>
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

export default WorkerOrders;
