package br.com.vamodebus.model;

/**
 * Created by Eduardo Silva Rosa on 30/06/2013.
 * mail to: edus.silva.rosa@gmail.com
 */
public class FavoriteRoute {

    private String id;
    private String code;
    private String name;
	private int accesNumber;

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
    
    public int getAccesNumber() {
		return accesNumber;
	}

	public void setAccesNumber(int accesNumber) {
		this.accesNumber = accesNumber;
	}
}
