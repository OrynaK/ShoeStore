import React, {useEffect, useState} from "react";
import "./MyOrders.css";

function MyOrders() {
    const [orders, setOrders] = useState([]);
    const role = JSON.parse(localStorage.getItem("user"))?.role;
    const userId = JSON.parse(localStorage.getItem("user"))?.id;
    const [status, setStatus] = useState('');
    const [description, setDescription] = useState({});
    useEffect(() => {
        fetchMyOrders(); // Виклик функції при завантаженні компоненту або при зміні userId
    }, [userId]);

    function fetchMyOrders() {
        if (userId) {
            fetch(`http://localhost:8080/myOrders?userId=${userId}`)
                .then((response) => response.json())
                .then((data) => {
                        setOrders(data)
                    }
                )
                .catch((error) => {
                    console.error("Error:", error);
                });
            console.log(orders)
        }
    }

    function handleStatusChange(orderId, selectedStatus) {
        const orderDescription = description[orderId]; // Отримати опис для вказаного замовлення
        const changeStatusDTO = {
            orderId: orderId,
            userId: userId,
            status: selectedStatus,
            description: orderDescription // Використовувати опис для вказаного замовлення
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
                    setDescription(""); // Очистити опис після успішної зміни статусу
                    fetchMyOrders(); // Виклик функції оновлення замовлень після успішного виконання запиту
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
            {(role === 'WAREHOUSE' || role === 'PACKER') ? (
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

                    {
                        orders
                            .sort((a, b) => {
                                if ((a.status === 'ACCEPTED' && role === 'WAREHOUSE') || (a.status === 'COMPILED' && role === 'PACKER')) return -1;
                                if ((b.status === 'ACCEPTED' && role === 'WAREHOUSE') || (a.status === 'COMPILED' && role === 'PACKER')) return 1;
                                return 0;
                            })
                            .filter((order) => {
                                if (role === 'PACKER' && (order.status === 'COMPILED' || order.status === 'READY_FOR_SENDING')) {
                                    return true;
                                }
                                if (role === 'WAREHOUSE' && (order.status === 'ACCEPTED' || order.status === 'COMPILED')) {
                                    return true;
                                }

                                return false;
                            })
                            .map((order, index) => (

                                <tr key={order.id}>
                                    <td className="my-orders-table-td">{order.id}</td>
                                    <td className="my-orders-table-td">

                                        {new Date(order.date).toLocaleDateString('uk-UA')} {order.time[0] + ":" + order.time[1] + ":" + +order.time[2]}

                                    </td>
                                    <td className="my-orders-table-td" colSpan={1}>
                                        {order.shoesInOrder.map((shoe) => (
                                            <p key={shoe.shoeId}>
                                                №{shoe.shoeId} {shoe.name} {shoe.color} {shoe.size}р
                                                - {shoe.amount}шт
                                            </p>
                                        ))}
                                    </td>

                                    <td className="my-orders-table-td">
                                        {order.status}
                                    </td>
                                    <td className="my-orders-table-td">

                                        {(role === 'WAREHOUSE' && order.status === 'ACCEPTED') || (role === 'PACKER' && order.status === 'COMPILED') ? (
                                            <>
                                                <button onClick={() => handleStatusChange(order.id, 'CANCELLED')}
                                                        className="my-orders-table-btn-red">Відхилине
                                                </button>
                                                {role === 'WAREHOUSE' ? (
                                                    <button onClick={() => handleStatusChange(order.id, 'COMPILED')}
                                                            className="my-orders-table-btn-green">Зібране</button>
                                                ) : (
                                                    <button
                                                        onClick={() => handleStatusChange(order.id, 'READY_FOR_SENDING')}
                                                        className="my-orders-table-btn-green">Упаковане</button>
                                                )}
                                            </>
                                        ) : (
                                            <h4>Cтатус було змінено</h4>

                                        )}


                                    </td>

                                    {(order.status === 'CANCELLED') || (order.status === 'COMPILED' && role === 'WAREHOUSE') || (order.status === 'READY_FOR_SENDING' && role === 'PACKER') ? (
                                        role === 'WAREHOUSE' ? (

                                            <td className="my-orders-table-td">{order.usersInOrder.WAREHOUSE.description}</td>

                                        ) : (

                                            <td className="my-orders-table-td">{order.usersInOrder.PACKER.description}</td>

                                        )
                                    ) : (
                                        <input
                                            className="my-orders-form-input"
                                            type="text"
                                            name={`description-${order.id}`}
                                            value={description[order.id] || ''}
                                            onChange={event => {
                                                const inputDescription = event.target.value;
                                                setDescription(prevDescription => ({
                                                    ...prevDescription,
                                                    [order.id]: inputDescription
                                                }));
                                            }}
                                        />
                                    )}


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
                    {orders
                        .sort((a, b) => {
                            if (a.status === 'READY_FOR_SENDING') return -1;
                            if (b.status === 'READY_FOR_SENDING') return 1;
                            return 0;
                        })
                        .filter((order) => {
                            if (role === 'COURIER' && (order.status === 'READY_FOR_SENDING' || order.status === 'DELIVERED')) {
                                return true;
                            }

                            return false;
                        })
                        .map((order, index) => (
                            <tr>
                                <td className="my-orders-table-td">{order.id}</td>
                                <td className="my-orders-table-td">

                                    {new Date(order.date).toLocaleDateString('uk-UA')} {order.time[0] + ":" + order.time[1] + ":" + +order.time[2]}

                                </td>

                                <td className="my-orders-table-td">{order.address.country}, м.{order.address.city},
                                    вул.{order.address.street}, буд.{order.address.houseNumber},
                                    п.{order.address.entrance}, кв. {order.address.apartmentNumber}</td>

                                <td className="my-orders-table-td">

                                    {order.status}

                                </td>
                                <td className="my-orders-table-td">
                                    {order.status === 'CANCELLED' || order.status === 'DELIVERED' ? (
                                        <h4>Статус було змінено</h4>
                                    ) : (
                                        <>
                                            <button onClick={() => handleStatusChange(order.id, 'CANCELLED')}
                                                    className="my-orders-table-btn-red">Відхилине
                                            </button>
                                            <button onClick={() => handleStatusChange(order.id, 'DELIVERED')}
                                                    className="my-orders-table-btn-green">Доставлене
                                            </button>
                                        </>
                                    )}
                                </td>


                                {order.status === 'CANCELLED' || order.status === 'DELIVERED' ? (
                                    <td className="my-orders-table-td">{order.usersInOrder.COURIER.description}</td>

                                ) : (
                                    <input
                                        className="my-orders-form-input"
                                        type="text"
                                        name={`description-${order.id}`}
                                        value={description[order.id] || ''}
                                        onChange={event => {
                                            const inputDescription = event.target.value;
                                            setDescription(prevDescription => ({
                                                ...prevDescription,
                                                [order.id]: inputDescription
                                            }));
                                        }}
                                    />
                                )}

                                <td className="my-orders-table-td">

                                    {order.description}

                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>) : role === 'CLIENT' ? (
                <table className="my-orders-table">
                    <thead>
                    <tr>
                        <th className="my-orders-table-th">Дата та час</th>
                        <th className="my-orders-table-th">Взуття</th>
                        <th className="my-orders-table-th">Статус</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map((order) => (
                        <tr key={order.id}>
                            <td className="my-orders-table-td">
                                {new Date(order.date).toLocaleDateString('uk-UA')} {order.time[0] + ":" + order.time[1] + ":" + +order.time[2]}
                            </td>
                            <td className="my-orders-table-td" colSpan={1}>
                                {order.shoesInOrder.map((shoe) => (
                                    <p>
                                        №{shoe.shoeId} {shoe.name} {shoe.color} {shoe.size}р
                                        - {shoe.amount}шт
                                    </p>
                                ))}
                            </td>
                            <td className="my-orders-table-td">
                                {order.status}
                            </td>

                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <table className="my-orders-table">

                    <thead>
                    <tr>
                        <th className="my-orders-table-th">ID</th>
                        <th className="my-orders-table-th">Дата та час</th>
                        <th className="my-orders-table-th">Взуття</th>
                        <th className="my-orders-table-th">Адреса</th>
                        <th className="my-orders-table-th">Статус</th>
                        <th className="my-orders-table-th">Змінити статус</th>
                        <th className="my-orders-table-th">Опис</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders
                        .sort((a, b) => {
                            if (a.status === 'PROCESSING') return -1;
                            if (b.status === 'PROCESSING') return 1;
                            return 0;
                        })
                        .map((order) => (
                            <tr key={order.id}>
                                <td className="my-orders-table-td">{order.id}</td>
                                <td className="my-orders-table-td">

                                    {new Date(order.date).toLocaleDateString('uk-UA')} {order.time[0] + ":" + order.time[1] + ":" + +order.time[2]}

                                </td>
                                <td className="my-orders-table-td" colSpan={1}>
                                    {order.shoesInOrder.map((shoe) => (
                                        <p>
                                            №{shoe.shoeId} {shoe.name} {shoe.color} {shoe.size}р
                                            - {shoe.amount}шт
                                        </p>
                                    ))}
                                </td>

                                <td className="my-orders-table-td">{order.address.country}, м.{order.address.city},
                                    вул.{order.address.street}, буд.{order.address.houseNumber},
                                    п.{order.address.entrance}, кв. {order.address.apartmentNumber}
                                </td>

                                <td className="my-orders-table-td">
                                    {order.status}
                                </td>
                                <td className="my-orders-table-td">
                                    {order.status !== 'PROCESSING' ? (
                                        <h4>Cтатус було змінено</h4>
                                    ) : (
                                        <>
                                            <button onClick={() => handleStatusChange(order.id, 'CANCELLED')}
                                                    className="my-orders-table-btn-red">Відхилине
                                            </button>

                                            <button onClick={() => handleStatusChange(order.id, 'ACCEPTED')}
                                                    className="my-orders-table-btn-green">Прийняте
                                            </button>

                                        </>
                                    )}


                                </td>

                                {order.status !== 'PROCESSING' ? (

                                    <td className="my-orders-table-td">{order.usersInOrder.ADMIN.description}</td>

                                ) : (
                                    <input
                                        className="my-orders-form-input"
                                        type="text"
                                        name={`description-${order.id}`}
                                        value={description[order.id] || ''}
                                        onChange={event => {
                                            const inputDescription = event.target.value;
                                            setDescription(prevDescription => ({
                                                ...prevDescription,
                                                [order.id]: inputDescription
                                            }));
                                        }}
                                    />
                                )}

                                <td className="my-orders-table-td">
                                    <li>{order.description}</li>


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