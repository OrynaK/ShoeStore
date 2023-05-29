import React, {useState} from "react";
import "./SortBtn.css";
import UpArrow from "./../../assets/up-arrow.png"
import DownArrow from "./../../assets/down-arrow.png"
function SortBtn({onSortChange}) {
    const [sortBy, setSortBy] = useState('asc');

    const handleSortChange = (sortType) => {
        setSortBy(sortType);
        onSortChange(sortType);
    };
    return (
        <div className="sort-btn">
            <div className="sort-btn-form">
                    <span className="sort-btn-form-header">Сортувати за ціною</span>
                        <img className="sort-btn-form-img" src={UpArrow} alt="Від найвижчої" width="10px" height="15px" onClick={() => handleSortChange('asc')}/>
                        <img className="sort-btn-form-img" src={DownArrow} alt="Від найнижчої" width="10px" height="15px"onClick={() => handleSortChange('desc')}/>
            </div>
        </div>
    );
}

export default SortBtn;