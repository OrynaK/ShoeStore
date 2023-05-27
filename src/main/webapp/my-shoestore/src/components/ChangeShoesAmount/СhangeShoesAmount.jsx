import React, {useEffect, useRef, useState} from "react";
import './СhangeShoesAmount.css';

function ChangeShoesAmount() {
    const [shoes, setShoes] = useState([]);
    const [amount, setAmount] = useState('');
    const [triggerEffect, setTriggerEffect] = useState(true);
    useEffect(() => {
        if (triggerEffect) {
            fetch("http://localhost:8080/getShoesAmount")
                .then((response) => response.json())
                .then((data) => setShoes(data))
                .catch((error) => {
                    console.error("Error:", error);
                });
            setTriggerEffect(false);
        }
    }, [triggerEffect]);
    const handleAmountChange = (shoeId, selectedAmount) => {
        const requestData = {
            id: shoeId,
            selectedAmount : amount,
        };
        console.log(requestData);
        fetch("http://localhost:8080/updateShoesAmount", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => response.text())
            .then((data) => {
                setShoes(shoes.map((shoe) => {
                    if (shoe.id === shoeId) {
                        return {
                            ...shoe,
                            amount: selectedAmount,
                        };
                    }
                    return shoe;
                }));
            })
            .catch((error) => {
                console.error("Error:", error);
            });
        const delay = 500;

        const timeoutId = setTimeout(() => {
            setTriggerEffect(true);
        }, delay);
        return () => clearTimeout(timeoutId);
    };

    return (
        <div className="shoes-list">
            <h1 className="shoes-list-header">Список усіх пар взуття</h1>
            <table className="shoes-list-table">
                <thead>
                <tr>
                    <th className="shoes-list-table-th">Фото</th>
                    <th className="shoes-list-table-th">Ім'я</th>
                    <th className="shoes-list-table-th">Розмір</th>
                    <th className="shoes-list-table-th">Колір</th>
                    <th className="shoes-list-table-th">Кількість</th>
                    <th className="shoes-list-table-th">Нова кількість</th>
                    <th className="shoes-list-table-th">Застосувати зміни</th>
                </tr>
                </thead>
                <tbody>
                {shoes.map((shoe) => (
                    <tr key={shoe.id}>
                        <td className="shoes-list-table-td">
                            <img src={"./images/" + shoe.imageName} alt="shoe" width="150px" height="100px" />
                        </td>
                        <td className="shoes-list-table-td">{shoe.name}</td>
                        <td className="shoes-list-table-td">{shoe.size}</td>
                        <td className="shoes-list-table-td">{shoe.color}</td>
                        <td className="shoes-list-table-td">{shoe.amount}</td>
                        <td className="shoes-list-table-td">
                        <input
                            className="shoes-list-table-select"
                            name="amount"
                            onChange={(event) => setAmount(event.target.value)}
                        ></input>
                    </td>
                    <td className="shoes-list-table-td">
                    <button
                    className="shoes-list-table-btn"
                    onClick={() => handleAmountChange(shoe.id)}
                    >
                    Змінити
                    </button>
                    </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default ChangeShoesAmount;
