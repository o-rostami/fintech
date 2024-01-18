package com.example.fintech.model;

//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;

import java.io.Serial;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
public class Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Serial
	private static final long serialVersionUID = -7155382914252567768L;

	@CreatedDate
	@Column(name = "created_date")
	private Long createdDate;

	@Column(name = "last_modified_date")
	private Long lastModifiedDate;

	@PrePersist
	public void onPrePersist() {
		createdDate = new Date().getTime();
	}

	@PreUpdate
	public void onPreUpdate() {
		lastModifiedDate = new Date().getTime();
	}
}
