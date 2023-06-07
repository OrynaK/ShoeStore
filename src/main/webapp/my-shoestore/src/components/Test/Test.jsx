import React, {useEffect, useState} from "react";

function Test() {
    const [bestUserDTO, setBestUserDTO] = useState([]);
    useEffect(() => {
        fetch("http://localhost:8080/test")
            .then((response) => response.json())
            .then((data) => setBestUserDTO(data))
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
                    <th className="users-list-table-th">Айді замовлення</th>
                    <th className="users-list-table-th">Загальні витрати</th>
                </tr>
                </thead>
                <tbody>
                {bestUserDTO.map((bestUser) => (
                    <tr key={bestUser.user_id}>
                        <td className="users-list-table-td">{bestUser.user_id}</td>
                        <td className="users-list-table-td">{bestUser.name}</td>
                        <td className="users-list-table-td">{bestUser.surname}</td>
                        <td className="users-list-table-td">{bestUser.order_id}</td>
                        <td className="users-list-table-td">{bestUser.total_spent}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default Test;
