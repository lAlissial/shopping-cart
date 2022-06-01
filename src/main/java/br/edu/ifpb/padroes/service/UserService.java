package br.edu.ifpb.padroes.service;

import br.edu.ifpb.padroes.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User saveUser(User user);

}
