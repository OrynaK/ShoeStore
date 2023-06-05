/*import React, {useEffect, useState} from "react";

function Test() {
    const [tests, setTests] = useState([]);
    useEffect(() => {
        fetch("http://localhost:8080/shoto")
            .then((response) => response.json())
            .then((data) => setTests(data))
            .catch((error) => {
                console.error("Error:", error);
            });
    }, []);

    return (
        <div className="users-list">
            <table className="users-list-table">
                <thead>
                <tr>
                    <th className="users-list-table-th">ID</th>
                    <th className="users-list-table-th">Ім'я</th>
                    <th className="users-list-table-th">Прізвище</th>
                    <th className="users-list-table-th">Пошта</th>
                    <th className="users-list-table-th">Роль</th>
                </tr>
                </thead>
                <tbody>
                {tests.map((test) => (
                    <tr key={test.id}>
                        <td className="users-list-table-td">{test.id}</td>
                        <td className="users-list-table-td">{test.name}</td>
                        <td className="users-list-table-td">{test.surname}</td>
                        <td className="users-list-table-td">{test.email}</td>
                        <td className="users-list-table-td">{test.role}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default Test;*/
