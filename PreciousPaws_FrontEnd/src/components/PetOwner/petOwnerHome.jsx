import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';
class PetOwnerHome extends Component {
    constructor(props) {
        super(props);
        this.state={
            fullName: null,
            userId: 0,
            serviceCatalog: []
         };
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

    goToBookServicePage() {
        this.props.history.push({
            pathname: '/petOwner/bookService'
        });
    }

    goToBookDoctorPage() {
        this.props.history.push({
            pathname: '/petOwner/bookDoctor'
        });
    }

    goToViewAppointmentsPage() {
        this.props.history.push({
            pathname: '/petOwner/viewAppointments'
        });
    }

    render(){
    
        return(
            <div>
                <h1>You've reached PRECIOUS PAWS FOUNDATION home page</h1>
                <p>
                    Welcome, { this.state.fullName }
                </p>

                <div>
                    <div>
                        <button onClick={(e)=>{this.goToViewAppointmentsPage()}}>View My Appointments</button>
                    </div>
                    <div>
                        <button onClick={(e)=>{this.goToBookDoctorPage()}}>Book Doctor Appointment</button>                        
                    </div>
                    <div>
                        <button onClick={(e)=>{this.goToBookServicePage()}}>Book Service Appointment</button>
                    </div>
                </div>

                <p>
                    Following are the services we provide
                </p>
                <table id="table-style">
                    <thead>
                        <tr>
                            <th>Service</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.serviceCatalog.map(( listValue, index ) => {
                            return (
                                <tr key={index}>
                                    <td>{listValue[0]}</td>
                                    <td>${listValue[1]}</td>
                                </tr>
                                );
                            })}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default PetOwnerHome;