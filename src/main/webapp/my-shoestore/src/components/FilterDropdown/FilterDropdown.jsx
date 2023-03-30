import React, {useState} from "react";
import "./FilterDropdown.css";

function FilterDropdown() {
    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };
    const [isOpenSublistColor, setIsOpenSublistColor] = useState(false);

    const toggleSublistColor = () => {
        setIsOpenSublistColor(!isOpenSublistColor);
    };


    return (
        <div className="filter-dropdown" onMouseEnter={toggleDropdown} onMouseLeave={toggleDropdown}>
            <button className="filter-dropdown__toggle">Фільтри</button>
            {isOpen && (
                <ul className="filter-dropdown__list">
                    <li>
                        <div className="filter-dropdown__sublist">
                            <a onMouseEnter={toggleSublistColor} onMouseLeave={toggleSublistColor} href="#">За кольором</a>
                            {isOpenSublistColor && (
                                <ul className="filter-dropdown__sublist-content">
                                    <li><a href="#">Чорні</a></li>
                                    <li><a href="#">Білі</a></li>
                                    <li><a href="#">Сині</a></li>
                                    <li><a href="#">Червоні</a></li>
                                    <li><a href="#">Сірі</a></li>
                                    <li><a href="#">Рожеві</a></li>
                                </ul>
                            )}
                        </div>
                        <div className="filter-dropdown__sublist">
                            <a href="#">За розміром</a>

                                <ul className="filter-dropdown__sublist-content">
                                    <li><a href="#">36</a></li>
                                    <li><a href="#">37</a></li>
                                    <li><a href="#">38</a></li>
                                    <li><a href="#">39</a></li>
                                    <li><a href="#">40</a></li>
                                    <li><a href="#">41</a></li>
                                    <li><a href="#">42</a></li>
                                    <li><a href="#">43</a></li>
                                </ul>

                        </div>
                        <div className="filter-dropdown__sublist">
                            <a href="#">За статтю</a>

                            <ul className="filter-dropdown__sublist-content">
                                <li><a href="#">Жіноча</a></li>
                                <li><a href="#">Чоловіча</a></li>
                                <li><a href="#">Унісекс</a></li>
                            </ul>

                        </div>
                    </li>

                </ul>
            )}
        </div>
    );
}

export default FilterDropdown;
