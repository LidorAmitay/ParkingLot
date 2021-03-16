package demo;

//"key1":"can be set to any value you wish",
//"key2":"you can also name the attributes any name you like",
//"key3":58,
//"key4":false
public class itemAttributes {
	
	private String key1;
	private String key2;
	private Integer key3;
	private Boolean key4;
	
	public itemAttributes() {
		
	}
	public itemAttributes(String key1, String key2, Integer key3, Boolean key4) {
		super();
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
		this.key4 = key4;
	}
	
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getKey2() {
		return key2;
	}
	public void setKey2(String key2) {
		this.key2 = key2;
	}
	public Integer getKey3() {
		return key3;
	}
	public void setKey3(Integer key3) {
		this.key3 = key3;
	}
	public Boolean getKey4() {
		return key4;
	}
	public void setKey4(Boolean key4) {
		this.key4 = key4;
	}
	

}
