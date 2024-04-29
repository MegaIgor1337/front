import React, { useState } from "react";
import { getRequest } from "../util/axios_helper";
import { useNavigate } from 'react-router-dom';


function Contact({ showContactsEmail, arrowRotated, toggleContactsEmail }) {
  const [emails, setEmails] = useState([]);
  const [loading, setLoading] = useState(false); 
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  const getEmails = () => {
    setLoading(true); 
    getRequest("GET", "/api/v1/getEmailsForContact", null)
      .then((response) => {
        console.log(response.data);
        setEmails(response.data);
      })
      .finally(() => {
        setLoading(false);
      })
      .catch((error) => {
        if (error.response) {
          if (error.response.status === 404) {
            setErrorMessage("Контактов нет");
          }
        } else if (error.message === "Network Error") {
          navigate('/server-error', { replace: true });
        } 
      });
  };

  return (
    <div className="contacts-container">
      <div
        className={`contacts-text ${showContactsEmail ? "active" : ""}`}
        onClick={() => {
          getEmails();
          toggleContactsEmail();
        }}
      >
        Контакты
        <div
          className={`contacts-arrow ${
            arrowRotated ? "rotate" : ""
          }`}
        ></div>
      </div>
      <div className={`contacts-email ${showContactsEmail ? "active" : ""}`}>
        {loading ? (
          <div>Загрузка...</div>
        ) : emails.length !== 0 ? (
          <ul>
            {emails.map((email, index) => (
              <li key={index}>{email}</li>
            ))}
          </ul>
        ) : (
          <p>{errorMessage}</p>
        )}
      </div>
    </div>
  );
}

export default Contact;
