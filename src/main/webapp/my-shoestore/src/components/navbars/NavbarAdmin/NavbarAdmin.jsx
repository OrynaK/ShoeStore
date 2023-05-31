import "./NavbarAdmin.css"
import logo from "../../../assets/logo.png";
import {NavLink} from "react-router-dom";
import {useNavigate} from "react-router";
import {useState} from "react";
function NavbarAdmin() {
    const navigate = useNavigate();
    const [isOpen, setIsOpen] = useState(false);

    const toggleMenu = () => {
        setIsOpen(!isOpen);
    };

    const handleItemClick = () => {
        setIsOpen(false);
    };
    function handleSubmit() {
        localStorage.clear();

        navigate('/main'); // Перенаправлення на головну сторінку
        window.location.reload(); // Оновлення сторінки
    }


    return (
        <nav className="nav-admin">
            <div className="container">
                <div className="nav-row-admin">
                    <img className="logo-admin" src={logo} alt="logo"/>
                    <ul className="nav-list-admin">
                        <NavLink to="/addNewShoe" className="nav-list__item">
                            Додати товар
                        </NavLink>
                        <NavLink to="/changeShoesAmount" className="nav-list__item">
                            Змінити кількість взуття
                        </NavLink>
                        <NavLink to="/userslist" className="nav-list__item">
                            Список користувачів
                        </NavLink>
                        <div className="dropdown">
                            <button className="dropdown-toggle" onClick={toggleMenu}>
                                Замовлення
                            </button>
                            {isOpen && (
                                <div className="dropdown-menu">
                                    <NavLink to="/accountorders" onClick={handleItemClick}>
                                        Облік замовлень
                                    </NavLink>
                                    <NavLink to="/workerorders" onClick={handleItemClick}>
                                        Доступні Замовлення
                                    </NavLink>
                                    <NavLink to="/myorders" onClick={handleItemClick}>
                                        Мої замовлення
                                    </NavLink>
                                </div>
                            )}
                        </div>
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