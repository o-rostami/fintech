package com.example.fintech.model.token;


import java.util.Date;

import com.example.fintech.model.Auditable;
import com.example.fintech.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_token", indexes = { @Index(name = "token", columnList = "token") })
public class Token extends Auditable {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;

	private String token;

	@Enumerated(EnumType.STRING)
	private TokenType tokenType;

	private boolean expired;

	private boolean revoked;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Version
	private Long version;

//	@CreationTimestamp
//	private Date createdAt;
//
//	@UpdateTimestamp
//	private Date updatedAt;


}
