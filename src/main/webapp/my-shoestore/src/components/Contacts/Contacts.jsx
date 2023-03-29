import React from 'react';

import "./Contacts.css"
import iconInst from "./../../assets/icon-inst.png"
import iconYoutube from "./../../assets/icon-youtube.png"
import iconFacebook from "./../../assets/icon-facebook.png"

function Contacts() {


    return (
        <div className="contacts">
            <h1 className="contacts-header">Контакти</h1>
            <div className="contacts-form">

                <h2 className="contacts-form-header">Адреса</h2>
                <a href="https://www.google.com/maps/dir//%D0%B2%D1%83%D0%BB%D0%B8%D1%86%D1%8F+%D0%A1%D1%83%D0%BC%D1%81%D1%8C%D0%BA%D0%B0,+19,+%D0%A5%D0%B0%D1%80%D0%BA%D1%96%D0%B2,+%D0%A5%D0%B0%D1%80%D0%BA%D1%96%D0%B2%D1%81%D1%8C%D0%BA%D0%B0+%D0%BE%D0%B1%D0%BB%D0%B0%D1%81%D1%82%D1%8C,+%D0%A3%D0%BA%D1%80%D0%B0%D0%B8%D0%BD%D0%B0,+61000/@49.9971485,36.1626818,12z/data=!4m8!4m7!1m0!1m5!1m1!1s0x4127a1f5a8fbb9b1:0x51401eb914d3d0e9!2m2!1d36.23277!2d49.9971643" target="_blank">вулиця Сумська, 19</a>
                <h2 className="contacts-form-header">Ми у соц мережах</h2>
                <div className="contacts-form-icons">
                    <a href="https://instagram.com/oriisya?igshid=YmMyMTA2M2Y=" target="_blank"><img src={iconInst} alt="instagram" width="60px" height="60px"/></a>
                    <a href="https://instagram.com/kllymenko?igshid=YmMyMTA2M2Y=" target="_blank"><img src={iconYoutube}  alt="youtube" width="60px" height="45px"/></a>
                    <a href="https://instagram.com/vykssy?igshid=YmMyMTA2M2Y=" target="_blank"><img src={iconFacebook}  alt="facebook" width="60px" height="60px"/></a>
                </div>
                <h2 className="contacts-form-header">Подзвонити</h2>
                <a href="/" target="_blank">+38 050 873 91 44</a>
            </div>

        </div>
    );
}

export default Contacts;