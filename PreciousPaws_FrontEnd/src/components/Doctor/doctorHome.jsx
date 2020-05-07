import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';
import ListGroup from 'react-bootstrap/ListGroup'
import "./doctorHome.css";
class DoctorHome extends Component {

    constructor(props) {
        super(props);
        this.state={
            userId: 0,
            fullName: "",
            appointments: [],
            scheduleList: []
         };
    }

    componentDidMount() {
        this.setState({fullName: sessionStorage.getItem("fullName"), userId: sessionStorage.getItem("userId")});

        ax.getDoctorAppointments(sessionStorage.getItem("userId")).then((payload)=>{
            if(payload !== undefined){
                if(payload.length > 0){
                    this.setState({appointments: payload})
                }
            }
        })

        ax.getSchedules(sessionStorage.getItem("userId")).then((response)=> {
            if(response !== undefined) {
                if(response.schedules !== undefined) {
                    this.setState({scheduleList:response.schedules})
                }
            }
        })
    }

    openAppointmentDetail(id) {
        this.props.history.push({
            pathname: '/doctor/appointment',
            data: id 
        });
    }

    render(){
        return(
            <div>
                <h1 className="welcomeMessage">
                    Welcome, { this.state.fullName }
                </h1>
                <h3>
                    Your Appointment History
                </h3>
                <table id="table-style">
                    <thead>
                    <tr>
                        <th>Appointment No</th>
                        <th>Animal</th>
                        <th>Reason</th>
                        <th>Owner</th>
                        <th>Operation</th>
                    </tr>
                    </thead>
                    <tbody>
                        {this.state.appointments.map(( listValue, index ) => {
                            return (

                                <tr key={index}>
                                    <td>{listValue["appointmentId"]}</td>
                                    <td>{listValue["animal"]}</td>
                                    <td>{listValue["reason"]}</td>
                                    <td>{listValue["owner"].fullName}</td>
                                    <td><a href={"/doctorAppointment?"+ listValue["appointmentId"]}>View</a></td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                <div>
                    <h3>Following are your free schedules</h3>
                    <div className="schedules">
                        <ListGroup>
                            {this.state.scheduleList.map((schedule) => (
                                <ListGroup.Item>{schedule}</ListGroup.Item>
                            ))}
                        </ListGroup>
                    </div>
                </div>
            </div>
        )
    }

}

export default DoctorHome;
