package com.hexagonal.client.domain.models.abstracts;

import java.util.UUID;

import com.hexagonal.client.domain.models.valueObjects.Address;
import com.hexagonal.client.domain.models.valueObjects.Age;
import com.hexagonal.client.domain.models.valueObjects.Cellphone;
import com.hexagonal.client.domain.models.valueObjects.Gender;
import com.hexagonal.client.domain.models.valueObjects.Guid;
import com.hexagonal.client.domain.models.valueObjects.Name;

public abstract class Person {
    private Guid personId;
    private Name name;
    private Gender gender;
    private Age age;
    private Address address;
    private Cellphone cellphone;

    public Person() {
    }

    public Person(Guid personId, Name name, Gender gender, Age age, Address address, Cellphone cellphone) {
        this.personId = personId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.cellphone = cellphone;
    }

    public String getId() {
        return personId.getGuid();
    }

    public String getName() {
        return name.getName();
    }

    public String getGender() {
        return gender.getGender();
    }

    public Integer getAge() {
        return age.getAge();
    }

    public String getAddress() {
        return address.getAddress();
    }

    public String getCellphone() {
        return cellphone.getCellphone();
    }

    public UUID getPersonUUID() {
        return personId.getUUID();
    }

    public Guid getPersonId() {
        return personId;
    }

    public void setPersonId(Guid personId) {
        this.personId = personId;
    }

    public void setName(String name) {
        this.name = Name.Create(name);
    }

    public void setGender(String gender) {
        this.gender = Gender.Create(gender);
    }

    public void setAge(Integer age) {
        this.age = Age.Create(age);
    }

    public void setAddress(String address) {
        this.address = Address.Create(address);
    }

    public void setCellphone(String cellphone) {
        this.cellphone = Cellphone.Create(cellphone);
    }

}
