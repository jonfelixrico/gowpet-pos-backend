package com.gowpet.pos.catalog;

import java.time.Instant;

import com.gowpet.pos.user.service.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@With
@Getter
@Entity
public class CatalogItem {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	
	private String name;
	private Float price;
	private ItemType type;
	

	@ManyToOne
	@JoinColumn(nullable=false)
	private User createBy;
	private Instant createDt;
	
	@ManyToOne
	@JoinColumn
	private User deleteBy;
	private Instant deleteDt;
}
