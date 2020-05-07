import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button'
import "./login.css"

class Login extends Component{
    constructor(props) {
        super(props);
        this.state={
            fullName: null,
            userId: 0
         };
    }

    authenticate_user(e){
      e.preventDefault();
      let username=document.getElementById("username").value;
      let password=document.getElementById("password").value;
      let role=document.getElementById("role").value;
      if (role === 'admin') {
        ax.getuserdata(username,password,role).then((payload)=>{
            if(payload!==undefined){
                if(payload.userName!==""){
                    this.setState({fullName: payload['fullName'], userId: payload['userId']});
                    sessionStorage.setItem("userId",payload['userId']);
                    sessionStorage.setItem("fullName",payload['fullName']);
                    sessionStorage.setItem("authtoken",payload['authtoken']);
                    this.props.history.push({
                        pathname: '/admin/home'
                    });
                }
                else{
                    alert("login fail")
                }
            }
            else{
                alert("login fail");
            }
        })
      }
      else if (role === 'doctor') {
            ax.getuserdata(username,password,role).then((payload)=>{
                if(payload!==undefined){
                    if(payload.userName!==""){
                        this.setState({fullName: payload['fullName'], userId: payload['userId']});
                        sessionStorage.setItem("userId",payload['userId']);
                        sessionStorage.setItem("fullName",payload['fullName']);
                        sessionStorage.setItem("authtoken",payload['authtoken']);
                        this.props.history.push({
                            pathname: '/doctor/home'
                        });
                    }
                    else{
                        alert("login fail")
                    }
                }
                else{
                    alert("login fail");
                }
            })
        }
        else if (role === 'owner') {
            ax.getuserdata(username,password,role).then((payload)=>{
                if(payload!==undefined){
                    if(payload.userName!==""){
                        this.setState({fullName: payload['fullName'], userId: payload['userId']});
                        sessionStorage.setItem("userId",payload['userId']);
                        sessionStorage.setItem("fullName",payload['fullName']);
                        sessionStorage.setItem("authtoken",payload['authtoken']);
                        this.props.history.push({
                            pathname: '/petOwner/home'
                        });
                    }
                    else{
                        alert("login fail")
                    }
                }
                else{
                    alert("login fail");
                }
            })
        }
    }

    gotoSignUp(){
        this.props.history.push({
            pathname: '/signUp'
        });
    }

    render(){
        return(
            <div>
                <div className="loginSignupForm">
                <Form>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label>Email Address</Form.Label>
                        <Form.Control type="email" id="username" placeholder="Enter email" />
                        <Form.Text className="text-muted">
                        We'll never share your email with anyone else.
                        </Form.Text>
                    </Form.Group>

                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control id="password" type="password" placeholder="Password" />
                    </Form.Group>

                    <Form.Group controlId="exampleForm.ControlSelect1">
                        <Form.Label>I am a</Form.Label>
                        <Form.Control id="role" as="select">
                            <option value = "doctor">Doctor</option>
                            <option value = "admin">Admin</option>
                            <option value = "owner">PetOwner</option>
                        </Form.Control>
                    </Form.Group>

                    <Button variant="primary" type="submit" onClick={(e)=>{this.authenticate_user(e)}}>
                        LOGIN
                    </Button>

                    <Button className="signup" variant="primary" type="button" onClick={(e)=>{this.gotoSignUp()}}>
                    Not a Registered User? Click here
                    </Button>
                </Form>
                </div>
            </div>
        )
    }
}

export default Login;