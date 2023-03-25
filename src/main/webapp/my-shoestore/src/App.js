
import "./index.css";
import Navbar from "./components/Navbar/Navbar";
import RegistrationForm from "./components/RegistrationForm/RegistrationForm";
import LoginForm from "./components/LoginForm/LoginForm";

function App() {
  return (
    <div className="ShoeStore">
        <div className="__navbar">
            <Navbar/>
        </div>
            {/*<RegistrationForm/>*/}
        <LoginForm/>
    </div>
  );
}

export default App;
