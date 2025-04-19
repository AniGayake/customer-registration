package com.banking.app.user.registration.service;

import com.banking.app.user.registration.bo.Address;
import com.banking.app.user.registration.dto.request.AddressRequest;
import com.banking.app.user.registration.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;
    @Autowired
    public AddressService(final AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address registerAddress(final AddressRequest addressRequest){
        Address address = populateAddress(addressRequest);
        return addressRepository.save(address);
    }

    private static Address populateAddress(AddressRequest addressRequest) {
        Address address= new Address();
        address.setCustomerId(addressRequest.getCustomerId());
        address.setAddressLine1(addressRequest.getAddressLine1());
        address.setAddressLine2(addressRequest.getAddressLine2());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setCountry(addressRequest.getCountry());
        address.setPostalCode(addressRequest.getPostalCode());
        return address;
    }
}
