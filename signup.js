import React from 'react';
import './signup.css';

function signup(){

    return(
        <div className="wrapper">
    <div className="login">
        <div className="header">
            Signup
        </div>
    <div className="login-form">
        <input
           // onChange={changeID}
            className="userid"
            type="text"
           // value={loginID}
            placeholder="ID"
        />
        <input
           // onChange={changePassword}
            className="password"
            type="password"
           // value={password}
            placeholder="Pw(영문,숫자 6자 이상)"
        />
        <input
           // onChange={confirming}
            className="confirm_password"
            type="password"
            //value={confirmPassword}
            placeholder="Confirm PW"
        />
        <button
            className="loginbtn"
            type="button"
            //onClick={}
        >
            회원가입
        </button>
    </div>
</div>
</div>
);
}

export default signup;