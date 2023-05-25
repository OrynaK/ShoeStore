import React from "react";
import {Link, NavLink} from "react-router-dom";
import "./ShoeCard.css";
import sneaker from "./../../images/88c4b9aa-aef6-4e53-8d1d-02ddefab871e.jpeg"
import {useNavigate} from "react-router";
function ShoeCard(props) {
    const { id, name, price, imageName} = props;
    const navigate = useNavigate();

    const handleClick = () => {
        navigate().push({
            pathname: `/shoepage/${name}`,
            state: { id, name, price, imageName }
        });
    };

    return (
        <Link to={`/shoepage/${name}`} state={{ id, name, price, imageName}} className="shoe-card">
        <div className="shoe-card">
            <div className="shoe-card-form">
                <img className="shoe-card-form--img" src={"./../../images/88c4b9aa-aef6-4e53-8d1d-02ddefab871e.jpeg"} alt="shoe" width="170px" height="100px"/>
                <h3 className="shoe-card-form--header">{name}</h3>
                <span className="shoe-card-form--price"><strong>{price}$</strong></span>
            </div>
        </div>
        </Link>
    );
}

export default ShoeCard;
