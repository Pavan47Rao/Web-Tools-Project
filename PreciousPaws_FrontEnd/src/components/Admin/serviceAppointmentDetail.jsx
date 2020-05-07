import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';
import Button from 'react-bootstrap/Button';

class ServiceAppointmentDetail extends Component {

    constructor(props) {
        super(props);
        this.state={
            appointmentDetails: [],
            owner: []
         };
    }

    componentDidMount() {
        const id = window.location.href.split('?')[1]
        ax.getServiceAppointmentById(id).then((payload)=>{
            if(payload!==undefined){
                this.setState({appointmentDetails: payload,
                    owner: payload.owner
                })
                document.getElementById("applicationStatus").value =this.state.appointmentDetails.status
            }
        })
    }

    updateAppointment() {
        ax.updateServiceAppointment(this.state.appointmentDetails.appointmentId, document.getElementById("applicationStatus").value).then((payload) => {
            if(payload!==undefined){
                alert("Appointment updated!")
            }
        })
    }

    render(){
        return(
            <div>
                <h3>Service Appointment Details</h3>
                <table id="table-style">
                    <tr>
                        <td> Appointment Id </td>
                        <td> {this.state.appointmentDetails.appointmentId} </td>
                    </tr>
                    <tr>
                        <td> Animal </td>
                        <td> {this.state.appointmentDetails.animal} </td>
                    </tr>
                    <tr>
                        <td> Service </td>
                        <td> {this.state.appointmentDetails.service} </td>
                    </tr>
                    <tr>
                        <td> Price </td>
                        <td> {this.state.appointmentDetails.price} </td>
                    </tr>
                    <tr>
                        <td> Appointment Time </td>
                        <td> {this.state.appointmentDetails.appointmentTime} </td>
                    </tr>
                    <tr>
                        <td> Status </td>
                        <select id="applicationStatus">
                            <option value="Confirmed">Confirmed</option>
                            <option value="Cancelled">Cancelled</option>
                            <option value="Completed">Completed</option>
                        </select>
                    </tr>
                    <tr>
                        <td> Owner </td>
                        <td> {this.state.owner.fullName} </td>
                    </tr>
                    <tr>
                        <td> Contact No </td>
                        <td> {this.state.owner.contactNo} </td>
                    </tr>    
                </table>
                <Button onClick={(e)=>{this.updateAppointment()}} variant="primary">Update Appointment</Button>
                
            </div>
        )
    }
}

export default ServiceAppointmentDetail;