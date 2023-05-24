import React, {useEffect} from "react";
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
import ShoePage from "./components/ShoePage/ShoePage";
import AdminOrders from "./components/AdminOrders/AdminOrders";
import AddNewShoe from "./components/AddNewShoe/AddNewShoe";
import MakeOrder from "./components/MakeOrder/MakeOrder";
import Basket from "./components/Basket/Basket";
import UsersList from "./components/UsersList/UsersList";
import ClientOrders from "./components/ClientOrders/ClientOrders";

function App() {
    //localStorage.clear();
  return (
      <Router>
          <div className="ShoeStore">
              <div className="__navbar">
                  <NavbarParser/>

              </div>
                {/*<WarehouseOrders/>*/}
              {
                <Routes>
                  <Route path="/loginform" element={<LoginForm/>}></Route>
                  <Route path="/clientcabinet" element={<ClientCabinet/>}></Route>
                  <Route path="/registrationform" element={<RegistrationForm/>}></Route>
                  <Route path="/contacts" element={<Contacts/>}></Route>
                  <Route path="/paymentanddelivery" element={<PaymentAndDelivery/>}></Route>
                    <Route path="/adminorders" element={<AdminOrders/>}></Route>
                    <Route path="/addNewShoe" element={<AddNewShoe/>}></Route>
                  <Route path="/main" element={<Main/>}></Route>
                    <Route path="/makeorder" element={<MakeOrder/>}></Route>
                    <Route path="/basket" element={<Basket/>}></Route>
                    <Route path="/userslist" element={<UsersList/>}></Route>
                    <Route path="/clientorders" element={<ClientOrders/>}></Route>
                    <Route path="/shoepage" element={<ShoePage/>}></Route>
                    <Route path="/shoepage/:id" element={<ShoePage/>}/>
                </Routes>
                }

          </div>

      </Router>

  );
}

export default App;
