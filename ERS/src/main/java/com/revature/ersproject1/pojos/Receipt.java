package com.revature.ersproject1.pojos;

import java.io.File;
import java.io.Serializable;

public class Receipt implements Serializable{

	private static final long serialVersionUID = 1;
	
	private File location;
	
	public Receipt() {
		// TODO Auto-generated constructor stub
	}

	public Receipt(File location) {
		super();
		this.location = location;
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		Receipt other = (Receipt) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Receipt [location=" + location + "]";
	}
	
	

}
