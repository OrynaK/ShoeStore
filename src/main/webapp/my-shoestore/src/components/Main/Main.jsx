import React, {useState} from "react";
import "./Main.css";
import ShoeCard from "../ShoeCard/ShoeCard";
import FilterDropdown from "../FilterDropdown/FilterDropdown";
import SearchBox from "../SearchBox/SearchBox";
import SortBtn from "../SortBtn/SortBtn";


function Main() {
    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };

    return(
        <div className="main">
            <div className="main-menu">
                <FilterDropdown/>
                <SortBtn/>
                <SearchBox/>

            </div>
            <div className="main-shoe-cards">
                <ShoeCard/>
                <ShoeCard/>
                <ShoeCard/>
                <ShoeCard/>
            </div>

        </div>

    );
}
export default Main;