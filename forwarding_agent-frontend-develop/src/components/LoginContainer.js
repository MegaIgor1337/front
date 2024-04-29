import React, { useState } from "react";
import "../css/loginContainer.css";
import { request, setAuthToken } from "../util/axios_helper";
import { useNavigate } from 'react-router-dom';

function LoginContainer() {
  const [emailFocused, setEmailFocused] = useState(false);
  const [passwordFocused, setPasswordFocused] = useState(false);
  const [emailValid, setEmailValid] = useState(true);
  const [passwordValid, setPasswordValid] = useState(true);
  const [emailValue, setEmailValue] = useState("");
  const [passwordValue, setPasswordValue] = useState("");
  const [emailVisited, setEmailVisited] = useState(false);
  const [passwordVisited, setPasswordVisited] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const navigate = useNavigate();

  const handleEmailFocus = () => {
    setEmailFocused(true);
    setErrorMessage("");
  };

  const handlePasswordFocus = () => {
    setPasswordFocused(true);
    setErrorMessage("");
  };

  const handleEmailBlur = () => {
    setEmailFocused(false);
    setEmailVisited(true);
    setEmailValid(emailValue.trim() !== "" ? emailValid : false);
  };

  const handlePasswordBlur = () => {
    setPasswordFocused(false);
    setPasswordVisited(true);
    setPasswordValid(passwordValue.trim() !== "" ? passwordValid : false);
  };

  const handlePasswordChange = (e) => {
    const { value } = e.target;
    setPasswordValue(value);
    setPasswordValid(value.trim() !== "");
  };

  const handleEmailChange = (e) => {
    const { value } = e.target;
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const isValid = value.trim() === "" || emailRegex.test(value);
  
    setEmailValue(value);
    setEmailValid(isValid);
    setEmailVisited(true);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const emailEmpty = emailValue.trim() === "";
    const passwordEmpty = passwordValue.trim() === "";
    const emailInvalidFormat = !emailValid;

    setEmailVisited(true);
    setPasswordVisited(true);
    setEmailValid(!emailEmpty && !emailInvalidFormat);
    setPasswordValid(!passwordEmpty);

    if (emailValid && passwordValid) {
      request("POST", "/api/v1/auth/login", { email: emailValue, password: passwordValue })
        .then((response) => {
          setAuthToken(response.data.token);
        })
        .catch((error) => {
          if (error.response) {
            if (error.response.status === 400) {
              setErrorMessage("Неверная электронная почта или пароль");
            }
          } else if (error.message === "Network Error") {
            navigate('/server-error', { replace: true });
          } 
        });
    }
  };

  const isEmailInvalid = !emailValid && emailVisited && !emailFocused;
  const isPasswordInvalid = !passwordValid && passwordVisited && !passwordFocused;

  return (
    <div className="modal-container">
      <h2 className="login-title">Вход</h2>
      <form className="login-form" onSubmit={handleSubmit}>
        <div>
          <label htmlFor="email"></label>
          <input
            placeholder="Эл. почта"
            className={`${isEmailInvalid ? "invalid" : ""}`}
            onFocus={handleEmailFocus}
            onBlur={handleEmailBlur}
            onChange={handleEmailChange}
          />
          {isEmailInvalid && (
            <p className="validation-message">
              {emailVisited && !emailValid && emailValue.trim() === ""
                ? "Обязательное поле"
                : "Неверный формат почты"}
            </p>
          )}
        </div>
        <div>
          <label htmlFor="password"></label>
          <input
            type="password"
            placeholder="Пароль"
            className={`${isPasswordInvalid ? "invalid" : ""}`}
            onFocus={handlePasswordFocus}
            onBlur={handlePasswordBlur}
            onChange={handlePasswordChange}
          />
          {!passwordValid && passwordVisited && !passwordFocused && (
            <p className="validation-message">
              {isPasswordInvalid && passwordValue.trim() === ""
                ? "Обязательное поле"
                : ""}
            </p>
          )}
        </div>

        <div className="error-message">
          {errorMessage}
        </div>
        <button className="btn btn--form" type="submit">
          Войти
        </button>
      </form>
    </div>
  );
}

export default LoginContainer;
