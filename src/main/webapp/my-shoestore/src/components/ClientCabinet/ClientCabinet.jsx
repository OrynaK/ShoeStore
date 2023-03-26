import "./ClientCabinet.css"

function ClientCabinet() {


    return (
        <div className="cabinet">
            <h1>Особистий кабінет</h1>
            <h2 className="cabinet-data">Особисті данні</h2>
            <h2 className="cabinet-basket">Кошик</h2>
        <div className="cabinet-form">

            <span>Орина</span>
            <span>Касапова</span>
            <span>Номер телефону</span>
            <span><em>+38 050 133 33 56</em></span>
            <span>Email</span>
            <span><em>xxxxxxx@gmail.com</em></span>
            <span>Адреса</span>
            <span><em>Харкiв, вул. Х кв 13</em></span>
            <button>Змiнити даннi</button>

        </div>
            </div>
    )
}

export default ClientCabinet;