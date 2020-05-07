import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';
import Button from 'react-bootstrap/Button';
import "./doctorHome.css";

class AppointmentDetails extends Component {

    constructor(props) {
        super(props);
        this.state={
            appointmentDetails: [],
            medication: [],
            owner: [],
            doctor: [],
            addMedication: false,
            updateMedication: false,
            time: ""
         };
    }

    componentDidMount() {
        const id = window.location.href.split('?')[1]
        ax.getDoctorAppointmentById(id).then((payload)=>{
            if(payload!==undefined){
                this.setState({appointmentDetails: payload,
                    owner: payload.owner,
                    doctor: payload.doctor, 
                    time: payload.appointmentTime
                })
                document.getElementById("applicationStatus").value =payload.status
            }
        })
        ax.getMedication(id).then(payload => {
            if(payload!==undefined) {
                this.setState({medication: payload})
            }
        })
    }

    allowToAddMedication() {
        this.setState({
            addMedication: true
        })
    }

    allowToUpdateMedication() {
        this.setState({
            updateMedication: true
        })
    }

    addMedication(e) {
        e.preventDefault()
        let medication={};
        medication.treatment = document.getElementById("treatment").value
        medication.treatingDoctor = this.state.doctor.fullName
        ax.addMedicationn(medication,this.state.appointmentDetails.appointmentId).then((payload)=>{
            if(payload!==undefined){
                alert("Medication created!")
                window.location.reload()
            }
        })
    }

    updateMedication() {
        let medication={};
        medication.treatment = document.getElementById("treatment").value
        let appointmentId = this.state.appointmentDetails.appointmentId
        ax.updateMedicationn(medication, appointmentId).then((payload)=>{
            if(payload!==undefined){
                alert("Medication updated!")
                this.setState({updateMedication: false})
                window.location.reload(); 
            }
        })
    }

    updateAppointment() {
        let appointment={};
        appointment.status = document.getElementById("applicationStatus").value
        appointment.appointmentId = this.state.appointmentDetails.appointmentId
        appointment.animal = this.state.appointmentDetails.animal
        appointment.reason = this.state.appointmentDetails.reason
        appointment.appointmentTime = this.state.time
        ax.updateAppointment(appointment,this.state.doctor.id,this.state.owner.id).then((payload)=>{
            if(payload!==undefined){
                alert("Appointment updated!")
                window.location.reload();
            }
        })
    }

    render(){
        return(
            <div>
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
                        <td> Reason </td>
                        <td> {this.state.appointmentDetails.reason} </td>
                    </tr>
                    <tr>
                        <td> Status </td>
                        <select id="applicationStatus">
                            <option value="Confirmed">Confirmed</option>
                            <option value="Cancelled">Cancelled</option>
                            <option value="Waitlisted">Waitlisted</option>
                            <option value="Postponed">Postponed</option>
                            <option value="Treated">Treated</option>
                        </select>
                    </tr>
                    <tr>
                        <td> Appointment Time </td>
                        <td> {this.state.time} </td>
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
                <Button onClick={(e)=>{this.updateAppointment()}} variant="primary">UPDATE APPOINTMENT</Button>

                <p>
                {this.state.medication.length === 0 ? 
                <div className="medication">
                    Medication not given
                    <div>
                    <Button onClick={(e)=>{this.allowToAddMedication()}} variant="primary">Add Medication</Button>
                    {
                        this.state.addMedication?
                        <div>
                            <input type="text" id="treatment" placeholder="Treatment" />
                            <div>
                            <Button onClick={(e)=>{this.addMedication(e)}} variant="primary">Save</Button>
                            </div>
                        </div>
                        :
                        <div></div>
                    }
                    </div>
                </div>
                : 
                <div className="medication">
                    Medication given
                    {!this.state.updateMedication ?
                        <div>
                            Treatment: {this.state.medication.treatment}
                            <div>
                                <Button onClick={(e)=>{this.allowToUpdateMedication()}} variant="primary">Update Medication</Button>
                            </div>
                        </div>
                    :
                        <div>
                            <input type="text" id="treatment" placeholder={this.state.medication.treatment}/>
                            <div><Button onClick={(e)=>{this.updateMedication(e)}} variant="primary">Save</Button></div>
                        </div>
                    }
                </div>}
                </p>
                
            </div>
        )
    }
}

export default AppointmentDetails;