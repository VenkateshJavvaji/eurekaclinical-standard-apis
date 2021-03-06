package org.eurekaclinical.standardapis.filter;

/*-
 * #%L
 * Eureka! Clinical Common
 * %%
 * Copyright (C) 2016 - 2017 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.security.Principal;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletRequest;
import org.eurekaclinical.standardapis.dao.UserDao;
import org.eurekaclinical.standardapis.entity.UserEntity;

/**
 * Filter that adds the user's roles from a {@link UserDao} to the request.
 * Users of this filter must bind {@link UserDao} in their Guice configuration.
 *
 * @author Andrew Post
 */
@Singleton
public class RolesFromDbFilter extends AbstractRolesFilter {
    
    private final UserDao userDao;

    /**
     * Instantiates a filter that assigns the user roles obtained from the
     * database. Users of this filter must bind the UserDao&lt;?, ?&gt;
     * interface to an implementation.
     * 
     * @param inUserDao the user data access object.
     */
    @Inject
    public RolesFromDbFilter(UserDao<?, ?> inUserDao) {
        this.userDao = inUserDao;
    }
    
    /**
    * The getRoles method will now return 
    * Null if UserObject not found,
    * EMPTY_ARRAY if the user found and don't have any associated roles and
    * String Array if the user found and have associated roles.
    * @param inPrincipal the user principal.
    * @param inRequest the HTTP request.
    * @return Array of Roles, empth array if no roles associated and Null if User not found
    * @author  Dileep Gunda
    * @since   06-11-18 
    */
    @Override
    protected String[] getRoles(Principal inPrincipal, ServletRequest inRequest) {
        UserEntity user = this.userDao.getByPrincipal(inPrincipal);
        if (user != null) {
            return user.getRoleNames();
        } else {
            return null;
        }

    }

}
