
import "./index.css";
import NavbarClient from "./components/NavbarClient/NavbarClient";
import RegistrationForm from "./components/RegistrationForm/RegistrationForm";
import LoginForm from "./components/LoginForm/LoginForm";
import NavbarAdmin from "./components/NavbarAdmin/NavbarAdmin";
import NavbarPacker from "./components/NavbarPacker/NavbarPacker";
import ClientCabinet from "./components/ClientCabinet/ClientCabinet";

function App() {
  return (
    <div className="ShoeStore">
        <div className="__navbar">
            <NavbarClient/>
        </div>
            {/*<RegistrationForm/>*/}
        <ClientCabinet/>
    </div>
  );
}

export default App;
