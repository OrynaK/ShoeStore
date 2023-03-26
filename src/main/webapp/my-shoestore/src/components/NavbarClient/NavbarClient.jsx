
import "./NavbarClient.css"
import logo from "./../../assets/logo.png"

function NavbarClient () {


    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <img className="logo" src={logo} alt="logo"/>
                    <ul className="nav-list">
                        <li className="nav-list__item">Головна</li>
                        <li className="nav-list__item">Взуття</li>
                        <li className="nav-list__item">Оплата та доставка</li>
                        <li className="nav-list__item">Контакти</li>
                        <li className="nav-list__item">Вхід/Реєстрація</li>
                        <li className="nav-list__item">Особистий кабінет</li>
                    </ul>
                </div>
            </div>
        </nav>

    );

}
export default NavbarClient;