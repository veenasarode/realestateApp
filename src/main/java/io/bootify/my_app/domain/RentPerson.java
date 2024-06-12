package io.bootify.my_app.domain;

import io.bootify.my_app.dto.RentPersonDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class RentPerson {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentPerson;

    @Column(length = 45)
    private String name;

    @Column(length = 45)
    private String address;

    @Column(length = 45)
    private String moNumber;

    @Column(length = 45)
    private String rentPersoncol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

    @OneToMany(mappedBy = "rentPerson")
    private Set<Lease> rentPersonLease;

    public RentPerson(RentPersonDto rentPersonDto) {
        this.rentPerson = rentPersonDto.getRentPersonId();
        this.name = rentPersonDto.getName();
        this.address = rentPersonDto.getAddress();
        this.moNumber = rentPersonDto.getMoNumber();
        this.rentPersoncol = rentPersonDto.getRentPersoncol();
    }
}
