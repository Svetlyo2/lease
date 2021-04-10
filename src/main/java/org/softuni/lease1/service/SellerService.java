package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.binding.SellerAddBindingModel;
import org.softuni.lease1.domain.model.service.SellerServiceModel;

public interface SellerService {
    SellerServiceModel add(SellerAddBindingModel sellerAddBindingModel, String offerId);
}
