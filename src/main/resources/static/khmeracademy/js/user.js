/**
 * 
 */
	var user={};
	
	$(document).ready(function(){
		
		 
		 
		 /*** Events ***/ 
		 
		/** OnClick **/
		
		$(document).on('click',"#btDelete" , function(){
			user.DeleteUser($(this).data("uuid"));
		});
		
		$(document).on('click' , "#btDetail" , function(){
			user.detail($(this).data("uuid"));
		});

		$(document).on('click' , "#btEdit" , function(){ 
			user.findUser($(this).data("uuid"));
		});
		
		
		$(document).on('click' , "#btAdd" , function(){ 
			$('#userAddModal').modal('show');
		});

		$(document).on('click' , "#btUpdateStatus" , function(){ 
			user.updateUserStatus($(this).data("uuid"), $(this).data("status") );
		});
//		
		
		/** Submit **/
		
		// Submit category data to update 
		$("#userUpdateForm").submit(function(e){
				 e.preventDefault();
				 user.submitCategoryToUpdate();
		});
		
		// Submit category data to insert 
		$("#userAddForm").submit(function(e){
				 e.preventDefault();
				 user.submitUserToInsert();
		});
		
		
		
		
		/*** Function ***/  
		
		// Display category detail by uuid
		user.detail = function(data){
			
			$.ajax({ 
			    url:  "http://localhost:8081/v2/api/user/"+ data, 
			    type: "GET",
			    beforeSend: function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
			    success: function(data) { 
					console.log(data);
					
					$("#username").text(data.data.username);
//					$("#createdDate").text(moment(data.data.created_date).format("DD-MM-YYYY HH:mm:ss"));
					$("#status").text(data.data.status);
					$("#index").text(data.data.index);
				//	$("#articleCount").text(data.data.couont_article);
					$("#remark").text(data.data.remark);
					// Show Popup 
					$('#userDetailModal').modal('show');
			    },
			    error:function(data,status,er) { 
			         console.log(data);
			    }
			});
	
}
		
		// find category detail by uuid to set in update category form 
		user.findUser = function(data){
					
					$.ajax({ 
					    url:  "http://localhost:8081/v2/api/user/"+ data, 
					    type: "GET",
					    beforeSend: function(xhr) {
		                    xhr.setRequestHeader("Accept", "application/json");
		                    xhr.setRequestHeader("Content-Type", "application/json");
		                },
					    success: function(data) { 
							console.log(data);
							
							$("#userUpdateForm #uuid").val(data.data.uuid);
							$("#userUpdateForm #username").val(data.data.username);
							$("#userUpdateForm #status").val(data.data.status);
							$("#userUpdateForm #index").val(data.data.index);
						//	$("#userUpdateForm #remark").val(data.data.remark);
							
							// Show Popup 
							$('#userEditModal').modal('show');
					    },
					    error:function(data,status,er) { 
					        console.log(data);
					    }
					});
			
		};
		
		
		// Submit category data to update
		user.submituserToUpdate = function(){
				cateData = {
						  "uuid": $("#userUpdateForm #uuid").val(),
						  "name": $("#userUpdateForm #name").val(),
						  "status": $("#userUpdateForm #status").val(),
						  "index": parseInt($("#userUpdateForm #index").val()),
						  "remark":  $("#userUpdateForm #remark").val()
				};
				console.log(cateData);
				
				swal({   title: " user" ,   
					 text: "Are you sure you want to update this user?",   
					 type: "info",  
					 showCancelButton: true,   
					 closeOnConfirm: false,   
					 showLoaderOnConfirm: true, 
				}, function(){   
					$.ajax({ 
					    url:  "http://localhost:8081/v2/api/user", 
					    type: "PUT",
					    data: JSON.stringify(cateData),
					    beforeSend: function(xhr) {
		                    xhr.setRequestHeader("Accept", "application/json");
		                    xhr.setRequestHeader("Content-Type", "application/json");
		                },
					    success: function(data) { 
							console.log(data);
							swal(data.message);
						    user.findAllUser();
							$('#userEditModal').modal('hide');
							 
					    	
					    },
					    error:function(data,status,er) { 
					         console.log(data);
					    }
				});

				
			});
		
		};
		
		
		
		// find all category 
		user.findAllUser = function(){
					var url = "http://localhost:8081/admin/user/fragment/users";
					$("#results").load(url);
		};
		
		
		user.DeleteUser = function(data){
			swal({  title: " User" ,   
					text: "Are you sure you want to deleted this name?",   
					type: "info",  
					showCancelButton: true,   
					closeOnConfirm: false,   
					showLoaderOnConfirm: true, 
			}, function(){   
				$.ajax({ 
				    url:  "http://localhost:8081/v2/api/user/delete/"+ data, 
				    type: "DELETE",
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                },
				    success: function(data) { 
						console.log(data);
						swal(data.message);
						user.findAllUser();
						
				    },
				    error:function(data,status,er) { 
				         console.log(data);
				    }
				});
			
					
			});		
	};
		
		
		// Submit category data to insert
		user.submitUserToInsert = function(){
			
			userData = {
					  "username": $("#userAddForm #name").val(),
					  "status": $("#userAddForm #status").val(),
					  "gender":  $("#userAddForm #gender").val()
			};
			console.log(userData);
			
			swal({   title: "User" ,   
				 text: "Are you sure you want to add this person?",   
				 type: "info",  
				 showCancelButton: true,   
				 closeOnConfirm: false,   
				 showLoaderOnConfirm: true, 
			}, function(){   
				$.ajax({ 
				    url:  "http://localhost:8081/v2/api/user", 
				    type: "POST",
				    data: JSON.stringify(userData),
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                },
				    success: function(data) { 
						console.log(data);
				    	    swal(data.message);
						user.findAllUser();
						$('#userAddModal').modal('hide');
				    },
				    error:function(data) { 
				        console.log(data);
				        
				    }
			});
		});
	
	};
		
	user.updateUserStatus = function(uuid,status){
		
		swal({  title: " User" ,   
					text: "Are you sure you want to update this person status?",   
					type: "info",  
					showCancelButton: true,   
					closeOnConfirm: false,   
					showLoaderOnConfirm: true, 
			}, function(){   
				$.ajax({ 
				    url:  "http://localhost:8081/v2/api/user/status/"+uuid+"/status/"+status, 
				    type: "PUT",
				    beforeSend: function(xhr) {
	                    xhr.setRequestHeader("Accept", "application/json");
	                    xhr.setRequestHeader("Content-Type", "application/json");
	                },
				    success: function(data) { 
						console.log(data);
						swal(data.message);
						user.findAllUser();
				    },
				    error:function(data,status,er) { 
				        console.log(data);
				    }
				});
			
					
			});

	};
		
		
		 /** OnLoad **/
		 
		 user.findAllUser();
		 
		
		
		
	});