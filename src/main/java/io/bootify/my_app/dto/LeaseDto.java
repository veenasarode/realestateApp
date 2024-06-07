package io.bootify.my_app.dto;

import io.bootify.my_app.domain.Lease;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LeaseDto {

    private Integer leaseId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(length = 45)
    private String status;

    @Column(length = 45)
    private String ownerId;

    @Column(length = 45)
    private String renterId;

    @Column(length = 45)
    private String amount;

    @Column(length = 45)
    private String rent;

    @Column(length = 45)
    private String deposit;

    @Column(length = 45)
    private String propertyBookingcol;

    public LeaseDto(Lease lease)
    {
        this.leaseId = lease.getLeaseId();
        this.startDate = lease.getStartDate();
        this.endDate = lease.getEndDate();
        this.status = lease.getStatus();
        this.ownerId = lease.getOwnerId();
        this.renterId = lease.getRenterId();
        this.amount = lease.getAmount();
        this.rent = lease.getRent();
        this.deposit = lease.getDeposit();
        this.propertyBookingcol = lease.getPropertyBookingcol();
    }
}
