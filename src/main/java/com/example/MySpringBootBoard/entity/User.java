package com.example.MySpringBootBoard.entity;


import jakarta.persistence.*; // JPA 관련 어노테이션
import java.time.LocalDateTime; // 날짜와 시간을 다루기 위함

@Entity // 데이터베이스 테이블과 매핑
@Table(name = "users") // 매핑될 테이블 이름을 users로 지정

public class User {

	 @Id // 기본 키(Primary Key)를 나타냄
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에 위임
	 private Integer id; // 사용자 고유 번호
	 
	 @Column(nullable = false) // 사용자 이름에 null을 허용하지않음
	 private String name;
	 
	 @Column(nullable = false, unique = true) // 로그인 ID에 null를 허용하지 않으며 유일해야함
	 private String username;
	 
	 @Column(nullable = false) // 비밀번호에 null을 허용하지 않음
	 private String password;
	 
	 @Column(nullable = false, unique = true) // 이메일주소에 null을 허용하지않으며 유일해야함
	 private String email;
	 
	 @Column(nullable = false, unique = true) // 핸드폰번호에 null을 허용하지 않으며 유일해야함
	 private String phoneNumber;
	 
	 // 계정 생성일시
	 private LocalDateTime createDate;
	 
	// 계정 정보 수정일시 (null 허용)
	 private LocalDateTime modifyDate;

	 public Integer getId() {
		 return id;
	 }

	 public void setId(Integer id) {
		 this.id = id;
	 }

	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }

	 public String getUsername() {
		 return username;
	 }

	 public void setUsername(String username) {
		 this.username = username;
	 }

	 public String getPassword() {
		 return password;
	 }

	 public void setPassword(String password) {
		 this.password = password;
	 }

	 public String getEmail() {
		 return email;
	 }

	 public void setEmail(String email) {
		 this.email = email;
	 }

	 public String getPhoneNumber() {
		 return phoneNumber;
	 }

	 public void setPhoneNumber(String phoneNumber) {
		 this.phoneNumber = phoneNumber;
	 }

	 public LocalDateTime getCreateDate() {
		 return createDate;
	 }

	 public void setCreateDate(LocalDateTime createDate) {
		 this.createDate = createDate;
	 }

	 public LocalDateTime getModifyDate() {
		 return modifyDate;
	 }

	 public void setModifyDate(LocalDateTime modifyDate) {
		 this.modifyDate = modifyDate;
	 }
	 // JPA 엔티티 생명주기 콜백 메서드(insert 시 creatDate 자동 설정)
	 @PrePersist
	 protected void onCreate() {
	        this.createDate = LocalDateTime.now();
	    }
	    // JPA 엔티티 생명주기 콜백 메서드 (UPDATE 시 modifyDate 자동 설정)
	    @PreUpdate
	    protected void onUpdate() {
	        this.modifyDate = LocalDateTime.now();
	    }
}
