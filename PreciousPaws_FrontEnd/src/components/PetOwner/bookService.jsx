import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';

class BookService extends Component {
    constructor(props) {
        super(props);
        this.state={
            fullName: null,
            userId: 0,
            serviceCatalog: []
         };
    }

    createServiceAppointment() {
        let serAppointment = {}
        serAppointment.service = document.getElementById('serValue').value.split('_')[0]
        serAppointment.price = document.getElementById('serValue').value.split('_')[1]
        serAppointment.appointmentTime = document.getElementById('timing_select').value
        serAppointment.status = "Confirmed"
        serAppointment.animal = document.getElementById('animal').value
        
        ax.createServiceAppointment(serAppointment, sessionStorage.getItem("userId")).then(response => {
            if(response !== undefined) {
                alert("Created service appointment")
            }
        })
    }

    componentDidMount() {

        this.setState({userId: sessionStorage.getItem("userId")});
        this.setState({fullName: sessionStorage.getItem("fullName")});

        ax.getServiceCatalog().then((payload) => {
            if(payload !== undefined) {
                this.setState({serviceCatalog: payload})
            }
        })
    }

    render(){
    
        return(
            <div>
                <table id="table-style">
                    <thead>
                        <tr>
                            <th>Service</th>
                            <th>Price</th>
                            <th>Pick One Service</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.serviceCatalog.map(( listValue, index ) => {
                            return (
                                <tr key={index}>
                                    <td>{listValue[0]}</td>
                                    <td>${listValue[1]}</td>
                                    <td><input type="radio" name="service" id="serValue" value={listValue[0] +"_"+listValue[1]}/></td>
                                </tr>
                                );
                            })}
                    </tbody>
                </table>
                <label>
                    Animal:
                    <input type="text" id="animal"/>
                </label>
                <label>
                    Pick a time:
                    <select id="timing_select" value={this.state.selectedSchedule}>
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
                <button onClick={(e)=>{this.createServiceAppointment()}}>Book Service Appointment</button>
            </div>
        )
    }
}
export default BookService;