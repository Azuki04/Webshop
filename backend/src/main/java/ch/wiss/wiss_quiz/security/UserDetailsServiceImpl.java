package ch.wiss.wiss_quiz.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.wiss.wiss_quiz.model.Role;
import ch.wiss.wiss_quiz.model.User;
import ch.wiss.wiss_quiz.model.UserRepository;

/**
 * UserDetailsService is used to fetch a UserDetails object. The interface has only one method: UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
 * So we implement it and override loadUserByUsername() method.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    
        List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role r : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(r.getName()));
    }

    return UserDetailsImpl.build(user);
  }
}
