package br.com.jwt.enumeration;

import static br.com.jwt.domain.constant.Authority.*;

public enum Role {
   USER_ROLE(USER_AUTHORITIES),
   HR_ROLE(HR_AUTHORITIES),
   ADMIN_ROLE(ADMIN_AUTHORITIES),
   MANAGER_ROLE(MANAGER_AUTHORITIES),
   SUPER_USER_ROLE(SUPER_USER_AUTHORITIES);

   public String[] authorities;



   Role(String... authorities){
      this.authorities = authorities;
   }

   public String[] getAuthorities(){
      return authorities;
   }
}
