import React, {useEffect, useState} from "react";
import './UsersList.css';

function UsersList() {
    const [users, setUsers] = useState([]);
    const [role, setRole] = useState('');
    useEffect(() => {
        fetch("http://localhost:8080/getUsers")
            .then((response) => response.json())
            .then((data) => setUsers(data))
            .catch((error) => {
                console.error("Error:", error);
            });
    }, []);

    const handleRoleChange = () => {
        fetch("http://localhost:8080/updateUserRole", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => response.json())
            .then((data) => setRole(data))
            .catch((error) => {
                console.error("Error:", error);
            });
    };

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
                    <th className="users-list-table-th">Роль</th>
                    <th className="users-list-table-th">Змінити роль</th>
                </tr>
                </thead>
                <tbody>
                {users.map((user) => (
                    <tr key={user.id}>
                        <td className="users-list-table-td">{user.id}</td>
                        <td className="users-list-table-td">{user.name}</td>
                        <td className="users-list-table-td">{user.surname}</td>
                        <td className="users-list-table-td">{user.email}</td>
                        <td className="users-list-table-td">{user.role}</td>
                        <td className="users-list-table-td">
                            <select
                                className="users-list-table-select"
                                name="role"
                                value={role}
                                onChange={(event) =>
                                    handleRoleChange(user.role, event.target.value)
                                }
                            >
                                <option value="">Змінити роль</option>
                                <option value="CLIENT">Клієнт</option>
                                <option value="ADMIN">Адмін</option>
                                <option value="WAREHOUSE">Працівник складу</option>
                                <option value="PACKER">Пакувальник</option>
                                <option value="COURIER">Кур'єр</option>
                            </select>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default UsersList;
