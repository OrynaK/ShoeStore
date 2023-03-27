import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

import "./index.css";

import RegistrationForm from "./components/RegistrationForm/RegistrationForm";
import LoginForm from "./components/LoginForm/LoginForm";
import ClientCabinet from "./components/ClientCabinet/ClientCabinet";
import NavbarParser from "./components/navbars/NavbarParser/NavbarParser";

function App() {
    localStorage.clear();
  return (
      <Router>
          <div className="ShoeStore">
              <div className="__navbar">
                  <NavbarParser/>
              </div>
              <Routes>
                  <Route path="/loginform" element={<LoginForm/>}></Route>
                  <Route path="/clientcabinet" element={<ClientCabinet/>}></Route>
                  <Route path="/registrationform" element={<RegistrationForm/>}></Route>
              </Routes>

          </div>

      </Router>

  );
}

export default App;
