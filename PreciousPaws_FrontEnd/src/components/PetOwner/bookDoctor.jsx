import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';

class BookDoctor extends Component {
    constructor(props) {
        super(props);
        this.state={
            fullName: "",
            userId: 0,
            sex: "",
            doctors: [],
            doctorName: "",
            availableSchedules: [],
            selectedSchedule: "",
            doctorId: 0
         };
         this.handleSubmit = this.handleSubmit.bind(this);
         this.handleSexChange = this.handleSexChange.bind(this);
    }

    handleSexChange(event) {
        this.setState({sex: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        let docAppointment = {}
        docAppointment.animal = document.getElementById('animal').value
        docAppointment.reason = document.getElementById('reason').value
        docAppointment.appointmentTime = document.getElementById("timing_select").value
        docAppointment.status = "Waitlisted"
        const doctorId = document.getElementById("doctor_select").value
        const userId = this.state.userId
        
        ax.createDoctorAppointment(docAppointment, doctorId, userId).then((response) => {
            if(response !== undefined) {
                alert("Successfully created doctor appointment")
            }
            else {
                alert("Failed to create doctor appointment")
            }
        })
    }

    componentDidMount() {

        this.setState({userId: sessionStorage.getItem("userId")});
        this.setState({fullName: sessionStorage.getItem("fullName")});

        ax.getAllDoctors().then((payload) => {
            if(payload !== undefined) {
                this.setState({doctors: payload})
            }
        })
    }

    render(){
    
        return(
            <div>
                <form onSubmit={this.handleSubmit}>
                <label>
                    Animal:
                    <input type="text" id="animal" value={this.state.value}/>
                </label>
                <label>
                    Reason:
                    <input type="text" id="reason" value={this.state.value}/>
                </label>
                <label>
                    Breed:
                    <input type="text" value={this.state.value}/>
                </label>
                <label>
                    Age:
                    <input type="number" value={this.state.value}/>
                </label>
                <label>
                    Sex:
                    <select value={this.state.sex} onChange={this.handleSexChange}>
                        <option value="female">Female</option>
                        <option value="male">Male</option>
                    </select>
                </label>
                <label>
                    Pick a doctor:
                    <select id="doctor_select" >
                    { Object.values(this.state.doctors).map((d)=>{
                        return <option value={d.userId}>{d.fullName}</option>
                        })
                    }
                    </select>
                </label>
                <label>
                    Pick an Available Schedule:
                    <select id="timing_select" >
                        <option value="10:00-10:30">10:00-10:30</option>
                        <option value="10:30-11:00">10:30-11:00</option>
                        <option value="11:00-11:30">11:00-11:30</option>
                        <option value="11:30-12:00">11:30-12:00</option>
                        <option value="11:30-12:00">14:00-14:30</option>
                        <option value="11:30-12:00">14:30-15:00</option>
                        <option value="11:30-12:00">15:00-15:30</option>
                        <option value="11:30-12:00">15:30-12:00</option>
                    </select>
                </label>
                <input type="submit" value="Submit" />
                </form>
            </div>
        )
    }
}
export default BookDoctor;