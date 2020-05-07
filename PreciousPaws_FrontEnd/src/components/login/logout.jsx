import React,{ Component } from "react";

class Logout extends Component{
    componentDidMount() {
        sessionStorage.setItem('authtoken','')
        sessionStorage.setItem('userId','')
    }
    render(){
        return(
            <div>
                You have Logged out. 
                <div>
                    <a href="/">Please click here to Login Again</a>
                </div>
            </div>
        )
    }
}



export default Logout;