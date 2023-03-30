import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

import "./index.css";

import RegistrationForm from "./components/RegistrationForm/RegistrationForm";
import LoginForm from "./components/LoginForm/LoginForm";
import ClientCabinet from "./components/ClientCabinet/ClientCabinet";
import NavbarParser from "./components/navbars/NavbarParser/NavbarParser";
import NavbarAdmin from "./components/navbars/NavbarAdmin/NavbarAdmin";
import Contacts from "./components/Contacts/Contacts";
import PaymentAndDelivery from "./components/PaymentAndDelivery/PaymentAndDelivery";
import Main from "./components/Main/Main";

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
                  <Route path="/contacts" element={<Contacts/>}></Route>
                  <Route path="/paymentanddelivery" element={<PaymentAndDelivery/>}></Route>
                  <Route path="/main" element={<Main/>}></Route>
              </Routes>

          </div>

      </Router>

  );
}

export default App;
