import React, {useEffect, useState} from "react";
import "./MyOrders.css";
function MyOrders() {
    const [orders, setOrders] = useState([]);
    const role = JSON.parse(localStorage.getItem("user"))?.role;
    const userId = JSON.parse(localStorage.getItem("user"))?.id;
    const [status, setStatus] = useState('');
    const [description, setDescription] = useState('');
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

    function handleStatusChange(orderId, selectedStatus) {
        const changeStatusDTO = {
            orderId: orderId,
            userId: userId,
            status: selectedStatus,
            description: description
        };

        fetch("http://localhost:8080/changeStatus", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(changeStatusDTO),
        })
            .then((response) => {
                if (response.ok) {
                    // Оновити статус у стані компонента або виконати інші дії
                    setStatus(selectedStatus);
                } else {
                    throw new Error("Помилка при виконанні запиту");
                }
            })
            .catch((error) => {
                console.error("Помилка:", error);
            });
    }


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
                        <th className="my-orders-table-th">Опис</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map((order) => (
                        <tr key={order.id}>
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
                                <button onClick={() => handleStatusChange(order.id, 'CANCELLED')} className="my-orders-table-btn-red">Відхилине</button>
                                {role === 'WAREHOUSE' ?
                                    ( <button  onClick={() => handleStatusChange(order.id, 'COMPILED')} className="my-orders-table-btn-green">Зібране</button>
                                    )
                                    : (<button onClick={() => handleStatusChange(order.id, 'READY_FOR_SENDING')} className="my-orders-table-btn-green">Упаковане</button>)}

                            </td>
                            <td className="my-orders-table-td">
                                <input className="registration-form-input"
                                       type="text"
                                       name="description"
                                       value={description}
                                       onChange={event => setDescription(event.target.value)}
                                />
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : role === 'COURIER' ? (
                <table className="my-orders-table">
                <thead>
                <tr>
                    <th className="my-orders-table-th">ID</th>
                    <th className="my-orders-table-th">Дата та час</th>
                    <th className="my-orders-table-th">Адреса</th>
                    <th className="my-orders-table-th">Статус</th>
                    <th className="my-orders-table-th">Змінити статус</th>
                    <th className="my-orders-table-th">Опис</th>
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
                            <button onClick={() => handleStatusChange(order.id, 'CANCELLED')} className="my-orders-table-btn-red">Відхилине</button>
                            <button onClick={() => handleStatusChange(order.id, 'DELIVERED')} className="my-orders-table-btn-green">Зібране</button>
                        </td>
                        <td className="my-orders-table-td">
                            <input className="registration-form-input"
                                   type="text"
                                   name="description"
                                   value={description}
                                   onChange={event => setDescription(event.target.value)}
                            />
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>) : (
                <table className="my-orders-table">

                    <thead>
                    <tr>
                        <th className="my-orders-table-th">ID</th>
                        <th className="my-orders-table-th">Дата та час</th>
                        <th className="my-orders-table-th">Взуття</th>
                        <th className="my-orders-table-th">Статус</th>
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

                        </tr>
                    ))}
                    </tbody>
                </table>
            )

            }


        </div>
    )
}
export default MyOrders;