package twins.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "Items")
public class ItemEntity {
 
	private String itemId; 
	private String userId;
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestamp;
	private double lat;
	private double lng;
	private String itemAttributes;
	
										// relate item entity to other item entities
	private Set<ItemEntity> itemChildren; // valid items children
	private ItemEntity itemParent; 		// valid original item or null

	// Constructor
	public ItemEntity() {
		this.itemChildren = new HashSet<>();
	}

	@Id
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Lob
	public String getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(String itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public void addItem(ItemEntity child) {
		this.getItemChildren().add(child);
		child.setItemParent(this);
	}

	@OneToMany(mappedBy = "itemParent", fetch = FetchType.LAZY)
	public Set<ItemEntity> getItemChildren() {
		return itemChildren;
	}

	public void setItemChildren(Set<ItemEntity> itemChildren) {
		this.itemChildren = itemChildren;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public ItemEntity getItemParent() {
		return itemParent;
	}

	public void setItemParent(ItemEntity itemParent) {
		this.itemParent = itemParent;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemEntity other = (ItemEntity) obj;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		return true;
	}


}
