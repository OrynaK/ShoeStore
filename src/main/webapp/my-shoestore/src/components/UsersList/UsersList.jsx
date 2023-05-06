import React, {useState} from "react";
import './UsersList.css';

function UsersList() {

    const users = [
        { id: '1', name: 'bob', surname: 'black', email: 'bob@gmail.com', role:'client' },
        { id: '2', name: 'bob', surname: 'black', email: 'bob@gmail.com', role:'client' },
        { id: '3', name: 'bob', surname: 'black', email: 'bob@gmail.com', role:'client' },
    ];

    return (
        <div className="users-list">
            <h1 className="users-list-header">Список усіх користувачів</h1>
            <table className="users-list-table">
                <thead>
                <tr>
                    <th className="users-list-table-th">Айді</th>
                    <th className="users-list-table-th">Ім'я</th>
                    <th className="users-list-table-th">Прізвище</th>
                    <th className="users-list-table-th">Пошта</th>

                    <th className="users-list-table-th"></th>
                </tr>
                </thead>
                <tbody>
                {users.map((user, index) => (
                    <tr key={index}>
                        <td className="users-list-table-td">{user.id}</td>
                        <td className="users-list-table-td">{user.name}</td>
                        <td className="users-list-table-td">{user.surname}</td>
                        <td className="users-list-table-td">{user.email}</td>

                        <select className="users-list-table-td"
                                name="role"
                        >
                            <option value="" disabled>Change role</option>
                            <option value="Client">Клієнт</option>
                            <option value="Admin">Адмін</option>
                            <option value="Warehouse">Працівник складу</option>
                            <option value="Packer">Пакувальник</option>
                            <option value="Courier">Кур'єр</option>
                        </select>

                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    )
}

export default UsersList;