package com.hexagonal.client.domain.models;

import java.util.UUID;

import com.hexagonal.client.domain.models.abstracts.Person;
import com.hexagonal.client.domain.models.enums.GenderEnum;
import com.hexagonal.client.domain.models.valueObjects.Address;
import com.hexagonal.client.domain.models.valueObjects.Age;
import com.hexagonal.client.domain.models.valueObjects.Cellphone;
import com.hexagonal.client.domain.models.valueObjects.Gender;
import com.hexagonal.client.domain.models.valueObjects.Guid;
import com.hexagonal.client.domain.models.valueObjects.Name;
import com.hexagonal.client.domain.models.valueObjects.Password;
import com.hexagonal.client.domain.models.valueObjects.State;

public class Client extends Person {
    private Guid clientId;
    private Password password;
    private State state;

    public Client() {
    }

    public Client(Guid clientId,
            Password password,
            State state,
            Guid personId,
            Name name,
            Gender gender,
            Age age,
            Address address,
            Cellphone cellphone) {
        super(personId, name, gender, age, address, cellphone);
        this.clientId = clientId;
        this.password = password;
        this.state = state;
    }

    public static Client CreateFromRequest(String clientId,
            String password,
            boolean state,
            String personId,
            String name,
            String gender,
            int age,
            String address,
            String cellphone) {

        if (clientId == null) {
            clientId = Guid.Create().getGuid();
        }

        if (personId == null) {
            personId = Guid.Create().getGuid();
        }

        if (password == null) {
            password = "";
        }

        Client client = new Client(
                new Guid(clientId),
                Password.CreateWithoutKey(password),
                State.Create(state),
                new Guid(personId),
                Name.Create(name),
                Gender.Create(gender),
                Age.Create(age),
                Address.Create(address),
                Cellphone.Create(cellphone));
        return client;
    }

    public static Client CreateFromEntity(String clientId,
            String secretPassword,
            String secretKey,
            boolean state,
            String personId,
            String name,
            String gender,
            int age,
            String address,
            String cellphone) {
        Client client = new Client(
                new Guid(clientId),
                Password.CreateWithKey(secretPassword, secretKey),
                State.Create(state),
                new Guid(personId),
                Name.Create(name),
                Gender.Create(gender),
                Age.Create(age),
                Address.Create(address),
                Cellphone.Create(cellphone));
        return client;
    }

    public String getClientId() {
        return clientId.getGuid();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getSecretPassword() {
        return password.getEncryptedPassword();
    }

    public String getSecretKey() {
        return password.getSecret();
    }

    public boolean getState() {
        return state.getState();
    }

    public UUID getClientUUID() {
        return clientId.getUUID();
    }

    public void setClientId(Guid clientId) {
        this.clientId = clientId;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setState(boolean state) {
        this.state = State.Create(state);
    }

    public Client merge(Client other) {
        if (!other.getName().isEmpty() && !other.getName().equals(this.getName())) {
            this.setName(other.getName());
        }

        if (!other.getGender().equals(GenderEnum.DEFAULT.getValue())) {
            this.setGender(other.getGender());
        }

        if (other.getAge() > 0 && !other.getAge().equals(this.getAge())) {
            this.setAge(other.getAge());
        }

        if (!other.getAddress().isEmpty() && !other.getAddress().equals(this.getAddress())) {
            this.setAddress(other.getAddress());
        }

        if (!other.getCellphone().isEmpty() && !other.getCellphone().equals(this.getCellphone())) {
            this.setCellphone(other.getCellphone());
        }

        if (!other.getPassword().isEmpty()) {
            this.setPassword(other.password);
        }

        if (other.getState() != this.getState()) {
            this.setState(other.getState());
        }

        return this;
    }
}
