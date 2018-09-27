package com.example.polls.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.polls.model.audit.UserDateAudit;

@Entity
@Table(name = "paints", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "user_id",
                "id"
        })
})
public class Paint extends UserDateAudit {

	@Id
    private Long id;
    
	@NotBlank
	@Size(max = 20)
	private String paintId;
	@NotBlank
	@Size(max = 20)
	private String paintName;
	@NotBlank
	@Size(max = 20)
	private String rgb;
	@NotBlank
	@Size(max = 20)
	private String brand;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
		return "{" + "id=" + id + ", brand='" + brand + '\'' + ", name='" + paintName + '\'' + ", paintId='"
				+ paintId + '\'' + ", rgb='" + rgb + '\'' + '}';
	}
}
