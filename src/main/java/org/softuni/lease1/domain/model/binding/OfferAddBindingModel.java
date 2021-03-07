package org.softuni.lease1.domain.model.binding;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OfferAddBindingModel {
    private Integer deposit;
    private Integer term;
    private Integer residualValue;
    private String status;

    public OfferAddBindingModel() {
    }

    @NotNull(message = "Please fill deposit")
    @Min(value = 0, message = "Deposit must be at least 0.")
    @Max(value = 80, message = "Deposit could not exceed 80%")
    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    @NotNull(message = "Please fill repayment term")
    @Min(value = 12, message = "Minimum term is 12 months")
    @Max(value = 72, message = "Term could not exceed 72 months")
    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    @NotNull(message = "Please fill residual value")
    @Min(value = 0, message = "Residual value must be at least 0.")
    @Max(value = 60, message = "Residual value could not exceed 60%")
    public Integer getResidualValue() {
        return residualValue;
    }

    public void setResidualValue(Integer residualValue) {
        this.residualValue = residualValue;
    }

}
