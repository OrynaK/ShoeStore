import React, {useState} from "react";
import "./CourierOrders.css";
function CourierOrders() {
    const [orders, setOrders] = useState([
        { client: 'John Doe', order: '1234', adress: 'Sumska 96', status: 'None' },
        { client: 'Jane Smith', order: '5678', adress: 'Sumska 96', status: 'None' },
        { client: 'Bob Johnson', order: '9101', adress: 'Sumska 96', status: 'None' },
    ]);

    const handleStatusChange = (index, newStatus) => {
        const updatedOrders = [...orders];
        updatedOrders[index].status = newStatus;
        setOrders(updatedOrders);
    };
    return (
        <div className="courier-orders">

            <table className="courier-orders-table">
                <thead>
                <tr>
                    <th className="courier-orders-table-th">Клієнт</th>
                    <th className="courier-orders-table-th">Замовлення</th>
                    <th className="courier-orders-table-th">Адреса</th>
                    <th className="courier-orders-table-th">Статус</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order, index) => (
                    <tr key={index}>
                        <td className="courier-orders-table-td">{order.client}</td>
                        <td className="courier-orders-table-td">{order.order}</td>
                        <td className="courier-orders-table-td">{order.adress}</td>
                        <td className="courier-orders-table-td">
                            <li>
                                {order.status}
                            </li>
                            <ul>
                                <button className="courier-orders-table-btn-green" onClick={() => handleStatusChange(index, 'Accepted')}>
                                    Прийняти замовлення
                                </button>
                                <button className="courier-orders-table-btn-red" onClick={() => handleStatusChange(index, 'Rejected')}>
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
export default CourierOrders;