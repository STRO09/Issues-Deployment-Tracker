package dtos;

public class UserDTO {


	private Long id;
	private String fullName;
    private String email;
    private String role;
    
    public UserDTO(Long id, String fullName, String email, String role) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.role = role;
	}
    
    public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
}
