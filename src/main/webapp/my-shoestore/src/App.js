import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

import "./index.css";
import NavbarClient from "./components/NavbarClient/NavbarClient";
import RegistrationForm from "./components/RegistrationForm/RegistrationForm";
import LoginForm from "./components/LoginForm/LoginForm";
import NavbarAdmin from "./components/NavbarAdmin/NavbarAdmin";

import NavbarPacker from "./components/NavbarPacker/NavbarPacker";
import ClientCabinet from "./components/ClientCabinet/ClientCabinet";

function App() {
  return (
      <Router>
          <div className="ShoeStore">
              <div className="__navbar">
                  <NavbarClient/>
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
