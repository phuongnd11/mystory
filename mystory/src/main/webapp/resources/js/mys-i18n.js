MYS.i18n = (function (w, doc, $, undefined){
	
	var opts = {
		en : {
			locale : "en",
			errors : {
				general : "An error occured. Your action did not complete successfully, please try again later.",
				login : {
					username : {
						required	: "Please enter your username"
					},
					password : {
						required	: "Please enter your password"
					}
					
				},
				register : {
					username : {
						required 	: "Please provide an username",
						rangelength	: "Username must be winthin 4-13 characters",
						remote		: "This username is already taken"
					},
					password : {
						required	: "Please enter your password",
						minlength	: "Password must be at least 6 characters"
					},
					email : {
						required	: "Please provide an email address",
						format		: "Please enter an email address",
						remote		: "This email is already used.<br>Did you <a href='/resetpassword'>forget your password?</a>"
					}
				},
				updatePassword : {
					oldRequired		: "Please enter your current password",
					newRequired		: "Please enter your new password",
				},
				telling : {
					content : {
						required	: "Please provide a content"
					},
					title : {
						required	: "Please provide a title",
						maxlength	: "Title can't be longer than 200 characters"
					},
					recipient : {
						required	: "Please provide a recipient",
						notexist	: "This user does not exists"
					}
				},
				upload : {
					inProgress		: "You have an upload in progress. If you choose to submit now, the uploading file won't be available, are you sure you want to submit?"
				}				
			},
			warnings : {
				onleave				: "Are you sure you want to leave this page? If you leave now, contents you have written will be lost."
			},
			labels : {
				categoriesList	: {
					add 			: "See more categories",
					complete 		: "Close textbox",
					addThis			: "Add this category to quick access bar"
				},
				telling : {
					submitting		: "Submitting..."
				}
			},
			editorStrings : {
				upimageTooltip		: "Upload an image"
			},
			helpers : {
				register : {
					label 			: "Registration completed!",
					message 		: "You account was created. Please check your mailbox for confirmation email.<br>" +
										"Email confirmation is not mandatory, but is needed if you want to change your account password later."
				},
				activate :{
					label 			: "Account activation completed!",
					message 		: "Your account is activated, you can now proceed to joining the MyStory community."
				},
				reset : {
					label 			: "Password reset completed successfully!",
					message 		: "Please check your mailbox for your new password."
				}
			}
		},
		vi : {
			locale : "vi",
			errors : {
				general : "Hệ thống đang bị lỗi. Bạn vui lòng thử lại sau.",
				login : {
					username : {
						required	: "Bạn chưa điền tên tài khoản"
					},
					password : {
						required	: "Bạn chưa điền mật khẩu"
					}
				},
				register : { 	
					username : {
						required	: "Bạn chưa điền tên tài khoản",
						rangelength	: "Hãy điền tên tài khoản với độ dài từ 4 tới 13 ký tự",
						remote		: "Tên tài khoản này đã có người sử dụng"
					},
					password : {
						required	: "Bạn chưa điền mật khẩu",
						minlength	: "Hãy điền mật khẩu có độ dài tối thiểu 6 ký tự",
					},
					email : {
						required	: "Bạn chưa điền địa chỉ email",
						format		: "Hãy cung cấp địa chỉ email của bạn",
						remote		: "Địa chỉ email đã được sử dụng.<br>Có phải bạn <a href='/resetpassword'>quên mật khẩu?</a>"
					}
				},
				updatePassword : {
					oldRequired		: "Bạn chưa điền mật khẩu hiện tại ",
					newRequired		: "Bạn chưa điền mật khẩu mới",
				},
				telling : {
					content : {
						required	: "Bạn chưa nhập nội dung tin nhắn"
					},
					title : {
						required	: "Bạn chưa nhập chủ đề",
						maxlength	: "Chủ đề không dài quá 200 ký tự"
					},
					recipient : {
						required	: "Bạn chưa điền tên người nhận",
						notexist	: "Không tồn tại thành viên này"
					}
				},
				upload : {
					inProgress		: "Hệ thống đang trong quá trình upload file. Nếu bạn gửi bài bây giờ, file đang upload sẽ không sử dụng được. Bạn có chắc chắn muốn gửi bài không?"
				}
			},
			warnings : {
				onleave				: "Bạn có chắc chắn muốn rời khỏi trang này không? Nếu bạn rời vào lúc này, những nội dung bạn đã viết vẫn chưa được lưu lại và sẽ mất."
			},
			labels : {
				categoriesList	: {
					add 			: "Xem thêm chuyên mục",
					complete 		: "Đóng textbox",
					addThis			: "Thêm chuyên mục này vào thanh truy cập nhanh"
				},
				telling : {
					submitting		: "Đang gửi..."
				}
			},
			editorStrings : {
				upimageTooltip		: "Up ảnh từ máy"
			},
			helpers : {
				register : {
					label 			: "Đăng ký thành công!",
					message 		: "Tài khoản của bạn đã được tạo. Hãy kiểm tra hòm thư của bạn để nhận email xác thực từ website.<br>" +
										"Việc xác thực địa chỉ email là không bắt buộc, nhưng rất cần thiết nếu bạn muốn thay đổi mật khẩu tài khoản sau này."
				},
				activate :{
					label 			: "Email xác thực thành công!",
					message 		: "Bạn có thể bắt đầu sử dụng MyStory từ bây giờ rồi. Đón chào bạn, thành viên mới đến cộng đồng MyStory."
				},
				reset : {
					label    : "Tài khoản của bạn đã có thể sử dụng lại.",
					message   : "Hãy kiểm tra email của bạn để lấy mật khẩu mới."
				}
			}
		}
	};
	
	// return public functions here.
	return {
		messages : $("html").attr("lang") == "en" ? opts.en : opts.vi
	};
	
}(window, document, jQuery));