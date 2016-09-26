package model;

import java.io.Serializable;

public class Address implements Serializable
{
	private String streetName;
	private String area;
	private int pincode;

	public Address(String streetName, String area, int pincode)
	{
		this.streetName = streetName;
		this.area = area;
		this.pincode = pincode;
	}

	public String getStreetName()
	{
		return streetName;
	}

	public void setStreetName(String streetName)
	{
		this.streetName = streetName;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public int getPincode()
	{
		return pincode;
	}

	public void setPincode(int pincode)
	{
		this.pincode = pincode;
	}

	public String toString()
	{
		return "Address{" +
				       "streetName='" + streetName + '\'' +
				       ", area='" + area + '\'' +
				       ", pincode=" + pincode +
				       '}';
	}
}
