package com.dr.betadapurrakyat.Model;

/**
 * Created by ASUS on 1/27/2018.
 */

public class UserData {
    private String FullName, UserName, Password, NoID, Alamat, Kodepos;

    public UserData() {
    }

    public UserData(String fullName, String userName, String password, String noID, String alamat, String kodepos) {
        FullName = fullName;
        UserName = userName;
        Password = password;
        NoID = noID;
        Alamat = alamat;
        Kodepos = kodepos;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNoID() {
        return NoID;
    }

    public void setNoID(String noID) {
        NoID = noID;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getKodepos() {
        return Kodepos;
    }

    public void setKodepos(String kodepos) {
        Kodepos = kodepos;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "FullName='" + FullName + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", NoID='" + NoID + '\'' +
                ", Alamat='" + Alamat + '\'' +
                ", Kodepos='" + Kodepos + '\'' +
                '}';
    }
}
