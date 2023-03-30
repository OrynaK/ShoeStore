import React, {useState} from "react";
import "./Main.css";
import ShoeCard from "../ShoeCard/ShoeCard";
import FilterDropdown from "../FilterDropdown/FilterDropdown";
import SearchBox from "../SearchBox/SearchBox";


function Main() {
    const [isOpen, setIsOpen] = useState(false);

    const toggleDropdown = () => {
        setIsOpen(!isOpen);
    };

    return(
        <div className="main">
            <div className="main-menu">
                <FilterDropdown/>
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