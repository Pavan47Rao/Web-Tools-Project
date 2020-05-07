import React from 'react';
import './App.css';
import { Route, Switch } from "react-router-dom";
import PetOwnerHome from "../src/components/PetOwner/petOwnerHome"
import DoctorHome from "../src/components/Doctor/doctorHome";
import Login from '../src/components/login/login';
import AppointmentDetails from "../src/components/Doctor/appointmentDetails";
import AdminHome from "../src/components/Admin/adminHome";
import ServiceAppointmentDetail from "../src/components/Admin/serviceAppointmentDetail";
import Appointments from "../src/components/PetOwner/appointments"
import BookDoctor from "../src/components/PetOwner/bookDoctor";
import BookService from "../src/components/PetOwner/bookService";
import DoctorAppointmentDetails from "../src/components/PetOwner/doctorAppointmentDetails";
import { Navbar, Nav, NavItem, NavDropdown, MenuItem } from 'react-bootstrap';
import "react-bootstrap/dist/react-bootstrap.min.js";
import SignUp from "../src/components/signup/signup";
import Logout from "../src/components/login/logout";

function App() {
  return (
    <div className="container-fluid">
      
      <Navbar  sticky="top" collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Navbar.Brand href="#home">Precious Paws</Navbar.Brand>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
      <Nav className="mr-auto">
        <Nav.Link href="/">Home</Nav.Link>
        <Nav.Link href="/signUp">SignUp</Nav.Link>
        <Nav.Link href="/logout">Logout</Nav.Link>
      </Nav>
      </Navbar.Collapse>
      </Navbar>

      <Switch>
        <Route path="/doctor/home" component={DoctorHome} />
        <Route path="/petOwner/home" component={PetOwnerHome} />
        <Route path="/doctorAppointmentDetails" component={DoctorAppointmentDetails} />
        <Route path="/doctorAppointment" component={AppointmentDetails} />
        <Route path="/admin/home" component={AdminHome} />
        <Route path="/serviceAppointment" component={ServiceAppointmentDetail} />
        <Route path="/petOwner/viewAppointments" component={Appointments} />
        <Route path="/petOwner/bookDoctor" component={BookDoctor} />
        <Route path="/petOwner/bookService" component={BookService} />
        <Route path="/signUp" component={SignUp} />
        <Route path="/logout" component={Logout} />
        <Route path="/" exact component={Login} />
      </Switch>
    </div>
  );
}

export default App;
