import React, {useEffect} from "react";
import './ClientOrders.css';

function ClientOrders() {

    return (
        <div className="client-orders">
            <h1 className="client-orders-header">Мої замовлення</h1>
            <table className="client-orders-table">
                <thead>
                <tr>
                    <th className="client-orders-table-th">Номер</th>
                    <th className="client-orders-table-th">Дата замовлення</th>
                    <th className="client-orders-table-th">Статус</th>
                </tr>
                </thead>
                <tbody>

                    <tr>
                        <td className="client-orders-table-td">1</td>
                        <td className="client-orders-table-td">20.03.2023</td>
                        <td className="client-orders-table-td">Обробляється</td>
                    </tr>

                </tbody>
            </table>
        </div>
    )
}
export default ClientOrders;