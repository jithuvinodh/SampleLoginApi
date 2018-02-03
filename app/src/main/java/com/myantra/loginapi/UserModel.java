package com.myantra.loginapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Myantra on 02-02-2018.
 */

public class UserModel implements Serializable {

    private String userName;
    private String EmailId;
    private String schoolName;
    private String phoneNo;
    private String code;

    public static UserModel fromJson(JSONObject jsonObject){

        UserModel userModel = new UserModel();

        try {

            userModel.code = jsonObject.getString("code");
            userModel.userName = jsonObject.getJSONObject("userToken").getString("userName");
            userModel.EmailId = jsonObject.getJSONObject("userToken").getString("emailId");
            userModel.schoolName = jsonObject.getJSONObject("userToken").getString("schoolName");
            userModel.phoneNo = jsonObject.getJSONObject("userToken").getString("phoneNo");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userModel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
