package br.com.itau.seguros.restfull.repositories;

import br.com.itau.seguros.restfull.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Integer> {

}
