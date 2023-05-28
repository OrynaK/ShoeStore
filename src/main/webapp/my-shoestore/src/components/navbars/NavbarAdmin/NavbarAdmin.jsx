import "./NavbarAdmin.css"
import logo from "../../../assets/logo.png";
import {NavLink} from "react-router-dom";
import {useNavigate} from "react-router";
function NavbarAdmin() {
    const navigate = useNavigate();
    function handleSubmit() {
        localStorage.clear();

        navigate('/main'); // Перенаправлення на головну сторінку
        window.location.reload(); // Оновлення сторінки
    }


    return (
        <nav className="nav-admin">
            <div className="container">
                <div className="nav-row-admin">
                    <img className="logo" src={logo} alt="logo"/>
                    <ul className="nav-list">
                        <NavLink to="/addNewShoe" className="nav-list__item">
                            Додати товар
                        </NavLink>
                        <NavLink to="/changeShoesAmount" className="nav-list__item">
                            Змінити кількість взуття
                        </NavLink>
                        <NavLink to="/userslist" className="nav-list__item">
                            Список користувачів
                        </NavLink>
                        <NavLink to="/adminorders" className="nav-list__item">
                            Замовлення
                        </NavLink>
                        <NavLink to="/clientcabinet" className="nav-list__item">
                            Особистий кабінет
                        </NavLink>
                       <button className="btn-exit" onClick={handleSubmit}>Вихід</button>

                    </ul>
                </div>
            </div>
        </nav>

    );

}

export default NavbarAdmin;