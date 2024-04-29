import React, { useEffect } from 'react';
import "../css/errorPage.css";

const ErrorPage = () => {
  useEffect(() => {
    const removeLoadingClass = () => {
      document.body.classList.remove('loading');
    };

    const timer = setTimeout(removeLoadingClass, 1000);
    
    return () => clearTimeout(timer);
  }, []);


  return (
    <div className="">
      <link href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400" rel="stylesheet" />
      <h1>500</h1>
      <h2>Ошибка на сервере <b>:(</b></h2>
      <div className="loading">
        <div className="gears">
          <div className="gear one">
            <div className="bar"></div>
            <div className="bar"></div>
            <div className="bar"></div>
          </div>
          <div className="gear two">
            <div className="bar"></div>
            <div className="bar"></div>
            <div className="bar"></div>
          </div>
          <div className="gear three">
            <div className="bar"></div>
            <div className="bar"></div>
            <div className="bar"></div>
          </div>
        </div>
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script src="js/main.js" type="text/javascript"></script>
      </div>
    </div>
  );
};

export default ErrorPage;