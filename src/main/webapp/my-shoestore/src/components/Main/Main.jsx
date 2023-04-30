import React, {useEffect, useState} from "react";
import "./Main.css";
import ShoeCard from "../ShoeCard/ShoeCard";
import FilterDropdown from "../FilterDropdown/FilterDropdown";
import SearchBox from "../SearchBox/SearchBox";
import SortBtn from "../SortBtn/SortBtn";


function Main() {
    const [isOpen, setIsOpen] = useState(false);
    const [shoes, setShoes] = useState([]);
    const [filteredShoes, setFilteredShoes] = useState([]);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };
    const handleFilterChange = (filteredData) => {
        setFilteredShoes(filteredData);
    };

    useEffect(() => {
        fetch('http://localhost:8080/getShoes')
            .then(response => response.json())
            .then(data => setShoes(data));
    }, []);

    return(
        <div className="main">
            <div className="main-menu">
                <FilterDropdown onFilterChange={handleFilterChange} />
                <SortBtn/>
                <SearchBox/>

            </div>
            <div className="main-shoe-cards">
                {filteredShoes && filteredShoes.length > 0 ? (
                    filteredShoes.map(shoe => (
                        <ShoeCard key={shoe.id} name={shoe.name} price={shoe.price} image={shoe.image} />
                    ))
                ) : (
                    shoes.map(shoe => (
                        <ShoeCard key={shoe.id} name={shoe.name} price={shoe.price} image={shoe.image} />
                    ))
                )}
            </div>

        </div>

    );
}
export default Main;