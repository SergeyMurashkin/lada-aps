package ru.murashkin.ladaAps.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customers_seq")
    @SequenceGenerator(name="Customers_seq", sequenceName="Customers_SEQUENCE")
    private Long id;
    @NotEmpty
    @Size(min = 1, max = 35)
    private String firstName;
    @NotEmpty
    @Size(min = 1, max = 25)
    private String lastName;
    @NotEmpty
    @Size(min = 1, max = 35)
    @Column(unique = true)
    @Email
    private String email;

    public Customer() {
    }

    public Customer(@NotEmpty @Size(min = 1, max = 35) String firstName,
                    @NotEmpty @Size(min = 1, max = 25) String lastName,
                    @NotEmpty @Size(min = 1, max = 35) @Email String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
