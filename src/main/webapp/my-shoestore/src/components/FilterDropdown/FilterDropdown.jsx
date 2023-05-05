import React, {useState} from "react";
import "./FilterDropdown.css";


function FilterDropdown({onFilterChange}) {
    const [isOpen, setIsOpen] = useState(false);
    const [isOpenSublistColor, setIsOpenSublistColor] = useState(false);
    const [isOpenSublistSize, setIsOpenSublistSize] = useState(false);
    const [isOpenSublistSex, setIsOpenSublistSex] = useState(false);
    const [color, setSelectedColor] = useState("");
    const [size, setSelectedSize] = useState("");
    const [sex, setSelectedSex] = useState("");

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };
    const toggleSublistColor = (e) => {
        e.stopPropagation();
        setIsOpenSublistColor(!isOpenSublistColor);
    };
    const toggleSublistSize = (e) => {
        e.stopPropagation();
        setIsOpenSublistSize(!isOpenSublistSize);
    };
    const toggleSublistSex = (e) => {
        e.stopPropagation();
        setIsOpenSublistSex(!isOpenSublistSex);
    };
    const handleColorClick = (color) => {
        setSelectedColor(color);
        setIsOpenSublistColor(false);
        fetch(`http://localhost:8080/getShoesByColor?color=${color}`)
            .then((response) => response.json())
            .then((data) => {
                onFilterChange(data);
            });
    };
    const handleSizeClick = (size) => {
        setSelectedSize(size);
        setIsOpenSublistSize(false);
        fetch(`http://localhost:8080/getShoesBySize?size=${size}`)
            .then((response) => response.json())
            .then((data) => {
                onFilterChange(data);
            });
    };
    const handleSexClick = (sex) => {
        setSelectedSex(sex);
        setIsOpenSublistSex(false);
        fetch(`http://localhost:8080/getShoesBySex?sex=${sex}`)
            .then((response) => response.json())
            .then((data) => {
                onFilterChange(data);
            });
    };

    // При наведенні на dropdown ми відкриваємо список і при відведенні - закриваємо
    return (
        <div
            className="filter-dropdown"
            onClick={toggleDropdown}
        >
            <button className="filter-dropdown__toggle">Фільтри</button>
            {isOpen && (
                <ul className="filter-dropdown__list">
                    <li>
                        <div className="filter-dropdown__sublist">
                            <a className="filter-dropdown__sublist--option"
                               onClick={toggleSublistColor}
                               href="#"
                            >
                                За кольором
                            </a>
                            {isOpenSublistColor && (
                                <ul className="filter-dropdown__sublist-content">
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleColorClick('black')}>Чорні</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleColorClick('white')}>Білі</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleColorClick('blue')}>Сині</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleColorClick('red')}>Червоні</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleColorClick('grey')}>Сірі</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleColorClick('pink')}>Рожеві</a>
                                    </li>
                                </ul>
                            )}
                        </div>
                        <div className="filter-dropdown__sublist">
                            <a className="filter-dropdown__sublist--option"
                               onClick={toggleSublistSize}
                               href="#"
                            >
                                За розміром
                            </a>
                            {isOpenSublistSize && (
                                <ul className="filter-dropdown__sublist-content">
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('36')}>36</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('36.5')}>36.5</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('37')}>37</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('37.5')}>37.5</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('38')}>38</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('38.5')}>38.5</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('39')}>39</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('39.5')}>39.5</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('40')}>40</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('40.5')}>40.5</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('41')}>41</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('41.5')}>41.5</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('42')}>42</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('42.5')}>42.5</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSizeClick('43')}>43</a>
                                    </li>
                                </ul>
                            )}
                        </div>
                        <div className="filter-dropdown__sublist">
                            <a className="filter-dropdown__sublist--option"
                               onClick={toggleSublistSex}
                               href="#"
                            >
                                За статтю
                            </a>
                            {isOpenSublistSex && (
                                <ul className="filter-dropdown__sublist-content">
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSexClick('female')}>Жіноча</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSexClick('male')}>Чоловіча</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#"
                                           onClick={() => handleSexClick('unisex')}>Унісекс</a>
                                    </li>
                                </ul>
                            )}
                        </div>
                    </li>
                </ul>
            )}
        </div>
    );
}

export default FilterDropdown;
