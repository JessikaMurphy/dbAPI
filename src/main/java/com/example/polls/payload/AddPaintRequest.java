package com.example.polls.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddPaintRequest {

	@NotNull
	
	private long id;
	@NotBlank
	@Size(max = 8)
	private String paintId;
	@NotBlank
	@Size(max = 20)
	private String paintName;
	@NotBlank
	@Size(max = 8)
	private String rgb;
	@NotBlank
	@Size(max = 15)
	private String brand;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getPaintName() {
		return paintName;
	}

	public void setPaintName(String paintName) {
		this.paintName = paintName;
	}

	public String getPaintId() {
		return paintId;
	}

	public void setPaintId(String paintId) {
		this.paintId = paintId;
	}

	public String getRgb() {
		return rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Paint{" + "id=" + id + ", brand='" + brand + '\'' + ", name='" + paintName + '\'' + ", paintId='"
				+ paintId + '\'' + ", rgb='" + rgb + '\'' + '}';
	}
}
