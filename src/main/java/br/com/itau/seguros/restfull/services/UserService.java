package br.com.itau.seguros.restfull.services;

import br.com.itau.seguros.restfull.dto.UserDTO;
import br.com.itau.seguros.restfull.domain.user.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User create(UserDTO obj);

    User update(UserDTO obj);

    void delete(Integer id);

}




