package image.processing.api;

import com.google.common.base.Preconditions;

/**
 * Simple pojo which describes a User entity for the example
 *
 * @author marenzon
 */
public class User {

  public User(final int id) {
    this.id = id;
  }

  private int id;
//  private String name;
//  private String address;
//  private String profession;

  // Empty constructor for the marshallers
  public User() {}



//  public User(final String name, final String address, final String profession) {
//    this.name = Preconditions.checkNotNull(name, "name may not be null");
//    this.address = Preconditions.checkNotNull(address, "address may not be null");
//    this.profession = Preconditions.checkNotNull(profession, "profession may not be null");
//  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

//  public String getName() {
//    return name;
//  }
//
//  public void setName(final String name) {
//    this.name = name;
//  }
//
//  public String getAddress() {
//    return address;
//  }
//
//  public void setAddress(final String address) {
//    this.address = address;
//  }
//
//  public String getProfession() {
//    return profession;
//  }
//
//  public void setProfession(final String profession) {
//    this.profession = profession;
//  }

  public void updateFrom(final User userData) {
    Preconditions.checkNotNull(userData, "userData may not be null");

//    if (userData.getName() != null) {
//      setName(userData.getName());
//    }
//
//    if (userData.getAddress() != null) {
//      setAddress(userData.getAddress());
//    }
//
//    if (userData.getProfession() != null) {
//      setProfession(userData.getProfession());
//    }
//  }
//
//  @Override
//  public String toString() {
//    return Objects.toStringHelper(this)
//            .add("id", id)
//            .add("name", name)
//            .add("address", address)
//            .add("profession", profession)
//            .toString();
  }
}
