import React, {useState} from "react";
import "./FilterDropdown.css";


function FilterDropdown() {
    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };

    const toggleSublistColor = (e) => {
        // Ця функція викликається при наведенні на або відведенні курсору від елемента "За кольором"
        // Ми використовуємо e.stopPropagation() для того, щоб запобігти подальшій передачі події (наприклад, до toggleDropdown)
        e.stopPropagation();
        setIsOpenSublistColor(!isOpenSublistColor);
    };

    const [isOpenSublistColor, setIsOpenSublistColor] = useState(false);

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
                            {/* isOpenSublistColor перевіряється лише тоді, коли ми наводимо курсор на "За кольором" */}
                            {isOpenSublistColor && (
                                <ul className="filter-dropdown__sublist-content">
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#">Чорні</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#">Білі</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#">Сині</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#">Червоні</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#">Сірі</a>
                                    </li>
                                    <li>
                                        <a className="filter-dropdown__sublist--option" href="#">Рожеві</a>
                                    </li>
                                </ul>
                            )}
                        </div>
                        <div className="filter-dropdown__sublist">
                            <a className="filter-dropdown__sublist--option" href="#">За розміром</a>
                            <ul className="filter-dropdown__sublist-content">
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">36</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">37</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">38</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">39</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">40</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">41</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">42</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">43</a>
                                </li>
                            </ul>
                        </div>
                        <div className="filter-dropdown__sublist">
                            <a className="filter-dropdown__sublist--option" href="#">За статтю</a>

                            <ul className="filter-dropdown__sublist-content">
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">Жіноча</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">Чоловіча</a>
                                </li>
                                <li>
                                    <a className="filter-dropdown__sublist--option" href="#">Унісекс</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            )}
        </div>
    );
}

export default FilterDropdown;
