package com.tc.training.model;

import com.tc.training.utils.Role;
import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

 @Id
 private UUID id;

 private String firstName;

 private String lastName;

 private String password;

 private LocalDate dob;

 private String email;

 private Integer age;

 private String aadharCardNumber;

 private String panCardNumber;

 private String phoneNumber;

 @Enumerated(EnumType.STRING)
 private Role roleName = Role.ROLE_CUSTOMER;


 public UUID getId() {
  return id;
 }

 public void setId(UUID id) {
  this.id = id;
 }

 public String getFirstName() {
  return firstName;
 }

 public void setFirstName(String firstName) {
  this.firstName = firstName;
 }

 public String getLastName() {
  return lastName;
 }

 public void setLastName(String lastName) {
  this.lastName = lastName;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public LocalDate getDob() {
  return dob;
 }

 public void setDob(LocalDate dob) {
  this.dob = dob;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public Integer getAge() {
  return age;
 }

 public void setAge(Integer age) {
  this.age = age;
 }

 public String getAadharCardNumber() {
  return aadharCardNumber;
 }

 public void setAadharCardNumber(String aadharCardNumber) {
  this.aadharCardNumber = aadharCardNumber;
 }

 public String getPanCardNumber() {
  return panCardNumber;
 }

 public void setPanCardNumber(String panCardNumber) {
  this.panCardNumber = panCardNumber;
 }

 public String getPhoneNumber() {
  return phoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
  this.phoneNumber = phoneNumber;
 }

 public Role getRoleName() {
  return roleName;
 }

 public void setRoleName(Role roleName) {
  this.roleName = roleName;
 }



}
