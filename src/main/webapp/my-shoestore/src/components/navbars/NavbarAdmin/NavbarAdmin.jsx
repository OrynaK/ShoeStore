import "./NavbarAdmin.css"
import logo from "../../../assets/logo.png";

function NavbarAdmin() {

    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <img className="logo" src={logo} alt="logo"/>
                    <ul className="nav-list">
                        <li className="nav-list__item">Додати товар</li>
                        <li className="nav-list__item">Видалити товар</li>
                        <li className="nav-list__item">Список користувачів</li>
                        <li className="nav-list__item">Замовлення</li>
                        <li className="nav-list__item">Особистий кабінет</li>
                    </ul>
                </div>
            </div>
        </nav>

    );

}

export default NavbarAdmin;