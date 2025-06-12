package com.example.hospital_project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import jakarta.transaction.Transactional;


public interface Admin_repo extends JpaRepository<Admin_model, String>{

	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="insert into admin(id,name,email,pass) values(:id,:name,:email,:pass)")
	void admin_add(@Param("id") String id,@Param("name") String name
			,@Param("email") String email,@Param("pass") String pass);
	
	
	
	@Query(nativeQuery = true,value="select count(*) from admin where id = :id ")
	int admin_idCount(@Param("id") String id);
	
	@Query(nativeQuery = true,value="select * from admin where id = :id ")
	Admin_model admin_search(@Param("id") String id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="delete from admin where id = :id ")
	void admin_delete(@Param("id") String id);
	
	
	@Query(nativeQuery = true,value="select * from admin ")
	List<Admin_model> admin_fetch();
	
	@Query(nativeQuery = true,value="select * from admin where email = :email ")
	Admin_model admin_login(@Param("email") String email);
	
	
	@Query(nativeQuery = true,value="select * from admin where email = :email ")
	Admin_model admin_verify_email(@Param("email") String email);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update admin set name = :name,email=:email where id = :id")
	void admin_edit(@Param("id") String id,@Param("name") String name
			,@Param("email") String email);
	
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true,value="update admin set pass=:npass where id=:id")
	void admin_update_pass(@Param("npass") String npass,@Param("id") String id);
}
