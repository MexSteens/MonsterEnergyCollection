import React from 'react';
import ReactDOM from 'react-dom/client';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import Home from './pages/Home';
import Scanner from './pages/Scanner';
import Result from './pages/Result';
import Can from './pages/Can';
import Log from './pages/Log';
import Register from './pages/Register';
import Login from './pages/Login';
import Approval from './pages/Approval';
import Roles from './pages/Roles';
import { useState } from 'react';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />} >
        <Route path="home" element={<Home />} />
        <Route path="scanner" element={<Scanner />} />
        <Route path="result/:barcode" element={localStorage.getItem('jwt') === null ? <Navigate to="/" /> : <Result />} />
        <Route path="can/:barcode" element={localStorage.getItem('jwt') === null ? <Navigate to="/" /> : <Can />} />
        <Route path="log" element={<Log />} />
        <Route index element={<Register />} />
        <Route path="login" element={<Login />} />
        <Route path="roles" element={<Roles />} />
        <Route path="approval" element={<Approval />} />
      </Route>
    </Routes>
  </BrowserRouter>
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.register();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
