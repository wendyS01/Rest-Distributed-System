package service.core;

/**
 * Interface to define the state to be stored in ClientInfo objects
 * Java Been of ClientInfo
 * @author Rem
 *
 */
public class ClientInfo {
	public static final char MALE				= 'M';
	public static final char FEMALE				= 'F';
	
	public ClientInfo(String name, char sex, int age, int points, int noClaims, String licenseNumber) {
		this.name = name;
		this.gender = sex;
		this.age = age;
		this.points = points;
		this.noClaims = noClaims;
		this.licenseNumber = licenseNumber;
	}

	public ClientInfo() {}

	/**
	 * Public fields are used as modern best practice argues that use of set/get
	 * methods is unnecessary as (1) set/get makes the field mutable anyway, and
	 * (2) set/get introduces additional method calls, which reduces performance.
	 */
	private String name;
	private char gender;
	private int age;
	private int points;
	private int noClaims;
	private String licenseNumber;

	public static char getMALE() {
		return MALE;
	}

	public static char getFEMALE() {
		return FEMALE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getNoClaims() {
		return noClaims;
	}

	public void setNoClaims(int noClaims) {
		this.noClaims = noClaims;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
}
