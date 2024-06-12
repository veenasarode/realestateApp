package io.bootify.my_app.domain;

import io.bootify.my_app.dto.PropertyOwnerDto;
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
public class PropertyOwner {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proprtyWonerId;

    @Column(length = 45)
    private String moNumber;

    @Column(length = 45)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id", nullable = false)
    private User userUser;

    @OneToMany(mappedBy = "proprtyWoner")
    private Set<Property> proprtyWoner;

    @OneToMany(mappedBy = "proprtyWoner")
    private Set<Lease> proprtyWonerLeases;


    public PropertyOwner(PropertyOwnerDto dto) {
        this.proprtyWonerId = dto.getPropertyOwnerId();
        this.moNumber = dto.getMoNumber();
        this.address = dto.getAddress();
    }
}