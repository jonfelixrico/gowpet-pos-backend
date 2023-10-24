package com.gowpet.pos.catalog;

import java.time.Instant;

import com.gowpet.pos.user.service.User;

import jakarta.persistence.Column;
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
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Float price;
	
	@Column(nullable = false)
	private ItemType type;
	

	@ManyToOne
	@JoinColumn(nullable=false)
	private User createBy;
	@Column(nullable=false)
	private Instant createDt;
	
	private ItemStatus status;
	
	@ManyToOne
	@JoinColumn
	private User updateBy;
	private Instant updateDt;

	private Integer updateCtr;
}
