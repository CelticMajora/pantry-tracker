package com.pantrytracker.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ingredient_delete_statistics")
public class IngredientDeleteStatistics implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "wastossed")
	private Boolean wasTossed;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getWasTossed() {
		return wasTossed;
	}
	
	public void setWasTossed(Boolean wasTossed) {
		this.wasTossed = wasTossed;
	}

}
