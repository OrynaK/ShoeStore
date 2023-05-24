
import "./NavbarClient.css"
import logo from "../../../assets/logo.png"
import {NavLink} from "react-router-dom";
import {useNavigate} from "react-router";

function NavbarClient () {
    const navigate = useNavigate();
    function handleSubmit() {
        localStorage.clear();

        navigate('/main'); // Перенаправлення на головну сторінку
        window.location.reload(); // Оновлення сторінки
    }


    return (
        <nav className="nav">
            <div className="container">
                <div className="nav-row">
                    <NavLink to="/main">
                        <img className="logo" src={logo} alt="logo"/>
                    </NavLink>

                    <ul className="nav-list">
                        <NavLink to="/main" className="nav-list__item">
                            Головна
                        </NavLink>

                        <NavLink to="/paymentanddelivery" className="nav-list__item">Оплата та доставка</NavLink>
                        <NavLink to="/contacts" className="nav-list__item">Контакти</NavLink>
                        <NavLink to="/loginform" className="nav-list__item">Вхід</NavLink>
                        <NavLink to="/clientcabinet" className="nav-list__item">Особистий кабінет</NavLink>
                        <NavLink to="/basket" className="nav-list__item">Кошик</NavLink>
                        <NavLink to="/clientorders" className="nav-list__item">Мої замовлення</NavLink>
                        <button className="btn-exit" onClick={handleSubmit}>Вихід</button>
                    </ul>
                </div>
            </div>
        </nav>

    );

}
export default NavbarClient;