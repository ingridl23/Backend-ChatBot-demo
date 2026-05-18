package com.chat.demo.service.auth;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.chat.demo.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

	    private final User user;

	    public CustomUserDetails(User user) {
	        this.user = user;
	    }

	   /*
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return user.getRoles().stream()
	                .flatMap(role -> role.getPermissions().stream())
	                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
	                .toList();
	    }
*/
	    
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return user.getRoles().stream()
	                .flatMap(role -> {
	                    var roleAuthority =
	                            java.util.stream.Stream.of(
	                                    new SimpleGrantedAuthority("ROLE_" + role.getName())
	                            );

	                    var permissionAuthorities = role.getPermissions().stream()
	                            .map(permission ->
	                                    new SimpleGrantedAuthority(permission.getName()));

	                    return java.util.stream.Stream.concat(
	                            roleAuthority,
	                            permissionAuthorities
	                    );
	                })
	                .toList();
	    }
	    
	    
	   
	    
	    @Override
	    public String getPassword() {
	        return user.getPassword();
	    }

	    @Override
	    public String getUsername() {
	        return user.getUserName();
	    }

	    @Override
	    public boolean isAccountNonExpired() { return true; }

	    @Override
	    public boolean isAccountNonLocked() { return true; }

	    @Override
	    public boolean isCredentialsNonExpired() { return true; }

	    @Override
	    public boolean isEnabled() { return true; }

		public User getUser() {
			return user;
		}
	}


