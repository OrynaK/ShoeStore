import React, {useEffect, useState} from "react";
import "./MyOrders.css";
function MyOrders() {
    const [orders, setOrders] = useState([]);
    const role = JSON.parse(localStorage.getItem("user"))?.role;
    const userId = JSON.parse(localStorage.getItem("user"))?.id;
    const [status, setStatus] = useState('');
    useEffect(() => {
        if(userId) {
            fetch(`http://localhost:8080/myOrders?userId=${userId}`)
                .then((response) => response.json())
                .then((data) => setOrders(data))
                .catch((error) => {

                    console.error("Error:", error);
                });
        }
    }, [userId]);
    console.log(orders)
    return (
        <div className="my-orders">
            {role === 'WAREHOUSE' || role === 'PACKER'  ? (
                <table className="my-orders-table">

                    <thead>
                    <tr>
                        <th className="my-orders-table-th">ID</th>
                        <th className="my-orders-table-th">Дата та час</th>
                        <th className="my-orders-table-th">Взуття</th>
                        <th className="my-orders-table-th">Статус</th>
                        <th className="my-orders-table-th">Змінити статус</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map((order, index) => (
                        <tr key={index}>
                            <td className="my-orders-table-td">{order.id}</td>
                            <td className="my-orders-table-td">
                                <td className="my-orders-table-td">
                                    {new Date(order.date).toLocaleDateString('uk-UA')} {new Date(`1970-01-01T${order.time}`).toLocaleTimeString('uk-UA')}
                                </td>
                            </td>
                            <td className="my-orders-table-td" colSpan={1}>
                                {order.shoesInOrder.map((shoe, index) => (
                                    <tr >
                                    <td key={index}>№{shoe.shoeId} {shoe.name} {shoe.color} {shoe.size}р - {shoe.amount}шт</td>
                                        </tr >
                                ))}
                            </td>
                            <td className="my-orders-table-td">
                                    {order.status}
                            </td>
                            <td className="my-orders-table-td">
                                <button className="my-orders-table-btn-red">Відхилине</button>
                                <button className="my-orders-table-btn-green">Зібране</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) :  (
                <table className="my-orders-table">
                <thead>
                <tr>
                    <th className="my-orders-table-th">ID</th>
                    <th className="my-orders-table-th">Дата та час</th>
                    <th className="my-orders-table-th">Адреса</th>
                    <th className="my-orders-table-th">Статус</th>
                    <th className="my-orders-table-th">Змінити статус</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order, index) => (
                    <tr key={index}>
                        <td className="my-orders-table-td">{order.id}</td>
                        <td className="my-orders-table-td">
                            <td className="my-orders-table-td">
                                {new Date(order.date).toLocaleDateString('uk-UA')} {new Date(`1970-01-01T${order.time}`).toLocaleTimeString('uk-UA')}
                            </td>
                        </td>
                        <td className="my-orders-table-td">
                            <li>{order.shoesInOrder.map((shoe, index) => (
                                <tr >
                                    <td key={index}>{order.address.country}, м.{order.address.city}, вул.{order.address.street}, буд.{order.address.houseNumber}, п.{order.address.entrance}, кв. {order.address.apartmentNumber}</td>
                                </tr >
                            ))}

                            </li>
                        </td>
                        <td className="my-orders-table-td">
                            <li>
                                {order.status}
                            </li>
                        </td>
                        <td className="my-orders-table-td">
                            <button className="my-orders-table-btn-red">Відхилине</button>
                            <button className="my-orders-table-btn-green">Зібране</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>)}


        </div>
    )
}
export default MyOrders;