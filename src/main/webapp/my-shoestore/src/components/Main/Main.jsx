import React, {useEffect, useState} from "react";
import "./Main.css";
import ShoeCard from "../ShoeCard/ShoeCard";
import FilterDropdown from "../FilterDropdown/FilterDropdown";
import SearchBox from "../SearchBox/SearchBox";
import SortBtn from "../SortBtn/SortBtn";
import {useNavigate} from "react-router";


function Main() {
    const [shoes, setShoes] = useState([]);
    const [filteredShoes, setFilteredShoes] = useState([]);
    const [sortType, setSortType] = useState('default');
    const handleSortChange = (sortType) => {
        setSortType(sortType);
    };
    useEffect(() => {
        let url = 'http://localhost:8080/getShoes';
        if (sortType === 'asc') {
            url = 'http://localhost:8080/getShoesAscendingPrice';
        } else if (sortType === 'desc') {
            url = 'http://localhost:8080/getShoesDescendingPrice';
        }
        fetch(url)
            .then(response => response.json())
            .then(data => setShoes(data));
    }, [sortType]);

    const handleFilterChange = (filteredData) => {
        setFilteredShoes(filteredData);
        setShoes([]);
    };
    const handleSearch = (searchResults) => {
        setFilteredShoes(searchResults);
    };

    return (
        <div className="main">
            <div className="main-menu">
                <FilterDropdown onFilterChange={handleFilterChange}/>
                <SortBtn onSortChange={handleSortChange}/>
                <SearchBox onSearch={handleSearch}/>
            </div>
            <div className="main-shoe-cards">
                {filteredShoes && filteredShoes.length > 0 ? (
                    filteredShoes.map(shoe => (
                        <ShoeCard key={shoe.id} name={shoe.name} price={shoe.price} imageName={shoe.imageName}/>
                    ))
                ) : (
                    shoes.length > 0 ? (
                        shoes.map(shoe => (
                            <ShoeCard key={shoe.id} name={shoe.name} price={shoe.price} imageName={shoe.imageName}/>
                        ))
                    ) : (
                        <h1>По вашому запиту нічого не знайдено</h1>
                    )
                )}
            </div>
        </div>
    );
}

export default Main;