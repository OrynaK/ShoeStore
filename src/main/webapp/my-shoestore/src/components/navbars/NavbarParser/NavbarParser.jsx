import "./NavbarParser.css"
import NavbarAdmin from "../NavbarAdmin/NavbarAdmin";
import NavbarClient from "../NavbarClient/NavbarClient";
import NavbarDefault from "../NavbarDefault/NavbarDefault";
import {useEffect, useState} from "react";


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
            {(user && user.role) === null && <NavbarDefault />}
            {(user && user.role) === 'CLIENT' && <NavbarClient />}
            {(user && user.role) === 'ADMIN' && <NavbarAdmin/>}
            {(user && user.role) === 'COURIER' && <NavbarAdmin/>}
            {(user && user.role) === 'PACKER' && <NavbarAdmin/>}
            {(user && user.role) === 'WAREHOUSE' && <NavbarAdmin/>}
        </nav>

    );

}

export default NavbarParser;