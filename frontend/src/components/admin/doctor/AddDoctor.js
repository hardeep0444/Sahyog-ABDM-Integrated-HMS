import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import adminService from "../../../services/adminService";



const AddDoctor = () => {

    let navigate = useNavigate();

    const user = {
        username: "",
        password: "",
        role: ""
    }

    const address = {
        line: "",
        district: "",
        state: "",
        pincode: ""
    }

    const [doctor, setDoctor] = useState({
        name: "",
        mobile: "",
        healthId:"",
        healthIdNumber:"",
        registrationNumber:"",
        gender:"",
        yearOfBirth:"",
        monthOfBirth:"",
        dayOfBirth:"",
        address: address,
        user: user
    });

    const {
        name,
        mobile,
        healthId,
        healthIdNumber,
        registrationNumber,
        gender,
        yearOfBirth,
        monthOfBirth,
        dayOfBirth,
        address: {
            line,
            district,
            state,
            pincode
        },
        user: {
            username,
            password,
            role
        }
    } = doctor;

    const onInputChange = (e) => {
        // console.log(e.target.name );
        // console.log(e.target.value);

        if(e.target.name === "username" || e.target.name === "password" || e.target.name === "role") {
            setDoctor({...doctor, user : {
                    ...doctor.user,
                    [e.target.name]: e.target.value
                }})
        }

        else if(e.target.name === "line" || e.target.name === "district" || e.target.name === "pincode" || e.target.name === "state") {

            setDoctor({...doctor, address : {
                    ...doctor.address,
                    [e.target.name]: e.target.value
                }})
        }

        else {
            setDoctor({...doctor, [e.target.name]: e.target.value});
        }
        // console.log(doctor);
    };


    const onSubmit = async (e) => {
        e.preventDefault();

        console.log(doctor)

        await adminService.addDoctor(doctor)
        // navigate("/admin");
    };

    return (
        <div className="container">
            <div className="row">
                <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                    <h2 className="text-center m-4">Register Doctor</h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Name
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter name"
                                name="name"
                                value={name}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Contact
                            </label>
                            <input
                                type={"text"}
                                className="form-control"
                                placeholder="Enter number"
                                name="mobile"
                                value={mobile}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                healthId
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Health-Id"
                                name="healthId"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                healthIdNumber
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Health-Number"
                                name="healthIdNumber"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Registration Number
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Registration-Number"
                                name="registrationNumber"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Gender
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Gender"
                                name="gender"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                year-of-Birth
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Year of Birth"
                                name="yearOfBirth"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                month-Of-Birth
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter month-Of-Birth"
                                name="monthOfBirth"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                day-Of-Birth
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter dayOfBirth"
                                name="dayOfBirth"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Line
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Line"
                                name="line"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                District
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter District"
                                name="district"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                State
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter State"
                                name="state"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Pin-code
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Pincode"
                                name="pincode"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                UserName
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter UserName"
                                name="username"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                Password
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Password"
                                name="password"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="Name" className="form-label">
                                role
                            </label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter role"
                                name="role"
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <center><button type="submit" className="btn btn-outline-primary">
                            Submit
                        </button>
                            <Link className="btn btn-outline-danger mx-2" to="/admin">
                                Cancel
                            </Link>
                        </center>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default AddDoctor;