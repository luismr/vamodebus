package br.com.vamodebus.model;

/**
 * Created by edusr on 7/1/13.
 */
public class History {

    private String id;
    private String code;
    private String name;
    private String accesNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccesNumber() {
        return accesNumber;
    }

    public void setAccesNumber(String accesNumber) {
        this.accesNumber = accesNumber;
    }

    @Override
    public String toString(){
        return name;
    }
}
