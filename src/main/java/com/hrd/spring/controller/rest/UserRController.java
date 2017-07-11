package com.hrd.spring.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hrd.spring.entities.User;
import com.hrd.spring.entities.Form.UserForm;
import com.hrd.spring.entities.filters.UserFilter;
import com.hrd.spring.entities.responses.HttpMessage;
import com.hrd.spring.entities.responses.Pagination;
import com.hrd.spring.entities.responses.Response;
import com.hrd.spring.entities.responses.ResponseHttpStatus;
import com.hrd.spring.entities.responses.ResponseList;
import com.hrd.spring.entities.responses.ResponseRecord;
import com.hrd.spring.entities.responses.Table;
import com.hrd.spring.entities.responses.Transaction;
import com.hrd.spring.entities.responses.failure.ResponseFailure;
import com.hrd.spring.entities.responses.failure.ResponseListFailure;
import com.hrd.spring.entities.responses.failure.ResponseRecordFailure;
import com.hrd.spring.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/v2/api/user")
public class UserRController {
	
	private HttpStatus httpStatus = HttpStatus.OK;
	private UserServiceImpl userservice;
	@Autowired
	public UserRController(UserServiceImpl userservice) {
		super();
		this.userservice = userservice;
	}
	@GetMapping("/find-all-user")
	public ResponseEntity<ResponseList<User>> findAll(
			@RequestParam (name="limit",required= false,defaultValue="2") int limit,
			@RequestParam (name="page",required= false,defaultValue="1") int page
			){
		ResponseList<User> responseList;
		Pagination pagination = new Pagination();
		try{
			pagination.setLimit(limit);
			pagination.setPage(page);
			pagination.setTotalCount(userservice.countTotalUsers());
			pagination.setOffSet((page-1)*limit);
			List<User> users = userservice.findAll();
			if(!users.isEmpty()){
				responseList = new ResponseList<User>(
						HttpMessage.success(Table.USERS, Transaction.Success.RETRIEVE),  // message
						true,  // status 
						users,  // data
						pagination); // pagination
				System.out.println("sucess");
			}else{
				httpStatus = HttpStatus.NOT_FOUND;
				responseList = new ResponseListFailure<User>(
						HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE), // message
						false, // status 
						ResponseHttpStatus.RECORD_NOT_FOUND // error
						
						);
				System.out.println("failed");
			}
		}catch(Exception  e){
			e.printStackTrace();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseList = new ResponseListFailure<User>(
					HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE),
					false,
					ResponseHttpStatus.INTERNAL_SERVER_ERROR
					);
		}
		return new ResponseEntity<ResponseList<User>>(responseList, httpStatus);
	}
	
	@GetMapping("/{uuid}")
	public ResponseEntity<ResponseRecord<User>> findbyUUID(
			@PathVariable("uuid") String uuid
			){
		
		ResponseRecord<User> responseRecord = new ResponseRecord<User>();
		User users = userservice.findByUUID(uuid);
		try{
			if(users != null){
				responseRecord = new ResponseRecord<User>(
						HttpMessage.success(Table.USERS, Transaction.Success.RETRIEVE), 
						true, 
						users);
			}else{
				httpStatus = HttpStatus.NOT_FOUND;
				responseRecord = new ResponseRecordFailure<User>(
						HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE), 
						false, 
						ResponseHttpStatus.RECORD_NOT_FOUND);
			}
		}catch(Exception e){
			e.printStackTrace();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseRecord = new ResponseRecordFailure<User>(
					HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE), 
					false, 
					ResponseHttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseRecord<User>>(responseRecord, httpStatus);
	}
	
	@PutMapping("/{uuid}")
	public ResponseEntity<Response<User>> update(
			@Valid @RequestBody UserForm userfrm,
			BindingResult result
			){
		Response<User> response = new Response<User>();
		if(result.hasErrors()){
			httpStatus = HttpStatus.NOT_ACCEPTABLE;
			response = new ResponseFailure<User>(
					HttpMessage.invalid(Table.USERS, Transaction.Fail.UPDATED, result.toString()),
					false, 
					ResponseHttpStatus.BAD_REQUEST);
		}else{
			
			try{
				if(userservice.update(userfrm)){
					response = new Response<User>(
							HttpMessage.success(Table.USERS, Transaction.Success.UPDATED), 
							true);
				}else{
					httpStatus = HttpStatus.NOT_ACCEPTABLE;
					response = new ResponseFailure<User>(
							HttpMessage.fail(Table.USERS, Transaction.Fail.UPDATED),
							false, 
							ResponseHttpStatus.FAIL_UPDATED);
				}
			}catch(Exception e){
				e.printStackTrace();
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				response = new ResponseFailure<User>(
						HttpMessage.fail(Table.USERS, Transaction.Fail.UPDATED),
						false, 
						ResponseHttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		return new ResponseEntity<Response<User>>(response , httpStatus);
	}
	@PatchMapping("/update/status/{status}/{uuid}")
	public ResponseEntity<Response<User>> updateStatusByUUID(
			@PathVariable("status") String status,
			@PathVariable("uuid") String uuid
		//	BindingResult result
			){
		Response<User> response = new Response<User>();	
			try{
				if(userservice.updateStatusByUUID(status,uuid)){
					response = new Response<User>(
							HttpMessage.success(Table.USERS, Transaction.Success.UPDATED), 
							true);
				}else{
					httpStatus = HttpStatus.NOT_ACCEPTABLE;
					response = new ResponseFailure<User>(
							HttpMessage.fail(Table.USERS, Transaction.Fail.UPDATED),
							false, 
							ResponseHttpStatus.FAIL_UPDATED);
				}
			}catch(Exception e){
				e.printStackTrace();
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				response = new ResponseFailure<User>(
						HttpMessage.fail(Table.USERS, Transaction.Fail.UPDATED),
						false, 
						ResponseHttpStatus.INTERNAL_SERVER_ERROR);
			}
		
		return new ResponseEntity<Response<User>>(response , httpStatus);
	}
	
	@PostMapping("/filter")
	public ResponseEntity<ResponseList<User>> userFilter(@RequestBody UserFilter userFilter){
		ResponseList<User> responseList = new ResponseList<User>();
		try{
			List<User> users = userservice.userFilter(userFilter);
			if(!users.isEmpty()){
				responseList = new ResponseList<User>(
						HttpMessage.success(Table.USERS, Transaction.Success.RETRIEVE),  // message
						true,  // status 
						users,  // data
						null); // pagination
				System.out.println("sucess");
			}else{
				httpStatus = HttpStatus.NOT_FOUND;
				responseList = new ResponseListFailure<User>(
						HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE), // message
						false, // status 
						ResponseHttpStatus.RECORD_NOT_FOUND // error
						
						);
				System.out.println("failed");
			}
		}catch(Exception  e){
			e.printStackTrace();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			responseList = new ResponseListFailure<User>(
					HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE),
					false,
					ResponseHttpStatus.INTERNAL_SERVER_ERROR
					);
		}
		return new ResponseEntity<ResponseList<User>>(responseList, httpStatus);
	}
	
	 @PostMapping
	    private ResponseEntity<ResponseRecord<User>> addUser(@RequestBody UserForm userFrm) {
		 ResponseRecord<User> responseList = new ResponseRecord<User>();
			try{
				User users = userservice.addUser(userFrm);
				if(users!=null){
					responseList = new ResponseRecord<User>(
							HttpMessage.success(Table.USERS, Transaction.Success.RETRIEVE),  // message
							true,  // status 
							users); // pagination
					System.out.println("sucess");
				}else{
					httpStatus = HttpStatus.NOT_FOUND;
					responseList = new ResponseRecordFailure<User>(
							HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE), // message
							false, // status 
							ResponseHttpStatus.RECORD_NOT_FOUND // error
							
							);
					System.out.println("failed");
				}
			}catch(Exception  e){
				e.printStackTrace();
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseList = new ResponseRecordFailure<User>(
						HttpMessage.fail(Table.USERS, Transaction.Fail.RETRIEVE),
						false,
						ResponseHttpStatus.INTERNAL_SERVER_ERROR
						);
			}
			return new ResponseEntity<ResponseRecord<User>>(httpStatus);
	    }
	 
	 @PutMapping
	    public ResponseEntity<ResponseRecord> updateUser(@RequestBody UserForm userForm) {
	        ResponseRecord<User> responseRecord = new ResponseRecord<>();
	        try{
	            boolean updateSuccessful = userservice.updateUser(userForm);
	            if(updateSuccessful) {
	                responseRecord = new ResponseRecord<>(HttpMessage.success(Table.USERS, Transaction.Success.UPDATED),
	                        true, null);

	            } else {
	                httpStatus = HttpStatus.NOT_FOUND;
	                responseRecord = new ResponseRecordFailure<User>(
	                        HttpMessage.fail(Table.USERS, Transaction.Fail.UPDATED),
	                        false, ResponseHttpStatus.NOT_FOUND);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	            responseRecord = new ResponseRecordFailure<User>(
	                    HttpMessage.fail(Table.USERS, Transaction.Fail.UPDATED),
	                    false, ResponseHttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        return new ResponseEntity<ResponseRecord>(responseRecord, httpStatus);
	    }

	 @PutMapping("/delete/{uuid}")
	    public ResponseEntity<ResponseRecord> deleteUserByUUID(@PathVariable("uuid") String uuid) {
	        ResponseRecord<User> responseRecord;
	        try{
	            boolean deleteUser = userservice.deleteUserByUUID(uuid);
	            if(deleteUser) {
	                responseRecord = new ResponseRecord<>(HttpMessage.success(Table.USERS, Transaction.Success.DELETED),
	                        true, null);
	            } else {
	                httpStatus = HttpStatus.NOT_FOUND;
	                responseRecord = new ResponseRecordFailure<User>(
	                        HttpMessage.fail(Table.USERS, Transaction.Fail.DELETED),
	                        false, ResponseHttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	            responseRecord = new ResponseRecordFailure<User>(
	                    HttpMessage.fail(Table.USERS, Transaction.Fail.DELETED),
	                    false, ResponseHttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        return new ResponseEntity<ResponseRecord>(responseRecord, httpStatus);
	    }

}
