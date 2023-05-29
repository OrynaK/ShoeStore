import "./NavbarParser.css"
import NavbarAdmin from "../NavbarAdmin/NavbarAdmin";
import NavbarClient from "../NavbarClient/NavbarClient";
import NavbarDefault from "../NavbarDefault/NavbarDefault";
import {useEffect, useState} from "react";

import NavbarStaff from "../NavbarStaff/NavbarStaff";



function NavbarParser() {
    const [user, setUser] = useState(() => {
        const storedUser = localStorage.getItem("user");
        return storedUser ? JSON.parse(storedUser) : null;
    });

    useEffect(() => {
        const handleStorageChange = () => {
            const storedUser = localStorage.getItem('user');
            setUser(storedUser ? JSON.parse(storedUser) : null);
        };
        window.addEventListener('storage', handleStorageChange);

        return () => {
            window.removeEventListener('storage', handleStorageChange);
        };
    }, []);

    return (
        <nav className="nav">
            {(user && user.role) === null && <NavbarDefault/>}
            {(user && user.role) === 'CLIENT' && <NavbarClient/>}
            {(user && user.role) === 'ADMIN' && <NavbarAdmin/>}
            {(user && user.role) === 'COURIER' && <NavbarStaff/>}
            {(user && user.role) === 'PACKER' && <NavbarStaff/>}
            {(user && user.role) === 'WAREHOUSE' && <NavbarStaff/>}
        </nav>

    );

}

export default NavbarParser;