import React,{ Component } from "react";
import  * as ax  from '../../APIs/api';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button'
import "./signup.css"

class SignUp extends Component {

    signUpUser() {
        let user = {}
        user.userName = document.getElementById('userName').value
        user.password = document.getElementById('Password').value
        user.fullName = document.getElementById('fullname').value
        let role = document.getElementById('role').value
        if(role === "doctor") {
            ax.createDoctor(user).then(resp => {
                if(resp !== undefined) {
                    alert("Registered successfully")
                }
            })
        }
        else if(role === "admin") {
            user.contactNo = document.getElementById('contactno').value
            ax.createAdmin(user).then(resp => {
                if(resp !== undefined) {
                    alert("Registered successfully")
                }
            })
        }
        else if(role === "owner") {
            user.contactNo = document.getElementById('contactno').value
            ax.createPetOwner(user).then(resp => {
                if(resp !== undefined) {
                    alert("Registered successfully")
                }
            })
        }
    }

    render(){
        return(
            <div>
                <div className="loginSignupForm">
                <Form>
                   <Form.Group controlId="formBasicEmail">
                        <Form.Label>Email Address</Form.Label>
                        <Form.Control type="email" id="userName" placeholder="Enter email" />
                        <Form.Text className="text-muted">
                        We'll never share your email with anyone else.
                        </Form.Text>
                    </Form.Group>    

                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control id="Password" type="password" placeholder="Password" />
                    </Form.Group>

                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Re-enter Password</Form.Label>
                        <Form.Control id="repassword" type="password" placeholder="Password" />
                    </Form.Group>

                    <Form.Group controlId="formBasicEmail">
                        <Form.Label>Full Name</Form.Label>
                        <Form.Control type="text" id="fullname" placeholder="Enter full name" />
                        <Form.Text className="text-muted">
                        </Form.Text>
                    </Form.Group> 

                    <Form.Group controlId="formBasicEmail">
                        <Form.Label>Contact Number</Form.Label>
                        <Form.Control type="text" id="contactno" placeholder="Enter contact number" />
                        <Form.Text className="text-muted">
                        </Form.Text>
                    </Form.Group> 
                    
                    <Form.Group controlId="exampleForm.ControlSelect1">
                        <Form.Label>I am a</Form.Label>
                        <Form.Control id="role" as="select">
                            <option value = "doctor">Doctor</option>
                            <option value = "admin">Admin</option>
                            <option value = "owner">PetOwner</option>
                        </Form.Control>
                    </Form.Group>
                    
                    <Button variant="primary" type="submit" onClick={(e)=>{this.signUpUser()}}>
                        SIGN UP
                    </Button>
                </Form> 
                </div> 
            </div> 
        )
    }
}

export default SignUp;