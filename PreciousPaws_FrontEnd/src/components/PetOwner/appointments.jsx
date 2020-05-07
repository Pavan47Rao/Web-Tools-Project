import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';

class Appointments extends Component {
    constructor(props) {
        super(props);
        this.state={
            fullName: null,
            userId: 0,
            doctorAppointments: [],
            serviceAppointments: []
         };
    }

    componentDidMount() {

        this.setState({userId: sessionStorage.getItem("userId")});
        this.setState({fullName: sessionStorage.getItem("fullName")});

        ax.getDoctorAppointmentByOwnerId(sessionStorage.getItem("userId")).then((payload) => {
            if(payload !== undefined) {
                this.setState({doctorAppointments: payload})
            }
        })

        ax.getServiceAppointmentByOwnerId(sessionStorage.getItem("userId")).then((payload) => {
            if(payload !== undefined) {
                this.setState({serviceAppointments: payload})
            }
        })
    }

    render(){
    
        return(
            <div>
                Your Service Appointment History
                <table id="table-style">
                    <thead>
                        <tr>
                            <th>Appointment No</th>
                            <th>Service</th>
                            <th>Price</th>
                            <th>Animal</th>
                            <th>Status</th>
                            <th>Appointment Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.serviceAppointments.map(( listValue, index ) => {
                            return (
                                <tr key={index}>
                                    <td>{listValue["appointmentId"]}</td>
                                    <td>{listValue["service"]}</td>
                                    <td>${listValue["price"]}</td>
                                    <td>{listValue["animal"]}</td>
                                    <td>{listValue["status"]}</td>
                                    <td>{listValue["appointmentTime"]}</td>
                                </tr>
                                );
                            })}
                    </tbody>
                </table>
                Your Doctor Appointment History
                <table id="table-style">
                    <thead>
                        <tr>
                            <th>Appointment No</th>
                            <th>Animal</th>
                            <th>Reason</th>
                            <th>Status</th>
                            <th>Appointment Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.doctorAppointments.map(( listValue, index ) => {
                            return (
                                <tr key={index}>
                                    <td><a href={"/doctorAppointmentDetails?"+ listValue["appointmentId"]}>{listValue["appointmentId"]}</a></td>
                                    <td>{listValue["animal"]}</td>
                                    <td>{listValue["reason"]}</td>
                                    <td>{listValue["status"]}</td>
                                    <td>{listValue["appointmentTime"]}</td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>
        )
    }
}
export default Appointments;