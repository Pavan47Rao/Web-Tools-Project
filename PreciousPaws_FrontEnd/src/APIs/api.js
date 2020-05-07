import axios from 'axios';

var config = {
  headers: {"authtoken": sessionStorage.getItem("authtoken")}
};

export const getuserdata = async (username,password,role) => {
    try {
      let res;
      if(role === "doctor") {
        res = await axios.get('http://localhost:8080/test/api/doctor/'+username+'/password/'+password, config);
      }
      else if(role === "admin") {
        res = await axios.get('http://localhost:8080/test/api/vetOrg/'+username+'/password/'+password, config);
      }
      else {
        res = await axios.get('http://localhost:8080/test/api/petOwner/'+username+'/password/'+password, config);
      }
      const user = res.data;
      return user;
    }     
    catch (error) {
      console.error(error);
    }
}

export const getServiceCatalog = async () => {
  try{
    let res;
    res = await axios.get('http://localhost:8080/test/api/serviceCatalogs', config);
    const catalog = res.data;
    return catalog;
  }
  catch (error) {
    console.error(error);
  }
}

export const getDoctorAppointments = async (userId) => {
  try{
    let res;
    res = await axios.get('http://localhost:8080/test/api/doctorAppointments/'+userId, config);
    const appointments = res.data;
    return appointments;
  }
  catch (error) {
    console.error(error);
  }
}

export const getServiceAppointments = async (userId) => {
  try{
    let res;
    res = await axios.get('http://localhost:8080/test/api/serviceAppointments/',config);
    const appointments = res.data;
    return appointments;
  }
  catch (error) {
    console.error(error);
  }
}

export const getDoctorAppointmentById = async (appointmentId) => {
  try{
    let res;
    res = await axios.get('http://localhost:8080/test/api/doctorAppointment/'+appointmentId, config);
    const appointment = res.data;
    return appointment;
  }
  catch (error) {
    console.error(error);
  }
}

export const getDoctorAppointmentByOwnerId = async (ownerId) => {
  try{
    let res;
    res = await axios.get('http://localhost:8080/test/api/doctorAppointments/owner/'+ownerId, config);
    const appointment = res.data;
    return appointment;
  }
  catch (error) {
    console.error(error);
  }
}

export const getServiceAppointmentById = async (appointmentId) => {
  try{
    let res;
    res = await axios.get('http://localhost:8080/test/api/serviceAppointment/'+appointmentId, config);
    const appointment = res.data;
    return appointment;
  }
  catch (error) {
    console.error(error);
  }
}

export const getServiceAppointmentByOwnerId = async (ownerId) => {
  try{
    let res;
    res = await axios.get('http://localhost:8080/test/api/serviceAppointments/owner/'+ownerId, config);
    const appointment = res.data;
    return appointment;
  }
  catch (error) {
    console.error(error);
  }
}

export const addMedicationn = async (medication, appointmentId) => {
  try {
   const res = await axios.post('http://localhost:8080/test/api/medication/add/doctorAppointment/'+appointmentId,medication, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const updateMedicationn = async (medication, appointmentId) => {
  try {
   const res = await axios.post('http://localhost:8080/test/api/medication/update/appointmentId/'+appointmentId,medication, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const updateAppointment = async (appointment, doctorId, ownerId) => {
  try {
   const res = await axios.post('http://localhost:8080/test/api/doctorAppointment/update/doctor/'+doctorId+'/owner/'+ownerId,appointment, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const updateServiceAppointment = async (statusId, status) => {
  try {
   const res = await axios.get('http://localhost:8080/test/api/update/serviceAppointment/sId/'+statusId+'/status/'+status, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const getSchedules = async (doctorId) => {
  try{
    const res = await axios.get('http://localhost:8080/test/api/doctor/'+doctorId, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const getAdminById = async (userId) => {
  try{
    const res = await axios.get('http://localhost:8080/test/api/vetOrgUser/'+userId, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const deleteServiceByCatalogId = async (catalogId) => {
  try{
    const res = await axios.post('http://localhost:8080/test/api/delete/serviceCatalog/cid/'+catalogId, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const addServiceToCatalog = async(service) => {
  try{
    const res = await axios.post('http://localhost:8080/test/api/serviceCatalog/add',service, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const getAllDoctors = async() => {
  try{
    const res = await axios.get('http://localhost:8080/test/api/doctors/', config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const getMedication = async(appointmentId) => {
  try{
    const res = await axios.get('http://localhost:8080/test/api/medication/appointmentId/'+appointmentId, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const createDoctorAppointment = async(doc, doctorId, ownerId) => {
  try{
    const res = await axios.post('http://localhost:8080/test/api/doctorAppointment/add/doctor/'+doctorId+'/owner/'+ownerId,doc, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const createServiceAppointment = async(appointment, ownerId) => {
  try{
    const res = await axios.post('http://localhost:8080/test/api/serviceAppointment/add/owner/'+ownerId,appointment, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const createDoctor = async(doctor) => {
  try{
    const res = await axios.post('http://localhost:8080/test/api/doctor/add',doctor, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const createAdmin = async(admin) => {
  try{
    const res = await axios.post('http://localhost:8080/test/api/vetOrg/add',admin, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}

export const createPetOwner = async(owner) => {
  try{
    const res = await axios.post('http://localhost:8080/test/api/petOwner/add',owner, config);
    return res.data;
  } catch (error) {
    console.error(error) 
  }
}