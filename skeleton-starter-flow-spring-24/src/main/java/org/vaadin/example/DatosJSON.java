package org.vaadin.example;

import java.util.UUID;

public class DatosJSON {

    private UUID _id;
    private String msCode;

    private String year;

    private String estCode;

    private float estimate;

    private float se;

    private float lowerCIB;

    private float upperCIB;

    private String flag;



    public DatosJSON() {
    }

    public DatosJSON(UUID _id, String msCode, String year, String estCode, float estimate, float SE, float LowerCIB, float UpperCIB, String flag) {
        this._id = _id;
        this.msCode = msCode;
        this.year = year;
        this.estCode = estCode;
        this.estimate = estimate;
        this.se = SE;
        this.lowerCIB = LowerCIB;
        this.upperCIB = UpperCIB;
        this.flag = flag;
    }

    public String getMsCode() {
        return msCode;
    }

    public void setMsCode(String msCode) {
        this.msCode = msCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEstCode() {
        return estCode;
    }

    public void setEstCode(String estCode) {
        this.estCode = estCode;
    }

    public float getEstimate() {
        return estimate;
    }

    public void setEstimate(float estimate) {
        this.estimate = estimate;
    }

    public float getSe() {
        return se;
    }

    public void setSe(float se) {
        this.se = se;
    }

    public float getLowerCIB() {
        return lowerCIB;
    }

    public void setLowerCIB(float lowerCIB) {
        this.lowerCIB = lowerCIB;
    }

    public float getUpperCIB() {
        return upperCIB;
    }

    public void setUpperCIB(float upperCIB) {
        this.upperCIB = upperCIB;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "DatosJSON{" +
                "_id=" + _id +
                ", msCode='" + msCode + '\'' +
                ", year='" + year + '\'' +
                ", estCode='" + estCode + '\'' +
                ", estimate=" + estimate +
                ", se=" + se +
                ", lowerCIB=" + lowerCIB +
                ", upperCIB=" + upperCIB +
                ", flag='" + flag + '\'' +
                '}';
    }

    public String mostrarJson() {
        return "{\n" +
                "\"_id\": " + "\"" + get_id() + "\"," + "\n" +
                "\"msCode\": " + "\"" + getMsCode() + "\"," + "\n" +
                "\"year\": " + "\"" + getYear()  + "\"," + "\n" +
                "\"estCode\": " + "\"" + getEstCode() + "\""  +  ",\n" +
                "\"estimate\": " + getEstimate() + ",\n" +
                "\"se\": " + getSe() + ",\n" +
                "\"lowerCIB\": " + getLowerCIB() + ",\n" +
                "\"upperCIB\": " + getUpperCIB() + ",\n" +
                "\"flag\": " + "\"" + getFlag() + "\"" + "\n" +
                "}";
    }

}
