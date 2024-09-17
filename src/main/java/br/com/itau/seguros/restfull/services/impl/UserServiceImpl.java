package br.com.itau.seguros.restfull.services.impl;

import br.com.itau.seguros.restfull.dto.UserDTO;
import br.com.itau.seguros.restfull.domain.user.User;
import br.com.itau.seguros.restfull.exceptions.DataIntegratyViolationException;
import br.com.itau.seguros.restfull.exceptions.ObjectNotFoundException;
import br.com.itau.seguros.restfull.repositories.UserRepository;
import br.com.itau.seguros.restfull.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ModelMapper mapper;




    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente Nao encontrado"));


    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        validarEmail(obj);
        return userRepository.save(mapper.map(obj, User.class));

    }

    @Override
    public User update(UserDTO obj) {
        validarEmail(obj);
        return userRepository.save(mapper.map(obj, User.class));
    }

    @Override
    public void  delete(Integer id) {
        findById(id);
        userRepository.deleteById(id);

    }


    private void validarEmail(UserDTO obj) {

        Optional<User> user = userRepository.findByEmail(obj.getEmail());
        if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegratyViolationException("E-mail já cadastrado no sistema.");
        }
    }



}
