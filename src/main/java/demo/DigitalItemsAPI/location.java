package demo.DigitalItemsAPI;
//	"lat":32.115139,
//	"lng":34.817804
public class location {
	
	private Double lat;
	private Double lng;
	public location() {
	}
	public location(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}

}
