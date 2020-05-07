import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';
import "../Doctor/doctorHome.css";
import Button from 'react-bootstrap/Button';

class AdminHome extends Component {

    constructor(props) {
        super(props);
        this.state={
            userId: 0,
            fullName: "",
            appointments: [],
            serviceCatalog: [],
            addService: false
         };
    }

    componentDidMount() {
        this.setState({userId: sessionStorage.getItem("userId")});
        this.setState({fullName: sessionStorage.getItem("fullName")});
        ax.getServiceAppointments().then((payload)=>{
            if(payload !== undefined){
                if(payload.length > 0){
                    this.setState({appointments: payload})
                    console.log(this.state.appointments)
                }
            }
        })

        ax.getServiceCatalog().then((payload) => {
            if(payload !== undefined) {
                this.setState({serviceCatalog: payload})
            }
        })
    }

    deleteService(e){
        const id = e.target.id
        ax.deleteServiceByCatalogId(id).then(payload => {
            if(payload !== undefined) {
                window.location.reload()
            }
        })
    }

    allowToAddService(){
        this.setState({
            addService: true
        })
    }

    createNewService() {
        const serviceObj = {}
        serviceObj.orgId = sessionStorage.getItem("userId")
        serviceObj.service = document.getElementById('service').value
        serviceObj.price = document.getElementById('price').value
        ax.addServiceToCatalog(serviceObj).then(payload => {
            if(payload !== undefined) {
                window.location.reload()
            }
        })
    }

    render(){
        return(
            <div>
                <h1 className="welcomeMessage">Welcome, { this.state.fullName }</h1>
                <p>
                    <h3>Service Appointment History</h3>
                </p>
                <table id="table-style">
                    <thead>
                    <tr>
                        <th>Appointment No</th>
                        <th>Service</th>
                        <th>Price</th>
                        <th>Owner</th>
                        <th>Operation</th>
                    </tr>
                    </thead>
                    <tbody>
                        {this.state.appointments.map(( listValue, index ) => {
                            return (

                                <tr key={index}>
                                    <td>{listValue["appointmentId"]}</td>
                                    <td>{listValue["service"]}</td>
                                    <td>${listValue["price"]}</td>
                                    <td>{listValue["owner"].fullName}</td>
                                    <td><a href={"/serviceAppointment?"+ listValue["appointmentId"]}>View</a></td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                <h3>Services currently offered</h3>
                <table id="table-style">
                    <thead>
                        <tr>
                            <th>Service</th>
                            <th>Price</th>
                            <th>Operation</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.serviceCatalog.map(( listValue, index ) => {
                            return (
                                <tr key={index}>
                                    <td>{listValue[0]}</td>
                                    <td>${listValue[1]}</td>
                                    <td><button id={listValue[2]} onClick={(e)=>{this.deleteService(e)}}>Delete</button></td>
                                </tr>
                                );
                            })}
                    </tbody>
                </table>
                <div>
                    <Button onClick={(e)=>{this.allowToAddService()}} variant="primary">Add New Service</Button>
                    {this.state.addService? 
                        <div className="new-service">
                            Service<input type="text" id="service" name="service"></input>
                            Price<input type="number" id="price" name="price"></input>
                            <Button onClick={(e)=>{this.createNewService()}} variant="primary">Save</Button>
                        </div> 
                        : 
                        <div></div>}
                </div>
            </div>
        )
    }

}

export default AdminHome;
