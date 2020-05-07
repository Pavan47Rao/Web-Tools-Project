import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';

class DoctorAppointmentDetails extends Component {

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
                    doctor: payload.doctor, 
                    time: payload.appointmentTime
                })
                if(payload.medication!==undefined) {
                    this.setState({medication: payload.medication})
                }
            }
        })
    }

    render(){
        return(
            <div>
                <table id="table-style">
                    <tbody>
                    <tr>
                        <td> Appointment Id </td>
                        <td> {this.state.appointmentDetails.appointmentId} </td>
                    </tr>
                    <tr>
                        <td> Treating Doctor </td>
                        <td> {this.state.doctor.fullName} </td>
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
                        <td> {this.state.appointmentDetails.status} </td>
                    </tr>
                    <tr>
                        <td> Appointment Time </td>
                        <td> {this.state.time} </td>
                    </tr>
                    </tbody>
                </table>

                <p>
                {this.state.medication.length === 0 ? 
                <div>
                    Medication not given
                </div>
                : 
                <div>
                    Medication given
                    <div>
                        Treatment: {this.state.medication.treatment}
                    </div>
                </div>}
                </p>
                
            </div>
        )
    }
}

export default DoctorAppointmentDetails;