package sssvn.location;

import java.util.ArrayList;

import sssvn.location.LocationCo;
import sssvn.personnel.Manager;
import sssvn.personnel.Person;
import sssvn.personnel.definers.PositionRequirednsessForEmployeeDefiner;
import sssvn.personnel.validators.PersonInitialsValidator;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.SkipEntityExistsValidation;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.Unique;
import ua.com.fielden.platform.entity.annotation.mutator.AfterChange;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.user.User;
import ua.com.fielden.platform.utils.Pair;

/**
 * Represents a location.
 *
 * @author Generated
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle(value="Location", desc = "A location that represents a point of distribution for a network of bakeries.")
@CompanionObject(LocationCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription

public class Location extends ActivatableAbstractEntity<DynamicEntityKey> {
	
    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Manager.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Country", desc = "Country where the bakery point is located.")
//    @BeforeChange(@Handler(LocationCountryValidator.class))
    private String country;
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "City", desc = "City where a bakery is located.")
    // @CompositeKeyMember(1)  // property will be the key of that entity
    // TODO: Implement Validators!
//    @BeforeChange(@Handler(LocationCityValidator.class))
    private String city;

    @IsProperty
    @MapTo
    @Required
    @Unique
    @Title(value = "Address", desc = "Location's address")
//  @BeforeChange(@Handler(LocationAddressValidator.class))
    @CompositeKeyMember(1)
    private String address;

    @IsProperty
    @MapTo
    @Unique
    @Required
    @Title(value="Phone number", desc = "Phone number of the location.")
    // @AfterChange(PositionRequirednsessForEmployeeDefiner.class)
//  @BeforeChange(@Handler(LocationPhoneValidator.class))
    @CompositeKeyMember(2)
    private String phone;
    
    
    @IsProperty
    @MapTo
    @Required
    @Title(value="Working hours", desc = "Working hours of the location.")
//  @BeforeChange(@Handler(LocationWorkingHoursValidator.class))
    private String workingHours;
    
    @IsProperty
    @MapTo
    @Required
    @Title(value="Employees amount", desc = "Number of employees at the location.")
//  @BeforeChange(@Handler(LocationEmployeeAmountValidator.class))
    private Long employeesAmount;
    
    @Observable
    public Location setCountry(final String country) {
        this.country = country;
        return this;
    }

    public String getCountry() {
        return country;
    }
    
    @Observable
    public Location setCity(final String city) {
        this.city = city;
        return this;
    }

    public String getCity() {
        return city;
    }
    
    @Observable
    public Location setPhone(final String phone) {
        this.phone = phone;
        return this;
    }

    public String getPhone() {
        return phone;
    }
    
    @Observable
    public Location setWorkingHours(final String workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    public String getWorkingHours() {
        return workingHours;
    }
    
    @Observable
    public Location setAddress(final String address) {
        this.address = address;
        return this;
    }

    public String getAddress() {
        return address;
    }
    
    @Observable
    public Location setEmployeesAmount(final Long employeesAmount) {
        this.employeesAmount = employeesAmount;
        return this;
    }

    public Long getEmployeesAmount() {
        return employeesAmount;
    }
    
}
