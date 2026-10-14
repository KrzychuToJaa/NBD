package pobi.infrastructure.jpa.entity;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for {@link pobi.infrastructure.jpa.entity.ClientEnt}
 **/
@StaticMetamodel(ClientEnt.class)
public abstract class ClientEnt_ {

	
	/**
	 * @see #phoneNumber
	 **/
	public static final String PHONE_NUMBER = "phoneNumber";
	
	/**
	 * @see #firstName
	 **/
	public static final String FIRST_NAME = "firstName";
	
	/**
	 * @see #lastName
	 **/
	public static final String LAST_NAME = "lastName";
	
	/**
	 * @see #city
	 **/
	public static final String CITY = "city";
	
	/**
	 * @see #street
	 **/
	public static final String STREET = "street";
	
	/**
	 * @see #number
	 **/
	public static final String NUMBER = "number";
	
	/**
	 * @see #archive
	 **/
	public static final String ARCHIVE = "archive";

	
	/**
	 * Static metamodel type for {@link pobi.infrastructure.jpa.entity.ClientEnt}
	 **/
	public static volatile EntityType<ClientEnt> class_;
	
	/**
	 * Static metamodel for attribute {@link pobi.infrastructure.jpa.entity.ClientEnt#phoneNumber}
	 **/
	public static volatile SingularAttribute<ClientEnt, String> phoneNumber;
	
	/**
	 * Static metamodel for attribute {@link pobi.infrastructure.jpa.entity.ClientEnt#firstName}
	 **/
	public static volatile SingularAttribute<ClientEnt, String> firstName;
	
	/**
	 * Static metamodel for attribute {@link pobi.infrastructure.jpa.entity.ClientEnt#lastName}
	 **/
	public static volatile SingularAttribute<ClientEnt, String> lastName;
	
	/**
	 * Static metamodel for attribute {@link pobi.infrastructure.jpa.entity.ClientEnt#city}
	 **/
	public static volatile SingularAttribute<ClientEnt, String> city;
	
	/**
	 * Static metamodel for attribute {@link pobi.infrastructure.jpa.entity.ClientEnt#street}
	 **/
	public static volatile SingularAttribute<ClientEnt, String> street;
	
	/**
	 * Static metamodel for attribute {@link pobi.infrastructure.jpa.entity.ClientEnt#number}
	 **/
	public static volatile SingularAttribute<ClientEnt, String> number;
	
	/**
	 * Static metamodel for attribute {@link pobi.infrastructure.jpa.entity.ClientEnt#archive}
	 **/
	public static volatile SingularAttribute<ClientEnt, Boolean> archive;

}

