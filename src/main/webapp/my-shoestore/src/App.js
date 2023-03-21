
import "./index.css";
import Navbar from "./components/Navbar/Navbar";
import RegistrationForm from "./components/RegistrationForm/RegistrationForm";

function App() {
  return (
    <div className="ShoeStore">
        <div className="__navbar">
            <Navbar/>
        </div>
            <RegistrationForm/>
    </div>
  );
}

export default App;
